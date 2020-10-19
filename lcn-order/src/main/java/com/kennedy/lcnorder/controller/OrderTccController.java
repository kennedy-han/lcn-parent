package com.kennedy.lcnorder.controller;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.kennedy.lcnorder.dao.TblOrderDao;
import com.kennedy.lcnorder.entity.TblOrder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName OrderTccController
 * @Description 用于测试 TCC事务
 * @Author kennedyhan
 * @Date 2020/10/17 0017 22:36
 * @Version 1.0
 **/
@RestController
public class OrderTccController {

    @Autowired
    private TblOrderDao tblOrderDao;

    @Autowired
    private RestTemplate restTemplate;

    private static Map<String,Integer> maps = new ConcurrentHashMap<>();

    @PostMapping("/add-order-tcc")
    @Transactional(rollbackFor = Exception.class)
    @TccTransaction
    public String add(@RequestBody TblOrder bean) {
        JSONObject date = new JSONObject();
        date.put("payName", bean.getOrderName());

        restTemplate.postForEntity("http://lcn-pay/add-pay-tcc", date, String.class);

        tblOrderDao.insert(bean);
        Integer id = bean.getId();
        maps.put("test-key", id);   //这里的 KEY 建议改为硬件ID

        //        int i = 1/0;
        return "新增订单成功";
    }

    public String confirmAdd(TblOrder bean){
        System.out.println("order confirm ");
        return "confirm---新增订单成功";
    }

    public String cancelAdd(TblOrder bean){
        Integer key = maps.get("test-key");
        System.out.println("key: " + key);
        tblOrderDao.deleteByPrimaryKey(key);
        System.out.println("order cancel ");
        return "cancel---失败回滚订单";
    }
}
