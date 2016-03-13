public class Process {
		int id, arrival, burst, prior;
		int remainingTime, index;

		public Process (int id, int arrival, int burst, int prior) {
			this.id = id;
			this.arrival = arrival;
			this.burst = burst;
			this.prior = prior;
			this.remainingTime = burst;
		}

		int getArrivalTime() {
	    return arrival;
	  }

	  int getPriority() {
	    return prior;
	  }

		int getIndex() {
			return index;
		}

	  void print() {
	    System.out.println(arrival + " " + burst + " " + prior);
	  }

	  void run() {
	    remainingTime--;
	  }
	}
