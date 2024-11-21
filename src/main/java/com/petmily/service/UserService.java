package com.petmily.service;

import com.petmily.domain.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
	
	
	
	List<UserDTO> UserList(String id);

	// ** selectList
	List<UserDTO> selectList();

	// ** selectOne
	UserDTO selectOne(UserDTO dto);
	
	
	// ** insert
	int insert(UserDTO dto);

	// ** update
	int update(UserDTO dto);
	
	int pwupdate(UserDTO dto);

	// ** delete
	int delete(UserDTO dto);
	//아이디 찾기
	 String foundUserId(String username, String useremail);
	 //비밀번호찾기
	String foundUserPw(String userid, String useremail);
	//임시비밀번호
	boolean updateUserPassword(String userid, String temporaryPassword);

	boolean randompw(String userId, String temporaryPassword);

	
	
}