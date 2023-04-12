package config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static config.GameConfig.CSV_HEADERS;
import static config.GameConfig.CSV_FILENAME;

public class GameExporter {
    public GameExporter(String[] data){
        File csvFile = new File(CSV_FILENAME);
        boolean csvExists = csvFile.exists();

        try {
            FileWriter csvWriter = new FileWriter(csvFile, true); // true for appending
            if (!csvExists) {
                for (int i = 0; i < CSV_HEADERS.length; i++) {
                    csvWriter.append(CSV_HEADERS[i]);
                    if (i != CSV_HEADERS.length - 1) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");
            }


            for (int i = 0; i < data.length; i++) {
                csvWriter.append(data[i]);
                if (i != data.length - 1) {
                    csvWriter.append(",");
                }
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }
}