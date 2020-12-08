package SklepZGitarami.modele;

public enum RodzajeGitarEnum {
    AKUSTYCZNA(0),
    BASOWA(1),
    KLASYCZNA(2),
    ELEKTRYCZNA(3),
    NIEZIDENTYFIKOWANA_GITARA(4);

    public final int wartosc;

    RodzajeGitarEnum(int rodzaj) {
        this.wartosc = rodzaj;
    }

    public static RodzajeGitarEnum getTypeById(int id) {

        switch (id) {
            case 0:
                return AKUSTYCZNA;
            case 1:
                return BASOWA;
            case 2:
                return KLASYCZNA;
            case 3:
                return ELEKTRYCZNA;
            default:
                return NIEZIDENTYFIKOWANA_GITARA;
        }
    }
}
