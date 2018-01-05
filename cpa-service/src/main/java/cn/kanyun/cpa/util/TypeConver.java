package cn.kanyun.cpa.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KANYUN on 2017/9/10.
 * 类型转换工具类
 */
public class TypeConver {

    /**
     * @describe: List转Map, 需要注意的是List中本身存放的就是Map
     * @params: List：List集合,需要List本身存放的就是Map, key:List中存放Map的key,key变长参数（两个点），也即Map中可以有多个Key，但需要注意key的顺序
     * 虽说可以是多个Key，但实际上也就只有两个Key(因为Map中每一个Item都是有键值对组成,所以传入的List的Size即时新Map的Size,而List中的每一个Item即是
     * 新Map中的每一Item,所以说有且只能有两个Key)，因为这个List里的每一个Item里面放的是Map且是两个Map，这两个Map，分别组成了键值对的Key和 Value
     * @Author: Kanyun
     * @Date: 2018/1/4 0004 22:20
     */
    /**
     * 一个方法只能有一个可变长参数，并且这个可变长参数必须是该方法的最后一个参数
     */

    /**
     * 最初写法
     */
    public static Map List2Map(List<Map<Object, Object>> list, String key1, String key2) {
        Map resultMap = new HashMap();
        for (Map map : list) {
            resultMap.put(map.get(key1), map.get(key2));
        }
        return resultMap;
    }

    /**
     * 变长方式在这里并不适合
     */
/*    public static Map List2Map(List<Map<Object, Object>> list, String... keys) {
        Map resultMap = new HashMap();
        for (Map map : list) {
            resultMap.put(map.get(keys[0]), map.get(keys[1]));

        }
        return resultMap;
    }*/
}
