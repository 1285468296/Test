package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Book;
import model.Information;
import model.pathConstant;

/**
 * 此类为初始化信息类，包含了管理者信息初始化和图书信息初始化
 * @author Administrator
 *
 */
public class init {
	
	/**
	 * 管理者信息初始化方法
	 */
	public static void adminInit(){
		Information[] adminIfm = new Information[2];
		String adminPath = pathConstant.adminPath;
		File adminFile = new File(adminPath);
		ObjectOutputStream oos = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		
		adminIfm[0] = new Information("admin", "123", time);
		adminIfm[1] = new Information("lh", "123", time);
		
		/**
		 * 对文件进行验证，如果不存在，则创建
		 */
		if(!adminFile.exists()) {
			try {
				adminFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 将对象内容写入文件中
		 */
		try {
			oos = new ObjectOutputStream(new FileOutputStream(adminFile));
			oos.writeObject(adminIfm);
			System.out.println("adminInit>>>OVER");
		} catch (Exception e) {
			e.printStackTrace();
		}
	//adminInit()方法结束位置	
	}
	
	/**
	 * 用户信息初始化方法
	 */
	public static void userInit(){
		Information[] userIfm = new Information[2];
		String userPath = pathConstant.userPath;
		File userFile = new File(userPath);
		ObjectOutputStream oos = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		
		userIfm[0] = new Information("aa", "123", time);
		userIfm[1] = new Information("bb", "123", time);
		
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
			System.out.println("userInit>>>OVER");
		} catch (Exception e) {
			e.printStackTrace();
		}
	//adminInit()方法结束位置	
	}
	
	
	/**
	 * 图书信息初始化方法
	 */
	public static void bookInit(){
		Book[] books = new Book[15];
		String bookPath = pathConstant.bookPath;
		File bookFile = new File(bookPath);
		ObjectOutputStream oos = null;
		
		Book b1 = new Book("康熙大帝", "二月河", 10.0, 10);
		Book b2 = new Book("雍正皇帝", "二月河", 20.0, 10);
		Book b3 = new Book("乾隆皇帝", "二月河", 30.0, 20);
		Book b4 = new Book("未来简史", "雅克·阿塔利", 78.9, 30);
		Book b5 = new Book("人间失格", "太宰治", 12.5, 50);
		Book b6 = new Book("猎人笔记", "屠格涅夫", 18.0, 40);
		Book b7 = new Book("雪落香杉树", "戴维·伽特森", 26.0, 50);
		Book b8 = new Book("追风筝的人", "卡勒德·胡赛尼", 14.5, 65);
		Book b9 = new Book("天才在左疯子在右", "高铭", 19.9, 10);
		Book b10 = new Book("你是人间四月天", "林徽因", 14.0, 25);
		Book b11 = new Book("镜花缘", "李汝珍", 19.9, 17);
		Book b12 = new Book("偷影子的人", "马克·李维", 13.6, 9);
		Book b13 = new Book("岛上书店", "加·泽文", 24.2, 25);
		Book b14 = new Book("我喜欢生命本来的样子", "周国平", 22.5, 21);
		Book b15 = new Book("双城记", "查尔斯", 12.5, 20);
		
		books[0] = b1;
		books[1] = b2;
		books[2] = b3;
		books[3] = b4;
		books[4] = b5;
		books[5] = b6;
		books[6] = b7;
		books[7] = b8;
		books[8] = b9;
		books[9] = b10;
		books[10] = b11;
		books[11] = b12;
		books[12] = b13;
		books[13] = b14;
		books[14] = b15;
		
		/**
		 * 对文件进行验证，如果不存在，则创建
		 */
		if(!bookFile.exists()) {
			try {
				bookFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 将对象内容写入文件中
		 */
		try {
			oos = new ObjectOutputStream(new FileOutputStream(bookFile));
			oos.writeObject(books);
			System.out.println("bookInit>>>OVER");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	//bookInit()方法结束位置	
	}
	
	
	/**
	 * 管理者储存信息验证方法
	 */
	public static void adminSee(){
		String adminPath = pathConstant.adminPath;
		File adminFile = new File(adminPath);
		Information[] adminIfm = new Information[2];
		ObjectInputStream ois = null;
		
		try {
				ois = new ObjectInputStream(new FileInputStream(adminFile));
				adminIfm = (Information[])ois.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for(int i = 0; i < adminIfm.length; i++){
			System.out.println(adminIfm[i]);
		}
	//adminSee方法结束位置
	}
	
	/**
	 * 图书储存信息验证方法
	 */
	public static void bookSee(){
		String bookPath = pathConstant.bookPath;
		File bookFile = new File(bookPath);
		Book[] bookIfm = new Book[15];
		ObjectInputStream ois = null;
		
		try {
				ois = new ObjectInputStream(new FileInputStream(bookFile));
				bookIfm = (Book[])ois.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for(int i = 0; i < bookIfm.length; i++){
			System.out.println(bookIfm[i]);
		}
	//bookSee()方法结束位置
	}
	
	/**
	 * 用户储存信息验证方法
	 */
	public static void userSee(){
		String userPath = pathConstant.userPath;
		File userFile = new File(userPath);
		Information[] userIfm = new Information[2];
		ObjectInputStream ois = null;
		
		try {
				ois = new ObjectInputStream(new FileInputStream(userFile));
				userIfm = (Information[])ois.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for(int i = 0; i < userIfm.length; i++){
			System.out.println(userIfm[i]);
		}
	//userSee方法结束位置
	}
	
	
	
//Init类结束位置
}
