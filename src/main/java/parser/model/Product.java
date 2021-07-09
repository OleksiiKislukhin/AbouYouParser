package parser.model;

public class Product {
    private String productName;
    private String brandName;
    private String price;
    private String priceCurrency;
    private String articleID;
    private String[] colors;

    public Product(String productName,
                   String brandName,
                   String price,
                   String priceCurrency,
                   String articleID,
                   String[] colors) {
        this.productName = productName;
        this.brandName = brandName;
        this.price = price;
        this.priceCurrency = priceCurrency;
        this.articleID = articleID;
        this.colors = colors;
    }
}
