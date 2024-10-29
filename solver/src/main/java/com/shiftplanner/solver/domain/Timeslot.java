package com.shiftplanner.solver.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "timeslots")
public class Timeslot {
    @Id
    private String id;

    private LocalDateTime slot;
    
    private boolean closed;

    public Timeslot(){}

    public Timeslot(String id, LocalDateTime slot, boolean closed){
        this.id = id;
        this.slot = slot;
        this.closed = closed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getSlot() {
        return slot;
    }

    public void setSlot(LocalDateTime slot) {
        this.slot = slot;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        if(obj==null || obj.getClass()!=getClass()) return false;
        Timeslot timeslot = (Timeslot) obj;
        return this.slot.equals(timeslot.slot);
    }

    @Override
public int hashCode() {
    return slot != null ? slot.hashCode() : 0;
}
}
