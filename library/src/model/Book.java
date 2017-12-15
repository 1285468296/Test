package model;

import java.io.Serializable;

public class Book implements Serializable{

	private static final long serialVersionUID = -1783774129490795293l;
	/**
	 * 图书名,String
	 */
	private String bookName;
	/**
	 * 作者,String
	 */
	private String author;
	/**
	 * 价格,double
	 */
	private double bookPrice;
	/**
	 * 库存量,int
	 */
	private int count;
	
	
	
	/**
	 * 无参构造方法
	 */
	public Book() {
		
	}

	/**
	 * 有参构造方法
	 * @param bookName
	 * @param author
	 * @param bookPrice
	 * @param count
	 */
	public Book(String bookName, String author, double bookPrice, int count) {
		this.bookName = bookName;
		this.author = author;
		this.bookPrice = bookPrice;
		this.count = count;
	}

	/**
	 * @param s 对输出长度的控制
	 * @return
	 */
	public String T(String s){
		if(s.length() > 4){
			s =  s.subSequence(0, 3) + "...";
		}

		return s;
	}
	
	/**
	 * 重写toString方法
	 */
	@Override
	public String toString() {
		return T(bookName) + "\t\t" + T(author) + "\t\t" + bookPrice + "\t\t" + count;
	}
	
	/**
	 * 图书的全名展示方法
	 */
	public void show() {
		System.out.println(bookName + "\t\t" + author + "\t\t" + bookPrice + "\t\t" + count);
	}
	
	/**
	 * 封装方法
	 * @return
	 */
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(double bookPrice) {
		this.bookPrice = bookPrice;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
//类结束位置	
}
