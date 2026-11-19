package co.edu.poli.Examen2.dominio.modelo;

/**
 * Factor Rh de un examen de sangre.
 */
public enum FactorRH {
    /** Rh positivo. */
    POSITIVO("+"),
    /** Rh negativo. */
    NEGATIVO("-");

    private final String simbolo;

    FactorRH(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * Devuelve el símbolo del factor Rh.
     *
     * @return {@code "+"} para positivo o {@code "-"} para negativo
     */
    public String getSimbolo() {
        return simbolo;
    }
}
