package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;

// �α׾ƿ�
public class LogoutAction extends Action {

	@Override
	public String execute( HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		// �α׾ƿ� : ���� ��ȿȭ �� ���� ����
		session.invalidate();
		
		// �ʱ�ȭ������ �̵���Ŵ
		return "redirect:/index.jsp";
	}
}