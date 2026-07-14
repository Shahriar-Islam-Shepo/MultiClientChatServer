import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;


public class inputThread  extends Thread{

    private String name;
    private ObjectInputStream objectInputStream;


    public inputThread(String name,ObjectInputStream objectInputStream){
        this.name = name;
        this.objectInputStream = objectInputStream;

    }


    @Override
    public void run() {

            try{
                while (true) {
                    String FullMessage = (String) objectInputStream.readObject();
                    System.out.println(FullMessage);

                    if(FullMessage.contains("__")) {
                        String[] parts = FullMessage.split("__", 3);
                        String userName = parts[0];
                        String receiver = parts[1];
                        String message = parts[2];
                        ObjectOutputStream ob = Main.clientMap.get(receiver);

                        if (ob != null) {
                            ob.writeObject(userName+" :"+message);
                        }

                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }


    }
}
