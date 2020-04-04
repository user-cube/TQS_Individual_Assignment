package pt.ua.tqs.airquality.entities;

public enum CitiesNames {
    A("Aveiro"),
    B("Beja"),
    BB("Braga"),
    BBB("Bragança"),
    C("Castelo Branco"),
    CC("Coimbra"),
    E("Évora"),
    F("Faro"),
    G("Guarda"),
    L("Leiria"),
    LL("Lisboa"),
    P("Portalegre"),
    PP("Porto"),
    S("Santarém"),
    SS("Setúbal"),
    V("Viana do Castelo"),
    VV("Vila Real"),
    VVV("Viseu"),
    FF("Funchal"),
    PPP("Porto Santo"),
    PPPP("Ponta Delgada"),
    AA("Angra do Heroísmo"),
    H("Horta"),
    SCF("Santa Cruz das Flores");

    public final String label;

    CitiesNames(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
