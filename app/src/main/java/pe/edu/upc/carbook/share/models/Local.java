package pe.edu.upc.carbook.share.models;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String Status;

    private List<GenericPhoto> Gallery;

    public Local() {
    }

    public Local(String name, String address, Integer capacity, Double latitude, Double longitude, Integer providerId, String firstPhotoUrl, String status) {
        Name = name;
        Address = address;
        Capacity = capacity;
        Latitude = latitude;
        Longitude = longitude;
        ProviderId = providerId;
        FirstPhotoUrl = firstPhotoUrl;
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public Local setName(String name) {
        Name = name;
        return this;
    }

    public String getAddress() {
        return Address;
    }

    public Local setAddress(String address) {
        Address = address;
        return this;
    }

    public Integer getCapacity() {
        return Capacity;
    }

    public Local setCapacity(Integer capacity) {
        Capacity = capacity;
        return this;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public Local setLatitude(Double latitude) {
        Latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public Local setLongitude(Double longitude) {
        Longitude = longitude;
        return this;

    }

    public Integer getProviderId() {
        return ProviderId;
    }

    public Local setProviderId(Integer providerId) {
        ProviderId = providerId;
        return this;
    }

    public String getFirstPhotoUrl() {
        return FirstPhotoUrl;
    }

    public Local setFirstPhotoUrl(String firstPhotoUrl) {
        FirstPhotoUrl = firstPhotoUrl;
        return this;
    }

    public String getStatus() {
        return Status;
    }

    public Local setStatus(String status) {
        Status = status;
        return this;
    }

    public void setGallery(List<GenericPhoto> gallery) {
        Gallery = gallery;
    }

    public static Local buildFromJSONObject(JSONObject jsonService){
        Local local = new Local();
        try{
            local.setName(jsonService.getString("Name"));
            local.setAddress(jsonService.getString("Address"));
            local.setName(jsonService.getString("Name"));
            local.setCapacity(jsonService.getInt("Capacity"));
            local.setLatitude(jsonService.getDouble("Latitude"));
            local.setLongitude(jsonService.getDouble("Longitude"));
            local.setProviderId(jsonService.getInt("ProviderId"));
            local.setFirstPhotoUrl(jsonService.getString("FirstPhotoUrl"));
            local.setStatus(jsonService.getString("Status"));
            return local;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<Local> buildFromJSONArray(JSONArray jsonResult){
        List<Local> locals = new ArrayList<>();
        int localsCount = jsonResult.length();
        for(int i = 0 ; i < localsCount; i++) {
            try{
                locals.add(buildFromJSONObject(jsonResult.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return locals;
    }

    public static Local buildFromBundle(Bundle bundle) {
        Local local = new Local();
        local.setName(bundle.getString("Name"))
                .setAddress(bundle.getString("Address"))
                .setCapacity(bundle.getInt("Capacity"))
                .setLatitude(bundle.getDouble("Latitude"))
                .setLongitude(bundle.getDouble("Longitude"))
                .setProviderId(bundle.getInt("ProviderId"))
                .setFirstPhotoUrl(bundle.getString("FirstPhotoUrl"))
                .setStatus(bundle.getString("Status"));
        return local;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("Name", this.getName());
        bundle.putString("Address", this.getAddress());
        bundle.putInt("Capacity", this.getCapacity());
        bundle.putDouble("Latitude", this.getLatitude());
        bundle.putDouble("Longitude", this.getLongitude());
        bundle.putInt("ProviderId", this.getProviderId());
        bundle.putString("FirstPhotoUrl", this.getFirstPhotoUrl());
        bundle.putString("Status", this.getStatus());
        return bundle;

    }
}
