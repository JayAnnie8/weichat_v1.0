/**
 * 用户实现类
 */
package com.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.UsersMapper;
import com.pojo.Users;
import com.service.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * @author Jayyan
 *项目名称：
 *类名称：
 *类描述:
 *创建时间:2019年4月15日下午8:15:09
 * @version
 *TODO：
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UsersMapper userMapper;
	
	@Autowired
	private Sid sid;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameIsExist(String username) {
		Users user=new Users();
		user.setUsername(username);
		Users result=userMapper.selectOne(user);
		return result!=null? true :false;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	@Override
	public Users queryUserForLogin(String username, String pwd) {
		Example userExample=new Example(Users.class);
		Criteria criteria=userExample.createCriteria();
		
		//通过逆向工具查询数据库是否有当前用户的记录
		criteria.andEqualTo("username", username);	//判断是否存在一个username在数据库上
		criteria.andEqualTo("password", pwd);		//判断是否存在一个password在数据库上
		//通过userMapper去执行查询 example
		
		Users result=userMapper.selectOneByExample(userExample);
		return result;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public Users saveUser(Users user) {
		// TODO 为每一个用户生成一个唯一的二维码
		user.setQrcode("");
		
		//生成唯一id 设置用户的id
		String userid=sid.nextShort();
		user.setId(userid);
		
		//user的其他属性在UserController的注册局部已经赋值后才传入 user
		//该接口实现用于设置二维码和id
		userMapper.insert(user);
		
		return user;
	}

	/* （非 Javadoc）
	 * @see com.service.UserService#updataUserInfo(com.pojo.Users)
	 * 该方法实现updataUserInfo接口
	 * 实现更新用户记录
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public Users updataUserInfo(Users user) {
		userMapper.updateByPrimaryKeySelective(user);
		return queryUserById(user.getId());
	}
	@Transactional(propagation=Propagation.SUPPORTS)
	private Users queryUserById(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

}
