import java.util.Comparator;

class RemainingTimeComparator implements Comparator<Process> {
  @Override
  public int compare(Process p1, Process p2) {
    return p1.remainingTime - p2.remainingTime;
  }
}
