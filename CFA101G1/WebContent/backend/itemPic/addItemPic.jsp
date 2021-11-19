<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ page
import="java.util.*"%> <%@ page import="com.itempic.model.*"%> <%@ page
import="com.item.model.*"%> <% ItemPicVO itemPicVO = (ItemPicVO)
request.getAttribute("itemPicVO"); %>

<html>
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>修改商品照片</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />

    <link
      rel="stylesheet"
      href="<%=request.getContextPath()%>/frontend/css/template.css"
    />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Serif+TC&display=swap"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    />

    <style>
    img#pre{
    width:320px;
    height:320px;
    
    }
    
    .div4{
    margin:10px 0px 0px 0px;
    }
    </style>
  </head>

  <c:if test="${not empty errorMsgs}">
    <font style="color: red">請修正以下錯誤:</font>
    <ul>
      <c:forEach var="message" items="${errorMsgs}">
        <li style="color: red">${message}</li>
      </c:forEach>
    </ul>
  </c:if>

  <body>
    <header>
      <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <div class="container-fluid">
          <button class="btn">
            <i class="fa fa-home"></i>
          </button>
          <a class="navbar-brand" href="#">CountMEIn</a>
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0"></ul>
            <form class="d-flex">
              <button
                class="btn btn-outline-success btn-sm me-md-4"
                type="submit"
              >
                註冊
              </button>
              <button
                class="btn btn-outline-success btn-sm"
                type="submit"
                onclick="window.location.href('<%=request.getContextPath()%>/frontend/member/login.html')"
              >
                登入會員
              </button>
            </form>
          </div>
        </div>
      </nav>
      <div class="MallTop">
        <div style="margin-top: 56px">
          <div class="container">
            <div class="row">
              <div class="col" style="height: 50px"></div>
            </div>
            <div class="row">
              <div class="col-3">
                <a href="#page-top">
                  <img
                    src="<%=request.getContextPath()%>/resource/Images/logo.png"
                    alt="logo"
                    style="height: 100px"
                    margin-bottom="10px;"
                  />
                </a>
              </div>
              <div class="col-6">
                <div class="row">
                  <div class="input-group mb-3"></div>
                </div>
              </div>
              <br />
            </div>
            <div class="row" style="height: 20px"></div>
          </div>
        </div>
      </div>
      <!-- 		</div> -->
    </header>
    <br>
    <session>
      <div class="container">
      <h3>新增商品照片 : </h3>
      <hr>
        <div class="row">

			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/itemPic/itemPic.do" name="form1" enctype="multipart/form-data" >
    
				<jsp:useBean id="ItemSvc" scope="page" class="com.item.model.ItemService" />
			  <div class="">
				  <span>請選擇商品：</span>
				  <select size="1" name="itemNo">
				  <c:forEach var="itemVO" items="${ItemSvc.all}">
					  <option value="${itemVO.itemNo}">${itemVO.itemName}
				  </c:forEach>
			  </select>
			  </div>
			   <table>
				  <tr>
					  <td>商品圖片:</td>
					  <td>
						  <input name="itemPic" type="file" id="myFile" onchange="preView(this)">
					  </td>
				  </tr>
			  </table>
			  <img id="pre">
			  
			  <br>
			  <input type="hidden" name="action" value="addPic">
			  <input type="submit" value="確認新增商品照片">
		  </FORM>
		  <br>
		  <br>
		  <div class="row">
		  
		  <div class="col-12">
		  	<div class="div4">
						<button id="bt2" type="button" class="btn btn-primary"
							onclick="location.href='<%=request.getContextPath()%>/backend/item.jsp'">
							回首頁</button>
		  	</div>
		  	<div class="div4">
		  	</div>
		  	<div class="div4">
		  	</div>
		  </div>
		  
		  </div>

		</div>
      </div>
    </session>
    <footer>
      <div class="foot">
        <div class="container">
          <div class="row" style="height: 200px">
            <div class="col-3"></div>
            <div class="col-9"></div>
          </div>
        </div>
      </div>
    </footer>

    <script>
      function preView(e) {
        let myFile = document.getElementById('myFile');

        if (e.files) {
          let file = e.files[0];
          let reader = new FileReader();
          reader.addEventListener('load', function (e) {
            console.log(e.target.result);
            let img = document.getElementById('pre');
            img.src = e.target.result;
          });

          reader.readAsDataURL(file);
        } else {
          alert('請上傳商品照片');
        }
      }
    </script>
  </body>
</html>
