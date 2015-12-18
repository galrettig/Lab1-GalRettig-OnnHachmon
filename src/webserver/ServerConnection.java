package webserver;

public class ServerConnection {
	
	private static ServerConnection m_instance = new ServerConnection();
	
	private int m_connections = 0;
	
	private MySemaphore m_Semaphore = new MySemaphore();
	
	private ServerConnection() {
		
	}
	
	public static ServerConnection getServerConnection() {
		return m_instance;
	}
	
	
	public void connect() {
		try {
			m_Semaphore.doAquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			doConnect();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			m_Semaphore.doRelease();
		}
	}
	
	public void doConnect() {
		
		
		
		synchronized (this) {
			m_connections++;
		}
		
		synchronized (this) {
			m_connections--;
		}
	}
}
