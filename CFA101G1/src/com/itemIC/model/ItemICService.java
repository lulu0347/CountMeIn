package com.itemIC.model;

import java.util.List;

public class ItemICService {

	
	private ItemICDAO_interface dao;
	
	public ItemICService() {
		dao = new ItemICDAO();
	}
	
	
	//------讓會員搜尋收藏列表且查看時能同時顯示出商品資料------------------
	public List<ItemICVO> getCollectionByMemNo(Integer memNo){
		return dao.getCOllectionByMemNo(memNo);
	}
	
}
