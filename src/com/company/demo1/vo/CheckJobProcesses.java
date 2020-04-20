package com.company.demo1.vo;

import java.util.concurrent.DelayQueue;

/**
 * 用来查询过期队列
 */
public class CheckJobProcesses {
    private static final DelayQueue<ItemVo<String>> queue = new DelayQueue<>();//存放已完成任务等待过期的队列

    //单例模式------
    private CheckJobProcesses() {
    }

    private static class ProcessesHolder {
        public static CheckJobProcesses processes = new CheckJobProcesses();
    }

    public static CheckJobProcesses getInstance() {
        return ProcessesHolder.processes;
    }
    //单例模式------

    //处理队列中到期任务的实行
    private static class FetchJob implements Runnable {

        @Override
        public void run() {
            try {
                for(;;){
                    //拿到已经过期的任务
                    ItemVo<String> item = queue.take();
                    String jobName = item.getDate();
                    PendingJobPool.getMap().remove(jobName);
                    System.out.println(jobName + " is out of date,remove from map!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*任务完成后，放入队列，经过expireTime时间后，从整个框架中移除*/
    public void putJob(String jobName, long expireTime) {
        ItemVo<String> item = new ItemVo<>(expireTime, jobName);
        queue.offer(item);
        System.out.println("Job[" + jobName + "已经放入了过期检查缓存，过期时长：" + expireTime);
    }

    static {
        Thread thread = new Thread(new FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("开启任务过期检查守护线程................");
    }
}
