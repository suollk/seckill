package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillExpection;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by suollk on 2016/7/19.
 */
public class SeckillServiceImpl implements SeckillService {
    //日志API
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SeckillDao seckillDao;
    private SuccessKilledDao successKilledDao;


    //用于混淆MD5
    public final String slat = "a1b2c3d4~!@#$%^&*()";

    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,10);
    }

    /**
     * 获取单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 秒杀开启时输出秒杀地址
     * 否则输出系统时间以及秒杀时间
     *
     * @param seckillId
     */
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);

        if(seckill == null){
            return  new Exposer(false,seckillId);
        }

        Date startTime = seckill.getStartTime();

        Date endTime = seckill.getEndTime();

        Date nowTime = new Date();

        if(nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转化特定字符串的操作
        String md5 = getMD5(seckillId);  //TODO

        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base = seckillId+"/"+slat;

        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());

        return md5;
    }

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    @Override
    public SeckillExcution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillExpection, RepeatKillException, SeckillCloseException {
        if(md5 == null || md5.equals(getMD5(seckillId))){




        }


        return null;
    }
}
