/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_ass3;

import java.util.Scanner;

/**
 *
 * @author Hoda
 */
public class SJF {
    
    Process[] processes;
    int n;//number of process
    int lastCompletionT = 0;
  
    
    void getData(Scanner input) 
    {
    	 System.out.print("Enter the number of processes: ");
          n = input.nextInt();
         processes = new Process[n];
         for (int i = 0; i < n; i++) {
             System.out.print("Enter the name of process " + (i + 1) + ": ");
             String name = input.next();
             System.out.print("Enter the arrival time for Process " + (i + 1) + ": ");
             int arrivalTime = input.nextInt();
             System.out.print("Enter the burst time for Process " + (i + 1) + ": ");
             int brustTime = input.nextInt();
             processes[i] = new Process(name,  arrivalTime,  brustTime);
         }
    }
    
    void swap(int i, int j){
        Process temp;
        temp = processes[i];
        processes[i] = processes[j];
        processes[j] = temp;
    }

    void sorting() {

        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
               if(processes[j].getArrivalTime() < processes[i].getArrivalTime())
                  {
                    swap(i, j);
                }
                }
            }
        
        for (int i = 0; i < n ; i++){
            for (int j = i + 1; j <n; j++){
                if(processes[j].getBurstTime() < processes[i].getBurstTime()&&
                   processes[j].getArrivalTime() <= lastCompletionT){
                    swap(i, j);
                }
                
            }
            lastCompletionT += processes[i].getBurstTime();
        }
    }

    void print() {
        System.out.println();
        System.out.print("Processes execution order is : ");
         for (int i = 0; i < n; i++) {
            System.out.print( processes[i].getProcessName()+"   ");
        }System.out.println();
        System.out.println("ProcessID\t\tArrivalTime\t\tFinishTime\t\tWaitingTime\t\tTurnAroundTime");
        for (int i = 0; i < n; i++) {
            System.out.println("\t" + processes[i].getProcessName() + "\t\t\t" + processes[i].getArrivalTime() + "\t\t\t" + processes[i].finishingTime + "\t\t\t" + processes[i].waitingTime + "\t\t\t" + processes[i].turnaroundTime);
        }
    }
    
    void starviation(int k){
        
        
        for(int i = k; i < n ; i++){
             
            if (processes[i].getBurstTime() > 30 ){
                 processes[i].setPriority(processes[i].getPriority()+1);
            if (processes[i].getPriority() >20){
                    int index = i;
                    Process tmp = processes[k];
                    Process temp2;
                    processes[k]=processes[index];
                   for(int j = k+1; j < n ; j++){
                        if(j==index+1)
                          break;
                    temp2=processes[j];
                    processes[j]=tmp;
                    tmp=temp2;    
                  }
               }
            }
         }
          
    }
     void buildGanttChart(Scanner input) 
    {
    	getData(input);
         double sum1 = 0.0;
         double sum2 = 0.0;
         sorting();
         // Calculating finishing, turnaround, and waiting time
         processes[0].finishingTime = processes[0].getArrivalTime() + processes[0].getBurstTime() ;
         processes[0].turnaroundTime = processes[0].finishingTime - processes[0].getArrivalTime();
         processes[0].waitingTime =  processes[0].turnaroundTime - processes[0].getBurstTime();
         for (int i = 1; i < n; i++) {
             processes[i].finishingTime = processes[i - 1].finishingTime+ processes[i].getBurstTime();
            processes[i].turnaroundTime =  processes[i].finishingTime - processes[i].getArrivalTime();
             processes[i].waitingTime = processes[i].turnaroundTime - processes[i].getBurstTime();
            starviation(i+1);
         }
         for (Process Process : processes) {
             sum1 +=  Process.waitingTime;
              sum2 += Process.turnaroundTime;
         }
         double avgWaiting = sum1 / (double) n;
         double avgTurn = sum2/ (double) n;
         print();
         System.out.println("Average Waiting Time = " + avgWaiting);
         System.out.println("Average Turnaround Time = " + avgTurn);
    } 
    



}
