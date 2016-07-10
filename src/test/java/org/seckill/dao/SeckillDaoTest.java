package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by suollk on 2016/7/10.
 * 配置spring和junit整合,junit启动时加载springIOC容器
 * spring-test,junit
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
//    注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;


    @Test
    public void reduceNumber() throws Exception {
        int number = seckillDao.reduceNumber(3L,new Date());
        System.out.println(number==1);
    }

    // 单元测试模拟
    @Test
    public void queryById() throws Exception {
        long id = 1;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> list = seckillDao.queryAll(0,10);
        System.out.println(list.toString());
    }

}