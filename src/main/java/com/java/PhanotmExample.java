package com.java;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

public class PhanotmExample {

	
	public static void main(String[] args) {
//		ReferenceQueue<Object> rq = new ReferenceQueue<>();
//		Reference<Object> pr = new WeakReference<Object>(new Object(), rq);
//		Reference<Object> pr = new PhantomReference<Object>(new Object(), rq);
//		Reference<Object> pr = new PhantomReference<Object>(new Object(), rq);
//		System.out.println(pr.get());
		
		ReferenceQueue<Foo> queue = new ReferenceQueue<Foo>();
		ArrayList< PhantomReference<Foo>> list=new ArrayList<PhantomReference<Foo>>();
		 
		for ( int i = 0; i < 10; i++) {
		    Foo o = new Foo( Integer.toOctalString( i));
		    list.add(new PhantomReference<Foo>(o, queue));
		}
		 
		// make sure the garbage collector does it’s magic
		System.gc();
		 
		// lets see what we’ve got
		Reference<? extends Foo> referenceFromQueue;
		for ( PhantomReference<Foo> reference : list)
		    System.out.println(reference.isEnqueued());
		 
		while ( (referenceFromQueue = queue.poll()) != null) {
		    System.out.println(referenceFromQueue.get());
		    referenceFromQueue.clear();
		}
	}
}
