package co.edu.poli.Examen2.infraestructura.entrada.javafx;

import co.edu.poli.Examen2.aplicacion.casosuso.DeserializarExamenesServicio;
import co.edu.poli.Examen2.aplicacion.casosuso.ListarExamenesServicio;
import co.edu.poli.Examen2.aplicacion.casosuso.RegistrarExamenServicio;
import co.edu.poli.Examen2.aplicacion.casosuso.SerializarExamenesServicio;
import co.edu.poli.Examen2.aplicacion.decorador.CodigoUnicoRepositorioDecorador;
import co.edu.poli.Examen2.aplicacion.observador.PublicadorEventos;
import co.edu.poli.Examen2.aplicacion.puertos.salida.ArchivoExamenesPuerto;
import co.edu.poli.Examen2.aplicacion.puertos.salida.ExamenRepositorioPuerto;
import co.edu.poli.Examen2.infraestructura.salida.archivo.SerializadorExamenesArchivo;
import co.edu.poli.Examen2.infraestructura.salida.memoria.RepositorioExamenesMemoria;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Punto de arranque de la aplicación JavaFX.
 *
 * <p>Ensambla la arquitectura hexagonal: crea los adaptadores de salida,
 * los casos de uso y el controlador, y conecta cada uno con los puertos
 * que necesita. Es la única clase que conoce las implementaciones concretas.</p>
 */
public class App extends Application {

    private static final String ARCHIVO_EXAMENES = "examenes.dat";

    /**
     * Construye las dependencias y muestra la ventana principal.
     *
     * @param stage ventana principal
     * @throws IOException si no se puede cargar el archivo FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        ExamenRepositorioPuerto repositorio =
                new CodigoUnicoRepositorioDecorador(new RepositorioExamenesMemoria());
        ArchivoExamenesPuerto archivo = new SerializadorExamenesArchivo(Path.of(ARCHIVO_EXAMENES));
        PublicadorEventos publicador = new PublicadorEventos();

        PrimaryController controlador = new PrimaryController(
                new RegistrarExamenServicio(repositorio, publicador),
                new ListarExamenesServicio(repositorio),
                new SerializarExamenesServicio(repositorio, archivo, publicador),
                new DeserializarExamenesServicio(repositorio, archivo, publicador),
                publicador);

        FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/poli/Examen2/primary.fxml"));
        loader.setControllerFactory(tipo -> controlador);
        Parent raiz = loader.load();

        stage.setTitle("Exámenes de Sangre");
        stage.setScene(new Scene(raiz, 640, 580));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Método principal.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}
