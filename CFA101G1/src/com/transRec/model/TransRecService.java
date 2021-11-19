package com.transRec.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.member.model.MemberService;

public class TransRecService {

	private TransRecDAO_interface transRecDao = new TransRecDAO_JDBC();
	private MemberService memSvc = new MemberService();

	/**
	 * 取得會員錢包現有金額
	 * 
	 * @param memNo
	 * @return 金額
	 */
	public Integer getDeposit(Integer memNo) {
		Integer money = null;
		if (memNo<1) {
			throw new RuntimeException("此會員不存在");
		} else {
			money = transRecDao.getDeposit(memNo);
		}
		return money;
	}

	/**
	 * 儲值
	 * 
	 * @param transRecVO
	 */
	public void saveMoney(TransRecVO transRecVO) {
		if (transRecVO.getTransAmount() == null || transRecVO.getTransAmount() <= 0) {
			throwException("amount 進行錢包儲值應為正值", transRecVO);
		}

		isValid(transRecVO);

		transRecDao.add(transRecVO);
		Integer eCash = transRecDao.getDeposit(transRecVO.getMemNo());
		memSvc.updateEcash(transRecVO.getMemNo(), eCash);
	}

	/**
	 * 建立交易扣款(未使用連線)
	 * 
	 * @param transRecVO
	 * @return 取得交易紀錄PK
	 */
	public Integer createTransRecord(TransRecVO transRecVO) {
		if (transRecVO.getTransAmount() == null || transRecVO.getTransAmount() >= 0) {
			throwException("amount 進行錢包扣款應為負值", transRecVO);
		}
		isValidForCreateTransRecord(transRecVO);
		Integer transRecNo = transRecDao.add(transRecVO);
		Integer eCash = transRecDao.getDeposit(transRecVO.getMemNo());
		memSvc.updateEcash(transRecVO.getMemNo(), eCash);
		return transRecNo;
	}
	
	
	/**
	 * 建立交易扣款，二手商城用，同步新增賣家取款
	 * 
	 * @param transRecVO
	 * @return 取得交易紀錄PK
	 */
	public Integer createTransRecordWithSeller(TransRecVO transRecVO, Integer memNo) {
		if (transRecVO.getTransAmount() == null || transRecVO.getTransAmount() >= 0) {
			throwException("amount 進行錢包扣款應為負值", transRecVO);
		}
		isValidForCreateTransRecord(transRecVO);
		TransRecVO sellerTransRecVO = createSellerTransRec(transRecVO, memNo);
		transRecDao.add(sellerTransRecVO);
		Integer transRecNo = transRecDao.add(transRecVO);
		Integer eCash = transRecDao.getDeposit(transRecVO.getMemNo());
		memSvc.updateEcash(transRecVO.getMemNo(), eCash);
		return transRecNo;
	}

	/**
	 * 二手商城用，非同步允許賣家取款
	 * 
	 * @param transRecVO
	 * @return 取得交易紀錄PK
	 */
	public Integer createTransRecordForSeller(TransRecVO transRecVO) {
		if (transRecVO.getTransAmount() == null || transRecVO.getTransAmount() <= 0) {
			throwException("賣家取得款項應為正值", transRecVO);
		}
		isValidForCreateTransRecord(transRecVO);
		Integer transRecNo = transRecDao.add(transRecVO);
		Integer eCash = transRecDao.getDeposit(transRecVO.getMemNo());
		memSvc.updateEcash(transRecVO.getMemNo(), eCash);
		return transRecNo;
	}
	

	private TransRecVO createSellerTransRec(TransRecVO transRecVO, Integer memNo) {
		TransRecVO sellerTransRecVO =new TransRecVO();
		sellerTransRecVO.setMallName(transRecVO.getMallName());
		sellerTransRecVO.setMemNo(memNo);
		sellerTransRecVO.setOrderNo(transRecVO.getOrderNo());
		sellerTransRecVO.setTransRecTime(transRecVO.getTransRecTime());
		sellerTransRecVO.setTransAmount(-transRecVO.getTransAmount());
		sellerTransRecVO.setTransState(1);
		return sellerTransRecVO;
	}

	/**
	 * 在交易狀態下建立扣款建立交易扣款
	 * 
	 * @param transRecVO
	 * @return 取得交易紀錄PK
	 */
	public Integer createTransRecordInTransaction(Connection con, TransRecVO transRecVO) {
		if (transRecVO.getTransAmount() == null || transRecVO.getTransAmount() >= 0) {
			throwException("amount", transRecVO);
		}
		isValidForCreateTransRecord(transRecVO);

		Integer transRecNo = null;
		try {
			transRecNo = transRecDao.addInTransaction(con, transRecVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Integer eCash = transRecDao.getDeposit(transRecVO.getMemNo());
		memSvc.updateEcash(transRecVO.getMemNo(), eCash);
		return transRecNo;
	}

	/**
	 * 取得會員錢包紀錄
	 * 
	 * @param MemNo
	 * @return
	 */
	public List<TransRecVO> memTransRec(Integer MemNo) {
		// 以會員編號取得錢包交易紀錄
		List<TransRecVO> transRecList = transRecDao.findByMemNo(MemNo);

		return transRecList;
	}

	private void isValid(TransRecVO transRecVO) {
		String mallName = transRecVO.getMallName();
		if (mallName == null || mallName.isEmpty()) {
			throwException("mallName 不得為空", transRecVO);
		}

		Integer memNo = transRecVO.getMemNo();

		if (memNo == null || memNo < 1) {
			throwException("memNo 格式錯誤", transRecVO);
		}
	}

	private void throwException(String info, TransRecVO vo) {
		System.out.println(vo);
		throw new RuntimeException(info);
	}

	private void isValidForCreateTransRecord(TransRecVO transRecVO) {
		isValid(transRecVO);
		Integer orderNo = transRecVO.getOrderNo();
		if (orderNo == null) {
			throwException("orderNo 不得為空值", transRecVO);
		}
	}

	public boolean cancelTransRecord(Integer orderNo) {
		boolean result = false;

		List<TransRecVO> recordList = transRecDao.findByOrderNo(orderNo);

		if (recordList.size() == 1) {
			TransRecVO record = recordList.get(0);
			Integer transAmount = record.getTransAmount();
			record.setTransAmount(-transAmount);
			record.setTransState(0);
			record.setMallName(record.getMallName() + "退款");
			record.setTransRecTime(new Timestamp(new Date().getTime()));
			saveMoney(record);
			result = true;
		}

		return result;
	}

}
