# Chat using Java TCP Sockets

This is a 2-user chat application.
One user is the server and the other user is the client.

## How to use:

1.  Compile the application on both the server and client.

        javac tcpchat.java

2.  Run the server application on the server.

        java ChatServer

3.  Run the client application on the client.

        java ChatClient

4.  The client application will ask for server address.
    Enter the IP address of the server.

5.  Enjoy chatting!

Unfortunately, this application doesn't end gracefully when one end closes the connection.
