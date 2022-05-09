package os_ass3;

import java.util.*;
import java.lang.Math;
public class AGAT {
    int numOfProcesses;
    public static Vector<Process> Processes = new Vector<Process>();
    public static Vector<Process> deadList = new Vector<Process>();
    public static Vector<Integer> timeOrder = new Vector<Integer>();
    public static Vector<String> processOrder = new Vector<String>();
    public static Vector<Process> Queue = new Vector<Process>();
    public static double V2;
    public static int time = 0 ;
    public static Vector<Process> myProcesses = new Vector<Process>();


    public void getdata(Scanner input)
    {

        String data;
        System.out.print("Enter the number of processes: ");
        numOfProcesses = input.nextInt();
        input.nextLine();
        System.out.println("processes' data (Quantum, processName, brustTime, ArrivalTime,Priority) in order separated with spaces : ");
        for (int i = 0; i < numOfProcesses; i++) {
            data = input.nextLine();

            String[] splitStr = data.split("\\s+");
            int quantam = Integer.parseInt(splitStr[0]);
            String name = splitStr[1];
            int btime =  Integer.parseInt(splitStr[2]);
            int Atime =  Integer.parseInt(splitStr[3]);
            int priority = Integer.parseInt(splitStr[4]);
            Process newProcess = new Process(quantam, name, btime, Atime ,priority);
            myProcesses.add(newProcess);

    }}
    AGAT()
    {

    }
    public void AGATT(Vector<Process> processData) {
        this.numOfProcesses = processData.size();
        for (int i = 0; i < processData.size(); i++) {
            Processes.add(processData.get(i));
        }

    }

    public int Maxi() {
        int maximum = 0;
        for (int i = 0; i < Processes.size(); i++) {
            maximum = maximum + Processes.get(i).burstTime;
        }
        return maximum;
    }

    public static boolean readyQueue() {
        boolean arrived = false;
        for (int i = 0; i < Processes.size(); i++) {
            if (Processes.get(i).ArrivalTime <= time && !Processes.get(i).Done() && !Queue.contains(Processes.get(i))) {
                Queue.add(Processes.get(i));
                arrived = true;
            }
        }
        return arrived;
    }

    public static double calculateV1(Vector<Process> processes)
    {
        double V1;
        float maxi =  processes.get(0).ArrivalTime;

        for (int i = 1; i <  processes.size(); i++) {
            if ( processes.get(i).ArrivalTime>maxi)
            {
                maxi =  processes.get(i).ArrivalTime;
            }
        }
        if (maxi >10)
        {
            V1 = maxi/10;
        }
        else V1=1;

        return V1;

    }

    public static double calculateV2(Vector<Process> process)
    {
        int Maxi = process.get(0).remainingTime;

        for (int i = 0; i < process.size(); i++) {
            if (process.get(i).remainingTime > Maxi) {
                Maxi = process.get(i).remainingTime;
            }
        }
        if (Maxi> 10) {
            V2 = Maxi / 10.0;
        } else
            V2 = 1.0;
        return V2;

    }

    public void calculateAgat(Vector<Process> process)
    {
        int agat=0;
        for (int i=0 ; i <process.size();i++) {
            agat =(10 - process.get(i).getPriority())
                    + (int) Math.ceil(process.get(i).ArrivalTime / calculateV1(myProcesses))
                    + (int) Math.ceil(process.get(i).remainingTime / calculateV2(process));
            process.get(i).AgatFactor=agat;
            process.get(i).updateAGATS(agat);
        }
    }
    public Process smallestAgatProcess(Process process)
    {
        int smallest= Queue.get(0).AgatFactor;

        for (int i=0 ; i <Queue.size();i++) {
            if (Queue.get(i).AgatFactor<smallest)
            {
                smallest=Queue.get(i).AgatFactor;

            } }

        for (int i = 0; i < Queue.size(); i++) {
            if (Queue.get(i).AgatFactor == smallest && Queue.get(i) != process)
                return  Queue.get(i);
        }
        return process;
    }


    public static void moveForward(Process process) {

        for (int i = 0; i < Queue.size(); i++) {
            if (process == Queue.get(i)) {
                for (int j = i; j > 0; j--) {
                    Queue.set(j, Queue.get(j - 1));
                }
                Queue.set(0, process);
                break;
            }
        }
    }

    public static void moveBack(Process process) {
        for (int i = 0; i < Queue.size(); i++) {
            if (process == Queue.get(i)) {
                for (int j = i; j < Queue.size() - 1; j++) {
                    Process process1 = Queue.get(j + 1);
                    Queue.set(j, process1);
                }
                Queue.set(Queue.size() - 1, process);
                break;
            }
        }
    }
    public void printOrder() {
        for (int i = 0; i < processOrder.size(); i++) {
            System.out.println(processOrder.get(i) + " : " + timeOrder.get(i));
        }
    }


    public void printInfo()
    {
        printOrder();
        double sum1=0;
        double sum2=0;
        for (int i = 0; i < myProcesses.size(); i++) {
            Process process = myProcesses.get(i);
            System.out.println("-------"+process.Name + "-------");
            System.out.println("**Quantum history**");
            for (int j=0 ; j< process.quantamHistory.size();j++)
            {
                System.out.println(process.quantamHistory.get(j));
            }
            System.out.println("**AGATs history**");
            for (int j=0 ; j< process.agatHistory.size();j++)
            {
                System.out.println(process.agatHistory.get(j));
            }
            System.out.println( );
            System.out.println("**waiting time**");
            process.calculateTurnAroundTime();
            process.calculateWaitingTime();
            System.out.println(process.waitingTime);
            System.out.println("**turnarround time**");
            System.out.println(process.turnaroundTime);
            System.out.println( );
        }
        for(Process p:myProcesses)
        {
            sum1 += p.waitingTime;
            sum2 += p.turnaroundTime;
        }
        double avgWaiting=sum1/(double)myProcesses.size();
        double avgTurn=sum2/(double)myProcesses.size();
        System.out.println("*****Average waiting time*******");
        System.out.println(avgWaiting);
        System.out.println("***** Average turnarround time*******");
        System.out.println(avgTurn);
    }


    public void run(Scanner input) {
        getdata(input);
        AGATT(myProcesses);
        boolean roundDone=false;
        int reminder=0;
        readyQueue();
        Process currprocess= Queue.get(0);

        while(time<Maxi()) {
            readyQueue();

            if (currprocess.remainingTime > 0) {

                if(!roundDone)
                {
                    currprocess= Queue.get(0);
                    reminder= currprocess.Quantum;
                    currprocess.updateQuantam(currprocess.Quantum);
                    currprocess.updateQuantam(currprocess.Quantum);
                    long premitiveTime= Math.round(0.40*currprocess.Quantum);
                    for (int i=0 ; i<premitiveTime;i++) {
                        if (processOrder.size() <= 0) {
                            processOrder.add(currprocess.Name);
                            timeOrder.add(time);

                        } else {
                            if (!processOrder.get(processOrder.size() - 1).equals(currprocess.Name)) {
                                processOrder.add(currprocess.Name);
                                timeOrder.add(time);
                            }

                        }
                        time++;

                        currprocess.editBrustTime(currprocess.remainingTime - 1);

                        reminder--;
                        if (currprocess.remainingTime == 0)
                            break;

                    }

                    if (readyQueue()) {
                        calculateAgat(Queue); }
                    roundDone = true;
                }
                else    //if round time is done -nonpremitive mood-
                {
                    if (currprocess==smallestAgatProcess(currprocess)) {
                        if (reminder > 0) //first option
                        {
                            if (processOrder.size() <= 0) {
                                processOrder.add(currprocess.Name);
                                timeOrder.add(time);

                            } else {
                                if (!processOrder.get(processOrder.size() - 1).equals(currprocess.Name)) {
                                    processOrder.add(currprocess.Name);
                                    timeOrder.add(time);
                                }

                            }
                            time++;
                            currprocess.editBrustTime(currprocess.remainingTime - 1);
                            reminder--;
                            if (readyQueue())
                            {
                                calculateAgat(Queue);
                            }
                            if (currprocess != smallestAgatProcess(currprocess)) {
                                currprocess.Quantum = (currprocess.Quantum + reminder);
                                currprocess.updateQuantam(currprocess.Quantum + reminder);
                                moveBack(currprocess);
                                moveForward(smallestAgatProcess(currprocess));
                                roundDone = false;
                            }
                        }


                        if (reminder == 0) {
                            currprocess.Quantum = (currprocess.Quantum + 2);
                            currprocess.updateQuantam(currprocess.Quantum + 2);
                            moveBack(currprocess);
                            roundDone = false;
                        }
                    }


                    else { //NOTSMALLESTAGAT
                        currprocess.Quantum=(currprocess.Quantum + reminder);
                        currprocess.updateQuantam(currprocess.Quantum + reminder);
                        moveBack(currprocess);
                        moveForward(smallestAgatProcess(currprocess));
                        roundDone = false;
                    }

                }

            }

            else {  //remainingtime==0
                currprocess.Quantum=0;
                currprocess.updateQuantam(0);
                currprocess.finishingTime=time;
                deadList.add(currprocess);
                Queue.remove(currprocess);

                currprocess = Queue.get(0);
                roundDone = false;
            }

        }
        currprocess.finishingTime=time;
        processOrder.add(processOrder.get(processOrder.size() - 1));
        timeOrder.add(time);
     printInfo();
    }
}