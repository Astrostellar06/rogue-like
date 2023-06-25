package roguelike.utils;

import roguelike.game.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FileConfiguration {

    public static void save() {
        try {
            String fileName = "datas\\save.data";
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Constants.data);

            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
        Constants.data = new Data();
        try {
            File f1 = new File("datas\\save.data");
            if (!f1.exists()) f1.createNewFile();
            String fileName;
            FileInputStream fin = null;
            ObjectInputStream ois = null;
            if (f1.length() != 0) {
                fileName = "datas\\save.data";
                fin = new FileInputStream(fileName);
                ois = new ObjectInputStream(fin);
                Constants.data = (Data) ois.readObject();
            }

            if (ois != null) ois.close();
            if (fin != null) fin.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
