import java.util.*;

class Test {
  public static void main(String args[]) {
    int processesLeft = 5;
    Process currentProcess = null;

    List<Process> currentProcesses = new ArrayList<Process>();

    List<Process> unusedProcesses = new ArrayList<Process>();
    unusedProcesses.add(new Process(0, 20, 1, 1));
    unusedProcesses.add(new Process(0, 10, 5, 2));
    unusedProcesses.add(new Process(0, 15, 4, 3));
    unusedProcesses.add(new Process(5, 3, 0, 4));
    unusedProcesses.add(new Process(7, 4, 2, 5));

    //unused processes -> sorted based on arrival time
    Collections.sort(unusedProcesses, new ArrivalTimeComparator());

    int currentTime = 0;
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
        Collections.sort(currentProcesses, new PriorityComparator());
        //get the first priority process as current process
        currentProcess = currentProcesses.get(0);
      }

      /*System.out.println("Current time: " + currentTime);
      currentProcess.print();*/

      //burst time of current process - 1
      currentProcess.run();
      //if burst time is 0, process is done
      if (currentProcess.remainingTime == 0) {
        System.out.println("Done!");
        //remove process from ongoing queue
        currentProcesses.remove(0);
        //if there are still processes in ongoing queue, get the next one
        if (currentProcesses.size() > 0)
          currentProcess = currentProcesses.get(0);
        processesLeft--;
      }

      //move on to next second
      currentTime++;
    }
  }
}
