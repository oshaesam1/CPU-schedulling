/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os_ass3;

import java.util.Vector;

/**
 *
 * @author Hoda
 */
public class Process {
    String Name;
    int arrivalTime;
    int burstTime;
    int remain_T ;
    double V1;
    public Vector<String> agatHistory = new Vector<String>();
    public Vector<String > quantamHistory = new Vector<String>();
     AGAT agat;
    private int priority;
    
     int waitingTime;
     int turnaroundTime;
    int finishingTime;
    int ArrivalTime;
    int remainingTime;
    int AgatFactor;
    int Quantum;

    Process(String Name, int arrivalTime, int burstTime, int priority) {
        this.Name = Name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
    Process(int Quantum, String processName, int burstTime, int ArrivalTime,int priority){
        this.Quantum    =Quantum;
        //Math.round((40/100)*Quantum)
        this.Name=processName;
        this.burstTime  =burstTime;
        remainingTime=burstTime;
        this.ArrivalTime=ArrivalTime;
        this.priority=priority;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.finishingTime=0;
        agatHistory =new Vector<>();
        quantamHistory =new Vector<>();
    }
    Process(String Name, int arrivalTime, int burstTime) {
        this.Name = Name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = 0;
        this.remain_T=burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }
    Process() {
        this.Name = " ";
        this.arrivalTime = 0;
        this.burstTime = 0;
        this.priority = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }
    public String getProcessName() {
        return Name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }
    public int getWaitingTime() {
        return waitingTime;
    }
    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setPriority(int priority) { this.priority = priority; }
    public int calculateTurnAroundTime() {
        turnaroundTime=this.finishingTime - this.ArrivalTime;
        return turnaroundTime;
    }
    public void set_End(int end) {
        this.finishingTime = end;
    }

    public void set_Remaining(int r){
        remain_T=r;
    }

    public int calculateWaitingTime() {

        waitingTime= Math.abs(this.burstTime- this.turnaroundTime);
        return waitingTime;
    }
    public boolean Done() {
        return remainingTime == 0;
    }

    public void editBrustTime(int time) {
        remainingTime = time;

    }
    public void updateAGATS(int AgatFactor) {
        this.AgatFactor = AgatFactor;

        agatHistory.add( agat.time + ": " + AgatFactor);
    }
    public void updateQuantam(int quantam) {
        if (!quantamHistory.contains("" + Quantum))
            quantamHistory.add( "" + Quantum);
    }

	
}

