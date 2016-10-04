package sample.optional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by a.nigam on 11/05/16.
 */
@SuppressWarnings("validate")
public class FileUtils {
    private FileUtils(){}
    public static String readContents(String fileName) {

        StringBuilder builder = new StringBuilder();
        String line = null;
        try ( BufferedReader br = new BufferedReader(new FileReader( fileName )) ){
            while((line = br.readLine())!= null){
                builder.append(line);
            }
        } catch ( IOException ex) {
            throw new RuntimeException(ex);
        }

        return builder.toString();
    }
}
