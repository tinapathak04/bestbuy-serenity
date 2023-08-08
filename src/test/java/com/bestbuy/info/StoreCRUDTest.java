package com.bestbuy.info;

import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCRUDTest extends TestBase {

    static String name = "Nova";
    static String type = "Shop";
    static String address = "51 Reddrive close";
    static String address2 = "";
    static String city = "London";
    static String state = "MS";
    static String zip = "10234";
    static int lat = 44;
    static int lng = 93;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9";

    static int storesId;

    @Steps
    StoreSteps storeSteps;


    @Title("This will create a new Products")
    @Test
    public void test001() {

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);

        SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)
                .post()
                .then().log().all().statusCode(201);

        storeSteps.createStores(name, type,address,address2,city,state,zip,lat,lng,hours).statusCode(201);
    }
    @Title("Verify if the store was added to the application")
    @Test
    public void test002() {

        HashMap<String, Object> productsMap = storeSteps.getStoresInfoByName(name);
        Assert.assertThat(productsMap, hasValue(name));
        storesId = (int) productsMap.get("id");
    }
    @Title("Get the store by Id")
    @Test
    public void test003() {

        storeSteps.getStoreById(storesId).log().all().statusCode(200);
    }

    @Title("Update the store information and verify the updated information")
    @Test
    public void test004() {
        name = name + "_Updated";
        //update information
        storeSteps.updateStores(storesId, name, type,address,address2,city,state,zip,lat,lng,hours).statusCode(200);

        //verify if the store was updated
        HashMap<String, Object> storeMap = storeSteps.getStoresInfoByName(name);
        Assert.assertThat(storeMap, hasValue(name));
    }

    @Title("Delete the products and verify if the products is deleted!")
    @Test
    public void test005() {
        storeSteps.deleteStores(storesId).statusCode(204);
        storeSteps.getStoresByID(storesId).statusCode(404);


    }

}
