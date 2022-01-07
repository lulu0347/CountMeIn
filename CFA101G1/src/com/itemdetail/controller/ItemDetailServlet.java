package com.itemdetail.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itemdetail.model.*;

public class ItemDetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//---------------------以訂單與商品編號查詢單一訂單明細並進入更新--------------
		if ("findByPrimaryKey".equals(action)) { // form listALL_ItemDetail.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer orderNo = new Integer(req.getParameter("orderNo"));

				Integer itemNo = new Integer(req.getParameter("itemNo"));

				ItemDetailService itemDetailSvc = new ItemDetailService();
				ItemDetailVO itemDetailVO = itemDetailSvc.findByPrimaryKey(orderNo, itemNo);

				req.setAttribute("itemDetailVO", itemDetailVO);
				String url = "/backend/itemDetail/update_detail_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/itemDetail/listAll_ItemDetail.jsp");
				failureView.forward(req, res);
			}
		}
//---------------------從清單選一訂單明細進更新--------------
		if ("updateItemDetail".equals(action)) { // 來自update_detail_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer orderNo = null;
				try {
					orderNo = new Integer(req.getParameter("orderNo").trim());
				} catch (NumberFormatException ONe) {
					errorMsgs.add("訂單編號請填數字.");
				}

				Integer itemNo = null;
				try {
					itemNo = new Integer(req.getParameter("itemNo").trim());
				} catch (NumberFormatException INe) {
					errorMsgs.add("商品編號請填數字.");
				}

				Integer itemSales = null;

				try {
					itemSales = new Integer(req.getParameter("itemSales").trim());
				} catch (NumberFormatException ISe) {
					errorMsgs.add("訂單商品數量請輸入數字");
				}
				if (itemSales < 0) {
					errorMsgs.add("訂單商品數量請勿輸入負數");
				}

				ItemDetailVO itemDetailVO = new ItemDetailVO();
				
				itemDetailVO.setOrderNo(orderNo);
				itemDetailVO.setItemNo(itemNo);
				itemDetailVO.setItemSales(itemSales);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("itemDetailVO", itemDetailVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/itemDetail/update_detail_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				ItemDetailService itemDetailSvc = new ItemDetailService();
				itemDetailVO = itemDetailSvc.updateItemDetail(itemSales, orderNo, itemNo);
				req.setAttribute("itemDetailVO", itemDetailVO);
				String url = "/backend/itemDetail/listAll_ItemDetail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/itemDetail/update_detail_input.jsp");
				failureView.forward(req, res);
			}
		}
//------------------以訂單編號查詢單一訂單所有明細--------------------------------		
		if("GetAllByOrderNo".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer orderNo = null;
				try {
					orderNo = new Integer(req.getParameter("orderNo").trim());
				}catch(NumberFormatException ONe) {
					errorMsgs.add("訂單編號請輸入數字");
				}
				
				ItemDetailService itemDetailSvc = new ItemDetailService();
				List<ItemDetailVO> list = new ArrayList<ItemDetailVO>();
				
				list = itemDetailSvc.GetAllByOrderNo(orderNo);
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/itemDetail/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				req.setAttribute("list", list);
				String url = "/backend/itemDetail/listOneOrderDetail.jsp";
				RequestDispatcher dispatcher = req.getRequestDispatcher(url);
				dispatcher.forward(req, res);
				
			}catch(Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/itemDetail/select_page.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
