<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.care.model.Member" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Member Registeration</title>
    <link type="text/css" rel="stylesheet" href="../css/bootstrap.css">
    <script src="../js/bootstrap.js"></script>
</head>
<body>
    <div class="container">
        <div id="wrapper">
            <div id="header">
                <h2>Register</h2>
            </div>
        </div>
        <form action="/HomeJobMarketplace/visitor/register" method="post">
            <div class="form-group">
            <label for="fname">First Name:</label>
            <input type="text" class="form-control" name="firstname" id="fname">
            </div>
            <div class="form-group">
            <label for="lname">Last Name:</label>
            <input type="text" class="form-control" id="lname" name="lastname">
            </div>
            <div class="form-group">
            <label for="phone">Phone No:</label>
            <input type="number" class="form-control" id="phone" name="phoneno">
            </div>
            <div class="form-group">
            <label for="email">Email Id:</label>
            <input type="email" class="form-control" id="email" name="emailid">
            </div>
            <div class="form-group">
            <label for="pwd">Password:</label>
            <input type="password" class="form-control" id="password" name="password">
            </div>
            <div class="form-group">
            <label for="address">Address:</label>
            <input type="text" class="form-control" id="address" name="address">
            </div>
            <div class="form-group">
            <label for="pincode">Pincode:</label>
            <input type="number" class="form-control" id="pincode" name="pincode">
            </div>

            <c:choose>
                <c:when test="${sessionScope.MemberType == 'SITTER'}">
                    <div class="form-group">
                    <label for="exp">Experience:</label>
                    <input type="number" class="form-control" id="exp" name="experinece">
                    </div>
                </c:when>
                <c:when test="${sessionScope.MemberType == 'SEEKER'}">
                    <div class="form-group">
                    <label for="totalchildren">Total Children:</label>
                    <input type="number" class="form-control" id="totalchildren" name="totalchildren">
                    </div>
                    <div class="form-group">
                    <label for="sname">Spouse Name:</label>
                    <input type="text" class="form-control" id="sname" name="spousename">
                    </div>
                </c:when>
                <c:otherwise>
                    Please put the blame on Abhay!
                </c:otherwise>
            </c:choose>
            <input type="submit" class="btn btn-default" value="Register"/>
        </form>
    </div>
</body>
</html>