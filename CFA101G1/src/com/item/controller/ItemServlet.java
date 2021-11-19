package com.item.controller;

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

import com.item.model.*;

@MultipartConfig
public class ItemServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("itemNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入商品編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/listOneItem2.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer itemNo = null;
				try {
					itemNo = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("商品編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/listOneItem2.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				System.out.println(itemNo);
				/*************************** 2.開始查詢資料 *****************************************/
				ItemService itemSvc = new ItemService();
				ItemVO itemVO = itemSvc.getOneItem(itemNo);
				if (itemVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/listOneItem2.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("itemVO", itemVO); // 資料庫取出的itemVO物件,存入req
				String url = "/backend/item/listOneItem2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneItem.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/listOneItem2.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllItem.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer itemNo = new Integer(req.getParameter("itemNo"));

				/*************************** 2.開始查詢資料 ****************************************/
				ItemService itemSvc = new ItemService();
				ItemVO itemVO = itemSvc.getOneItem(itemNo);
				System.out.println(itemVO.getSoldTime());

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("itemVO", itemVO); // 資料庫取出的itemVO物件,存入req
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

		if ("update".equals(action)) { // 來自update_item_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				Integer itemNo = new Integer(req.getParameter("itemNo").trim());
				System.out.println(itemNo);

				Integer kindNo = new Integer(req.getParameter("kindNo").trim());
				System.out.println(kindNo);
				String itemName = req.getParameter("itemName");
				String itemNameReg = "^[(\\-,./)(><*\\)\\s\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,40}$";
				if (itemName == null || itemName.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
					}
				 else if (!itemName.trim().matches(itemNameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到40之間");
				}
				System.out.println(itemName);

				Integer itemPrice = null;

				try {
					itemPrice = new Integer(req.getParameter("itemPrice").trim());

				} catch (NumberFormatException IPe) {
					itemPrice = 0;
					errorMsgs.add("商品價格請填數字.");
				}
				if (itemPrice == null || itemPrice <= 0) {
					errorMsgs.add("價格請填入大於0");
				} else if (itemPrice > 99999) {
					errorMsgs.add("價格請填5位數");
				}
				System.out.println(itemPrice);
				Integer itemState = null;

				try {
					itemState = new Integer(req.getParameter("itemState"));
				} catch (NumberFormatException ISe) {
					itemState = 1;
					errorMsgs.add("商品狀態請填數字");
				}
				if (itemState > 1 || itemState < 0) {
					errorMsgs.add("上架狀態為0或1");
				}
				System.out.println(itemState);
				java.sql.Timestamp launchedTime = null;

				try {
					launchedTime = java.sql.Timestamp.valueOf(req.getParameter("launchedTime").trim());

				} catch (IllegalArgumentException LTe) {
					launchedTime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入上架時間!");
				}
				System.out.println(launchedTime);
				java.sql.Timestamp soldTime = null;

				try {
					soldTime = java.sql.Timestamp.valueOf(req.getParameter("soldTime"));

				} catch (IllegalArgumentException STe) {
//					soldTime = new java.sql.Timestamp(System.currentTimeMillis()+60000);
//					errorMsgs.add("請輸入下架時間");
				}
				System.out.println(soldTime);
				
				
				Double warrantyDate = null;

				try {
					warrantyDate = new Double(req.getParameter("warrantyDate").trim());
				} catch (NumberFormatException WDe) {
					warrantyDate = 0.0;
					errorMsgs.add("商品保固年限請填數字.");
				}
				if (warrantyDate < 0) {
					errorMsgs.add("保固年限不得低於0");
				}
				System.out.println(warrantyDate);
				String itemProdDescription = req.getParameter("itemProdDescription");
				if (itemProdDescription == null || itemProdDescription.trim().length() == 0) {
					errorMsgs.add("商品描述: 請勿空白");
					}
//				String itemProdDescriptionReg = "^[(\\\\-,./)(><*\\\\)\\\\s\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{1,1000}$";
//				if (itemProdDescription == null || itemProdDescription.trim().length() == 0) {
//						errorMsgs.add("商品描述: 請勿空白");
//				} else if (!itemProdDescription.trim().matches(itemProdDescriptionReg)) { // 以下練習正則(規)表示式(regular-expression)
//						errorMsgs.add("商品描述: 只能是中、英文字母、數字和_ , 且長度必需在1到1000之間");
//				}
				System.out.println(itemProdDescription);
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

//				for (int i = 0; i < errorMsgs.size(); i++) {
//                    System.out.println(errorMsgs.get(i));
//                }

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("itemVO", itemVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/update_item_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				ItemService itemSvc = new ItemService();
				itemVO = itemSvc.updateItem(itemNo, kindNo, itemName, itemPrice, itemState, soldTime,launchedTime,
						warrantyDate, itemProdDescription);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("itemVO", itemVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/backend/item/listAllItem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/update_item_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("addUsed".equals(action)) { // 來自addItem.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				Integer kindNo = new Integer(req.getParameter("kindNo").trim());

				String itemName = req.getParameter("itemName");
				String itemNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,40}$";
				if (itemName == null || itemName.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白且且長度必需在2以上");
				} 
				else if (!itemName.trim().matches(itemNameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字 , 且長度必需在2到40之間");
				}

				Integer itemPrice = new Integer(req.getParameter("itemPrice").trim());
				try {
					itemPrice = new Integer(req.getParameter("itemPrice").trim());
				} catch (NumberFormatException IPe) {
					itemPrice = 0;
					errorMsgs.add("商品價格請填數字");
				}
				if (itemPrice == null || itemPrice <= 0) {
					errorMsgs.add("價格請填入大於0");
				} else if (itemPrice > 99999) {
					errorMsgs.add("價格請填5位數以內");
				}

				Integer itemState = null;
				try {
					itemState = new Integer(req.getParameter("itemState").trim());
				} catch (NumberFormatException e) {
					itemState = 0;
					errorMsgs.add("上架狀態請填數字");
				}
				if (itemState > 1 || itemState < 0) {
					errorMsgs.add("上架狀態為0或1");
				}else if(itemState ==null) {
					errorMsgs.add("必須設定商品狀態");
				}

				java.sql.Timestamp launchedTime = null;
				try {
					launchedTime = java.sql.Timestamp.valueOf(req.getParameter("launchedTime"));
				} catch (IllegalArgumentException LTe) {
					launchedTime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入上架時間!");
				}

				Double warrantyDate = null;
				try {
					warrantyDate = new Double(req.getParameter("warrantyDate").trim());
				} catch (NumberFormatException WDe) {
					warrantyDate = 0.0;
					errorMsgs.add("商品保固年限請填數字");
				}
				if (warrantyDate == null) {
					errorMsgs.add("商品必須得有保固年限");
				}

				String itemProdDescription = req.getParameter("itemProdDescription");
//				String itemProdDescriptionReg = "^[(\\-,><\\)(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,1000}$";
				if (itemProdDescription == null || itemProdDescription.trim().length() == 0) {
					errorMsgs.add("商品描述: 請勿空白");
				} 
//				else if (!itemProdDescription.trim().matches(itemProdDescriptionReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("商品描述: 只能是中、英文字母、數字和_ , 且長度必需在1到1000之間");
//				}

				ItemVO itemVO = new ItemVO();
				itemVO.setKindNo(kindNo);
				itemVO.setItemName(itemName);
				itemVO.setItemPrice(itemPrice);
				itemVO.setItemState(itemState);
				itemVO.setLaunchedTime(launchedTime);
				itemVO.setWarrantyDate(warrantyDate);
				itemVO.setItemProdDescription(itemProdDescription);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("itemVO", itemVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/addItem.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				ItemService itemSvc = new ItemService();
				List<Part> list = (List<Part>) req.getParts();
				List<byte[]> listPic = new ArrayList<byte[]>();
				for (Part part : list) {

					InputStream in = part.getInputStream();
					if (in.available() > 30) {
						byte[] b = new byte[in.available()];
						in.read(b);
						listPic.add(b);
					}
				}

				System.out.println("ItemServlet取得的listPic為 : " + listPic);
				itemSvc.addItem2(kindNo, itemName, itemPrice, itemState, launchedTime, warrantyDate,
						itemProdDescription, listPic);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = null;
				if ("addUsed".equals(action))
					url = "/backend/item/listAllItem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllItem.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/addItem.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllItem.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer itemNo = new Integer(req.getParameter("itemNo"));

				/*************************** 2.開始刪除資料 ***************************************/
				ItemService itemSvc = new ItemService();
				itemSvc.deleteItem(itemNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/backend/item/listAllItem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/item/deleteItem.jsp");
				failureView.forward(req, res);
			}
		}

//-------------模糊搜尋------------------------------------
		if ("listItem_ByCompositeQuery".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("取得的ItemName為 : ");
			System.out.println(req.getParameter("ItemName"));
			System.out.println("-------------------");
			try {
				Map<String, String[]> map = req.getParameterMap();
				System.out.println("從ITServlet來的:"+map);
				ItemService itemSvc = new ItemService();
				List<ItemVO> list = itemSvc.getAll(map);
				
				if(list.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/EShop.jsp");
					failureView.forward(req, res);
				}
			
				req.setAttribute("listItem_ByCompositeQuery", list);
				RequestDispatcher successView = req.getRequestDispatcher("/frontend/listItem_ByCompositeQuery.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
			}
		}
//---------------查看商品詳情--------------------------------		
		if("getOneItemForView".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				Integer itemNo = new Integer(req.getParameter("itemNo"));
				
				ItemService itemSvc = new ItemService();
				
				ItemVO itemVO = itemSvc.getOneItem(itemNo);
				
				req.setAttribute("itemVO", itemVO);
				String url = "/frontend/listOneItem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
			}catch(NullPointerException NPe) {
				System.out.println("沒取到啦");
			}
		}
	}
}
