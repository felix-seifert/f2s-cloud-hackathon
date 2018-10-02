package conti.hackteam2.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Passenger {

    private int id;

    private String name;

    private int age;

    private double latitude;

    private double longitude;
}
