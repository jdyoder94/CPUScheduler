import java.util.ArrayList;

public class ShortestJobFirstScheduler extends Scheduler {

	public ShortestJobFirstScheduler(int processcount, int runtime) {
		super(processcount, runtime);
	}

	//@Override
	public void run(ArrayList<Process> process_list) {
		/*
		System.out.println("Number of Processes: " + this.get_processcount());
		System.out.println("Runtime: " + this.get_runtime());
		
		for(Process currProcess : process_list) {
			System.out.println("Name: " + currProcess.get_name() + " Arrival: " + currProcess.get_arrival_time() + " Burst: " + currProcess.get_burst());
		}
		*/
	}

}
