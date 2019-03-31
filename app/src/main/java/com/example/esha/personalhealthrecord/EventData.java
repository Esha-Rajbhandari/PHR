package com.example.esha.personalhealthrecord;

public class EventData {
    private String eventTitle;
    private String eventData;

    public EventData(String eventTitle, String eventData) {
        this.eventTitle = eventTitle;
        this.eventData = eventData;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }
}
