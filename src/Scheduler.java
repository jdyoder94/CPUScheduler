import java.util.ArrayList;

public abstract class Scheduler {
	private int m_runtime, m_current_time, m_processcount;
	private boolean m_processing;
	private Process current_process;
	
	public Scheduler(int processcount, int runtime) {
		m_runtime = runtime;
		m_processcount = processcount;
		m_current_time = -1;
		m_processing = false;
	}
	
	public abstract void run(ArrayList<Process> process_list);
	
	public void advance_timer() {
		m_current_time++;
	}

	public int get_runtime() {
		return m_runtime;
	}

	public int get_current_time() {
		return m_current_time;
	}

	public int get_processcount() {
		return m_processcount;
	}

	public boolean processing() {
		return m_processing;
	}

	public void set_processing(boolean m_processing) {
		this.m_processing = m_processing;
	}

	public Process get_current_process() {
		return current_process;
	}

	public void set_current_process(Process current_process) {
		this.current_process = current_process;
	}

}
