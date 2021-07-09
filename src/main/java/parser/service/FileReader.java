package parser.service;

import java.io.*;

public class FileReader {
    public String readFromResourcesFile(String resourceName) {
        StringBuffer result = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(resourceName)))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
