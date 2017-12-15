package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.Scanner;

import controller.sign;
import model.Book;
import model.Information;
import model.pathConstant;

public class bookUtil {

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
	 * 选择是否继续进行操作
	 * @return
	 */
	public static String yOrN(){
		String choice = "";
		boolean flag = false;
		
		do{
			System.out.println("是否继续该种操作：\t\t\ty/n");
			String s = sc.next();
			boolean ok = isOk(s);
			if(ok){
				choice = s;
				flag = false;
				break;
			} else{
				System.out.println("请按规定重新输入");
				flag = true;
				continue;
			}
			
		} while(flag);
		
		return choice;
	}
	
	/**
	 * 判断从键盘输入的内容是y/n
	 * @param s
	 * @return
	 */
	public static boolean isOk(String s){
		boolean flag = false;
		if(s.length() == 1){
			if("y".equals(s) || "n".equals(s)){
				flag = true;
			} else{
				flag = false;
			}
		} else{
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 将出入的数字保留一位小数，返回去
	 * @param s
	 * @return
	 */
	public static String P(String s){
		DecimalFormat df = new DecimalFormat("#0.0");
		double price = Double.parseDouble(s);
		String s1 = df.format(price);
		
		return s1;
	}
	
	/**
	 * 按照给定的路径，将文件中的图书数据取出来，放在对象数组中，并返回出来
	 * @param path
	 * @return
	 */
	public static Book[] bookInit(String path){
		File newFile = new File(path);
		ObjectInputStream newOis = null;
		ObjectOutputStream newOos = null;
		Book[] newbooks = null;
		
		/**
		 * 当文件为空时，定义一个长度为20的空数组，装入文件中
		 */
		try {
			if(newFile.length() == 0){
				try {
					Book[] books = new Book[20];
					newOos = new ObjectOutputStream(new FileOutputStream(newFile));
					newOos.writeObject(books);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			newOis = new ObjectInputStream(new FileInputStream(newFile));
			newbooks = (Book[])newOis.readObject();
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
		return	newbooks;
	// bookInit()方法结束位置
	}
	
	/**
	 * 书名验证，验证传入的书名是否已经存在
	 * @param name
	 * @return
	 */
	public static boolean validateBookName(String name){
		boolean flag = false;
		Book[] books = bookInit(pathConstant.bookPath);
		
		for(int i = 0; i < books.length; i++){
			if(books[i] != null){
				if(books[i].getBookName().equals(name)){
//					String na1 = adminIfm[i].getUserName();
//					System.out.println(na1);
					flag = true;
					break;
				} else{
					flag = false;
				}
			} else{
				break;
			}
		}
		
		return flag;
	//validateBookName()方法结束位置
	}
	
	/**
	 * 将上架图书写入文件的方法
	 * @param book
	 */
	public static void writeAddBook(Book book){
		String bookPath = pathConstant.bookPath;
		File bookFile = new File(bookPath);
		ObjectOutputStream oos = null;
		
		Book[] books = bookInit(pathConstant.bookPath);
		//验证已有的数组最后一位为空，说明该数组不满，直接往里面添加；
		if(books[books.length - 1] == null){
			for(int i = 0; i < books.length; i++){
				if(books[i] != null){
					continue;
				} else{
					books[i] = book;
					break;
				}
			}
		} else{
		//如果数组最后一位不为空，说明数组已满，则将数组长度加一，再往里面添加；
			books = increaseBookOne(books);
			books[books.length - 1] = book;	
		}
		
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
		
		System.out.println("图书上架成功");
		
	// writeAddBook()方法结束位置
	}
	
	/**
	 * 将下架后的图书信息写入文件的方法
	 * @param book
	 */
	public static void writeDelBook(Book[] books, int i, int length){
		String bookPath = pathConstant.bookPath;
		File bookFile = new File(bookPath);
		ObjectOutputStream oos = null;
		Book[] newbooks = new Book[length - 1];
		
		if(i == books.length - 1){
			for(int m = 0; m < newbooks.length; m++){
				newbooks[m] = books[m];
			}
		} else{
			for(int j = i; j < books.length - 1; j++){
				if(books[j+1] != null) {
					books[j] = books[j+1];
				}
			}
			for(int m = 0; m < newbooks.length; m++){
				newbooks[m] = books[m];
			}
		}
		
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
			oos.writeObject(newbooks);
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
		
		System.out.println("图书下架成功");
		
	// writeDelBook()方法结束位置 
	}
	
	/**
	 * 将传入的数字更新至图书信息文件
	 * @param books
	 */
	public static void writeBookArray(Book[] books){
		String bookPath = pathConstant.bookPath;
		File bookFile = new File(bookPath);
		ObjectOutputStream oos = null;
		
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
		
	// writeBookArray()方法结束位置	
	}
	
	/**
	 * 图书信息数组长度增加的方法，传入一个数组，将数组长度加一并返回（老短新长）
	 * @param books
	 * @return
	 */
	public static Book[] increaseBookOne(Book[] books){
		int oldLen = books.length;
		int newLen = oldLen + 1;
		
		Book[] newBooks = new Book[newLen];
		for(int i = 0; i < oldLen; i++){
			newBooks[i] = books[i];
		}
		return newBooks;
	// increaseBookOne()方法结束位置
	}
	
	/**
	 * 图书信息数组长度减少的方法，传入一个数组，将数组长度减一并返回（老长新短）
	 * @param books
	 * @return
	 */
	public static Book[] reduceBookOne(Book[] books){
		int oldLen = books.length;
		int newLen = oldLen - 1;
		
		Book[] newBooks = new Book[newLen];
		for(int i = 0; i < newLen; i++){
			newBooks[i] = books[i];
		}
		return newBooks;
	// reduceBookOne()方法结束位置
	}
	
	/**
	 * 获得实付款的方法
	 * @return
	 */
	public static double payMoney(double totalMoney){
		boolean flag = false;
		double payMoney = 0;
		do{
			System.out.println("总计金额：" + totalMoney + "元");
			System.out.println("请付款：");
			String s = sc.next();
			boolean isN = isNumber(s);
			if(!isN){
				System.out.println("请规范您的输入内容");
				flag = true;
				continue;
			} else{
				s = P(s);
				payMoney = Double.parseDouble(s);
				if(payMoney < totalMoney){
					System.out.println("请规范您的输入内容");
					flag = true;
					continue;
				} else{
					break;
				}
			}
			
		} while(flag);
		
		return payMoney;
	// payMoney()方法结束位置
	}
	
	/**
	 * 将收据信息写入系统文件的方法
	 * @param totalMoney
	 * @param payMoney
	 * @param rebackMoney
	 * @param receipt
	 * @param number
	 */
	public static void writeReceipt(double totalMoney, double payMoney, double rebackMoney, String receipt, int number){
		String receiptPath = pathConstant.receiptPath;
		File receiptFile = new File(receiptPath);
		/**
		 * 对文件进行验证，如果不存在，则创建
		 */
		if(!receiptFile.exists()) {
			try {
				receiptFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			Writer w = new FileWriter(receiptFile, true);
			BufferedWriter bw  = new BufferedWriter(w);
			bw.write("收据信息：");
			bw.newLine();
			bw.write("***************藏书阁***************");
			bw.newLine();
			bw.write("购物列表：");
			bw.newLine();
			bw.write("名称\t\t\t单价\t\t数量\t\t小计");
			bw.newLine();
			bw.write(receipt);
			bw.newLine();
			bw.write("-----------------------------------");
			bw.newLine();
			if(number == 0){
				bw.write("合计：\t\t" + (int)(totalMoney / 0.8));
				bw.newLine();
				bw.write("折扣：\t\t0.8");	
			} else if(number == 1){
				bw.write("合计：\t\t" + (int)(totalMoney / 0.5));
				bw.newLine();
				bw.write("折扣：\t\t0.5");
			}
			bw.newLine();
			bw.write("应付：\t\t" + (int)totalMoney);
			bw.newLine();
			bw.write("实付：\t\t" + (int)payMoney);
			bw.newLine();
			bw.write("找零：\t\t" + (int)rebackMoney);
			bw.newLine();
			bw.newLine();
			bw.write("**********************************");
			bw.newLine();
			bw.newLine();
			bw.newLine();
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	// writeReceipt()方法结束位置
	}
	
// 类方法结束位置
}
