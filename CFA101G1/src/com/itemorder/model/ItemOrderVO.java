package com.itemorder.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

public class ItemOrderVO implements Serializable {

	private Integer orderNo;
	private Integer memNo;
	private Timestamp tranTime;
	private Integer orderTotal;
	private Integer orderState;
	private String receiverName;
	private String receiverAddress;
	private String receiverPhone;
	
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public Timestamp getTranTime() {
		return tranTime;
	}
	public void setTranTime(Timestamp tranTime) {
		this.tranTime = tranTime;
	}
	public Integer getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(Integer orderTotal) {
		this.orderTotal = orderTotal;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	
	
}
