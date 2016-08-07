<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <%@include file="common/head.jsp"%>
    <title>秒杀列表页</title>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading">${seckill.name}</div>
            <div class="panel-body">
                <h2 class="text-danger">
                    <%--显示time图标--%>
                    <span class="glyphicon glyphicon-time"></span>
                    <%--显示倒计时--%>
                    <span class="glyphicon" id="seckill-box"></span>
                </h2>
            </div>
        </div>
    </div>

    <%--登录弹出层,输入电话--%>
    <div class="modal fade" id="killPhoneModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <span class="glphyicon glphyicon-phone"></span>秒杀电话:
                    </h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8 clo-xs-offset-2">
                            <input type="text" name="killPhone" id="killPhoneKey" placeholder="填写手机号^o^" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div style="display: none;margin-bottom:20px;padding-top: 0;padding-bottom: 0;" role="alert" id="killPhoneMessage">
                        <strong>Warning!</strong> 手机号错误!
                    </div>
                    <button type="button" id="killPhoneBtn" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone"></span>
                        submit
                    </button>
                </div>
            </div>
        </div>
    </div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/jsp/resources/script/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/jsp/resources/script/bootstrap.min.js"></script>

<%--cdn获取静态资源--%>
<%--Jquery倒计时插件--%>
<script src="/jsp/resources/script/jquery.countdown.min.js"></script>
<%--Jquery cookie操作插件--%>
<script src="/jsp/resources/script/jquery.cookie.js"></script>
<%--开始编写交互逻辑--%>
<script src="/jsp/resources/script/seckill.js" type="application/javascript"></script>

<script type="application/javascript">
    $(function () {
        //使用EL表达式传入参数
        seckill.detail.init({
            seckillId:${seckill.seckillId},
            startTime:${seckill.startTime.time},//毫秒
            endTime:${seckill.endTime.time}
        })
    })
</script>
</body>


</html>
