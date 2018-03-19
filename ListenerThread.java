import java.io.*;
import java.util.*;
import java.net.*;
@SuppressWarnings("unused")
/*this Thread is used to create a number of threads for each connection
 * used for the concurrent server model.
 */
public class ListenerThread extends Thread {
	private ServerSocket s1;
	private Boolean flag; 
	private NodeOperations n;
public ListenerThread(ServerSocket s,NodeOperations n1)
{
	s1=s;
	flag=true;
	n=n1;
	//System.out.println("listen thread of process : "+n.id);
}
public void run()
{
	while(flag)
	{
		Socket socket=null;
			try {
			    socket = n.s1.accept();
			new ListenThread(socket,n).start(); //start thread to listen for each client
	            }
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally{
				try{
				//socket.close();
			}catch(Exception e){}
			}
	}
}

public void stopRunning()
{
	flag=false;
}

}
