package com.kennedy.lcnorder.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.kennedy.lcnorder.dao.TblOrderDao;
import com.kennedy.lcnorder.entity.TblOrder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName OrderController
 * @Description 测试 LCN事务
 * @Author kennedyhan
 * @Date 2020/10/17 0017 10:57
 * @Version 1.0
 **/
@RestController
public class OrderController {

    @Autowired
    private TblOrderDao tblOrderDao;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 测试分布式事务
     * 1. 调用 pay 服务
     * 2. 抛异常
     * 3. dao 保存
     *
     * @param bean
     * @return
     */
    @PostMapping("/add-order")
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String add(@RequestBody TblOrder bean){
        JSONObject date = new JSONObject();
        date.put("payName",bean.getOrderName()+"pay");

        restTemplate.postForEntity("http://lcn-pay/add-pay",date,String.class);

        int i = 1/0;
        tblOrderDao.insert(bean);
        return "新增订单成功";
    }
}
