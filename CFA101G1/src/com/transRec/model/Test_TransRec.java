package com.transRec.model;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Test_TransRec {
	
	
	static TransRecDAO_JDBC  dao= new  TransRecDAO_JDBC();
			
	public static void main(String[] args) {
		
		
//		getAllRec();
//		testInsert();
//		testUpdate();
		
		testGetDeposit();
	}

	private static void testGetDeposit() {
		dao.getDeposit(11007);
		
	}

	private static TransRecVO getVO(){
		TransRecVO transRecVO=new TransRecVO();
		transRecVO.setTransRecNo(15012);
		transRecVO.setMemNo(11001);
		transRecVO.setTransAmount(15700);
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date transTime= new Date();
		long time =transTime.getTime();
		Timestamp tranRec = new Timestamp(time);
		transRecVO.setTransRecTime(tranRec);
		transRecVO.setTransState(2);
		transRecVO.setMallName("系統儲值");
		transRecVO.setTransCont("");
		
		
		return transRecVO;
	}

	private static void testUpdate()  {
		TransRecVO  tranRecVO = getVO();
		tranRecVO.setTransRecNo(15010);
		dao.update(tranRecVO);
		
	}

	private static void testInsert() {
		TransRecVO  transRecVO = getVO();
		dao.add(transRecVO);
		
	}

	private static void getAllRec() {
		List<TransRecVO> transRecList = dao.getAll();
		for(TransRecVO transRecVO:transRecList) {
			System.out.println(transRecVO);
		}
		
	}

}
