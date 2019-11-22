package cn.kanyun.cpa.model.event;

import lombok.Data;

@Data
public abstract class CpaEvent<T> {

    T getSource;

    public CpaEvent(T getSource) {
        this.getSource = getSource;
    }


}
