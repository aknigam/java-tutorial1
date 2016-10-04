package threads;

public class RegisterTest {

	private boolean done = false;

	public void run() {
		while (!done) {
			foo(); 
		}
		System.out.println("Finished");
	}

	public void setDone() {
		System.out.println("Done !!!!!!!!!!!!!!!!!!!!!!!!! ");
		done = true; 
	}

	private void foo() {
		System.out.println("Fooing...");

	}
	
	void recursivemethod(){
		try{
			// String[] a = new String[100000];
			recursivemethod();
		}catch(StackOverflowError e){
			e.printStackTrace();
			
			System.out.println(e.getStackTrace().length);
			
			
		}
	}
	
	public static void main(String[] args) {
		RegisterTest rt = new RegisterTest();
		rt.recursivemethod();
	}
	
	public static void run(String[] args) throws InterruptedException {
		
		RegisterTest rt = new RegisterTest();
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				rt.run();
			}
		});
		t.start();
		Thread.sleep(20l);
		rt.setDone();
	}
}
