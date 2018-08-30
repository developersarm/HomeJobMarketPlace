<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Home Job Marketplace</title>
    <link type="text/css" rel="stylesheet" href="../css/bootstrap.css">
    <script src="../js/bootstrap.js"></script>
</head>
<body>
    <div class="container">
        <div id="wrapper">
            <div id="header">
                <h2>Login to Home Job Marketplace</h2>
            </div>
        </div>

       	<form action="/HomeJobMarketplace/visitor/login" method="post">
          <div class="form-group">
            <label for="email">Email address:</label>
            <input type="email" class="form-control" name="emailid" id="email">
          </div>
          <div class="form-group">
            <label for="pwd">Password:</label>
            <input type="password" class="form-control" name="password" id="pwd">
          </div>
          <div class="checkbox">
            <label><input type="checkbox"> Remember me</label>
          </div>
          <input type="submit" class="btn btn-default" value="login"/>
        </form>

        <a href="/HomeJobMarketplace/visitor/register">Register</a>
        <a href="/HomeJobMarketplace/visitor/resetpassword">Reset Password</a>
    </div>
</body>
</html>