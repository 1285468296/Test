package enter;

import controller.init;

public class libInit {

	public static void main(String[] args) {
		
		System.out.println("初始化结束");
	}
	
	/**
	 * 静态代码块用于初始化管理者信息和图书信息
	 */
	static{
		init.adminInit();
		init.bookInit();
		
//		init.userSee();
//		init.bookSee();
	}
}
