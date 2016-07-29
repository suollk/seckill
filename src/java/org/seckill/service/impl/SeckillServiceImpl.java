package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilld;
import org.seckill.enums.SeckillEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillExpection;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import java.util.Date;
import java.util.List;

/**
 * Created by suollk on 2016/7/19.
 * @Component   @Service  @Dao @Controller
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    //日志API
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //注入Service依赖   @Resource @Inject

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
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
    @Transactional
    /**
     * 使用注解控制实物方法的优点
     * 1:开发团队达成唯一的约定,明确事务方法的编程风格
     * 2:保证事务方法的执行时间尽可能的短,不要穿插其他的网络操作,RPC/HTTP请求或者剥离到事务方法外
     * 3:不是所有的方法都需要事务如只有一条修改操作的时候  或者是Select不需要事务
     */
    public SeckillExcution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillExpection, RepeatKillException, SeckillCloseException {
        //判断MD5是否正确
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillExpection("seckill data rewrite");
        }
        Date nowTime = new Date();

        try {
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
            if (updateCount <= 0){
                //没有更新到记录,意味着已经结束了
                throw  new SeckillCloseException("seckill is cloased");
            }
            //开始插入成功描述记录
            int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
            if (insertCount <= 0){
                //重复秒杀
                throw  new RepeatKillException("seckill repeated");
            }else{
                //秒杀成功
                SuccessKilld successKilld = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExcution(seckillId, SeckillEnum.SUCCESS,successKilld);
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译器的异常转化为运行期异常
            throw new SeckillExpection("seckill inner error"+e.getMessage());
        }
    }
}
