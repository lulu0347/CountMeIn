package com.itemdetail.model;

import java.io.Serializable;

public class ItemDetailVO implements Serializable {

	private Integer orderNo;
	private Integer itemNo;
	private	Integer itemSales;
	private	Integer itemPrice;
	
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	public Integer getItemSales() {
		return itemSales;
	}
	public void setItemSales(Integer itemSales) {
		this.itemSales = itemSales;
	}
	public Integer getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Integer itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	
	

}
