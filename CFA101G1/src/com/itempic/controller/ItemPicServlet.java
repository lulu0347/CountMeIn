package com.itempic.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.itempic.model.*;
import com.item.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class ItemPicServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//----------------顯示圖片------------------------
		if ("showItemPic".equals(action)) {

			ItemPicDAO dao = new ItemPicDAO();

			byte[] itemPic = dao.getItemPic(new Integer(req.getParameter("itemPicNo")));

			res.setContentType("image/*");
			ServletOutputStream out = res.getOutputStream();

			out.write(itemPic);
			out.close();
		}
// ----------------顯示圖片------------------------
		if ("showItemPic2".equals(action)) {

			ItemPicDAO dao = new ItemPicDAO();

			byte[] itemPic = dao.getItemPic2(new Integer(req.getParameter("itemNo")));

			res.setContentType("image/*");
			ServletOutputStream out = res.getOutputStream();

			out.write(itemPic);
			out.close();
		}

//----------------新增圖片------------------------
		if ("addPic".equals(action)) {
			ItemPicVO itemPicVO = new ItemPicVO();
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				Integer itemNo = new Integer(req.getParameter("itemNo"));
//				System.out.println("line55取得的itemNo為" + itemNo);

				Part part = req.getPart("itemPic");

				if (part != null) {
					InputStream is = part.getInputStream();
					if (is.available() == 0) {
						errorMsgs.add("圖片來源或格式不正確");
					}

					byte[] itemPic = new byte[is.available()];
					is.read(itemPic);
//					System.out.println("line67取得的itempic為" + itemPic);
					is.close();
					itemPicVO.setItemPic(itemPic);
				}

				itemPicVO.setItemNo(itemNo);

				ItemPicService itemPicSvc = new ItemPicService();
				itemPicVO = itemPicSvc.addPic(itemPicVO.getItemNo(), itemPicVO.getItemPic());

				String url = "/backend/itemPic/listAll_ItemPic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("其他錯誤發生");
				e.printStackTrace();
			}
		}

//------------------依商品照片編號查詢並進入更新--------------------------------
		if ("findByItemPicNoToUpdate".equals(action)) { // 從listAll或listOne傳入並重導到update頁面裡

			Integer itemPicNo = new Integer(req.getParameter("itemPicNo"));
			ItemPicService itemPicSvc = new ItemPicService();
			ItemPicVO itemPicVO = itemPicSvc.findByItemPicNo(itemPicNo);
			try {

				req.setAttribute("itemPicVO", itemPicVO);

//				System.out.println("from line 117" + itemPicVO);

				String url = "/backend/itemPic/update_item_photo_input.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//------------------更新商品照片--------------------------------		
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			Integer itemPicNo = new Integer(req.getParameter("itemPicNo"));
			System.out.println(itemPicNo);
			ItemPicService itemPicSvc = new ItemPicService();
			ItemPicVO itemPicVO = itemPicSvc.findByItemPicNo(itemPicNo);
			

			try {

				Integer itemNo = new Integer(req.getParameter("itemNo"));
				System.out.println(itemNo);
				
				byte[] itemPic = null;
				Part part = req.getPart("itemPic");

				if (part != null) {
					InputStream is = part.getInputStream();
					itemPic = new byte[is.available()];
					is.read(itemPic);
					is.close();
				}

				if (itemPic.length != 0) {
					itemPicVO.setItemPic(itemPic);
				}

				itemPicVO.setItemNo(itemNo);

			} catch (Exception e) {
				errorMsgs.add("其他錯誤發生");
				e.printStackTrace();
			}

			if (errorMsgs.isEmpty()) {

				itemPicSvc.updatePic(itemPicVO.getItemPicNo(), itemPicVO.getItemNo(), itemPicVO.getItemPic());

				req.setAttribute("itemPicVO", itemPicVO);
				String url = "/backend/itemPic/listAll_ItemPic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} else {
				req.setAttribute("itemPicVO", itemPicVO);
				String url = "/backend/itemPic/update_item_photo_input.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);

			}

		}

//------------------刪除商品照片--------------------------------
		if ("deletePic".equals(action)) {

			try {

				Integer itemPicNo = new Integer(req.getParameter("itemPicNo"));

				ItemPicService itemPicSvc = new ItemPicService();
				itemPicSvc.deletePic(itemPicNo);

				String url = "/backend/itemPic/listAll_ItemPic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {

				System.out.println("刪除失敗");
				e.printStackTrace();
			}

		}
//------------------依商品照片編號查詢該商品資訊--------------------------------		
		if ("findByItemPicNo".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer itemPicNo = null;
				try {
					itemPicNo = new Integer(req.getParameter("itemPicNo"));
				} catch (NumberFormatException IPNe) {
					errorMsgs.add("商品照片編號請輸入數字");
				}
				if (itemPicNo >= 23000 || itemPicNo <= 22000) {
					errorMsgs.add("請輸入區間為22001到22999區間的數字唷");
				}

				if (!errorMsgs.isEmpty()) {
					String url = "/backend/item.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}

				ItemPicService itemPicSvc = new ItemPicService();
				ItemPicVO itemPicVO = itemPicSvc.findByItemPicNo(itemPicNo);

				req.setAttribute("itemPicVO", itemPicVO);
				String url = "/backend/itemPic/listOneItemPic.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/item.jsp");
				failureView.forward(req, res);
			}
		}

//------------------依商品編號查詢該商品照片--------------------------------	
		if ("findByItemNo".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer itemNo = null;
				try {
					itemNo = new Integer(req.getParameter("itemNo"));
//				System.out.println("line229所取得的商品編號為  " + itemNo);
				} catch (NumberFormatException INe) {
					errorMsgs.add("商品編號請輸入數字");
				}
				if (itemNo >= 22000 || itemNo < 21000) {
					errorMsgs.add("請輸入商品編號區間為21001至21999的數字");
				}
				ItemPicService itemPicSvc = new ItemPicService();
				List<ItemPicVO> list = new ArrayList<ItemPicVO>();

				list = itemPicSvc.findByItemNo(itemNo);
//				System.out.println("line240所取得的商品編號為  " + itemNo);
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/item.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				req.setAttribute("list", list);
				String url = "/backend/itemPic/listByItemNo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/item.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
