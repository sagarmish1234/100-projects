import java.io.*;
import java.util.ArrayList;

public class WcCommand {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Provide one argument");
            System.exit(1);
        }
        String path = args[0];
        File file = new File(path);
        try (var fileReader = new FileReader(file)) {
            var bufferedReader = new BufferedReader(fileReader);
            long count = bufferedReader.lines().count();
            System.out.println(count);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
