package SklepZGitarami;

import SklepZGitarami.modele.Gitary;
import SklepZGitarami.modele.RodzajeGitarEnum;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static ServiceRegistry registry;


    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // tworzenie rejestru
                registry = new StandardServiceRegistryBuilder().configure().build();
                // tworzenie MetadataSources
                MetadataSources sources = new MetadataSources(registry);
                // Tworzenie Metadata
                Metadata metadata = sources.getMetadataBuilder().build();
                // Tworzenie SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }


    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }


    public static List<Gitary> pobierzListeGitar() {
        return getSessionFactory().openSession().createQuery("from Gitary where ilosc > 0", Gitary.class).getResultList();
    }


    public static void dodajListeGitar(List<Gitary> gitary) {
        Session session = getSessionFactory().openSession();
        try {
            otworzTransakcje(session);
            for (Gitary gitara : gitary) {
                session.save(gitara);
            }
            zatwierdzTransakcje(session);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                cofnijTransakcje(session);
            }
            e.printStackTrace();
        }
    }


    public static void dodajGitare(Gitary gitara) {
        Session session = getSessionFactory().openSession();
        try {
            otworzTransakcje(session);
            List<Gitary> znalezioneGitary = pobierzListeGitar().stream().filter(g -> g.getModel().equals(gitara.getModel()) && g.getProducent().equals(gitara.getProducent())).collect(Collectors.toList());
            if (znalezioneGitary.isEmpty()) {
                session.save(gitara);
            } else {
                Gitary aktualnionaGitara = znalezioneGitary.get(0);
                aktualnionaGitara.setIlosc(gitara.getIlosc());
                aktualizujGitare(aktualnionaGitara);
            }
            zatwierdzTransakcje(session);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                cofnijTransakcje(session);
            }
            e.printStackTrace();
        }
    }


    public static void aktualizujGitare(Gitary gitara) {
        Session session = getSessionFactory().openSession();
        try {
            otworzTransakcje(session);
            session.update(gitara);
            zatwierdzTransakcje(session);
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                cofnijTransakcje(session);
            }
            e.printStackTrace();
        }
    }


    public static List<Gitary> pobierzGitaryPoRodzaju(RodzajeGitarEnum rodzaj) {
        return pobierzListeGitar()
                .stream()
                .filter(gitara -> gitara.getRodzaj() == rodzaj.wartosc && gitara.getIlosc() > 0)
                .collect(Collectors.toList());
    }


    public static List<Gitary> pobierzGitaryDoCenyMaxymalnej(float cenaDo) {
        return pobierzListeGitar()
                .stream()
                .filter(gitara -> gitara.getCena() <= cenaDo)
                .collect(Collectors.toList());
    }


    public static void inicjalizacjaListGitar() {
        List<Gitary> listaGitar = Arrays.asList(new Gitary("Gibson", "Les Paul Special Tribute Humbucker 5N Natural Walnut Satin", (float) 4499.00, RodzajeGitarEnum.ELEKTRYCZNA.wartosc, 2),
                new Gitary("Gibson", "Slash Les Paul Standard NV November Burst", (float) 13499.00, RodzajeGitarEnum.ELEKTRYCZNA.wartosc, 1),
                new Gitary("Taylor", "American Dream AD27", (float) 7140.46, RodzajeGitarEnum.AKUSTYCZNA.wartosc, 3),
                new Gitary("Taylor", "American Dream AD17e", (float) 8033.58, RodzajeGitarEnum.AKUSTYCZNA.wartosc, 1),
                new Gitary("Gibson", "Les Paul Special Tribute Humbucker 5N Natural Walnut Satin", (float) 4499.00, RodzajeGitarEnum.ELEKTRYCZNA.wartosc, 1),
                new Gitary("Gibson", "FLYING V 70's CW Classic White", (float) 13499.00, RodzajeGitarEnum.ELEKTRYCZNA.wartosc, 1),
                new Gitary("Gibson", "ES-339 Figured B9 Blueberry Burst", (float) 12999.00, RodzajeGitarEnum.ELEKTRYCZNA.wartosc, 1),
                new Gitary("Epiphone", "SG Standard 61 VC Vintage Cherry", (float) 1999.00, RodzajeGitarEnum.ELEKTRYCZNA.wartosc, 3),
                new Gitary("Epiphone", "Explorer EB Ebony", (float) 2699.00, RodzajeGitarEnum.ELEKTRYCZNA.wartosc, 1),
                new Gitary("Admira", "Virtuoso nowe", (float) 1179.00, RodzajeGitarEnum.KLASYCZNA.wartosc, 1),
                new Gitary("Admira", "Triana", (float) 919.00, RodzajeGitarEnum.KLASYCZNA.wartosc, 11),
                new Gitary("Arrow", "Calma 3/4 mat", (float) 391.00, RodzajeGitarEnum.KLASYCZNA.wartosc, 10),
                new Gitary("Alhambra", "2C", (float) 1899.00, RodzajeGitarEnum.KLASYCZNA.wartosc, 1),
                new Gitary("Admira", "Juanita 3/4", (float) 688.00, RodzajeGitarEnum.KLASYCZNA.wartosc, 5),
                new Gitary("Epiphone", "PRO-1 Classic 2.0 NA Natural", (float) 477.00, RodzajeGitarEnum.ELEKTRYCZNA.wartosc, 1),
                new Gitary("Taylor", "American Dream AD27", (float) 7140.46, RodzajeGitarEnum.AKUSTYCZNA.wartosc, 1),
                new Gitary("Taylor", "American Dream AD17 Blacktop", (float) 7587.46, RodzajeGitarEnum.AKUSTYCZNA.wartosc, 1),
                new Gitary("Taylor", "GS Mini Rosewood", (float) 2857.46, RodzajeGitarEnum.AKUSTYCZNA.wartosc, 1),
                new Gitary("Spector", "Performer 4 White Gloss", (float) 1479.46, RodzajeGitarEnum.BASOWA.wartosc, 1),
                new Gitary("Spector", "Performer 4 Black Gloss", (float) 1479.00, RodzajeGitarEnum.BASOWA.wartosc, 1),
                new Gitary("Epiphone", "EB3 EB", (float) 1569.46, RodzajeGitarEnum.BASOWA.wartosc, 1),
                new Gitary("Epiphone", "EB3 CH", (float) 1469.46, RodzajeGitarEnum.BASOWA.wartosc, 1),
                new Gitary("SCHECTER", "DIAMOND-J BASS IVY", (float) 3391.46, RodzajeGitarEnum.BASOWA.wartosc, 1));
        dodajListeGitar(listaGitar);
    }


    public static Gitary pobierzGitarePoId(int id) {
        return pobierzListeGitar()
                .stream()
                .filter(gitara -> gitara.getId() == id)
                .collect(Collectors.toList())
                .get(0);
    }


    private static void otworzTransakcje(Session session) {
        session.getTransaction().begin();
    }


    private static void zatwierdzTransakcje(Session session) {
        session.getTransaction().commit();
    }


    private static void cofnijTransakcje(Session session) {
        session.getTransaction().rollback();
    }
}