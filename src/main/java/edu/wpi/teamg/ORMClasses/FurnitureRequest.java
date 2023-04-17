package edu.wpi.teamg.ORMClasses;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

public class FurnitureRequest extends Request {
    @Getter @Setter
    private String furnitureType;
    @Getter @Setter
    private String color;
    @Getter @Setter
    private String note;

    public FurnitureRequest(String reqtype,
                            int empid,
                            String location,
                            int serveBy,
                            StatusTypeEnum status,
                            Date deliveryDate,
                            Time deliveryTime,
                            String furnitureType,
                            String color,
                            String note) {
        super(reqtype, empid, location, serveBy, status, deliveryDate, deliveryTime);
        this.furnitureType = furnitureType;
        this.color = color;
        this.note = note;
    }
}
