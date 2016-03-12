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
		PriorityQueue<Process> pq = new PriorityQueue<Process>  (list.size(), new ArrivalTimeComparator());
		int block = 0;
		for (Process o : list) {
			pq.offer (o);
		}
		while (!pq.isEmpty()) {
			Process p = pq.poll();
			Process q = pq.peek();
			System.out.println (block + " " + p.id + " " + p.burst + "X");
			try {
				if (q.arrival > p.burst)
					block = q.arrival;
				continue;
			} catch (Exception e) {}
			block += p.burst;
		}
	}
	
	// Shortest Job First
	public static void sjf (ArrayList<Process> list) {
		PriorityQueue<Process> pq = new PriorityQueue<Process> (list.size(), new BurstTimeComparator());
		for (Process o : list) {
			pq.offer (o);
		}
		int block = pq.peek().arrival;;
		while (!pq.isEmpty()) {
			Process p = pq.poll();
			// TODO Clarify details
			System.out.println (block + " " + p.id + " " + p.burst + "X");
			block += p.burst;
		}
	}
	
	// Shortest Remaining Time First
	public static void srtf (ArrayList<Process> list) {
		PriorityQueue<Process> pq = new PriorityQueue<Process> (list.size(), new ArrivalTimeComparator());
		int block = 0;
		for (Process o : list) {
			pq.offer (o);
		}
		while (!pq.isEmpty()) {
			Process p = pq.poll();
			Process q = pq.peek();
			if (q == null || q.arrival > p.burst) {
				System.out.println (block + " " + p.id + " " + p.burst + "X");
				// TODO Something is missing here.
				try {
					if (q.arrival < p.burst)
						block = q.arrival;
					continue;
				} catch (Exception e) {}
				block += p.burst;
			} else {
				int temp = p.burst - q.arrival;
				p.arrival = q.arrival;
				if (temp <= q.burst) {
					System.out.println (block + " " + p.id + " " + p.burst + "X");
					block += p.burst;
				} else {
					System.out.println (block + " " + p.id + " " + q.arrival);
					p.burst = temp;
					p.arrival = q.arrival;
					pq.offer (p);
					block = q.arrival;
				}
			}
		}
	}
	
	// Priority
	public static void priority (ArrayList<Process> list) {
		PriorityQueue<Process> pq = new PriorityQueue<Process>  (list.size(), new PriorityComparator());
		int block = 0;
		for (Process o : list) {
			pq.offer (o);
		}
		while (!pq.isEmpty()) {
			Process p = pq.poll();
			Process q = pq.peek();
			System.out.println (block + " " + p.id + " " + p.burst + "X");
			try {
				if (q.arrival > p.burst)
					block = q.arrival;
				continue;
			} catch (Exception e) {}
			block += p.burst;
		}
	}
	
	// Round Robin
	public static void robin (ArrayList<Process> list, int rr) {
		
	}
}