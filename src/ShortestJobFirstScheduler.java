import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ShortestJobFirstScheduler extends Scheduler {
	private LinkedList<Process> ready_queue;
	private String m_mode;
	private boolean m_preempted;

	public ShortestJobFirstScheduler(int processcount, int runtime) {
		super(processcount, runtime);
		ready_queue = new LinkedList<Process>();
		m_mode = "Preemptive Shortest Job First";
		m_preempted = false;
	}

	@Override
	public void run(ArrayList<Process> process_list) {
		print_processor_info();
		
		while(this.get_current_time() < this.get_runtime()){
			
			check_arrivals(process_list);
			
			
			
			if(!this.processing()) {
				if(ready_queue.isEmpty()){
					append_to_file("Time " + this.get_current_time() + ": Idle");
					advance_timer();
					continue;
				}
				else
					select_process();
			}
			else {
				if( this.get_current_process().get_remaining_burst() == 0) {
					end_processing();
					if(!ready_queue.isEmpty())
						select_process();
				}
			}
			/*else if(m_preempted){
				m_preempted = false;
				advance_timer();
				increment_wait_times();
				continue;
			}*/
			
			
			this.get_current_process().decrement_burst();
			increment_wait_times();
			/*if( this.get_current_process().get_remaining_burst() == 0) {
				end_processing();
				if(!ready_queue.isEmpty())
					select_process();
			}*/
			
			
			advance_timer();
		}
		
		append_to_file("Finished at time " + this.get_runtime() + "\n");
		write_process_info(process_list);
	}
	
	public void select_process(){
		if(ready_queue.size() == 1){
			this.set_current_process(ready_queue.remove());
		}
		else{
			Process first_process = ready_queue.element();
			for(Process i_process : ready_queue){
				if(i_process.get_remaining_burst() < first_process.get_remaining_burst()){
					this.set_current_process(i_process);
					ready_queue.remove(i_process);
				}
			}
		}
		append_to_file("Time " + this.get_current_time() + ": " + this.get_current_process().get_name() + " selected (burst " + this.get_current_process().get_remaining_burst() + ")" + "\n");
		this.set_processing(true);
	}
	
	public void increment_wait_times() {
		for(Process i_process : ready_queue) {
			i_process.increment_wait_time();
		}
	}
	
	public void print_processor_info() {
		String file_name = "processes.out";
		try {
			FileWriter filewriter = new FileWriter(file_name);
			BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
			
			bufferedwriter.write(this.get_processcount() + " processes");
			bufferedwriter.newLine();
			bufferedwriter.write("Using " + m_mode);
			bufferedwriter.newLine();
			bufferedwriter.newLine();
			bufferedwriter.newLine();
			bufferedwriter.close();
		}
		catch (IOException e) {
			System.out.println("Error");
		}
	}
	
	public void append_to_file(String line) {
		String file_name = "processes.out";
		
		try {
			FileWriter filewriter = new FileWriter(file_name, true);
			BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
			
			bufferedwriter.write(line);
			bufferedwriter.newLine();
			bufferedwriter.close();
		}
		catch(IOException e) {
			System.out.println("Error");
		}
	}
	
	public void write_process_info(ArrayList<Process> process_list) {
		String file_name = "processes.out";
		
		try {
			FileWriter filewriter = new FileWriter(file_name, true);
			BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
			bufferedwriter.newLine();
			
			for(Process i_process : process_list) {			
				bufferedwriter.write(i_process.get_name() + " wait " + i_process.get_wait_time() + " turnaround " + i_process.get_turnaround());
				bufferedwriter.newLine();
			}
			
			bufferedwriter.close();
		}
		catch (IOException ex) {
			System.out.println("Error");
		}
	}
	
	public void end_processing(){
		append_to_file("Time " + this.get_current_time() + ": " + this.get_current_process().get_name() + " finished\n");
		this.get_current_process().set_turnaround(this.get_current_time() - this.get_current_process().get_arrival_time());
		this.set_processing(false);
	}
	
	public void check_arrivals(ArrayList<Process> process_list) {
		for(Process i_process : process_list) {
			if (i_process.get_arrival_time() == this.get_current_time()){
				append_to_file("Time " + this.get_current_time() + ": " + i_process.get_name() + " arrived\n");
				if(this.processing() && i_process.get_remaining_burst() < this.get_current_process().get_remaining_burst())
				{
					ready_queue.addLast(this.get_current_process());
					this.set_current_process(i_process);
					m_preempted = true;
					append_to_file("Time " + this.get_current_time() + ": " + this.get_current_process().get_name() + " selected (burst " + this.get_current_process().get_remaining_burst() + ")" + "\n");
				}
				else
					ready_queue.addLast(i_process);
			}
		}
	}

}
