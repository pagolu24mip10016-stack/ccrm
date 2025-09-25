package edu.ccrm.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

    public static List<String[]> readCsv(Path filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(values);
            }
        }
        return records;
    }

    public static void writeCsv(Path filePath, List<String[]> data) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            for (String[] record : data) {
                bw.write(String.join(",", record));
                bw.newLine();
            }
        }
    }
}
