package com.bestbuy.info;

import com.bestbuy.constants.ProductsEndPoints;
import com.bestbuy.constants.StoresEndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoreSteps {


    @Step("Creating stores with name : {0},type : {1},address : {2}, address2 : {3},city : {4}, state : {5},zip : {6},lat : {7}, lng :{8}, hours :{9}")
    public ValidatableResponse createStores(String name, String type, String address, String address2, String city, String state,
                                              String zip, int lat, int lng, String hours) {

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

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)
                .post()
                .then();

    }
    @Step("Getting the stores information with name :{0}")
    public HashMap<String, Object> getStoresInfoByName(String name) {
        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                //.queryParam("name",name)
                .when()
                .get(StoresEndPoints.GET_ALL_STORES)
                .then().statusCode(200)
                .extract()
                .path(s1 + name + s2);

    }
    @Step("Getting the store information with storeId: {0}")
    public ValidatableResponse getStoreById(int storesId) {
        return SerenityRest.given().log().all()
                .pathParam("storesId", storesId)
                .when()
                .get(StoresEndPoints.UPDATE_STORES_BY_ID)
                .then();
    }

    @Step("Updating product information with storesId name : {0},type : {1},address : {2}, address2 : {3},city : {4}, state : {5},zip : {6},lat : {7}, lng :{8}, hours :{9}")
    public ValidatableResponse updateStores(int storesId, String name, String type, String address, String address2, String city, String state,
                                              String zip, int lat, int lng, String hours){

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

        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParam("storesId", storesId)
                .body(storePojo)
                .when()
                .put(ProductsEndPoints.UPDATE_PRODUCTS)
                .then();
    }
    @Step("Getting the student information with firstName :{0}")
    public ValidatableResponse deleteStores(int storesId) {
        return SerenityRest.given()
                .pathParam("storesId", storesId)
                .when()
                .delete(StoresEndPoints.DELETE_STORES_BY_ID)
                .then();

    }

    @Step("Getting the products information with name :{0}")
    public ValidatableResponse getStoresByID(int storesId){
        return SerenityRest.given()
                .pathParam("storesId", storesId)
                .when()
                .get(StoresEndPoints.GET_SINGLE_STORES_BY_ID)
                .then();
    }
}
