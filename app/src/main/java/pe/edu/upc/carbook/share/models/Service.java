package pe.edu.upc.carbook.share.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 05/11/2016.
 */

public class Service {
    private String Description;
    private String ClientName;
    private String ClientCar;
    private Double Total;
    private Double Rating;
    private String FirstImageUrl;
    private String[] Gallery;

    public Service() {
    }

    public Service(String description, String clientName, String clientCar, Double total, Double rating, String firstImageUrl, String[] gallery) {
        Description = description;
        ClientCar = clientCar;
        ClientName = clientName;
        Total = total;
        Rating = rating;
        FirstImageUrl = firstImageUrl;
        Gallery = gallery;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getClientCar() {
        return ClientCar;
    }

    public void setClientCar(String clientCar) {
        ClientCar = clientCar;
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

    public String getFirstImageUrl() {
        return FirstImageUrl;
    }

    public void setFirstImageUrl(String firstImageUrl) {
        FirstImageUrl = firstImageUrl;
    }

    public String[] getGallery() {
        return Gallery;
    }

    public void setGallery(String[] gallery) {
        Gallery = gallery;
    }

    public static final List<Service> services = new ArrayList<Service>();
    static {
        services.add(new Service("Descripcion servicio 1","Pedro Perez", "2015 Ferrari 458", 500.0 , 3.5 ,
                "http://www.cecwheels.com/images/Cars/Ferrari%20458%20c884%20Forged%20gunmetal/04-IMG_9914-001.jpg",null));
        services.add(new Service("Descripcion servicio 2","Jorge Luis Lamarque", "2014 Lamborghini Huracan", 1500.50 , 4.0 ,
                "https://i.ytimg.com/vi/xgG4IKGkXyE/maxresdefault.jpg",null));
        services.add(new Service("Descripcion servicio 3","Luis Nieto", "2016 Mercedes Benz AMG C63", 500.0 , 3.5 ,
                "https://s-media-cache-ak0.pinimg.com/originals/95/2b/06/952b0646cbc45581509ae2251f12d7bf.jpg",null));
        services.add(new Service("Descripcion servicio 4","Adrian Villayzan", "2017 Ford Raptor", 500.0 , 4.5 ,
                "https://www.ford.com/resources/ford/general/microsites/f150raptor/img/wells/raptor_cm_roadrage_2.jpg",null));
        services.add(new Service("Descripcion servicio 5","Jose Martin Flores", "2017 Nissan GTR R35", 2000.0 , 5.0 ,
                "http://wallpapercave.com/wp/ohfaLxR.jpg",null));
    }
}
