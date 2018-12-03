package com.acc.cloud.ms.event;

import com.acc.cloud.ms.domain.Friend;

/**
 * An event that encapsulates a state transition for the {@link Friend}
 * domain object.
 *
 * @link Laxminarayan Rajput
 */
public class FriendEvent {

    private Friend subject;
    private EventType eventType;

    public FriendEvent() {
    }

    public FriendEvent(Friend subject, EventType eventType) {
        this.subject = subject;
        this.eventType = eventType;
    }

    public Friend getSubject() {
        return subject;
    }

    public void setSubject(Friend subject) {
        this.subject = subject;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
