#include<iostream>
#include<stdio.h>
#include "Process.hpp"
using namespace std;

int main() {
  Process* processes[5] = {};

  processes[0] = new Process(20, 0, 1);
  processes[1] = new Process(10, 0, 5);
/*  int numAllProcesses = 5;
  int numProcessesLeft = numAllProcesses;
  int arrivalTime[numProcesses] = {20, 10, 15, 3, 4};
  int burstTime[numProcesses] = {0, 0, 0, 5, 7};
  int priority[numProcesses] = {1, 5, 4, 0, 2};
  int currentTime = 0;

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
