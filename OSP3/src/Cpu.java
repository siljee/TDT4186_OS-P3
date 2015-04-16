
public class Cpu {
	
	private Queue cpuQueue;
	
	private Process activeProcess;
	
	private Statistics statistics;
	
	private long maxCpuTime;
	
	private Gui gui;
	
	private long timeSpentProcessing = 0;
	
	/** Keeps track of the clock when the cpu started it's last process. This is a help variable for processingTime. */
	private long timeOfLastProcess;
	
	public Cpu(Queue cpuQueue, Statistics statistics, long maxCpuTime, Gui gui) {
		this.cpuQueue = cpuQueue;
		this.statistics = statistics;
		this.maxCpuTime = maxCpuTime;
		this.gui = gui;
		activeProcess = null;
	}
	
	/** 
	 * Take the first process in cpuQueue and makes it active, if the queue is not empty. Also updates statistics for the process since it leaves cpu-Queue. 
	 * */
	public Event setNextActiveProcess(long clock) {
		if (!cpuQueue.isEmpty() && activeProcess == null) {
			this.activeProcess = (Process) cpuQueue.removeNext();
			timeOfLastProcess = clock;
			activeProcess.leftCpuQueue(clock);
			updateGui();
			return nextEventCpu(clock);
		}
		updateGui();
		return null;
		
	}

	public void insertProcessToQueue(Process p) {
		cpuQueue.insert(p);
	}
	
	public void removeActiveProcess(long clock) {
		activeProcess = null;
		timeSpentProcessing += clock - timeOfLastProcess;
		timeOfLastProcess = -1;
	}

	public Event switchProcess(long clock) {
		if (activeProcess != null) {
			// A forced switch is happening because of round robin. 
			insertProcessToQueue(activeProcess);
			// Update statistics of active process.
			activeProcess.leftCpu(clock, Constants.SWITCH_PROCESS);
			statistics.nofSwitchedProcesses++;
			removeActiveProcess(clock);
		}
		return setNextActiveProcess(clock);
	}
	
	public Event nextEventCpu(long clock) {
		int type;
		long timePassed;
		long cpuTime = activeProcess.timeLeftInCpu();
		long ioTime = activeProcess.timeBeforeIo();
		
		if (maxCpuTime < cpuTime && maxCpuTime < ioTime) {
			// Round robin will happen first
			type = Constants.SWITCH_PROCESS;
			timePassed = maxCpuTime;
		} else if (ioTime < cpuTime) {
			// I/O Request will happen first
			type = Constants.IO_REQUEST;
			timePassed = ioTime;
		} else {
			// The process will end first.
			type = Constants.END_PROCESS;
			timePassed = cpuTime;
		}
		return new Event(type, clock + timePassed);
	}

	public Process ioRequest(long clock) {
		Process p = getActiveProcess();
		p.leftCpu(clock, Constants.IO_REQUEST);
		removeActiveProcess(clock);
		updateGui();
		return p;
	}
	
	public void endProcess(long clock) {
		activeProcess.leftCpu(clock, Constants.END_PROCESS);
		activeProcess.updateStatistics(statistics);
		removeActiveProcess(clock);
		updateGui();
		
	}
	
	
	// Help functions 
	
	public boolean isIdle() {
		return activeProcess == null;
	}
	
	public Process getActiveProcess() {
		return activeProcess;
	}
	
	private void updateGui() {
		gui.setCpuActive(activeProcess);
	}
	
	// Statistics
	public void timePassed(long timePassed) {
		statistics.cpuTimeSpentProcessing = timeSpentProcessing;
		statistics.cpuQueueLengthTime += cpuQueue.getQueueLength()*timePassed;
		if (cpuQueue.getQueueLength() > statistics.cpuQueueLargestLength) {
			statistics.cpuQueueLargestLength = cpuQueue.getQueueLength(); 
		}
		// Update timepassed i activeProcess 
	}
	
	
	
}
