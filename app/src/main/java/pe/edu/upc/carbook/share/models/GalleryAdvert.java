package pe.edu.upc.carbook.share.models;

/**
 * Created by usuario on 4/11/2016.
 */

public class GalleryAdvert {

    public Integer AdvertGalleryId;
    public Integer AdvertId;
    public String Name;
    public String ImageUrl;
    public String Status;

    public GalleryAdvert() {
    }

    public Integer getAdvertGalleryId() {
        return AdvertGalleryId;
    }

    public void setAdvertGalleryId(Integer advertGalleryId) {
        AdvertGalleryId = advertGalleryId;
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
}
