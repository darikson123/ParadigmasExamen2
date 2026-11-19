package co.edu.poli.Examen2.dominio.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExamenSangreTest {

    private ExamenSangre.Builder builderValido() {
        return ExamenSangre.builder()
                .codigo("EXM-001")
                .nombrePaciente("Ana Pérez")
                .costo(150.0)
                .tipoSangre(TipoSangre.A)
                .factorRH(FactorRH.POSITIVO);
    }

    @Test
    void construyeExamenValido() {
        ExamenSangre examen = builderValido().build();

        assertEquals("EXM-001", examen.getCodigo());
        assertEquals("Ana Pérez", examen.getNombrePaciente());
        assertEquals(150.0, examen.getCosto());
        assertEquals(TipoSangre.A, examen.getTipoSangre());
        assertEquals(FactorRH.POSITIVO, examen.getFactorRH());
    }

    @Test
    void eliminaEspaciosDelCodigoYNombre() {
        ExamenSangre examen = builderValido().codigo("  EXM-002 ").nombrePaciente(" Luis ").build();

        assertEquals("EXM-002", examen.getCodigo());
        assertEquals("Luis", examen.getNombrePaciente());
    }

    @Test
    void rechazaCodigoVacio() {
        assertThrows(IllegalArgumentException.class, () -> builderValido().codigo("  ").build());
    }

    @Test
    void rechazaNombreVacio() {
        assertThrows(IllegalArgumentException.class, () -> builderValido().nombrePaciente(null).build());
    }

    @Test
    void rechazaCostoCeroONegativo() {
        assertThrows(IllegalArgumentException.class, () -> builderValido().costo(0).build());
        assertThrows(IllegalArgumentException.class, () -> builderValido().costo(-10).build());
    }

    @Test
    void rechazaTipoSangreNulo() {
        assertThrows(IllegalArgumentException.class, () -> builderValido().tipoSangre(null).build());
    }

    @Test
    void rechazaFactorRHNulo() {
        assertThrows(IllegalArgumentException.class, () -> builderValido().factorRH(null).build());
    }

    @Test
    void toStringMuestraTipoConSimbolo() {
        ExamenSangre examen = builderValido().factorRH(FactorRH.NEGATIVO).build();

        assertEquals("EXM-001 | Ana Pérez | A- | $150.00", examen.toString());
    }
}
