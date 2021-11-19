package com.itemcollection.model;

import java.util.List;

public class ItemCollectionService {

	private ItemCollectionDAO_interface dao;
	
	public ItemCollectionService() {
		dao = new ItemCollectionDAO();
	}
	
	public ItemCollectionVO addCollection(Integer memNo, Integer itemNo) {
		
		ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
		
		itemCollectionVO.setMemNo(memNo);
		itemCollectionVO.setItemNo(itemNo);
		dao.insert(itemCollectionVO);
		
		return itemCollectionVO;
	}
	
	
	public ItemCollectionVO updateCollection(Integer memNo, Integer itemNo) {
		
		ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
		
		itemCollectionVO.setMemNo(memNo);
		itemCollectionVO.setItemNo(itemNo);
		dao.update(itemCollectionVO);
		
		return itemCollectionVO;
	}
	
	public void deleteCollection(Integer itemNo, Integer memNo) {
		dao.delete(itemNo, memNo);
	}
	
	public ItemCollectionVO findOneByMemNo(Integer memNo){
		ItemCollectionVO itemCollectionVO = new ItemCollectionVO();

		itemCollectionVO = dao.findOneByMemNo(memNo);
		
		return itemCollectionVO;
	}
	
	public List<ItemCollectionVO> getOneCollection(Integer memNo) {
		return dao.findByMemNo(memNo);
	}
	
	public List<ItemCollectionVO> getAll(){
		return dao.getAll();
	}
//-----------回傳一個數字0或1來計算此會員是否對於這個商品有加入過收藏列表----------------------
	public int getcount(Integer itemNo, Integer memNo) {
		return dao.getcount(itemNo, memNo);
	}
	
}
