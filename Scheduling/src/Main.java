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

			//just testing this because my java version doesn't allow switch
			//statements with strings lol
			if (sched.equals("FCFS"))
				fcfs(list);
				else if (sched.equals("SJF"))
					sjf(list);
					else if (sched.equals("SRTF"))
						priorityOrSRTF(list, new RemainingTimeComparator());
						else if (sched.equals("P"))
							priorityOrSRTF(list, new PriorityComparator());
							else if (sched.equals("RR"))
								robin(list, rr);

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

	public static void priorityOrSRTF(ArrayList<Process>list, Comparator<Process> comparator) {
		Process currentProcess = null;

		List<Process> currentProcesses = new ArrayList<Process>();

		List<Process> unusedProcesses = new ArrayList<Process>();
		for (int x = 0; x < list.size(); x++) {
			Process p = list.get(x);
			p.index = x + 1;
			unusedProcesses.add(p);
		}

		int processesLeft = unusedProcesses.size();

		//unused processes -> sorted based on arrival time
		Collections.sort(unusedProcesses, new ArrivalTimeComparator());

		int currentTime = 0;
		int timeProcessStarted = currentTime;
		boolean added = false;

		//while you still have processes left
		while (processesLeft > 0){
			added = false;

			//if right now, there's a process that's arriving
			while ((unusedProcesses.size()) > 0 && (unusedProcesses.get(0).getArrivalTime() == currentTime)) {
				//add it to ongoing processes
				currentProcesses.add(unusedProcesses.get(0));
				//remove it from unused processes
				unusedProcesses.remove(0);
				added = true;
			}

			//if a new process just arrived
			if (added) {
				//resort current processes so new process ends up where it should
				Collections.sort(currentProcesses, comparator);
				//get the first priority process as current process
				if ((currentProcess != null) && (currentProcess.index != currentProcesses.get(0).index)) {
					System.out.println(timeProcessStarted + " " + currentProcess.index + " " + (currentTime - timeProcessStarted));
					timeProcessStarted = currentTime;
				}

				currentProcess = currentProcesses.get(0);
			}

			if (currentProcess == null) {
				currentTime++;
				continue;
			}

			//burst time of current process - 1
			currentProcess.run();
			//if burst time is 0, process is done
			if (currentProcess.remainingTime == 0) {
				//remove process from ongoing queue
				System.out.println(timeProcessStarted + " " + currentProcess.index + " " + (currentTime - timeProcessStarted + 1) + "X");
				currentProcesses.remove(0);
				//if there are still processes in ongoing queue, get the next one
				if (currentProcesses.size() > 0) {
					currentProcess = currentProcesses.get(0);
					timeProcessStarted = currentTime+1;
				}
				processesLeft--;
			}

			//move on to next second
			currentTime++;
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

	// Shortest Remaining Time First
/*	public static void srtf (ArrayList<Process> list) {
		Process currentProcess = null;

		List<Process> currentProcesses = new ArrayList<Process>();
		List<Process> unusedProcesses = new ArrayList<Process>();

		for (int x = 0; x < list.size(); x++) {
			Process p = list.get(x);
			p.index = x + 1;
			unusedProcesses.add(p);
		}

		int processesLeft = unusedProcesses.size();

		//unused processes -> sorted based on arrival time
		Collections.sort(unusedProcesses, new ArrivalTimeComparator());

		int currentTime = 0;
		int timeProcessStarted = currentTime;
		boolean added = false;

		//while you still have processes left
		while (processesLeft > 0){
			added = false;

			//if right now, there's a process that's arriving
			while ((unusedProcesses.size()) > 0 && (unusedProcesses.get(0).getArrivalTime() == currentTime)) {
				//add it to ongoing processes
				currentProcesses.add(unusedProcesses.get(0));
				//remove it from unused processes
				unusedProcesses.remove(0);
				added = true;
			}

			//if a new process just arrived
			if (added) {
				//resort current processes so new process ends up where it should
				Collections.sort(currentProcesses, new RemainingTimeComparator());
				//get the first priority process as current process
				if ((currentProcess != null) && (currentProcess.index != currentProcesses.get(0).index)) {
					System.out.println(timeProcessStarted + " " + currentProcess.index + " " + (currentTime - timeProcessStarted));
					timeProcessStarted = currentTime;
				}

				currentProcess = currentProcesses.get(0);
			}

			//burst time of current process - 1
			currentProcess.run();
			//if burst time is 0, process is done
			if (currentProcess.remainingTime == 0) {
				//remove process from ongoing queue
				System.out.println(timeProcessStarted + " " + currentProcess.index + " " + (currentTime - timeProcessStarted + 1) + "X");
				currentProcesses.remove(0);
				//if there are still processes in ongoing queue, get the next one
				if (currentProcesses.size() > 0) {
					currentProcess = currentProcesses.get(0);
					timeProcessStarted = currentTime+1;
				}
				processesLeft--;
			}

			//move on to next second
			currentTime++;
		}

/*		//priority queue based on arrival time
		PriorityQueue<Process> pq = new PriorityQueue<Process> (list.size(), new ArrivalTimeComparator());

		//just fills up the arrival time queue
		for (Process o : list) {
			pq.offer (o);
		}

		//checks when the next arrival is
		int block = pq.peek().arrival;

		//makes a priority queue based on burst time
		PriorityQueue<Process> ongoing = new PriorityQueue<Process> (1, new BurstTimeComparator());

		//while there are still ongoing processes
		while (!pq.isEmpty()) {

			//p -> current process
			Process p = pq.poll();
			Process q = pq.peek();

			//if no more processes or current finishes before next arrives
			if (q == null || q.arrival > p.burst + block) {

//if you still have processes AND these next
				//go ahead and do the process
				System.out.println (block + " " + p.id + " " + p.burst + "X");

				//block refers to the amount of time elapsed
				//so just add the burst time
				block += p.burst;


				//while there are still processes arriving + processes left behind
				while (q != null && q.arrival > block && !ongoing.isEmpty()) {
					//get the current process
					Process o = ongoing.poll();

					//if new process will arrive before current process ends
					if (q.arrival < o.burst + block) {
						//if it's going to take less time
						if (q.burst <= o.burst) {
							int temp = q.arrival - block;
							System.out.println (block + " " + o.id + " " + temp);
							//adjusting time of old
							o.burst -= temp;
							//moving on to arrival time of new
							block += temp;
							//put it back in ongoing
							ongoing.offer (o);
						} else {
							// TODO Fix bug here.
							//otherwise put new process into ongoing queue
							ongoing.offer (pq.poll());
						}
						//if no new process just continue on as before
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
	}*/

	// Priority
	/*public static void priority (ArrayList<Process> list) {
		//the efficient version
		/*
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
	}*/
}
