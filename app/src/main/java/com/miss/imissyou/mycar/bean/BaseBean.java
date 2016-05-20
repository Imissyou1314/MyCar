package com.miss.imissyou.mycar.bean;

import java.io.Serializable;
import java.util.UUID;

/**
 * 所有的Bean 都继承它
 *
 * Created by Imissyou on 2016/3/27.
 */
public abstract class BaseBean implements Serializable{

    public UUID uuid;

    /**
     * 唯一的识别标
     * @return
     */
    public UUID getUuid() {
        return UUID.randomUUID();
    }

}
