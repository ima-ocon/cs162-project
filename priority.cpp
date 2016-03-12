#include<iostream>
#include<stdio.h>
#include "Process.hpp"
using namespace std;

int main() {
  Process* processes[5] = {};

  processes[0] = new Process(20, 0, 1);
  processes[1] = new Process(10, 0, 5);
  processes[2] = new Process(15, 0, 4);
  processes[3] = new Process(3, 5, 0);
  processes[4] = new Process(4, 7, 2);

  int numAllProcesses = 5;
  int numProcessesLeft = numAllProcesses;

  int currentTIme = 0;

  int firstPriorityProcess = 0;
  int firstPriorityNum = processes[0]->priority;

  for (int x = 0; x < numAllProcesses; x++) {
    if (firstPriorityNum > processes[x]->priority) {
      firstPriorityProcess = x;
      firstPriorityNum = processes[x]->priority;
    }
  }

  cout << firstPriorityNum;

/*
  //time so far, which process, CPU time


  //look for least priority, only if not yet done and arrivalTime <= currentTime
  //keep priority num in mind
  //keep on incrementing time until currentTime == arrivalTime
  //check if priority is lower
  //if yes, set aside then
  //if done, set to -1...?

  int firstPriority = 0;
  int priorityNum = priority[0];

  int incomingNum = 0;
  int priorityIncoming = 0;

  while (numAllProcesses > 0) {
    //check least time, only if arrivalTime <= currentTime and != -1
    for (int x = 0; x < numAllProcesses; x++) {

      //checks which has the least priority
      if ((arrivalTime[x] <= currentTime) && (arrivalTime[x] != -1)) {
        if (priority[x] < priorityNum) {
          priorityNum = priority[x];
          firstPriority = x;
        }
      }
    }



    //execute until a new one arrives
    //when a new one arrives, check if priority is higher
    //if higher, switch
    //if not, check for incoming again...
    //if done, mark; then recheck which has lower priority
  }*/

  return 0;
}
