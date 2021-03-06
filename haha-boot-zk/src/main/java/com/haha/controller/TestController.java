package com.haha.controller;

import com.haha.service.OrderService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/***
 * @Author 郭嘉   QQ:2790284115
 * @Slogan 致敬大师，致敬未来的你
 */
@RestController
public class TestController {


    @Autowired
    private OrderService orderService;

    @Value("${server.port}")
    private String port;



    @Autowired
    CuratorFramework curatorFramework;

    @PostMapping("/stock/deduct")
    public Object reduceStock(Integer id) throws Exception {

        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/product_" + id);

        try {
            // ...
            interProcessMutex.acquire();
            orderService.reduceStock(id);

        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw e;
            }
        }finally {
            interProcessMutex.release();
        }
        return "ok:" + port;
    }


}
