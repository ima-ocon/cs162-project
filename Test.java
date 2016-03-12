import java.util.*;

class Test {
  public static void main(String args[]) {
    List<Process> currentProcesses = new ArrayList<Process>();

    List<Process> unusedProcesses = new ArrayList<Process>();
    unusedProcesses.add(new Process(0, 20, 1, 1));
    unusedProcesses.add(new Process(0, 10, 5, 2));
    unusedProcesses.add(new Process(0, 15, 4, 3));
    unusedProcesses.add(new Process(5, 3, 0, 4));
    unusedProcesses.add(new Process(7, 4, 2, 5));

    Collections.sort(unusedProcesses, new ArrivalTimeComparator());

    int currentTime = 0;

    for (int x = 0; x < unusedProcesses.size(); x++) {
      if (unusedProcesses.get(x).getArrivalTime() == currentTime) {
        currentProcesses.add(unusedProcesses.get(x));
        //transfer to other list
      }
      else
        break;
    }

    Collections.sort(currentProcesses, new PriorityComparator());

    for (int x = 0; x < currentProcesses.size(); x++) {
      System.out.println(currentProcesses.get(x).getPriority());
    }
  }
}
