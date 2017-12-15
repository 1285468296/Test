package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import controller.bookOperation;
import controller.sign;
import model.Information;
import model.pathConstant;

/**
 * 判断类，进行一些判断
 * @author Administrator
 *
 */
public class userUtil {

	static Scanner sc = new Scanner(System.in);
	/**
	 * 判断传入的内容是否为纯数字
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s){
		boolean isN = true;
		
		for(int i = 0; i < s.length(); i++){
			char a = s.charAt(i);
			if(Character.isLetter(a)){
				isN = false;
				break;
			} 
		}
		return isN;	
	}
	
	/**
	 * 按照给定的路径，将文件中的用户数据取出来，放在对象数组中，并返回出来
	 * @param path
	 * @return
	 */
	public static Information[] arrayInit(String path){
		File newFile = new File(path);
		ObjectInputStream newOis = null;
		ObjectOutputStream newOos = null;
		Information[] newIfm = null;
		
		/**
		 * 当文件为空时，定义一个长度为5的空数组，装入文件中
		 */
		try {
			if(newFile.length() == 0){
				try {
					Information[] ifm = new Information[5];
					newOos = new ObjectOutputStream(new FileOutputStream(newFile));
					newOos.writeObject(ifm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			newOis = new ObjectInputStream(new FileInputStream(newFile));
			newIfm = (Information[])newOis.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(newOis != null) {
				try {
					newOis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return	newIfm;
	// arrayInit()方法结束位置
	}
	
	
	/**
	 * 用户名验证，进行登录/注册时输入的用户名是否存在的验证
	 * @param name
	 * @param number
	 * @return
	 */
	public static boolean validateName(String name){
		boolean flag = false;
		int admin = -1;
		int user = -1;
		Information[] adminIfm = arrayInit(pathConstant.adminPath);
		Information[] userIfm = arrayInit(pathConstant.userPath);
		
		for(int i = 0; i < adminIfm.length; i++){
			if(adminIfm[i] != null){
				if(adminIfm[i].getUserName().equals(name)){
//					String na1 = adminIfm[i].getUserName();
//					System.out.println(na1);
					admin = 1;
					break;
				} else{
					admin = 0;
				}
			} else{
				break;
			}
		}
		
		for(int j = 0; j < userIfm.length; j++){
			if(userIfm[j] != null){
				if(userIfm[j].getUserName().equals(name)){
//					String na2 = userIfm[j].getUserName();
//					System.out.println(na2);
					user = 1;
					break;
				} else{
					user = 0;
				}
			} else{
				break;
			}
		}
		
		if(admin == 1 || user == 1){
			flag = true;
		} else{
			flag = false;
		}
		
		return flag;
	//validateNam()方法结束位置
	}
	
	/**
	 * 密码验证，进行登录/注册时输入的密码是否正确的验证
	 * @param passWord
	 */
	public static void validatePassWord(String name){
		//生成一个计数器，用来判断是否是否循环到结尾
		int admin = -1;
		int user = -1;
		System.out.println("请输入登录密码：");
		String passWord = sc.next();
		//生成两个数组，分别来自管理者信息库和用户信息库
		Information[] adminIfm = arrayInit(pathConstant.adminPath);
		Information[] userIfm = arrayInit(pathConstant.userPath);
		
		for(int i = 0; i < adminIfm.length; i++){
			if(adminIfm[i] != null){
				if(adminIfm[i].getUserName().equals(name) && adminIfm[i].getPassWord().equals(passWord)){
//					String na1 = adminIfm[i].getUserName();
//					String pwd1 = adminIfm[i].getPassWord();
//					System.out.println(na1);
//					System.out.println(pwd1);
					admin = 1;
					System.out.println("欢迎管理员" + name + "登录");
					System.out.println("您的注册时间为" + adminIfm[i].getTime());
					bookOperation.adminShowBooks();
					break;
				} else{
					admin = 0;
				}		
			} else{
				break;
			}
		}
		
		for(int j = 0; j < userIfm.length; j++){
			if(userIfm[j] != null){
				if(userIfm[j].getUserName().equals(name) && userIfm[j].getPassWord().equals(passWord)){
//					String na2 = userIfm[j].getUserName();
//					String pwd2 = userIfm[j].getPassWord();
//					System.out.println(na2);
//					System.out.println(pwd2);
					user = 1;
					System.out.println("欢迎用户" + name + "登录");
					System.out.println("您的注册时间为" + userIfm[j].getTime());
					bookOperation.userShowBooks();
					break;
				} else{
					user = 0;
				}
			} else{
				break;
			}
		}
		
		if(admin == 0 && user ==0){
			System.out.println("输入密码不正确，请重新输入");
			validatePassWord(name);
		}
		
	// validatePassWord()方法结束位置
	}

	/**
	 * 注册添加类，将新注册的用户信息存入用户信息库
	 * @param information
	 */
	public static void addUser(Information information){
		String userPath = pathConstant.userPath;
		File userFile = new File(userPath);
		ObjectOutputStream oos = null;
		
		Information[] userIfm = arrayInit(pathConstant.userPath);
		//验证已有的数组最后一位为空，说明该数组不满，直接往里面添加；
		if(userIfm[userIfm.length - 1] == null){
			for(int i = 0; i < userIfm.length; i++){
				if(userIfm[i] != null){
					continue;
				} else{
					userIfm[i] = information;
					break;
				}
			}
		} else{
		//如果数组最后一位不为空，说明数组已满，则将数组长度加一，再往里面添加；
			userIfm = increaseOne(userIfm);
			userIfm[userIfm.length - 1] = information;	
		}
		
		/**
		 * 对文件进行验证，如果不存在，则创建
		 */
		if(!userFile.exists()) {
			try {
				userFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 将对象内容写入文件中
		 */
		try {
			oos = new ObjectOutputStream(new FileOutputStream(userFile));
			oos.writeObject(userIfm);
//			System.out.println("userInit>>>OVER");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("注册成功，请登录");
		sign.choice();
		
	// addUser()方法结束位置
	}
	
	/**
	 * 用户信息数组长度增加的方法，传入一个数组，将数组长度加一并返回
	 * @param userIfm
	 * @return
	 */
	public static Information[] increaseOne(Information[] userIfm){
		int oldLen = userIfm.length;
		int newLen = oldLen + 1;
		
		Information[] newUserIfm = new Information[newLen];
		for(int i = 0; i < oldLen; i++){
			newUserIfm[i] = userIfm[i];
		}
		return newUserIfm;
	// increaseOne()方法结束位置
	}
	
	
//类方法结束位置
}
