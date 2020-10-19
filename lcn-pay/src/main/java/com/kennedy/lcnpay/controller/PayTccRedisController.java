package com.kennedy.lcnpay.controller;

import com.kennedy.lcnpay.dao.TblPayDao;
import com.kennedy.lcnpay.entity.TblPay;
import com.kennedy.lcnpay.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PayTccRedisController
 * @Description 测试 TCC，Redis双写一致性
 * @Author kennedyhan
 * @Date 2020/10/17 0017 23:20
 * @Version 1.0
 **/
@RestController
public class PayTccRedisController {

    @Autowired
    private TblPayDao tblPayDao;

    @Autowired
    private RedisService redisService;

    @PostMapping("/add-pay-tcc-redis")
    @Transactional(rollbackFor = Exception.class)
    public String addPay(@RequestBody TblPay bean){
        redisService.addPay(null);
        int i = 1/0;
        return "新增支付成功";

    }
}
