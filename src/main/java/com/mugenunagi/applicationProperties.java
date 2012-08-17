package com.mugenunagi;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mugenunagi.amalbum.ApplicationContextWrapper;
import com.mugenunagi.amalbum.Constants;

@Component
public class ApplicationProperties {
	@Autowired
	ApplicationContextWrapper applicationContextWrapper;
	
	private ResourceBundle resourceBundle;

	private ApplicationProperties() {
	}

	/**
	 * 値を取得します
	 * @param key プロパティファイル内のキー
	 * @return プロパティの値
	 */
	public String getString(String key) {
		if( resourceBundle==null ){
			Constants constants = applicationContextWrapper.getConstants();
			String bundleName = constants.getApplicationPropertiesName();
			resourceBundle = ResourceBundle.getBundle(bundleName, new Locale("ja","JP"));
		}
		
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
