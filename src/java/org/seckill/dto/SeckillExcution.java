package org.seckill.dto;

import org.seckill.entity.SuccessKilld;
import org.seckill.enums.SeckillEnum;

/**
 * Created by suollk on 2016/7/19.
 */
public class SeckillExcution {
    //
    private long seckillId;
    //秒杀状态
    private int state;

    //秒杀信息
    private String stateInfo;

    //秒杀成功对象
    private SuccessKilld successKilld;

    public SeckillExcution(long seckillId, SeckillEnum seckillEnum, SuccessKilld successKilld) {
        this.seckillId = seckillId;
        this.state = seckillEnum.getState();
        this.stateInfo = seckillEnum.getStateInfo();
        this.successKilld = successKilld;
    }

    //失败状态
    public SeckillExcution(long seckillId, SeckillEnum seckillEnum) {
        this.seckillId = seckillId;
        this.state = seckillEnum.getState();
        this.stateInfo = seckillEnum.getStateInfo();
    }

    @Override
    public String toString() {
        return "SeckillExcution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilld=" + successKilld +
                '}';
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilld getSuccessKilld() {
        return successKilld;
    }

    public void setSuccessKilld(SuccessKilld successKilld) {
        this.successKilld = successKilld;
    }
}
