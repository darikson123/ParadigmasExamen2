package co.edu.poli.Examen2.infraestructura.entrada.javafx;

import co.edu.poli.Examen2.aplicacion.observador.ObservadorExamen;
import co.edu.poli.Examen2.aplicacion.observador.PublicadorEventos;
import co.edu.poli.Examen2.aplicacion.puertos.entrada.DeserializarExamenesCasoUso;
import co.edu.poli.Examen2.aplicacion.puertos.entrada.ListarExamenesCasoUso;
import co.edu.poli.Examen2.aplicacion.puertos.entrada.RegistrarExamenCasoUso;
import co.edu.poli.Examen2.aplicacion.puertos.entrada.SerializarExamenesCasoUso;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.Examen2.dominio.modelo.FactorRH;
import co.edu.poli.Examen2.dominio.modelo.TipoSangre;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;

/**
 * Adaptador de entrada JavaFX. Traduce los eventos del formulario en llamadas
 * a los puertos de entrada y muestra los resultados en el área de mensajes.
 *
 * <p>También actúa como observador ({@link ObservadorExamen}) de los eventos
 * publicados por los casos de uso.</p>
 */
public class PrimaryController implements ObservadorExamen {

    @FXML
    private TextField txtCodigoExamen;
    @FXML
    private TextField txtNombrePaciente;
    @FXML
    private TextField txtCosto;
    @FXML
    private ComboBox<TipoSangre> cmbTipoSangre;
    @FXML
    private RadioButton rbPositivo;
    @FXML
    private RadioButton rbNegativo;
    @FXML
    private ToggleGroup tgFactorRH;
    @FXML
    private TextArea txtAreaMensajes;

    private final RegistrarExamenCasoUso registrarExamen;
    private final ListarExamenesCasoUso listarExamenes;
    private final SerializarExamenesCasoUso serializarExamenes;
    private final DeserializarExamenesCasoUso deserializarExamenes;
    private final PublicadorEventos publicador;

    /**
     * Crea el controlador con los puertos de entrada que necesita.
     *
     * @param registrarExamen      caso de uso Registrar
     * @param listarExamenes       caso de uso Listar
     * @param serializarExamenes   caso de uso Serializar
     * @param deserializarExamenes caso de uso Deserializar
     * @param publicador           sujeto al que se suscribe para recibir mensajes
     */
    public PrimaryController(RegistrarExamenCasoUso registrarExamen,
                             ListarExamenesCasoUso listarExamenes,
                             SerializarExamenesCasoUso serializarExamenes,
                             DeserializarExamenesCasoUso deserializarExamenes,
                             PublicadorEventos publicador) {
        this.registrarExamen = Objects.requireNonNull(registrarExamen);
        this.listarExamenes = Objects.requireNonNull(listarExamenes);
        this.serializarExamenes = Objects.requireNonNull(serializarExamenes);
        this.deserializarExamenes = Objects.requireNonNull(deserializarExamenes);
        this.publicador = Objects.requireNonNull(publicador);
    }

    /**
     * Carga los tipos de sangre desde el dominio y se suscribe a los eventos.
     */
    @FXML
    public void initialize() {
        cmbTipoSangre.getItems().setAll(TipoSangre.values());
        cmbTipoSangre.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(TipoSangre item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? cmbTipoSangre.getPromptText() : item.name());
            }
        });
        publicador.suscribir(this);
    }

    @FXML
    private void handleGuardar() {
        try {
            registrarExamen.registrar(
                    txtCodigoExamen.getText(),
                    txtNombrePaciente.getText(),
                    leerCosto(),
                    cmbTipoSangre.getValue(),
                    leerFactorRH());
            limpiarCampos();
        } catch (IllegalArgumentException e) {
            mostrar("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleLimpiar() {
        limpiarCampos();
    }

    @FXML
    private void handleListar() {
        List<ExamenSangre> examenes = listarExamenes.listar();
        if (examenes.isEmpty()) {
            mostrar("No hay exámenes registrados.");
            return;
        }
        mostrar("Exámenes registrados (" + examenes.size() + "):");
        for (ExamenSangre examen : examenes) {
            mostrar("  " + examen);
        }
    }

    @FXML
    private void handleSerializar() {
        try {
            serializarExamenes.serializar();
        } catch (UncheckedIOException e) {
            mostrar("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeserializar() {
        try {
            deserializarExamenes.deserializar();
        } catch (UncheckedIOException e) {
            mostrar("Error: " + e.getMessage());
        }
    }

    /**
     * Recibe los mensajes publicados por los casos de uso.
     *
     * @param mensaje descripción del evento
     */
    @Override
    public void actualizar(String mensaje) {
        mostrar(mensaje);
    }

    private double leerCosto() {
        String texto = txtCosto.getText() == null ? "" : txtCosto.getText().trim().replace(',', '.');
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("El costo es obligatorio.");
        }
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El costo debe ser un número válido.");
        }
    }

    private FactorRH leerFactorRH() {
        return tgFactorRH.getSelectedToggle() == rbNegativo ? FactorRH.NEGATIVO : FactorRH.POSITIVO;
    }

    private void limpiarCampos() {
        txtCodigoExamen.clear();
        txtNombrePaciente.clear();
        txtCosto.clear();
        cmbTipoSangre.getSelectionModel().clearSelection();
        rbPositivo.setSelected(true);
    }

    private void mostrar(String mensaje) {
        txtAreaMensajes.appendText(mensaje + System.lineSeparator());
    }
}
