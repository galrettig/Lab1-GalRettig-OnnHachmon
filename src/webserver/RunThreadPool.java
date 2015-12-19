package webserver;

public class RunThreadPool implements Runnable {
	
	    private ServerThreadPool tasks;

	    public RunThreadPool(ServerThreadPool tasks) {
	        this.tasks = tasks;
	    }

	    @Override
	    public void run() {
	        while (true) {
	            Runnable task = null;
	            try {
	                //task = tasks.removeTask();
	            	
	            } catch (Exception e) {
	            	e.printStackTrace();
	                // Shutdown thread
	                break;
	            }

	            // Run
	            if (task != null) {
	                task.run();
	            }
	        }
	    }

}
