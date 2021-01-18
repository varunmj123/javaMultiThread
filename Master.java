package java_scheduler;

public class Master extends Thread {
	BoundedBuffer buffer;
	int jobs, maxDur, sleep;
	
	public Master(BoundedBuffer buffer, int jobs, int maxDur, int sleep) {
		this.buffer = buffer;
		this.jobs = jobs;
		this.maxDur = maxDur;
		this.sleep = sleep;
	}
	
	public void run() {
		try {
			for(int i = 1; i <= jobs; i++) {
				int dur = (int)(Math.random() * maxDur + 1);
				buffer.add(i, dur);
				System.out.printf("Master: sleeping for %d seconds\n", sleep);
				sleep(sleep * 1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
