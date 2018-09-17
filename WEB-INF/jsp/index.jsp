<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.care.context.MyApplicationContext"%>
<%
if(MyApplicationContext.get().getMember() != null) {
  request.getRequestDispatcher("/visitor/login").forward(request, response);
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Home Job Marketplace</title>
    <link type="text/css" rel="stylesheet" href="/HomeJobMarketplace/css/bootstrap.css">
    <script src="/HomeJobMarketplace/js/bootstrap.js"></script>
</head>
<body>
    <div class="container mt-5 w-25">
        <div id="wrapper">
            <div id="header">
                <h2 class="text-center mb-5">Home Job Marketplace</h2>
            </div>
        </div>

        <c:if test="${not empty requestScope.msg}">
            <div class="alert alert-success">
              ${requestScope.msg}
            </div>
        </c:if>

        <c:if test="${not empty requestScope.error}">
            <div class="alert alert-danger">
              ${requestScope.error}
            </div>
        </c:if>

        <form action="/HomeJobMarketplace/visitor/login" method="post">
            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">
                  ${requestScope.error}
                </div>
            </c:if>
            <c:if test="${not empty requestScope.msg}">
                <div class="alert alert-success">
                  ${requestScope.msg}
                </div>
            </c:if>
            <div class="form-group">
                <label for="email">Email address:</label>
                <input type="email" class="form-control" name="email"
                    id="email" value="${formData.emailId}">
                <c:if test="${not empty formData.errors['emailId']}">
                    <div class="alert alert-danger">
                        ${formData.errors["emailId"]}
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label for="pwd">Password:</label>
                <input type="password" class="form-control" name="password" id="pwd">
                <c:if test="${not empty formData.errors['password']}">
                    <div class="alert alert-danger">
                        ${formData.errors["password"]}
                    </div>
                </c:if>
            </div>

            <div style="text-align:center">
                <input type="submit" class="btn btn-default" value="login"/>
            </div>
        </form>
        <div>
            <div style="text-align:center">
                <a href="/HomeJobMarketplace/visitor/register">Register</a>
                        &nbsp;&nbsp;&nbsp
                <a href="/HomeJobMarketplace/visitor/reset-password">Reset Password</a>
            </div>
        </div>

       	<form action="/HomeJobMarketplace/visitor/welcome" method="get">
       	    <div class="row" style="margin-top:5em">
       	        <div class="col align-self-center" style="text-align:center">
                    <button type="submit" class="btn btn-default" name="type" value="sitter">Apply for a Job</button>
       	        </div>
       	        <div class="col align-self-center" style="text-align:center">
                    <button type="submit" class="btn btn-default" name="type" value="seeker">Post a Job</button>
                </div>
       	    </div>
        </form>
    </div>
</body>
</html>