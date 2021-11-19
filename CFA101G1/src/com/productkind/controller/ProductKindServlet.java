package com.productkind.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.admini.model.AdminiService;
import com.admini.model.AdminiVO;
import com.item.model.*;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.productkind.model.ProductKindService;
import com.productkind.model.ProductKindVO;

@MultipartConfig
public class ProductKindServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getAll".equals(action)) { // 來自listAllItem.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				ProductKindService productKindSvc = new ProductKindService();
				List<ProductKindVO> productKindVO = productKindSvc.getAllProductKind();
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("productKindVO", productKindVO); // 資料庫取出的itemVO物件,存入req
				String url = "/backend/item/update_item_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_item_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/listAllItem.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				String kindName = req.getParameter("kindName");

				if (kindName == null || kindName.trim().length() == 0) {
					errorMsgs.add("商品類別名稱: 請勿空白");
				} 
				ProductKindVO productKindVO = new ProductKindVO();
				
				productKindVO.setKindName(kindName);;

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productKindVO", productKindVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/productKind/addProductKind.jsp");
					failureView.forward(req, res);
					return;
				}				
				
				/***************************2.開始新增資料***************************************/
				ProductKindService productKindSvc = new ProductKindService();
				productKindVO = productKindSvc.addProductKind(kindName);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/productKind/listAllProductKind.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace(); //訊息
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/productKind/addProductKind.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
