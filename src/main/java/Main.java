import parser.service.ThreadRequestURL;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static final int SUBPAGE_COUNT = 5;

    public static void main(String[] args) {
        ThreadRequestURL R1 = new ThreadRequestURL( "Thread-1", 1, (SUBPAGE_COUNT / 2));
        ThreadRequestURL R2 = new ThreadRequestURL( "Thread-2", ((SUBPAGE_COUNT + 2) / 2), SUBPAGE_COUNT);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(R1.getFilePath()));
            writer.print("");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file", e);
        }

        R1.start();
        R2.start();

        try {
            R1.getThread().join();
            R2.getThread().join();
        } catch (Exception e) {
            throw new RuntimeException("Can't join thread", e);
        }

        int productsCount = R1.getProductsCount() + R2.getProductsCount();

        System.out.println("Amount of triggered HTTP requests: " + SUBPAGE_COUNT);
        System.out.println("Amount of extracted products: " + productsCount);
    }
}