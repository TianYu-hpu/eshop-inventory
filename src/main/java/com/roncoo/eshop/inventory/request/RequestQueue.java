package com.roncoo.eshop.inventory.request;

import com.roncoo.eshop.inventory.constants.Constants;
import com.roncoo.eshop.inventory.thread.RequestProcessorThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
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

    public int queueSize() {
        return this.queue.size();
    }

    /**
     * 获取内存队列
     * @param index
     * @return
     */
    public ArrayBlockingQueue<Request> getQueue(int index) {
        return this.queue.get(index);
    }

}
