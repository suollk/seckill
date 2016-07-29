<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入jstl--%>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <%--引入jstl--%>
    <title>秒杀详情页</title>
    <%--静态包含会把jsp放在外面  会合并--%>
    <%@include file="common/head.jsp"%>
</head>
<body>
    <%--页面开发显示部分--%>

    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>秒杀列表</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>库存</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>创建时间</th>
                            <th>详情页</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="sk" items="${list}">
                            <tr>
                            <th>${sk.name}</th>
                            <th>${sk.number}</th>
                            <th>
                                <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss">
                            </th>
                            <th><fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"></th>
                            <th><fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></th>
                            <th>
                                <a class="btn btn-info" href="/seckill/${sk.seckillId}/detail" target="_blank">
                            </th>
                            </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
