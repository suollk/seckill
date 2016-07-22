package org.seckill.enums;

/**
 * 使用枚举表示我们的常量数据
 * Created by suollk on 2016/7/22.
 */
public enum SeckillEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPERTKILL(-1,"秒杀重复"),
    INNERERROT(-2,"系统异常"),
    REWRITE(-3,"数据窜改");

    private int state;

    private String stateInfo;

    SeckillEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static  SeckillEnum stateof(int index){
        for(SeckillEnum state :values()){
          if (state.getState() == index) {
              return state;
          }
        }
        return null;
    }
}
