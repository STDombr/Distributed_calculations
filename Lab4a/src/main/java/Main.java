public class Main {

    public static final String path = "src/main/java/phones.txt";

    public static void main(String[] args) {

        PhonesManager phonesManager = new PhonesManager(path);

        Utils.startProcess(phonesManager);

    }
}
