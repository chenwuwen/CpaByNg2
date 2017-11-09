package cn.kanyun.cpa.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KANYUN on 2017/9/10.
 *  类型转换工具类
 */
public class TypeConver {
    public static Map List2Map(List<Map<Object, Object>> list, String key1, String key2) {
        Map resultMap = new HashMap();
        for (Map map : list) {
            resultMap.put(map.get(key1), map.get(key2));
        }
        return resultMap;
    }
}
