import java.io.*;
import java.util.ArrayList;

public class CPUScheduler {
	public static void main(String[] args) {
		ArrayList<Process> process_list = new ArrayList<Process>();
		Scheduler main_scheduler = readFile(process_list);
		main_scheduler.run(process_list);
	}

	public static Scheduler readFile(ArrayList<Process> process_list) {
		//String variables to parse input
		String fileName = "processes.in";
		String line = null;
		
		//Scheduler object to store the specified scheduler
		Scheduler returnScheduler = null;
		
		//Scheduler parameters
		int processcount = 0;
		int runtime = 0;
		String mode = "";
		int quantum = 0;
		
		try {
			//File IO objects
			FileReader inReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(inReader);
			
			//Loop continues to loop as long as there's still a new line
			while((line = bufferedReader.readLine()) != null) {
				//Finds the index of a pound symbol
				int poundIndex = line.indexOf("#");
				//Checks to see if a pound was found
				if(poundIndex != -1){
					//if a pound was found, disregard it and everything following it
					line = line.substring(0,  poundIndex - 1);
				}
				
				//Splits the rest of the line, delimited by spaces
				String[] parameters = line.split(" ");
				
				//Set the parameters of the scheduler or a process based on what the current line specifies
				switch (parameters[0]) {
				case "processcount":
					processcount = Integer.parseInt(parameters[1]);
					break;
				case "runfor":
					runtime = Integer.parseInt(parameters[1]);
					break;
				case "use":
					mode = parameters[1];
					break;
				case "quantum":
					quantum = Integer.parseInt(parameters[1]);
					break;
				case "process":
					process_list.add(new Process(parameters[2], Integer.parseInt(parameters[4]), Integer.parseInt(parameters[6])));
					break;
				case "end":
					break;
				default:
					System.out.println("Invalid input entered");
					break;
				}
			}
			
			//close the reader
			bufferedReader.close();
			
			//Set up the scheduler based on the mode specified in the file
			switch (mode) {
			case "fcfs":
				returnScheduler = new FirstComeFirstServedScheduler(processcount, runtime);
				break;
			case "sjf":
				returnScheduler = new ShortestJobFirstScheduler(processcount, runtime);
				break;
			case "rr":
				returnScheduler = new RoundRobinScheduler(processcount, runtime, quantum);
				break;
			default:
				System.out.println("Error - Not a valid scheduler mode");
				break;
			}
			
		}
		catch(FileNotFoundException ex) {
			System.out.println("Error opening file...");
		}
		catch(IOException ex) {
			System.out.println("Error reading file...");
		}
		
		return returnScheduler;
	}
}
