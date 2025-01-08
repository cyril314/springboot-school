<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>微信接收信息</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@include file="./common/css.jsp" %>
</head>
<body>
<%@ include file="./common/navbar.jsp" %>
<div class="main-container container-fluid">
    <a class="menu-toggler" id="menu-toggler" href="#">
        <span class="menu-text"></span>
    </a>
    <%@ include file="./common/sidebar.jsp" %>
    <div class="main-content">
        <div class="page-content">
            <div class="page-header position-relative">
                <h1><small> <i class="icon-comment"></i> 微信接收信息 </small></h1>
            </div>
            <div class="row-fluid">
                <div class="span12">
                    <!--PAGE CONTENT BEGINS-->
                    <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th width="5%">#</th>
                            <th width="15%">MsgId</th>
                            <th width="10%">MsgType</th>
                            <th width="15%">FromUserName</th>
                            <th width="25%">CreateTime</th>
                            <th width="30%">Content</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${messageList}" var="message" varStatus="st">
                            <tr>
                                <td>${st.index+1}</td>
                                <td>${message.msgId}</td>
                                <td>${message.msgType}</td>
                                <td>${message.fromUserName}</td>
                                <td>${message.createTime}</td>
                                <td>${message.content}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <div class="dataTables_paginate paging_bootstrap pagination">
                        <button class="btn btn-success btn-mini" onclick="location.href='${ctx}/manager/messages?pagenum=${pagenum-1}'"
                                <c:if test="${pagenum <= 1}">disabled="disabled"</c:if>    >&laquo;
                        </button>
                        <button class="btn btn-success btn-mini" disabled="disabled">第 ${pagenum} 页</button>
                        <button class="btn btn-success btn-mini" onclick="location.href='${ctx}/manager/messages?pagenum=${pagenum+1}'"
                                <c:if test="${length < 10}">disabled="disabled"</c:if> >&raquo;
                        </button>
                    </div>
                </div><!--PAGE CONTENT ENDS-->
            </div><!--/.row-fluid-->
        </div><!--/.page-content-->
    </div><!--/.main-content-->
</div><!--/.main-container-->
<%@include file="./common/js.jsp" %>
</body>
</html>