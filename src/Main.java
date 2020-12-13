import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    private final static String SAVE_DIRECTORY = "/Users/anvartdinovtimur/Documents/games/savegames/";

    public static void main(String[] args) {
        openZIP(SAVE_DIRECTORY + "saves.zip", SAVE_DIRECTORY);
        GameProgress game = openProgress(SAVE_DIRECTORY + "game1.dat");
        System.out.println(game);
    }

    private static void openZIP(String pathToArchive, String pathToUnzip) {
        try (ZipInputStream zin = new ZipInputStream(new
                FileInputStream(pathToArchive))) {
            ZipEntry entry;
            String name;

            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(pathToUnzip + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static GameProgress openProgress(String pathToSave) {
        GameProgress game = null;
        try (FileInputStream fis = new FileInputStream(pathToSave);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            game = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return game;
    }

}
