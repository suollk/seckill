package org.seckill.dao;

import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by suollk on 2016/7/4.
 */
public interface SeckillDao {
    /**
     * 减库存
      * @param seckillId
     * @param killTime
     * @return
     * 返回库存的数量
     */
    int reduceNumber(long seckillId,Date killTime);

    /**
     * 根据ID  获取秒杀详情
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀详情
     * @param offet
     * @param limit
     * @return
     */
    List<Seckill> queryAll(int offet,int limit);

}
