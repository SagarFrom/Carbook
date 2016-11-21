package pe.edu.upc.carbook.share.models;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 20/11/2016.
 */

public class Postulation{
    private String postulationId;
    private String advertId;
    private String providerId;
    private String providerName;
    private String providerRank;
    private String quotation;
    private String status;
    private String postulationStatus;

    public Postulation() {
    }

    public String getPostulationId() {
        return postulationId;
    }

    public void setPostulationId(String postulationId) {
        this.postulationId = postulationId;

    }

    public String getAdvertId() {
        return advertId;
    }

    public void setAdvertId(String advertId) {
        this.advertId = advertId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderRank() {
        return providerRank;
    }

    public void setProviderRank(String providerRank) {
        this.providerRank = providerRank;
    }

    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(String quotation) {
        this.quotation = quotation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null? "":status;
    }

    public String getPostulationStatus() {
        return postulationStatus;
    }

    public void setPostulationStatus(String postulationStatus) {
        this.postulationStatus = postulationStatus == null?"":postulationStatus;
    }

    public static Postulation buildFromJSONObject(JSONObject jsonPostulation){
        Postulation postulation = new Postulation();
        try{
            postulation.setAdvertId(jsonPostulation.getString("AdvertId"));
            postulation.setPostulationId(jsonPostulation.getString("PostulationId"));
            postulation.setProviderId(jsonPostulation.getString("ProviderId"));
            postulation.setProviderName(jsonPostulation.getString("ProviderName"));
            postulation.setProviderRank(jsonPostulation.getString("ProviderRank"));
            postulation.setQuotation(jsonPostulation.getString("Quotation"));
            postulation.setStatus(jsonPostulation.getString("Status"));
            postulation.setPostulationStatus(jsonPostulation.getString("PostulationStatus"));
            return postulation;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return postulation;
    }

    public static List<Postulation> buildFromJSONArray(JSONArray jsonResult){
        List<Postulation> postulations = new ArrayList<>();
        int postulacionesCount = jsonResult.length();
        for(int i = 0 ; i < postulacionesCount; i++) {
            try{
                postulations.add(buildFromJSONObject(jsonResult.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return postulations;
    }

    public static Postulation buildFromBundle(Bundle bundle){
        Postulation postulation = new Postulation();
        if(bundle!=null){

        }
        return postulation;
    }
}
