package google.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.Set;

/**
 * Created by a.nigam on 04/10/16.
 */
public class TablesSample {

    public static void main(String[] args) {

        Table<String, String, String> weightedGraph = HashBasedTable.create();

        weightedGraph.put("A48726AC-76EF-4339-B496-00076CC975C4", "r_stub", "003671CB-FF53-4779-9056-073C48379F53");
        weightedGraph.put("A48726AC-76EF-4339-B496-00076CC975C4", "scores", "20");
        weightedGraph.put("A48726AC-76EF-4339-B496-00076CC975C4", "s_stub", "62CA1E71-504E-43B4-84E1-07C781C9B4DB");
        weightedGraph.put("A48726AC-76EF-4339-B496-00076CC975C4", "a_text", "abc");


        weightedGraph.put("B48726AC-76EF-4339-B496-00076CC975C4", "r_stub", "003671CB-FF53-4779-9056-073C48379F53");
        weightedGraph.put("B48726AC-76EF-4339-B496-00076CC975C4", "scores", "10");
        weightedGraph.put("B48726AC-76EF-4339-B496-00076CC975C4", "s_stub", "62CA1E71-504E-43B4-84E1-07C781C9B4DB");
        weightedGraph.put("B48726AC-76EF-4339-B496-00076CC975C4", "a_text", "efg");

        weightedGraph.put("C48726AC-76EF-4339-B496-00076CC975C4", "r_stub", "003671CB-FF53-4779-9056-073C48379F53");
        weightedGraph.put("C48726AC-76EF-4339-B496-00076CC975C4", "scores", "40");
        weightedGraph.put("C48726AC-76EF-4339-B496-00076CC975C4", "s_stub", "62CA1E71-504E-43B4-84E1-07C781C9B4DB");
        weightedGraph.put("C48726AC-76EF-4339-B496-00076CC975C4", "a_text", "ijk");

//        System.out.println("original table \n"+weightedGraph+"\n");

        printTable(weightedGraph);

        System.out.println("\nTransformed table\n-------------------\n");


        Table transposedTable = Tables.transpose(weightedGraph);

        printTable(transposedTable);


    }

    private static void printTable(Table<String, String, String> t) {
        Set<String> cols = t.columnKeySet();

        Set<String> rows = t.rowKeySet();

        StringBuffer c = new StringBuffer();
        c.append("\t\t");
        for(String col : cols) {
            c.append(col).append("\t\t");
        }
        System.out.println(c);
        for(String r : rows){
            StringBuffer row = new StringBuffer();
            row.append(r).append("\t\t");
            for(String col: cols) {
                row.append(t.get(r, col)).append("\t\t");
            }
            System.out.println(row);
        }

    }


}
