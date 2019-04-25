/**
 * 
 */
package com.service;

import com.pojo.Users;

/**
 * @author Jayyan
 *项目名称：
 *类名称：
 *类描述:
 *创建时间:2019年4月15日下午8:08:02
 * @version
 *TODO：
 */
public interface UserService {
	/**
	 * 判断用户名是否存在
	 * @param username
	 * @return ture or false
	 */
	public boolean queryUsernameIsExist(String username);
	
	/**
	 * 查询用户名和密码是否一致，用户是否存在
	 */
	public Users queryUserForLogin(String username,String pwd);
	
	/**
	 * 注册用户，保存输入的用户信息到数据库
	 */
	public Users saveUser(Users user);
	
	/**
	 * 修改用户的记录
	 */
	public Users updataUserInfo(Users user);
}
