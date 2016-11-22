package pe.edu.upc.carbook.share.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 21/11/2016.
 */

public class Car {
    private String carId;
    private String clientId;
    private String brand;
    private String model;
    private String status;
    private String plateNumber;
    private String firstPhotoUrl;

    public Car() {
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getFirstPhotoUrl() {
        return firstPhotoUrl;
    }

    public void setFirstPhotoUrl(String firstPhotoUrl) {
        this.firstPhotoUrl = firstPhotoUrl;
    }

    public String getCarFullName(){
        return this.brand + " " + this.model;
    }

    public static Car buildFromJSONObject(JSONObject jsonCar){
        Car car = new Car();
        try{
            car.setCarId(jsonCar.getString("CarId"));
            car.setClientId(jsonCar.getString("ClientId"));
            car.setBrand(jsonCar.getString("Brand"));
            car.setModel(jsonCar.getString("Model"));
            car.setStatus(jsonCar.getString("Status"));
            car.setPlateNumber(jsonCar.getString("PlateNumber"));
            car.setFirstPhotoUrl(jsonCar.getString("FirstPhotoUrl"));
            return car;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return car;
    }

    public static List<Car> buildFromJSONArray(JSONArray jsonResult){
        List<Car> cars = new ArrayList<>();
        int carsCount = jsonResult.length();
        for(int i = 0; i < carsCount; i++){
            try {
                cars.add(buildFromJSONObject(jsonResult.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return cars;
    }

}
