package slides;

public class Deadlock {
	Object lock1 = new Object();
	Object lock2 = new Object();
	
	public Deadlock() {
		// TODO Auto-generated constructor stub
	}

	public void startThread() throws InterruptedException {
		Runnable runner1 = new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (lock1) {
						synchronized (lock2) {
							while (true) {
								System.out.println("runner 1 running!");
								Thread.sleep(1000);
							}
						}
					}
				} catch (InterruptedException e) {
					System.out.println("Interrupted!");
				}
			}
		};
		Runnable runner2 = new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (lock2) {
						Thread.sleep(1000);
						synchronized (lock1) {
							while (true) {
								System.out.println("runner 2 running!");
								Thread.sleep(1000);
							}
						}
					}
				} catch (InterruptedException e) {
					System.out.println("Interrupted!");
				}
			}
		};
		Thread runnerThread1 = new Thread(runner1);
		Thread runnerThread2 = new Thread(runner2);
		runnerThread1.start();
		runnerThread2.start();
		Thread.yield();
		for (int i = 0; i < 10; i++) {
			System.out.println(runnerThread1.getState());
			System.out.println(runnerThread2.getState());
			Thread.sleep(1000);			
		}
		runnerThread1.interrupt();
		runnerThread2.interrupt();
		System.out.println("Done!");
	}

	public static void main(String[] args) throws InterruptedException {
		(new Deadlock()).startThread();
	}
}