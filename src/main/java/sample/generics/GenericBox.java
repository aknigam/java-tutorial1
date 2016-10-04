package sample.generics;

import java.util.List;

/**
 * Created by a.nigam on 18/04/16.
 */
public class GenericBox<T extends Number> {

    // T stands for "Type"
    private T t;

    public void set(T t) { this.t = t; }
    public T get() {

        return t;
    }

    public T print (T t){


        return t;
    }

    public static void printList(List<? extends Number> list) {
        for (Number elem: list) {
            System.out.print(elem + " ");
            elem.intValue();

        }
        System.out.println();
    }


    public static void main(String[] args) {
        GenericBox<Double> strbox = new GenericBox<>();
        GenericBox<Integer> intBox = new GenericBox<>();
    }

}
