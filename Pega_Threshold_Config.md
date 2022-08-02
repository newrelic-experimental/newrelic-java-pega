# Pega Threshold Tracing

The instrumentation for Pega includes the ability to restrict the tracing on Activities, Steps, Rules and Functions.   
Tracing of activities is always enabled but are only reported when the time spent in the activity exceeds a configurable threshold.  In addition you can configure certain activities to always be traced regardless of whether its response time exceeds the threshold or not. 
 The tracing of steps, rules and functions can be enabled or disabled.  If enabled then they are only reported if they exceed the configured threshold or the name is configured to always report.
## Configuration
The configuration is contained in the file pega-config.json and must be placed in the New Relic Java Agent directory.   If not present then then the default configuration is used.   The directory is checked every minute to see if the file was created or edited.  If the file has changed then then the configuration is reloaded.  Note that the first check does not occur until 5 minutes after the application starts.  
### Configuration JSON
Here is the structure of the configuration.  If an item is not present then the default value is used. Note that the threshold value is milliseconds.   
{  
&nbsp;&nbsp;"activities": {  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"threshold": 100,   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"track": [  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]    
&nbsp;&nbsp;},    
&nbsp;&nbsp;"steps": {   
&nbsp;&nbsp;&nbsp;&nbsp;"enabled": true,    
&nbsp;&nbsp;&nbsp;&nbsp;"threshold": 100,    
&nbsp;&nbsp;&nbsp;&nbsp;"track": [   
&nbsp;&nbsp;&nbsp;&nbsp;]    
&nbsp;&nbsp;},   
&nbsp;&nbsp;"rules": {   
&nbsp;&nbsp;&nbsp;&nbsp;"enabled": false,   
&nbsp;&nbsp;&nbsp;&nbsp;"threshold": 100,   
&nbsp;&nbsp;&nbsp;&nbsp;"track": [  
&nbsp;&nbsp;&nbsp;&nbsp;]   
&nbsp;&nbsp;},   
&nbsp;&nbsp;"functions": {   
&nbsp;&nbsp;&nbsp;&nbsp;"enabled": true,   
&nbsp;&nbsp;&nbsp;&nbsp;"threshold": 100,   
&nbsp;&nbsp;&nbsp;&nbsp;"track": [   
&nbsp;&nbsp;&nbsp;&nbsp;]  
&nbsp;&nbsp;}   
}  
### Configuring track
The “track” attribute is used to configure the tracking of items to always track.  
It takes a comma-separated list of string values.  The string values are treated as Java regular expressions (slightly different from other regular expressions).  The following is a good reference of Java Regular Expressions (https://www.jrebel.com/blog/java-regular-expressions-cheat-sheet).  Note that an actual name will be treated as a regular expression that only matches the actual name.
#### Example
&nbsp;&nbsp;"activities": {    
&nbsp;&nbsp;&nbsp;&nbsp;"threshold": 100,    
&nbsp;&nbsp;&nbsp;&nbsp;"track": [   
&nbsp;&nbsp;&nbsp;&nbsp;“myActivity.*”,   
&nbsp;&nbsp;&nbsp;&nbsp;“loggingActivity”  
&nbsp;&nbsp;&nbsp;&nbsp;]  
&nbsp;&nbsp;},   

With the above configuration, any activity whose name starts with myActivity will be tracked as well as loggingActivity.  
Default Values
The default thresholds for all four items is 100 milliseconds.  
Activities are always tracked.  
By default steps, rules and functions are disabled (i.e. enabled is set to false).  So to track them you will need to explicitly enable them in pega-config.json.

