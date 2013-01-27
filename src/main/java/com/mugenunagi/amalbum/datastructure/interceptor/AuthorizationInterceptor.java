package com.mugenunagi.amalbum.datastructure.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.ui.ModelMap;

import com.mugenunagi.amalbum.album.dto.LoginInfoDTO;
import com.mugenunagi.amalbum.exception.InvalidParameterException;

public class AuthorizationInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		// HttpServletRequestを探して取得する
		HttpServletRequest request = null;
	    Object[] args = invocation.getArguments();
	    for( Object arg : args ){
	    	if( arg instanceof HttpServletRequest ){
	    		request = (HttpServletRequest)arg;
	    	}
	    }
	    if( request==null ){
	    	// HttpServletRequestがなければ処理なし
	    	return null;
	    }

	    // セッション自体がなければ処理なし
	    HttpSession session = request.getSession(false);
	    if( session==null ){
	    	return null;
	    }
	    
	    // セッションがあってもログイン情報がなければ処理なし
    	LoginInfoDTO loginInfoDTO = (LoginInfoDTO)session.getAttribute("loginInfoDTO");
    	if(loginInfoDTO==null){
        	return null;
        }

    	// OK.
    	return invocation.proceed();
    }
}
