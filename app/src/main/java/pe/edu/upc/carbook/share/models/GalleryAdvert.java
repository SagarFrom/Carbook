package pe.edu.upc.carbook.share.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 4/11/2016.
 */

public class GalleryAdvert {

    public String AdvertGalleryId;
    public String AdvertId;
    public String Name;
    public String ImageUrl;
    public String Status;

    public GalleryAdvert() {
    }

    public String getAdvertGalleryId() {
        return AdvertGalleryId;
    }

    public void setAdvertGalleryId(String advertGalleryId) {
        AdvertGalleryId = advertGalleryId;
    }

    public String getAdvertId() {
        return AdvertId;
    }

    public void setAdvertId(String advertId) {
        AdvertId = advertId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public static GalleryAdvert buildFromJSONObject(JSONObject jsonGallery){
        GalleryAdvert galleryAdvert = new GalleryAdvert();
        try{
            galleryAdvert.setAdvertGalleryId(jsonGallery.getString("AdvertGalleryId"));
            galleryAdvert.setStatus(jsonGallery.getString("Status"));
            galleryAdvert.setAdvertId(jsonGallery.getString("AdvertId"));
            galleryAdvert.setName(jsonGallery.getString("Name"));
            galleryAdvert.setImageUrl(jsonGallery.getString("ImageUrl"));
            return galleryAdvert;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return galleryAdvert;
    }

    public static List<GalleryAdvert> buildFromJSONArray(JSONArray jsonResult){
        List<GalleryAdvert> galleryAdverts = new ArrayList<>();
        int galleryAdvertCount = jsonResult.length();
        for(int i = 0 ; i < galleryAdvertCount; i++) {
            try{
                galleryAdverts.add(buildFromJSONObject(jsonResult.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return galleryAdverts;
    }
}
