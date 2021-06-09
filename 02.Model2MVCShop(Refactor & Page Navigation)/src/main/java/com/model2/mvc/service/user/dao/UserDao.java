package com.model2.mvc.service.user.dao;

import java.sql.Connection; 		// Connection
import java.sql.PreparedStatement;	// PreparedStatement
import java.sql.ResultSet;			// ResultSet

import java.util.ArrayList;			// ArrayList
import java.util.HashMap;			// HashMap
import java.util.Map;				// Map
import java.util.List;				// List

import com.model2.mvc.common.Search;		// Search
import com.model2.mvc.common.util.DBUtil;	// DBUtil
import com.model2.mvc.service.domain.User;	// 기존의 userVO


public class UserDao {
	
	///Field
	
	///Constructor
	public UserDao() {
	}

	///Method
	
	// 1. insertUser
	public void insertUser(User user) throws Exception {
		
		// Connection으로 DBUtil(JDBC_Driver) 연결
		Connection con = DBUtil.getConnection();
		
		// sql에 회원가입 관련 Query문 날리기
		String sql = "INSERT "+ 
					 "INTO USERS "+ 
					 "VALUES (?,?,?,'user',?,?,?,?,SYSDATE)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, user.getUserId());
		pStmt.setString(2, user.getUserName());
		pStmt.setString(3, user.getPassword());
		pStmt.setString(4, user.getSsn());
		pStmt.setString(5, user.getPhone());
		pStmt.setString(6, user.getAddr());
		pStmt.setString(7, user.getEmail());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
	}
	
	// 2. findUser
	public User findUser(String userId) throws Exception {
		
		// Connection으로 DBUtil(JDBC_Driver) 연결
		Connection con = DBUtil.getConnection();
		
		// sql에 userid 찾기 관련 Query문 날리기
		String sql = "SELECT "+
					 "user_id ,  user_name ,  password , role , cell_phone ,  addr ,  email , reg_date " + 
					 "FROM users WHERE user_id = ?";
		
		// PreparedStatement
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, userId);

		ResultSet rs = pStmt.executeQuery();

		User user = null;

		while (rs.next()) {
			user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserName(rs.getString("user_name"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			user.setPhone(rs.getString("cell_phone"));
			user.setAddr(rs.getString("addr"));
			user.setEmail(rs.getString("email"));
			user.setRegDate(rs.getDate("reg_date"));
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		return user;
	}
	
	// 3. 유저List 
	public Map<String , Object> getUserList(Search search) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		// JDBC드라이버로 Connection
		Connection con = DBUtil.getConnection();
		
		// Original Query 구성 _ [0=user_id],[1=user_name]
		String sql = "SELECT user_id ,  user_name , email  FROM  users ";
		
		if (search.getSearchCondition() != null) {
			if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
				sql += " WHERE user_id = '" + search.getSearchKeyword()+"'";
			} else if ( search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE user_name ='" + search.getSearchKeyword()+"'";
			}
		}
		sql += " ORDER BY user_id";
		
		System.out.println("UserDAO::Original SQL :: " + sql); // SQL 실행문 확인
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);	
		System.out.println("UserDAO :: totalCount  :: " + totalCount);	// 전체 게시물 수 확인
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<User> list = new ArrayList<User>();
		
		while(rs.next()){
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserName(rs.getString("user_name"));
			user.setEmail(rs.getString("email"));
			list.add(user);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}

	public void updateUser(User vo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = 	"UPDATE users "+
								"SET user_name = ?, cell_phone = ? , addr = ? , email = ? "+ 
								"WHERE user_id = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, vo.getUserName());
		pStmt.setString(2, vo.getPhone());
		pStmt.setString(3, vo.getAddr());
		pStmt.setString(4, vo.getEmail());
		pStmt.setString(5, vo.getUserId());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만 return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		return sql;
	}
}