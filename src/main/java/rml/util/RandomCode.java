package rml.util;

import java.util.Random;

/**
 * Created by Administrator on 2015/9/13.
 */
public class RandomCode {
    public static String getRandomCode(int length){
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for(int i=0;i<length;i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
