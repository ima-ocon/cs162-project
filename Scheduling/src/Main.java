import java.util.*;

public class Main {
	public static void main (String args[]) {
		Scanner in = new Scanner (System.in);
		int cases = in.nextInt();
		for (int i = 1; i <= cases; i++) {
			int proc = in.nextInt();
			String sched = in.next();
			int rr = 0;
			if (sched.equals ("RR"))
				rr = in.nextInt();
			ArrayList<Process> list = new ArrayList<Process>();
			for (int j = 1; j <= proc; j++) {
				list.add (new Process (j, in.nextInt(), in.nextInt(), in.nextInt()));
			}
			
			System.out.println (i + "");
			switch (sched) {
				case "FCFS" : fcfs(list);
				break;
				case "SJF" : sjf(list);
				break;
				case "SRTF" : srtf(list);
				break;
				case "P" : priority(list);
				break;
				case "RR" : robin(list, rr);
				break;
			}
		}
	}
	
	// First Come, First Served
	public static void fcfs (ArrayList<Process> list) {
		// list of processes sorted according to arrival time
		PriorityQueue<Process> pq = new PriorityQueue<Process>  (list.size(), new ArrivalTimeComparator());
		for (Process o : list) {
			pq.offer (o);
		}
		int block = pq.peek().arrival; // current time
		while (!pq.isEmpty()) {
			Process p = pq.poll(); // current process
			Process q = pq.peek(); // next process
			System.out.println (block + " " + p.id + " " + p.burst + "X");
			block += p.burst;
			if (q != null) {
				if (q.arrival > block)
					block = q.arrival;
			}
		}
	}
	
	// Shortest Job First
	public static void sjf (ArrayList<Process> list) {
		// list of processes sorted according to arrival time
		PriorityQueue<Process> pq = new PriorityQueue<Process> (list.size(), new ArrivalTimeComparator());
		for (Process o : list) {
			pq.offer (o);
		}
		int block = pq.peek().arrival; // current time
		// list of processes to be sorted according to burst time
		PriorityQueue<Process> ongoing = new PriorityQueue<Process> (1, new BurstTimeComparator());
		while (!pq.isEmpty()) {
			Process p = pq.poll(); // current process
			Process q = pq.peek(); // next process
			// if p is the last process or the next process will arrive when or after the current process
			if (q == null || q.arrival >= p.burst + block) {
				System.out.println (block + " " + p.id + " " + p.burst + "X");
				block += p.burst;
				// if there are no pending processes
				if (ongoing.isEmpty()) {
					try {
						block = q.arrival;
					} catch (Exception e) {}
				} else {
					// label shortest pending process as "unused"
					pq.offer (ongoing.poll());
				}
			} else {
				// label next process as pending
				ongoing.offer (pq.poll());
				// label current process as "unused"
				pq.offer (p);
			}
		}
	}
	
	// Shortest Remaining Time First
	public static void srtf (ArrayList<Process> list) {
		// list of processes sorted according to arrival time
		PriorityQueue<Process> pq = new PriorityQueue<Process> (list.size(), new ArrivalTimeComparator());
		for (Process o : list) {
			pq.offer (o);
		}
		int block = pq.peek().arrival; // current time
		// list of processes to be sorted according to burst/remaining time
		PriorityQueue<Process> ongoing = new PriorityQueue<Process> (1, new BurstTimeComparator());
		while (!pq.isEmpty()) {
			Process p = pq.poll(); // current process
			Process q = pq.peek(); // next process
			// if p is the last process or the next process will arrive after the current process is finished
			if (q == null || q.arrival > p.burst + block) {
				System.out.println (block + " " + p.id + " " + p.burst + "X");
				block += p.burst;
				// while p is not the last process, the next process hasn't arrived yet, and there are pending processes
				while (q != null && q.arrival > block && !ongoing.isEmpty()) {
					Process o = ongoing.poll(); // pending process with the shortest remaining time
					// if a new process will arrive while o is running
					if (q.arrival < o.burst + block) {
						if (q.burst <= o.burst) {
							int temp = q.arrival - block; // time elapsed before process was interrupted
							System.out.println (block + " " + o.id + " " + temp);
							o.burst -= temp; // update remaining time
							block += temp;
							// label o as pending
							ongoing.offer (o);
						} else {
							// label next process as pending
							ongoing.offer (pq.poll());
							// label o as "unused"
							pq.offer (o);
							q = pq.peek();
						}
					} else {
						System.out.println (block + " " + o.id + " " + o.burst + "X");
						block += o.burst;
					}
				}
			} else {
				if (p.burst < q.burst) {
					// label next process as pending
					ongoing.offer (pq.poll());
					// label current process as "unused"
					pq.offer (p);
				} else {
					int temp = q.arrival - block; // time elapsed before process was interrupted
					System.out.println (block + " " + p.id + " " + temp);
					block += temp;
					p.burst -= temp; // update remaining time
					// label current process as pending
					ongoing.offer (p);
				}
			}
		}
		// while there are still unfinished processes
		while (!ongoing.isEmpty()) {
			Process p = ongoing.poll();
			System.out.println (block + " " + p.id + " " + p.burst + "X");
			block += p.burst;
		}
	}
	
	// Priority
	public static void priority (ArrayList<Process> list) {
		// list of processes sorted according to arrival time
		PriorityQueue<Process> pq = new PriorityQueue<Process>  (list.size(), new ArrivalTimeComparator());
		for (Process o : list) {
			pq.offer (o);
		}
		int block = pq.peek().arrival; // current time
		// list of processes to be sorted according to priority
		PriorityQueue<Process> ongoing = new PriorityQueue<Process> (1, new PriorityComparator());
		while (!pq.isEmpty()) {
			Process p = pq.poll(); // current process
			Process q = pq.peek(); // next process
			// if p is the last process or the next process will arrive after the current process is finished
			if (q == null || q.arrival > p.burst + block) {
				System.out.println (block + " " + p.id + " " + p.burst + "X");
				block += p.burst;
				// while p is not the last process, the next process hasn't arrived yet, and there are pending processes
				while (q != null && q.arrival > p.burst + block && !ongoing.isEmpty()) {
					Process o = ongoing.poll(); // pending process with the highest priority
					if (q.prior < o.prior) {
						// label o as pending
						ongoing.offer (o);
					} else {
						int temp = q.arrival - block; // time elapsed before process was interrupted
						System.out.println (block + " " + p.id + " " + temp);
						block += temp;
						p.burst -= temp; // update remaining time
						// label next process as pending
						ongoing.offer (pq.poll());
					}
				}
			} else {
				if (p.prior < q.prior) {
					// label next process as pending
					ongoing.offer (pq.poll());
					// label current process as "unused"
					pq.offer (p);
				} else {
					int temp = q.arrival - block; // time elapsed before process was interrupted
					System.out.println (block + " " + p.id + " " + temp);
					block += temp;
					p.burst -= temp; // update remaining time
					// label current process as pending
					ongoing.offer (p);
				}
			}
		}
		// while there are still unfinished processes
		while (!ongoing.isEmpty()) {
			Process p = ongoing.poll();
			System.out.println (block + " " + p.id + " " + p.burst + "X");
			block += p.burst;
		}
	}
	
	// Round Robin
	public static void robin (ArrayList<Process> list, int rr) {
		// list of processes sorted according to arrival time
		PriorityQueue<Process> pq = new PriorityQueue<Process> (list.size(), new ArrivalTimeComparator());
		for (Process o : list) {
			pq.offer (o);
		}
		int block = pq.peek().arrival; // current time
		// list of pending processes
		ArrayList<Process> ongoing = new ArrayList<Process>();
		while (!pq.isEmpty()) {
			Process p = pq.poll(); // current process
			// if next process hasn't arrived yet
			while (p.arrival > block) {
				// if there are no pending processes
				if (ongoing.isEmpty()) {
					block = p.arrival;
					break;
				}
				Process q = ongoing.get (0); // queued process
				// if the queued process will finish within quantum time
				if (q.burst <= rr) {
					System.out.println (block + " " + q.id + " " + q.burst + "X");
					block += q.burst;
				} else {
					System.out.println (block + " " + q.id + " " + rr);
					q.burst -= rr; // update remaining time
					// add queued process to the end of the pending queue
					ongoing.add (q);
					block += rr;
				}
				ongoing.remove (0);
			}
			// if current process will finish in quantum time
			if (p.burst <= rr) {
				System.out.println (block + " " + p.id + " " + p.burst + "X");
				block += p.burst;
			} else {
				System.out.println (block + " " + p.id + " " + rr);
				p.burst -= rr; // update remaining time
				// add current process to the end of the pending queue
				ongoing.add (p);
				block += rr;
			}
		}
		// while there are still unfinished processes
		while (!ongoing.isEmpty()) {
			// equivalent of poll()
			Process p = ongoing.get (0); // current process
			ongoing.remove (p);
			// if current process will finish in quantum time
			if (p.burst <= rr) {
				System.out.println (block + " " + p.id + " " + p.burst + "X");
				block += p.burst;
			} else {
				System.out.println (block + " " + p.id + " " + rr);
				p.burst -= rr; // update remaining time
				// add current process to the end of the pending queue
				ongoing.add (p);
				block += rr;
			}
		}
	}
}