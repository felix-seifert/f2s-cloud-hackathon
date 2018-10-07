package conti.hackteam2.test.controller;

import io.crossbar.autobahn.utils.ABLogger;
import io.crossbar.autobahn.utils.IABLogger;
import io.crossbar.autobahn.wamp.Client;
import io.crossbar.autobahn.wamp.Session;
import io.crossbar.autobahn.wamp.types.CallResult;
import io.crossbar.autobahn.wamp.types.ExitInfo;
import io.crossbar.autobahn.wamp.types.SessionDetails;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


public class TestController {
    private static final IABLogger LOGGER = ABLogger.getLogger(TestController.class.getName());

    private final Executor executor;

    private final Session session;

    public TestController(Executor executor) {

        this.executor = executor;
        session = new Session(executor);

        // When the session joins a realm, run the code
        session.addOnJoinListener(this::callNotifyPassenger);
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

    /**
     * Simulates the frontend calling conti.hackteam2.notify_passenger with a given passenger ID and a given event ID
     *
     * Prints the result.
     *
     * @param session
     * @param details
     */
    private void callNotifyPassenger(Session session, SessionDetails details) {

        int passengerID = 11;
        int eventID = 2;

        CompletableFuture<CallResult> callNotifyPassengerCompletableFuture =
                session.call("conti.hackteam2.notify_passenger", passengerID, eventID);
        callNotifyPassengerCompletableFuture.whenComplete((callResult, throwable) -> {
            if (throwable == null) {
                LOGGER.i("Result: " + callResult.results);
            } else {
                LOGGER.i(String.format("ERROR - call failed: %s", throwable.getMessage()));
            }
        });
    }
}
