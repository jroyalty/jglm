/* Copyright (C) 2013 James L. Royalty
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hackoeur.jglm.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

/**
 * @author James Royalty
 */
public class JglmConfig {
	public static final String CONFIG_FILENAME = "jglm.properties";
	public static final String CONFIG_NAMESPACE = "jglm";
	
	private static final Properties PROPERTIES;
	
	static {
		// Start with an empty set of properties.
		PROPERTIES = new Properties();
		
		String[] files = {
				CONFIG_FILENAME,
				"/" + CONFIG_FILENAME
		};
		
		for (final String fn : files) {
			InputStream in = JglmConfig.class.getResourceAsStream(fn);
			if (in != null) {
				try {
					PROPERTIES.load(in);
				} catch (IOException e) {
					// We need to let the user know their property file is invalid.
					throw new ExceptionInInitializerError("Unable to read configuration file " + fn + ": " + e);
				}
				
				break;
			}
		}
		
		for (final Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
			if (entry.getKey() instanceof String) {
				final String name = (String) entry.getKey();
				
				if (name.startsWith(CONFIG_NAMESPACE)) {
					PROPERTIES.setProperty(name, entry.getValue().toString());
				}
			}
		}
	}
	
	public static final boolean hasProperty(final String key) {
		return PROPERTIES.getProperty(withNamespace(key)) != null;
	}
	
	public static final String getProperty(final String key) {
		return PROPERTIES.getProperty(withNamespace(key));
	}
	
	public static final String getProperty(final String key, final String defaultIfNull) {
		return PROPERTIES.getProperty(withNamespace(key), defaultIfNull);
	}
	
	public static final float getFloatProperty(final String key, final float defaultValue) {
		final String sValue = getProperty(key);
		
		if (sValue == null) {
			return defaultValue;
		} else {
			return Float.parseFloat(sValue);
		}
	}
	
	public static final <T> T getInstanceProperty(final String key, final Class<T> type) {
		final String className = PROPERTIES.getProperty(withNamespace(key));
		
		Class<?> clazz = null;
		
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Unable to find class named " + className);
		}
		
		try {
			Object obj = clazz.newInstance();
			return type.cast(obj);
		} catch (InstantiationException e) {
			throw new IllegalStateException("Unable to instantiate " + clazz, e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Unable to access " + clazz, e);
		}
	}
	
	public static final <T> T getInstancePropertyOrNull(final String key, final Class<T> type) {
		try {
			return getInstanceProperty(key, type);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String dumpProperties() {
		StringWriter sw = new StringWriter();
		try {
			PROPERTIES.store(sw, null);
			return sw.toString();
		} catch (IOException e) {
			return "";
		}
	}
	
	private static final String withNamespace(final String key) {
		if (key.startsWith(CONFIG_NAMESPACE)) {
			return key;
		} else {
			return new StringBuilder()
				.append(CONFIG_NAMESPACE)
				.append(".")
				.append(key)
				.toString();
		}
	}
}
