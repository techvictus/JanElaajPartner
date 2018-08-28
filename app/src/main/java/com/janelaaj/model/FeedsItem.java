package com.janelaaj.model;

/**
 * Created On 24-05-2018
 *
 * @author Narayan Semwal
 */
public class FeedsItem {

    private String eventText, nameText, addressText,dateText,eventButtonText;

    public FeedsItem(String eventText, String nameText, String addressText, String dateText, String eventButtonText) {
        this.eventText = eventText;
        this.nameText = nameText;
        this.addressText = addressText;
        this.dateText = dateText;
        this.eventButtonText = eventButtonText;
    }


    public String getEventText() {
        return eventText;
    }

    public void setEventText(String eventText) {
        this.eventText = eventText;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getEventButtonText() {
        return eventButtonText;
    }

    public void setEventButtonText(String eventButtonText) {
        this.eventButtonText = eventButtonText;
    }
}
