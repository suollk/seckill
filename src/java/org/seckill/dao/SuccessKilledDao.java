package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
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
     * 返回插入的行数量
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 根据id查询秒杀情况
     * @param seckillid
     * @return
     */
    SuccessKilld queryByIdWithSeckill(@Param("seckillid") long seckillid, @Param("userPhone")long userPhone);

}
