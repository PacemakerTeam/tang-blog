<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" name="username" class="form-control" placeholder="Enter Username" id="username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
        </div>

        <button id="btn-login" class="btn btn-primary">로그인</button>
        <a href="Https://kauth.kakao.com/oauth/authorize?client_id=8a33a6a0caeba8c98df9077858dee986&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code HTTP/1.1"><img height = "38px" src="/image/kakao.png"/></a>
    </form>

</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>