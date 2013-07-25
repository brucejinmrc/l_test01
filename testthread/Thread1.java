package testthread;

import java.util.Date;

//This class extends Thread
class Thread1 extends Thread {
	 
	public void run() {
		for (int i = 0; i < 10; i++) {
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
 				e.printStackTrace();
			}
			
			RunThread.count = i+100;
			System.out.println(RunThread.count + ",  time=" + new Date());
		}
	}
	
	void runcode() {
		
	}
}
