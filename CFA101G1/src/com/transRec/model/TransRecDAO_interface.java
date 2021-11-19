package com.transRec.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TransRecDAO_interface {

	Integer add(TransRecVO transRec);

	void update(TransRecVO transRec);

	List<TransRecVO> findByMemNo(Integer memNo);

	TransRecVO findByMall_Order(String mallName, Integer orderNo);

	List<TransRecVO> getAll();

	
	Integer getDeposit(Integer memNo);
	//建立錢包與訂單連線
	Connection getConnection() throws SQLException;
	Integer addInTransaction(Connection con, TransRecVO transRec) throws SQLException;
	
	List<TransRecVO> findByOrderNo(Integer orderNo);
}
