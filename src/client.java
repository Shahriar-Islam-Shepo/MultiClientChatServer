import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        String HOST ="localhost";
        int PORT = 6000;

        Scanner scanner = new Scanner(System.in);


        try{

            Socket socket = new Socket(HOST,PORT);


            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            outputThread sender = new outputThread("server",objectOutputStream,scanner);
            inputThread receiver = new inputThread("server",objectInputStream);



            receiver.start();
            sender.start();


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
