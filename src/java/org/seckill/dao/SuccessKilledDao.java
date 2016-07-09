package org.seckill.dao;

import org.seckill.entity.SuccessKilld;

/**
 * Created by suollk on 2016/7/4.
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细,可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(long seckillId,long userPhone);

    /**
     * 根据id查询秒杀情况
     * @param seckillid
     * @return
     */
    SuccessKilld queryByIdWithSeckill(long seckillid);

}
