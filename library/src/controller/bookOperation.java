package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Scanner;
import model.Book;
import model.pathConstant;
import util.bookUtil;
import util.userUtil;

/**
 * 图书的操作类
 * @author Administrator
 */
public class bookOperation {

	static Scanner sc = new Scanner(System.in);
	
	/**
	 * 展示图书列表的方法
	 */
	public static void showBooks(){
		Book[] books = bookUtil.bookInit(pathConstant.bookPath);
		System.out.println();
		System.out.println("*******************本书阁的藏书信息如下*******************");
		System.out.println();
		System.out.println(" 书     名\t\t作    者\t\t价   格\t\t库 存");
		for(int i = 0; i < books.length; i++){
			System.out.println(books[i]);
		}
		System.out.println();
	}
	
	
	/**
	 * 管理者登录成功后，将已有的图书信息展示出来，并提供操作选项
	 */
	public static void adminShowBooks(){
		showBooks();
		adminChoice();
	// adminShowBooks()方法结束位置
	}
	
	/**
	 * 用户登录成功后，将已有的图书信息展示出来，并提供操作选项
	 */
	public static void userShowBooks(){
		showBooks();
		userChoice();
	// 	userShowBooks()方法结束位置
	}
	
	/**
	 * 管理员的选择操作方法
	 */
	public static void adminChoice(){
		System.out.print("请选择您要进行的操作：");
		System.out.println("1.新增图书\t2.下架图书\t3.修改图书信息\t4.查询图书\t5.购买图书\t6.切换账号\t7.退出系统");
		String s1 = sc.next();
		boolean isN1 = userUtil.isNumber(s1);
//		System.out.println(isN1);
		if(!isN1){
			System.out.println("请规范您的输入内容");
			adminChoice();
		} else{
			int choice = Integer.parseInt(s1);
			switch(choice){
			case 1:
				addBook();
				break;
			case 2:
				delBook();
				break;
			case 3:
				reviseBook();
				break;
			case 4:
				queryBooks(1);
				break;
			case 5:
				buyBook(1, 0, "");
				break;
			case 6:
				sign.choice();
				break;
			case 7:
				System.out.println("期待您的下次光临，啾咪！");
				System.exit(0);
				break;
			default:
				System.out.println("请按规定重新输入");
				adminChoice();
				break;
			}
		}	
	//adminChoice()方法结束位置
	}
	
	/**
	 * 用户的选择操作方法
	 */
	public static void userChoice(){
		System.out.print("请选择您要进行的操作：");
		System.out.println("1.查询图书\t2.购买图书\t3.切换账号\t4.退出系统");
		String s1 = sc.next();
		boolean isN1 = userUtil.isNumber(s1);
//		System.out.println(isN1);
		if(!isN1){
			System.out.println("请规范您的输入内容");
			userChoice();
		} else{
			int choice = Integer.parseInt(s1);
			switch(choice){
			case 1:
				queryBooks(0);
				break;
			case 2:
				buyBook(0, 0, "");
				break;
			case 3:
				sign.choice();
				break;
			case 4:
				System.out.println("期待您的下次光临，啾咪！");
				System.exit(0);
				break;
			default:
				System.out.println("请按规定重新输入");
				userChoice();
				break;
			}
		}	
	//userChoice()方法结束位置
	}
	
	
	
	/**
	 * 新增图书的方法
	 */
	private static void addBook() {
		String choise = "";
		boolean flag = false;
		double bPrice = 0;
		int bCount = 0;
		
		do{
			System.out.print("请输入新增的图书名：");
			String bName = sc.next();
			boolean bNameIsOk = bookUtil.validateBookName(bName);
			if(bNameIsOk){
				System.out.println("该书已存在，请重新输入");
				addBook();
			} else{
				System.out.println("请输入作者");
				String bAuthor = sc.next();
				
				System.out.print("请输入价格：");
				String s1 = sc.next();
				boolean isN1 = bookUtil.isNumber(s1);
				if(!isN1){
					System.out.println("请规范您的输入内容");
					flag = true;
					continue;
				} else{
					s1 = bookUtil.P(s1);
					bPrice = Double.parseDouble(s1);
					if(bPrice < 0 || bPrice == 0){
						System.out.println("请规范您的输入内容");
						flag = true;
						continue;
					}
				}
				
				System.out.print("请输入库存：");
				String s2 = sc.next();
				boolean isN2 = bookUtil.isNumber(s2);
				if(!isN2){
					System.out.println("请规范您的输入内容");
					flag = true;
					continue;
				} else{
					bCount = Integer.parseInt(s2);	
					if(bCount < 0 || bCount == 0){
						System.out.println("请规范您的输入内容");
						flag = true;
						continue;
					}
				}
				
				// 根据用户输入的图书信息， 创建一个图书对象
				Book book = new Book(bName, bAuthor, bPrice, bCount);
				
				bookUtil.writeAddBook(book);
				showBooks();
			}
			
			choise = bookUtil.yOrN();
			if("y".equals(choise)){
				flag = true;
			} else if("n".equals(choise)){
				adminShowBooks();
			}
			
		} while(flag);
	// addBook()方法结束位置	
	}
	
	/**
	 * 下架图书的方法
	 */
	private static void delBook() {
		String choise = "";
		boolean flag = false;
		
		do{
			Book[] books = bookUtil.bookInit(pathConstant.bookPath);
			showBooks();
			System.out.println("请输入要下架的书名");
			String name = sc.next();
			
			boolean bNameIsOk = bookUtil.validateBookName(name);
			if(!bNameIsOk){
				System.out.println("您要下架的图书不存在，请重新输入：");
				delBook();
			} else{
				for(int i = 0; i < books.length; i++){
					if(books[i] != null){
						if(books[i].getBookName().equals(name)){
							bookUtil.writeDelBook(books, i, books.length);
							showBooks();
							break;
						}
					} else{
						break;
					}
				}
			}
			
			choise = bookUtil.yOrN();
			if("y".equals(choise)){
				flag = true;
			} else if("n".equals(choise)){
				adminShowBooks();
			}
			
		} while(flag);
		
	// delBook()方法结束位置	
	}
	
	/**
	 * 修改图书的方法
	 */
	private static void reviseBook() {
		String choise = "";
		boolean flag = false;
		double bPrice = 0;
		int bCount = 0;
		
		showBooks();
		System.out.println("请输入您要修改信息的图书名：");
		String bName = sc.next();
		boolean bNameIsOk = bookUtil.validateBookName(bName);
		if(!bNameIsOk){
			System.out.println("您要修改的图书不存在，请重新输入：");
			reviseBook();
		} else{
			do{
				Book[] books = bookUtil.bookInit(pathConstant.bookPath);
				for(int i = 0; i < books.length; i++){
					if(books[i] != null){
						if(books[i].getBookName().equals(bName)){
							System.out.println(" 书     名\t\t作    者\t\t价   格\t\t库 存");
							System.out.println(books[i]);
							System.out.println();
							System.out.println("请选择要修改的内容：\t1.书名\t2.作者\t3.价格\t4.库存" );
							String s = sc.next();
							boolean isN = bookUtil.isNumber(s);
							if(!isN){
								System.out.println("请规范您的输入内容");
								System.out.println();
								flag = true;
								continue;
							} else{
								int number = Integer.parseInt(s);
								
								switch(number){
								case 1:
									System.out.println("请输入修改后的书名：");
									String name = sc.next();
									books[i].setBookName(name);;
									bookUtil.writeBookArray(books);
									bName = name;
									showBooks();
									break;
								case 2:
									System.out.println("请输入修改后的作者名：");
									String author = sc.next();
									books[i].setAuthor(author);
									bookUtil.writeBookArray(books);
									showBooks();
									break;
								case 3:
									System.out.println("请输入修改后的价格：");
									String s1 = sc.next();
									boolean isN1 = bookUtil.isNumber(s1);
									if(!isN1){
										System.out.println("请规范您的输入内容");
										flag = true;
										continue;
									} else{
										s1 = bookUtil.P(s1);
										bPrice = Double.parseDouble(s1);
										if(bPrice < 0 || bPrice == 0){
											System.out.println("请规范您的输入内容");
											flag = true;
											continue;
										}
									}
									books[i].setBookPrice(bPrice);
									bookUtil.writeBookArray(books);
									showBooks();
									break;
								case 4:
									System.out.println("请输入修改后的库存量：");
									String s2 = sc.next();
									boolean isN2 = bookUtil.isNumber(s2);
									if(!isN2){
										System.out.println("请规范您的输入内容");
										flag = true;
										continue;
									} else{
										bCount = Integer.parseInt(s2);	
										if(bCount < 0 || bCount == 0){
											System.out.println("请规范您的输入内容");
											flag = true;
											continue;
										}
									}
									books[i].setCount(bCount);
									bookUtil.writeBookArray(books);
									showBooks();
									break;
								default:
									System.out.println("请重新选择您要进行的操作");
									flag = true;
									break;
								}	
							}	
						}
					} else{
						break;
					}
				}
				
				choise = bookUtil.yOrN();
				if("y".equals(choise)){
					flag = true;
				} else if("n".equals(choise)){
					adminShowBooks();
				}
				
			} while(flag);
		}
			
	// reviseBook()方法结束位置	
	}
	
	/**
	 * 查询图书的方法，模糊查询
	 */
	private static void queryBooks(int number) {
		String choise = "";
		boolean flag = false;
		
		do{
			int num = 0;
			Book[] books = bookUtil.bookInit(pathConstant.bookPath);
			System.out.println("请输入查询的关键字：");
			String key = sc.next();
			
			for (int i = 0; i < books.length; i++) {
				// 找到 包含 关键字的图书
				if(books[i].getBookName().contains(key) || books[i].getAuthor().contains(key)) {
					books[i].show();
					continue;
				} else{
					num++;
					if(num == books.length){
						System.out.println("没有找到匹配的信息");
					}
				}
			}
			
			choise = bookUtil.yOrN();
			if("y".equals(choise)){
				flag = true;
			} else if("n".equals(choise)){
				if(number == 0){
					userShowBooks();
				} else if(number == 1){
					adminShowBooks();
				}
			}
			
		} while(flag);
	// queryBooks()方法结束位置	
	}
	
	/**
	 * 购买图书的方法
	 * @param number 为区分管理者和用户的标记，当number = 0时，为用户，八折；
	 * 									当number = 1时，为管理者，五折；
	 */
	private static void buyBook(int number, double Money, String a) {
		String choise = "";
		boolean flag = false;
		// 目标图书的库存数量
		int targetHaveBookCount = 0;
		// 目标图书的购买数量
		int targetBuyBookCount = 0;
		// 目标图书的剩余数量
		int targetOverplusBookCount = 0;
		// 目标图书的价格
		double targetBookPrice = 0;
		// 管理者购买书的价格
		double adminBookPrice = 0;
		// 用户购买图书的价格
		double userBookPrice = 0;
		// 购买单一图书的小计金额
		double money = 0;
		// 购买完成后的总计金额
		double totalMoney = Money;
		// 小计字符串
		String s = "";
		// 收据字符串
		String receipt = a;
		// 实付款
		double payMoney = 0;
		// 找零
		double rebackMoney = 0;
		
		do{
			Book[] books = bookUtil.bookInit(pathConstant.bookPath);
			showBooks();
			System.out.println("请输入您要购买的图书名");
			String name = sc.next();
			
			boolean bNameIsOk = bookUtil.validateBookName(name);
			if(!bNameIsOk){
				System.out.println("您要购买的图书不存在，请重新输入：");
				buyBook(number, totalMoney, receipt);
			} else{
				for(int i = 0; i < books.length; i++){
					if(books[i] != null){
						if(books[i].getBookName().equals(name)){
							targetHaveBookCount = books[i].getCount();
							targetBookPrice = books[i].getBookPrice();
							
							System.out.println("请输入您要购买的数量：");
							String s1 = sc.next();
							boolean isN1 = bookUtil.isNumber(s1);
							if(!isN1){
								System.out.println("请规范您的输入内容");
								flag = true;
								continue;
							} else{
								targetBuyBookCount = Integer.parseInt(s1);	
								if(targetBuyBookCount < 0 || targetBuyBookCount == 0){
									System.out.println("请规范您的输入内容");
									flag = true;
									continue;
								} else{
									if(targetHaveBookCount == 0){
										System.out.println("该书已售罄，请购买其它图书");
										flag = true;
										continue;
									} else{
										// 当购买数量小于等于库存数量时
										if(targetBuyBookCount < targetHaveBookCount || targetBuyBookCount == targetHaveBookCount){
											if(number == 0){
												money = targetBookPrice * 0.8 * targetBuyBookCount;
											} else if(number == 1){
												money = targetBookPrice * 0.5 * targetBuyBookCount;
											}
											s = books[i].getBookName() + "\t\t" + targetBookPrice + "\t\t" + targetBuyBookCount + "\t\t" + money + "\r\n";
											
											targetOverplusBookCount = targetHaveBookCount - targetBuyBookCount;
											books[i].setCount(targetOverplusBookCount);
											bookUtil.writeBookArray(books);
											showBooks();
											//当购买数量大于库存数量时
										} else if(targetBuyBookCount > targetHaveBookCount){
											System.out.print("本阁" + books[i].getBookName() + "图书现有库存量与您的实际需求量相差：");
											System.out.println((targetBuyBookCount - targetHaveBookCount) + "本");
											System.out.println("本阁将按最大库存量出货");
											if(number == 0){
												money = targetBookPrice * 0.8 * targetHaveBookCount;
											} else if(number == 1){
												money = targetBookPrice * 0.5 * targetHaveBookCount;
											}
											s = books[i].getBookName() + "\t\t" + targetBookPrice + "\t\t" + targetHaveBookCount + "\t\t" + money + "\r\n";
											
											targetOverplusBookCount = 0;
											books[i].setCount(targetOverplusBookCount);
											bookUtil.writeBookArray(books);
											showBooks();
										}
										totalMoney = totalMoney + money;	
										receipt = receipt + s;
									}
								}
							}
							
						}
					} else{
						break;
					}
				// for循环结束位置	
				}
			}
			
			choise = bookUtil.yOrN();
			if("y".equals(choise)){
				flag = true;
			} else if("n".equals(choise)){
				payMoney = bookUtil.payMoney(totalMoney);
				rebackMoney = payMoney - totalMoney;
				bookUtil.writeReceipt(totalMoney, payMoney, rebackMoney, receipt, number);
				
				System.out.println("收据信息：");
				System.out.println("***************藏书阁***************");
				System.out.println("购物列表：");
				System.out.println("名称\t\t单价\t\t数量\t\t小计");
				System.out.println(receipt);
				System.out.println("-----------------------------------");
				if(number == 0){
					System.out.println("合计：\t\t" + (int)(totalMoney / 0.8));
					System.out.println("折扣：\t\t0.8");	
				} else if(number == 1){
					System.out.println("合计：\t\t" + (int)(totalMoney / 0.5));
					System.out.println("折扣：\t\t0.5");
				}
				System.out.println("应付：\t\t" + (int)totalMoney);
				System.out.println("实付：\t\t" + (int)payMoney);
				System.out.println("找零：\t\t" + (int)rebackMoney);
				System.out.println("**********************************");
				System.out.println();
				if(number == 0){
					userShowBooks();
				} else{
					adminShowBooks();
				}
			}
			
		} while(flag);
	// buyBook()方法结束位置	
	}
	
	
	
// 类方法结束位置
}
