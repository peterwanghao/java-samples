package com.peterwanghao.samples.java.utils.java;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletableFutureDemo {
	public static void main(String args[]) throws InterruptedException, ExecutionException {
		// 使用 runAsync() 运行异步计算
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			// Simulate a long-running Job
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			log.info("I'll run in a separate thread than the main thread.");
		});
		// Block and wait for the future to complete
		future.get();
		
		// 使用 supplyAsync() 运行一个异步任务并且返回结果
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
	        try {
	            TimeUnit.SECONDS.sleep(1);
	        } catch (InterruptedException e) {
	            throw new IllegalStateException(e);
	        }
	        return "Result of the asynchronous computation";
		});
		// Block and get the result of the Future
		String result = future2.get();
		log.info(result);
		
		CountDownLatch countDownLatch = new CountDownLatch(2);

	    CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> {
	      System.out.println(Thread.currentThread().getName() + "进行一连串操作1....");
	      try {
	        TimeUnit.SECONDS.sleep(3);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	      return 1;
	    });

	    //whenComplete方法,返回的future的结果还是1
	    CompletableFuture<Integer> future4 = future3.whenComplete((x, y) -> {
	      try {
	        TimeUnit.SECONDS.sleep(3);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	      System.out.println(Thread.currentThread().getName() + "whenComplete,future返回:" + x);
	    });

	    //handler返回的future结果是字符串"2"
	    CompletableFuture<String> handle = future4.handle((x, y) -> {
	      System.out.println(Thread.currentThread().getName() + "handle接收的结果:" + x);
	      countDownLatch.countDown();
	      return "呵呵";
	    });
	    CompletableFuture<Integer> handle1 = handle.handle((x, y) -> {
	      System.out.println(Thread.currentThread().getName() + "handle返回的结果:" + x);
	      countDownLatch.countDown();
	      return 5;
	    });
	    countDownLatch.await();
	    System.out.println("end");
	    
	    // 可以使用 thenApply() 处理和改变CompletableFuture的结果。
	    CompletableFuture<String> welcomeText = CompletableFuture.supplyAsync(() -> {
	        try {
	            TimeUnit.SECONDS.sleep(1);
	        } catch (InterruptedException e) {
	           throw new IllegalStateException(e);
	        }
	        return "Rajeev";
	    }).thenApply(name -> {
	        return "Hello " + name;
	    }).thenApply(greeting -> {
	        return greeting + ", Welcome to the CalliCoder Blog";
	    });

	    log.info(welcomeText.get());
	    
	    // 使用thenCombine()组合两个独立的 future,当两个Future都完成的时候，传给``thenCombine()的回调函数将被调用。
	    System.out.println("Retrieving weight.");
	    CompletableFuture<Double> weightInKgFuture = CompletableFuture.supplyAsync(() -> {
	        try {
	            TimeUnit.SECONDS.sleep(1);
	        } catch (InterruptedException e) {
	           throw new IllegalStateException(e);
	        }
	        return 65.0;
	    });

	    System.out.println("Retrieving height.");
	    CompletableFuture<Double> heightInCmFuture = CompletableFuture.supplyAsync(() -> {
	        try {
	            TimeUnit.SECONDS.sleep(1);
	        } catch (InterruptedException e) {
	           throw new IllegalStateException(e);
	        }
	        return 177.8;
	    });

	    System.out.println("Calculating BMI.");
	    CompletableFuture<Double> combinedFuture = weightInKgFuture
	            .thenCombine(heightInCmFuture, (weightInKg, heightInCm) -> {
	        Double heightInMeter = heightInCm/100;
	        return weightInKg/(heightInMeter*heightInMeter);
	    });

	    System.out.println("Your BMI is - " + combinedFuture.get());
	    
	    // anyOf 组合多个future
	    CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
	        try {
	            TimeUnit.SECONDS.sleep(2);
	        } catch (InterruptedException e) {
	           throw new IllegalStateException(e);
	        }
	        return "Result of Future 1";
	    });

	    CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
	        try {
	            TimeUnit.SECONDS.sleep(1);
	        } catch (InterruptedException e) {
	           throw new IllegalStateException(e);
	        }
	        return "Result of Future 2";
	    });

	    CompletableFuture<String> futureC = CompletableFuture.supplyAsync(() -> {
	        try {
	            TimeUnit.SECONDS.sleep(3);
	        } catch (InterruptedException e) {
	           throw new IllegalStateException(e);
	        }
	        return "Result of Future 3";
	    });

	    CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(futureA, futureB, futureC);

	    System.out.println(anyOfFuture.get()); // Result of Future 2
	    
	    // 异常处理
	    Integer age = -1;

	    CompletableFuture<String> maturityFuture = CompletableFuture.supplyAsync(() -> {
	        if(age < 0) {
	            throw new IllegalArgumentException("Age can not be negative");
	        }
	        if(age > 18) {
	            return "Adult";
	        } else {
	            return "Child";
	        }
	    }).exceptionally(ex -> {
	        System.out.println("Oops! We have an exception - " + ex.getMessage());
	        return "Unknown!";
	    });

	    System.out.println("Maturity : " + maturityFuture.get()); 
	    
	    CompletableFuture<String> maturityFuture1 = CompletableFuture.supplyAsync(() -> {
	        if(age < 0) {
	            throw new IllegalArgumentException("Age can not be negative");
	        }
	        if(age > 18) {
	            return "Adult";
	        } else {
	            return "Child";
	        }
	    }).handle((res, ex) -> { // 如果异常发生，res参数将是 null，否则，ex将是 null。
	        if(ex != null) {
	            System.out.println("Oops! We have an exception - " + ex.getMessage());
	            return "Unknown!";
	        }
	        return res;
	    });
	    
	    System.out.println("Maturity : " + maturityFuture1.get());
	}
}
