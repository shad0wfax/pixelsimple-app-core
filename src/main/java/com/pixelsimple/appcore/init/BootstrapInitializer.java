/**
 * © PixelSimple 2011-2012.
 */
package com.pixelsimple.appcore.init;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.commons.util.Assert;
import com.pixelsimple.commons.util.OSUtils;
import com.pixelsimple.commons.util.StringUtils;

/**
 *
 * @author Akshay Sharma
 * Jan 17, 2012
 */
public class BootstrapInitializer {
	private Pattern variableSubstitutionPattern = Pattern.compile("(\\$\\{[^}]*})",  Pattern.CASE_INSENSITIVE);

	// Want the java cli params statically identified here.  
	// The system arg passed from script during the startup: -Dapp.home=\COMPUTED_PATH\..
	public static final String JAVA_SYS_ARG_APP_HOME_DIR = "app.home";

	// Want the java cli params statically identified here.  
	// The system arg passed from script during the startup: -DappConfigFile=\XYZ\..
	public static final String JAVA_SYS_ARG_CONFIG_FILE = "appConfigFile";

	// Should also init the logging here. Verify if this can lead to other issues?
	private static final Logger LOG = LoggerFactory.getLogger(BootstrapInitializer.class);
	
	// Ensure that JAVA_SYS_ARG_APP_HOME_DIR dir is properly truncated
	static {
		String appHomeDir = System.getProperty(JAVA_SYS_ARG_APP_HOME_DIR);
		
		if (!StringUtils.isNullOrEmpty(appHomeDir))
				System.setProperty(JAVA_SYS_ARG_APP_HOME_DIR, OSUtils.appendFolderSeparator(appHomeDir));
	}
	
	/**
	 * Return a Map of configuration by reading the system properties that matter.
	 * @return
	 */
	public Map<String, String> bootstrap() throws Exception {
		String appHomeDir = System.getProperty(JAVA_SYS_ARG_APP_HOME_DIR);
		String configFile = System.getProperty(JAVA_SYS_ARG_CONFIG_FILE);
		
		// TODO: Add more static checks (we need this strong)
		Assert.notNull(appHomeDir, "Looks like the home directory wasn't supplied");
		Assert.notNull(configFile, "Looks like the config file wasn't supplied");
		
		// Check its a valid file:
		File file = new File(configFile);
		Assert.isTrue(file.isFile(), "Looks like the config file in not valid");
		Map<String, String> configs = this.loadConfigFile(file);
		configs.put(JAVA_SYS_ARG_APP_HOME_DIR, appHomeDir);
		
		LOG.debug("bootstrap::Final computed config map::{}.", configs);
		
		return configs;
	}

	// Note: not all param is a key-value pair. It could be a single value. Interpret it however needed. 
	private Map<String, String> loadConfigFile(File configFile) throws Exception {
		Properties props = new Properties();
		props.load(new FileInputStream(configFile));

		return this.asMapWithVariableSubstitution(props);
	}
	
	/**
	 * Other modules can use this method to get variable substitution if needed.
	 * Te variable substitution allows values of properties to contain certain system properties.
	 * Ex: tempDirectory=${app.home}/temp.
	 * This method will replace the ${app.home} with a valid value if a system property by the name app.home exists. 
	 * @param props
	 * @return
	 */
	public Map<String, String> asMapWithVariableSubstitution(Properties props) {
		Map<String, String> configs = new HashMap<String, String>();
		Set<Map.Entry<Object, Object>> entries = props.entrySet();

		for (Map.Entry<Object, Object> entry : entries) {
			// Do variable substitution - replace any value that has system properties with the correct values.
			String value = (String) entry.getValue();
			value = variableSubstitute(value);

			configs.put((String) entry.getKey(), value);
		}
		return configs;
	}
 
	/**
	 * @param value
	 * @return
	 */
	private String variableSubstitute(String value) {
		// If variables contain ${...} pattern, replace them with the system variable that is present in between (...).
		// If no system variable is found, leave it as it is.
		if (StringUtils.isNullOrEmpty(value))
			return value;
		
		Matcher matcher = variableSubstitutionPattern.matcher(Matcher.quoteReplacement(value));
		String replacement = value;

		LOG.debug("value = {}", value);

		while (matcher.find()) {
			String patternGroup = matcher.group();
			if (!StringUtils.isNullOrEmpty(patternGroup)) {
				// Strip off the ${ and }
				String systemVariable = patternGroup.substring(2, patternGroup.length() - 1);
				String systemVariableValue = System.getProperty(systemVariable);				
				if (!StringUtils.isNullOrEmpty(systemVariableValue)) {
					replacement = replacement.replaceAll(Pattern.quote(patternGroup), Matcher.quoteReplacement(systemVariableValue));
					
					LOG.debug("variableSubstitute::Replaced systemVariable {} with value {}.", patternGroup, systemVariableValue);
				}
			}
		}
		return replacement;
	}


}
