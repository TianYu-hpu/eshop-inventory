package com.roncoo.eshop.inventory.request;

import com.roncoo.eshop.inventory.constants.Constants;
import com.roncoo.eshop.inventory.thread.WorkerThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: tianyu
 * @Date: 2018/12/29 22:27
 * @Description: 请求内存队列
 */
public class RequestQueue {

    /**
     * 线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(Constants.FIXED_THREAD_NUM);
    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queue = new ArrayList<ArrayBlockingQueue<Request>>();

    public RequestQueue() {
        for(int i = 0; i < Constants.FIXED_THREAD_NUM; i++) {
            queue.add(new ArrayBlockingQueue<Request>(100));
            threadPool.submit(new WorkerThread(queue));
        }
    }
    private static class Singleton {

        private static RequestQueue instance;

        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }
    }

    public static RequestQueue getInstance() {
        return RequestQueue.Singleton.getInstance();
    }

    /**
     * 添加一个内存队列
     * @param queue
     */
    public void addQueue(ArrayBlockingQueue<Request> queue) {
        this.queue.add(queue);
    }
}
