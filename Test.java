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

    Collections.sort(unusedProcesses, new ArrivalTimeComparator());

    int currentTime = 0;
    boolean added = false;

    while (processesLeft > 0){
      added = false;

/*      for (int x = 0; x < unusedProcesses.size(); x++) {
        System.out.println("Unused:");
              unusedProcesses.get(x).print();
      }*/

      while ((unusedProcesses.size()) > 0 && (unusedProcesses.get(0).getArrivalTime() == currentTime)) {
        currentProcesses.add(unusedProcesses.get(0));
        unusedProcesses.remove(0);
        added = true;
      }

      if (added) {
        Collections.sort(currentProcesses, new PriorityComparator());
        currentProcess = currentProcesses.get(0);
      }

      System.out.println("Current time: " + currentTime);
      currentProcess.print();

      currentProcess.run();
      if (currentProcess.remainingTime == 0) {
        System.out.println("Done!");
        currentProcesses.remove(0);
        if (currentProcesses.size() > 0)
          currentProcess = currentProcesses.get(0);
        processesLeft--;
      }

/*      for (int x = 0; x < currentProcesses.size(); x++) {
        System.out.println(currentProcesses.get(x).getArrivalTime());
      }*/

      currentTime++;
    }
  }
}
