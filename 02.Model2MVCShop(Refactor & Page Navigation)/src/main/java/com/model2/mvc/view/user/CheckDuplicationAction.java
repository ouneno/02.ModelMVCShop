package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

// �ߺ�IDã��
//			/checkDuplication.do=com.model2.mvc.view.user.CheckDuplicationAction

public class CheckDuplicationAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// userID �Է� ���� String���� �޾ƿ���
		String userId=request.getParameter("userId");	
		
		// userId�� �Է�â�� �Է��Ͽ� ��� �� �ߺ�üũ����
		// true - �ߺ��ƴ�(���԰���) / false - �ߺ�(���ԺҰ�)
		UserService userService=new UserServiceImpl();
		boolean result=userService.checkDuplication(userId);
		
		request.setAttribute("result",new Boolean(result) );
	
		request.setAttribute("userId", userId);
	System.out.println("�̰� Ȯ��3 : "+userId);
	System.out.println("---------------------------------------------");
		
		return "forward:/user/checkDuplication.jsp";
	}
}