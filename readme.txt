Langugae used: JAVA (jdk 1.8)

Follow the given steps to run the distributed mututal exclusion algorithm:

1. Connect to the virtual machines-dc01.utdallas.edu-dc09.utdallas.edu using putty
   dc01-dc05 are the client machines. 
   dc06-dc08 are the server machines.
   dc09 is the extra machine used to trigger the entire process.
   Type "cd public_html" on all terminals. 
  
2. Type "javac Node.java" on all the 8 putty terminals to compile the code.
   Type "java Node" on all the the 8 putty terminals. This will get all the machines to be up and running.
   The program would prmpt the user to enter the id of the process and propogation delay(used For lamport's clock    implementation).

3. On the 9th putty terminal corresponding to dc09 macine, type "javac Start.java".
   After compiling, type "java Start".
   This would get all the clients and servers to be connected.

 
The program runs until each client gets a chance to enter the critical sections 30 times.

I have logged the messages on the console output screen(Putty terminal).