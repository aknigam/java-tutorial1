package google.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.Set;

/**
 * Created by a.nigam on 04/10/16.
 */
public class TablesSample {

    static class Column {

        final String name;


        public Column(String name) {
            this.name = name;

        }

        @Override
        public String toString() {
            return name;
        }


    }
    static class CelVal{

        final int score;
        final String ans;
        String rStub ="";
        String sStub = "";


        public CelVal(int score, String ans, String sStub, String rStub) {
            this.score = score;
            this.ans =ans;
            this.rStub = rStub;
            this.sStub = sStub;
        }

        @Override
        public String toString() {
            return ans+" | "+ score+" | "+ sStub+" | "+ rStub;
        }
    }
    public static void main(String[] args) {

        Table<String, String, CelVal> weightedGraph = HashBasedTable.create();

        weightedGraph.put("R1", "Q1", new CelVal(20, "abc", "R1", "62CA1E71-504E-43B4-84E1-07C781C9B4DB"));
        weightedGraph.put("R1", "Q2", new CelVal(20, "efg", "R1", "62CA1E71-504E-43B4-84E1-07C781C9B4DB"));
        weightedGraph.put("R1", "Q3", new CelVal(20, "ijk", "R1", "62CA1E71-504E-43B4-84E1-07C781C9B4DB"));
        weightedGraph.put("R1", "Q4", new CelVal(20, "lmn", "R1", "62CA1E71-504E-43B4-84E1-07C781C9B4DB"));



        weightedGraph.put("R2", "Q1", new CelVal(20, "opq", "R2", "62CA1E71-504E-43B4-84E1-07C781C9B4DB"));
        weightedGraph.put("R2", "Q2", new CelVal(20, "rst", "R2", "62CA1E71-504E-43B4-84E1-07C781C9B4DB"));
        weightedGraph.put("R2", "Q3", new CelVal(20, "uvw", "R2", "62CA1E71-504E-43B4-84E1-07C781C9B4DB"));
        weightedGraph.put("R2", "Q4", new CelVal(20, "xyz", "R2", "62CA1E71-504E-43B4-84E1-07C781C9B4DB"));



//        System.out.println("original table \n"+weightedGraph+"\n");

        printTable(weightedGraph);

        System.out.println("\nTransformed table\n-------------------\n");


        Table transposedTable = Tables.transpose(weightedGraph);

        printTable(transposedTable);


    }

    private static  <E, V, C> void printTable(Table<E, V, C> t) {
        Set<V> cols = t.columnKeySet();

        Set<E> rows = t.rowKeySet();

        StringBuffer c = new StringBuffer();
        c.append("\t\t");
        for(V col : cols) {
            c.append(col).append("\t\t");
        }
        System.out.println(c);
        for(E r : rows){
            StringBuffer row = new StringBuffer();
            row.append(r).append("\t\t");
            for(V col: cols) {
                row.append(t.get(r, col)).append("\t\t");
            }
            System.out.println(row);
        }

    }


}
