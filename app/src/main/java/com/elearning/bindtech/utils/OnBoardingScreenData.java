package com.elearning.bindtech.utils;

import com.elearning.bindtech.models.OnSliderItem;


import java.io.Serializable;
import java.util.List;

public class OnBoardingScreenData implements Serializable {
    public String lastUpdatedOn;
    List<OnSliderItem> imageData;

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public List<OnSliderItem> getImageData() {
        return imageData;
    }

    public void setImageData(List<OnSliderItem> imageData) {
        this.imageData = imageData;
    }
}
