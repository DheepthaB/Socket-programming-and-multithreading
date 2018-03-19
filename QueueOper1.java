import java.io.*;
import java.util.*;
//class that handles all the queue operations
public class QueueOper1 
{
public static void insert(String str,NodeOperations n) // code to insert element into the queue
{	
String msg[]=str.split(" ");
int times=Integer.parseInt(msg[1]);
int id=Integer.parseInt(msg[0]);
for(int i=0;i<n.queue1.length;i++)
{
	if(n.queue1[i]==null)
	{
		n.queue1[i]=str;
		break;
	}
	else
	{
		String tss[] = n.queue1[i].split(" ");
		int ts= Integer.parseInt(tss[1]);
		int qid=Integer.parseInt(tss[0]);
		if(ts>times)
		{
			for(int ix=n.queue1.length-1;ix>i;ix--)
			{
				n.queue1[ix]=n.queue1[ix-1];
			}
			n.queue1[i]=str;
			break;
		}
		else if(ts==times)
		{
			if(id<qid)
			{
				for(int ix=n.queue1.length-1;ix>i;ix--)
				{
					n.queue1[ix]=n.queue1[ix-1];
				}
				n.queue1[i]=str;
				break;
			}
		}
	}
}
}

public static void delete(NodeOperations n) // code to delete element from the queue
{
	for(int i=0;i<n.queue1.length-2;i++)
	{
		n.queue1[i]=n.queue1[i+1];	
	}
	n.queue1[n.queue1.length-1]=null;
}
	
}
