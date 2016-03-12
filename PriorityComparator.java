import java.util.Comparator;

class PriorityComparator implements Comparator<Process> {
  @Override
  public int compare(Process p1, Process p2) {
    return p1.priority - p2.priority;
  }
}
