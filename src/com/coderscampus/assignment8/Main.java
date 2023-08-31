

package com.coderscampus.assignment8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        Assignment8 assignment8 = new Assignment8();
        ExecutorService pool = Executors.newCachedThreadPool();
        Map<Integer, AtomicInteger> numberFreq = new ConcurrentHashMap<>();
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        
        
        for (int x = 0; x < 1000; x++) {
            CompletableFuture<Integer> future = 
            		CompletableFuture.supplyAsync(() -> {
            		List<Integer> numbersList = assignment8.getNumbers();
            		for (Integer number : numbersList) {
            			numberFreq.computeIfAbsent(number, k -> new AtomicInteger(0)).incrementAndGet();
            		}
            		return numbersList.size();
            }, pool);
            futures.add(future);
        }
      
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allTasks.join();

        System.out.println("Frequency of unique numbers:");
        synchronized (numberFreq) {
            numberFreq.forEach((number, frequency) -> {
                System.out.println(number + "=" + frequency);
            });
        }

      
        pool.shutdown();
    
    }
}