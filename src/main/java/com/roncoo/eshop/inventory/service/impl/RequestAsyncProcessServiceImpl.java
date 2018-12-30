package com.roncoo.eshop.inventory.service.impl;

import com.roncoo.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.roncoo.eshop.inventory.request.ProductInvetoryDBUpdateRequest;
import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.request.RequestQueue;
import com.roncoo.eshop.inventory.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Auther: tianyu
 * @Date: 2018/12/30 19:34
 * @Description:  请求异步执行的Service实现
 */
@Service
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

    @Override
    public void process(Request request) {
        try {
            //请求的路由，根据每个商品的id路由到请求队列中去
            ArrayBlockingQueue<Request>  queue = getRouteKey(request.getProductId());
            //将请求放入到对应的内存队列中，完成路由操作
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 路由到对应的请求队列
     * @param productId 商品id
     * @return 内存队列
     */
    @Override
    public ArrayBlockingQueue<Request> getRouteKey(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();
        int h;
        int hash = (productId == null) ? 0 : (h = productId.hashCode()) ^ (h >>> 16);
        //对hash值取模，将hash值路由到指定的内存队列中
        int index = (requestQueue.queueSize() - 1) & hash;
        return requestQueue.getQueue(index);
    }


}
