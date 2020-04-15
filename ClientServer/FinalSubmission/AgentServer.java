/** File is: InetServer.java, Version 1.9

A multithreaded server for AgentClient. Elliott, after Hughes, Shoffner, Winslow

You will need at least TWO separate command windows to run this N > 1 process program.

ShellA> java AgentServer
ShellB> java AgentClient

Optionally, to run across the Internet:

ShellB> java AgentClient YourDomainNameOfOtherMachine

You can run 5,000 AgentClients if you wish. Each will have a separate conversation with its Worker.

Windows Bat File:

> start java AgentServer
> start java AgentClient
----------------------------------------------------------------------*/

import java.io.*;  // Get the Input Output libraries
import java.net.*; // Get the Java networking libraries

class Worker extends Thread {    // Class definition. Each connection gets its own Worker
  Socket sock;                   // Class member, socket, local to Worker.
  Worker (Socket s) {sock = s;}  // Constructor, assign arg s to local sock
  String prevMessge=new String();
  public void run(){
    // Get I/O streams in/out from the socket:
    PrintStream out = null;
    BufferedReader in = null;

    try {
      in = new BufferedReader
        (new InputStreamReader(sock.getInputStream()));
      out = new PrintStream(sock.getOutputStream());
      // Note that this branch might not execute when expected:
      try {
    	//Ask the client to tell you more about whatever it is they talked about
    	String[] InquisitiveMessages= {"I implore you to tell me more about ","Pleased to be of service, please continue to type in some messages to me about ","There was a glitch in the matrix, I could not comprehend what you just said, but I do not like being blamed for anything, so, the fault must lie with you. yes, so, now get to typing one more message so that I can correctly answer your queries about "};

    	//Neglect the client when they ask questions - (?)
    	String[] NeglectfulMessages= {
    			"I actually dont care enough about you to tell you anything.Do continue talking to yourself", "Lets talk about something else I dont want to talk about this.", "It was folly to try and understand your mind, please do not disturb me anymore."};
    	String ClientMsg;
        ClientMsg = in.readLine ();//Get data from client
        System.out.println("Got: " + ClientMsg);

	//If the Client's message contains a '?', then enter this branching statement
	if(ClientMsg.contains("?") )
	{
	var randomNum=(int)Math.round(Math.random()*(NeglectfulMessages.length-1));//Get a random number
	out.println(NeglectfulMessages[randomNum]);//Sends a neglectful message to the client.


	}//If the client's message does not contain a '?' enter this branch
	else {
		out.println("Hi. I am your Agent."); // To client...
		Integer messageTypeRand=(int) (Math.random()*(InquisitiveMessages.length));

		out.println("You said " + ClientMsg +"?");
		out.println(InquisitiveMessages[messageTypeRand]+ ClientMsg);//Acts as if the system is asking the user for more information, but does not save or reuse the information anywhere

	}

      } catch (IOException x) {
        System.out.println("Server read error");
        x.printStackTrace ();
      }
      sock.close(); // close this connection, but not the server;
    } catch (IOException ioe) {System.out.println(ioe);}
  }
}

public class AgentServer {

  public static void main(String a[]) throws IOException {
    int q_len = 6; /* Not interesting. # of simultaneous requests for OpSys to queue */
    int port = 45565;
    Socket sock; // This will hold the new connection.

    @SuppressWarnings("resource")
	ServerSocket servsock = new ServerSocket(port, q_len); // Create a doorbell socket

    System.out.println
      ("Srinath's Agent server 1 starting up, listening at port 45565.\n");
    while (true) {
      sock = servsock.accept(); // Wait for the next client connection. Ding-Dong! Hello?
      new Worker(sock).start(); // Spawn worker to handle it, and start the thread.
    }
  }
}

