#include<iostream>
#include<stdio.h>

using namespace std;

struct Process {
  Process() {
    arrivalTime = 0;
    burstTime = 0;
    priority = 0;
    }

  Process(int arrivalTime, int burstTime, int priority) {
    this->arrivalTime = arrivalTime;
    this->burstTime = burstTime;
    this->priority = priority;
  }

  int arrivalTime;
  int burstTime;
  int priority;
};
