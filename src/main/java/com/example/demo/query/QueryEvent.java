package com.example.demo.query;

import com.example.demo.enums.EventType;

public class QueryEvent
{

    private EventType eventType;
    private String id;

    public QueryEvent(){}
    public QueryEvent(EventType eventType, String id) {
        this.eventType = eventType;
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
