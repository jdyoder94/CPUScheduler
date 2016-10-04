public class Process {
	private int m_arrival_time, m_burst, m_wait_time, m_turnaround, m_remaining_burst, m_time_in_processor;
	private String m_name;
	private Boolean m_ready;
	
	public Process(String name, int arrival, int burst)
	{
		m_arrival_time = arrival;
		m_name = name;
		m_burst = burst;
		m_wait_time = 0;
		set_turnaround(0);
		m_remaining_burst = burst;
		m_time_in_processor = 0;
	}
	
	public int get_arrival_time() {
		return m_arrival_time;
	}
	
	public int get_burst() {
		return m_burst;
	}

	public String get_name() {
		return m_name;
	}

	public void set_ready() {
		m_ready = true;
	}
	
	public Boolean ready_status() {
		return m_ready;
	}
	
	public void decrement_burst() {
		m_remaining_burst--;
	}
	
	public int get_remaining_burst() {
		return m_remaining_burst;
	}

	public int get_wait_time() {
		return m_wait_time;
	}

	public void increment_wait_time() {
		m_wait_time++;
	}

	public int get_turnaround() {
		return m_turnaround;
	}

	public void set_turnaround(int m_turnaround) {
		this.m_turnaround = m_turnaround;
	}
	
	public int get_tip(){
		return m_time_in_processor;
	}
	public void increment_tip(){
		m_time_in_processor++;
	}
	
}
