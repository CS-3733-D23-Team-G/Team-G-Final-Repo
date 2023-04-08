package edu.wpi.teamg.ORMClasses;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.sql.Time;

public class FlowerRequest extends Request{
    @Getter @Setter
    private String flowertype;
    @Getter @Setter
    private int numFlower;
    @Getter @Setter
    private String note;
    @Getter @Setter
    private String recipient;



}
