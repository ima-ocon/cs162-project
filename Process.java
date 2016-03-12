

class Process{
  int arrivalTime, burstTime, priority, index;

  Process(int arrivalTime, int burstTime, int priority, int index) {
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.priority = priority;
    this.index = index;
  }

  int getArrivalTime() {
    return arrivalTime;
  }

  int getPriority() {
    return priority;
  }
}
