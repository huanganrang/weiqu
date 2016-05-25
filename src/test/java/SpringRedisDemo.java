import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2015/9/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-mybatis.xml")
public class SpringRedisDemo {

    @Autowired
    RedisTemplate jedisTemplate;

    @Test
    public void putAndGet(){
        jedisTemplate.opsForHash().put("user","name","张三");
        Object name =  jedisTemplate.opsForHash().get("user","name");
        System.out.println(name);
    }
}