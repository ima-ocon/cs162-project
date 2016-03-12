import java.util.Comparator;

class ArrivalTimeComparator implements Comparator<Process> {
  @Override
  public int compare(Process p1, Process p2) {
    return p1.arrivalTime - p2.arrivalTime;
  }
}
