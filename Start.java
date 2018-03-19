import java.io.*;
import java.net.*;
import java.util.*;
public class Start {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NodeOperations n=new NodeOperations();
		n.connect();
		n.sendStart(); 
		System.out.println("CLients and Servers connected!!");
	}
}
