package com.example.demo.query;

import com.example.demo.enums.EventType;

public class QueryClosedEvent extends QueryEvent {

    public QueryClosedEvent(EventType eventType, String id) {
        super(eventType, id);
    }

}
