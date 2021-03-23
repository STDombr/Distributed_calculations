import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class Utils {

    private final static List<String> NAME_LIST = new ArrayList<>(Arrays.asList("Bennett", "Cooper", "Jordan", "Gibson", "Lopez", "King", "Harrison", "Morris", "Henry", "Hutchinson", "Arnold"));
    private final static List<String> PHONE_LIST = new ArrayList<>(Arrays.asList("0501111111", "0676767676", "0999999999", "0503991322", "0664311902", "0500000000", "0635059912", "0777777777", "0508001210", "0633333333", "0672222222"));

    public static String pickOutThePhone(String record) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < record.length(); i++) {
            if (isDigit(record.charAt(i))) {
                output.append(record.charAt(i));
            }
        }
        return output.toString();
    }

    public static String pickOutTheSurname(String record) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < record.length(); i++) {
            if (isLetter(record.charAt(i))) {
                output.append(record.charAt(i));
            }
        }
        return output.toString();
    }

    public static void startProcess(PhonesManager phonesManager) {
        Random rand = new Random();

        Runnable findPhoneRunnable = () -> {
            while (true) {
                int nameIndex = rand.nextInt(NAME_LIST.size());
                String nameToFind = NAME_LIST.get(nameIndex);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String phone = null;
                try {
                    phone = phonesManager.findPhone(nameToFind);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread from group [1] found the phone of *" + nameToFind + "*: " + phone);
            }
        };

        Thread findPhone1 = new Thread(findPhoneRunnable);
        Thread findPhone2 = new Thread(findPhoneRunnable);

        Runnable findSurnameRunnable = () -> {
            while (true) {
                int nameIndex = rand.nextInt(PHONE_LIST.size());
                String phoneToFind = PHONE_LIST.get(nameIndex);

                try {
                    Thread.sleep(3500);
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String surname = null;

                try {
                    surname = phonesManager.findSurname(phoneToFind);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread from group [2] found the surname of *" + phoneToFind + "*: " + surname);
            }
        };

        Thread findSurname1 = new Thread(findSurnameRunnable);
        Thread findSurname2 = new Thread(findSurnameRunnable);

        Thread addRecords = new Thread(() -> {
            while (true) {
                int indexPhone = rand.nextInt(PHONE_LIST.size());
                String phoneToAdd = PHONE_LIST.get(indexPhone);
                int indexSurname = rand.nextInt(NAME_LIST.size());
                String surnameToAdd = NAME_LIST.get(indexSurname);

                try {
                    Thread.sleep(1500);
                   } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    phonesManager.addRecord(surnameToAdd, phoneToAdd);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread from group [3] added a new record: * " + surnameToAdd + " * - * " + phoneToAdd + " *");

            }
        });

        Thread deleteRecords = new Thread(() -> {
            while (true) {
                int indexToDelete = rand.nextInt(phonesManager.getCurrPhonesSize());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String deletedRecord = null;
                try {
                    deletedRecord = phonesManager.deleteRecord(indexToDelete);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread from group [3] deleted the record: * " + deletedRecord + " *");
            }
        });

        findPhone1.start();
        findPhone2.start();
        findSurname1.start();
        findSurname2.start();
        addRecords.start();
        deleteRecords.start();
    }
}
