package java_scheduler;
import java.util.Date;

public class Slave extends Thread {
	BoundedBuffer buffer;
	int num;

	public Slave(BoundedBuffer buffer, int num) {
		this.buffer = buffer;
		this.num = num;
	}
	
	public void run() {
		try {
			while(true) {
				int[] job = buffer.remove(num);
				sleep(job[1] * 1000);
				Date time = java.util.Calendar.getInstance().getTime();
				System.out.printf("Slave %d: completed request ID %d at time %tc\n", num, job[0], time);
			}
		} catch (InterruptedException e) {
		}
	}
}
