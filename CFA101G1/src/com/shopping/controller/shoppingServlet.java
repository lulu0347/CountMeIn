package com.shopping.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.item.model.ItemVO;
import com.itemdetail.model.ItemDetailVO;
import com.itemorder.model.ItemOrderService;
import com.itemorder.model.ItemOrderVO;
import com.member.model.*;
import com.transRec.model.TransRecDAO_JDBC;
import com.transRec.model.TransRecDAO_interface;
import com.transRec.model.TransRecService;
import com.transRec.model.TransRecVO;


public class shoppingServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		// res.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = res.getWriter();

		HttpSession session = req.getSession();
		Vector<ItemVO> buylist = (Vector<ItemVO>) session.getAttribute("shoppingcart");
		String action = req.getParameter("action");

		if (!action.equals("CHECKOUT")) {

			// 刪除購物車中的商品
			if (action.equals("DELETE")) {
				String del = req.getParameter("del");
				int d = Integer.parseInt(del);
				buylist.removeElementAt(d);
				System.out.println("已刪除index為" + d +"的商品");
			}
			
			//清空購物車中的商品
			else if (action.equals("clear")) {
				buylist.removeAllElements();
				if(buylist.size() == 0) {
					System.out.println(buylist.size());
				}
			}
			
			// 新增商品至購物車中
			else if (action.equals("ADD")) {
				boolean match = false;
				// 取得後來新增的商品
				ItemVO aitemVO = getItem(req);
//				System.out.println("給照片Service抓照片用的測試用 req所取得的itemNo為 : " + aitemVO.getItemNo());
				// 新增第一個商品至購物車時
				if (buylist == null) {
					buylist = new Vector<ItemVO>();
					buylist.add(aitemVO);
				} else {
					for (int i = 0; i < buylist.size(); i++) {
						ItemVO itemVO = buylist.get(i);
						// 假若新增的商品和購物車的商品一樣時
						if (itemVO.getItemName().equals(aitemVO.getItemName())) {
							itemVO.setQuantity(itemVO.getQuantity() + aitemVO.getQuantity());
							buylist.setElementAt(itemVO, i);
							match = true;
						} // end of if name matches
					} // end of for
						// 假若新增的商品和購物車的商品不一樣時
					if (!match)
						buylist.add(aitemVO);
				}
			}

			session.setAttribute("shoppingcart", buylist);
			String url = "/frontend/EShop.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
			
		}
		
		// 結帳，計算購物車商品價錢總數
		else if (action.equals("CHECKOUT")) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);		
			
			List<ItemDetailVO> list = new ArrayList<ItemDetailVO>();
			
			Integer total = 0;
			 
			
			//跑迴圈將buylist中被選到的商品用迴圈取出,用order裝完後計算total
			for (int i = 0; i < buylist.size(); i++) {
				ItemDetailVO itemDetailVO  = new ItemDetailVO();
				ItemVO order = buylist.get(i);
				Integer price = order.getItemPrice();
				Integer quantity = order.getQuantity();
				total += (price * quantity);
				
//				System.out.println("測試CheckOut後從ShoppingServlet取得的itemNo為" + order.getItemNo());
//				System.out.println("------------以上為測試shoppingservlet取得itemNo是否正確---------------------");
				
				//跑迴圈取得buylist購物車內容分別取出被選到的商品
				itemDetailVO.setItemNo(order.getItemNo());
				itemDetailVO.setItemSales(order.getQuantity());
				itemDetailVO.setItemPrice(order.getItemPrice());
				list.add(itemDetailVO);
				
				
				
			}
			String amount = String.valueOf(total);
			req.setAttribute("amount", amount);
			
			MemberVO memVO = (MemberVO)session.getAttribute("user");
			
			Date dNow = new Date();
		    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		    java.sql.Timestamp sTimestamp = java.sql.Timestamp.valueOf(ft.format(dNow));
		    
		    StringBuilder sb = new StringBuilder();
            String receiverAddressCounty = null;
            String receiverAddressCity = null;
            String receiverAddressDetail = null;
            String receiverAddress = null;
		    try {
		    receiverAddressCounty = req.getParameter("receiverAddressCounty");
            receiverAddressCity = req.getParameter("receiverAddressCity");
            receiverAddressDetail = req.getParameter("receiverAddressDetail");
           
            
            sb.append(receiverAddressCounty);

            sb.append(receiverAddressCity);

            sb.append(receiverAddressDetail);

            receiverAddress = sb.toString(); 
		    }catch(Exception e) {
		    	System.out.println("錯誤");
		    }
			String receiverName = req.getParameter("receiverName");			
			String receiverPhone = req.getParameter("receiverPhone");
			
			ItemOrderVO itemOrderVO = new ItemOrderVO();
			itemOrderVO.setMemNo(memVO.getMemno());
			itemOrderVO.setTranTime(sTimestamp);
			itemOrderVO.setOrderTotal(total);
			itemOrderVO.setOrderState(0);
			itemOrderVO.setReceiverName(receiverName);
			itemOrderVO.setReceiverAddress(receiverAddress);
			itemOrderVO.setReceiverPhone(receiverPhone);
			
			ItemOrderService itemOrderSvc = new ItemOrderService();
			TransRecVO transRecVO = itemOrderSvc.getTransRecVO(itemOrderVO);
			TransRecDAO_interface transRecDao = new TransRecDAO_JDBC();
			transRecDao.add(transRecVO);
		
			String finalCheck = insertData(total, memVO, itemOrderVO, list, req, transRecVO);
			session.setAttribute("shoppingcheckout", session.getAttribute("shoppingcart"));
			session.removeAttribute("shoppingcart");
				
			if(finalCheck.equals("ok")) {
				String url = "/frontend/Checkout.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			}else if(finalCheck.equals("false")) {
				String url = "/frontend/transRec/saveMoney.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			}
		}
			
		
		
	}
	
	private ItemVO getItem(HttpServletRequest req) {
		
		String quantity = req.getParameter("quantity");
		String itemName = req.getParameter("itemName");
		String itemPrice = req.getParameter("itemPrice");
		String warrantyDate = req.getParameter("warrantyDate");
		String itemNo = req.getParameter("itemNo");
		
		ItemVO itemVO = new ItemVO();
		
		itemVO.setItemName(itemName);
		itemVO.setQuantity(new Integer(quantity));
		itemVO.setItemPrice(new Integer(itemPrice));
		itemVO.setWarrantyDate(new Double(warrantyDate));
		itemVO.setItemNo(new Integer(itemNo));
		
		return itemVO;
	}
	
	private String insertData(Integer total, MemberVO memVO, ItemOrderVO itemOrderVO, List<ItemDetailVO> list, HttpServletRequest req, TransRecVO transRecVO) {
		
		ItemOrderService itemOrderSvc = new ItemOrderService();
		TransRecService transRecSvc=new TransRecService();
		Integer money = transRecSvc.getDeposit(memVO.getMemno());
		System.out.println(total);
		System.out.println(money);
		Integer cost = money - total;
		
		if(cost < 0) {
			req.setAttribute("check", "fail");
			return "false";
		}

		else {
			//更新會員付款後的Ecash
//			MemberService MemSvc = new MemberService();
//			MemSvc.updateEcash(memVO.getMemno(), cost);
			
			//更新訂單、明細跟錢包交易紀錄
			itemOrderSvc.insertWithItemDetails(itemOrderVO, list, transRecVO);
			req.setAttribute("check", "success");
			return "ok";
		}
	}
}
