

class Process{
  int arrivalTime, burstTime, priority, index;
  int remainingTime;

  Process(int arrivalTime, int burstTime, int priority, int index) {
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.priority = priority;
    this.index = index;
    this.remainingTime = burstTime;
  }

  int getArrivalTime() {
    return arrivalTime;
  }

  int getPriority() {
    return priority;
  }

  void print() {
    System.out.println(arrivalTime + " " + burstTime + " " + priority);
  }

  void run() {
    remainingTime--;
  }
}
