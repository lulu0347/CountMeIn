package com.auctOrder.controller;

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.auctActProd.model.AuctActProdService;
import com.auctActProd.model.AuctActProdVO;
import com.auctOrder.model.AuctOrdService;
import com.auctOrder.model.AuctOrdVO;
import com.auctOrderDetl.model.AuctOrdDetlService;
import com.auctOrderDetl.model.AuctOrdDetlVO;
import com.auctProd.model.AuctProdService;
import com.auctProd.model.AuctProdVO;
import com.google.gson.Gson;
import com.member.model.MemberVO;
import com.transRec.model.TransRecService;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

public class AuctOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuctActProdService auctProdSvc = new AuctActProdService();
	private AuctProdService auctProd = new AuctProdService();
	private TransRecService transRecSvc=new TransRecService();
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("action: " + action);

		HttpSession session = req.getSession();
		MemberVO memVO = (MemberVO) session.getAttribute("user");
		boolean memVOIsNull = memVO == null;
		Integer memNo = null;
		if (!memVOIsNull) {
			memNo = memVO.getMemno();
		}
		
		List<String> memLoginNeedPage = new ArrayList();
		memLoginNeedPage.add("displayCart");
		memLoginNeedPage.add("ajaxAddProd");
		memLoginNeedPage.add("checkOut");
		memLoginNeedPage.add("getOneDetl");
		memLoginNeedPage.add("shipment");
		memLoginNeedPage.add("cancel");
		memLoginNeedPage.add("delCartProd");
		memLoginNeedPage.add("ajaxNum");
		//contain??????+!  =>?????????
		if(memVOIsNull && !memLoginNeedPage.contains(action)) {
			//session??????????????????????????????????????????
			String ordRecurl = "/frontend/auctAct/auctActIndex.jsp";
			RequestDispatcher failView = req.getRequestDispatcher(ordRecurl);
			failView.forward(req, res);
			return;
		}

		// ?????????????????????>>?????????????????????
		// ????????????SESSION???????????????????????????cartList?????????????????????????????????????????????

		// ????????????????????????
		Map<Integer, AuctOrdDetlVO> auctCart = (Map<Integer, AuctOrdDetlVO>) session.getAttribute("auctCart");

		// ?????????????????????????????????
		if ("getOneDetl".equals(action)) {
			String auctOrdNoStr = req.getParameter("auctOrdNo");
			Integer auctOrdNo = new Integer(auctOrdNoStr);

			AuctOrdDetlService auctDetlSvc = new AuctOrdDetlService();
			List<AuctOrdDetlVO> auctOrdDetlList = auctDetlSvc.getbyProdNpOrdNo(auctOrdNo);

			req.setAttribute("auctOrdDetlList", auctOrdDetlList);
			String ordFDetlurl = "/backend/auct/auctOrder/orderRecDetl.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(ordFDetlurl);
			successView.forward(req, res);
		}

		// ?????????????????????????????????
		if ("getOneProdDetl".equals(action)) {
			String auctOrdNoStr = req.getParameter("auctOrdNo");
			Integer auctOrdNo = new Integer(auctOrdNoStr);

			AuctOrdDetlService auctDetlSvc = new AuctOrdDetlService();
			List<AuctOrdDetlVO> auctOrdDetlList = auctDetlSvc.getbyProdNpOrdNo(auctOrdNo);
			// ??????????????????


			Map<Integer, String> nameMap = generateProdNameMap();
			req.setAttribute("nameMap", nameMap);

			req.setAttribute("auctOrdDetlList", auctOrdDetlList);
			String ordFDetlurl = "/frontend/auctAct/orderRecDetl_front.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(ordFDetlurl);
			successView.forward(req, res);
		}

		// ??????/??????????????????????????????
		// ??????????????????
		if ("shipment".equals(action)) {
			String auctOrdNoStr = req.getParameter("auctOrdNo");
			Integer auctOrdNo = new Integer(auctOrdNoStr);
			System.out.println(auctOrdNo);
			AuctOrdService auctOrdSvc = new AuctOrdService();
			AuctOrdVO auctOrd = auctOrdSvc.getOneOrd(auctOrdNo);

			// ?????????????????? ??????????????? 2:?????????
			auctOrd.setAuctOrdState(2);
			// ??????????????????
			long time = System.currentTimeMillis();
			Timestamp auctOrdModTime = new Timestamp(time);
			auctOrd.setAuctOrdModTime(auctOrdModTime);
			auctOrdSvc.updateOrd(auctOrd);

			String ordFDetlurl = "/backend/auct/auctOrder/orderRecAll.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(ordFDetlurl);
			successView.forward(req, res);

		}

		// ??????????????????
		if ("cancel".equals(action)) {
			String auctOrdNoStr = req.getParameter("auctOrdNo");
			Integer auctOrdNo = new Integer(auctOrdNoStr);
			AuctOrdService auctOrdSvc = new AuctOrdService();
			AuctOrdVO auctOrd = auctOrdSvc.getOneOrd(auctOrdNo);
			// ?????????????????? ??????????????? 0????????????
			auctOrd.setAuctOrdState(0);
			// ??????????????????
			long time = System.currentTimeMillis();
			Timestamp auctOrdModTime = new Timestamp(time);
			auctOrd.setAuctOrdModTime(auctOrdModTime);
			auctOrdSvc.updateOrd(auctOrd);

			String ordFDetlurl = "/backend/auct/auctOrder/orderRecAll.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(ordFDetlurl);
			successView.forward(req, res);

		}

		// ??????????????????
		if ("cancelOrd".equals(action)) {

			// ????????????
			String auctOrdNoStr = req.getParameter("auctOrdNo");
			Integer auctOrdNo = new Integer(auctOrdNoStr);
			AuctOrdService auctOrdSvc = new AuctOrdService();
			AuctOrdVO auctOrd = auctOrdSvc.getOneOrd(auctOrdNo);
			// ?????????????????? ??????????????? 0????????????
			auctOrd.setAuctOrdState(0);
			// ??????????????????
			long time = System.currentTimeMillis();
			Timestamp auctOrdModTime = new Timestamp(time);
			auctOrd.setAuctOrdModTime(auctOrdModTime);
			auctOrdSvc.updateOrd(auctOrd);

			List<AuctOrdVO> auctOrdList = new ArrayList<AuctOrdVO>();
			AuctOrdService OrdSvc = new AuctOrdService();
			auctOrdList = OrdSvc.getOrderRec(memNo);

			req.setAttribute("auctOrdList", auctOrdList);
			String ordRecurl = "/frontend/auctAct/orderRec.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(ordRecurl);
			successView.forward(req, res);
		}
		
		
		// ????????????
		if ("finishOrd".equals(action)) {
			// ????????????
			if (memVOIsNull) {
				return;
			}
			String auctOrdNoStr = req.getParameter("auctOrdNo");
			Integer auctOrdNo = new Integer(auctOrdNoStr);
			AuctOrdService auctOrdSvc = new AuctOrdService();
			AuctOrdVO auctOrd = auctOrdSvc.getOneOrd(auctOrdNo);
			// ?????????????????? ??????????????? 0????????????
			auctOrd.setAuctOrdState(3);
			// ??????????????????
			long time = System.currentTimeMillis();
			Timestamp auctOrdModTime = new Timestamp(time);
			auctOrd.setAuctOrdModTime(auctOrdModTime);
			auctOrdSvc.updateOrd(auctOrd);
			List<AuctOrdVO> auctOrdList = new ArrayList<AuctOrdVO>();
			AuctOrdService OrdSvc = new AuctOrdService();
			auctOrdList = OrdSvc.getOrderRec(memNo);

			req.setAttribute("auctOrdList", auctOrdList);
			String ordRecurl = "/frontend/auctAct/orderRec.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(ordRecurl);
			successView.forward(req, res);
		}

		// ?????????????????????????????????
		// ????????????????????????????????????
		if ("orderRec".equals(action)) {
			List<AuctOrdVO> auctOrdList = new ArrayList<AuctOrdVO>();
			AuctOrdService OrdSvc = new AuctOrdService();
			// ????????????
			if (memVOIsNull) {
				return;		
			}
			auctOrdList = OrdSvc.getOrderRec(memNo);

			req.setAttribute("auctOrdList", auctOrdList);
			String ordRecurl = "/frontend/auctAct/orderRec.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(ordRecurl);
			successView.forward(req, res);
			// 0:???????????? 1:????????? 2:????????? 3:????????????
		}

		// ??????????????????????????????
		if ("createOrd".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String errorPage = "/frontend/auctAct/orderList.jsp";

//			try {
			// ????????????
			if (memVOIsNull) {
				// TODO
				return;
			}

			// ?????????????????????
			String receName = req.getParameter("receName");
			String receReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (receName == null || receName.trim().length() == 0) {
				errorMsgs.add("???????????????????????????");
			} else if (!receName.trim().matches(receReg)) {
				errorMsgs.add("??????????????????????????????????????????????????????_ ,??????????????????2???10??????");
			}

			// ?????????????????????
			// ??????select ??????
			String county = req.getParameter("county");
			if (county == "0000") {
				errorMsgs.add("???????????????");
			}
			String city = req.getParameter("city");

			// ???????????????
			String addr = req.getParameter("addr");
			String addrReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,100}$";
			if (addr == null || addr.trim().length() == 0) {
				errorMsgs.add("???????????????????????????");
			} else if (!addr.trim().matches(addrReg)) {
				errorMsgs.add("??????????????????????????????????????????????????????_,??????????????????2???100??????");
			}

			// ?????????????????????
			String recePhone = req.getParameter("recePhone");
			String recePhoneReg = "[0-9]{10}";
			if (recePhone == null || recePhone.trim().length() == 0) {
				errorMsgs.add("???????????????????????????");
			} else if (!recePhone.trim().matches(recePhoneReg)) {
				errorMsgs.add("??????????????????????????????0-9");
			}

			// ??????????????????
			String note = req.getParameter("note");

			// ????????????
			long currentTime = System.currentTimeMillis();
			Timestamp auctOrdTime = new Timestamp(currentTime);
			Timestamp auctOrdModTime = new Timestamp(currentTime);

			// ?????????????????????
			StringBuffer receAddr = new StringBuffer(county).append(city).append(addr);

			// ?????????????????????
			Integer sum = countTotalPrice(auctCart);

			// ????????????????????????
			AuctOrdService auctOrdSvc = new AuctOrdService();
			AuctOrdVO auctOrdVO = new AuctOrdVO();
			auctOrdVO.setMemNo(memNo);
			auctOrdVO.setAuctOrdAmount(sum);
			auctOrdVO.setAuctOrdState(1);
			auctOrdVO.setReceName(receName);
			auctOrdVO.setReceAddr(receAddr.toString());// StringBuffer????????????Sting
			auctOrdVO.setRecePhone(recePhone);
			auctOrdVO.setNote(note);
			auctOrdVO.setAuctOrdTime(auctOrdTime);
			auctOrdVO.setAuctOrdModTime(auctOrdModTime);
			req.setAttribute("auctOrdVO", auctOrdVO);
			req.setAttribute("summary", sum);
			
			Map<Integer, String> nameMap = generateProdNameMap();
			req.setAttribute("nameMap", nameMap);
			// ??????????????????
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failView = req.getRequestDispatcher(errorPage);
				failView.forward(req, res);
				return;
			}

			/*************************** 2.????????????????????? ***************************************/
			List<AuctOrdDetlVO> auctOrdDetlList = new ArrayList<AuctOrdDetlVO>(auctCart.values());
			auctOrdSvc.addOrderWithOrderDetl(auctOrdVO, auctOrdDetlList);
			session.removeAttribute("auctCart");
			req.setAttribute("auctOrdDetlList", auctOrdDetlList);
			
			String creOrdurl = "/frontend/auctAct/createOrd.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(creOrdurl);
			successView.forward(req, res);

//			} 
//			catch (Exception e) {
//				errorMsgs.add("??????????????????");
//				RequestDispatcher failView = req.getRequestDispatcher(errorPage);
//				failView.forward(req, res);
//			}
		}

		// JSP?????????????????????????????????????????????
		if ("addProdToCart".equals(action)) {

			// ??????????????????????????????
			String auctActprodNostr = req.getParameter("auctActProdNo");
			Integer auctActProdNo = new Integer(auctActprodNostr);

			// ???auctActProdNo??? service???????????????????????????
			AuctActProdService auctActProdSvc = new AuctActProdService();
			AuctActProdVO auctActProdVO = auctActProdSvc.getByActProdNo(auctActProdNo);

			AuctOrdDetlVO newOrdProd = new AuctOrdDetlVO();
			newOrdProd.setAuctActProdNo(auctActProdNo);
			newOrdProd.setPrice(auctActProdVO.getAuctProdPrice());
			newOrdProd.setProdPurQty(1);

			// ?????????????????????
			if (auctCart == null) { // ????????????????????????
				//
				auctCart = new HashMap<Integer, AuctOrdDetlVO>();
				auctCart.put(newOrdProd.getAuctActProdNo(), newOrdProd);
			} else {
				// ?????????????????????
				AuctOrdDetlVO inOrdProd = auctCart.get(newOrdProd.getAuctActProdNo());
				if (inOrdProd != null) {
					inOrdProd.setProdPurQty(inOrdProd.getProdPurQty() + newOrdProd.getProdPurQty());

				} else {
					inOrdProd = newOrdProd;
				}
				auctCart.put(inOrdProd.getAuctActProdNo(), inOrdProd);
				// ????????????????????????????????????????????????????????????????????????????????????????????????ELSE????????????
			}
			session.setAttribute("auctCart", auctCart);
			String addProdurl = "/frontend/auctAct/auctActProd.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(addProdurl);
			successView.forward(req, res);

		}

		// AJAX?????????????????????????????????
		if ("ajaxAddProd".equals(action)) {

			// ??????????????????????????????
			String auctActprodNostr = req.getParameter("auctActProdNo");
			Integer auctActProdNo = new Integer(auctActprodNostr);

			// ???auctActProdNo??? service???????????????????????????
			AuctActProdService auctActProdSvc = new AuctActProdService();
			AuctActProdVO auctActProdVO = auctActProdSvc.getByActProdNo(auctActProdNo);

			AuctOrdDetlVO newOrdProd = new AuctOrdDetlVO();
			newOrdProd.setAuctActProdNo(auctActProdNo);
			newOrdProd.setPrice(auctActProdVO.getAuctProdPrice());
			newOrdProd.setProdPurQty(1);

			// ?????????????????????
			if (auctCart == null) { // ????????????????????????
				//
				auctCart = new HashMap<Integer, AuctOrdDetlVO>();
				auctCart.put(newOrdProd.getAuctActProdNo(), newOrdProd);
			} else {
				// ?????????????????????
				AuctOrdDetlVO inOrdProd = auctCart.get(newOrdProd.getAuctActProdNo());
				if (inOrdProd != null) {
					inOrdProd.setProdPurQty(inOrdProd.getProdPurQty() + newOrdProd.getProdPurQty());

				} else {
					inOrdProd = newOrdProd;
				}
				auctCart.put(inOrdProd.getAuctActProdNo(), inOrdProd);
				// ????????????????????????????????????????????????????????????????????????????????????????????????ELSE????????????
			}
			session.setAttribute("auctCart", auctCart);
			PrintWriter out = res.getWriter();
			out.write("true");
			out.flush();
			out.close();
		}

		// ???????????????
		if ("displayCart".equals(action)) {

			AuctActProdService auctProdSvc = new AuctActProdService();
			List<AuctActProdVO> auctProds = auctProdSvc.getAll();
			AuctProdService auctProd = new AuctProdService();
			List<AuctProdVO> prods = auctProd.getallProd();

			Map<Integer, String> nameMap = generateProdNameMap();
			req.setAttribute("nameMap", nameMap);
			if (auctCart != null) {
				Integer sum = countTotalPrice(auctCart);
				req.setAttribute("summary", sum);
			}

			String ordUrl = "/frontend/auctAct/auctProdCart.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(ordUrl);
			successView.forward(req, res);

		}

		// ??????????????????????????????
		// ??????????????????
		if ("delCartProd".equals(action)) {
			String auctActProdNostr = req.getParameter("auctActProdNo");
			Integer auctActProdNo = new Integer(auctActProdNostr);
			auctCart.remove(auctActProdNo);
			Integer sum = countTotalPrice(auctCart);
			req.setAttribute("summary", sum);
			session.setAttribute("auctCart", auctCart);
			Map<Integer, String> nameMap = generateProdNameMap();
			req.setAttribute("nameMap", nameMap);
			String addProdurl = "/frontend/auctAct/auctProdCart.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(addProdurl);
			successView.forward(req, res);
		}

		if ("addCartProd".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<>();
			req.setAttribute("errorMsgs", errorMsgs);

			// ????????????????????? =auctActProdNo??????
			String prodStr = "%sQty";
			for (AuctOrdDetlVO prodVO : auctCart.values()) {
				// ??????????????????
				String Qty = new String(String.format(prodStr, prodVO.getAuctActProdNo()));
				String prodPurQtystr = req.getParameter(Qty);
				if (prodPurQtystr == null || prodPurQtystr.trim().length() == 0) {
					errorMsgs.put("prodPurQtystr", "?????????????????????");
				}
				// ??????????????????
				Integer prodPurQty = null;
				try {
					prodPurQty = new Integer(prodPurQtystr);
					if (prodPurQty < 0) {
						errorMsgs.put("prodPurQty", "???????????????????????????");
					}
				} catch (Exception e) {
					errorMsgs.put("prodPurQty", "??????????????????");
				}
				// ?????????????????????
				prodVO.setProdPurQty(prodPurQty);
				prodVO.setSumPrice(countTotalPrice(auctCart));
				auctCart.put(prodVO.getAuctOrdNo(), prodVO);
				Integer sum = countTotalPrice(auctCart);
				req.setAttribute("summary", sum);
				session.setAttribute("auctCart", auctCart);
				String addProdurl = "/frontend/auctAct/auctProdCart.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(addProdurl);
				successView.forward(req, res);

			}
		}

		// ????????????????????????????????????
		if ("getCartProdUpdate".equals(action)) {
			// ??????????????????
			AuctActProdService auctProdSvc = new AuctActProdService();
			List<AuctActProdVO> auctProds = auctProdSvc.getAll();
			AuctProdService auctProd = new AuctProdService();
			List<AuctProdVO> prods = auctProd.getallProd();

			Map<Integer, String> nameMap = generateProdNameMap();
			req.setAttribute("nameMap", nameMap);

			String updateProdurl = "/frontend/auctAct/updateCart.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(updateProdurl);
			successView.forward(req, res);
		}

		// ???AJAX?????????????????????

		if ("ajaxNum".equals(action)) {
			// ?????????????????????????????????????????? 1.
			Integer auctActProdNo = new Integer(req.getParameter("auctActProdNo"));
			Integer prodPurQty = new Integer(req.getParameter("prodPurQty"));

			// 2. session
			AuctOrdDetlVO auctOrdDetlVO = auctCart.get(auctActProdNo);
			Integer price = auctOrdDetlVO.getPrice();
			Integer sum = price * prodPurQty;
			auctOrdDetlVO.setProdPurQty(prodPurQty);
			auctOrdDetlVO.setSumPrice(sum);

			auctCart.put(auctActProdNo, auctOrdDetlVO);

			// 3. front end
			PrintWriter out = res.getWriter();
			JSONObject obj = new JSONObject();
			try {
				obj.put("sum", sum);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			out.print(obj);
			out.flush();
			out.close();

		}

		// ????????????
		if ("checkOut".equals(action)) {
			// ????????????

			Integer cartSum = countTotalPrice(auctCart);
			TransRecService transRecSvc=new TransRecService();
			Integer nowMoney=transRecSvc.getDeposit(memNo);
			// ????????????????????????
			if (memVOIsNull) {
				// ????????????????????????
				String failurl = "/frontend/member/login.html";
				RequestDispatcher failView = req.getRequestDispatcher(failurl);
				failView.forward(req, res);
				return;
				// ??????????????????????????????????????????????????????
			} else if (cartSum>nowMoney) {
				session.setAttribute("from", "auctProd");//????????????????????????????????????
				req.setAttribute("cartSum", cartSum);//??????????????????(????????????????????????)
				req.setAttribute("nowMoney", nowMoney);//??????????????????(????????????????????????)
				String failurl = "/frontend/transRec/saveMoney1.jsp";
				RequestDispatcher failView = req.getRequestDispatcher(failurl);
				failView.forward(req, res);
				return;
			}

			/*************************** 2.?????????????????? **************************************/
			// ?????????????????????????????????????????????????????????
			// ??????????????????

			AuctActProdService auctProdSvc = new AuctActProdService();
			List<AuctActProdVO> auctProds = auctProdSvc.getAll();
			AuctProdService auctProd = new AuctProdService();
			List<AuctProdVO> prods = auctProd.getallProd();

			Map<Integer, String> nameMap = generateProdNameMap();
			req.setAttribute("nameMap", nameMap);
			req.setAttribute("summary", cartSum);
			
			session.setAttribute("auctCart", auctCart);
			String addProdurl = "/frontend/auctAct/orderList.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(addProdurl);
			successView.forward(req, res);
		}
		
		
			//??????????????????????????????
		
		if("checkAgain".equals(action)){
			//?????????????????????
			String from = (String) session.getAttribute("from");
			session.removeAttribute("from");
			
			if("auctProd".equals(from)) { // ??????????????????!!!
				Integer sum = countTotalPrice(auctCart);
				Map<Integer, String> nameMap = generateProdNameMap();
				req.setAttribute("nameMap", nameMap);
				req.setAttribute("summary", sum);
				session.setAttribute("auctCart", auctCart);
				String addProdurl = "/frontend/auctAct/orderList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(addProdurl);
				successView.forward(req, res);
			} 
		}
		
		
		
		//???AJAX????????????		
		if("ajaxCheckMoney".equals(action)) {
			Integer nowMoney=transRecSvc.getDeposit(memNo);
			//???ajax??????
			PrintWriter out = res.getWriter();
			//???JSON???
			JSONObject moneyObj = new JSONObject();
			try {
				moneyObj.put("nowMoney", nowMoney);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			out.print(moneyObj);
			out.flush();
			out.close();
		}
	}

	private Map<Integer, String> generateProdNameMap() {
		List<AuctActProdVO> auctProds = auctProdSvc.getAll();
		List<AuctProdVO> prods = auctProd.getallProd();
		Map<Integer, String> nameMap = new HashMap<>();
		Map<Integer, AuctProdVO> prodMap = getProdMap(prods);

		for (AuctActProdVO auctProd : auctProds) {
			Integer auctActProdNo = auctProd.getAuctActProdNo();
			Integer prodNo = auctProd.getAuctProdNo();
			AuctProdVO prodVO = prodMap.get(prodNo);
			String prodName = prodVO.getAuctProdName();
			nameMap.put(auctActProdNo, prodName);
		}
		return nameMap;
	}

	private Map<Integer, AuctProdVO> getProdMap(List<AuctProdVO> prods) {
		Map<Integer, AuctProdVO> prodMap = new HashMap<>();
		for (AuctProdVO vo : prods) {
			prodMap.put(vo.getAuctProdNo(), vo);
		}

		return prodMap;
	}

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
}
