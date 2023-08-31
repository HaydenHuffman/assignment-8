

package com.coderscampus.assignment8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        Assignment8 assignment8 = new Assignment8();
        ExecutorService pool = Executors.newCachedThreadPool();
        ConcurrentMap<Integer, Integer> numberFreq = new ConcurrentHashMap<>();
        AtomicInteger counter = new AtomicInteger(0);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        
        
        for (int x = 0; x < 1000; x++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                List<Integer> numbersList = Collections.synchronizedList(assignment8.getNumbers());
                updateFrequencyMap(numberFreq, numbersList);
            }, pool);
            futures.add(future);
            counter.incrementAndGet();
        }
        
       
       
       

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allTasks.join();

        // Print frequency results
        System.out.println("Frequency of unique numbers:");
        synchronized (numberFreq) {
            numberFreq.forEach((number, frequency) -> {
                System.out.println(number + "=" + frequency);
            });
        }

        // Shutdown the thread pool
//        pool.shutdown();
    }

    private static void updateFrequencyMap(ConcurrentMap<Integer, Integer> numberFreq, List<Integer> numbersList) {
        for (Integer number : numbersList) {
            synchronized (numberFreq) {
                numberFreq.merge(number, 1, Integer::sum);
            }
        }
    }
}