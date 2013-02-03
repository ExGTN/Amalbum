package com.mugenunagi.amalbum.album.interceptor;

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

public class LoginInterceptor implements MethodInterceptor {

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
	    	return "site/login";
	    }
    
		// メソッド名、クラス名を取得
		String methodName = invocation.getMethod().getName();
		String className = invocation.getThis().getClass().getName();

	    // ログイン画面はセッション管理外
	    if(   methodName.equals("login")
	       || methodName.equals("loginCheck")
	    ){
	    	return invocation.proceed();
	    }

	    // ログイン情報がなければログイン画面へ
	    HttpSession session = request.getSession(true);
    	LoginInfoDTO loginInfoDTO = (LoginInfoDTO)session.getAttribute("loginInfoDTO");
    	if(loginInfoDTO==null){
    		// 現在のURLをセッションに入れておく
    	    String url = request.getRequestURL().toString();
    	    session.removeAttribute("requestURL");
    	    session.setAttribute("requestURL", url);

    	    return "redirect:/site/login.do";
        }

    	// OK.
    	session.removeAttribute("requestURL");
    	return invocation.proceed();
    }
}
