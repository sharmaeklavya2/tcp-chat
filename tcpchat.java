import java.net.*;
import java.io.*;

class ChatWriter extends Thread
// Reads input from the console and sends it on the socket
// This thread needs to be killed when the socket closes
// Both the server and the client will use this class
{
    BufferedReader con_br;
    PrintWriter sock_pw;

    public ChatWriter(String name, PrintWriter sock_pw, BufferedReader con_br)
    {
        super(name);
        this.sock_pw = sock_pw;
        this.con_br = con_br;
    }

    public void run()
    {
        String s;
        try
        {
            while(true)
            {
                System.out.print("> ");
                s = con_br.readLine();
                if(s != null)
                    sock_pw.println(s);
                else
                    break;
            }
        }
        catch(Exception e)
        {System.err.println("chat_server_writer: Exception occured:\n" + e);}
    }
}

class ChatServer
{
    public static int port = 13000;
    public static BufferedReader con_br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args)throws IOException
    {
        ServerSocket ssock = new ServerSocket(port);
        System.out.println("server: Waiting for client to connect");
        Socket csock = ssock.accept();
        System.out.println("server: Connection established");

        BufferedReader csock_br = new BufferedReader(new InputStreamReader(csock.getInputStream()));
        PrintWriter csock_pw = new PrintWriter(csock.getOutputStream(), true);

        Thread chat_server_writer = new ChatWriter("chat_server_writer", csock_pw, con_br);
        chat_server_writer.start();

        String s;
        while((s = csock_br.readLine()) != null)
        {
            System.out.println("\rclient: " + s);
            System.out.print("> ");
        }

        System.out.println("\rserver: Client has disconnected");
        csock.close();
        ssock.close();
    }
}

class ChatClient
{
    public static int port = 13000;
    public static BufferedReader con_br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args)throws IOException
    {
        System.out.print("Enter server address: ");
        String address = con_br.readLine();
        Socket sock = new Socket(address, port);
        BufferedReader sock_br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        PrintWriter sock_pw = new PrintWriter(sock.getOutputStream(), true);
        System.out.println("Connection established");

        Thread chat_client_writer = new ChatWriter("chat_client_writer", sock_pw, con_br);
        chat_client_writer.start();
        
        String s;
        while((s = sock_br.readLine()) != null)
        {
            System.out.println("\rserver: " + s);
            System.out.print("> ");
        }
        sock.close();
    }
}
