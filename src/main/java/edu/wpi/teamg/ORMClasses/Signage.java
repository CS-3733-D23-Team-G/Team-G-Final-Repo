package edu.wpi.teamg.ORMClasses;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class Signage {
    @Getter @Setter int kioskNum;
    @Getter @Setter Date date;
    @Getter @Setter String arrow;

    public Signage(){}
    public Signage(int kioskNum, Date date, String arrow){
        this.kioskNum=kioskNum;
        this.date=date;
        this.arrow=arrow;
    }
}
