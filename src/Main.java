import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final ConcurrentHashMap<String, ObjectOutputStream> clientMap = new ConcurrentHashMap<>();

    public static void main(String[] args)  {




        String[] clientNameList={"shepo","dipo","fijo","torun","maruf","nobin"};
        int clientNumber=0;


        String HOST ="localhost";
        int PORT = 6000;

        Scanner scanner = new Scanner(System.in);


        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("server is started ... waiting for user ....");

            while (true){

                Socket socket = serverSocket.accept();

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                //maximum 6 user allowed ...
                if(clientNumber == clientNameList.length){
                    System.out.println("no other user is allowed ...");
                    break;
                }

                String clientName = clientNameList[clientNumber++];
                clientMap.put(clientName,objectOutputStream);
                System.out.println(clientName +"---is connected to our server ....");

                clientMaker user = new clientMaker(
                        clientName,
                        scanner,
                        objectInputStream,
                        objectOutputStream

                );
                objectOutputStream.writeObject(" your name is : "+clientName+"\n message format : senderName__receiverName__message");
                user.start();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }
}