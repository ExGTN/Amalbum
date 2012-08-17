package com.mugenunagi.amalbum;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.WebContentGenerator;

/**
 * ApplicationContextからのBean取得をラップするクラス
 * @author gtn
 *
 */
@Component
public class ApplicationContextWrapper extends WebContentGenerator{
	/**
	 * Constantsのオブジェクトを取得する
	 * @return
	 */
	public Constants getConstants() {
		ApplicationContext context = getApplicationContext();
		Constants constants = (Constants)context.getBean("amalbumConstants");
		return constants;
	}
}
