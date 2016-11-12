package pe.edu.upc.carbook.share.models;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 12/11/2016.
 */

public class GenericPhoto {
    private Integer GalleryId;
    private Integer FatherId;
    private String Name;
    private String ImageUrl;
    private String Status;

    public GenericPhoto() {
    }

    public GenericPhoto(Integer galleryId, Integer fatherId, String name, String imageUrl, String status) {
        GalleryId = galleryId;
        FatherId = fatherId;
        Name = name;
        ImageUrl = imageUrl;
        Status = status;
    }

    public Integer getGalleryId() {
        return GalleryId;
    }

    public GenericPhoto setGalleryId(Integer galleryId) {
        GalleryId = galleryId;
        return this;
    }

    public Integer getFatherId() {
        return FatherId;
    }

    public GenericPhoto setFatherId(Integer fatherId) {
        FatherId = fatherId;
        return this;
    }

    public String getName() {
        return Name;
    }

    public GenericPhoto setName(String name) {
        Name = name;
        return this;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public GenericPhoto setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
        return this;
    }

    public String getStatus() {
        return Status;
    }

    public GenericPhoto setStatus(String status) {
        Status = status;
        return this;
    }

    public static GenericPhoto buildFromJSONObject(JSONObject jsonService){
        GenericPhoto gallery = new GenericPhoto();
        try{
            gallery.setGalleryId(jsonService.getInt("GalleryId"));
            gallery.setFatherId(jsonService.getInt("FatherId"));
            gallery.setName(jsonService.getString("Name"));
            gallery.setImageUrl(jsonService.getString("ImageUrl"));
            gallery.setStatus(jsonService.getString("Status"));
            return gallery;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<GenericPhoto> buildFromJSONArray(JSONArray jsonResult){
        List<GenericPhoto> gallery = new ArrayList<>();
        int galleryCount = jsonResult.length();
        for(int i = 0 ; i < galleryCount; i++) {
            try{
                gallery.add(buildFromJSONObject(jsonResult.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return gallery;
    }

    public static GenericPhoto buildFromBundle(Bundle bundle) {
        GenericPhoto gallery = new GenericPhoto();
        gallery.setGalleryId(bundle.getInt("GalleryId"))
                .setFatherId(bundle.getInt("FatherId"))
                .setName(bundle.getString("Name"))
                .setImageUrl(bundle.getString("ImageUrl"))
                .setStatus(bundle.getString(""));
        return gallery;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("GalleryId", this.getGalleryId());
        bundle.putInt("FatherId", this.getFatherId());
        bundle.putString("Name", this.getName());
        bundle.putString("ImageUrl", this.getImageUrl());
        bundle.putString("Status", this.getStatus());
        return bundle;

    }
}
