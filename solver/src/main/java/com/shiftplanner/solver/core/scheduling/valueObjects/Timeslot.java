package com.shiftplanner.solver.core.scheduling.valueObjects;

import java.time.LocalDateTime;
import java.util.Objects;

public class Timeslot implements Comparable<Timeslot> {
    private LocalDateTime slot;
    private boolean isClosed;

    public Timeslot() {
    }

    public Timeslot(LocalDateTime slot, boolean isClosed) {
        this.slot = slot;
        this.isClosed = isClosed;
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public LocalDateTime getSlot() {
        return this.slot;
    }

    public boolean isAfter(Timeslot other) {
        return this.slot.isAfter(other.slot);
    }

    @Override
    public int compareTo(Timeslot other) {
        return this.slot.compareTo(other.slot);
    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "slot=" + slot +
                ", isClosed=" + isClosed +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Timeslot timeslot = (Timeslot) obj;
        return this.slot.equals(timeslot.slot) && this.isClosed == timeslot.isClosed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.slot, this.isClosed);
    }
}