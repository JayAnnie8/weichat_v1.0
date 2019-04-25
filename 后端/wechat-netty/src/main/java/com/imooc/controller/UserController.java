/**
 * 
 */
package com.imooc.controller;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pojo.Users;
import com.pojo.bo.UsersBO;
import com.pojo.vo.UsersVO;
import com.service.UserService;
import com.imooc.FastDFSClient;
import com.util.FileUtils;
import com.util.IMoocJSONResult;
import com.util.MD5Utils;

/**
 * @author Jayyan
 *项目名称：
 *类名称：
 *类描述:用户注册登录用的类
 *创建时间:2019年4月15日下午4:15:46
 * @version
 *TODO：
 */
@RestController
@RequestMapping("u")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private FastDFSClient fastDFSClient;
	
	@PostMapping("/registOrLogin")
	public IMoocJSONResult registOrLogin(@RequestBody Users user) throws Exception{
		//1.判断用户名与密码不能为空
		if(StringUtils.isBlank(user.getUsername())
				||StringUtils.isBlank(user.getPassword())) {
			return IMoocJSONResult.errorMsg("用户名或密码不能为空....");
		}
		//2.判断用户名是否存在，如果存在就登录，如果不存在就注册
		boolean usernameIsExist =userService.queryUsernameIsExist(user.getUsername());
		Users userResult =null;
		if(usernameIsExist) {
			//2.1登录
			userResult=userService.queryUserForLogin(user.getUsername(),
								MD5Utils.getMD5Str(user.getPassword()));
			if(userResult == null) {
				return IMoocJSONResult.errorMsg("用户名或密码不正确");
			}
			
			
		}else {
			//2.2注册
			user.setNickname(user.getUsername());
			user.setFaceImage("");
			user.setFaceImageBig("");
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			userResult=userService.saveUser(user);
			//这样生成的是包含id和二维码的userResult
		}
		
		//将某两个相似的对象相互转化，参数1是要转化的对象，参数2是要生成的目标对象
		//即把A——>B
		UsersVO userVO =new UsersVO();
		BeanUtils.copyProperties(userResult, userVO);
		return IMoocJSONResult.ok(userVO);
	}

	@PostMapping("/uploadFaceBase64")
	public IMoocJSONResult uploadFaceBace64(@RequestBody UsersBO usersBO) throws Exception{
		//获取到前端传过来的bace64字符串，转换成文件对象上传
		System.out.println(usersBO.toString());
		String bace64=usersBO.getFacebace64();
		System.out.println(bace64);
		System.out.println(usersBO.getUserid());
		String templocalurl="C:\\"+usersBO.getUserid()+"userface64.png"; 
		//临时文件地址用于保存FileUtils.java base64ToFile方法产生的file文件到什么位置
		 FileUtils.base64ToFile(templocalurl, bace64);			
		 //将bace64转化为File文件
		 MultipartFile uploadMultipart=FileUtils.fileToMultipart(templocalurl);
		 //将file文件转化为MultipartFile
		 String imageBig_url=fastDFSClient.uploadBase64(uploadMultipart);
		 //uploadBase64需要MultipartFile所以以上转化才实现上传 返回一个图片的保存地址
		 System.out.println(imageBig_url);
		 
		 //要对地址进行处理因为我们上传了两个图片一个大图一个80X80的缩略图
		 String[] a=imageBig_url.split("\\.");
		 String thump = "_80x80.";
		 String image_url=a[0]+thump+a[1];
		 
		 //建立一个User对象把User对象完整后抛出
		 Users user=new Users();
		 user.setId(usersBO.getUserid());
		 user.setFaceImage(image_url);
		 user.setFaceImageBig(imageBig_url);
		 //把user对象和数据库中的数据核对更新后再抛出
		 userService.updataUserInfo(user);
		return IMoocJSONResult.ok();
	}
}



