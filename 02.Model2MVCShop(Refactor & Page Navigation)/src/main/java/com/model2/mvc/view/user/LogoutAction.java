package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;

// 로그아웃
public class LogoutAction extends Action {

	@Override
	public String execute( HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		// 로그아웃 : 세션 무효화 및 연결 해제
		session.invalidate();
		
		// 초기화면으로 이동시킴
		return "redirect:/index.jsp";
	}
}