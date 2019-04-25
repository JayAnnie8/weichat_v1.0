package com.pojo.bo;

/**
 * 由客户端发来的信息
 */
import javax.persistence.*;

public class UsersBO {
	private String userid;
	private String facebace64;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFacebace64() {
		return facebace64;
	}
	public void setFacebace64(String facebace64) {
		this.facebace64 = facebace64;
	}
    
}