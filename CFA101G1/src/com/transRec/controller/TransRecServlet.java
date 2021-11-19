package com.transRec.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.auctOrderDetl.model.AuctOrdDetlVO;
import com.google.gson.Gson;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.transRec.model.TransRecService;
import com.transRec.model.TransRecVO;

import Utils.ScenerioControl;

public class TransRecServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// 設立會員有無登入機制錢包
		HttpSession session = req.getSession();
		MemberVO memVO = (MemberVO) session.getAttribute("user");
		boolean memVOIsNull = memVO == null;
		Integer memNo = null;
		if (!memVOIsNull) {
			memNo = memVO.getMemno();
		}
		List<String> memLoginNeedPage = new ArrayList();
		memLoginNeedPage.add("getOneMemRec");
		
		if (memVOIsNull && !memLoginNeedPage.contains(action)) {
			// session取得使用者資訊失敗，導向首頁
			String ordRecurl = "/frontend/transRec/deposit.jsp";
			RequestDispatcher failView = req.getRequestDispatcher(ordRecurl);
			failView.forward(req, res);
			return;
		}

//		if ("memLogin".equals(action)) {
//			MemDao memDAO = new MemDao();
//			MemVO memVO = new MemVO();
//			memVO.setAcccount(req.getParameter("acccount"));
//			memVO.setPassword(req.getParameter("password"));
//
//			MemVO memLoginVO = memDAO.memberLogin(memVO);
//			req.getSession().setAttribute("memVO", memLoginVO);
//
//			String requestUrl = req.getParameter("forwardPage");
//			RequestDispatcher dispatch = req.getRequestDispatcher(requestUrl);
//			dispatch.forward(req, res);
//		}
//
//		if ("logout".equals(action)) {
//			req.getSession().removeAttribute("memVO");
//
//			String requestUrl = req.getParameter("forwardPage");
//			RequestDispatcher dispatch = req.getRequestDispatcher(requestUrl);
//			dispatch.forward(req, res);
//		}

		// 查詢錢包餘額
		if ("deposit".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
//			try {

			if (memVO != null) {
				TransRecService TranSvc = new TransRecService();
				Integer money = TranSvc.getDeposit(memNo);
				req.setAttribute("money", money);
			}

			String url = "/frontend/transRec/deposit.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failView = req.getRequestDispatcher("/frontend/transRec/deposit.jsp");
//				failView.forward(req, res);
//			}
		}
		// 錢包儲值
		if ("saveMoney".equals(action)) {

			String transAmountstr = req.getParameter("transAmount");
			if (transAmountstr.trim().isEmpty()) {
				String failurl = "/frontend/transRec/deposit.jsp";
				RequestDispatcher failView = req.getRequestDispatcher(failurl);
				failView.forward(req, res);
				return;

			}
			Integer transAmount = null;
			transAmount = new Integer(transAmountstr);
			if (transAmount == null || transAmount <= 0) {
				String failurl = "/frontend/transRec/deposit.jsp";
				RequestDispatcher failView = req.getRequestDispatcher(failurl);
				failView.forward(req, res);
				return;
			}

			Timestamp transRecTime = new Timestamp(System.currentTimeMillis());
			TransRecService TranSvc = new TransRecService();
			TransRecVO transRecVO = new TransRecVO();
			transRecVO.setMemNo(memNo);
			transRecVO.setMallName("系統儲值");
			transRecVO.setOrderNo(null);
			transRecVO.setTransAmount(transAmount);
			transRecVO.setTransCont("");
			transRecVO.setTransState(2);
			transRecVO.setTransRecTime(transRecTime);

			// 取得存入的單筆紀錄
			TranSvc.saveMoney(transRecVO);
			req.setAttribute("transRecVO", transRecVO);

			// 建立一個錢包交易紀錄表
			// 錢包交易紀錄編號,錢包紀錄
			List<TransRecVO> transRecList = new ArrayList<TransRecVO>();
			TransRecService TranRecSvc = new TransRecService();

			TranRecSvc.memTransRec(memVO.getMemno());
			transRecList.add(transRecVO);

			req.setAttribute("transRecList", transRecList);
			String url = "/frontend/transRec/transRec.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		// 查詢錢包交易
		if ("transRec".equals(action)) {
//			Object obj = session.getAttribute("user");
//			MemberVO memVO;
//			if (obj == null &&  ScenerioControl.isInDevelop()) {
//				memVO = getMemVO();
//				session.setAttribute("user", memVO);
//			} else {
//				memVO = (MemberVO) obj;
//			}

			TransRecService tranRecSvc = new TransRecService();
			List<TransRecVO> transRecList = tranRecSvc.memTransRec(memVO.getMemno());
			req.setAttribute("transRecList", transRecList);

			String url = "/frontend/transRec/transRec.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		
		//後臺查詢會員交易紀錄
		
		if ("getOneMemRec".equals(action)) {
			String memNostr=req.getParameter("memno");
			Integer memberNo=new Integer(memNostr);
			MemberService memberSvc=new MemberService();
			MemberVO memberVO=memberSvc.getOneMem(memberNo);
			
			TransRecService tranRecSvc = new TransRecService();
			List<TransRecVO> transRecList = tranRecSvc.memTransRec(memberVO.getMemno());
			req.setAttribute("transRecList", transRecList);
			String url = "/backend/transRec/memRec.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		
		
		if ("ajaxSaveMoney".equals(action)) {
			
			PrintWriter out = res.getWriter();
			//接收錢包輸入的金額
			Integer transAmount = new Integer (req.getParameter("transAmount"));
			Timestamp transRecTime = new Timestamp(System.currentTimeMillis());
				if(memVO==null) {
					out.write("noMember");
					return;
				}
				
			TransRecService tranSvc = new TransRecService();
			TransRecVO transRecVO = new TransRecVO();
			transRecVO.setMemNo(memNo);
			transRecVO.setMallName("系統儲值");
			transRecVO.setOrderNo(null);
			transRecVO.setTransCont("");
			transRecVO.setTransState(2);
			transRecVO.setTransRecTime(transRecTime);
			
			
			// 錢包物件以字串方式傳遞[成功]的標記符號
			if (transAmount>= 0) {
				transRecVO.setTransAmount(transAmount);
				tranSvc.saveMoney(transRecVO);
				out.write("success");
			} else {
				out.write("fail");
				return;
			}			
		}
	


		// 用訂單檢驗儲值
		if ("ajaxSaveMoney1".equals(action)) {
			PrintWriter out = res.getWriter();

			// 接收錢包輸入的金額
			Integer transAmount = new Integer(req.getParameter("transAmount"));
			Timestamp transRecTime = new Timestamp(System.currentTimeMillis());
			JSONObject moneyObj = new JSONObject();
			if (memVO == null) {
				try {
					moneyObj.put("status", "noMember");
				} catch (JSONException e) {
				}
				out.write(moneyObj.toString());
				return;
			}

			TransRecService tranSvc = new TransRecService();
			TransRecVO transRecVO = new TransRecVO();
			transRecVO.setMemNo(memNo);
			transRecVO.setMallName("系統儲值");
			transRecVO.setOrderNo(null);
			transRecVO.setTransCont("");
			transRecVO.setTransState(2);
			transRecVO.setTransRecTime(transRecTime);

			try {
				// 錢包物件以字串方式傳遞[成功]的標記符號
				if (transAmount >= 0) {
					transRecVO.setTransAmount(transAmount);
					tranSvc.saveMoney(transRecVO);
					Integer nowMoney = tranSvc.getDeposit(transRecVO.getMemNo());

					moneyObj.put("nowMoney", nowMoney);
					moneyObj.put("status", "success");

				} else {
					moneyObj.put("status", "fail");
			
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			out.write(moneyObj.toString());
			
		}
		
		
		// 用AJAX確認錢包餘額
//		if ("ajaxcheckMoney".equals(action)) {
//			PrintWriter out = res.getWriter();
//			// 撈會員餘額
//			// 1.確認會員有沒有登入
//			if (memVO == null) {
//				out.write("noMember");
//				return;
//			} else {
//				// 2.確認錢夠不夠
//				TransRecService tranSvc = new TransRecService();
//				Integer nowMoney = tranSvc.getDeposit(memVO.getMemno());
//				// 傳送錢包的餘額
//				JSONObject obj = new JSONObject();
//				try {
//					obj.put("nowMoney", nowMoney);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				// 傳給AJAX錢包餘額
//				out.print(obj);
//				out.flush();
//				out.close();
//
//			}
//
//		}

	}

	// 算購物車的總額
	private Integer countTotalPrice(Map<Integer, AuctOrdDetlVO> cart) {
		Integer sum = 0;

		for (AuctOrdDetlVO detlVO : cart.values()) {
			Integer quantity = detlVO.getProdPurQty();
			Integer price = detlVO.getPrice();

			Integer prodSum = quantity * price;
			sum += prodSum;

			detlVO.setSumPrice(prodSum);
			cart.put(detlVO.getAuctActProdNo(), detlVO);
		}

		return sum;
	}

	// 假會員資料
//	private MemberVO getMemVO() {
//		MemberVO memVO = new MemberVO();
//		memVO.setMemno(11003);
//		return memVO;
//	}
}