package com.itemcollection.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.itemcollection.model.*;
import com.member.model.MemberVO;
import com.item.model.*;
import jdbc.Util.CompositeQuery.jdbcUtil_CompositeQuery_Item;

public class ItemCollectionServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//－－－－－－－－－－－－－以會員編號列出該會員所持有的所有收藏
		if ("listByMemNo".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				Integer memNo = null;
				try {
					memNo = new Integer(req.getParameter("memNo").trim());
				} catch (NumberFormatException MNe) {
					errorMsgs.add("請填入數字");
				}
				if (memNo >= 12000 || memNo <= 11000) {
					errorMsgs.add("請填入區間為11999-11001的會員編號");
				}

				ItemCollectionService ICS = new ItemCollectionService();
				List<ItemCollectionVO> list = new ArrayList<ItemCollectionVO>();

				list = ICS.getOneCollection(memNo);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/itemCollection/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				req.setAttribute("list", list);
				String url = "/backend/itemCollection/listOneCollection.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/itemCollection/select_page.jsp");
				failureView.forward(req, res);
			}
		}
//－－－－－－－－－－－－－更新一個會員所持有的單一收藏商品（沒什麼用）
		if ("getOne_For_Update".equals(action)) { // 來自listAllItemCollection.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer memNo = new Integer(req.getParameter("memNo"));

				ItemCollectionService itemCollectionSvc = new ItemCollectionService();
				ItemCollectionVO itemCollectionVO = itemCollectionSvc.findOneByMemNo(memNo);

				req.setAttribute("itemCollectionVO", itemCollectionVO); // 資料庫取出的itemVO物件,存入req
				String url = "/backend/itemCollection/update_itemCollection_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/itemCollection/listAllItemCollection.jsp");
				failureView.forward(req, res);
			}

		}

//－－－－－－－－－－－－－更新一個會員所持有的單一收藏商品（沒什麼用）
		if ("update".equals(action)) { // 來自update_itemCollection_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer memNo = new Integer(req.getParameter("memNo"));
				try {

				} catch (NumberFormatException MNe) {
					memNo = 0;
					errorMsgs.add("請填入會員編號");
				}
				if (memNo == null || memNo < 11000) {
					errorMsgs.add("請輸入會員編號從11000開始");
				} else if (memNo >= 12000) {
					errorMsgs.add("哪來這麼多人啊?");
				}

				Integer itemNo = null;
				try {
					itemNo = new Integer(req.getParameter("itemNo"));

				} catch (NumberFormatException INe) {
					itemNo = 0;
					errorMsgs.add("商品編號請填數字.");
				}
				if (itemNo == null || itemNo < 21000) {
					errorMsgs.add("請輸入商品編號為21001開始");
				} else if (itemNo >= 22000) {
					errorMsgs.add("沒這麼多商品=_=a");
				}

				ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
				itemCollectionVO.setMemNo(memNo);
				itemCollectionVO.setItemNo(itemNo);
				/*************************** 2.開始修改資料 ****************************************/
				ItemCollectionService itemCollectionSvc = new ItemCollectionService();
				itemCollectionVO = itemCollectionSvc.updateCollection(memNo, itemNo);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("itemCollectionVO", itemCollectionVO); // 資料庫取出的itemCollectionVO物件,存入req
				String url = "/backend/itemCollection/listOneCollection.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 listOneCollection.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/itemCollection/update_itemCollection_input.jsp.jsp");
				failureView.forward(req, res);
			}
		}

//－－－－－－－－－－－－－－－－後端新增會員收藏列表
		if ("insert".equals(action)) { // 來自addItemCollection.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer memNo = null;
				try {
					// 接受請求參數----檢查資料格式
					memNo = new Integer(req.getParameter("memNo"));
				} catch (NumberFormatException MNe) {
					errorMsgs.add("請輸入數字唷~");
				}
				if (memNo == null) {
					errorMsgs.add("沒取到值唷");
				} else if (memNo <= 11000 || memNo >= 12000) {
					errorMsgs.add("請輸入11001-11999");
				}

				Integer itemNo = null;

				try {
					itemNo = new Integer(req.getParameter("itemNo"));
				} catch (NumberFormatException INe) {
					errorMsgs.add("請輸入數字唷~");
				}
				if (itemNo == null) {
					errorMsgs.add("沒取到值唷");
				} else if (itemNo <= 21000 || itemNo >= 22000) {
					errorMsgs.add("請輸入21001-21999");
				}

				ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
				itemCollectionVO.setMemNo(memNo);
				itemCollectionVO.setItemNo(itemNo);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("itemCollectionVO", itemCollectionVO);
					String url = "/backend/itemCollection/addItemCollection.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				ItemCollectionService itemCollectionSvc = new ItemCollectionService();
				itemCollectionVO = itemCollectionSvc.addCollection(memNo, itemNo);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/backend/itemCollection/listAllItemCollection.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFavorite.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/itemCollection/addItemCollection.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)刪除一個商品到收藏列表 For EShop---------------------------------
		if ("delete".equals(action)) { // 來自listAllItemCollection.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer itemNo = new Integer(req.getParameter("itemNo"));
				Integer memNo = new Integer(req.getParameter("memNo"));
				/*************************** 2.開始刪除資料 ***************************************/
				ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
				ItemCollectionSvc.deleteCollection(itemNo, memNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/frontend/listAllCollectionByMemNo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listAllCollectionByMemNo.jsp");
				failureView.forward(req, res);
			}
		}
//---------------------(前端)新增一個商品到收藏列表  For EShop---------------------------------
		if ("addCollection".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			HttpSession session = req.getSession();
			MemberVO memVO = (MemberVO) session.getAttribute("user");
			try {

				Integer memNo = new Integer(req.getParameter("memNo"));

				Integer itemNo = new Integer(req.getParameter("itemNo"));

				ItemCollectionService itemCollectionSvc = new ItemCollectionService();

				int collect = itemCollectionSvc.getcount(itemNo, memNo);

				if (collect == 0) {
					ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
					itemCollectionVO.setMemNo(memNo);
					itemCollectionVO.setItemNo(itemNo);
					itemCollectionVO = itemCollectionSvc.addCollection(memNo, itemNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				else if (collect == 1) {
					ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
					ItemCollectionSvc.deleteCollection(itemNo, memNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				String url = "/frontend/EShop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFavorite.jsp
				successView.forward(req, res);
			} catch (RuntimeException e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/EShop.jsp");
				failureView.forward(req, res);
			}
		}
//---------------------(前端)刪除一個商品到收藏列表 For listByPhone---------------------------------
		if ("deleteforPhone".equals(action)) { // 來自listAllItemCollection.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer itemNo = new Integer(req.getParameter("itemNo"));
				Integer memNo = new Integer(req.getParameter("memNo"));

				/*************************** 2.開始刪除資料 ***************************************/
				ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
				ItemCollectionSvc.deleteCollection(itemNo, memNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/frontend/listByPhone.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listByPhone.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)新增一個商品到收藏列表  For listByPhone---------------------------------
		if ("addCollectionforPhone".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			HttpSession session = req.getSession();
			MemberVO memVO = (MemberVO) session.getAttribute("user");
			try {

				Integer memNo = new Integer(req.getParameter("memNo"));

				Integer itemNo = new Integer(req.getParameter("itemNo"));

				ItemCollectionService itemCollectionSvc = new ItemCollectionService();

				int collect = itemCollectionSvc.getcount(itemNo, memNo);

				if (collect == 0) {
					ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
					itemCollectionVO.setMemNo(memNo);
					itemCollectionVO.setItemNo(itemNo);
					itemCollectionVO = itemCollectionSvc.addCollection(memNo, itemNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				else if (collect == 1) {
					ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
					ItemCollectionSvc.deleteCollection(itemNo, memNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				String url = "/frontend/listByPhone.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFavorite.jsp
				successView.forward(req, res);
			} catch (RuntimeException e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listByPhone.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)刪除一個商品到收藏列表 For listByComputer---------------------------------
		if ("deleteforComputer".equals(action)) { // 來自listAllItemCollection.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer itemNo = new Integer(req.getParameter("itemNo"));
				Integer memNo = new Integer(req.getParameter("memNo"));

				/*************************** 2.開始刪除資料 ***************************************/
				ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
				ItemCollectionSvc.deleteCollection(itemNo, memNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/frontend/listByComputer.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listByComputer.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)新增一個商品到收藏列表  For listByComputer---------------------------------
		if ("addCollectionforComputer".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			HttpSession session = req.getSession();
			MemberVO memVO = (MemberVO) session.getAttribute("user");
			try {

				Integer memNo = new Integer(req.getParameter("memNo"));

				Integer itemNo = new Integer(req.getParameter("itemNo"));

				ItemCollectionService itemCollectionSvc = new ItemCollectionService();

				int collect = itemCollectionSvc.getcount(itemNo, memNo);

				if (collect == 0) {
					ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
					itemCollectionVO.setMemNo(memNo);
					itemCollectionVO.setItemNo(itemNo);
					itemCollectionVO = itemCollectionSvc.addCollection(memNo, itemNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				else if (collect == 1) {
					ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
					ItemCollectionSvc.deleteCollection(itemNo, memNo);
					itemCollectionSvc.getcount(itemNo, memNo);
//							System.out.println("點擊之後刪除為" + collect);
				}

				String url = "/frontend/listByComputer.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFavorite.jsp
				successView.forward(req, res);
			} catch (RuntimeException e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listByComputer.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)刪除一個商品到收藏列表 For listByCamera---------------------------------
		if ("deleteforCamera".equals(action)) { // 來自listAllItemCollection.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer itemNo = new Integer(req.getParameter("itemNo"));
				Integer memNo = new Integer(req.getParameter("memNo"));

				/*************************** 2.開始刪除資料 ***************************************/
				ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
				ItemCollectionSvc.deleteCollection(itemNo, memNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/frontend/listByCamera.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listByCamera.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)新增一個商品到收藏列表  For listByCamera---------------------------------
		if ("addCollectionforCamera".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			HttpSession session = req.getSession();
			MemberVO memVO = (MemberVO) session.getAttribute("user");
			try {

				Integer memNo = new Integer(req.getParameter("memNo"));

				Integer itemNo = new Integer(req.getParameter("itemNo"));

				ItemCollectionService itemCollectionSvc = new ItemCollectionService();

				int collect = itemCollectionSvc.getcount(itemNo, memNo);

				if (collect == 0) {
					ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
					itemCollectionVO.setMemNo(memNo);
					itemCollectionVO.setItemNo(itemNo);
					itemCollectionVO = itemCollectionSvc.addCollection(memNo, itemNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				else if (collect == 1) {
					ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
					ItemCollectionSvc.deleteCollection(itemNo, memNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				String url = "/frontend/listByCamera.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFavorite.jsp
				successView.forward(req, res);
			} catch (RuntimeException e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listByCamera.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)刪除一個商品到收藏列表 For listByWatch---------------------------------
		if ("deleteforphoneforWatch".equals(action)) { // 來自listAllItemCollection.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer itemNo = new Integer(req.getParameter("itemNo"));
				Integer memNo = new Integer(req.getParameter("memNo"));

				/*************************** 2.開始刪除資料 ***************************************/
				ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
				ItemCollectionSvc.deleteCollection(itemNo, memNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/frontend/listByWatch.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listByWatch.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)新增一個商品到收藏列表  For listByWatch---------------------------------
		if ("addCollectionforWatch".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			HttpSession session = req.getSession();
			MemberVO memVO = (MemberVO) session.getAttribute("user");
			try {

				Integer memNo = new Integer(req.getParameter("memNo"));

				Integer itemNo = new Integer(req.getParameter("itemNo"));

				ItemCollectionService itemCollectionSvc = new ItemCollectionService();

				int collect = itemCollectionSvc.getcount(itemNo, memNo);

				if (collect == 0) {
					ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
					itemCollectionVO.setMemNo(memNo);
					itemCollectionVO.setItemNo(itemNo);
					itemCollectionVO = itemCollectionSvc.addCollection(memNo, itemNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				else if (collect == 1) {
					ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
					ItemCollectionSvc.deleteCollection(itemNo, memNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				String url = "/frontend/listByWatch.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFavorite.jsp
				successView.forward(req, res);
			} catch (RuntimeException e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listByWatch.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)刪除一個商品到收藏列表 For listByOthers---------------------------------
		if ("deleteforOthers".equals(action)) { // 來自listAllItemCollection.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer itemNo = new Integer(req.getParameter("itemNo"));
				Integer memNo = new Integer(req.getParameter("memNo"));

				/*************************** 2.開始刪除資料 ***************************************/
				ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
				ItemCollectionSvc.deleteCollection(itemNo, memNo);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/frontend/listByOthers.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listByOthers.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)新增一個商品到收藏列表  For listByOther---------------------------------
		if ("addCollectionforOthers".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			HttpSession session = req.getSession();
			MemberVO memVO = (MemberVO) session.getAttribute("user");
			try {

				Integer memNo = new Integer(req.getParameter("memNo"));

				Integer itemNo = new Integer(req.getParameter("itemNo"));

				ItemCollectionService itemCollectionSvc = new ItemCollectionService();

				int collect = itemCollectionSvc.getcount(itemNo, memNo);

				if (collect == 0) {
					ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
					itemCollectionVO.setMemNo(memNo);
					itemCollectionVO.setItemNo(itemNo);
					itemCollectionVO = itemCollectionSvc.addCollection(memNo, itemNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				else if (collect == 1) {
					ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
					ItemCollectionSvc.deleteCollection(itemNo, memNo);
					itemCollectionSvc.getcount(itemNo, memNo);
				}

				String url = "/frontend/listByOthers.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFavorite.jsp
				successView.forward(req, res);
			} catch (RuntimeException e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listByOthers.jsp");
				failureView.forward(req, res);
			}
		}

//---------------------(前端)新增一個商品到收藏列表  For 模糊搜尋---------------------------------
		if ("addCollection2".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			HttpSession session = req.getSession();
			MemberVO memVO = (MemberVO) session.getAttribute("user");
			try {
				Integer memNo = new Integer(req.getParameter("memNo"));

				Integer itemNo = new Integer(req.getParameter("itemNo"));

				ItemCollectionService itemCollectionSvc = new ItemCollectionService();

				int collect = itemCollectionSvc.getcount(itemNo, memNo);

				if (collect == 0) {
					ItemCollectionVO itemCollectionVO = new ItemCollectionVO();
					itemCollectionVO.setMemNo(memNo);
					itemCollectionVO.setItemNo(itemNo);
					itemCollectionVO = itemCollectionSvc.addCollection(memNo, itemNo);
					itemCollectionSvc.getcount(itemNo, memNo);
					Map<String, String[]> map = req.getParameterMap();

					ItemService itemSvc = new ItemService();
					List<ItemVO> list = itemSvc.getAll(map);
					req.setAttribute("listItem_ByCompositeQuery", list);
					String url = "/frontend/EShop.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFavorite.jsp
					successView.forward(req, res);

				} else if (collect == 1) {
					ItemCollectionService ItemCollectionSvc = new ItemCollectionService();
					ItemCollectionSvc.deleteCollection(itemNo, memNo);
					itemCollectionSvc.getcount(itemNo, memNo);
					Map<String, String[]> map = req.getParameterMap();

					ItemService itemSvc = new ItemService();
					List<ItemVO> list = itemSvc.getAll(map);
					req.setAttribute("listItem_ByCompositeQuery", list);
					String url = "/frontend/EShop.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFavorite.jsp
					successView.forward(req, res);
				}

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/listItem_ByCompositeQuery.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
