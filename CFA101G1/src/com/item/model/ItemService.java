package com.item.model;

import java.util.List;
import java.util.Map;

public class ItemService {

	private ItemDAO_interface dao;

	public ItemService() {
		dao = new ItemDAO();
	}

	// -----------後台新增商品--------------
	public ItemVO addItem(Integer kindNo, String itemName, Integer itemPrice, Integer itemState,
			java.sql.Timestamp launchedTime, Double warrantyDate, String itemProdDescription) {

		ItemVO itemVO = new ItemVO();

		itemVO.setKindNo(kindNo);
		itemVO.setItemName(itemName);
		itemVO.setItemPrice(itemPrice);
		itemVO.setItemState(itemState);
		itemVO.setLaunchedTime(launchedTime);
		itemVO.setWarrantyDate(warrantyDate);
		itemVO.setItemProdDescription(itemProdDescription);
		dao.insert(itemVO);

		return itemVO;
	}

	// -----------後台新增商品與照片---------------
	public ItemVO addItem2(Integer kindNo, String itemName, Integer itemPrice, Integer itemState,
			java.sql.Timestamp launchedTime, Double warrantyDate, String itemProdDescription, List<byte[]> list) {
		System.out.println("ItemService所取得的kindNo為" + kindNo);
		System.out.println("ItemService所取得的List為" + list);

		ItemVO itemVO = new ItemVO();
		itemVO.setKindNo(kindNo);
		itemVO.setItemName(itemName);
		itemVO.setItemPrice(itemPrice);
		itemVO.setItemState(itemState);
		itemVO.setLaunchedTime(launchedTime);
		itemVO.setWarrantyDate(warrantyDate);
		itemVO.setItemProdDescription(itemProdDescription);
		dao.insertWithPics(itemVO, list);
		return itemVO;
	}

	// -----------後台更新商品---------------
	public ItemVO updateItem(Integer itemNo, Integer kindNo, String itemName, Integer itemPrice, Integer itemState,
			java.sql.Timestamp soldTime,  java.sql.Timestamp launchedTime, Double warrantyDate,
			String itemProdDescription) {

		ItemVO itemVO = new ItemVO();
		itemVO.setItemNo(itemNo);
		itemVO.setKindNo(kindNo);
		itemVO.setItemName(itemName);
		itemVO.setItemPrice(itemPrice);
		itemVO.setItemState(itemState);
		itemVO.setSoldTime(soldTime);
		itemVO.setLaunchedTime(launchedTime);
		itemVO.setWarrantyDate(warrantyDate);
		itemVO.setItemProdDescription(itemProdDescription);
		dao.update(itemVO);

		return itemVO;
	}

	// -----------後台刪除商品---------------
	public void deleteItem(Integer ItemNo) {
		dao.delete(ItemNo);
	}

	// -----------查看商品詳情---------------
	public ItemVO getOneItem(Integer ItemNo) {
		return dao.findByItemNo(ItemNo);
	}

	// -----------取得所有商品---------------
	public List<ItemVO> getAll() {
		return dao.getAll();
	}

	// ----------- 手機---------------
	public List<ItemVO> getOneKind1(){
		return dao.getOneKind1();
	}

	// ----------- 電腦---------------
	public List<ItemVO> getOneKind2(){
		return dao.getOneKind2();
	}

	// ----------- 相機---------------
	public List<ItemVO> getOneKind3(){
		return dao.getOneKind3();
	}

	// ----------- 手錶---------------
	public List<ItemVO> getOneKind4(){
		return dao.getOneKind4();
	}

	// ----------- 配件---------------
	public List<ItemVO> getOneKind5(){
		return dao.getOneKind5();
	}
	
	// -----------模糊搜尋-------------
	public List<ItemVO> getAll(Map<String, String[]> map){
		return dao.getAll(map);
	}
	

}
