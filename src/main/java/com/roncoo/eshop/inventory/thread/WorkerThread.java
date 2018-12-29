package com.roncoo.eshop.inventory.thread;

import com.roncoo.eshop.inventory.request.Request;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @Auther: tianyu
 * @Date: 2018/12/29 22:21
 * @Description:  执行请求的工作线程
 */
public class WorkerThread implements Callable<Boolean> {

    /**
     * 自己监控的内存队列
     */
    private List<ArrayBlockingQueue<Request>> queue;

    public WorkerThread(List<ArrayBlockingQueue<Request>> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        return null;
    }
}
