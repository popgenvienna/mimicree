package mimcore.data.misc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MimicreeThreadPool {

	private MimicreeThreadPool(){}

	
	private static int threadCount=1;
	private static ExecutorService executor=Executors.newFixedThreadPool(1);
	public static int getThreads()
	{
		return threadCount;	
	}
	
	
	public static ExecutorService getExector()
	{
		return executor;
	}
	
	public static void setThreads(int threads)
	{
		threadCount=threads;
		executor=Executors.newFixedThreadPool(threads);
	}
	
}
