package com.example.demo.Query;

import com.example.demo.EventType;

public class QueryClosedEvent extends QueryEvent {

    public QueryClosedEvent(EventType eventType, String id) {
        super(eventType, id);
    }

}
