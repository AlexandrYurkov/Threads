package ru.otus;

import static java.lang.System.exit;

public class ThreadPool {
   private final BlockingQueue<Runnable> queue;
   private boolean isShutdown = false;
    public ThreadPool(int queueSize) {
        queue = new BlockingQueue<>(queueSize);
        start(3);
    }

    public void execute(Runnable task) throws InterruptedException {
        if(isShutdown)
            throw new IllegalStateException("Потоки завершены!");
        queue.enqueue(task);
    }

    public void shutdown(){
        isShutdown = true;
        if(queue.size() >= 1)
            start(queue.size());
        exit(0);

    }

    public void start(int nThread){
        String threadName = null;
        Executor task = null;
        for (int count = 0; count < nThread; count++) {
            threadName = "Thread-"+count;
            task = new Executor(queue);
            Thread thread = new Thread(task, threadName);
            thread.start();
        }

    }


}
