package test;

public class DaemonTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new WorkerThread().start();
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
		}
		System.out.println("Main Thread ending");
		cmp();
	}

	static void cmp() {
		String[] ss = {"a","b","c","d","e"};
		int oo = 1;
		for (int i=0; i<ss.length; i++) {
			for (int j=i+1; j<ss.length; j++) {
				System.out.println(oo++ + ":   "+ ss[i] + ", " + ss[j]);
			}
		}
	}
}

class WorkerThread extends Thread {

	public WorkerThread() {
		setDaemon(true); // When false, (i.e. when it's a user thread),
		// the Worker thread continues to run.
		// When true, (i.e. when it's a daemon thread),
		// the Worker thread terminates when the main
		// thread terminates.
	}

	public void run() {
		int count = 0;
		while (true) {
			System.out.println("Hello from Worker " + count++);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}
