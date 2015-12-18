package webserver;

import java.util.LinkedList;

public class ServerThreadPool {
	
	
	LinkedList<Runnable> m_Queue;
	private int m_MaxThreads;
	
	public ServerThreadPool() {
		m_Queue = new LinkedList<>();
		m_MaxThreads = 0;
	}
	
	public synchronized void addTask(Runnable i_runnable){
		if (m_MaxThreads < ConfigurationObject.getMaxThreads()) {
			m_Queue.add(i_runnable);
			m_MaxThreads++;
		}
	}
	
	public synchronized void removeTask(Runnable i_runnable)
	{
		if (m_MaxThreads > 0 && !m_Queue.isEmpty()) {
			m_Queue.remove(i_runnable);
			m_MaxThreads--;
		}
	}
	
}
