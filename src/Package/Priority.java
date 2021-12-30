package Package;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Priority
{

    int burstTime[];
    int priority[];
    int arrivalTime[];
    String[] processId;
    int numberOfProcess;
    //  static int contextSwitch=0;
    Process p = null;
    List<Process> processes = new ArrayList<>();

    public void getProcessInfo(Scanner input)
    {
        System.out.print("Enter the number of Process for Scheduling: ");
        int inputNumberOfProcess = input.nextInt();
        numberOfProcess = inputNumberOfProcess;
        //  System.out.print("Enter the context switch: ");
        // int inputContextSwitch = input.nextInt();
        // contextSwitch = inputContextSwitch;
        burstTime = new int[numberOfProcess];
        priority = new int[numberOfProcess];
        arrivalTime = new int[numberOfProcess];
        processId = new String[numberOfProcess];
        String st = "P";
        for (int i = 0; i < numberOfProcess; i++)
        {
            processId[i] = st.concat(Integer.toString(i+1));
            System.out.print("Enter the burst time of Process - " + (i+1) + " : ");
            burstTime[i] = input.nextInt();
            System.out.print("Enter the arrival time of Process - " + (i+1) + " : ");
            arrivalTime[i] = input.nextInt();
            System.out.print("Enter the priority of Process - " + (i+1) + " : ");
            priority[i] = input.nextInt();
            p = new Process(processId[i],arrivalTime[i],burstTime[i],priority[i]);
            processes.add(p);
        }
    }

    public void sort(int[] atime, int[] btime, int[] pr, String[] pname)
    {

        int temp;
        String stemp;
        for (int i = 0; i < numberOfProcess; i++)
        {

            for (int j = 0; j < numberOfProcess - i - 1; j++)
            {
                if (atime[j] > atime[j + 1])
                {
                    //swapping arrival time
                    temp = atime[j];
                    atime[j] = atime[j + 1];
                    atime[j + 1] = temp;

                    //swapping burst time
                    temp = btime[j];
                    btime[j] = btime[j + 1];
                    btime[j + 1] = temp;

                    //swapping priority
                    temp = pr[j];
                    pr[j] = pr[j + 1];
                    pr[j + 1] = temp;

                    //swapping process identity
                    stemp = pname[j];
                    pname[j] = pname[j + 1];
                    pname[j + 1] = stemp;

                }
                //sorting according to priority when arrival times are same
                if (atime[j] == atime[j + 1])
                {
                    if (pr[j] > pr[j + 1])
                    {
                        //swapping arrival time
                        temp = atime[j];
                        atime[j] = atime[j + 1];
                        atime[j + 1] = temp;

                        //swapping burst time
                        temp = btime[j];
                        btime[j] = btime[j + 1];
                        btime[j + 1] = temp;

                        //swapping priority
                        temp = pr[j];
                        pr[j] = pr[j + 1];
                        pr[j + 1] = temp;

                        //swapping process name
                        stemp = pname[j];
                        pname[j] = pname[j + 1];
                        pname[j + 1] = stemp;

                    }
                }
            }

        }
    }

    public void priorityAlgorithm()
    {
        int endTime[] = new int[numberOfProcess];
        int bt[] = burstTime.clone();
        int at[] = arrivalTime.clone();
        int prt[] = priority.clone();
        String pid[] = processId.clone();
        int waitingTime[] = new int[numberOfProcess];
        int turnAroundTime[] = new int[numberOfProcess];
        Process x = null;
        List<Process> execute = new ArrayList<>();
        sort(at, bt, prt, pid);
        //calculating waiting & turn-around time for each process
        endTime[0] = at[0] + bt[0] ;
        turnAroundTime[0] = endTime[0] - at[0];
        waitingTime[0] = turnAroundTime[0] - bt[0];

        for (int i = 1; i < numberOfProcess; i++)
        {
            endTime[i] = bt[i] + endTime[i - 1];
            turnAroundTime[i] = endTime[i] - at[i];
            waitingTime[i] = turnAroundTime[i] - bt[i] ;
        }
        float sum = 0;
        for (int n : waitingTime)
        {
            sum += n;
        }
        float averageWaitingTime = sum / numberOfProcess;

        sum = 0;
        for (int n : turnAroundTime)
        {
            sum += n;
        }
        float averageTurnAroundTime = sum / numberOfProcess;
        for (int i = 0; i < numberOfProcess; i++)
        {
            x = new Process(pid[i], at[i], bt[i], prt[i]);
            x.setEndTime(endTime[i]);
            x.setWaitingTime(waitingTime[i]);
            x.setTurnAroundTime(turnAroundTime[i]);
            execute.add(x);
        }

        //print the order of processes according to their turn around time

        System.out.format("%20s%20s%20s%20s%20s%20s%20s\n", "ProcessId", "BurstTime", "ArrivalTime", "Priority", "EndTime", "WaitingTime", "TurnAroundTime");
        for (int i = 0; i < numberOfProcess; i++) {
            System.out.format("%20s%20d%20d%20d%20d%20d%20d\n", execute.get(i).getName(),execute.get(i).getBurstTime() , execute.get(i).getArrivalTime(), execute.get(i).getPriority(), execute.get(i).getEndTime(), execute.get(i).getWaitingTime(), execute.get(i).getTurnAroundTime());
        }

        System.out.format("%100s%20f%20f\n", "Average", averageWaitingTime, averageTurnAroundTime);
    }

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        Priority obj = new Priority();
        obj.getProcessInfo(input);
        obj.priorityAlgorithm();
    }
}
