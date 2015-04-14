
public class IO {
	
	private Queue ioQueue;
	private Statistics statistics;
	
	public IO(Queue ioQueue, Statistics statistics) {
		this.ioQueue = ioQueue;
		this.statistics = statistics;
	}
	
	public void insertProcess(Process p) {
		ioQueue.insert(p);
	}
	
	public void timePassed(long timePassed) {
		statistics.ioQueueLengthTime += ioQueue.getQueueLength()*timePassed;
		if (ioQueue.getQueueLength() > statistics.ioQueueLargestLength) {
			statistics.ioQueueLargestLength = ioQueue.getQueueLength(); 
		}
	}

	
}
