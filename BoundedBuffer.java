package java_scheduler;
import java.util.*;

public class BoundedBuffer {
	private int[][] buffer;
	private int filled = 0, front = 0, end = 0;
	
	public BoundedBuffer(int size) {
		buffer = new int[size][2];
	}
	
	public synchronized void add(int id, int dur) {
		while(filled == buffer.length) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		buffer[end][0] = id;
		buffer[end][1] = dur;
		Date time = java.util.Calendar.getInstance().getTime();
		end = ++end % buffer.length;
		filled++;
		System.out.printf("Master: created request ID %d, length %d seconds at time %tc\n", id, dur, time);
		notifyAll();
	}
	
	public synchronized int[] remove(int slaveNum) {
		while(filled == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		int job[] = {buffer[front][0], buffer[front][1]};
		Date time = java.util.Calendar.getInstance().getTime();
		front = ++front % buffer.length;
		filled--;
		System.out.printf("Slave %d: assigned request ID %d, processing request for the next %d seconds, current time is %tc\n", slaveNum, job[0], job[1], time);
		notifyAll();
		return job;
	}
}
