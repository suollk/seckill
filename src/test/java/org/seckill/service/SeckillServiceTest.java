package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by suollk on 2016/7/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    //日志API
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //自动注入
    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill= seckillService.getById(1L);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 4;
        Exposer exposer =seckillService.exportSeckillUrl(id);

        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        Date nowTime = new Date();
        long id = 4;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("Date={}",nowTime);
        logger.info("exposer={}",exposer);
        // now   1469365114167      1469365114167
        // start=1469364572000, end=1469364572000

        SeckillExcution seckillExcution = seckillService.executeSeckill(id,13562268719L,exposer.getMd5());

        logger.info("url={}",seckillExcution);
    }

    @Test
    public void executeSeckillPro() throws Exception {
        Date nowTime = new Date();
        long id = 1;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("Date={}",nowTime);
        logger.info("exposer={}",exposer);

        SeckillExcution seckillExcution = seckillService.executeSeckillPro(id,13564468719L,exposer.getMd5());

        logger.info("url={}",seckillExcution);
    }



//    20:49:32.814 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@46271dd6] will not be managed by Spring
//    20:49:32.839 [main] DEBUG org.seckill.dao.SeckillDao.queryById - ==>  Preparing: select seckill_id as seckillId,name,number,start_time,end_time,create_time from seckill where seckill_id = ?;
//    20:49:32.861 [main] DEBUG org.seckill.dao.SeckillDao.queryById - ==> Parameters: 4(Long)
//    20:49:32.878 [main] DEBUG org.seckill.dao.SeckillDao.queryById - <==      Total: 1
//    20:49:32.883 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2b72cb8a]
//    20:49:32.939 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Creating a new SqlSession
//    20:49:32.939 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18e36d14]
//    20:49:32.942 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@3370f42] will be managed by Spring
//    20:49:32.942 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==>  Preparing: UPDATE seckill set number = number -1 where seckill_id = ? and start_time <= ? AND end_time >= ? and number>0;
//    20:49:32.945 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==> Parameters: 4(Long), 2016-07-24 20:49:32.936(Timestamp), 2016-07-24 20:49:32.936(Timestamp)
//    20:49:32.950 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - <==    Updates: 1
//    20:49:32.951 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18e36d14]
//    20:49:32.952 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18e36d14] from current transaction
//    20:49:32.953 [main] DEBUG o.s.d.S.insertSuccessKilled - ==>  Preparing: insert ignore into seckill_killd(seckill_id,user_phone) values (?,?)
//    20:49:32.955 [main] DEBUG o.s.d.S.insertSuccessKilled - ==> Parameters: 4(Long), 13527269169(Long)
//    20:49:32.958 [main] DEBUG o.s.d.S.insertSuccessKilled - <==    Updates: 1
//    20:49:32.959 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18e36d14]
//    20:49:32.960 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18e36d14] from current transaction
//    20:49:32.961 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==>  Preparing: select sk.seckill_id, sk.user_phone, sk.create_time, sk.state, s.seckill_id "seckill.seckill_id", s.name "seckill.name", s.number "seckill.number", s.start_time "seckill.start_time", s.end_time "seckill.end_time", s.create_time "seckill.create_time" FROM seckill_killd sk inner join seckill s on sk.seckill_id=s.seckill_id where sk.seckill_id = ? and user_phone = ?;
//    20:49:32.962 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==> Parameters: 4(Long), 13527269169(Long)
//    20:49:32.974 [main] DEBUG o.s.d.S.queryByIdWithSeckill - <==      Total: 1
//    20:49:32.974 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18e36d14]
//    20:49:32.976 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18e36d14]
//    20:49:32.976 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18e36d14]
//    20:49:32.976 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18e36d14]
//    20:49:32.981 [main] INFO  o.seckill.service.SeckillServiceTest - url=org.seckill.dto.SeckillExcution@7b8b43c7

}