package com.nr.instrumentation.pega.pegarules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.newrelic.agent.config.ConfigFileHelper;
import com.newrelic.agent.deps.org.json.simple.JSONArray;
import com.newrelic.agent.deps.org.json.simple.JSONObject;
import com.newrelic.agent.deps.org.json.simple.parser.JSONParser;
import com.newrelic.agent.deps.org.json.simple.parser.ParseException;
import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.NewRelic;

public class PegaConfiguration implements Runnable {
	
	private File configFile;
	private long lastUpdate;
	public static long PEGA_ACTIVITY_THRESHOLD = 100L;
	public static long PEGA_STEP_THRESHOLD = 100L;
	public static long PEGA_FUNCTION_THRESHOLD = 100L;
	public static long PEGA_RULE_THRESHOLD = 100L;
	public static boolean PEGA_STEP_ENABLED = true;
	public static boolean PEGA_FUNCTION_ENABLED = false;
	public static boolean PEGA_RULE_ENABLED = false;
	private static List<String> included_activities = new ArrayList<String>();
	private static List<String> included_steps = new ArrayList<String>();
	private static List<String> included_functions = new ArrayList<String>();
	private static List<String> included_rules = new ArrayList<String>();
	
	static {
		if(!PegaConfigService.initialized) {
			PegaConfigService.init();
		}
	}
	
	public PegaConfiguration() {
		File agentDir = ConfigFileHelper.getNewRelicDirectory();
		configFile = new File(agentDir,"pega-config.json");
		if(configFile.exists()) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Found Pega Config file");
			lastUpdate = configFile.lastModified();
			boolean updated = processConfig(configFile);
			if(!updated) {
				NewRelic.getAgent().getLogger().log(Level.FINE, "Failed to load Pega configuration");
			}
		} else {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Did not find Pega Config file");
			lastUpdate = System.currentTimeMillis();
		}
	}
	

	@Override
	public void run() {
		if(configFile.exists()) {
			long lastMod = configFile.lastModified();
			if(lastMod > lastUpdate) {
				boolean updated = processConfig(configFile);
				if(!updated) {
					NewRelic.getAgent().getLogger().log(Level.FINE, "Failed to load Pega configuration that was modified");
				}
				
				lastUpdate = lastMod;
			}
		}

	}

	public static boolean includeActivity(String activity) {
		
		if(included_activities.isEmpty()) return false;
		
		for(String match : included_activities) {
			if(activity.matches(match)) return true;
		}

		return false;
	}
	
	public static boolean includeStep(String step) {
		
		if(!PEGA_STEP_ENABLED) return false;
		
		if(included_steps.isEmpty()) return false;
		
		for(String match : included_steps) {
			if(step.matches(match)) return true;
		}

		return false;
	}
	
	public static boolean includeRule(String rule) {
		
		if(!PEGA_RULE_ENABLED) return false;
		
		if(included_rules.isEmpty()) return false;
		
		for(String regex : included_rules) {
			if(rule.matches(regex)) return true;
		}

		return false;
	}
	
	public static boolean includeFunction(String function) {
		
		if(!PEGA_FUNCTION_ENABLED) return false;
		
		if(included_functions.isEmpty()) return false;

		for(String regex : included_functions) {
			if(function.matches(regex)) return true;
		}
		
		return false;
	}
	
	protected static boolean processConfig(File config) {
		Logger logger = NewRelic.getAgent().getLogger();
		logger.log(Level.FINE, "Processing Pega Configuration file: {0}", config);
		boolean updated = false;
		try {
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(config);
			Object obj = parser.parse(reader);
			if(obj != null) {
				JSONObject json = (JSONObject)obj;
				JSONObject activities = (JSONObject)json.get("activities");
				if(activities != null) {
					Long t = (Long)activities.get("threshold");
					if(t != null) {
						PEGA_ACTIVITY_THRESHOLD = t;
						logger.log(Level.FINE, "Set Pega Activity Trace threshold at {0}", PEGA_ACTIVITY_THRESHOLD);
					}
					JSONArray activitiesToTrack = (JSONArray) activities.get("track");
					if(activitiesToTrack != null) {
						List<String> toTrack = new ArrayList<String>();
						for(Object activityToTrack :activitiesToTrack) {
							toTrack.add(activityToTrack.toString());
						}
						included_activities.addAll(toTrack);
						logger.log(Level.FINE, "Set list of Pega activties to track as {0}", included_activities);
					}
					
				}
				JSONObject steps = (JSONObject)json.get("steps");
				if(steps != null) {
					Boolean enabled = (Boolean)steps.get("enabled");
					if(enabled != null) {
						PEGA_STEP_ENABLED = enabled;
					}
					Long t = (Long)steps.get("threshold");
					if(t != null) {
						PEGA_STEP_THRESHOLD = t;
					}
					JSONArray stepsToTrack = (JSONArray) steps.get("track");
					if(stepsToTrack != null) {
						List<String> toTrack = new ArrayList<String>();
						for(Object stepToTrack :stepsToTrack) {
							toTrack.add(stepToTrack.toString());
						}
						included_steps.addAll(toTrack);
					}
					
				}

				JSONObject rules = (JSONObject)json.get("rules");
				if(rules != null) {
					Boolean enabled = (Boolean)rules.get("enabled");
					if(enabled != null) {
						PEGA_RULE_ENABLED = enabled;
					}
					Long t = (Long)rules.get("threshold");
					if(t != null) {
						PEGA_RULE_THRESHOLD = t;
					}
					JSONArray functionsToTrack = (JSONArray) rules.get("track");
					if(functionsToTrack != null) {
						List<String> toTrack = new ArrayList<String>();
						for(Object functionToTrack :functionsToTrack) {
							
							
							toTrack.add(functionToTrack.toString());
						}
						included_rules.addAll(toTrack);
					}
					
				}

				JSONObject functions = (JSONObject)json.get("functions");
				if(functions != null) {
					Boolean enabled = (Boolean)functions.get("enabled");
					if(enabled != null) {
						PEGA_FUNCTION_ENABLED = enabled;
					}
					Long t = (Long)functions.get("threshold");
					if(t != null) {
						PEGA_FUNCTION_THRESHOLD = t;
					}
					JSONArray functionsToTrack = (JSONArray) functions.get("track");
					if(functionsToTrack != null) {
						List<String> toTrack = new ArrayList<String>();
						for(Object functionToTrack :functionsToTrack) {
							
							
							toTrack.add(functionToTrack.toString());
						}
						included_functions.addAll(toTrack);
					}
					
				}
				
				updated = true;
			} else  {
				logger.log(Level.FINE,"Root object was not of type JSONObject, it was {)}",obj);
				updated = false;
			}
			
		} catch (FileNotFoundException e) {
			logger.log(Level.FINE, e, "Did not parse Pega config file due o FileNotFoundException, using defaults");
			updated = false;
		} catch (IOException e) {
			logger.log(Level.FINE, e, "Did not parse Pega config file due o IOException, using defaults");
			updated = false;
		} catch (ParseException e) {
			logger.log(Level.FINE, e, "Did not parse Pega config file due o ParseException, using defaults");
			updated = false;
		}
		return updated;
	}
}
