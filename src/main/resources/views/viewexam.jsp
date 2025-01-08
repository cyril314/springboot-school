<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>考试详情</title>
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
                <h1><small><i class="icon-file"></i>考试详情</small></h1>
            </div>
            <div class="row-fluid">
                <div class="span12">
                    <!--PAGE CONTENT BEGINS-->
                    <form action="addexammark" id="addexammark" class="form-horizontal" method="post">
                        <input type="hidden" name="classId" value="${exam.classId }"/>
                        <input type="hidden" name="course" value="${exam.course }"/>
                        <input type="hidden" name="fullMarks" value="${exam.fullMarks }"/>
                        <input type="hidden" name="remark" value="${exam.remark }"/>
                        <label> &nbsp;&nbsp;&nbsp; <b>${exam.classId}</b> 班 <b>${exam.course}</b> 考试详情
                            （满分<b>${exam.fullMarks}</b>分） &nbsp;&nbsp;
                            （平均分<b>${exam.average}</b>分）&nbsp;&nbsp;
                            （最高分<b>${exam.topMark}</b>分）&nbsp;&nbsp;
                            （最低分<b>${exam.lowestMark}</b>分）&nbsp;&nbsp;
                            （备注：${exam.remark}）
                        </label><br>
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>学生编号</th>
                                <th>学生姓名</th>
                                <th>排名</th>
                                <th>分数</th>
                                <th>备注</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${examMarks}" var="em">
                                <tr>
                                    <td>${em.studentId}</td>
                                    <td>${em.studentName}</td>
                                    <td>${em.rank}</td>
                                    <td>${em.mark}</td>
                                    <td>${em.remark}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <button class="btn btn-info" type="button" onclick="location.href='${ctx}/manager/exams'">
                            <i class="icon-arrow-left"></i> 返回
                        </button>
                    </form>
                </div><!--PAGE CONTENT ENDS-->
            </div><!--/.row-fluid-->
        </div><!--/.page-content-->
    </div><!--/.main-content-->
</div><!--/.main-container-->
<%@include file="./common/js.jsp" %>
<script type="text/javascript">
    $(function () {
        $('#add').on('click', function () {
            var flag = true;
            $(".mark").each(function () {
                if ($(this).val() == "" || isNaN($(this).val()) || $(this).val() < 0 || $(this).val() > ${exam.fullMarks}) {
                    alert('分数填写有误！');
                    flag = false;
                    return false;
                }
            });
            if (flag) $("#addexammark").submit();
        });
    });
</script>
</body>
</html>