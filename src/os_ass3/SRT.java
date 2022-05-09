package os_ass3;

import java.util.ArrayList;
import java.util.Scanner;

public class SRT {
    private ArrayList<Process> srt = new ArrayList<>();
    private ArrayList<Process> arrived = new ArrayList<>();
    private ArrayList<Process> not_arrived = new ArrayList<>();
    private static ArrayList<Process> p =new ArrayList<>();
    private Process curr , prev;
    private  boolean Switch = false;
    private  int context_switch_T = 0 , clk = 0;

    public SRT(){}

    public SRT(ArrayList <Process> p , int c_sw) {
        context_switch_T = c_sw;
        not_arrived = new ArrayList<>(p);
    }

    private void activate(){

        for (int i = 0; i < not_arrived.size(); i++) {
            Process tmp = not_arrived.get(i);
            if (tmp.arrivalTime <= clk) {
                arrived.add(tmp);
                not_arrived.remove(tmp);
            }
        }
    }

    public void Min_process(){

        int i = 0;
        if (clk != 0) {
            int remain_T = curr.remain_T;
            if (!Switch || context_switch_T == 0) {
                remain_T = remain_T-1;
                curr.set_Remaining(remain_T);

                if (remain_T == 0) {
                    curr.set_End(clk);
                    arrived.remove(curr);
                }
            }
        }

        if (arrived.size() != 0) {
            int min = arrived.get(i).remain_T;
            Switch = false;

            if (clk != 0) {
                prev = curr;
            }

            for (  int j = 0; j < arrived.size(); j++) {

                if (arrived.get(j).remain_T < min && arrived.get(j).remain_T > 0) {
                    min = arrived.get(j).remain_T;
                    i=j;
                }

            }
            curr = arrived.get(i);

            if (clk != 0 && ! curr.equals(prev)) {
                Switch = true;
            }
        }

    }

    void cal_Wait_Turn(ArrayList <Process> p){
        float Total_wait = 0;
        float Total_turnAround = 0;

        for (int i = 0; i < p.size(); i++) {

            float turnAround = p.get(i).finishingTime - p.get(i).arrivalTime;
            float  wait = turnAround - p.get(i).burstTime;

            System.out.println("Waiting time for  " + p.get(i).Name + " = " + wait);
            System.out.println("TurnAround time for " + p.get(i).Name + " = " +turnAround);

            Total_wait+= wait;
            Total_turnAround+=turnAround;
        }

        float avg_Wait = Total_wait / p.size();
        float avg_TurnAround = Total_turnAround / p.size();

        System.out.println("Average Waiting time by SRT = " + avg_Wait);
        System.out.println("Average TurnAround time by SRT = " + avg_TurnAround);
    }

    void print(){
        for (int i = 0; i < srt.size()-1; i++) {
            System.out.print(srt.get(i).Name);
            System.out.print("::");
        }
        System.out.println();
    }


    void  run(){
        Scanner input = new Scanner(System.in);
        int arr ,burst , size,c_sw;
        String name ;

        System.out.println("Enter Number of processes :");
        size=input.nextInt();

        System.out.println("Enter  Process Name , Arrival Time , Burst Time ( in this order ) :");
        for (int i=0;i<size;i++){
            name= input.next();
            arr=input.nextInt();
            burst=input.nextInt();

            Process tmp=new Process(name,arr,burst);
            p.add(tmp);
        }

        System.out.println("Enter Context Switching Time :");
        c_sw = input.nextInt();
        SRT s = new SRT(p,c_sw);

        while (s.arrived.size() != 0 || s.clk == 0) {
            s.activate();
            s.Min_process();

            if ( s.Switch && s.context_switch_T != 0) {

                s.clk += s.context_switch_T;
            }
            else {
                s.srt.add(s.curr);
                s.clk++;
            }

        }
        s.print();
        s.cal_Wait_Turn(p);
    }

    public static void main(String[] args) {

        SRT s=new SRT();
        s.run();

    }
}



