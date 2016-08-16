package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by suollk on 2016/8/16.
 */
public class RedisDao {
    //日志API
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final  JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip,int port){
        jedisPool = new JedisPool(ip,port);
    }


    public Seckill getSeckill(long seckillId){
        //redis操作逻辑
        try{
            Jedis jedis = jedisPool.getResource();
            try{

                String key = "seckill:"+seckillId;
                //并没有实现内部序列化操作
                //get->byte[]->返序列化->object
                //采用自定义序列化
                //protostuff:pojo(包含getset方法的class)
                byte[] bytes = jedis.get(key.getBytes());
                //代表缓存中获取到
                if(bytes != null){
                    //空对象
                    Seckill seckill = schema.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes,seckill,schema);

                    return seckill;
                }
            }finally{
                jedis.close();
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill){
        //set object(Seckill)->序列化 ->byte[]

        try{
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "seckill:"+seckill.getSeckillId();
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout= 60*60;
                String result = jedis.setex(key.getBytes(),timeout,bytes);
                return result;
            }finally{
                jedis.close();
            }

        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }

        return "";
    }



}
