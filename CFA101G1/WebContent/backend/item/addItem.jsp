<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.item.model.*"%>
<%@ page import="com.itempic.model.*"%>
<%@ page import="java.util.*"%>

<%
	ItemService itemSrv = new ItemService();
	List<ItemVO> list = itemSrv.getAll();
	pageContext.setAttribute("list", list);
%>
<%
	ItemVO itemVO = (ItemVO) request.getAttribute("itemVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>上架商品</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/frontend/css/template.css" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Serif+TC&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
 	#holder{
 	width:100%;
 	height:200px;
 	}
 	img{
 	width:200px;
 	height:200px;
 	}
 	#img1{
 	height:100px;
 	width:100.63px;
 	}
</style>

</head>
<body>
	<header>
		<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
			<div class="container-fluid">
				<button class="btn"><i class="fa fa-home"></i></button>
				<a class="navbar-brand" href="#">CountMEIn</a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<li class="nav-item"><a class="nav-link" href="#">維修服務</a></li>
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
							role="button" data-bs-toggle="dropdown" aria-expanded="false">
								客服中心 </a>
							<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
								<li><a class="dropdown-item" href="#">檢舉商品</a></li>
								<li><a class="dropdown-item" href="#">檢舉賣家</a></li>
								<li>
									<hr class="dropdown-divider">
								</li>
								<li><a class="dropdown-item" href="#">查看更多</a></li>
							</ul></li>

					</ul>
					<form class="d-flex">
						<button class="btn btn-outline-success btn-sm me-md-4"
							type="submit">註冊</button>
						<button class="btn btn-outline-success btn-sm" type="submit">登入會員</button>
					</form>
				</div>
			</div>
		</nav>
		<div class="MallTop">
			<div style="margin-top: 56px">
				<div class="container">
					<div class="row">
						<div class="col" style="height: 50px;"></div>
					</div>
					<div class="row">
						<div class="col-3">
							<a href="#page-top"> <img id="img1"
								src="<%=request.getContextPath()%>/resource/Images/logo.png"
								alt="logo" style="height: 100px;" margin-bottom=10px;>
							</a>
						</div>
						<div class="col-6">
							<div class="row ">
							</div>
						</div>
						<br>
					</div>
					<div class="row" style="height: 20px"></div>
				</div>
			</div>
		</div>
	</header>
	<br>

		 <h3>上架商品</h3>
	

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/item/item.do" 
		name="form1" enctype="multipart/form-data">
<table>


<%-- <jsp:useBean id="deptSvc" scope="page" class="com.dept.model.DeptService" /> --%>
<!-- 	<tr> -->
<!-- 		<td>類別編號:<font color=red><b>*</b></font></td> -->
<!-- 		<td><select size="1" name="KindNo"> -->
<%-- 			<c:forEach var="deptVO" items="${deptSvc.all}"> --%>
<%-- 				<option value="${deptVO.deptno}" ${(empVO.deptno==deptVO.deptno)? 'selected':'' } >${deptVO.dname} --%>
<%-- 			</c:forEach> --%>
<!-- 		</select></td> -->
<!-- 	</tr> -->


	<tr>
		<td>商品類別編號:<font color=red><b>*</b></font></td>
		<td>
		<select name="kindNo" id="kindno">
			<option>請選擇</option>
			<option value="1" <c:if test="${itemVO.getKindNo() == 1}"><c:out value="selected"></c:out></c:if>>手機</option>
			<option value="2" <c:if test="${itemVO.getKindNo() == 2}"><c:out value="selected"></c:out></c:if>>電腦</option>
			<option value="3" <c:if test="${itemVO.getKindNo() == 3}"><c:out value="selected"></c:out></c:if>>相機</option>
			<option value="4" <c:if test="${itemVO.getKindNo() == 4}"><c:out value="selected"></c:out></c:if>>手錶</option>
			<option value="5" <c:if test="${itemVO.getKindNo() == 5}"><c:out value="selected"></c:out></c:if>>配件</option>
		</select>
		</td>
	</tr>

	<tr>
		<td>商品名稱:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="itemName" size="45" 
			 value="<%= (itemVO==null)? "" : itemVO.getItemName()%>" /></td>
	</tr>
	
	
	<tr>
		<td>商品單價:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="itemPrice" size="45"
			 value="<%= (itemVO==null)? "0" : itemVO.getItemPrice()%>" /></td>
	</tr>
	
	
	<tr>
		<td>上架狀態:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="itemState" size="1"
			 value="<%= (itemVO==null)? "1" : itemVO.getItemState()%>" /></td>
	</tr>
	
	<tr>
		<td>上架時間:<font color=red><b>*</b></font></td>
		<td><input name="launchedTime" id="f_date2" type="text"></td>
	</tr>
	
	<tr>
		<td>保固期限:<font color=red><b>*</b></font></td>
		<td><input type="TEXT" name="warrantyDate" size="5"
			 value="<%= (itemVO==null)? "0.0" : itemVO.getWarrantyDate()%>" /></td>
	</tr>
	
	<tr>
		<td>商品描述:<font color=red><b>*</b></font></td>
		<td><textarea name="itemProdDescription" rows="10" cols="50"><%= (itemVO==null)? "" : itemVO.getItemProdDescription()%></textarea></td>
	</tr>
	
	<tr>
		<td>
			<input type="file" name="path" multiple onclick="previewImage()" id="upfile">
			<input type="submit" value="確認新增商品">
			<input type="hidden" name="action" value="addUsed">
		</td>
	</tr>


</table>
</FORM>
<div id="holder"></div>

<div class="row">
					<div class="col-12">
						<button id="bt2" type="button" class="btn btn-success" onclick="location.href='<%=request.getContextPath()%>/backend/item.jsp'">回首頁</button>
</div>
</div>
<br>
	<footer>
		<div class="foot">
			<div class="container">
				<div class="row" style="height: 200px;">
					<div class="col-3"></div>
					<div class="col-9"></div>
				</div>
			</div>
		</div>
	</footer>

</body>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

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
        $.datetimepicker.setLocale('zh');
        $('#f_date2').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i:s',         //format:'Y-m-d H:i:s',
		   value: new Date(), // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           minDate:               '-1970-01-01' // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
   
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
<script type="text/javascript">
// 	$('.f').slick({
// 		dots : true,
// 		infinite : true,
// 		speed : 500,
// 		fade : true,
// 		cssEase : 'linear'
// 	});
	var filereader_support = typeof FileReader != 'undefined';

	if (!filereader_support) {
		alert("No FileReader support");
	}

	acceptedTypes = {
		'image/png' : true,
		'image/jpeg' : true,
		'image/gif' : true
	};

	function previewImage() {
		var upfile = document.getElementById("upfile");
		upfile.addEventListener("change", function(event) {
			var files = event.target.files || event.dataTransfer.files;
			for (var i = 0; i < files.length; i++) {
				previewfile(files[i])
			}
		}, false);
	}

	function previewfile(file) {
		if (filereader_support === true && acceptedTypes[file.type] === true) {
			var reader = new FileReader();
			reader.onload = function(event) {
				var image = new Image();
				image.src = event.target.result;
				image.width = 100;
				holder.appendChild(image);
			};
			reader.readAsDataURL(file);
		} else {
			holder.innerHTML += "<p>" + "filename: <strong>" + file.name
					+ "</strong><br>" + "ContentTyp: <strong>" + file.type
					+ "</strong><br>" + "size: <strong>" + file.size
					+ "</strong> bytes</p>";
		}
	}
</script>

</html>