package com.kennedy.lcnpay.service;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.kennedy.lcnpay.entity.TblPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RedisService
 * @Description 测试 TCC LCN 混合模式
 * @Author kennedyhan
 * @Date 2020/10/17 0017 23:25
 * @Version 1.0
 **/
@Service
public class RedisService {

    private static Map<String,Integer> maps = new HashMap<>();

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @TccTransaction
    public String addPay(@RequestBody TblPay bean){
        BoundValueOperations<String, String> pay = redisTemplate.boundValueOps("pay");
        pay.set("pay-value");
//        int i = 1/0;
        return "新增支付成功";

    }

    public String confirmAddPay(TblPay bean){
        System.out.println("pay confirm");
        return "新增支付成功";
    }

    /**
     * 逆sql
     * @param bean
     * @return
     */
    public String cancelAddPay(TblPay bean){
        redisTemplate.delete("pay");
        System.out.println("pay cancel");
        return "取消支付成功";
    }
}
