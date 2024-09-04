import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CatCommand {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Provide one argument");
            System.exit(1);
        }
        String path = args[0];
        File file = new File(path);
        ArrayList<String> strings = new ArrayList<>();
        try (var fileInputStream = new FileInputStream(file)) {
            var bufferedInputStream = new BufferedInputStream(fileInputStream);
            long start = System.currentTimeMillis();
            while(true){
                int read = bufferedInputStream.read();
                if(read!=-1){
                    System.out.print((char)read);
                    long end = System.currentTimeMillis();
                    strings.add(String.valueOf((end-start)/1000D));
                    continue;
                }
                break;
            }
        }
    }
}
