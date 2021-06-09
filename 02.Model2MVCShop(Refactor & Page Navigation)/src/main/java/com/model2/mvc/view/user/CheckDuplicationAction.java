package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

// 중복ID찾기
//			/checkDuplication.do=com.model2.mvc.view.user.CheckDuplicationAction

public class CheckDuplicationAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// userID 입력 값을 String으로 받아오기
		String userId=request.getParameter("userId");	
		
		// userId를 입력창에 입력하여 결과 값 중복체크진행
		// true - 중복아님(가입가능) / false - 중복(가입불가)
		UserService userService=new UserServiceImpl();
		boolean result=userService.checkDuplication(userId);
		
		request.setAttribute("result",new Boolean(result) );
	
		request.setAttribute("userId", userId);
	System.out.println("이거 확인3 : "+userId);
	System.out.println("---------------------------------------------");
		
		return "forward:/user/checkDuplication.jsp";
	}
}