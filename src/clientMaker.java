import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class clientMaker extends Thread{

    private String name;
    private Scanner scanner;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public clientMaker(String name,
                       Scanner scanner,
                       ObjectInputStream objectInputStream,
                       ObjectOutputStream objectOutputStream){
        this.name = name;
        this.scanner = scanner;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void run() {
        inputThread in = new inputThread(name,objectInputStream);
        outputThread out = new outputThread(name,objectOutputStream,scanner);

        in.start();
        out.start();
    }
}
