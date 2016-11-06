package pe.edu.upc.carbook.share.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 06/11/2016.
 */

public class Local {
    private String Name;
    private String Address;
    private int Capacity;
    private Double Latitude;
    private Double Longitude;
    private int ProviderId;

    public Local() {
    }

    public Local(String name, String address, int capacity, Double latitude, Double longitude, int providerId) {
        Name = name;
        Address = address;
        Capacity = capacity;
        Latitude = latitude;
        Longitude = longitude;
        ProviderId = providerId;
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

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
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

    public int getProviderId() {
        return ProviderId;
    }

    public void setProviderId(int providerId) {
        ProviderId = providerId;
    }

    public final static List<Local> locals = new ArrayList<Local>();
    static {
        locals.add(new Local("Local 1 prueba","Avenida Angamos 2356, San Borja",15,null,null,1));
        locals.add(new Local("Local 2 prueba","Avenida Angamos 2356, San Borja",25,null,null,1));
        locals.add(new Local("Local 3 prueba","Avenida Angamos 2356, San Borja",5,null,null,1));
        locals.add(new Local("Local 4 prueba","Avenida Angamos 2356, San Borja",10,null,null,1));
        locals.add(new Local("Local 5 prueba","Avenida Angamos 2356, San Borja",12,null,null,1));
    }
}
