package com.kennedy.lcnpay.controller;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.kennedy.lcnpay.dao.TblPayDao;
import com.kennedy.lcnpay.entity.TblPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName PayTccController
 * @Description 测试 TCC事务
 * @Author kennedyhan
 * @Date 2020/10/17 0017 22:48
 * @Version 1.0
 **/
@RestController
public class PayTccController {

    private static Map<String,Integer> maps = new ConcurrentHashMap<>();

    @Autowired
    private TblPayDao tblPayDao;

    @PostMapping("/add-pay-tcc")
    @Transactional(rollbackFor = Exception.class)
    @TccTransaction
    public String addPay(@RequestBody TblPay bean){
        tblPayDao.insert(bean);
        Integer id = bean.getId();
        maps.put("test-key",id);
//        int i = 1/0;
        return "新增支付成功";
    }

    public String confirmAddPay(TblPay bean){
        System.out.println("pay confirm");
        return "confirm---新增支付成功";
    }

    /**
     * 逆sql
     * @param bean
     * @return
     */
    public String cancelAddPay(TblPay bean){
        Integer key = maps.get("test-key");
        System.out.println("key: " + key);
        System.out.println("pay cancel");
        tblPayDao.deleteByPrimaryKey(key);
        return "cancel---取消支付成功";
    }
}
