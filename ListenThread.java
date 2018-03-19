
		import java.io.*;
		import java.util.*;
		import java.net.*;
		@SuppressWarnings("unused")
		/*this class is used to listen for each connection in the server 
		 */
		public class ListenThread extends Thread {
		private NodeOperations n;
		private Socket s;
		public ListenThread(Socket s1,NodeOperations n1)
		 {
			 
			 n=n1;
			 s=s1;
		 }
		public void run()
		 {
			    int i=0;
				//System.out.println("Listening to "+n.id);
				try (
						DataInputStream in = new DataInputStream(s.getInputStream());
						DataOutputStream out = new DataOutputStream(s.getOutputStream());
					
				)
				{
					while(true)
					{
					String message=null;
					message =(String) in.readUTF();
					System.out.println("****"+message+"****");
					if(message.startsWith("START"))
					{
						n.ready=true;
					}
					
					if(message.startsWith("REQUEST1"))
					{
						n.timeStamp=n.timeStamp+n.delay;
						String str[] = message.split(" ");
						int ts=Integer.parseInt(str[2]);
						QueueOper1.insert(str[1]+" "+str[2],n);
						
						if(n.timeStamp<ts+n.delay)
						{
							n.timeStamp=ts+n.delay;
						}
						
						n.timeStamp=n.timeStamp+n.delay;//------------------------
						out.writeUTF("REPLY "+n.timeStamp);
						out.flush();
					}
					if(message.startsWith("RELEASE1"))
					{
						n.timeStamp=n.timeStamp+n.delay;
						String str[] = message.split(" ");
						int ts = Integer.parseInt(str[1]);
						if(n.timeStamp<ts+n.delay)
						{
							n.timeStamp=ts+n.delay;
						}
						QueueOper1.delete(n);
						if(n.queue1[0]!=null)
						{
						
						String data[]=n.queue1[0].split(" ");
						if(Integer.parseInt(data[0])==n.id)
							{
							n.criticalSection1();
							}
						}
					}

					if(message.startsWith("REQUEST2"))
					{
						n.timeStamp=n.timeStamp+n.delay;
						String str[] = message.split(" ");
						int ts=Integer.parseInt(str[2]);
						QueueOper2.insert(str[1]+" "+str[2],n);
						
						if(n.timeStamp<ts+n.delay)
						{
							n.timeStamp=ts+n.delay;
						}
						
						n.timeStamp=n.timeStamp+n.delay;//------------------------
						out.writeUTF("REPLY "+n.timeStamp);
						out.flush();
					}
					if(message.startsWith("RELEASE2"))
					{
						n.timeStamp=n.timeStamp+n.delay;
						String str[] = message.split(" ");
						int ts = Integer.parseInt(str[1]);
						if(n.timeStamp<ts+n.delay)
						{
							n.timeStamp=ts+n.delay;
						}
						QueueOper2.delete(n);
						if(n.queue2[0]!=null)
						{
						
						String details[]=n.queue2[0].split(" ");
						if(Integer.parseInt(details[0])==n.id)
							{
							n.criticalSection1();
							}
						}
					}

					if(message.startsWith("REQUEST3"))
					{
						n.timeStamp=n.timeStamp+n.delay;
						String str[] = message.split(" ");
						int ts=Integer.parseInt(str[2]);
						QueueOper3.insert(str[1]+" "+str[2],n);
						
						if(n.timeStamp<ts+n.delay)
						{
							n.timeStamp=ts+n.delay;
						}
						
						n.timeStamp=n.timeStamp+n.delay;
						out.writeUTF("REPLY "+n.timeStamp);
						out.flush();
					}
					if(message.startsWith("RELEASE3"))
					{
						n.timeStamp=n.timeStamp+n.delay;
						String str[] = message.split(" ");
						int ts = Integer.parseInt(str[1]);
						if(n.timeStamp<ts+n.delay)
						{
							n.timeStamp=ts+n.delay;
						}
						QueueOper3.delete(n);
						if(n.queue3[0]!=null)
						{
						
						String details[]=n.queue3[0].split(" ");
						if(Integer.parseInt(details[0])==n.id)
							{
							n.criticalSection1();
							}
						}
					}

					if(message.startsWith("READ")){
						
						String str[] =message.split(" ");
						int ts = Integer.parseInt(str[1]);
						if(n.timeStamp<ts+n.delay)
						{
							n.timeStamp=ts+n.delay;
						}
						String fileName=str[2];
						File file = new File(fileName);
						String line=null,l=null;
						
		                
		               
		                   BufferedReader fileIn = new BufferedReader(new FileReader(file));
		                   try{
		                   while ((line=fileIn.readLine() )!= null) {
		                        
		                   	l=line;
		                   	 //System.out.println(l);
		                   
		               }
		           }catch(Exception e){}
		               
		           
		                   
		                System.out.println(l);
		                //out.writeUTF(line);
		               }
		               
					
                   
					if(message.startsWith("Write")){
						

						String str[] =message.split(" ");
						int ts = Integer.parseInt(str[1]);
						int idClient=Integer.parseInt(str[3]);
						if(n.timeStamp<ts+n.delay)
						{
							n.timeStamp=ts+n.delay;
						}
						String fileName=str[2];
                        BufferedWriter fileOut=null;
                        PrintWriter pw=null;
                        //File file1 = new File(fileName);  
                        try{
                            fileOut = new BufferedWriter(new FileWriter(new File(fileName),true));
                            //fileOut.newLine();
                            pw=new PrintWriter(fileOut);
                            pw.println(idClient+" "+ts);
                            //fileOut.close(); 
                        }catch(Exception e){}
                        finally{
                        	try{
                        		if(pw!=null)
                        			pw.close();
                        	}catch(Exception e){}
                        }         
                    }
						
							
														    
		         

					File directory;

					if(message.startsWith("ENQUIRY")){
						
						String str[] =message.split(" ");
						int ts = Integer.parseInt(str[1]);
						directory= new File(str[2]);
						if(n.timeStamp<ts+n.delay)
						{
							n.timeStamp=ts+n.delay;
						}	
						String[] fileList = directory.list();
						for (int j = 0; j < fileList.length; j++)
						   System.out.println(fileList[j]);
						
					}
				}
		  }catch(Exception e){}
		 
		}
	}
