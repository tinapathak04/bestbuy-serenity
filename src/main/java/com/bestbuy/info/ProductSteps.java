package com.bestbuy.info;

import com.bestbuy.constants.ProductsEndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductSteps {

    @Step("Creating products with name : {0},type : {1},price : {2}, shipping : {3},upc : {4}, description : {5},manufacturer : {6},model : {7}, url :{8}, image :{9}")
    public ValidatableResponse createProducts(String name, String type, int price, int shipping, String upc, String description,
                                              String manufacturer, String model, String url, String image) {

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post()
                .then();

    }

    @Step("Getting the products information with name :{0}")
    public HashMap<String, Object> getProductsInfoByName(String name) {
        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .queryParam("name",name)
                .when()
                .get()
                .then().log().all().statusCode(200)
                .extract()
                .path(s1 + name + s2);

    }

    @Step("Getting the product information with productId: {0}")
    public ValidatableResponse getProductById(int id) {
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .get(ProductsEndPoints.UPDATE_PRODUCTS_BY_ID)
                .then();
    }

    @Step("Updating product information with productsId : {0},type : {1},price : {2}, shipping : {3},upc : {4}, description : {5},manufacturer : {6},model : {7}, url :{8}, image :{9}")
    public ValidatableResponse updateProducts(int productsId, String name, String type, int price, int shipping, String upc, String description,
                                              String manufacturer, String model, String url, String image){

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParam("id", productsId)
                .body(productPojo)
                .when()
                .put(ProductsEndPoints.UPDATE_PRODUCTS_BY_ID)
                .then().log().all();
    }
    @Step("Deleting product information with id :{0}")
    public ValidatableResponse deleteProducts(int id) {
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .delete(ProductsEndPoints.DELETE_PRODUCTS_BY_ID)
                .then();

    }

        @Step("Getting the products information with name :{0}")
        public ValidatableResponse getProductsByID(int productsId){
            return SerenityRest.given()
                    .pathParam("productID", productsId)
                    .when()
                    .get(ProductsEndPoints.GET_SINGLE_PRODUCTS_BY_ID)
                    .then();
    }

}
