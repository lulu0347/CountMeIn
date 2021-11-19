package com.itempic.model;

import java.util.List;
import com.itempic.model.*;


public class ItemPicService {
	
	private  ItemPicDAO_interface dao;
	
	public ItemPicService() {
		dao = new ItemPicDAO();
	}
	
	public ItemPicVO addPic(Integer itemNo, byte[] itemPic) {
		
		ItemPicVO itemPicVO = new ItemPicVO();
		
	
		itemPicVO.setItemNo(itemNo);
		itemPicVO.setItemPic(itemPic);
		dao.addPic(itemPicVO);
		
		return itemPicVO;
	}
	
	public ItemPicVO updatePic(Integer itemPicNo, Integer itemNo, byte[] itemPic) {
	
		ItemPicVO itemPicVO = new ItemPicVO();
		
		itemPicVO.setItemPicNo(itemPicNo);
		itemPicVO.setItemNo(itemNo);
		itemPicVO.setItemPic(itemPic);				
		dao.updatePic(itemPicVO);
		
		return itemPicVO;
	}
	
	public void deletePic(Integer itemPicNo) {
		dao.deletePic(itemPicNo);
		
	}
	
	public ItemPicVO findByItemPicNo(Integer itemPicNo) {
		ItemPicVO itemPicVO = new ItemPicVO();
		itemPicVO = dao.findByItemPicNo(itemPicNo);
		
		return itemPicVO;
	}
	
	public List<ItemPicVO> getAllPics(){
		return dao.getAllPics();
	}
	
	public List<ItemPicVO> findByItemNo(Integer itemNo) {
		return dao.getByItemNo(itemNo);
	}
}
