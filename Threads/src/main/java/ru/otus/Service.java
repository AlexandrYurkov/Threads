package ru.otus;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service {
    private ExecutorService service;
    private int capacity = 5;
    private ArrayBlockingQueue<Runnable> runnables = new ArrayBlockingQueue<>(capacity);

    private boolean isShutdown = false;



    Service(int countPool){
        service = Executors.newFixedThreadPool(countPool);
    }

    public void execute(Runnable r) throws InterruptedException {
        try {
            if (!isShutdown){
                if(runnables.size() >= capacity)
                    start();
                setRunnables(r);
            }
            else{
                while (!runnables.isEmpty())
                    start();
                throw new IllegalStateException("Потоки завершены");
            }
        } catch (InterruptedException e){
            System.out.println("Очередь переполнена, попробуйте позже");
        }

    }

    public void setRunnables(Runnable r) throws InterruptedException {
        this.runnables.put(r);
    }

    public void shutdown(){
        this.isShutdown = true;
        service.shutdown();
    }

    public void start() throws InterruptedException {
        Runnable r = this.runnables.take();
        r.run();
    }
}
