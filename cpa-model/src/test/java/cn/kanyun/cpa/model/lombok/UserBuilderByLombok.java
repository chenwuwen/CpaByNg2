package cn.kanyun.cpa.model.lombok;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by Administrator on 2018/8/1.
 * Lombok 建筑者模式
 */
@Builder
@Getter
public class UserBuilderByLombok {
    private String name;
    private int age;

}
