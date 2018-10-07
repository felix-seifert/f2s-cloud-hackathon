package conti.hackteam2.notification.controller;

import io.crossbar.autobahn.utils.ABLogger;
import io.crossbar.autobahn.utils.IABLogger;
import io.crossbar.autobahn.wamp.Client;
import io.crossbar.autobahn.wamp.Session;
import io.crossbar.autobahn.wamp.types.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;


public class NotificationController {
    private static final IABLogger LOGGER = ABLogger.getLogger(NotificationController.class.getName());

    private final Executor executor;

    private final Session session;

    public NotificationController(Executor executor) {

        this.executor = executor;
        session = new Session(executor);

        // When the session joins a realm, run the code
        session.addOnJoinListener(this::registerNotifyPassenger);
    }

    public int start(String url, String realm) {

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

    private void registerNotifyPassenger(Session session, SessionDetails details) {
        CompletableFuture<Registration> registerNotifyPassengerCompletableFuture= session.register(
                "conti.hackteam2.notify_passenger", this::notifyPassenger);
        registerNotifyPassengerCompletableFuture.thenAccept(registration ->
                LOGGER.i("Procedure registered: " + registration.procedure));
    }

    private List<Object> notifyPassenger(List<Integer> args, InvocationDetails details) {
        LOGGER.i(String.format("Notify the passenger with the ID %d from the event with the id %d",
                args.get(0), args.get(1)));

        // Here should happen the notification
        // Proper notification has to be implemented.

        LOGGER.i(String.format("Delete the passenger with the ID %d from the event with the id %d",
                args.get(0), args.get(1)));

        // Call the database to delete the given passenger and return the Event without the passenger
        CompletableFuture<CallResult> callDeletePassengerCompletableFuture =
                session.call("conti.hackteam2.delete_passenger", args.get(0), args.get(1));

        List<Object> response = null;
        try {
            response = callDeletePassengerCompletableFuture.get().results;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
