package com.itemorder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itemdetail.model.ItemDetailVO;
import com.itemorder.model.*;
import com.member.model.MemberVO;

public class ItemOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//------------------同時新增訂單與明細-----------------
//		if ("insertWithItemDetails".equals(action)) {
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//			//------------取得前端訂單資料---------------------
//			try {
//				Integer memNo = new Integer(req.getParameter("memNo"));
//				Integer transRecNo = new Integer(req.getParameter("transRecNo"));
//				java.sql.Timestamp tranTime = java.sql.Timestamp.valueOf(req.getParameter("tranTime"));
//				Integer orderTotal = new Integer(req.getParameter("orderTotal"));
//				Integer orderState = new Integer(req.getParameter("orderState"));
//				String receiverName = req.getParameter("receiverName");
//				String receiverAddress = req.getParameter("receiverAddress");
//				String receiverPhone = req.getParameter("receiverPhone");
//
//				Integer itemNo = new Integer(req.getParameter("itemNo"));
//				Integer itemSales = new Integer(req.getParameter("itemSales"));
//				Integer itemPrice = new Integer(req.getParameter("itemPrice"));
//
//				ItemOrderVO itemOrderVO = new ItemOrderVO();
//				itemOrderVO.setMemNo(memNo);
//				itemOrderVO.setTransRecNo(transRecNo);
//				itemOrderVO.setTranTime(tranTime);
//				itemOrderVO.setOrderTotal(orderTotal);
//				itemOrderVO.setOrderState(orderState);
//				itemOrderVO.setReceiverName(receiverName);
//				itemOrderVO.setReceiverAddress(receiverAddress);
//				itemOrderVO.setReceiverPhone(receiverPhone);
//
//				List<ItemDetailVO> list = new ArrayList<>();
//				ItemDetailVO itemDetailVO = new ItemDetailVO();
//				itemDetailVO.setItemNo(itemNo);
//				itemDetailVO.setItemSales(itemSales);
//				itemDetailVO.setItemPrice(itemPrice);
//
//				list.add(itemDetailVO);
//
//				ItemOrderDAO itemOrderDAO = new ItemOrderDAO();
//				itemOrderDAO.insertWithItemDetails(itemOrderVO, list);
//				//TODO: 
//				String url = "/backend/itemOrder/listAllOrder.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//				
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				RequestDispatcher failureView = req.getRequestDispatcher("/backend/itemOrder/addItemOrder.jsp");
//				failureView.forward(req, res);
//			}
//		}

//------------------後台新增訂單-----------------
		if ("addOrder".equals(action)) { // 來自addorder.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer memNo = null;
				try {
					memNo = new Integer(req.getParameter("memNo"));
				} catch (NumberFormatException MNe) {
					errorMsgs.add("會員編號請輸入數字唷");
				}
				if (memNo <= 11000 || memNo >= 12000) {
					errorMsgs.add("請輸入區間為11001到11999的會員編號");
				}

				java.sql.Timestamp tranTime = null;
				try {
					tranTime = java.sql.Timestamp.valueOf(req.getParameter("tranTime"));
				} catch (IllegalArgumentException TTe) {
					tranTime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入錢包交易時間!");
				}

				Integer orderTotal = null;
				try {
					orderTotal = new Integer(req.getParameter("orderTotal"));
				} catch (NumberFormatException ORe) {
					errorMsgs.add("訂單金額請輸入數字");
				}

				Integer orderState = null;
				try {
					orderState = new Integer(req.getParameter("orderState"));
				} catch (NumberFormatException OSe) {
					errorMsgs.add("訂單狀態請輸入數字唷");
				}
				if (orderState > 4 || orderState < 0) {
					errorMsgs.add("請輸入0到4的數字,0為未出貨,1為已出貨,2為已收貨,3為退貨,4為取消");
				}

				String receiverName = req.getParameter("receiverName");
				String receiverNameRex = "^[(\u4e00-\u9fa5)]{2,10}$";
				if (receiverName == null || receiverName.trim().length() == 0) {
					errorMsgs.add("請輸入收件人名稱: 請勿空白且且長度必需在2以上");
				} else if (!receiverName.trim().matches(receiverNameRex)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入收件人名稱: 只能是中、英文字母 , 且長度必需在2到10之間");
				}

				String receiverAddress = req.getParameter("receiverAddress");
				String receiverAddressRex = "^[(\\-\\)(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,80}$";
				if (receiverAddress == null || receiverAddress.trim().length() == 0) {
					errorMsgs.add("請輸入收件人地址: 請勿空白且且長度必需在2以上");
				} else if (!receiverAddress.trim().matches(receiverAddressRex)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入收件人地址: 只能是中、英文字母、數字和_ , 且長度必需在2到80之間");
				}

				String receiverPhone = req.getParameter("receiverPhone");
				String receiverPhoneRex = "^09[0-9]{8}$";
				if (receiverPhone == null || receiverPhone.trim().length() == 0) {
					errorMsgs.add("請輸入收件人電話: 請勿空白且且長度必需在10");
				} else if (!receiverPhone.trim().matches(receiverPhoneRex)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入收件人電話:數字且長度必需在10");
				}

				// 驗證完成 打包
				ItemOrderVO itemOrderVO = new ItemOrderVO();
				itemOrderVO.setMemNo(memNo);
				itemOrderVO.setTranTime(tranTime);
				itemOrderVO.setOrderTotal(orderTotal);
				itemOrderVO.setOrderState(orderState);
				itemOrderVO.setReceiverName(receiverName);
				itemOrderVO.setReceiverAddress(receiverAddress);
				itemOrderVO.setReceiverPhone(receiverPhone);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("itemOrderVO", itemOrderVO);
					String url = "/backend/itemOrder/addItemOrder.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				ItemOrderService itemOrderSvc = new ItemOrderService();
				itemOrderSvc.addOrder(memNo, tranTime, orderTotal, orderState, receiverName, receiverAddress,
						receiverPhone);
				String url = "/backend/itemOrder/listAllOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/itemOrder/addItemOrder.jsp");
				failureView.forward(req, res);
			}
		}
//------------------依照單一訂單編號查詢某一訂單-----------------			

		if ("listByOrderNo".equals(action))

		{ // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer orderNo = null;
				try {
					orderNo = new Integer(req.getParameter("orderNo"));
				} catch (NumberFormatException ONe) {
					errorMsgs.add("請輸入數字唷");
				}
				if (orderNo >= 25000 || orderNo <= 24000) {
					errorMsgs.add("請輸入24000-25000區間的數字唷");
				}

				if (!errorMsgs.isEmpty()) {
					String url = "/backend/item.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}

				ItemOrderService itemOrderSvc = new ItemOrderService();
				ItemOrderVO itemOrderVO = itemOrderSvc.findByOrderNo(orderNo);

				req.setAttribute("itemOrderVO", itemOrderVO);
				String url = "/backend/itemOrder/listOneOrderByOrderNo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/item.jsp");
				failureView.forward(req, res);
			}

		}
//------------------依照單一訂單編號更新某一訂單-----------------
		if ("updateByOrderNo".equals(action)) { // 來自update_order_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer orderNo = new Integer(req.getParameter("orderNo"));
				// 先用findByOrderNo方法撈出該筆訂單
				ItemOrderService itemOrderSvc = new ItemOrderService();
				ItemOrderVO itemOrderVO = itemOrderSvc.findByOrderNo(orderNo);

				Integer memNo = null;
				try {
					memNo = new Integer(req.getParameter("memNo"));
				} catch (NumberFormatException MNe) {
					errorMsgs.add("會員編號請輸入數字唷");
				}
				if (memNo <= 11000 || memNo >= 12000) {
					errorMsgs.add("請輸入區間為11001到11999的會員編號");
				}

				java.sql.Timestamp tranTime = null;
				try {
					tranTime = java.sql.Timestamp.valueOf(req.getParameter("tranTime"));
				} catch (IllegalArgumentException TTe) {
					tranTime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入錢包交易時間!");
				}

				Integer orderTotal = null;
				try {
					orderTotal = new Integer(req.getParameter("orderTotal"));
				} catch (NumberFormatException ORe) {
					errorMsgs.add("訂單金額請輸入數字");
				}

				Integer orderState = null;
				try {
					orderState = new Integer(req.getParameter("orderState"));
				} catch (NumberFormatException OSe) {
					errorMsgs.add("訂單狀態請輸入數字唷");
				}
				if (orderState > 4 || orderState < 0) {
					errorMsgs.add("請輸入0到4的數字,0為未出貨,1為已出貨,2為已收貨,3為退貨,4為取消");
				}

				String receiverName = req.getParameter("receiverName");
				String receiverNameRex = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
				if (receiverName == null || receiverName.trim().length() == 0) {
					errorMsgs.add("請輸入收件人名稱: 請勿空白且且長度必需在2以上");
				} else if (!receiverName.trim().matches(receiverNameRex)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入收件人名稱: 只能是中、英文字母 , 且長度必需在2到10之間");
				}

				String receiverAddress = req.getParameter("receiverAddress");
				String receiverAddressRex = "^[(\\-\\)(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,80}$";
				if (receiverAddress == null || receiverAddress.trim().length() == 0) {
					errorMsgs.add("請輸入收件人地址: 請勿空白且且長度必需在2以上");
				} else if (!receiverAddress.trim().matches(receiverAddressRex)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入收件人地址: 只能是中、英文字母、數字和_ , 且長度必需在2到80之間");
				}

				String receiverPhone = req.getParameter("receiverPhone");
				String receiverPhoneRex = "^09[0-9]{8}$";
				if (receiverPhone == null || receiverPhone.trim().length() == 0) {
					errorMsgs.add("請輸入收件人電話: 請勿空白且且長度必需在10");
				} else if (!receiverPhone.trim().matches(receiverPhoneRex)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("請輸入收件人電話:數字且長度必需在10");
				}

				itemOrderVO.setTranTime(tranTime);
				itemOrderVO.setOrderTotal(orderTotal);
				itemOrderVO.setOrderState(orderState);
				itemOrderVO.setReceiverName(receiverName);
				itemOrderVO.setReceiverAddress(receiverAddress);
				itemOrderVO.setReceiverPhone(receiverPhone);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("itemOrderVO", itemOrderVO);
					String url = "/backend/itemOrder/update_order_input.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
				}

				itemOrderSvc.updateByOrderNo(orderNo, memNo, tranTime, orderTotal, orderState, receiverName,
						receiverAddress, receiverPhone);

				req.setAttribute("itemOrderVO", itemOrderVO);
				String url = "/backend/itemOrder/listOneOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

//------------------刪除訂單--------------------------------		
		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				Integer orderNo = new Integer(req.getParameter("orderNo"));

				ItemOrderService itemOrderSvc = new ItemOrderService();
				itemOrderSvc.deleteOrder(orderNo);

				String url = "/backend/itemOrder/listAllOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/itemOrder/listAllOrder.jsp");
				failureView.forward(req, res);
			}

		}

		if ("get_One_For_Update".equals(action)) { // 來自listAllOrder.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer orderNo = new Integer(req.getParameter("orderNo"));

				/*************************** 2.開始查詢資料 ****************************************/
				ItemOrderService itemOrderSvc = new ItemOrderService();
				ItemOrderVO itemOrderVO = itemOrderSvc.findByOrderNo(orderNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("itemOrderVO", itemOrderVO);
				String url = "/backend/itemOrder/update_order_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_item_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/listAllItem.jsp");
				failureView.forward(req, res);
			}
		}

//------------------(後台)查詢單一會員所持有的訂單-----------------		
		if ("listAllOrderByMemNo".equals(action)) { // 來自select_page.jsp的請求

			Integer memNo = new Integer(req.getParameter("memNo"));

			ItemOrderService itemOrderSvc = new ItemOrderService();
			List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
			list = itemOrderSvc.gerOrderByMemNo(memNo);
			
			req.setAttribute("list", list);
			String url = "/backend/itemOrder/listOneOrderByMemNo.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

//--------------(前台)會員尋找自己目前的訂單---------------		
		if ("viewMyOrder".equals(action)) { 

			Integer memNo = new Integer(req.getParameter("memNo"));

			ItemOrderService itemOrderSvc = new ItemOrderService();
			List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
			list = itemOrderSvc.getAllOrderByMemNo(memNo);

			req.setAttribute("list", list);
			String url = "/frontend/listAllOrderByMemNo.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
	}
}
