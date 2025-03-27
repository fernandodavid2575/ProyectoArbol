import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class grafico extends Application {
    private ArbolBinario arbol = new ArbolBinario();
    private Pane panelArbol = new Pane();
    private TextArea areaTexto = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Arbol Binario");

        VBox panelSuperior = new VBox(5);
        panelSuperior.setPadding(new Insets(10));
        panelSuperior.setAlignment(Pos.CENTER);
        panelSuperior.setStyle("-fx-background-color: #1976D2; -fx-border-color: #0D47A1; -fx-border-width: 0 0 1 0;");

        HBox panelBotones = new HBox(8);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(5));

        Button btnInsertar = crearBoton("Insertar", "#4CAF50", "#388E3C");
        Button btnEliminar = crearBoton("Eliminar", "#F44336", "#D32F2F");
        Button btnBuscar = crearBoton("Buscar Nodo", "#673AB7", "#5E35B1");
        Button btnPreOrden = crearBoton("PreOrden", "#9C27B0", "#7B1FA2");
        Button btnInOrden = crearBoton("InOrden", "#009688", "#00796B");
        Button btnPostOrden = crearBoton("PostOrden", "#607D8B", "#455A64");
        Button btnGuardar = crearBoton("Guardar", "#FFC107", "#FFA000");
        Button btnAbrir = crearBoton("Abrir", "#FF9800", "#F57C00");
        Button btnEliminarTodo = crearBoton("Limpiar Arbol", "#FF5722", "#E64A19");

        panelBotones.getChildren().addAll(
                btnInsertar, btnEliminar, btnBuscar,
                btnPreOrden, btnInOrden, btnPostOrden,
                btnGuardar, btnAbrir, btnEliminarTodo
        );

        TextField campoTexto = new TextField();
        campoTexto.setPromptText("Ingrese valor del nodo");
        campoTexto.setStyle("-fx-font-size: 14; -fx-background-radius: 4; -fx-border-radius: 4; " +
                "-fx-background-color: #E3F2FD; -fx-pref-width: 150px;");
        campoTexto.setMaxWidth(150);

        HBox panelTexto = new HBox();
        panelTexto.setAlignment(Pos.CENTER);
        panelTexto.getChildren().add(campoTexto);

        panelSuperior.getChildren().addAll(panelBotones, panelTexto);

        areaTexto.setEditable(false);
        areaTexto.setWrapText(true);
        areaTexto.setPrefHeight(100);
        areaTexto.setStyle("-fx-font-size: 13; -fx-control-inner-background: #E3F2FD; -fx-text-fill: #0D47A1;");

        panelArbol.setStyle("-fx-background-color: #2196F3;");

        BorderPane root = new BorderPane();
        root.setTop(panelSuperior);
        root.setCenter(panelArbol);
        root.setBottom(areaTexto);
        root.setStyle("-fx-background-color: #2196F3;");

        BorderPane.setMargin(areaTexto, new Insets(5));

        btnInsertar.setOnAction(e -> {
            try {
                int valor = Integer.parseInt(campoTexto.getText());
                arbol.insertar(valor);
                mostrarMensaje("Nodo " + valor + " insertado correctamente.");
                dibujarArbol(arbol.getRaiz(), 650, 50, 200, -1);
            } catch (NumberFormatException ex) {
                mostrarMensaje("Error: Ingrese un número válido.");
            }
            campoTexto.clear();
        });

        btnEliminar.setOnAction(e -> {
            try {
                int valor = Integer.parseInt(campoTexto.getText());
                arbol.eliminar(valor);
                mostrarMensaje("Nodo " + valor + " eliminado correctamente.");
                dibujarArbol(arbol.getRaiz(), 650, 50, 200, -1);
            } catch (NumberFormatException ex) {
                mostrarMensaje("Error: Ingrese un número válido.");
            }
            campoTexto.clear();
        });

        btnPreOrden.setOnAction(e -> {
            List<Integer> resultado = arbol.preOrden();
            mostrarMensaje("Recorrido PreOrden: " + resultado);
        });

        btnInOrden.setOnAction(e -> {
            List<Integer> resultado = arbol.inOrden();
            mostrarMensaje("Recorrido InOrden: " + resultado);
        });

        btnPostOrden.setOnAction(e -> {
            List<Integer> resultado = arbol.postOrden();
            mostrarMensaje("Recorrido PostOrden: " + resultado);
        });

        btnGuardar.setOnAction(e -> guardarArbolEnArchivo());
        btnAbrir.setOnAction(e -> abrirArchivo());

        btnEliminarTodo.setOnAction(e -> {
            arbol = new ArbolBinario();
            panelArbol.getChildren().clear();
            mostrarMensaje("Árbol eliminado completamente.");
        });

        btnBuscar.setOnAction(e -> {
            try {
                int valor = Integer.parseInt(campoTexto.getText());
                Nodo nodoEncontrado = arbol.buscar(valor);
                if (nodoEncontrado != null) {
                    int alturaNodo = arbol.alturaNodo(valor);
                    int cantidadHijos = arbol.contarHijos(valor);
                    mostrarMensaje("Nodo " + valor + " encontrado. " +
                            "Altura: " + alturaNodo + ", " +
                            "Hijos: " + cantidadHijos);
                    dibujarArbol(arbol.getRaiz(), 650, 50, 200, valor);
                } else {
                    mostrarMensaje("Nodo " + valor + " no encontrado.");
                    dibujarArbol(arbol.getRaiz(), 650, 50, 200, -1);
                }
            } catch (NumberFormatException ex) {
                mostrarMensaje("Error: Ingrese un número válido.");
            }
            campoTexto.clear();
        });

        Scene scene = new Scene(root, 1100, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button crearBoton(String texto, String colorNormal, String colorHover) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-font-size: 12; " +
                "-fx-background-color: " + colorNormal + "; " +
                "-fx-text-fill: #212121; " +
                "-fx-background-radius: 4; " +
                "-fx-border-radius: 4; " +
                "-fx-padding: 5 10; " +
                "-fx-cursor: hand;");

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-font-size: 12; " +
                "-fx-background-color: " + colorHover + "; " +
                "-fx-text-fill: #212121; " +
                "-fx-background-radius: 4; " +
                "-fx-border-radius: 4; " +
                "-fx-padding: 5 10; " +
                "-fx-cursor: hand;"));

        btn.setOnMouseExited(e -> btn.setStyle("-fx-font-size: 12; " +
                "-fx-background-color: " + colorNormal + "; " +
                "-fx-text-fill: #212121; " +
                "-fx-background-radius: 4; " +
                "-fx-border-radius: 4; " +
                "-fx-padding: 5 10; " +
                "-fx-cursor: hand;"));
        return btn;
    }

    private void mostrarMensaje(String mensaje) {
        areaTexto.setText(mensaje + "\n" + areaTexto.getText());
    }

    private void dibujarArbol(Nodo nodo, double x, double y, double espacio, int valorBuscado) {
        panelArbol.getChildren().clear();
        dibujarNodo(nodo, x, y, espacio, valorBuscado);
    }

    private void dibujarNodo(Nodo nodo, double x, double y, double espacio, int valorBuscado) {
        if (nodo != null) {
            if (nodo.izquierda != null) {
                Line linea = new Line(x - espacio, y + 50, x, y);
                linea.setStroke(Color.web("#E3F2FD"));
                panelArbol.getChildren().add(linea);
                dibujarNodo(nodo.izquierda, x - espacio, y + 50, espacio / 2, valorBuscado);
            }

            if (nodo.derecha != null) {
                Line linea = new Line(x + espacio, y + 50, x, y);
                linea.setStroke(Color.web("#E3F2FD"));
                panelArbol.getChildren().add(linea);
                dibujarNodo(nodo.derecha, x + espacio, y + 50, espacio / 2, valorBuscado);
            }

            Circle circulo = new Circle(x, y, 20);
            if (nodo.valor == valorBuscado && valorBuscado != -1) {
                circulo.setFill(Color.web("#FF5252"));
            } else {
                circulo.setFill(Color.web("#0D47A1"));
            }
            circulo.setStroke(Color.web("#E3F2FD"));

            Text texto = new Text(x - 5, y + 5, String.valueOf(nodo.valor));
            texto.setStyle("-fx-font-size: 13; -fx-fill: #ffffff;");

            panelArbol.getChildren().addAll(circulo, texto);
        }
    }

    private void guardarArbolEnArchivo() {
        String nombreArchivo = "arbol_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("Árbol Binario: " + obtenerArbolEnLinea(arbol.getRaiz()) + "\n");
            writer.write("---------------------------------------------------------\n");
            writer.write("Recorridos:\n");
            writer.write("PreOrden: " + arbol.preOrden().toString() + "\n");
            writer.write("InOrden: " + arbol.inOrden().toString() + "\n");
            writer.write("PostOrden: " + arbol.postOrden().toString() + "\n");

            mostrarMensaje("Árbol guardado en '" + nombreArchivo + "'");
        } catch (IOException e) {
            mostrarMensaje("Error al guardar el archivo");
        }
    }

    private void abrirArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Archivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto", "*.txt"));
        File archivo = fileChooser.showOpenDialog(null);

        if (archivo != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                StringBuilder contenido = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                areaTexto.setText(contenido.toString());

                reconstruirArbolDesdeArchivo(contenido.toString());
                mostrarMensaje("Archivo '" + archivo.getName() + "' cargado correctamente");
            } catch (IOException e) {
                mostrarMensaje("Error al leer el archivo");
            }
        } else {
            mostrarMensaje("Operación cancelada");
        }
    }

    private void reconstruirArbolDesdeArchivo(String contenido) {
        arbol = new ArbolBinario();

        String[] lineas = contenido.split("\n");
        if (lineas.length > 0 && lineas[0].startsWith("Árbol Binario: ")) {
            String arbolLinea = lineas[0].replace("Árbol Binario: ", "").trim();
            String[] valores = arbolLinea.split(", ");

            for (String valorStr : valores) {
                try {
                    int valor = Integer.parseInt(valorStr);
                    arbol.insertar(valor);
                } catch (NumberFormatException e) {
                }
            }

            dibujarArbol(arbol.getRaiz(), 650, 50, 200, -1);
        }
    }

    private String obtenerArbolEnLinea(Nodo nodo) {
        if (nodo == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        obtenerArbolEnLineaRec(nodo, sb);
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    private void obtenerArbolEnLineaRec(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.valor).append(", ");
            obtenerArbolEnLineaRec(nodo.izquierda, sb);
            obtenerArbolEnLineaRec(nodo.derecha, sb);
        }
    }
}