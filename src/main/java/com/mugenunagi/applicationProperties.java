package com.mugenunagi;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class applicationProperties {
	private static final String BUNDLE_NAME = "application"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, new Locale("ja","JP"));

	private applicationProperties() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
