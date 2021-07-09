package parser.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import parser.model.Product;
import com.google.gson.Gson;

public class FileWriterJson {
    public void writeToFile(Product[] products, String filePath) {
        try {
            Gson gson = new Gson();
            PrintWriter writer = new PrintWriter(new FileWriter(filePath, true));
            gson.toJson(products, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
