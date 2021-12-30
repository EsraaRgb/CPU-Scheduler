package Package;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Process> processes=new ArrayList<Process>();
        processes.add(new Process("p1", 0, 17,4,4));
        processes.add(new Process("p2", 3, 6,9,3));
        processes.add(new Process("p3", 4, 10,3,5));
        processes.add(new Process("p4", 29, 4,8,2));

        AGAT agat = new AGAT(processes);

//        SRTF.allProcesses = new ArrayList<Process>();
//        SRTF sjf = new SRTF();
//        int numOfPros;
//        int arrivalTime;
//        int burstTime;
//        String name;
//        int context;
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter the number of processes:   ");
//        numOfPros = scan.nextInt();
//
//        for (int i=0; i<numOfPros; ++i) {
//            System.out.println("Enter the process name, arrival time and burst time:  ");
//            name = scan.next();
//            arrivalTime = scan.nextInt();
//            burstTime = scan.nextInt();
//
//
//            SRTF.allProcesses.add(new Process(name, arrivalTime, burstTime));
//
//        }
//        System.out.println("Enter context switching: ");
//        context = scan.nextInt();
//        sjf.setContext(context);
//        SRTF.SRTFScheduling();
//        scan.close();

    }

}

