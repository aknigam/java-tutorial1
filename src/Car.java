import java.util.Map;

/**
 * Created by a.nigam on 12/04/16.
 */
public class Car extends  Vehicle implements  Writabkle{

    @Override
    public void start() {
        System.out.println("car ");
    }

    @Override
    public void write() {

    }


    public void dump(Map a ){
        new Runnable(){
            @Override
            public void run() {
                System.out.println(a.get(""));

            }
        };
    }
}


class Bike extends  Vehicle implements  Writabkle{

    @Override
    public void start() {
        System.out.println("car ");
    }

    @Override
    public void write() {

    }
}