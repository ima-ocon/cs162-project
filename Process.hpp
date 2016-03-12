#include<iostream>
#include<stdio.h>

using namespace std;

struct Process {
  Process(int arrivalTime, int burstTime, int priority) {
    this->arrivalTime = arrivalTime;
    this->burstTime = burstTime;
    this->priority = priority;
    this->isDone = false;
  }

  int arrivalTime;
  int burstTime;
  int priority;
  bool isDone;
};
