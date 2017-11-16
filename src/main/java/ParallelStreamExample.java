import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParallelStreamExample {


    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());

        List<Long> list = new ArrayList<>();
        for (long i = 0; i < 10000000; i++) {
            list.add(i);
        }


        printSum(list);
        printParallelSum(list);

        List<Long> list1 = new LinkedList<>();
        for (long i = 0; i < 10000000; i++) {
            list1.add(i);
        }

        printSum(list1);
        printParallelSum(list1);
    }

    private static void printParallelSum(List<Long> list) {
        long time;
        long sum;
        time = System.currentTimeMillis();
        sum = list.parallelStream().mapToLong(i -> i.longValue()).sum();
        time = System.currentTimeMillis() - time;
        System.out.println(time+"\t sum: "+ sum);
    }

    private static void printSum(List<Long> list) {
        long time = System.currentTimeMillis();
        long sum = list.stream().mapToLong(i -> i.longValue()).sum();
        time = System.currentTimeMillis() - time;
        System.out.println(time+"\t sum: "+ sum);
    }
}
