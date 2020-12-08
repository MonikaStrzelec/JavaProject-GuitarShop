package SklepZGitarami;

import SklepZGitarami.modele.Gitary;
import SklepZGitarami.modele.RodzajeGitarEnum;

import javax.transaction.SystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static SklepZGitarami.modele.RodzajeGitarEnum.*;

public class Main {

    public static final int EDYCJA_ASORtYMENTU = 3;
    private static final int DODAJ_NOWA_GITARE = 1;
    private static final int WYSWIETL_STAN_MAGAZYNU = 2;

    public static void main(String[] args) throws SystemException {
        HibernateUtil.inicjalizacjaListGitar();
        Scanner scan = new Scanner(System.in);
        System.out.println("Interesuje cię: \n obsługa klijenta (1) \n magazyn (2) \n sprzedaż (3) \n Wybierz interesującą cię opcję.");
        int wybor = scan.nextInt();

        switch (wybor) {
            case 1: //KLIJENT
                System.out.println("co klijent poszukuje: ");
                System.out.println("konkretnego rodzaju gitary (1) ");
                System.out.println("gitarę do danej kwoty (2) ");
                int wuborKlijent = scan.nextInt();

                switch (wuborKlijent) {
                    case 1:  //KLIJENT rodzaj gitary
                        System.out.println("Jaki rodzaj gitary interesuje klijenta?");
                        WyswietlenieIdTypowGitar();
                        RodzajeGitarEnum wyszukiwanieRodzajuGitary = null;
                        do {
                            try {
                                wyszukiwanieRodzajuGitary = getTypeById(scan.nextInt());
                            } catch (Exception e) {
                                System.out.println("Podales bledne dane popraw je, wpisz je jeszcze raz  ");
                            }
                        } while (wyszukiwanieRodzajuGitary == null);

                        List<Gitary> gitaryPoType = new ArrayList<>();
                        switch (wyszukiwanieRodzajuGitary) {
                            case ELEKTRYCZNA:
                                System.out.println("Gitary Elektryczne na magazynie to:");
                                gitaryPoType.addAll(HibernateUtil.pobierzGitaryPoRodzaju(ELEKTRYCZNA));
                                break;

                            case AKUSTYCZNA:
                                System.out.println("Gitary Akustyczne na magazynie to:");
                                gitaryPoType.addAll(HibernateUtil.pobierzGitaryPoRodzaju(AKUSTYCZNA));
                                break;

                            case KLASYCZNA:
                                System.out.println("Gitary Klasyczne na magazynie to:");
                                gitaryPoType.addAll(HibernateUtil.pobierzGitaryPoRodzaju(KLASYCZNA));
                                break;

                            case BASOWA:
                                System.out.println("Gitary Basowe na magazynie to:");
                                gitaryPoType.addAll(HibernateUtil.pobierzGitaryPoRodzaju(BASOWA));
                                break;

                            default:
                                System.out.println("Wybierz prawidłową opcję! Wybrana opcja nie jest obsługiwana");
                                break;
                        }
                        wyswietlListeGitar(gitaryPoType);
                        break;

                    case 2: //KLIJENT kwota
                        System.out.println("Podaj kwotę do jakiej wyszukać gitarę");
                        float cenaDo = -1F;
                        while (cenaDo < 0) {
                            try {
                                cenaDo = scan.nextFloat();
                            } catch (Exception e) {
                                System.out.println("podana cena nie jest prawidłowa, podaj jeszcze raz ");
                            }
                        }
                        wyswietlListeGitar(HibernateUtil.pobierzGitaryDoCenyMaxymalnej(cenaDo));
                        break;

                    default:
                        System.out.println("Wybierz prawidłową opcję! Wybrana opcja nie jest obsługiwana");
                        break;
                }
                break;

            case 2: //MAGAZYN
                System.out.println("Jaka akcja cię interesuje?");
                System.out.println("Dodanie asortymentu (1)");
                System.out.println("Wyświetlenie stanu magazynowego (2)");
                System.out.println("Edycja asortymentu (3)");
                int operacjeNaMagazynie = scan.nextInt();

                switch (operacjeNaMagazynie) {
                    case DODAJ_NOWA_GITARE:
                        System.out.println("Podaj producenta:");
                        String producentOdczyt = scan.nextLine();

                        System.out.println("Podaj model:");
                        String modelOdczyt = scan.nextLine();

                        System.out.println("Podaj cene: ");
                        Float cenaOdczyt = scan.nextFloat();

                        System.out.println("Podaj rodzaj gitary: ");
                        Main.WyswietlenieIdTypowGitar();
                        RodzajeGitarEnum rodzajOdczyt = RodzajeGitarEnum.getTypeById(scan.nextInt());

                        System.out.println("Podaj ilość sztuk do zamagazynowania: ");
                        int iloscOdczyt = scan.nextInt();

                        Gitary gitara = new Gitary();
                        gitara.setProducent(producentOdczyt);
                        gitara.setModel(modelOdczyt);
                        gitara.setCena(cenaOdczyt);
                        gitara.setRodzaj(rodzajOdczyt.wartosc);
                        gitara.setIlosc(iloscOdczyt);

                        HibernateUtil.dodajGitare(gitara);
                        break;

                    case WYSWIETL_STAN_MAGAZYNU: //wyświetlenie STANU MAGAZYNOWEGO
                        System.out.println("Stan magazynowy to: " + HibernateUtil.pobierzListeGitar());
                        break;

                    case EDYCJA_ASORtYMENTU: //EDYCJA asortymentu
                        System.out.println(HibernateUtil.pobierzListeGitar());
                        System.out.println("podaj id gitary do edycji");
                        int idGitary = -1;
                        Gitary wyszukanaGitara = null;
                        do {
                            try {
                                idGitary = scan.nextInt();
                                if (idGitary > -1) {
                                    wyszukanaGitara = HibernateUtil.pobierzGitarePoId(idGitary);
                                    System.out.println("Co chcesz edytować?");
                                    System.out.println("Stan magazynowy (1)");
                                    System.out.println("Cenę (2)");
                                    int wyborEdycji = scan.nextInt();

                                    switch (wyborEdycji) {
                                        case 1:
                                            System.out.println("Podaj ile sztuk chcesz dodać do magazynu");
                                            int edycjaStanMagazynowy = scan.nextInt();
                                            wyszukanaGitara.setIlosc(edycjaStanMagazynowy);
                                            HibernateUtil.aktualizujGitare(wyszukanaGitara);
                                            break;

                                        case 2:
                                            System.out.println("Podaj aktualną cenę");
                                            float edycjaCena = scan.nextFloat();
                                            wyszukanaGitara.setCena(edycjaCena);
                                            HibernateUtil.aktualizujGitare(wyszukanaGitara);
                                            break;

                                        default:
                                            System.out.println("Wybierz prawidłową opcję! Wybrana opcja nie jest obsługiwana");
                                            break;
                                    }
                                } else {
                                    throw new Exception();
                                }
                            } catch (Exception e) {
                                System.out.println("podales bledny nr gitary, podaj jeszcze raz ");
                            }
                        } while (idGitary < 0);
                        break;
                }
                break;

            case 3: //SPRZEDAŻ -usuwanie z bazy danych
                System.out.println(HibernateUtil.pobierzListeGitar());
                System.out.println("podaj id gitary do sprzedaży");
                int idGitary = -1;
                Gitary wyszukanaGitara = null;
                do {
                    try {
                        scan.nextLine();
                        idGitary = scan.nextInt();
                        System.out.println();
                        if (idGitary > -1) {
                            wyszukanaGitara = HibernateUtil.pobierzGitarePoId(idGitary);
                            scan.nextLine();
                            System.out.println("Ile sztuk sprzedałeś tej gitary?");
                            int iloscSprzedazy = scan.nextInt();
                            scan.nextLine();
                            if (wyszukanaGitara != null) {
                                int aktualnaIlosc = wyszukanaGitara.getIlosc();
                                int nowaIlosc = aktualnaIlosc - iloscSprzedazy;
                                if (nowaIlosc < 0) {
                                    System.out.println("Sprzedaż " + iloscSprzedazy + " sztuk wybranej przez ciebie gitary nie jest możliwa. Nie ma tyle sztuk na magazynie!");
                                } else {
                                    wyszukanaGitara.setIlosc(nowaIlosc);
                                    PlikiUtils.dodajSprzedanaGitare(wyszukanaGitara);
                                    HibernateUtil.aktualizujGitare(wyszukanaGitara);
                                }
                            }
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("podaleś błędny nr gitary, podaj jeszcze raz ");
                        e.printStackTrace();
                    }
                } while (idGitary < 0);
                break;
        }
    }


    private static void wyswietlListeGitar(List<Gitary> gitary) {
        System.out.println("================================ Lista Gitar ==========================================");
        gitary.forEach(System.out::println);
        System.out.println("=======================================================================================");
    }


    private static void WyswietlenieIdTypowGitar() {
        System.out.println("Akustyczna (0)");
        System.out.println("Basowa (1)");
        System.out.println("Klasyczna (2");
        System.out.println("Elektryczna (3)");
    }
}
