///////////////////////////////////////////////////////////////////////////////
//
//   AutobahnJava - http://crossbar.io/autobahn
//
//   Copyright (c) Crossbar.io Technologies GmbH and contributors
//
//   Licensed under the MIT License.
//   http://www.opensource.org/licenses/mit-license.php
//
///////////////////////////////////////////////////////////////////////////////

package conti.hackteam2.notification;

import conti.hackteam2.notification.controller.NotificationController;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class NotificationMain {

    private static final String LOG_CONFIG = "handlers= java.util.logging.ConsoleHandler\n"
            + ".level = %s\n" +
            "java.util.logging.ConsoleHandler.level = %s\n" +
            "java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter\n";

    private static final Logger LOGGER = Logger.getLogger(NotificationMain.class.getName());

    private static final Properties APPLICATION_PROPERTIES = loadProperties("application.properties");

    public static void main(String[] args) throws IOException {

        // readAndSetLogLevel();    // Optional for getting a more detailed logging

        Executor executor = Executors.newSingleThreadExecutor();

        // Address of the crossbar router retrieved from application.properties
        String url = APPLICATION_PROPERTIES.getProperty("connection.url");
        String realm = APPLICATION_PROPERTIES.getProperty("connection.realm");

        NotificationController notificationController = new NotificationController(executor);

        LOGGER.info("notificationController.start()");
        int returnCode = notificationController.start(url, realm);

        LOGGER.info(String.format(".. ended with return code %s", returnCode));
        System.exit(returnCode);
    }

    private static void readAndSetLogLevel() throws IOException {
        String logLevel = System.getProperty("logLevel", "FINEST");
        String config = String.format(LOG_CONFIG, logLevel, logLevel);
        InputStream stream = new ByteArrayInputStream(config.getBytes(Charset.forName("UTF-8")));
        LogManager.getLogManager().readConfiguration(stream);
    }

    private static Properties loadProperties(String fileName) {

        String filePath = "target/classes/" + fileName;

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
