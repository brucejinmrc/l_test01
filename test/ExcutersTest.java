package test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
public class ExcutersTest {
 
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(20);
 
        for (int i = 0; i<100; i++){
            executor.execute(new DataProcessor(i));
        }
 
        System.out.println("Starting shutdown...");
        executor.shutdown();
 
        try {
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted...");
        }
 
        System.out.println("All done!");
 
    }
}

class DataProcessor implements Runnable {
    public DataProcessor(int data){
        this.data = data;
    }
 
    @Override
    public void run() {
        System.out.println("Processing data: " + data);
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Data processing goes here
    }
 
    private int data;
}