package com.roncoo.eshop.inventory.thread;

import com.roncoo.eshop.inventory.constants.Constants;
import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.request.RequestQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: tianyu
 * @Date: 2018/12/29 22:04
 * @Description:  请求处理线程池:单例
 */
public class RequestProcessorThreadPool {

    /**
     * 线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(Constants.FIXED_THREAD_NUM);
    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queue = new ArrayList<ArrayBlockingQueue<Request>>();

    private RequestProcessorThreadPool() {
        RequestQueue requestQueue = new RequestQueue();
        for(int i = 0; i < Constants.FIXED_THREAD_NUM; i++) {
            requestQueue.addQueue(new ArrayBlockingQueue<Request>(100));
            threadPool.submit(new WorkerThread(queue));
        }
    }
    private static class Singleton {

        private static RequestProcessorThreadPool instance;

        static {
            instance = new RequestProcessorThreadPool();
        }

        public static RequestProcessorThreadPool getInstance() {
            return instance;
        }
    }

    public static RequestProcessorThreadPool getInstance() {
        return Singleton.getInstance();
    }

    public static void init() {
        getInstance();
    }

}
