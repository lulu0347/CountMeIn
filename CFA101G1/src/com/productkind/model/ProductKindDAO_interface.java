package com.productkind.model;

import java.util.*;

public interface ProductKindDAO_interface {
          public void insert(ProductKindVO productKindVO);
          public void update(ProductKindVO productKindVO);
          public ProductKindVO findByPrimaryKey(Integer kindNo);
          public List<ProductKindVO> getAll();
          //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//        public List<EmpVO> getAll(Map<String, String[]> map); 
}
