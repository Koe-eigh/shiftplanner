package com.shiftplanner.solver.app.db.scheduling.models;

import java.util.Objects;

import com.shiftplanner.solver.core.scheduling.values.Timeslot;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Embeddable
public class TimeslotJpaModel {
  @Column(name = "timeslot_year", nullable = false)
  private int year;
  @Column(name = "timeslot_month", nullable = false)
  private int month;
  @Column(name = "timeslot_day", nullable = false)
  private int day;
  @Column(name = "timeslot_hour", nullable = false)
  private int hour;
  @Column(name = "timeslot_minute", nullable = false)
  private int minute;
  @Column(name = "timeslot_second", nullable = false)
  private int second;
  @Column(name = "timeslot_is_closed", nullable = false)
  private boolean isClosed;

  public TimeslotJpaModel() {
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getYear() {
    return year;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getMonth() {
    return month;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public int getDay() {
    return day;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public int getHour() {
    return hour;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }

  public int getMinute() {
    return minute;
  }

  public void setSecond(int second) {
    this.second = second;
  }

  public int getSecond() {
    return second;
  }

  public void setClosed(boolean isClosed) {
    this.isClosed = isClosed;
  }

  public boolean isClosed() {
    return isClosed;
  }

  public static TimeslotJpaModel fromCoreObject(Timeslot timeslot) {
    TimeslotJpaModel jpaModel = new TimeslotJpaModel();
    jpaModel.setYear(timeslot.getSlot().getYear());
    jpaModel.setMonth(timeslot.getSlot().getMonth().getValue());
    jpaModel.setDay(timeslot.getSlot().getDayOfMonth());
    jpaModel.setHour(timeslot.getSlot().getHour());
    jpaModel.setMinute(timeslot.getSlot().getMinute());
    jpaModel.setSecond(timeslot.getSlot().getSecond());
    jpaModel.setClosed(timeslot.isClosed());
    return jpaModel;
  }

  public Timeslot toCoreObject() {
    return new Timeslot(LocalDateTime.of(year, month, month, hour, minute, second), this.isClosed);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || this.getClass() != obj.getClass())
      return false;
    TimeslotJpaModel other = (TimeslotJpaModel) obj;
    return this.year == other.year && this.month == other.month && this.day == other.day && this.hour == other.hour
        && this.minute == other.minute && this.second == other.second && this.isClosed == other.isClosed;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.year, this.month, this.day, this.hour, this.minute, this.second, this.isClosed);
  }
}
