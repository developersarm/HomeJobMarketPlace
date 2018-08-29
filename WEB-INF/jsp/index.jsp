<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Home Job Marketplace</title>
    <link type="text/css" rel="stylesheet" href="css/bootstrap.css">
    <script src="js/bootstrap.js"></script>
</head>
<body>
    <div class="container">
        <div id="wrapper">
        		<div id="header">
        			<h2>Home Job Marketplace</h2>
        		</div>
        	</div>
       <form action="/HomeJobMarketplace/visitor/login" method="post">
       		<table style="with: 50%">
       			<tr>
       				<td>UserName</td>
       				<td><input type="text" name="username" /></td>
       			</tr>
       				<tr>
       				<td>Password</td>
       				<td><input type="password" name="password" /></td>
       			</tr>
       		</table>
       		<input type="submit" name="button" value="login" /></form>
       		<a href="/HomeJobMarketplace/visitor/register">Register</a>
       		<a href="/HomeJobMarketplace/visitor/resetpassword">Reset Password</a>
    </div>
</body>
</html>