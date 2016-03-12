#include<iostream>
#include<stdio.h>

using namespace std;

struct Process {
  int arrivalTime;
  int burstTime;
  int priority;
  int index;
  bool isDone;

  Process(int a, int b, int p, int i) : arrivalTime(a), burstTime(b), priority(p), isDone(false), index(i) {}
};
