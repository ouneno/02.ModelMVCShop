package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;	// implement UserService�� ������
import com.model2.mvc.service.user.impl.UserServiceImpl;

// ȸ������ 
// 		/addUser.do=com.model2.mvc.view.user.AddUserAction)
public class AddUserAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user=new User();
		user.setUserId(request.getParameter("userId"));			// ID
		user.setPassword(request.getParameter("password"));		// PW
		user.setUserName(request.getParameter("userName"));		// ȸ����
		user.setSsn(request.getParameter("ssn"));				// �ĺ���
		user.setAddr(request.getParameter("addr"));				// �ּ�
		user.setPhone(request.getParameter("phone"));			// ����ó
		user.setEmail(request.getParameter("email"));			// �̸���
		
		// ȸ������ �Ϸ� - UserVO���� �����´�.
		System.out.println("AddUserAction ::"+user);
		
		UserService userService=new UserServiceImpl();
		userService.addUser(user);
		
		// ����Ϸ�� loginView.jsp �� ����
		return "redirect:/user/loginView.jsp";
	}
}