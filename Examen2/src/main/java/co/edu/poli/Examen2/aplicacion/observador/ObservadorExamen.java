package co.edu.poli.Examen2.aplicacion.observador;

/**
 * Observador del patrón <b>Observer</b>: recibe las notificaciones que publican
 * los casos de uso cuando ocurre una operación sobre los exámenes.
 */
public interface ObservadorExamen {

    /**
     * Se invoca cada vez que el sujeto publica un evento.
     *
     * @param mensaje descripción del evento ocurrido
     */
    void actualizar(String mensaje);
}
