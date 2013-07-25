package testthread;

public class RunThread {

	/**
	 * @param args
	 */
	public static int count;
	
	public static void main(String[] args) {
		System.out.println("start...");
		
		Thread thread = new Thread1();
		thread.start();
		

		Thread thread2 = new Thread1();
		thread.start();
		
		System.out.println("END...");
	}

}
