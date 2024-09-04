import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class GrepCommand {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Expected 2 argument provided " + args.length);
            System.exit(1);
        }
        String path = args[1];
        String stringToSearch = args[0];
        File file = new File(path);
        ConcurrentLinkedQueue<String> linkedQueue = new ConcurrentLinkedQueue<>();
        if (!file.isFile()) {
            System.out.println("Provided directory which is not supported as of now");
            System.exit(1);
        }
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        List<Future<?>> futures = new ArrayList<>();
        try (var reader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            while (bufferedReader.ready()) {
                Future<?> future = executorService.submit(() -> {
                    try {
                        String s = bufferedReader.readLine();
                        if (s!=null && s.contains(stringToSearch)) {
                            linkedQueue.add(s.trim());
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                futures.add(future);
            }
            futures.forEach(future -> {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        linkedQueue.forEach(System.out::println);
    }
}
