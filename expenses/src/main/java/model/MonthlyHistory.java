package model;

import java.io.Serializable;

/**
 * The persistent class for the history database table.
 *
 */
public class MonthlyHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	private String category;

//	private String sumPrice;
	private int sumPrice;


	public MonthlyHistory(String category,int price) {
		this.category = category;
		this.sumPrice =price;
	}

	public int getSumPrice() {
		return this.sumPrice;
	}

	public void setSumPrice(int price) {
		this.sumPrice = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}



}