import java.util.*;

class SRTF {
  public static void main(String args[]) {
    int processesLeft = 4;
    Process currentProcess = null;

    List<Process> currentProcesses = new ArrayList<Process>();

    List<Process> unusedProcesses = new ArrayList<Process>();
    unusedProcesses.add(new Process(0, 50, 2, 1));
    unusedProcesses.add(new Process(40, 2, 3, 2));
    unusedProcesses.add(new Process(20, 3, 1, 3));
    unusedProcesses.add(new Process(30, 55, 1, 3));

    System.out.println("Hello");

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
        Collections.sort(currentProcesses, new RemainingTimeComparator());
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
