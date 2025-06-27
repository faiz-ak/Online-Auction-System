package com.auction.util;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.logging.Logger;
import com.auction.logger.AuctionLoggerConfig;

public class LogArchiver {
    private static final Logger LOGGER = AuctionLoggerConfig.getLogger();
    private static final String LOG_DIR = "logs/current";
    private static final String ARCHIVE_DIR = "logs/archive";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void archiveLogs() {
        try {
            // Create archive directory
            Files.createDirectories(Paths.get(ARCHIVE_DIR));

            // Get current date for timestamped folder
            String date = LocalDate.now().format(DATE_FORMATTER);
            String archiveFolder = ARCHIVE_DIR + "/" + date;
            Files.createDirectories(Paths.get(archiveFolder));

            // Create ZIP file
            String zipFileName = archiveFolder + "/logs_" + date + ".zip";
            Path logDirPath = Paths.get(LOG_DIR);

            if (!Files.exists(logDirPath)) {
                LOGGER.warning("Log directory does not exist: " + LOG_DIR);
                return;
            }

            try (FileOutputStream fos = new FileOutputStream(zipFileName);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                Files.list(logDirPath)
                        .filter(path -> path.toString().endsWith(".log"))
                        .forEach(path -> {
                            try {
                                ZipEntry zipEntry = new ZipEntry(path.getFileName().toString());
                                zos.putNextEntry(zipEntry);
                                byte[] bytes = Files.readAllBytes(path);
                                zos.write(bytes);
                                zos.closeEntry();
                                Files.delete(path); // Delete original file
                                LOGGER.info("Archived: " + path.getFileName());
                            } catch (IOException e) {
                                LOGGER.severe("Error archiving " + path.getFileName() + ": " + e.getMessage());
                            }
                        });

                zos.finish();
                LOGGER.info("Created archive: " + zipFileName);
            }
        } catch (IOException e) {
            LOGGER.severe("Archival failed: " + e.getMessage());
        }
    }
}