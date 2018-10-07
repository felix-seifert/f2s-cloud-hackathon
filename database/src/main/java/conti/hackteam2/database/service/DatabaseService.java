package conti.hackteam2.database.service;

import conti.hackteam2.database.model.Event;
import conti.hackteam2.database.model.Passenger;
import lombok.Setter;

import javax.management.InstanceNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DatabaseService {

    @Setter
    private List<Event> eventList;

    public DatabaseService() {
        this.eventList = new LinkedList<>();
    }

    public Optional<List<Event>> findAllEvents() {
        return Optional.ofNullable(eventList);
    }

    public Optional<Event> findEventByID(int id) {
        return eventList.stream().filter(e -> e.getId() == id).findFirst();
    }

    public Optional<Passenger> findPassengerByIDAndEventID(int passengerID, int eventID)
            throws InstanceNotFoundException {
        Optional<Event> event = findEventByID(eventID);
        if(!event.isPresent()) { throw new InstanceNotFoundException("Event ID does not exists."); }

        return event.get().getPassengers().stream().filter(p -> p.getId() == passengerID).findFirst();
    }

    public Optional<Event> deletePassengerByIDAndEventID(int passengerID, int eventID) throws InstanceNotFoundException {
        Optional<Event> event = findEventByID(eventID);
        if(!event.isPresent()) { throw new InstanceNotFoundException("Event ID does not exists."); }

        Passenger passenger = findPassengerByIDAndEventID(passengerID, eventID).get();

        event.get().getPassengers().remove(passenger);

        return event;
    }

    public List<Event> createSampleData() {

        Event event1 = new Event(1, "Movie at Regensburg - Ostentor Kino",
                "Adolf-Schmetzer-Straße 5, 93055 Regensburg", 49.018029, 12.109601,
                "2018-09-29T09:24:17Z", createSamplePassengerList());

        Event event2 = new Event(2, "Museum at Regensburg - Historisches Museum Regensburg",
                "Dachaupl. 2-4, 93047 Regensburg", 49.017902,12.102091,
                "2018-09-29T09:24:17Z", createSamplePassengerList());

        Event event3 = new Event(3, "Asian dinner at Regensburg - VyVu Vietnam Asia Cuisine",
                "Prinz-Ludwig-Straße 9, 93055 Regensburg", 49.016378, 12.120169,
                "2018-09-29T09:24:17Z", createSamplePassengerList());

        Event event4 = new Event(4, "Shopping Mall at Regensburg - Regensburg Arcaden",
                "Friedenstraße 23, 93053 Regensburg", 49.010807, 12.099262,
                "2018-09-29T09:24:17Z", createSamplePassengerList());

        Event event5 = new Event(5, "Coffee shop is near you - BLACK BEAN - The Coffee Company",
                "Gesandtenstraße 3, 93047 Regensburg", 49.018524, 12.093211,
                "2018-09-29T09:24:17Z", createSamplePassengerList());

        List<Event> eventList = new LinkedList<>();
        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);
        eventList.add(event4);
        eventList.add(event5);

        return eventList;
    }

    private List<Passenger> createSamplePassengerList() {

        Passenger passenger1 = new Passenger(10, "John Gera", 23, 49.018663, 12.087172);
        Passenger passenger2 = new Passenger(11, "Carin Shed", 21, 49.021884, 12.104307);
        Passenger passenger3 = new Passenger(12, "Laura Gaan", 27, 49.004505, 12.122860);
        Passenger passenger4 = new Passenger(13, "David Fo", 24, 49.012991, 12.054065);

        List<Passenger> passengerList = new LinkedList<>();
        passengerList.add(passenger1);
        passengerList.add(passenger2);
        passengerList.add(passenger3);
        passengerList.add(passenger4);

        return passengerList;
    }
}
