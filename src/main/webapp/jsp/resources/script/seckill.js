//存放交互逻辑代码
//javascript 模块化
var seckill = {
  //封装秒杀相关的地址
  URL: {
    //获取当前时间的url
    now: function() {
      return '/seckill/time/now';
    },
    seckillResult: function(seckillId) {
      return '/seckill/' + seckillId + '/exposer';
    },
    execution: function(seckillId, md5) {
      return 'seckill/' + seckillId + '/' + md5 + '/execution';
    }
  },
  //验证手机  后期改为正则表达式 TODO
  vaildatePhone: function(phone) {
    if (phone && phone.length == 11 && !isNaN(phone)) {
      return true;
    } else {
      return false;
    }
  },
  //倒计时方法
  countdown: function(seckillId, nowTime, startTime, endTime) {
    //时间判断
    var seckillbox = $('#seckill-box');
    if (nowTime > endTime) {
      seckillbox.hide().html('秒杀已经结束').show();
    } else if (nowTime < startTime) {
      var killTime = new Date(startTime + 1000);
      seckillbox.countdown(killTime, function(event) {
        var format = event.strftime('秒杀计时:%D天%H时%M分%S秒');
        seckillbox.html(format);
        //时间完成后回调事件
      }).on('finish.countdown', function() {
        //获取秒杀地址,控制显示逻辑,执行秒杀
      });
    }
    //计时开始
    else {

    }
  },
  //秒杀开始
  handleSeckillkill: function(seckillId, node) {
    //处理秒杀逻辑
    node.hide().html('<button class="btn btn-primary btn-lg">秒杀</button>');
    $.post(seckill.URL.seckillResult(seckillId), function(result) {
      if (result && result.success) {
        //回调函数中执行交互流程
        var exposer = result.exposer;

        if (exposer.exposed) {
          //开启秒杀
          //获取秒杀地址

          var killurl = seckill.URL.execution(seckillId, exposer.md5);
          node.one('click', function() {
            $(this).addClass("disabled");

            $.post(killurl, {}, function(result) {
              if (result || result.SUCCESS == true) {
                //获取到结果集
                var state = result.state;
                var info = result.stateInfo;
                node.html('<button class="btn btn-primary btn-lg">' + info + '</button>');
              }else{
                console.log(result);
              }
            });
          })

          node.show('300', function() {
            console.log('按钮显示');
          });

        } else {
          //未开启时间  重新计算计时逻辑
          seckill.countdown(seckillId, exposer.now, exposer.start, exposer.end);
        }
      } else {
        console(result);
      }
    });
  },
  //详情页秒杀逻辑
  detail: {
    //详情页初始化
    init: function(params) {
      //用户手机验证和登录,计时交互
      //规划交互流程
      //在cookie中查找手机号
      var killPhone = $.cookie('killPhone');
      //验证手机号

      if (!seckill.vaildatePhone(killPhone)) {
        //绑定手机号
        var killPhoneModal = $('#killPhoneModal');

        killPhoneModal.modal({
          //显示弹出层
          show: true,
          //禁止位置关闭
          backdrop: 'static',
          //关闭键盘关闭
          keyboard: false
        });
      }

      var startTime = params.startTime;
      var endTime = params.endTime;
      var seckillId = params.seckillId;
      //已经登录  //计时交互
      $.get(seckill.URL.now(), {}, function(result) {
        if (result && result.success) {
          var nowTime = result.data;
          seckill.countdown(seckillId, nowTime, startTime, endTime);
        } else {
          console(result);
        }
      });

      $('#killPhoneBtn').click(function() {
        var inputPhone = $('#killPhoneKey').val();
        if (seckill.vaildatePhone(inputPhone)) {
          //电话写入cookie
          $.cookie('killPhone', inputPhone, {
            expires: 7,
            path: '/seckill'
          });
          //刷新
          window.location.reload();
        } else {
          $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
        }
      });

      $('killPhoneKey').onfocus = function() {
        $('#killPhoneMessage').hide();
      }
    }
  },

}