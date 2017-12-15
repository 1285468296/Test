package model;

import java.io.Serializable;

public class Information implements Serializable{
	/**
	 * 账号,String
	 */
	private String userName;
	/**
	 * 密码,String
	 */
	private String passWord;
	/**
	 * 登录时间,String
	 */
	private String time;
	
	
	/**
	 * 无参构造方法
	 */
	public Information() {
		
	}
	
	/**
	 * 有参构造方法
	 * @param userName
	 * @param passWord
	 * @param time
	 */
	public Information(String userName, String passWord, String time) {
		this.userName = userName;
		this.passWord = passWord;
		this.time = time;
	}
	
	/**
	 * 重写toString方法
	 */
	@Override
	public String toString() {
		return "用户名：" + userName + ", 密码：" + passWord + ", 登录时间：" + time;
	}
	
	/**
	 * 封装方法
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

//类结束位置
}
