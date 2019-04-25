/**
 * 
 */
package com.imooc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jayyan
 *项目名称：
 *类名称：
 *类描述:
 *创建时间:2019年4月15日下午4:15:46
 * @version
 *TODO：
 */
@RestController
public class HelloController {
	@GetMapping("/hello")
	public String hello() {
			return "hello";
	}

}
