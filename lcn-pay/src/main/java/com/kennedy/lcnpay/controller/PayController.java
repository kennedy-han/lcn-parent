package com.kennedy.lcnpay.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.kennedy.lcnpay.dao.TblPayDao;
import com.kennedy.lcnpay.entity.TblPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PayController
 * @Description 支付 Controller
 * @Author kennedyhan
 * @Date 2020/10/17 0017 11:05
 * @Version 1.0
 **/
@RestController
public class PayController {

    @Autowired
    private TblPayDao tblPayDao;

    /**
     * 测试分布式事务，被 order服务远程调用
     * @param bean
     * @return
     */
    @PostMapping("/add-pay")
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String addPay(@RequestBody TblPay bean){
        tblPayDao.insert(bean);
        return "新增支付成功";
    }
}
