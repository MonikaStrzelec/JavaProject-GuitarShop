package SklepZGitarami;

import SklepZGitarami.modele.Gitary;
import SklepZGitarami.modele.SprzedaneGitary;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.joda.time.LocalDateTime;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlikiUtils {

    private static final String NAZWA_PLIKU = "sprzedane.json";

    private static final Type TYPY_JSON = new TypeToken<List<SprzedaneGitary>>() {
    }.getType();

    private static List<SprzedaneGitary> odczytajSprzedaneGitaryZPliku() {
        Gson gson = new Gson();

        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(NAZWA_PLIKU));
            List<SprzedaneGitary> gitary = gson.fromJson(bufferedReader, TYPY_JSON);
            if (gitary != null) return gitary;
            else return new ArrayList<>();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }


    }

    private static String konwertujCzas() {
        LocalDateTime czas = new LocalDateTime();
        return czas.getDayOfMonth() + " " + czas.getMonthOfYear() + " " + czas.getYear() + " " + czas.getHourOfDay() + " " + czas.getMinuteOfHour();
    }

    public static void dodajSprzedanaGitare(Gitary gitara) {
        List<SprzedaneGitary> sprzedaneGitary = odczytajSprzedaneGitaryZPliku();
        sprzedaneGitary.add(new SprzedaneGitary(gitara, konwertujCzas()));
        Gson gson = new Gson();
        try {
            Writer writer = new FileWriter(NAZWA_PLIKU);
            gson.toJson(sprzedaneGitary.toArray(), writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
