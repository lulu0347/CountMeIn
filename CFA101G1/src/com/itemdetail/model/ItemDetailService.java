package com.itemdetail.model;

import java.sql.Connection;
import java.util.List;

public class ItemDetailService {
	
	private ItemDetailDAO_interface dao;
	
	public ItemDetailService() {
		dao = new ItemDetailDAO();
	}
		
	public ItemDetailVO addItemDetail(Integer orderNo, Integer itemNo, Integer itemSales, Integer itemPrice) {
		
		ItemDetailVO itemDetailVO = new ItemDetailVO();
		
		itemDetailVO.setOrderNo(orderNo);
		itemDetailVO.setItemNo(itemNo);
		itemDetailVO.setItemSales(itemSales);
		itemDetailVO.setItemPrice(itemPrice);
		dao.addItemDetail(itemDetailVO);
		
		return itemDetailVO;
	}
	//------------------更新訂單明細--------------------------------
	public ItemDetailVO updateItemDetail(Integer itemSales, Integer orderNo, Integer itemNo) {
		
		ItemDetailVO itemDetailVO = new ItemDetailVO();
		
		itemDetailVO.setItemSales(itemSales);
		itemDetailVO.setOrderNo(orderNo);
		itemDetailVO.setItemNo(itemNo);
		dao.updateItemDetail(itemDetailVO);
		
		return itemDetailVO;
	}
	
	public void deleteItemDetail(Integer orderNo, Integer itemNo, Integer itemSales, Integer itemPrice) {
		ItemDetailVO itemDetailVO = new ItemDetailVO();
		
		itemDetailVO.setOrderNo(orderNo);
		itemDetailVO.setItemNo(itemNo);
		itemDetailVO.setItemSales(itemSales);
		itemDetailVO.setItemPrice(itemPrice);
		dao.deleteItemDetail(itemDetailVO);	

	}
	//------------------以訂單編號查詢單一訂單所有明細--------------------------------	
	public List<ItemDetailVO> GetAllByOrderNo(Integer orderNo) {
		return dao.GetAllByOrderNo(orderNo);
	}
	//------------------查詢所有訂單明細--------------------------------
	public List<ItemDetailVO> getAll(){
		return dao.getAll();
	}
	
	public ItemDetailVO findByPrimaryKey(Integer orderNo, Integer itemNo) {
		return dao.findByPrimaryKey(orderNo, itemNo);
	}
	
	//--------------同時新增訂單跟明細---------------
	public void updateByShopping(ItemDetailVO ItemDetailVO, Connection con) {
		dao.updateByShopping(ItemDetailVO, con);
	}
		
	
}
