<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Jobs List</title>
    <link type="text/css" rel="stylesheet" href="../css/bootstrap.css">
    <script src="../js/bootstrap.js"></script>
</head>
<body>
    <div class="container mt-5 w-75">
        <div id="wrapper">
            <div id="header">
                <h2 class="text-center mb-5">Your Jobs</h2>
            </div>
        </div>

        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Job Title</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="tempJob" items="${JobsList}">

                        <c:url var="editLink" value="/seeker/edit-job">
                            <c:param name="JobId" value="${tempJob.id}"/>
                        </c:url>

                        <c:url var="deleteLink" value="/seeker/delete-job">
                            <c:param name="JobId" value="${tempJob.id}"/>
                        </c:url>

                        <c:url var="listAppLink" value="/seeker/list-job-application">
                            <c:param name="JobId" value="${tempJob.id}"/>
                        </c:url>

                        <tr>
                            <td> ${tempJob.title} </td>
                            <td> <fmt:formatDate value="${tempJob.startDate}" pattern="dd-MM-yyyy" /> </td>
                            <td> <fmt:formatDate value="${tempJob.endDate}" pattern="dd-MM-yyyy" /> </td>
                            <td> ${tempJob.status} </td>
                            <td>
                                <a href="${editLink}">Edit</a>
                                        |
                                <a href="${deleteLink}" onclick="if(!(confirm('Sure you want to delete this job?'))) return false">Delete</a>
                                        |
                                <a href="${listAppLink}">Show Applications</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>