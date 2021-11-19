<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%>

<%
  MemberVO memVO = (MemberVO) request.getAttribute("memVO"); //MemberServlet.java(Concroller), 存入req的memVO物件
%>

<html>
<head>
<title>會員資料 </title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
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

<style>
  table {
	width: 1500px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
    font-size: 14px
  }
  th, td {
    padding: 7px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>


<table id="table-1">
	<tr><td>
		 <h3>員工資料</h3>
		 <h4><a href="<%=request.getContextPath()%>/backend/frontend.jsp">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>會員<br>編號</th>
		<th>會員<br>帳號</th>
		<th>會員<br>密碼</th>
		<th>帳號<br>狀態</th>
		<th>驗證<br>狀態</th>
		<th>會員驗證完成時間</th>
		<th>會員<br>姓名</th>
		<th>會員<br>電話</th>
		<th>縣市</th>
		<th>區域</th>
		<th>地址</th>
		<th>E-Mail</th>
		<th>生日</th>
		<th>加入時間</th>
		<th>賣家功能<br>狀態</th>
		<th>錢包結餘</th>
	</tr>
	<tr>
		<td><%=memVO.getMemno()%></td>
		<td><%=memVO.getMemaccount()%></td>		
		<td><%=memVO.getMempassword()%></td>
		<td><%=memVO.getMemstatus()%></td>
		<td><%=memVO.getMemvrfed()%></td>
		<td><%=memVO.getMemnovrftime()%></td>
		<td><%=memVO.getMemname()%></td>
		<td><%=memVO.getMemmobile()%></td>
		<td><%=memVO.getMemcity()%></td>
		<td><%=memVO.getMemdist()%></td>
		<td><%=memVO.getMemadd()%></td>
		<td><%=memVO.getMememail()%></td>
		<td><%=memVO.getMembirth()%></td>
		<td><%=memVO.getMemjointime()%></td>
		<td><%=memVO.getUsderstatus()%></td>
		<td><%=memVO.getEcash()%></td>
		

	</tr>
</table>

</body>
</html>