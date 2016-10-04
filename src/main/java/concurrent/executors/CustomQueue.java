package concurrent.executors;

import java.util.concurrent.ArrayBlockingQueue;

public class CustomQueue<Runnable> extends ArrayBlockingQueue<Runnable> {

	public CustomQueue(int capacity) {
		super(capacity);
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean offer(Runnable e) {
		System.out.println("Adding to queue: "+ e);
		boolean result = super.offer(e);
		System.out.println("Result of adding "+e+" to queue: "+ result);
		return result;
	}

}
