package parser.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import parser.model.Product;
import java.util.HashMap;
import java.util.Map;

public class WebParser {
    public Product[] getProductsFromJson(String webContentJson) {
        JsonObject webContent = new JsonParser().parse(webContentJson).getAsJsonObject();
        Product[] products = new Product[0];

        if (webContent.has("entities")) {
            JsonArray entitiesArray = webContent.get("entities").getAsJsonArray();
            products = new Product[entitiesArray.size()];

            for (int i = 0; i < entitiesArray.size(); i++) {
                JsonObject entity = entitiesArray.get(i).getAsJsonObject();
                if (entity.get("isActive").getAsBoolean()) {
                    JsonObject attributes = entity.get("attributes").getAsJsonObject();

                    String id = entity.get("id").getAsString();

                    String productName = attributes
                            .get("name")
                            .getAsJsonObject()
                            .get("values")
                            .getAsJsonObject()
                            .get("label")
                            .getAsString();

                    String brandName = attributes
                            .get("brand")
                            .getAsJsonObject()
                            .get("values")
                            .getAsJsonObject()
                            .get("label")
                            .getAsString();

                    JsonObject priceObject = entity.get("priceRange")
                            .getAsJsonObject()
                            .get("min")
                            .getAsJsonObject();
                    //Converted to price value with decimal point
                    String priceValue = String.format("%.02f", priceObject.get("withTax").getAsFloat() / 100);
                    String priceCurrency = priceObject.get("currencyCode").getAsString();


                    Map<String, String> colorsMap = new HashMap<String, String>();
                    String firstColor = attributes
                            .get("colorDetail")
                            .getAsJsonObject()
                            .get("values")
                            .getAsJsonArray()
                            .get(0)
                            .getAsJsonObject()
                            .get("label")
                            .getAsString()
                            .toLowerCase();
                    colorsMap.put(firstColor, firstColor);

                    if (entity.get("advancedAttributes").getAsJsonObject().has("siblings")) {
                        JsonArray colorArray = entity
                                .get("advancedAttributes")
                                .getAsJsonObject()
                                .get("siblings")
                                .getAsJsonObject()
                                .get("values")
                                .getAsJsonArray()
                                .get(0)
                                .getAsJsonObject()
                                .get("fieldSet")
                                .getAsJsonArray()
                                .get(0)
                                .getAsJsonArray();

                        for (JsonElement colorElement : colorArray) {
                            if (colorElement.getAsJsonObject().get("isSoldOut").getAsBoolean()) {
                                continue;
                            }

                            String color = colorElement
                                    .getAsJsonObject()
                                    .get("color")
                                    .getAsJsonArray()
                                    .get(0)
                                    .getAsJsonObject()
                                    .get("label")
                                    .getAsString()
                                    .toLowerCase();

                            if (!colorsMap.containsValue(color)) {
                                colorsMap.put(color, color);
                            }
                        }
                    }
                    String[] colorsArray = colorsMap.values().toArray(new String[colorsMap.size()]);

                    products[i] = new Product(productName,
                            brandName,
                            priceValue,
                            priceCurrency,
                            id,
                            colorsArray);
                }
            }
        }
        return products;
    }
}