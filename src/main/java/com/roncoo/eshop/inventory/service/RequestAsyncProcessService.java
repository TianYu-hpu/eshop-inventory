package com.roncoo.eshop.inventory.service;

import com.roncoo.eshop.inventory.request.Request;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Auther: tianyu
 * @Date: 2018/12/30 19:30
 * @Description:  请求异步执行的Service
 */
public interface RequestAsyncProcessService {

    void process(Request request);

    ArrayBlockingQueue<Request> getRouteKey(Integer productId);

}
