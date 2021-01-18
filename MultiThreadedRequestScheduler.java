package java_scheduler;
import java.util.*;

public class MultiThreadedRequestScheduler {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the buffer size: ");
		int bufSize = in.nextInt();
		System.out.print("Enter the number of slave threads: ");
		int numSlaves = in.nextInt();
		System.out.print("Enter the number of jobs to produce: ");
		int numJobs = in.nextInt();
		System.out.print("Enter the max request duration (in seconds): ");
		int duration = in.nextInt();
		System.out.print("Enter the wait time for master (in seconds): ");
		int sleep = in.nextInt();
		
		BoundedBuffer buffer = new BoundedBuffer(bufSize);
		Master m = new Master(buffer, numJobs, duration, sleep);
		System.out.println("Master thread has been created");
		Slave[] s = new Slave[numSlaves];
		for(int i = 0; i < s.length; i++) {
			s[i] = new Slave(buffer, i + 1);
			System.out.printf("Slave %d has been created\n", i + 1);
		}
		m.start();
		for(int i = 0; i < s.length; i++) {
			s[i].start();
		}
		try {
			m.join();
			for(int i = 0; i < s.length; i++) {
				s[i].interrupt();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		in.close();
		System.out.println("All jobs have been completed\nGoodbye!");
		System.exit(0);
	}
}
