package com.neo.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class OrderInfo implements Serializable, Delayed {
    private static final long serialVersionUID = 1L;
    private String orderNo;// 订单号
   private String status;// 订单状态
   private String expTime;// 订单过期时间
    private String createTime;//订单创建时间

    @Override
    public long getDelay(TimeUnit unit) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            time = formatter.parse(this.expTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 long nowThreadtime = 0;
                 long queueThreadtime = 0;
                 try {
                         nowThreadtime = formatter.parse(this.expTime).getTime();
                        queueThreadtime = formatter.parse(((OrderInfo)o).expTime).getTime();
                     } catch (ParseException e) {
                         e.printStackTrace();
                     }
             return Long.valueOf(nowThreadtime).compareTo(Long.valueOf(queueThreadtime));
    }


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
