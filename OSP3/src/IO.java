
public class IO {
	
	private Queue ioQueue;
	private Statistics statistics;
	private Process activeProcess;
	private Gui gui;
	
	public IO(Queue ioQueue, Statistics statistics, Gui gui) {
		this.ioQueue = ioQueue;
		this.statistics = statistics;
		this.activeProcess = null;
		this.gui = gui;
	}
	
	public void insertProcessToQueue(Process p) {
		ioQueue.insert(p);
	}
	
	public Process removeActiveProcess(long clock) {
		if (!isIdle()) {
			Process p = getActiveProcess();
			p.leftIo(clock);
			activeProcess = null;
			updateGui();
			statistics.nofIoOperations++;
			return p;
		}
		return null;
	}

	
	public Event setNextActiveProcess(long clock) {
		if (!ioQueue.isEmpty() && activeProcess == null) {
			this.activeProcess = (Process) ioQueue.removeNext();
			updateGui();
			activeProcess.leftIoQueue(clock);
			
			return new Event(Constants.END_IO, clock + activeProcess.timeLeftInIo());
		}
		return null;
	}
	
	
	public boolean isIdle() {
		return activeProcess == null;
	}
	
	public Process getActiveProcess() {
		return activeProcess;
	}

	private void updateGui() {
		gui.setIoActive(activeProcess);
	}
	
	// Update statistics
	public void timePassed(long timePassed) {
		statistics.ioQueueLengthTime += ioQueue.getQueueLength()*timePassed;
		if (ioQueue.getQueueLength() > statistics.ioQueueLargestLength) {
			statistics.ioQueueLargestLength = ioQueue.getQueueLength(); 
		}
	}

	
}
