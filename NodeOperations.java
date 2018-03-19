import java.io.*;
import java.net.*;
import java.util.*;
//class which stores server and client variables. as well as methods
public class NodeOperations 
{
public int count;	
public boolean ready;
public String[] queue1=new String[5];
public String[] queue2=new String[5];
public String[] queue3=new String[5];
public String hosts[]={"dc01.utdallas.edu","dc02.utdallas.edu","dc03.utdallas.edu","dc04.utdallas.edu","dc05.utdallas.edu","dc06.utdallas.edu","dc07.utdallas.edu","dc08.utdallas.edu"};
//public String hosts[]={"127.0.0.1","127.0.0.1","127.0.0.1","127.0.0.1","127.0.0.1","127.0.0.1","127.0.0.1","127.0.0.1"};
public long timeStamp=0;
public int delay;
public boolean req;
public ListenerThread lt;
public ServerSocket s1=null;
public int id;
public Socket clientSockets[]=new Socket[5];
public Socket serverSockets[]=new Socket[3];
public DataOutputStream outC[]=new DataOutputStream[5];
public DataInputStream inClients[] = new DataInputStream[5];
public DataOutputStream outS[]=new DataOutputStream[3];
public DataInputStream inServers[] = new DataInputStream[3];
public NodeOperations(int id,int d) // initialize 
{
	ready=false;
	//ready=true;
	this.delay=d;
	this.id=id;
	req=false;
	count=0;
	for(int i=0;i<clientSockets.length;i++){
		queue1[i]=null;
	    queue2[i]=null;
	    queue3[i]=null;
	}
}

public NodeOperations(){

}


public void listen() // multithreaded approach.
{
	lt = new ListenerThread(s1,this);
	lt.start();

}
public void connect() // connect clients to server
{
	
	for(int i=0;i<this.clientSockets.length;i++)
	{
	  try 
	  {
		clientSockets[i]=new Socket(hosts[i],5000);
	  }
	  catch (Exception e) 
	  {
		
		e.printStackTrace();
	  }
	}
	for(int i=0;i<this.serverSockets.length;i++)
	{
	  try 
	  {
		serverSockets[i]=new Socket(hosts[i+5],5000);
	  }
	  catch (Exception e) 
	  {
		
		e.printStackTrace();
	  }
	}
	for(int i=0;i<clientSockets.length;i++)
	{
		try {
			outC[i]=new DataOutputStream(clientSockets[i].getOutputStream());
			inClients[i]=new DataInputStream(clientSockets[i].getInputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	for(int i=0;i<serverSockets.length;i++)
	{
		try {
			outS[i]=new DataOutputStream(serverSockets[i].getOutputStream());
			inServers[i]=new DataInputStream(serverSockets[i].getInputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}

public void sendStart() //n+1th node sends start message
{
	try
	{
	for(int i=0;i<clientSockets.length;i++)
	{
			outC[i].writeUTF("START");
			
	}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

public void sendRequest1() // for file1
{
	System.out.println("Request for file 1");
	try
	{
	req=true;	
	timeStamp=timeStamp+delay;
	String message = "REQUEST1 "+id+" "+timeStamp;
	for(int i=0;i<clientSockets.length;i++)
	{
        outC[i].writeUTF(message);
        
        
	}
	message=null;
	for(int i=0;i<clientSockets.length;i++)
	{
        message=inClients[i].readUTF();
        timeStamp=timeStamp+delay;
		String str[] = message.split(" ");
		int x = Integer.parseInt(str[1]);
		if(timeStamp<x+delay)
		{
			timeStamp=x+delay;
		}
		//System.out.println("Reply received at "+timeStamp);
	}
	String details[]=queue1[0].split(" ");
	if(Integer.parseInt(details[0])==id && queue1[0]!=null)
		{
		criticalSection1();
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

public void criticalSection1() // code to be executed in critical section: file1
{
	try
	{
	++count;
	System.out.println("client "+id+" Entering critical section for file1 at "+timeStamp);
	
    Random rand = new Random();
    int oprno=rand.nextInt(3)+1;
    //System.out.println("read/write/enquiry"+oprno);
    if(oprno==1){  //read
    	
    	int srno=rand.nextInt(3)+1;  //read from which server
    	if(srno==1){
    		outS[0].writeUTF("READ"+" "+timeStamp+" "+"server1/file1.txt");
    		
    		
    	}
    	else if(srno==2){
    		outS[1].writeUTF("READ"+" "+timeStamp+" "+"server2/file1.txt");
    		
    		
    	}
    	else if(srno==3){
    		outS[2].writeUTF("READ"+" "+timeStamp+" "+"server3/file1.txt");
    		
    		
    	}
    } 
    else if(oprno==2){ //write
    	
    	outS[0].writeUTF("Write"+" "+timeStamp+" "+"server1/file1.txt"+" "+id);
    	
    	
    	outS[1].writeUTF("Write"+" "+timeStamp+" "+"server2/file1.txt"+" "+id);
    	
    	
    	outS[2].writeUTF("Write"+" "+timeStamp+" "+"server3/file1.txt"+" "+id);
    	
    	
    }
    else if(oprno==3){            //enquiry
    	int erno=rand.nextInt(3)+1;  //enquire which server
    	if(erno==1){
    		outS[0].writeUTF("ENQUIRY"+" "+timeStamp+" "+"server1");
    		
    	    
    	}
    	else if(erno==2){
    		outS[1].writeUTF("ENQUIRY"+" "+timeStamp+" "+"server2");
    		
    	    
    	}
    	else if(erno==3){
    		outS[2].writeUTF("ENQUIRY"+" "+timeStamp+" "+"server3");
    		
    		
    	}
    }

	Thread.sleep(20);
	timeStamp=timeStamp+delay;
	String message = "RELEASE1 "+timeStamp;
	for(int i=0;i<clientSockets.length;i++)
	{
			//outs[i].reset();
			outC[i].writeUTF(message);
			
			
	}
	req=false;
	System.out.println("Client"+id+" leaving critical section1 at "+timeStamp);
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
} 

public void sendRequest2() // for file2
{
	System.out.println("Request for file 2");
	try
	{
	req=true;	
	timeStamp=timeStamp+delay;
	String message = "REQUEST2 "+id+" "+timeStamp;
	for(int i=0;i<clientSockets.length;i++)
	{
        outC[i].writeUTF(message);
        
        
	}
	message=null;
	for(int i=0;i<clientSockets.length;i++)
	{
        message=inClients[i].readUTF();
        timeStamp=timeStamp+delay;
		String str[] = message.split(" ");
		int x = Integer.parseInt(str[1]);
		if(timeStamp<x+delay)
		{
			timeStamp=x+delay;
		}
		//System.out.println("Reply at "+timeStamp);
	}
	String details[]=queue2[0].split(" ");
	if(Integer.parseInt(details[0])==id && queue2[0]!=null)
		{
		criticalSection2();
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

public void criticalSection2() // code to be executed in critical section: file2
{
	try
	{
	++count;
	System.out.println("client "+id+" Entering critical section for file2 at "+timeStamp);
	
    Random rand = new Random();
    int oprno=rand.nextInt(3)+1;
    //System.out.println("read/write/enquiry"+oprno);
    if(oprno==1){  //read
    	
    	int srno=rand.nextInt(3)+1;  //read from which server
    	if(srno==1){
    		outS[0].writeUTF("READ"+" "+timeStamp+" "+"server1/file2.txt");
    		
    	
    	}
    	else if(srno==2){
    		outS[0].writeUTF("READ"+" "+timeStamp+" "+"server2/file2.txt");
    		
    	
    	}
    	else if(srno==3){
    		outS[0].writeUTF("READ"+" "+timeStamp+" "+"server3/file2.txt");
    		
    	
    	}
    } 
    else if(oprno==2){ //write
    	
    	outS[0].writeUTF("Write"+" "+timeStamp+" "+"server1/file2.txt"+" "+id);
    	
    	
    	outS[1].writeUTF("Write"+" "+timeStamp+" "+"server2/file2.txt"+" "+id);
    	
    	outS[2].writeUTF("Write"+" "+timeStamp+" "+"server3/file2.txt"+" "+id);
    	
    }
    else if(oprno==3){            //enquiry
    	
    	int erno=rand.nextInt(3)+1;  //enquire which server
    	if(erno==1){
    		outS[0].writeUTF("ENQUIRY"+" "+timeStamp+" "+"server1");
    		
    	
    	}
    	else if(erno==2){
    		outS[0].writeUTF("ENQUIRY"+" "+timeStamp+" "+"server2");
    		
    	
    	}
    	else if(erno==3){
    		outS[0].writeUTF("ENQUIRY"+" "+timeStamp+" "+"server3");
    		
    	
    	}
    }

	Thread.sleep(20);
	timeStamp=timeStamp+delay;
	String message = "RELEASE2 "+timeStamp;
	for(int i=0;i<clientSockets.length;i++)
	{
			//outs[i].reset();
			outC[i].writeUTF(message);
			
			
	}
	req=false;
	System.out.println("Client"+id+" leaving critical section2 at "+timeStamp);
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
} 

public void sendRequest3() // for file3
{
	System.out.println("Request for file 3");
	try
	{
	req=true;	
	timeStamp=timeStamp+delay;
	String message = "REQUEST3 "+id+" "+timeStamp;
	for(int i=0;i<clientSockets.length;i++)
	{
        outC[i].writeUTF(message);
       
        
	}
	message=null;
	for(int i=0;i<clientSockets.length;i++)
	{
        message=inClients[i].readUTF();
        timeStamp=timeStamp+delay;
		String str[] = message.split(" ");
		int x = Integer.parseInt(str[1]);
		if(timeStamp<x+delay)
		{
			timeStamp=x+delay;
		}
		///System.out.println("Reply at "+timeStamp);
	}
	String details[]=queue3[0].split(" ");
	if(Integer.parseInt(details[0])==id && queue3[0]!=null)
		{
		criticalSection3();
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

public void criticalSection3() // code to be executed in critical section: file3
{
	try
	{
	++count;
	System.out.println("client "+id+" Entering critical section for file3 at "+timeStamp);
	
    Random rand = new Random();
    int oprno=rand.nextInt(3)+1;
    //System.out.println("read/write/enquiry"+oprno);
    if(oprno==1){  //read
    	
    	int srno=rand.nextInt(3)+1;  //read from which server
    	if(srno==1){
    		outS[0].writeUTF("READ"+" "+timeStamp+" "+"server1/file3.txt");
    		
    	
    	}
    	else if(srno==2){
    		outS[1].writeUTF("READ"+" "+timeStamp+" "+"server2/file3.txt");
    		
    	
    	}
    	else if(srno==3){
    		outS[2].writeUTF("READ"+" "+timeStamp+" "+"server3/file3.txt");
    		
    	
    	}
    } 
    else if(oprno==2){ //write
    	
    	outS[0].writeUTF("Write"+" "+timeStamp+" "+"server1/file3.txt"+" "+id);
    	
    	
    	outS[1].writeUTF("Write"+" "+timeStamp+" "+"server2/file3.txt"+" "+id);
    	
    	
    	outS[2].writeUTF("Write"+" "+timeStamp+" "+"server2/file3.txt"+" "+id);
    	
    }
    else if(oprno==3){            //enquiry
    	
    	int erno=rand.nextInt(3)+1;  //enquire  which server
    	if(erno==1){
    		outS[0].writeUTF("ENQUIRY"+" "+timeStamp+" "+"server1");
    		
    	
    	}
    	else if(erno==2){
    		outS[1].writeUTF("ENQUIRY"+" "+timeStamp+" "+"server2");
    		
    	
    	}
    	else if(erno==3){
    		outS[2].writeUTF("ENQUIRY"+" "+timeStamp+" "+"server3");
    		
    	
    	}
    }

	Thread.sleep(20);
	timeStamp=timeStamp+delay;
	String message = "RELEASE3 "+timeStamp;
	for(int i=0;i<clientSockets.length;i++)
	{
			//outs[i].reset();
			outC[i].writeUTF(message);
			outC[i].flush();
    	
	}
	req=false;
	System.out.println("Client"+id+" leaving critical section3 at "+timeStamp);
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
} 


}
