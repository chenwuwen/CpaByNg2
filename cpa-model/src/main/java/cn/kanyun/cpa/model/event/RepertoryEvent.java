package cn.kanyun.cpa.model.event;

import cn.kanyun.cpa.model.entity.itempool.CpaRepertory;

/**
 * 题库改变事件
 */
public class RepertoryEvent extends CpaEvent<CpaRepertory> {
    public RepertoryEvent(CpaRepertory getSource) {
        super(getSource);
    }
}
