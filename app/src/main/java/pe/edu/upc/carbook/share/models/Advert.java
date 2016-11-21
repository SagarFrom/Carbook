package pe.edu.upc.carbook.share.models;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by usuario on 4/11/2016.
 */

public class Advert {

    public String AdvertId;
    public String ProviderId;
    public String ClientId;
    public String ClientName;
    public String CarId;
    public String CarInfo;
    public String Description;
    public String Quotation;
    public String CreationDate;
    public String EndDate;
    public String SelectionDate;
    public String Comments;
    public String ClientRating;
    public String Status;
    public String FirstPhotoUrl;
    public List<GalleryAdvert> Gallery;
    public String CantApplications;
    public String PostulationStatus;

    public Advert() {
        Gallery = new ArrayList<>();
    }


    public String getAdvertId() {
        return AdvertId;
    }

    public void setAdvertId(String advertId) {
        AdvertId = advertId;
    }

    public String getProviderId() {
        return ProviderId;
    }

    public void setProviderId(String providerId) {
        ProviderId = providerId;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQuotation() {
        return Quotation;
    }

    public void setQuotation(String quotation) {
        Quotation = quotation;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getSelectionDate() {
        return SelectionDate;
    }

    public void setSelectionDate(String selectionDate) {
        SelectionDate = selectionDate;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getClientRating() {
        return ClientRating;
    }

    public void setClientRating(String clientRating) {
        ClientRating = clientRating;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFirstPhotoUrl() {
        return FirstPhotoUrl;
    }

    public void setFirstPhotoUrl(String firstPhotoUrl) {
        FirstPhotoUrl = firstPhotoUrl;
    }

    public List<GalleryAdvert> getGallery() {
        return Gallery;
    }

    public void setGallery(List<GalleryAdvert> gallery) {
        Gallery = gallery;
    }

    public String getCantApplications() {
        return CantApplications;
    }

    public void setCantApplications(String cantApplications) {
        CantApplications = cantApplications;
    }

    public String getCarId() {
        return CarId;
    }

    public void setCarId(String carId) {
        CarId = carId;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getCarInfo() {
        return CarInfo;
    }

    public void setCarInfo(String carInfo) {
        CarInfo = carInfo;
    }

    public String getPostulationStatus() {
        return PostulationStatus;
    }

    public void setPostulationStatus(String postulationStatus) {
        PostulationStatus = postulationStatus;
    }

    public static Advert buildFromJSONObject(JSONObject jsonAdvert){
        Advert advert = new Advert();
        try{
            advert.setAdvertId(jsonAdvert.getString("AdvertId"));
            advert.setClientId(jsonAdvert.getString("ClientId"));
            advert.setCarId(jsonAdvert.getString("CarId"));
            advert.setProviderId(jsonAdvert.getString("ProviderId"));
            advert.setDescription(jsonAdvert.getString("Description"));
            advert.setQuotation(jsonAdvert.getString("Quotation"));
            advert.setCreationDate(jsonAdvert.getString("CreationDate"));
            advert.setEndDate(jsonAdvert.getString("EndDate"));
            advert.setComments(jsonAdvert.getString("Comments"));
            advert.setClientRating(jsonAdvert.getString("ClientRating"));
            advert.setStatus(jsonAdvert.getString("Status"));
            advert.setFirstPhotoUrl(jsonAdvert.getString("FirstPhotoUrl"));
            advert.setCantApplications(jsonAdvert.getString("NPostulations"));
            advert.setClientName(jsonAdvert.getString("ClientName"));
            advert.setCarInfo(jsonAdvert.getString("CarInfo"));
            advert.setPostulationStatus(jsonAdvert.getString("PostulationStatus"));
            return advert;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<Advert> buildFromJSONArray(JSONArray jsonResult){
        List<Advert> adverts = new ArrayList<>();
        int adversCount = jsonResult.length();
        for(int i = 0 ; i < adversCount; i++) {
            try{
                adverts.add(buildFromJSONObject(jsonResult.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return adverts;
    }

    public static Advert buildFromBundle(Bundle bundle){
        Advert advert = new Advert();
        if(bundle != null){
            advert.setAdvertId(bundle.getString("advertId"));
            advert.setClientId(bundle.getString("clientId"));
            advert.setCarId(bundle.getString("carId"));
            advert.setProviderId(bundle.getString("providerId"));
            advert.setDescription(bundle.getString("description"));
            advert.setQuotation(bundle.getString("quotation"));
            advert.setCreationDate(bundle.getString("creationDate"));
            advert.setEndDate(bundle.getString("endDate"));
            advert.setComments(bundle.getString("comments"));
            advert.setClientRating(bundle.getString("clientRating"));
            advert.setStatus(bundle.getString("status"));
            advert.setFirstPhotoUrl(bundle.getString("firstPhotoUrl"));
            advert.setCantApplications(bundle.getString("cantApplications"));
            advert.setClientName(bundle.getString("clientName"));
            advert.setCarInfo(bundle.getString("carInfo"));
            advert.setPostulationStatus(bundle.getString("postulationStatus"));
        }
        return advert;
    }

    public Bundle toBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("advertId",this.getAdvertId());
        bundle.putString("clientId",this.getClientId());
        bundle.putString("carId",this.getCarId());
        bundle.putString("providerId",this.getProviderId());
        bundle.putString("description",this.getDescription());
        bundle.putString("quotation",this.getQuotation());
        bundle.putString("creationDate",this.getCreationDate());
        bundle.putString("endDate",this.getEndDate());
        bundle.putString("comments",this.getComments());
        bundle.putString("clientRating",this.getClientRating());
        bundle.putString("status",this.getStatus());
        bundle.putString("firstPhotoUrl",this.getFirstPhotoUrl());
        bundle.putString("cantApplications",this.getCantApplications());
        bundle.putString("clientName",this.getClientName());
        bundle.putString("carInfo",this.getCarInfo());
        bundle.putString("postulationStatus",this.getPostulationStatus());
        return bundle;
    }
}
