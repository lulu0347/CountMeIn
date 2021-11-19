<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login - Brand</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
</head>

<body class="bg-gradient-primary">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-9 col-lg-12 col-xl-10">
                <div class="card shadow-lg o-hidden border-0 my-5">
                    <div class="card-body p-0">
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-flex">
                                <div class="flex-grow-1 bg-login-image" style="background-image: url(&quot;images/logo.png&quot;);"></div>
                            </div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h4 class="text-dark mb-4">CountMeIn後臺管理</h4>
                                    </div>
                                    <form class="user" action="<%=request.getContextPath() %>/backend/login.do" method="post">
                                        <div class="form-group" id="accountback"><input class="form-control form-control-user" type="text" id="adminAccount" placeholder="Enter Account..." name="adminAccount" pattern="^(?=.*[a-zA-Z])(?=.*[0-9]).{6,12}$" required="required" oninput="setCustomValidity('');" oninvalid="setCustomValidity('請輸入正確的帳號格式:含英數6~12個字元');"></div>
                                        <div class="form-group"><input class="form-control form-control-user" type="password" id="adminPassword" placeholder="Password" name="adminPassword" pattern="^(?=.*[a-zA-Z])(?=.*[0-9]).{6,12}$" required="required" oninput="setCustomValidity('');" oninvalid="setCustomValidity('請輸入正確的密碼格式:含英數6~12個字元');"></div>
                                        <div class="form-group">
                                        </div><button class="btn btn-primary btn-block text-white btn-user" type="submit">Login</button>
                                        <c:if test="${not empty errorMsgs}">
											<font style="color:red">請修正以下錯誤:</font>
											<ul>
											    <c:forEach var="message" items="${errorMsgs}">
													<li style="color:red">${message}</li>
												</c:forEach>
											</ul>
										</c:if>
                                        <hr>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/chart.min.js"></script>
    <script src="assets/js/bs-init.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="assets/js/theme.js"></script>
</body>
</html>