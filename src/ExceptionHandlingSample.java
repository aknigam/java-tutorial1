import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class ExceptionHandlingSample {


    private static final int SIZE = 10;

    public static void main(String[] args) {
        ExceptionHandlingSample sample = new ExceptionHandlingSample();
        sample.writeList();
    }


    public void start(){
        throw new NullPointerException();
    }

    public void writeList() {
        PrintWriter out = null;
        List<String> list = new ArrayList<>();

        try {
            System.out.println("Entering" + " try statement");

            out = new PrintWriter(new FileWriter("OutFile.txt"));
            for (int i = 0; i < SIZE; i++) {
                out.println("Value at: " + i + " = " + list.get(i));
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Caught IndexOutOfBoundsException: "
                    +  e.getMessage());


        } catch (IOException e) {
            System.err.println("Caught IOException: " +  e.getMessage());

        } finally {
            if (out != null) {
                System.out.println("Closing PrintWriter");
                out.close();
            }
            else {
                System.out.println("PrintWriter not open");
            }
        }


    }
}
