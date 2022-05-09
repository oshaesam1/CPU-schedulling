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
public class Priority {
     Process[] proc;
    Process pross;
    int[] finishingT;
    int[] waitingT;
    int[] turnaroundT;
    int n;//number of process
    int lastCompletionT = 0;
    int contextSwitching=0;

    void getData(Scanner input) 
    {
    	 System.out.print("Enter the number of processes: ");
    	 n = input.nextInt();
          System.out.print("context switching: ");
          contextSwitching=input.nextInt();
         proc = new Process[n];
         for (int i = 0; i < n; i++) {
             System.out.print("Enter the name of process " + (i + 1) + ": ");
             String name = input.next();
             System.out.print("Enter the burst time   for Process " + (i + 1) + ": ");
             int brustTime = input.nextInt();
             System.out.print("Enter the arrival time for Process " + (i + 1) + ": ");
             int arrivalTime = input.nextInt();
             System.out.print("Enter the priority   for Process " + (i + 1) + ": ");
             int priority = input.nextInt();
             proc[i] = new Process(name,  arrivalTime,  brustTime, priority);
         }
    }
    void swap(int i, int j){
        Process temp;
        temp = proc[i];
        proc[i] = proc[j];
        proc[j] = temp;
    }

    void sorting(int k) {

        for (int i = k+1; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
               if(proc[j].getArrivalTime() < proc[i].getArrivalTime())
                  {
                    swap(i, j);
                }
                }
            }
        
        for (int i = k; i < n ; i++){
            for (int j = i + 1; j <n; j++){
                if(proc[j].getPriority() < proc[i].getPriority()&&
                   proc[j].getArrivalTime() <= lastCompletionT){
                    swap(i, j);
                }
                
            }
            lastCompletionT += proc[i].getBurstTime();
        }
    }
    
   

    void print() {

         System.out.println();
        System.out.print("Processes execution order is : ");
         for (int i = 0; i < n; i++) {
            System.out.print( proc[i].getProcessName()+"   ");
        }System.out.println();
        System.out.println("ProcessID\t\tArrivalTime\t\tPriority\t\tFinishTime\t\tWaitingTime\t\tTurnAroundTime");
        for (int i = 0; i < n; i++) {
            System.out.println("\t" + proc[i].getProcessName() + "\t\t\t" + proc[i].getArrivalTime() + "\t\t\t" + proc[i].getPriority() + "\t\t\t" + finishingT[i] + "\t\t\t" + waitingT[i] + "\t\t\t" + turnaroundT[i]);
        }
    }
    void starviation(int k){
//        System.out.println("shaaaaaa");
//         for(int y = k; y < n ; y++){
//                     
//                   System.out.println(proc[y].getProcessName()+"   "+y);
//                 }
        for(int i = k; i < n ; i++){
            if (proc[i].getPriority() > 20){
                proc[i].setPriority(proc[i].getPriority()-1);
                sorting(k);
                 } 
//                 System.out.println("yeeees");
//                 System.out.println( proc[i].getProcessName()+"  "+ proc[i].getPriority() );  
            
//        }System.out.println("faaaaaaaaaaa");
//                 for(int j = k; j < n ; j++){
//                     
//                     System.out.println(proc[j].getProcessName()+"  "+j);
//                 }
        
        } 
    }

    void NonPreem(Scanner input) 
    {
    	getData(input);
         finishingT = new int[n];
         waitingT = new int[n];
         turnaroundT = new int[n];
         double sum = 0.0;
         sorting(0);
         // Calculating finishing, turnaround, and waiting time
         finishingT[0] = proc[0].getArrivalTime() + proc[0].getBurstTime()+contextSwitching ; ;
         turnaroundT[0] = finishingT[0] - proc[0].getArrivalTime();
         waitingT[0] = turnaroundT[0] - proc[0].getBurstTime();
         for (int i = 1; i < n; i++) {
             finishingT[i] = finishingT[i - 1] + proc[i].getBurstTime()+contextSwitching ; ;
             turnaroundT[i] = finishingT[i] - proc[i].getArrivalTime();
             waitingT[i] = turnaroundT[i] - proc[i].getBurstTime();
            starviation(i+1);
         }
         for (int wait : waitingT) {
             sum += wait;
         }
         double avgWaiting = sum / (double) n;
         sum = 0.0;
         for (int turnaround : turnaroundT) {
             sum += turnaround;
         }
         double avgTurn = sum / (double) n;
         print();
         System.out.println("Average Waiting Time = " + avgWaiting);
         System.out.println("Average Turnaround Time = " + avgTurn);
    } 
    
}
