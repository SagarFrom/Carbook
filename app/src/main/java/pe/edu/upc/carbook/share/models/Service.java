package pe.edu.upc.carbook.share.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 05/11/2016.
 */

public class Service {
    private Integer ServiceId;
    private Integer AdvertId;
    private String Name;
    private String Description;
    private String CustomerName;
    private String CustomerCar;
    private Double Total;
    private Double Rating;
    private String FirstPhotoUrl;
    private String Status;
    private String[] Gallery;

    public Service() {
    }

    public Service(Integer serviceId, Integer advertId, String name, String description, String customerName, String customerCar, Double total, Double rating, String firstPhotoUrl, String status, String[] gallery) {
        ServiceId = serviceId;
        AdvertId = advertId;
        Name = name;
        Description = description;
        CustomerName = customerName;
        CustomerCar = customerCar;
        Total = total;
        Rating = rating;
        FirstPhotoUrl = firstPhotoUrl;
        Status = status;
        Gallery = gallery;
    }

    public Integer getServiceId() {
        return ServiceId;
    }

    public void setServiceId(Integer serviceId) {
        ServiceId = serviceId;
    }

    public Integer getAdvertId() {
        return AdvertId;
    }

    public void setAdvertId(Integer advertId) {
        AdvertId = advertId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerCar() {
        return CustomerCar;
    }

    public void setCustomerCar(String customerCar) {
        CustomerCar = customerCar;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public Double getRating() {
        return Rating;
    }

    public void setRating(Double rating) {
        Rating = rating;
    }

    public String getFirstPhotoUrl() {
        return FirstPhotoUrl;
    }

    public void setFirstPhotoUrl(String firstPhotoUrl) {
        FirstPhotoUrl = firstPhotoUrl;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String[] getGallery() {
        return Gallery;
    }

    public void setGallery(String[] gallery) {
        Gallery = gallery;
    }

    public static List<Service> getServices() {
        return services;
    }

    public static final List<Service> services = new ArrayList<Service>();

    public static Service buildFromJSONObject(JSONObject jsonService){
        Service service = new Service();
        try{
            service.setServiceId(jsonService.getInt("ServiceId"));
            service.setAdvertId(jsonService.getInt("AdvertId"));
            service.setName(jsonService.getString("Name"));
            service.setDescription(jsonService.getString("Description"));
            service.setCustomerName(jsonService.getString("CustomerName"));
            service.setCustomerCar(jsonService.getString("CustomerCar"));
            service.setTotal(jsonService.getDouble("Total"));
            service.setRating(jsonService.getDouble("Rating"));
            service.setFirstPhotoUrl(jsonService.getString("FirstPhotoUrl"));
            service.setStatus(jsonService.getString("Status"));
            return service;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
    public static List<Service> buildFromJSONArray(JSONArray jsonResult){
        List<Service> services = new ArrayList<>();
        int servicesCount = jsonResult.length();
        for(int i = 0 ; i < servicesCount; i++) {
            try{
                services.add(buildFromJSONObject(jsonResult.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return services;
    }
//    static {
//        services.add(new Service("Descripcion servicio 1","Pedro Perez", "2015 Ferrari 458", 500.0 , 3.5 ,
//                "http://www.cecwheels.com/images/Cars/Ferrari%20458%20c884%20Forged%20gunmetal/04-IMG_9914-001.jpg",null));
//        services.add(new Service("Descripcion servicio 2","Jorge Luis Lamarque", "2014 Lamborghini Huracan", 1500.50 , 4.0 ,
//                "https://i.ytimg.com/vi/xgG4IKGkXyE/maxresdefault.jpg",null));
//        services.add(new Service("Descripcion servicio 3","Luis Nieto", "2016 Mercedes Benz AMG C63", 500.0 , 3.5 ,
//                "https://s-media-cache-ak0.pinimg.com/originals/95/2b/06/952b0646cbc45581509ae2251f12d7bf.jpg",null));
//        services.add(new Service("Descripcion servicio 4","Adrian Villayzan", "2017 Ford Raptor", 500.0 , 4.5 ,
//                "https://www.ford.com/resources/ford/general/microsites/f150raptor/img/wells/raptor_cm_roadrage_2.jpg",null));
//        services.add(new Service("Descripcion servicio 5","Jose Martin Flores", "2017 Nissan GTR R35", 2000.0 , 5.0 ,
//                "http://wallpapercave.com/wp/ohfaLxR.jpg",null));
//    }
}
