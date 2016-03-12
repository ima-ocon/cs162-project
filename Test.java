import java.util.*;

class Test {
  public static void main(String args[]) {
    List<Process> unusedProcesses = new ArrayList<Process>();
    unusedProcesses.add(new Process(0, 20, 1, 1));
    unusedProcesses.add(new Process(0, 10, 5, 2));
    unusedProcesses.add(new Process(0, 15, 4, 3));
    unusedProcesses.add(new Process(5, 3, 0, 4));
    unusedProcesses.add(new Process(7, 4, 2, 5));

    Collections.sort(unusedProcesses, new ArrivalTimeComparator());

    for (int x = 0; x < unusedProcesses.size(); x++) {
      System.out.println(unusedProcesses.get(x).getArrivalTime());
    }
  }
}
