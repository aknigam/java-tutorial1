package concurrent.executors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
 
public class DemoExecutor
{
    public static void main(String[] args) throws InterruptedException
    {
        Integer threadCounter = 0;
        BlockingQueue<Runnable> blockingQueue = new CustomQueue<Runnable>(10);
 
        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(5,
                                            7, 5000, TimeUnit.MILLISECONDS, blockingQueue);
 
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r,
                    ThreadPoolExecutor executor) {
                System.out.println("DemoTask Rejected : "
                        + ((DemoThread) r).getName());
                System.out.println("Waiting for a second !!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Lets add another time : "
                        + ((DemoThread) r).getName());
                executor.execute(r);
            }
        });
        // Let start all core threads initially
        executor.prestartAllCoreThreads();
        while (true) {
            threadCounter++;
            // Adding threads one by one
            System.out.println("Adding DemoTask : " + threadCounter);
            executor.execute(new DemoThread(threadCounter.toString()));
            
//            blockingQueue.offer(new DemoThread(threadCounter.toString()));
//            Thread.sleep(10);
            if (threadCounter == 10)
                break;
        }
        System.out.println("!!!!!!!!!!!!!! Finished adding tasks");
        executor.awaitTermination(2000, TimeUnit.MILLISECONDS);
        executor.shutdown();
        System.exit(1);
    }
    
    
 
}