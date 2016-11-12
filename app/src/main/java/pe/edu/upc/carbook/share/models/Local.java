package pe.edu.upc.carbook.share.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 06/11/2016.
 */

public class Local {
    private String Name;
    private String Address;
    private Integer Capacity;
    private Double Latitude;
    private Double Longitude;
    private Integer ProviderId;
    private String FirstPhotoUrl;

    public Local() {
    }

    public Local(String name, String address, Integer capacity, Double latitude, Double longitude, Integer providerId, String firstPhotoUrl) {
        Name = name;
        Address = address;
        Capacity = capacity;
        Latitude = latitude;
        Longitude = longitude;
        ProviderId = providerId;
        FirstPhotoUrl = firstPhotoUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getCapacity() {
        return Capacity;
    }

    public void setCapacity(Integer capacity) {
        Capacity = capacity;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Integer getProviderId() {
        return ProviderId;
    }

    public void setProviderId(Integer providerId) {
        ProviderId = providerId;
    }

    public String getFirstPhotoUrl() {
        return FirstPhotoUrl;
    }

    public void setFirstPhotoUrl(String firstPhotoUrl) {
        FirstPhotoUrl = firstPhotoUrl;
    }


}
