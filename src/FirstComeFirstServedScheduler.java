import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class FirstComeFirstServedScheduler extends Scheduler {
	private LinkedList<Process> ready_queue;
	private String m_mode;

	public FirstComeFirstServedScheduler(int processcount, int runtime) {
		super(processcount, runtime);
		ready_queue = new LinkedList<Process>();
		m_mode = "First Come First Served";
	}

	@Override
	public void run(ArrayList<Process> process_list) {
		print_processor_info();
		
		while(this.get_current_time() < this.get_runtime()){
			advance_timer();
			check_arrivals(process_list);
			
			if(!this.processing()) {
				if(ready_queue.isEmpty()){
					append_to_file("Time " + this.get_current_time() + ": Idle");
					continue;
				}
				else{
					this.set_current_process(ready_queue.remove());
					append_to_file("Time " + this.get_current_time() + ": " + this.get_current_process().get_name() + " selected (burst " + this.get_current_process().get_remaining_burst() + ")" + "\n");
					this.set_processing(true);
				}
			}
			else {
				this.get_current_process().decrement_burst();
				if( this.get_current_process().get_remaining_burst() == 0) {
					end_processing();
					if(!ready_queue.isEmpty()){
						this.set_current_process(ready_queue.remove());
						append_to_file("Time " + this.get_current_time() + ": " + this.get_current_process().get_name() + " selected (burst " + this.get_current_process().get_remaining_burst() + ")" + "\n");
						this.set_processing(true);
					}
				}
			}

			increment_wait_times();
		}
		
		append_to_file("Finished at time " + this.get_runtime() + "\n");
		write_process_info(process_list);
		
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
	
	public void end_processing(){
		append_to_file("Time " + this.get_current_time() + ": " + this.get_current_process().get_name() + " finished\n");
		this.get_current_process().set_turnaround(this.get_current_time() - this.get_current_process().get_arrival_time());
		this.set_processing(false);
	}
	
	public void check_arrivals(ArrayList<Process> process_list) {
		for(Process i_process : process_list) {
			if (i_process.get_arrival_time() == this.get_current_time()){
				append_to_file("Time " + this.get_current_time() + ": " + i_process.get_name() + " arrived\n");
				ready_queue.addLast(i_process);
			}
		}
	}

}
