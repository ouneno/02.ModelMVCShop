package com.model2.mvc.service.user.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.dao.UserDao;
import com.model2.mvc.service.domain.User;


public class UserServiceImpl implements UserService{
	
	///Field
	private UserDao userDao;
	
	///Constructor
	public UserServiceImpl() {
		userDao=new UserDao();
	}

	///Method
	
		// 1. ȸ������
		public void addUser(User user) throws Exception {
			userDao.insertUser(user);
		}

		// 2. �α��ν���
		public User loginUser(User user) throws Exception {
				User dbUser=userDao.findUser(user.getUserId());
	
				if(! dbUser.getPassword().equals(user.getPassword())){
					throw new Exception("�α��ο� �����߽��ϴ�.");
				}
				
				return dbUser;
		}
		
		// 3. ȸ��ã��
		public User getUser(String userId) throws Exception {
			return userDao.findUser(userId);
		}
		
		// 4. ȸ�����
		public Map<String,Object> getUserList(Search search) throws Exception {
			return userDao.getUserList(search);
		}

		// 5. ȸ������
		public void updateUser(User user) throws Exception {
			userDao.updateUser(user);
		}

		// 6. �ߺ����̵� Ȯ��
		public boolean checkDuplication(String userId) throws Exception {
			boolean result=true;
			User user=userDao.findUser(userId);
			if(user != null) {
				result=false;
			}
			return result;
	}
}