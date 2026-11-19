package co.edu.poli.Examen2.dominio.modelo;

import java.io.Serializable;
import java.util.Locale;

/**
 * Entidad de dominio que representa un examen de sangre.
 *
 * <p>Las instancias solo se crean mediante {@link Builder} (patrón creacional
 * <b>Builder</b>), que valida las reglas de negocio antes de construir el objeto.
 * De esta forma nunca existe un examen en estado inválido.</p>
 *
 * <p>Reglas de negocio:</p>
 * <ul>
 *   <li>El código es obligatorio.</li>
 *   <li>El nombre del paciente es obligatorio.</li>
 *   <li>El costo debe ser mayor que cero.</li>
 *   <li>El tipo de sangre y el factor Rh son obligatorios.</li>
 * </ul>
 */
public final class ExamenSangre implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String codigo;
    private final String nombrePaciente;
    private final double costo;
    private final TipoSangre tipoSangre;
    private final FactorRH factorRH;

    private ExamenSangre(Builder builder) {
        this.codigo = builder.codigo;
        this.nombrePaciente = builder.nombrePaciente;
        this.costo = builder.costo;
        this.tipoSangre = builder.tipoSangre;
        this.factorRH = builder.factorRH;
    }

    /**
     * Crea un nuevo constructor de exámenes.
     *
     * @return un {@link Builder} vacío
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * @return código único del examen
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return nombre completo del paciente
     */
    public String getNombrePaciente() {
        return nombrePaciente;
    }

    /**
     * @return costo del examen
     */
    public double getCosto() {
        return costo;
    }

    /**
     * @return grupo sanguíneo
     */
    public TipoSangre getTipoSangre() {
        return tipoSangre;
    }

    /**
     * @return factor Rh
     */
    public FactorRH getFactorRH() {
        return factorRH;
    }

    /**
     * Representación legible del examen, usada al listar.
     *
     * @return texto con código, paciente, tipo de sangre y costo
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s | %s | %s%s | $%.2f",
                codigo, nombrePaciente, tipoSangre, factorRH.getSimbolo(), costo);
    }

    /**
     * Constructor paso a paso de {@link ExamenSangre} (patrón Builder).
     * Concentra la validación de las reglas de negocio en {@link #build()}.
     */
    public static final class Builder {

        private String codigo;
        private String nombrePaciente;
        private double costo;
        private TipoSangre tipoSangre;
        private FactorRH factorRH;

        private Builder() {
        }

        /**
         * @param codigo código del examen
         * @return este builder
         */
        public Builder codigo(String codigo) {
            this.codigo = codigo == null ? null : codigo.trim();
            return this;
        }

        /**
         * @param nombrePaciente nombre del paciente
         * @return este builder
         */
        public Builder nombrePaciente(String nombrePaciente) {
            this.nombrePaciente = nombrePaciente == null ? null : nombrePaciente.trim();
            return this;
        }

        /**
         * @param costo costo del examen
         * @return este builder
         */
        public Builder costo(double costo) {
            this.costo = costo;
            return this;
        }

        /**
         * @param tipoSangre grupo sanguíneo
         * @return este builder
         */
        public Builder tipoSangre(TipoSangre tipoSangre) {
            this.tipoSangre = tipoSangre;
            return this;
        }

        /**
         * @param factorRH factor Rh
         * @return este builder
         */
        public Builder factorRH(FactorRH factorRH) {
            this.factorRH = factorRH;
            return this;
        }

        /**
         * Valida las reglas de negocio y construye el examen.
         *
         * @return un examen válido
         * @throws IllegalArgumentException si alguna regla de negocio no se cumple
         */
        public ExamenSangre build() {
            if (codigo == null || codigo.isEmpty()) {
                throw new IllegalArgumentException("El código del examen es obligatorio.");
            }
            if (nombrePaciente == null || nombrePaciente.isEmpty()) {
                throw new IllegalArgumentException("El nombre del paciente es obligatorio.");
            }
            if (costo <= 0) {
                throw new IllegalArgumentException("El costo debe ser mayor que cero.");
            }
            if (tipoSangre == null) {
                throw new IllegalArgumentException("Debe seleccionar el tipo de sangre.");
            }
            if (factorRH == null) {
                throw new IllegalArgumentException("Debe seleccionar el factor RH.");
            }
            return new ExamenSangre(this);
        }
    }
}
