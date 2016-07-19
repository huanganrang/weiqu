import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rml.server.HouseKeeper;
import rml.server.NettyServer;

import java.io.IOException;

/**
 * Created by Administrator on 2015/9/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-mybatis.xml","classpath:/spring.xml"})
public class SpringRedisDemo {

    @Autowired
    RedisTemplate jedisTemplate;

    @Test
    public void putAndGet(){
       /* NettyServer nettyServer=HouseKeeper.getServer("test1");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}