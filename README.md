<a href="https://opensource.newrelic.com/oss-category/#new-relic-experimental"><picture><source media="(prefers-color-scheme: dark)" srcset="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/dark/Experimental.png"><source media="(prefers-color-scheme: light)" srcset="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/Experimental.png"><img alt="New Relic Open Source experimental project banner." src="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/Experimental.png"></picture></a>

![GitHub forks](https://img.shields.io/github/forks/newrelic-experimental/newrelic-java-pega?style=social)
![GitHub stars](https://img.shields.io/github/stars/newrelic-experimental/newrelic-java-pega?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/newrelic-experimental/newrelic-java-pega?style=social)

![GitHub all releases](https://img.shields.io/github/downloads/newrelic-experimental/newrelic-java-pega/total)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/newrelic-experimental/newrelic-java-pega)
![GitHub last commit](https://img.shields.io/github/last-commit/newrelic-experimental/newrelic-java-pega)
![GitHub Release Date](https://img.shields.io/github/release-date/newrelic-experimental/newrelic-java-pega)


![GitHub issues](https://img.shields.io/github/issues/newrelic-experimental/newrelic-java-pega)
![GitHub issues closed](https://img.shields.io/github/issues-closed/newrelic-experimental/newrelic-java-pega)
![GitHub pull requests](https://img.shields.io/github/issues-pr/newrelic-experimental/newrelic-java-pega)
![GitHub pull requests closed](https://img.shields.io/github/issues-pr-closed/newrelic-experimental/newrelic-java-pega)

# Pega Instrumentation

Instrumentation for Pega software.  Enables the Java Agent to monitor deep into the stack on Pega applications.

## Installation

To install the instrumentation:
1. Download the latest release.
2. In the New Relic Java Agent directory, create a directory named extensions if it does not already exist.
3. Copy the downloaded jar files into the extensions directory
4. Restart the Pega instance.  

## Getting Started

After installing the extensions you should start to see activity method metrics in your transactions.   
To verify that the instrumentation has loaded correctly,  you should be able to see metrics like the following in the Metric Explorer:   
/Supportability/WeaveInstrumentation/Loaded/com.newrelic.instrumentation.pega-*    


## Configuration

The instrumentation will track a Pega Activity and can be enabled to collect metrics on Rules, Steps and Functions.   In order to keep the number of traced methods for transactions and spans for distributed traces down, there are thresholds in place so that Activities are only reported if their response time exceeds the threshold.  Additional the tracking of Rules, Steps and Functions can be enabled.  This is done via a JSON configuration file.  See for details: https://github.com/newrelic-experimental/newrelic-java-pega/blob/main/Pega_Threshold_Config.md    

## Building

Although the code is available,  the jar files needed to compile are not available because they are Pega proprietary.  In each lib directory there is a holder.txt that lists the necessary jar files if you can gain access to them.

## Support

This instrumentation is designed to work with Pega 8.x versions.    

## Contributing

ew Relic has open-sourced this project. This project is provided AS-IS WITHOUT WARRANTY OR DEDICATED SUPPORT. Issues and contributions should be reported to the project here on GitHub.

We encourage you to bring your experiences and questions to the [Explorers Hub](https://discuss.newrelic.com) where our community members collaborate on solutions and new ideas.

**A note about vulnerabilities**

As noted in our [security policy](../../security/policy), New Relic is committed to the privacy and security of our customers and their data. We believe that providing coordinated disclosure by security researchers and engaging with the security community are important means to achieve our security goals.

If you believe you have found a security vulnerability in this project or any of New Relic's products or websites, we welcome and greatly appreciate you reporting it to New Relic through [HackerOne](https://hackerone.com/newrelic).


## License
New Relic Java Instrumentation for Pega is licensed under the [Apache 2.0](http://apache.org/licenses/LICENSE-2.0.txt) License.
