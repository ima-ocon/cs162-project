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
/*			switch (sched) {
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
			}*/

			srtf(list);
		}
	}

	// First Come, First Served
	public static void fcfs (ArrayList<Process> list) {
		PriorityQueue<Process> pq = new PriorityQueue<Process>  (list.size(), new ArrivalTimeComparator());
		for (Process o : list) {
			pq.offer (o);
		}
		int block = pq.peek().arrival;
		while (!pq.isEmpty()) {
			Process p = pq.poll();
			Process q = pq.peek();
			System.out.println (block + " " + p.id + " " + p.burst + "X");
			block += p.burst;
			try {
				if (q.arrival > block + p.burst)
					block = q.arrival;
				continue;
			} catch (Exception e) {}
		}
	}

	// Shortest Job First
	public static void sjf (ArrayList<Process> list) {
		PriorityQueue<Process> pq = new PriorityQueue<Process> (list.size(), new BurstTimeComparator());
		for (Process o : list) {
			pq.offer (o);
		}
		int block = pq.peek().arrival;
		while (!pq.isEmpty()) {
			Process p = pq.poll();
			Process q = pq.peek();
			// TODO Clarify details
			System.out.println (block + " " + p.id + " " + p.burst + "X");
			block += p.burst;
			try {
				if (q.arrival > block + p.burst)
					block = q.arrival;
				continue;
			} catch (Exception e) {}
		}
	}

	// Shortest Remaining Time First
	public static void srtf (ArrayList<Process> list) {
		PriorityQueue<Process> pq = new PriorityQueue<Process> (list.size(), new ArrivalTimeComparator());
		for (Process o : list) {
			pq.offer (o);
		}
		int block = pq.peek().arrival;
		PriorityQueue<Process> ongoing = new PriorityQueue<Process> (1, new BurstTimeComparator());
		while (!pq.isEmpty()) {
			Process p = pq.poll();
			Process q = pq.peek();
			if (q == null || q.arrival > p.burst + block) {
				System.out.println (block + " " + p.id + " " + p.burst + "X");
				block += p.burst;
				while (q != null && q.arrival > block && !ongoing.isEmpty()) {
					Process o = ongoing.poll();
					if (q.arrival < o.burst + block) {
						if (q.burst <= o.burst) {
							int temp = q.arrival - block;
							System.out.println (block + " " + o.id + " " + temp);
							o.burst -= temp;
							block += temp;
							ongoing.offer (o);
						} else {
							// TODO Fix bug here.
							ongoing.offer (pq.poll());
						}
					} else {
						System.out.println (block + " " + o.id + " " + o.burst + "X");
						block += o.burst;
					}
				}
			} else {
				if (p.burst < q.burst) {
					ongoing.offer (pq.poll());
					pq.offer (p);
				} else {
					int temp = q.arrival - block;
					System.out.println (block + " " + p.id + " " + temp);
					block += temp;
					p.burst -= temp;
					ongoing.offer (p);
				}
			}
		}
		while (!ongoing.isEmpty()) {
			Process p = ongoing.poll();
			System.out.println (block + " " + p.id + " " + p.burst + "X");
			block += p.burst;
		}
	}

	// Priority
	public static void priority (ArrayList<Process> list) {
		PriorityQueue<Process> pq = new PriorityQueue<Process>  (list.size(), new ArrivalTimeComparator());
		for (Process o : list) {
			pq.offer (o);
		}
		int block = pq.peek().arrival;
		PriorityQueue<Process> ongoing = new PriorityQueue<Process> (1, new PriorityComparator());
		while (!pq.isEmpty()) {
			Process p = pq.poll();
			Process q = pq.peek();
			if (q == null || q.arrival > p.burst + block) {
				System.out.println (block + " " + p.id + " " + p.burst + "X");
				block += p.burst;
				while (q != null && q.arrival > p.burst + block && !ongoing.isEmpty()) {
					Process o = ongoing.poll();
					if (q.prior < o.prior) {
						ongoing.offer (o);
					} else {
						int temp = q.arrival - block;
						System.out.println (block + " " + p.id + " " + temp);
						block += temp;
						p.burst -= temp;
						ongoing.offer (q);
					}
				}
			} else {
				if (p.prior < q.prior) {
					ongoing.offer (pq.poll());
					pq.offer (p);
				} else {
					int temp = q.arrival - block;
					System.out.println (block + " " + p.id + " " + temp);
					block += temp;
					p.burst -= temp;
					ongoing.offer (p);
				}
			}
		}
		while (!ongoing.isEmpty()) {
			Process p = ongoing.poll();
			System.out.println (block + " " + p.id + " " + p.burst + "X");
			block += p.burst;
		}
	}

	// Round Robin
	public static void robin (ArrayList<Process> list, int rr) {
		PriorityQueue<Process> pq = new PriorityQueue<Process> (list.size(), new ArrivalTimeComparator());
		int block = 0;
		for (Process o : list) {
			pq.offer (o);
		}
		ArrayList<Process> ongoing = new ArrayList<Process>();
		while (!pq.isEmpty()) {
			Process p = pq.poll();
			while (p.arrival >= block) {
				if (ongoing.isEmpty()) {
					block = p.arrival;
					break;
				}
				Process q = ongoing.get (0);
				if (q.burst <= rr) {
					System.out.println (block + " " + q.id + " " + q.burst + "X");
					block += q.burst;
				} else {
					System.out.println (block + " " + q.id + " " + rr);
					q.burst -= rr;
					ongoing.add (q);
					block += rr;
				}
				ongoing.remove (0);
			}
			if (p.burst <= rr) {
				System.out.println (block + " " + p.id + " " + p.burst + "X");
				block += p.burst;
			} else {
				System.out.println (block + " " + p.id + " " + rr);
				p.burst -= rr;
				ongoing.add (p);
				block += rr;
			}
		}
	}
}
