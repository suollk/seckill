package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilld;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.util.resources.cldr.ga.LocaleNames_ga;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by suollk on 2016/7/10.
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1000L;
        long phone = 13527269169L;
        int insertCount = successKilledDao.insertSuccessKilled(id,phone);
        System.out.println("insertCount="+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessKilld successKilld = successKilledDao.queryByIdWithSeckill(1L,13527269169L);
        System.out.println(successKilld);
        System.out.println("------------------------------");
        System.out.println(successKilld.getSeckill());
    }

}