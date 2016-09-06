package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by suollk on 2016/7/28.
 */
@Controller//@Service component
@RequestMapping("/seckill")//url:/模块/资源/{id}/细分 /seckill/list
public class SeckillController {
    //日志API
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public  String list(Model model){
        //list.jsp + model =ModelAndView
        List<Seckill> list = seckillService.getSeckillList();

        model.addAttribute("list",list);
        //list.jsp+modal = ModalAndView

        return "list";///WEB-INF/jsp/"list".jsp
    }


    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if(seckillId==null){
            return "redirect:/seckill/list";
        }
        Seckill seckillById = seckillService.getById(seckillId);
        if(seckillById==null){
            return "forward:/seckill/list";
        }
        logger.info("detail");
        model.addAttribute("seckill",seckillById);
        return "detail";
    }
    // ajax json
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try{
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }
    //两个url参数  一个cookie带入
    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExcution> excute(@PathVariable("seckillId") Long seckillId,
                                                 @PathVariable("md5") String md5,
                                                 @CookieValue(value = "killPhone",required = false) Long phone){
        //springmvc valid
       if(phone == null){
           return new SeckillResult<SeckillExcution>(false,"未注册");
       }

        SeckillResult<SeckillExcution> result;
        //异常全部转为内部异常
        try{
            SeckillExcution excution = seckillService.executeSeckill(seckillId,phone,md5);
            result = new SeckillResult<SeckillExcution>(true,excution);
        }catch(RepeatKillException e1){
            SeckillExcution excution = new SeckillExcution(seckillId, SeckillEnum.REPERTKILL);
            result = new SeckillResult<SeckillExcution>(false,excution);
        }catch(SeckillCloseException e2){
            SeckillExcution excution = new SeckillExcution(seckillId, SeckillEnum.END);
            result = new SeckillResult<SeckillExcution>(false,excution);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            SeckillExcution excution = new SeckillExcution(seckillId, SeckillEnum.INNERERROT);
            result = new SeckillResult<SeckillExcution>(false,excution);
        }

        return result;
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long>  time(){
        Date now = new Date();
        return  new SeckillResult<Long>(true,now.toString());
    }
}
