package co.edu.poli.Examen2.aplicacion.puertos.entrada;

import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.Examen2.dominio.modelo.FactorRH;
import co.edu.poli.Examen2.dominio.modelo.TipoSangre;

/**
 * Puerto de entrada: registrar un examen de sangre (botón Guardar).
 */
public interface RegistrarExamenCasoUso {

    /**
     * Registra un nuevo examen.
     *
     * @param codigo         código único del examen
     * @param nombrePaciente nombre del paciente
     * @param costo          costo del examen
     * @param tipoSangre     grupo sanguíneo
     * @param factorRH       factor Rh
     * @return el examen registrado
     * @throws IllegalArgumentException si los datos no cumplen las reglas de negocio
     *                                  o si el código ya existe
     */
    ExamenSangre registrar(String codigo, String nombrePaciente, double costo,
                           TipoSangre tipoSangre, FactorRH factorRH);
}
