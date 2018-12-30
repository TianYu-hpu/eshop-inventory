package com.roncoo.eshop.inventory.thread;

import com.roncoo.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.roncoo.eshop.inventory.request.ProductInvetoryDBUpdateRequest;
import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.request.RequestQueue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @Auther: tianyu
 * @Date: 2018/12/29 22:21
 * @Description:  执行请求的工作线程
 */
public class RequestProcessorThread implements Callable<Boolean> {

    /**
     * 自己监控的内存队列
     */
    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            while (true) {
                //Blocking就是说明，如果队列满了，或者是空的，那么都会在执行操作的时候，阻塞住
                Request request = queue.take();
                request.process();
                /**
                 * 假如说，执行完了一个读请求之后，假设数据已经刷新到redis中了
                 * 但是后面可能redis中的数据会因为内存满了，被自动清理掉
                 * 如果说数据从redis中被自动清理掉以后
                 * 然后后面又来了一个读请求，此时如果进来，发现标志位是false，就不会执行这个刷新的操作了
                 * 所以在执行完读请求之后，实际上这个标志是停留在false，就hang一会
                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
