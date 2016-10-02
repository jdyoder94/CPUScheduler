import java.util.ArrayList;

public class RoundRobinScheduler extends Scheduler {
	private int m_quantum;
	
	public RoundRobinScheduler(int processcount, int runtime, int quantum) {
		super(processcount, runtime);
		m_quantum = quantum;
	}

	//@Override
	public void run(ArrayList<Process> process_list) {
		/*
		System.out.println("Number of Processes: " + this.get_processcount());
		System.out.println("Runtime: " + this.get_runtime());
		System.out.println("Quantum: " + this.get_quantum());
		
		for(Process currProcess : process_list) {
			System.out.println("Name: " + currProcess.get_name() + " Arrival: " + currProcess.get_arrival_time() + " Burst: " + currProcess.get_burst());
		}
		*/
	}

	public int get_quantum() {
		return m_quantum;
	}

}
