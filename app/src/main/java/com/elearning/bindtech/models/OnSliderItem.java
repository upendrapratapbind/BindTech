package com.elearning.bindtech.models;

import android.net.Uri;

import com.elearning.bindtech.interfaces.DTO;


public class OnSliderItem implements DTO {

    public int drawableImageId;//drawble images id
    public String imagePath; //from server file url
    public String heading;
    public String id;
    public Uri imageUri; //locally saved file uri (pictures/roots directory)
    public String description;

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public int getDrawableImageId() {
        return drawableImageId;
    }

    public void setDrawableImageId(int drawableImageId) {
        this.drawableImageId = drawableImageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}