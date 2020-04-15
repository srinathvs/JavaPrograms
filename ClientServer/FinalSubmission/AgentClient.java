
/** File is: AgentClient.java, Version 1.9

    A client for AgentServer. Elliott, after Hughes, Shoffner, Winslow

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


public class AgentClient{
  public static String finalString="";

	static UUID uniqueKey = UUID.randomUUID();
  public static void main (String args[]) throws IOException {

    String serverName;
    //Generates uniqueLog for every client
    String path="AISocketLog of "+uniqueKey+".txt";

    if (args.length < 1) serverName = "localhost";
    else serverName = args[0];

    System.out.println("Srinath's Agent Client,"+uniqueKey.toString()+ "\n");
    System.out.println("Using server: " + serverName + ", Port: 45565");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    try {
      String name;

      do {

        System.out.print
          ("Enter a string to send to the Agent, (quit) to end: ");
        System.out.flush ();
        name = in.readLine ();
        if (name.indexOf("quit") < 0)

          getAgentResponse(name, serverName);
      } while (name.indexOf("quit") < 0); // quitSmoking.com?
      Files.writeString(Paths.get(path), finalString);
      System.out.println ("Cancelled by user request.");

    } catch (IOException x) {x.printStackTrace ();}
  }



  static void getAgentResponse (String name, String serverName){
    Socket sock;
    BufferedReader fromServer;
    PrintStream toServer;
    String textFromServer;



    try{
      /* Open our connection to server port, choose your own port number.. */
      sock = new Socket(serverName, 45565);

      // Create filter I/O streams for the socket:
      fromServer =
        new BufferedReader(new InputStreamReader(sock.getInputStream()));
      toServer = new PrintStream(sock.getOutputStream());

      // Send the Agent Server your string:
      toServer.println(name);
      toServer.flush();
      //Writing the query to the string
      finalString+=uniqueKey+" : "+name+"\n";

      // Read several lines of response from the server,
      // and block while synchronously waiting:

     for (int i = 1; i <=20; i++){
        textFromServer = fromServer.readLine();
        if (textFromServer != null) {
        	//Appending to the final String
        	finalString+=uniqueKey+ "'s server : "+textFromServer+"\n";
            System.out.println(textFromServer);

        }

      }

      sock.close();
    } catch (IOException x) {
      System.out.println ("Socket error.");
      x.printStackTrace ();
    }
  }




}
