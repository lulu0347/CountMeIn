package com.transRec.model;

import java.sql.Timestamp;

public class TransRecVO {

	private Integer transRecNo;
	private Integer memNo;
	private Integer transAmount;
	private Timestamp transRecTime;
	private Integer transState;
	private String mallName;
	private Integer orderNo;
	private String transCont;

	public TransRecVO() {
		super();

	}

	public TransRecVO(Integer transRecNo, Integer memNo, Integer transAmount, Timestamp transRecTime,
			Integer transState, String mallName, Integer orderNo, String transCont) {
		super();
		this.transRecNo = transRecNo;
		this.memNo = memNo;
		this.transAmount = transAmount;
		this.transRecTime = transRecTime;
		this.transState = transState;
		this.mallName = mallName;
		this.orderNo = orderNo;
		this.transCont = transCont;
	}

	public Integer getTransRecNo() {
		return transRecNo;
	}

	public void setTransRecNo(Integer transRecNo) {
		this.transRecNo = transRecNo;
	}

	public Integer getMemNo() {
		return memNo;
	}

	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}

	public Integer getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Integer transAmount) {
		this.transAmount = transAmount;
	}

	public Timestamp getTransRecTime() {
		return transRecTime;
	}

	public void setTransRecTime(Timestamp transRecTime) {
		this.transRecTime = transRecTime;
	}

	public String getTransStateString() {
		String result = null;
		switch (this.transState) {
		case 0:
			result = "取消交易";
			break;
		case 1:
			result = "商城扣款";
			break;
		case 2:
			result = "儲值";
			break;
		}

		return result;
	}

	public Integer getTransState() {
		return transState;
	}

	public void setTransState(Integer transState) {
		this.transState = transState;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getTransCont() {
		return transCont;
	}

	public void setTransCont(String transCont) {
		this.transCont = transCont;
	}

	@Override
	public String toString() {
		return "TransRecVO [transRecNo=" + transRecNo + ", memNo=" + memNo + ", transAmount=" + transAmount
				+ ", transRecTime=" + transRecTime + ", transState=" + transState + ", mallName=" + mallName
				+ ", orderNo=" + orderNo + ", transCont=" + transCont + "]";
	}

}
