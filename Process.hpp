#include<iostream>
#include<stdio.h>

using namespace std;

struct Process {
  int arrivalTime;
  int burstTime;
  int priority;
  int index;
  bool isDone;

  Process(int b, int a, int p, int i) : arrivalTime(a), burstTime(b), priority(p), isDone(false), index(i) {}

  bool operator < (const Process& process) const {
    return (arrivalTime < process.arrivalTime);
  }
};

/*struct earliest_arrival_time {
  inline bool operator() (const Process& struct1, const Process& struct2){
    return (struct1.arrivalTime < struct2.arrivalTime);
  }
};*/
