package pt.ua.tqs.airquality.entities;

public enum CitiesCoordinates {
    AVEIRO_LAT("40.6405"),
    AVEIRO_LONG("-8.6538"),
    BEJA_LAT("38.0153"),
    BEJA_LONG("-7.8627"),
    BRAGA_LAT("41.5454"),
    BRAGA_LONG("-8.4265"),
    BRAGANCA_LAT("41.8061"),
    BRAGANCA_LONG("-6.7567"),
    CASTELOBRANCO_LAT("39.8031"),
    CASTELOBRANCO_LONG("-7.4598"),
    COIMBRA_LAT("40.2033"),
    COIMBRA_LONG("-8.4103"),
    EVORA_LAT("38.5714"),
    EVORA_LONG("-7.9135"),
    FARO_LAT("37.0194"),
    FARO_LONG("-7.9304"),
    GUARDA_LAT("40.5308"),
    GUARDA_LONG("-7.2221"),
    LEIRIA_LAT("39.7495"),
    LEIRIA_LONG("-8.8077"),
    LISBOA_LAT("38.7223"),
    LISBOA_LONG("-9.1393"),
    PORTALEGRE_LAT("39.2967"),
    PORTALEGRE_LONG("-7.4285"),
    PORTO_LAT("41.1579"),
    PORTO_LONG("-8.6291"),
    SANTAREM_LAT("39.2367"),
    SANTAREM_LONG("-8.6860"),
    SETUBAL_LAT("38.5260"),
    SETUBAL_LONG("-8.8909"),
    VIANADOCASTELO_LAT("41.6918"),
    VIANADOCASTELO_LONG("-8.8345"),
    VILAREAL_LAT("41.3010"),
    VILAREAL_LONG("-7.7422"),
    VISEU_LAT("40.6566"),
    VISEU_LONG("-7.9125"),
    FUNCHAL_LAT("32.6669"),
    FUNCHAL_LONG("-16.9241"),
    PORTOSANTO_LAT("33.0603"),
    PORTOSANTO_LONG("-16.3339"),
    PONTADELGADA_LAT("37.7394"),
    PONTADELGADA_LONG("-25.6687"),
    ANGRADOHEROISMO_LAT("38.6635"),
    ANGRADOHEROISMO_LONG("-27.2294"),
    HORTA_LAT("38.5348"),
    HORTA_LONG("-28.6300"),
    SANTACRUZDASFLORES_LAT("39.4583"),
    SANTACRUZDASFLORES_LONG("-31.1338"),
    ;

    public final String label;

    CitiesCoordinates(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
