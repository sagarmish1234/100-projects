import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.*;

public class LsCommand {


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("1 argument need");
            System.exit(1);
        }
        String pathname = args[0];
        File directory = new File(pathname);
        if (!directory.isDirectory()) {
            System.out.println("The path is not a directory");
            System.exit(1);
        }
        ConcurrentLinkedQueue<String> linkedQueue = new ConcurrentLinkedQueue<>();
        ArrayList<Future<?>> arrayList = new ArrayList<>();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            long start = System.currentTimeMillis();
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                Future<?> future = executorService.submit(() -> {
                    String format = String.format("%s MB ----- %s", getFolderSize(file) / (1024 * 1024), file.getName());
                    linkedQueue.add(format);
                });
                arrayList.add(future);
            }
            for (Future<?> future : arrayList){
                future.get();
            }
            linkedQueue.forEach(System.out::println);
            System.out.println((System.currentTimeMillis() - start) / 1000);
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Exception thrown");
        }

    }

    private static long getFolderSize(File folder) {
        long length = 0;
        File[] files = folder.listFiles();

        if (files == null)
            return length;

        for (File file : files) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += getFolderSize(file);
            }
        }
        return length;
    }
}
