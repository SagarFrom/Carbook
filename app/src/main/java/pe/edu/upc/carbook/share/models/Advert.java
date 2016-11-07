package pe.edu.upc.carbook.share.models;

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
    public String Description;
    public String Quotation;
    public String CreationDate;
    public String EndDate;
    public String SelectionDate;
    public String Comments;
    public String Ranking;
    public String Status;
    public String FirstPhotoUrl;
    public List<GalleryAdvert> Gallery;
    public String CantApplications;

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

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
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

    public static Advert buildFromJSONObject(JSONObject jsonAdvert){
        Advert advert = new Advert();
        try{
            advert.setAdvertId(jsonAdvert.getString("AdvertId"));
            advert.setClientId(jsonAdvert.getString("ClientId"));
            advert.setProviderId(jsonAdvert.getString("ProviderId"));
            advert.setDescription(jsonAdvert.getString("Description"));
            advert.setQuotation(jsonAdvert.getString("Quotation"));
            advert.setCreationDate(jsonAdvert.getString("CreationDate"));
            advert.setEndDate(jsonAdvert.getString("EndDate"));
            advert.setComments(jsonAdvert.getString("Comments"));
            advert.setRanking(jsonAdvert.getString("Ranking"));
            advert.setStatus(jsonAdvert.getString("Status"));
            advert.setFirstPhotoUrl(jsonAdvert.getString("FirstPhotoUrl"));
            advert.setCantApplications("1");
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

}
