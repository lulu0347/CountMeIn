<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.itemorder.model.*"%>

<%
	ItemOrderVO itemOrderVO = (ItemOrderVO) request.getAttribute("itemOrderVO");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>訂單資料新增</title>

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
	width: 600px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>訂單新增</h3></td><td>
		 <h4><a href="<%=request.getContextPath()%>/backend/item.jsp">回商品管理</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/itemOrder/itemOrder.do" name="form1">
<table>

	<tr>
		<td>購買會員編號:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="memNo" size="45" 
			 value="<%= (itemOrderVO==null)? "" : itemOrderVO.getMemNo()%>" /></td>
	</tr>
	
	
	<tr>
		<td>商品售出日期:<font color=red><b>*</b></font></td>
		<td><input name="tranTime" id="f_date1" type="text"></td>
	</tr>

	<tr>
		<td>訂單金額:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="orderTotal" size="45" 
			 value="<%= (itemOrderVO==null)? "" : itemOrderVO.getOrderTotal()%>" /></td>
	</tr>
		
	<tr>
		<td>訂單狀態:<font color=red><b>*</b></font></td>
		<td>
		<select name="orderState" id="orderState">
			<option>請選擇</option>
			<option value="1" <c:if test="${itemOrderVO.getOrderState() == 0}"><c:out value="selected"></c:out></c:if>>未出貨</option>
			<option value="2" <c:if test="${itemOrderVO.getOrderState() == 1}"><c:out value="selected"></c:out></c:if>>已出貨</option>
			<option value="3" <c:if test="${itemOrderVO.getOrderState() == 2}"><c:out value="selected"></c:out></c:if>>已收貨</option>
			<option value="4" <c:if test="${itemOrderVO.getOrderState() == 3}"><c:out value="selected"></c:out></c:if>>退貨</option>
			<option value="5" <c:if test="${itemOrderVO.getOrderState() == 4}"><c:out value="selected"></c:out></c:if>>取消</option>
		</select>
		</td>
	</tr>	
	
	<tr>
		<td>收件人姓名:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="receiverName" size="45" 
			 value="<%= (itemOrderVO==null)? "" : itemOrderVO.getReceiverName()%>" /></td>
	</tr>		

	<tr>
		<td>收件人地址:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="receiverAddress" size="45" 
			 value="<%= (itemOrderVO==null)? "" : itemOrderVO.getReceiverAddress()%>" /></td>
	</tr>	
	
	<tr>
		<td>收件人電話:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="receiverPhone" size="45" 
			 value="<%= (itemOrderVO==null)? "" : itemOrderVO.getReceiverPhone()%>" /></td>
	</tr>	
	
</table>
<br>
<input type="hidden" name="action" value="insertWithItemDetails">
<input type="submit" value="送出新增"></FORM>	
		

</body>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
	<%System.out.println(request.getContextPath());%>
<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: new Date(), // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
//         $.datetimepicker.setLocale('zh');
//         $('#f_date2').datetimepicker({
// 	       theme: '',              //theme: 'dark',
// 	       timepicker:true,       //timepicker:true,
// 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
// 	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
// 		   value: new Date(), // value:   new Date(),
//            //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
//            //startDate:	            '2017/07/10',  // 起始日
//            minDate:               '-1970-01-01' // 去除今日(不含)之前
//            //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
//         });
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
        //      var somedate1 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});

        
        //      2.以下為某一天之後的日期無法選擇
        //      var somedate2 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});


        //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
        
</script>

</html>