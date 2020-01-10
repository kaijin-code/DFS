package com.neo.service;

import com.neo.listener.OrderOverTimeClose;
import com.neo.model.OrderInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderPay {

    static String[] str = new String[]{"成功","支付中","订单初始化"};

    public static String getTime(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String currentTime = formatter.format(date);
        return currentTime;
    }

    public static void main(String[] args) throws InterruptedException {


        OrderOverTimeClose.getInstance().init();

        for (int i = 0; i < 20; i++) {


            // 创建初始订单
            long createTime = System.currentTimeMillis();
            String currentTime = getTime(createTime);
            String overTime = getTime(createTime + 10000);// 十秒后超时
            String orderNo = String.valueOf(new Random().nextLong());

            OrderInfo order = new OrderInfo();
            order.setOrderNo(orderNo);
            order.setExpTime(overTime);
            int random_index = (int) (Math.random()*str.length);
            order.setStatus(str[random_index]);// 随机分配
            order.setCreateTime(currentTime);
            OrderOverTimeClose.getInstance().orderPutQueue(order, currentTime, overTime);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
