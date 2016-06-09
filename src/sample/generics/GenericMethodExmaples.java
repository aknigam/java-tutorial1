package sample.generics;

/**
 * Created by a.nigam on 18/04/16.
 */
public class GenericMethodExmaples {

    public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
                p1.getValue().equals(p2.getValue());
    }


    public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
        int count = 0;
        for (T e : anArray)
            if (e.compareTo(elem) > 0)
                ++count;
        return count;
    }

    public static void main(String[] args) {
        CompositeKeyBuilder builder = new CompositeKeyBuilder("-");
        System.out.println(builder.append("a").append("b").append("c").append(null).append("d").build());
    }
    static class CompositeKeyBuilder{

        private final String separator;

        CompositeKeyBuilder(String separator){
            this.separator = separator;
        }
        StringBuffer compositeKeyString = new StringBuffer();

        public CompositeKeyBuilder append(String s){
            if(s!=null){
                compositeKeyString.append(s).append(separator);
            }
            return this;
        }
        public String build(){
            compositeKeyString.delete(compositeKeyString.lastIndexOf(separator), compositeKeyString.length());
            return compositeKeyString.toString();
        }
    }



}
