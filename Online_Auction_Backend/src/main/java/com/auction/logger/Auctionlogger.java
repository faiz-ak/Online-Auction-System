//SHYAM

package com.auction.logger;

import java.util.logging.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Auctionlogger {
    private static final Logger LOGGER = Logger.getLogger(Auctionlogger.class.getName());
    private static final String LOG_DIR = "logs/current";
    private static final String LOG_FILE = LOG_DIR + "/auction.log";

    static {
        try {
            // Create logs directory if it doesn't exist
            Files.createDirectories(Paths.get(LOG_DIR));

            // Configure logger
            LOGGER.setLevel(Level.ALL);
            LOGGER.setUseParentHandlers(false); // Disable console output
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}