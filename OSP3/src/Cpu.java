
public class Cpu {
	
	private Queue cpuQueue;
	
	private Process activeProcess;
	
	private Statistics statistics;
	
	private long maxCpuTime;
	
	public Cpu(Queue cpuQueue, Statistics statistics, long maxCpuTime) {
		this.cpuQueue = cpuQueue;
		this.statistics = statistics;
		this.maxCpuTime = maxCpuTime;
		activeProcess = null;
	}
	
	public void setActiveProcess() {
		if (!cpuQueue.isEmpty()) {
			this.activeProcess = (Process) cpuQueue.removeNext();
		}
	}

	public void switchProcess() {
		if (activeProcess != null) {
			insertProcess(activeProcess);	
		}
		activeProcess = (Process) cpuQueue.removeNext();
	}
	
	public Process ioRequest() {
		Process p = getActiveProcess();
		endProcess();
		return p;
	}
	
	public void endProcess() {
		activeProcess = null;
	}
	
	public Process getActiveProcess() {
		return activeProcess;
	}
	
	public boolean isIdle() {
		return activeProcess == null;
	}
	
	public void insertProcess(Process p) {
		cpuQueue.insert(p);
	}

	public void timePassed(long timePassed) {
		statistics.cpuQueueLengthTime += cpuQueue.getQueueLength()*timePassed;
		if (cpuQueue.getQueueLength() > statistics.cpuQueueLargestLength) {
			statistics.cpuQueueLargestLength = cpuQueue.getQueueLength(); 
		}
		// Update timepassed i activeProcess 
	}
	
	public Event nextEventCpu(long clock) {
		int type;
		long timePassed;
		
		long cpuTime = activeProcess.timeLeftInCpu();
		long ioTime = activeProcess.timeBeforeIo();
		
		if (maxCpuTime < cpuTime ) {
			type = Constants.SWITCH_PROCESS;
			timePassed = maxCpuTime;
		} else if (cpuTime < ioTime) {
			type = Constants.END_PROCESS;
			timePassed = ioTime;
		} else {
			type = Constants.IO_REQUEST;
			timePassed = ioTime;
		}
		return new Event(type, clock + timePassed);
	}
}
