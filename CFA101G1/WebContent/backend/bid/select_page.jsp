<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.bid.model.*"%>
<%@ page import="com.productkind.model.*"%>
<%@ page import="java.util.*"%>
<%
	ProductKindService kindSvc = new ProductKindService();
	List<ProductKindVO> list = kindSvc.getAllProductKind();
	pageContext.setAttribute("list",list);
%>
<html>
<head>
<title>競標商品管理</title>

<style>
	body {
		background-color: lightblue;
	}
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
    border-radius: 15px;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

</head>
<body bgcolor='white'>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li>
  	<div class="accordion" id="accordionExample">
	  <div class="card">
	    <div class="card-header" id="headingOne">
	      <h2 class="mb-0">
	        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
	          	<b>顯示所有商品訂單</b>
	        </button>
	      </h2>
	    </div>
	
	    <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
	      <div class="card-body">
	        <b><a href='<%=request.getContextPath()%>/backend/bid/listAllBid.jsp'>競標商品列表</a></b><br>	 
  			<b><a href='<%=request.getContextPath()%>/backend/bid/listAllBidOrder.jsp'>競標訂單列表</a></b><br>	 
	    </div>
	  </div>
	</div>
  </li>
  
  
  <li>
  	 <div class="accordion" id="accordionExample">
	  <div class="card">
	    <div class="card-header" id="headingTwo">
	      <h2 class="mb-0">
	        <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
	          	<b>單一競標查詢</b>
	        </button>
	      </h2>
	    </div>
	    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">
	      <div class="card-body">
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/bid/bid.do" >
		        <b>輸入競標商品編號 (如51001):</b>
		        <input type="text" name="bidProdNo">
		        <input type="hidden" name="action" value="getOne_For_Display">
		        <input type="submit" value="送出">
		    </FORM>

		  </div> 
	      <div class="card-body" id="searchbyName">
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/bid/bid.do" >
		        <b>輸入錢包交易編號 (如15016):</b>
		        <input type="text" name="transRecNo">
		        <input type="hidden" name="action" value="getOne_For_Display_transRecNo">
		        <input type="submit" value="送出">
		    </FORM>  	      	
	      </div>

	      <div class="card-body">
		       <jsp:useBean id="bidSvc" scope="page" class="com.bid.model.BidService" />

			     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/bid/bid.do" >
			       <b>選擇競標商品編號:</b>
			       <select size="1" name="bidProdNo">
			         <c:forEach var="bidVO" items="${bidSvc.all}" > 
			          <option value="${bidVO.bidProdNo}">${bidVO.bidProdNo}
			         </c:forEach>   
			       </select>
			       <input type="hidden" name="action" value="getOne_For_Display">
			       <input type="submit" value="送出">
			    </FORM>	      	
	      </div>
	    </div>
	  </div>
	</div>
  </li>
  
  <li>
  	<div class="accordion" id="accordionExample">
	  <div class="card">
	    <div class="card-header" id="headingOne">
	      <h2 class="mb-0">
	        <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="true" aria-controls="collapseOne">
	          	<b>新增競標商品</b>
	        </button>
	      </h2>
	    </div>
	
	    <div id="collapseThree" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
	      <div class="card-body">
	        <b><a href='<%=request.getContextPath()%>/backend/bid/addBid.jsp'>新增一筆商品</a></b><br>	      
	      </div>
	    </div>
	  </div>
	</div>
  </li>
 </ul>
</body>
</html>