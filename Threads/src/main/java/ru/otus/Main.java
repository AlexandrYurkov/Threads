package ru.otus;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Service service = new Service(25);
        for(int i = 0; i < 100; i++) {
            int finalI = i + 1 ;
            service.execute(() ->{
                System.out.println("Thread :" + finalI + " -> run!)");
            });
            service.shutdown();
        }

        service.start();

    }
}