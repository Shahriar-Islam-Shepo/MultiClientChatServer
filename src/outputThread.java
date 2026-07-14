
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class outputThread extends Thread{
    private String name;
    private ObjectOutputStream objectOutputStream;
    private Scanner scanner;


    public outputThread(String name,
                        ObjectOutputStream objectOutputStream,
                        Scanner scanner){
        this.name = name;
        this.objectOutputStream = objectOutputStream;
        this.scanner = scanner;
    }



    @Override
    public void run() {
            try{
                while (true) {
                    String reply = scanner.nextLine();
                    objectOutputStream.writeObject(reply);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

    }
}
