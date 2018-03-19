import java.io.*;
import java.net.*;
import java.util.*;
public class Node {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enter the ID of the Node(1-8) & Delay value");
		Scanner in= new Scanner(System.in);
		NodeOperations n=new NodeOperations(in.nextInt(),in.nextInt());
		try {
		n.s1=new ServerSocket(5000); //starting socket at port 2000. common for all.
		n.listen();
		System.out.println("waiting...");
		while(true) //wait until we receive the start message from n+1th process. trying to get all of them to start together.
		{
			if(n.ready==true)
				break;
			Thread.sleep(20);
		}
		n.connect();
		while(true) // loop until each client enters critical section 5 times
		{
			Random rand = new Random();
			int x = rand.nextInt(100)+10;
			Thread.sleep(x);
			if(n.count>=30)
			   break;
			int  w = rand.nextInt(3)+1;  //Random number for selecting File1/file2/file3
			if(!n.req)
			{
				if(w==1)
					n.sendRequest1();
				else if(w==2)
					n.sendRequest2();
				else if(w==3)
					n.sendRequest3();
			}
		}
		System.out.println("****DONE!****"); // end of message passing
		//n.s1.close();	
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
