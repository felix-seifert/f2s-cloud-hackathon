package conti.hackteam2.database.controller;

import conti.hackteam2.database.model.Event;
import conti.hackteam2.database.service.DatabaseService;
import io.crossbar.autobahn.utils.ABLogger;
import io.crossbar.autobahn.utils.IABLogger;
import io.crossbar.autobahn.wamp.Client;
import io.crossbar.autobahn.wamp.Session;
import io.crossbar.autobahn.wamp.types.ExitInfo;
import io.crossbar.autobahn.wamp.types.InvocationDetails;
import io.crossbar.autobahn.wamp.types.Registration;
import io.crossbar.autobahn.wamp.types.SessionDetails;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


public class DatabaseController {
    private static final IABLogger LOGGER = ABLogger.getLogger(DatabaseController.class.getName());

    private final Executor executor;

    private final Session session;

    private DatabaseService database;

    public DatabaseController(Executor executor) {

        this.executor = executor;
        session = new Session(executor);

        // When the session joins a realm, run the code
        session.addOnJoinListener(this::registerFindAllEvents);
        session.addOnJoinListener(this::registerFindEventByID);
        session.addOnJoinListener(this::registerDeletePassengerByIDAndEventID);
    }

    public int start(String url, String realm) {

        LOGGER.i("Create database and set sample data");

        database = new DatabaseService();
        database.setEventList(database.createSampleData());

        LOGGER.i(String.format("Called with url=%s, realm=%s", url, realm));

        // finally, provide everything to a Client instance
        Client client = new Client(session, url, realm, executor);
        CompletableFuture<ExitInfo> exitFuture = client.connect();
        try {
            ExitInfo exitInfo = exitFuture.get();
            return exitInfo.code;
        } catch (Exception e) {
            LOGGER.e(e.getMessage());
            return 1;
        }
    }

    private void registerFindAllEvents(Session session, SessionDetails details) {
        CompletableFuture<Registration> registrationCompletableFuture = session.register(
                "conti.hackteam2.find_all_events", this::findAllEvents);
        registrationCompletableFuture.thenAccept(registration ->
                LOGGER.i("Procedure registered: " + registration.procedure));
    }

    private List<Event> findAllEvents(List<Object> args, InvocationDetails details) {
        List<Event> response = database.findAllEvents().get();
        LOGGER.i("Return: " + response);
        return response;
    }

    private void registerFindEventByID(Session session, SessionDetails details) {
        CompletableFuture<Registration> registrationCompletableFuture = session.register(
                "conti.hackteam2.find_event", this::findEventByID);
        registrationCompletableFuture.thenAccept(registration ->
                LOGGER.i("Procedure registered: " + registration.procedure));
    }

    private Event findEventByID(List<Integer> args, InvocationDetails details) {
        Event response = database.findEventByID(args.get(0)).get();
        LOGGER.i("Return: " + response);
        return response;
    }

    private void registerDeletePassengerByIDAndEventID(Session session, SessionDetails details) {
        CompletableFuture<Registration> registrationCompletableFuture = session.register(
                "conti.hackteam2.delete_passenger", this::deletePassengerByIDAndEventID);
        registrationCompletableFuture.thenAccept(registration ->
                LOGGER.i("Procedure registered: " + registration.procedure));
    }

    private Event deletePassengerByIDAndEventID(List<Integer> args, InvocationDetails details) {
        LOGGER.i(String.format("Delete passenger with the ID %d from the event with the ID %d",
                args.get(0), args.get(1)));
        Event response = null;
        try {
            response = database.deletePassengerByIDAndEventID(args.get(0), args.get(1)).get();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        }
        LOGGER.i("Return: " + response);
        return response;
    }
}
