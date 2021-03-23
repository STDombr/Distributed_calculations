import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PhonesManager {
    private int currPhonesSize;
    private final Path filePath;
    private final ReadWriteLock rwLock;

    public PhonesManager(String path) {
        filePath = Paths.get(path);
        currPhonesSize = 5;
        rwLock = new ReadWriteLock();
    }

    public int getCurrPhonesSize() {
        return currPhonesSize;
    }

    public String findPhone(String surname) throws IOException, InterruptedException {
        rwLock.acquireReadLock();
        List<String> records = Files.readAllLines(filePath);
        String result = "No such a surname";

        for (String record : records) {
            if (record.contains(surname)) {
                result = Utils.pickOutThePhone(record);
            }
        }

        rwLock.releaseReadLock();
        return result;
    }

    public String findSurname(String phone) throws IOException, InterruptedException {
        rwLock.acquireReadLock();
        List<String> records = Files.readAllLines(filePath);
        String result = "No such a surname";

        for (String record : records) {
            if (record.contains(phone)) {
                result = Utils.pickOutTheSurname(record);
            }
        }

        rwLock.releaseReadLock();
        return result;
    }

    public void addRecord(String surname, String phone) throws IOException, InterruptedException {
        rwLock.acquireWriteLock();
        String record = surname + " - " + phone + '\n';
        BufferedWriter writer = new BufferedWriter(new FileWriter(Main.path, true));
        writer.append(record);

        writer.close();
        currPhonesSize++;

        rwLock.releaseWriteLock();
    }

    public String deleteRecord(int row) throws IOException, InterruptedException {
        rwLock.acquireWriteLock();

        if(row <= 0 || row >= this.currPhonesSize) {
            row = this.currPhonesSize - 1;
        }

        List<String> records = Files.readAllLines(filePath);
        List<String> newRecords = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            if (i != row) {
                newRecords.add(records.get(i));
            }
        }

        StringBuilder output = new StringBuilder();

        for (String record : newRecords) {
            output.append(record);
            output.append('\n');
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(Main.path, false));
        writer.write(output.toString());

        writer.close();
        currPhonesSize--;

        rwLock.releaseWriteLock();

        return records.get(row);
    }
}
