package com.coderscampus.assignment8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		
		Assignment8 assignment8 = new Assignment8();
		 
	
		
		ExecutorService pool = Executors.newCachedThreadPool();
	
		List<CompletableFuture<Void>> numbers = new ArrayList<>();
		
		for(int i = 0; i < 1000; i++) {
		
	            CompletableFuture<Void> number = CompletableFuture.runAsync(() -> {
	                List<Integer> numbersList = Collections.synchronizedList(assignment8.getNumbers());}, pool);
		
	            numbers.add(number);
				}
		
		
	
		}
	
	}
			
		
		
