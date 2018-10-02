package conti.hackteam2.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class Event {

    private int id;

    private String title;

    private String location;

    private double latitude;

    private double longitude;

    private String time;

    private List<Passenger> passengers;

    public Event() {
        this.passengers = new LinkedList<>();
    }
}
