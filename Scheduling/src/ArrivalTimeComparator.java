import java.util.Comparator;

class ArrivalTimeComparator implements Comparator<Process> {
  @Override
  public int compare(Process p1, Process p2) {
	  if (p1.arrival == p2.arrival)
		  return p1.burst - p2.burst;
	  return p1.arrival - p2.arrival;
  }
}