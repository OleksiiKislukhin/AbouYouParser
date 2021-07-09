package parser.service;

import parser.model.Product;

public class ThreadRequestURL extends Thread {
    public static final String WEB_URL_FILE_PATH = "web_url.txt";
    public static final String OUTPUT_FILE_PATH = System.getProperty("user.dir") + "/output.json";
    public static final String WEB_URL_INDEX_NAME = "<index_for_change>";

    private Thread t;
    private String threadName;

    private int subpageStart;
    private int subpageEnd;
    private int productsCount;

    public ThreadRequestURL(String name,
                            int subpageStart,
                            int subpageEnd) {
        this.threadName = name;
        this.subpageStart = subpageStart;
        this.subpageEnd = subpageEnd;
        this.productsCount = 0;
    }

    public void run() {
        FileReader fileReader = new FileReader();
        String webUrl = fileReader.readFromResourcesFile(WEB_URL_FILE_PATH);
        WebParser webParser = new WebParser();
        FileWriterJson fileWriterJson = new FileWriterJson();
        JsonFromUrl jsonFromUrl = new JsonFromUrl();
        String webPageJson;

        for (int subpagesIndex = subpageStart; subpagesIndex <= subpageEnd; subpagesIndex++) {
            String webUrlWithIndex = webUrl.replace(WEB_URL_INDEX_NAME, String.valueOf(subpagesIndex));

            try {
                webPageJson = jsonFromUrl.getContent(webUrlWithIndex);
            } catch (RuntimeException exception) {
                System.out.println("Can't load subpage " + subpagesIndex + " .\n" + exception);
                continue;
            }

            Product[] products = webParser.getProductsFromJson(webPageJson);
            productsCount += products.length;
            fileWriterJson.writeToFile(products, OUTPUT_FILE_PATH);
        }
    }

    public void start () {
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

    public int getProductsCount() {
        return productsCount;
    }

    public Thread getThread() {
        return t;
    }

    public String getFilePath() {
        return OUTPUT_FILE_PATH;
    }
}
