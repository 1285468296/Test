package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.Information;
import util.userUtil;


/**
 * 登录类，负责登录注册等操作
 * @author Administrator
 */
public class sign {

	static Scanner sc = new Scanner(System.in);
	
	/**
	 * 选择登录/注册的方法
	 */
	public static void choice(){
		System.out.println("请选择您要进行的操作：1.登录\t\t2.注册\t\t3.退出");
		String s1 = sc.next();
		boolean isN1 = userUtil.isNumber(s1);
//		System.out.println(isN1);
		if(!isN1){
			System.out.println("请规范您的输入内容");
			choice();
		} else{
			int choice = Integer.parseInt(s1);
			switch(choice){
			case 1:
				signIn();
				break;
			case 2:
				register();
				break;
			case 3:
				System.out.println("期待您的下次光临,啾咪！");
				System.exit(0);
				break;
			default:
				System.out.println("请按规定重新输入");
				choice();
				break;
			}
		}	
	//choice()方法结束位置
	}
	
	/**
	 * 登陆的方法
	 */
	private static void signIn() {
		System.out.println("请输入您的用户名：");
		String name = sc.next();
		boolean nameIsOk = userUtil.validateName(name);
//		System.out.println(nameIsOk);
		if(nameIsOk){
			//如果用户名正确，接下来进行密码验证
			userUtil.validatePassWord(name);
		} else{
			System.out.println("该用户名不存在，请重新输入或注册");
			choice();
		}
		
	}
	
	/**
	 * 注册的方法
	 */
	private static void register() {
		System.out.println("请设置您的用户名：");
		String name = sc.next();
		boolean nameIsOk = userUtil.validateName(name);
//		System.out.println(nameIsOk);
		if(nameIsOk){
			System.out.println("该用户名已存在，请直接登录或重新设置");
			choice();
		} else{
			System.out.println("请设置您的登录密码：");
			String passWord = sc.next();
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(date);
			
			Information information = new Information(name, passWord, time);
			
			userUtil.addUser(information);
		}
		
		
	// register()方法结束位置	
	}
	
//类结束位置	
}
