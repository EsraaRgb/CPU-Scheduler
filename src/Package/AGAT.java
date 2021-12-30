package Package;

import java.util.*;

public class AGAT {
    static int contextSwitch;
    public  int currentTime=0;
    public double V1,V2;
    public double averageWaitingTime = 0;
    public double averageTurnAroundTime = 0;
    public Process runningProcess ;
    public ArrayList<Process> processes = new ArrayList<>();
    public ArrayList<Process> readyQueue= new ArrayList<>();
    public ArrayList<Process> deadList = new ArrayList<>();
    public AGAT(ArrayList<Process> p) {

        for (int i=0;i<p.size();i++){
            Process process=new Process(p.get(i).name, p.get(i).arrivalTime,p.get(i).burstTime,p.get(i).priority,p.get(i).quantum);
            processes.add(process);
        }
        this.processes.sort(new Process.ArrivalTimeComparator());
        readyQueue.add(processes.get(0));
        calculateV1();
        calculateAGATFactor();
        processAGAT();
    }

    public void calculateV1(){
        if(processes!=null){
            if (processes.get((processes.size()-1)).arrivalTime>10){
                V1=(processes.get((processes.size()-1)).arrivalTime)/10.0;
            }
            else V1=1;
            System.out.println("V1 = "+V1);
        }
    }
    public void calculateV2 (){
        if (processes!=null){
            ArrayList<Process> p =new ArrayList<Process>();
            for (int i=0;i<processes.size();i++){
                Process process=new Process(processes.get(i).name, processes.get(i).arrivalTime,processes.get(i).burstTime,processes.get(i).priority,processes.get(i).quantum);
                p.add(process);
            }
            p.sort(new Process.RemainingBurstComparator());
            if (p.get((p.size()-1)).remainingBurstTime>10){
                V2=(p.get((p.size()-1)).remainingBurstTime)/10.0;
            }
            else V2=1;
            System.out.println("V2 = "+V2);
        }
    }
    public void calculateAGATFactor (){
        calculateV2();
        for (Process p : processes){
            p.AGAT_Factor=(int)((10-p.priority)+Math.ceil(p.arrivalTime/V1)+Math.ceil(p.remainingBurstTime/V2));
        }
    }
    public int checkArrival(){
        int nextArrival=0;
        if (processes!=null){
            for (int i =0;i<processes.size();i++){
                if (processes.get(i).arrivalTime <= currentTime ){
                    for (int j=0;j<readyQueue.size();j++){
                        if (!Objects.equals(processes.get(i).name, readyQueue.get(j).name)){
                            readyQueue.add(processes.get(i));
                        }
                    }
                }
            }
        }
        readyQueue.sort(new Process.ArrivalTimeComparator());
        if (readyQueue!=null){
            for (Process p : readyQueue){
                if (p.arrivalTime>nextArrival){
                    nextArrival=p.arrivalTime;
                }
            }
            for (Process process : processes) {
                if (process.arrivalTime > nextArrival) {
                    nextArrival = process.arrivalTime;
                    break;
                }
            }
        }

        readyQueue.sort(new Process.AGATFactorComparator());
        System.out.println("nextArrival = " +nextArrival);

        return nextArrival;
    }

    public boolean checkNext(){
        for (Process p : readyQueue){
            if(p.AGAT_Factor<runningProcess.AGAT_Factor){
                return true;
            }
        }
        return false;
    }
    public void removeProcess(){
        if (runningProcess!=null){
            if (runningProcess.remainingQuantum==0 && runningProcess.remainingBurstTime!=0){
                runningProcess.quantum+=2;
                runningProcess.remainingQuantum=runningProcess.quantum;
            }
            if (runningProcess.remainingQuantum!=0 && runningProcess.remainingBurstTime!=0){
                runningProcess.quantum+=runningProcess.remainingQuantum;
                runningProcess.remainingQuantum=runningProcess.quantum;
            }
            if (runningProcess.remainingBurstTime!=0){
                runningProcess.quantum=0;
                runningProcess.remainingQuantum=0;
                deadList.add(runningProcess);
            }

        }
    }

    public void processAGAT(){
        // update ready queue with last arrived processes.
        int start,executionTime;
        while (deadList.size()!=processes.size()){
            start=currentTime;
            checkArrival();
            runningProcess=readyQueue.get(0);
            executionTime=(int)Math.round(.4*runningProcess.quantum);
            System.out.println(executionTime);
            currentTime+=executionTime;
            runningProcess.remainingQuantum-=executionTime;
            runningProcess.remainingBurstTime-=executionTime;
            int nextArrival=checkArrival();
            if(readyQueue.size()>1){
                if(checkNext()){
                    runningProcess.remainingQuantum-=executionTime;
                }
                else{
                    executionTime=runningProcess.quantum-runningProcess.remainingQuantum;
                    runningProcess.remainingQuantum=0;

                }
            }
            if (readyQueue.size()==1){
                executionTime=nextArrival-currentTime;
                runningProcess.remainingQuantum-=executionTime;
            }
            runningProcess.remainingBurstTime-=executionTime;
            System.out.println("execution time "+ executionTime);
            currentTime+=executionTime;//+contextSwitch;
            removeProcess();
            System.out.println(runningProcess.name+"\t from "+start + " to "+currentTime);
        }

    }
}
