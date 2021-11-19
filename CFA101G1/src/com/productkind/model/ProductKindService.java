package com.productkind.model;

import java.sql.Date;
import java.util.List;

public class ProductKindService {
	private ProductKindDAO_interface dao;
	public ProductKindService() {
		dao = new ProductKindJDBCDAO();
	}
	public ProductKindVO addProductKind(String kindName) {
		ProductKindVO productKindVO = new ProductKindVO();
		productKindVO.setKindName(kindName);
		dao.insert(productKindVO);
		return productKindVO;
	}
	
	public ProductKindVO updateProductKind(Integer kindNo, String kindName) {
		ProductKindVO productKindVO = new ProductKindVO();
		productKindVO.setKindNo(kindNo);
		productKindVO.setKindName(kindName);
		dao.insert(productKindVO);
		return productKindVO;
	}
	
	public ProductKindVO getOneProductKind(Integer kindNo) {
		return dao.findByPrimaryKey(kindNo);		
	}
	
	public List<ProductKindVO> getAllProductKind() {
		return dao.getAll();		
	}
}
