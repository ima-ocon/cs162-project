#include<iostream>
#include<stdio.h>
#include<algorithm>
#include<vector>
#include "Process.hpp"
using namespace std;


//2 arrays: sorted by priority
//2 arrays: arrival time


int main() {
  vector<Process> currentProcesses;
  vector<Process> unusedProcesses;
  //array where algorithms sorted by priority
  //another array where algorithms sorted by arrival time

  unusedProcesses.push_back(Process(20, 0, 1, 1));
  unusedProcesses.push_back(Process(10, 0, 5, 2));
  unusedProcesses.push_back(Process(15, 0, 4, 3));
  unusedProcesses.push_back(Process(3, 5, 0, 4));
  unusedProcesses.push_back(Process(4, 7, 2, 5));
  //time so far, which process, CPU time

  sort(unusedProcesses.begin(), unusedProcesses.end());

  int x = 0;
  while (x < unusedProcesses.size()) {
    if (unusedProcesses.at(x).arrivalTime == 0) {
      cout << "zero";
      unusedProcesses.erase(unusedProcesses.begin() + x);
    } else {
      x++;
    }
  }

  for (int x = 0; x < unusedProcesses.size(); x++) {
    cout << unusedProcesses.at(x).arrivalTime << endl;
  }

  //look for least priority, only if not yet done and arrivalTime <= currentTime
  //keep priority num in mind
  //keep on incrementing time until currentTime == arrivalTime
  //check if priority is lower
  //if yes, set aside then
  //if done, set to -1...?

/*  int firstPriority = 0;
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
