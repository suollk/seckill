//存放交互逻辑代码
//javascript 模块化
var seckill={
    //封装秒杀相关的地址
    URL: {
        now:function () {
            return '/seckill/time/now';
        }
    },
    //验证手机  后期改为正则表达式 TODO
    vaildatePhone:function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    //倒计时方法
    countdown:function (seckillId,nowTime,startTime,endTime) {
        //时间判断
        var seckillbox = $('#seckill-box');
        if(nowTime> endTime){
            seckillbox.hide().html('秒杀已经结束').show();
        }
        else if(nowTime<startTime){
            var killTime = new Date(startTime + 1000);
            seckillbox.countdown(killTime,function(event){
                var format = event.strftime('秒杀计时:%D天%H时%M分%S秒');
                seckillbox.html(format);
            });
        }
        //计时开始
        else{

        }
    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
       init:function(params){
           //用户手机验证和登录,计时交互
           //规划交互流程
           //在cookie中查找手机号
           var killPhone = $.cookie('killPhone');
           //验证手机号

           if(!seckill.vaildatePhone(killPhone)){
               //绑定手机号
               var killPhoneModal = $('#killPhoneModal');

               killPhoneModal.modal({
                   //显示弹出层
                   show:true,
                   //禁止位置关闭
                   backdrop:'static',
                   //关闭键盘关闭
                   keyboard:false
               });
           }

           var startTime = params.startTime;
           var endTime = params.endTime;
           var seckillId = params.seckillId;
           //已经登录  //计时交互
           $.get(seckill.URL.now(),{},function(result){
                if(result && result.success){
                    var nowTime = result.data;
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }else{
                    console(result);
                }
           });

           $('#killPhoneBtn').click(function () {
               var inputPhone = $('#killPhoneKey').val();
               if(seckill.vaildatePhone(inputPhone)){
                   //电话写入cookie
                   $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                   //刷新
                   window.location.reload();
               } else{
                   $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
               }
           });

           $('killPhoneKey').onfocus = function () {
               $('#killPhoneMessage').hide();
           }
       }
    },

}