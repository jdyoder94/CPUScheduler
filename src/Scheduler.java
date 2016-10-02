import java.util.ArrayList;

public abstract class Scheduler {
	private int m_runtime, m_current_time, m_processcount;
	
	public Scheduler(int processcount, int runtime) {
		m_runtime = runtime;
		m_processcount = processcount;
		m_current_time = 0;
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

}
