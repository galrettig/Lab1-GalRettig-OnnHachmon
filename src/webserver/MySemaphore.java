package webserver;

import java.util.concurrent.Semaphore;

public class MySemaphore {

	Semaphore m_Semaphore;
	
	public MySemaphore() {
		m_Semaphore = new Semaphore(ConfigurationObject.getMaxThreads());
	}
	
	// Increment numbers of Threads
	public void doRelease() {
		if (m_Semaphore != null) {
			m_Semaphore.release();
		}
	}
	// Decrement numbers of Threads
	public void doAquire() throws InterruptedException {
		if (m_Semaphore != null) {
			m_Semaphore.acquire();
		}
	}
	
	
}
