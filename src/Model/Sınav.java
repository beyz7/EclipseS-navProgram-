package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Sınav {
    private int id;
    private int dersId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int salonId;
    private int gozetmenId;

    
    public Sınav(int id, int dersId, LocalDate date, LocalTime startTime, LocalTime endTime, int salonId, int gozetmenId) {
        this.id = id;
        this.dersId = dersId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.salonId = salonId;
        this.gozetmenId = gozetmenId;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDersId() {
        return dersId;
    }

    public void setDersId(int dersId) {
        this.dersId = dersId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public int getGozetmenId() {
        return gozetmenId;
    }

    public void setGozetmenId(int gozetmenId) {
        this.gozetmenId = gozetmenId;
    }

    @Override
    public String toString() {
        return "Exam for Ders ID: " + dersId + " on " + date + " at " + startTime + " in Salon: " + salonId;
    }
}
