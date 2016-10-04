package concurrent.executors;


 
public class DemoThread implements Runnable
{
    private String name = null;
 
    public DemoThread(String name) {
        this.name = name;
    }
 
    public String getName() {
        return this.name;
    }
 
    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Executing : " + name);
    }
    
    @Override
    public String toString() {
    	return "["+name+"]-"+super.toString();
    }
}