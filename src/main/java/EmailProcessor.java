import java.util.*;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by a.nigam on 23/03/17.
 */
public class EmailProcessor {


    static class Range{
        int start;
        int end;

        public Range(int s, int e){
            this.start = s;
            this.end = e;
        }
        @Override
        public String toString() {
            return start +" : "+ end;
        }
    }

    static class ResultCollector{

        int parties;

        public ResultCollector(int i){
            parties = i;
        }

    }

    static class ZooKeeper{

        private final int shards;
        private int noOfActiveNodes;
        int counter;
        Map<Integer, Boolean> clusterStatus = new LinkedHashMap<>();

        public ZooKeeper(int shards){
            this.shards =  shards;

        }

        public synchronized int register() {
            int id = counter++;
            clusterStatus.put(id, true);
            noOfActiveNodes++;
            return id;
        }

        public synchronized  void nodeDied(int id){
            noOfActiveNodes--;
            clusterStatus.remove(id);

        }

        public synchronized  Range getShardRange(int id) {

            int index =0;
            int noOfShardsPerNode = shards/noOfActiveNodes;
            for (Map.Entry<Integer, Boolean> entry : clusterStatus.entrySet() ) {
                index++;
                if(entry.getKey() == id){
                    break;
                }

            }
//            System.out.println("I: "+ index+"\t"+ noOfActiveNodes);
            if(index == noOfActiveNodes)
                return new Range((index-1)*noOfShardsPerNode , shards);
            else
                return new Range((index-1)*noOfShardsPerNode , (index)*noOfShardsPerNode);

        }


        Map<Integer, List<Result>> results = new HashMap<>();

        public synchronized void publishResult(int itr , int iden, String id, String result){

            if(clusterStatus.get(iden) == null){
                return;
            }
            if( results.get(itr) == null){
                results.put(itr, new ArrayList<>());
            }

            List<Result> l = results.get(itr);

            l.add(new Result(id, result));

//            EmailProcessor.print(itr+"\t"+l.toString());
            if(l.size() == noOfActiveNodes){
                EmailProcessor.print(itr+"\t"+l.toString());
            }

        }

        class Result{

            String val;
            public Result(String id, String result) {
                val= id +"\t"+ result+"\t\t";
            }

            @Override
            public String toString() {
                return val;
            }
        }



    }
    static class Node extends Thread{

        private final ZooKeeper zookeeper;
        private int id;
        private volatile boolean active = true;

        public Node(ZooKeeper zooKeeper, String name){
            super(name);
            this.zookeeper = zooKeeper;
            begin();
        }

        public void begin() {
            active = true;
            this.id = zookeeper.register();
        }


        @Override
        public void run() {
            init();
            int i =0;
            while(true){

                ++i;
                if(!this.active){
                    pause(100);
//                    EmailProcessor.print("Inactive ");
                    continue;
                }
                Range r =  zookeeper.getShardRange(id);

                EmailProcessor.print(r.toString());
//                zookeeper.publishResult(i, id, getName(), r.toString());
                pause(100);

                if( i > 20){
                    break;
                }
//                System.out.println(getName());
            }

            kill();
        }


        public void kill(){
            this.active = false;
            zookeeper.nodeDied(id);
            print("Node "+getName()+" killed");

        }

        private void init() {

        }
    }

    public static void print(String r) {
        System.out.println(System.currentTimeMillis()+"\t"+Thread.currentThread().getName()+":\t"+ r);
    }

    public static void main(String[] args) throws InterruptedException {




        ZooKeeper zooKeeper = new ZooKeeper(15);

        Node m1 = new Node(zooKeeper, "m1");
        Node m2 = new Node(zooKeeper, "m2");
        Node m3 = new Node(zooKeeper, "m3");

        m1.start();
        m2.start();
        m3.start();

        pause(150);

        m1.kill();
        print("Node 1 died");

        pause(450);

        m1.begin();
        print("Node 1 back");

        Node m4 = new Node(zooKeeper, "m4");

        pause(950);
        m4.start();
        print("Node 4 added");

        Node m5 = new Node(zooKeeper, "m5");

        pause(250);
        m5.start();
        print("Node 5 added");


        /*
        int totalNoOfActiveNodes = 10;
        int orderOfCurrentNode = 6;

        int shards =  50;

        int noOfShardsPerNode = shards/totalNoOfActiveNodes;

        for (int i = 0; i < totalNoOfActiveNodes; i++) {
            Range r = new Range(i*noOfShardsPerNode , (i +1)*noOfShardsPerNode);
            System.out.println("Node no."+i +"\t"+r);
        }
        noOfShardsPerNode = shards/(totalNoOfActiveNodes-2);
        System.out.println(noOfShardsPerNode);
        for (int i = 0; i < totalNoOfActiveNodes-1; i++) {
            Range r = new Range(i*noOfShardsPerNode , (i +1)*noOfShardsPerNode);
            System.out.println("Node no."+i +"\t"+r);
        }
        */

        m4.join();


    }

    public static void pause(long i) {
        try {
            Thread.sleep(i );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
