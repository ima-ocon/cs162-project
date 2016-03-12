import java.util.Comparator;

class PriorityComparator implements Comparator<Process> {
  @Override
  public int compare(Process p1, Process p2) {
	  if (p1.prior == p2.prior)
		  return p1.arrival - p2.arrival;
	  return p1.prior - p2.prior;
  }
}