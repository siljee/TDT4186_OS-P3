/**
 * This class contains a lot of public variables that can be updated
 * by other classes during a simulation, to collect information about
 * the run.
 */
public class Statistics
{
	/** The number of processes that have exited the system */
	public long nofCompletedProcesses = 0;
	/** The number of processes that have entered the system */
	public long nofCreatedProcesses = 0;
	/** The number of times processes were switched from CPU to the queue because RR time had passed. */
	public long nofSwitchedProcesses = 0;
	/** The number of processed I/O operations. */
	public long nofIoOperations = 0;
	
	/** The amount of time the CPU were processing. (not Idle)  cpuTimeSpentProcessing + cpuTimeSpentIdle = simulationLength*/
	public long cpuTimeSpentProcessing = 0;
	
	
	/** The largest memory queue length that has occurred */
	public long memoryQueueLargestLength = 0;
	/** The time-weighted length of the memory queue, divide this number by the total time to get average queue length */
	public long memoryQueueLengthTime = 0; 
	/** The total time that all completed processes have spent waiting for memory */
	public long totalTimeSpentWaitingForMemory = 0;
	/** The total number of times there have been placed processes in memory queue. */
	public long totalNofTimesInMemoryQueue = 0;
	
	/** The largest CPU queue length that has occurred */
	public long cpuQueueLargestLength = 0;
	/** The time-weighted length of the CPU queue, divide this number by the total time to get average queue length */ 
	public long cpuQueueLengthTime = 0;
	/** The total time that all completed processes have spent waiting for CPU */
	public long totalTimeSpentWaitingForCpu = 0;
	/** The total number of times there have been placed processes in CPU queue. */
	public long totalNofTimesInReadyQueue = 0;
	
	/** The largest I/O queue length that has occurred */
	public long ioQueueLargestLength = 0;
	/** The time-weighted length of the I/O queue, divide this number by the total time to get average queue length */
	public long ioQueueLengthTime = 0; 
	/** The total time that all completed processes have spent waiting for I/O */
	public long totalTimeSpentWaitingForIo = 0;
	/** The total number of times there have been placed processes in I/O queue. */
	public long totalNofTimesInIoQueue = 0;
	
	public long totalTimeSpentInSystem = 0;
	
	public long totalTimeSpentInCpu = 0;
	
	public long totalTimeSpentInIo = 0;
	
	
    
	/**
	 * Prints out a report summarizing all collected data about the simulation.
	 * @param simulationLength	The number of milliseconds that the simulation covered.
	 */
	public void printReport(long simulationLength) {
		System.out.println();
		System.out.println("Simulation statistics:");
		System.out.println(nofCompletedProcesses + " " + simulationLength + " " + nofCompletedProcesses/simulationLength);
		System.out.println("Number of completed processes:                                "+nofCompletedProcesses);
		System.out.println("Number of created processes:                                  "+nofCreatedProcesses);
		System.out.println("Number of (forced) process switches:                          "+nofSwitchedProcesses);
		System.out.println("Number of processed I/O operations:                           "+nofIoOperations);
		System.out.println("Average throughput (processes per second):                    "+(float)nofCompletedProcesses*1000/simulationLength);
		
		System.out.println();
		System.out.println("Total CPU time spent processing:                              "+cpuTimeSpentProcessing+" ms");
		System.out.println("Fraction of CPU time spent processing:                        "+((float)cpuTimeSpentProcessing*100/simulationLength)+"%");
		System.out.println("Total CPU time spent waiting                                  "+(simulationLength-cpuTimeSpentProcessing)+" ms");
		System.out.println("Fraction of CPU time spent waiting:                           "+((float)(simulationLength-cpuTimeSpentProcessing)*100/simulationLength)+"%");
		
		System.out.println();
		System.out.println("Largest occuring memory queue length:                         "+memoryQueueLargestLength);
		System.out.println("Average memory queue length:                                  "+(float)memoryQueueLengthTime/simulationLength);
		System.out.println("Largest occuring cpu queue length:                         "+cpuQueueLargestLength);
		System.out.println("Average memory cpu length:                                  "+(float)cpuQueueLengthTime/simulationLength);
		System.out.println("Largest occuring I/O queue length:                         "+ioQueueLargestLength);
		System.out.println("Average I/O queue length:                                  "+(float)ioQueueLengthTime/simulationLength);
		
		if(nofCompletedProcesses > 0) {
			System.out.println("Average # of times a process has been placed in memory queue: "+(float)totalNofTimesInMemoryQueue/nofCompletedProcesses);
			System.out.println("Average # of times a process has been placed in cpu queue: "+(float)totalNofTimesInReadyQueue/nofCompletedProcesses);
			System.out.println("Average # of times a process has been placed in I/O queue: "+(float)totalNofTimesInIoQueue/nofCompletedProcesses);
			
			System.out.println();
			System.out.println("Average time spent in system per process:            "+(float)totalTimeSpentInSystem/nofCompletedProcesses+" ms");
			System.out.println("Average time spent waiting for memory per process:            "+(float)totalTimeSpentWaitingForMemory/nofCompletedProcesses+" ms");
			System.out.println("Average time spent waiting for cpu per process:            "+(float)totalTimeSpentWaitingForCpu/nofCompletedProcesses+" ms");
			System.out.println("Average time spent processing per process:            "+(float)totalTimeSpentInCpu/nofCompletedProcesses+" ms");
			System.out.println("Average time spent waiting for I/O per process:            "+(float)totalTimeSpentWaitingForIo/nofCompletedProcesses+" ms");
			System.out.println("Average time spent in I/O per process:            "+(float)totalTimeSpentInIo/nofCompletedProcesses+" ms");
		}
	}
}
