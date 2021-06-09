package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;	// implement UserService로 보내기
import com.model2.mvc.service.user.impl.UserServiceImpl;

// 회원가입 
// 		/addUser.do=com.model2.mvc.view.user.AddUserAction)
public class AddUserAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user=new User();
		user.setUserId(request.getParameter("userId"));			// ID
		user.setPassword(request.getParameter("password"));		// PW
		user.setUserName(request.getParameter("userName"));		// 회원명
		user.setSsn(request.getParameter("ssn"));				// 식별성
		user.setAddr(request.getParameter("addr"));				// 주소
		user.setPhone(request.getParameter("phone"));			// 연락처
		user.setEmail(request.getParameter("email"));			// 이메일
		
		// 회원가입 완료 - UserVO에서 가져온다.
		System.out.println("AddUserAction ::"+user);
		
		UserService userService=new UserServiceImpl();
		userService.addUser(user);
		
		// 정상완료시 loginView.jsp 로 보냄
		return "redirect:/user/loginView.jsp";
	}
}