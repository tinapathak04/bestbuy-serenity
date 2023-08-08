package com.bestbuy.info;

import com.bestbuy.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCRUDTest extends TestBase {

    static String name = "Sainsbury - AA Batteries(4-Pack)";
    static String type = " HardGood";
    static int price = 4;
    static int shipping = 0;
    static String upc = "042100222454";
    static String description = "Long-lasting energy; for toys, clocks, radios, games, remotes, and more";
    static String manufacturer = "Sainsbury";
    static String model = "SN1300S4N";
    static String url = "http://www.bestbuy.com/site/duracell-aa-1-5v-coppertop-batteries-4-pack/48530.p?id=1099385268988&skuId=48530&cmp=RMXCC";
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";


    static int productsId;

    @Steps
    ProductSteps productsSteps;


    @Title("This will create a new Products")
    @Test
    public void test001() {

//        ProductPojo productPojo = new ProductPojo();
//        productPojo.setName(name);
//        productPojo.setType(type);
//        productPojo.setPrice(price);
//        productPojo.setShipping(shipping);
//        productPojo.setUpc(upc);
//        productPojo.setDescription(description);
//        productPojo.setManufacturer(manufacturer);
//        productPojo.setModel(model);
//        productPojo.setUrl(url);
//        productPojo.setImage(image);
//
//        SerenityRest.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .body(productPojo)
//                .post()
//                .then().log().all().statusCode(201);

        ValidatableResponse response = productsSteps.createProducts(name, type, price, shipping, upc, description, manufacturer, model, url, image)
                .log().all().statusCode(201);
        productsId = response.extract().path("id");
    }

    @Title("Verify if the product was added ")
    @Test
    public void test002() {

        HashMap<String, Object> productsMap = productsSteps.getProductsInfoByName(name);
        productsId = (int) productsMap.get("id");
        Assert.assertThat(productsMap, hasValue(name));

    }


    @Title("Get the Products by Id")
    @Test
    public void test003() {

        productsSteps.getProductById(productsId).log().all().statusCode(200);
    }

//    @Title("This will update product with id")
//    @Test
//    public void test004() {
//
//        name = name + "_456";
//        //update information
//        productsSteps.updateProducts(productsId,name,type,price, shipping, upc, description, manufacturer, model, url, image).statusCode(200);
//
//        //verify if the product was updated
//        HashMap<String, Object> productMap = productsSteps.getProductsInfoByName(name);
//        Assert.assertThat(productMap, hasValue(name));
//
//    }

//    @Title("This will delete product with id")
//    @Test
//    public void test005() {
//        //delete the product
//        productsSteps.deleteProducts(productsId).statusCode(200);
//
//        //verify that the product is deleted
//        productsSteps.getProductById(productsId).statusCode(404);
//
//    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test004() {
        name = name + "_Updated";
        //update information
        productsSteps.updateProducts(productsId, name, type, price, shipping, upc, description, manufacturer, model, url, image).statusCode(200);

        //verify if the product was updated
        HashMap<String, Object> productMap = productsSteps.getProductsInfoByName(name);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Delete the products and verify if the products is deleted!")
    @Test
    public void test005() {
        productsSteps.deleteProducts(productsId).statusCode(204);
        productsSteps.getProductsByID(productsId).statusCode(404);


    }
}
