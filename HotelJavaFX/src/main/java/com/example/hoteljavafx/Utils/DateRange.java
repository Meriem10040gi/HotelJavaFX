package com.example.hoteljavafx.Utils;


import java.sql.Date;

public class DateRange {
    private Date dateDebut;
    private Date dateFin;

    public DateRange(Date dateDebut, Date dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }


    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }
}
