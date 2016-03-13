public class Process {
		int id, arrival, burst, prior;
		
		public Process (int id, int arrival, int burst, int prior) {
			this.id = id;
			this.arrival = arrival;
			this.burst = burst;
			this.prior = prior;
		}
	}