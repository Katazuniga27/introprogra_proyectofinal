/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package introprogra_proyectofinal1.pkg0;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * @author andreyvargassolis
 */
public class Parqueo {

    // Matrices que representan los 3 niveles del parqueo (G1, G2 y G3)
    private String[][] g1 = new String[4][5];
    private String[][] g2 = new String[5][5];
    private String[][] g3 = new String[6][5];

    // Constructor: al crear un objeto Parqueo, se inicializan los espacios
    public Parqueo() {
        inicializarParqueo();
    }

    // Llena las matrices con el estado inicial
    private void inicializarParqueo() {
        g1 = new String[][]{
            {"E", "O", "L", "L", "O"},
            {"L", "L", "L", "L", "L"},
            {"L", "O", "L", "L", "L"},
            {"D", "D", "D", "L", "E"}
        };
        g2 = new String[][]{
            {"O", "O", "L", "L", "O"},
            {"L", "L", "L", "L", "L"},
            {"L", "O", "L", "L", "L"},
            {"L", "L", "L", "L", "O"},
            {"D", "D", "D", "O", "O"}
        };
        g3 = new String[][]{
            {"O", "O", "L", "L", "O"},
            {"L", "L", "L", "L", "L"},
            {"L", "O", "L", "L", "L"},
            {"L", "L", "L", "L", "O"},
            {"O", "O", "E", "O", "O"},
            {"D", "D", "D", "L", "E"}
        };
    }

    // Menú principal para el usuario (versión texto - mantenido para compatibilidad)
    public void iniciarParqueo() {
        boolean salir = false;

        while (!salir) {
            String opcion = JOptionPane.showInputDialog("""
                PARQUEO - Seleccione una opción:
                1. Ver parqueo
                2. Reservar espacio
                3. Liberar espacio
                4. Salir
            """);

            if (opcion == null) {
                break;
            }

            switch (opcion) {
                case "1":
                    mostrarParqueo();
                    break;
                case "2":
                    Usuario uIn = seleccionarUsuarioParqueo();
                    if (uIn != null && uIn.isActivo()) {
                        asignarEspacioParqueo(uIn.getId());
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario inválido o inactivo.");
                    }
                    break;
                case "3":
                    Usuario uOut = seleccionarUsuarioParqueo();
                    if (uOut != null && uOut.isActivo()) {
                        liberarEspacioParqueo(uOut.getId());
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario inválido o inactivo.");
                    }
                    break;
                case "4":
                    salir = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }
        }
    }

    private Usuario seleccionarUsuarioParqueo() {
        String input = JOptionPane.showInputDialog("\nIngrese su ID:");
        if (input == null) {
            return null;
        }

        try {
            int id = Integer.parseInt(input);
            return Usuario.buscarPorId(id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
            return null;
        }
    }

    // Método para asignar un espacio de parqueo a un cliente con un ID específico
    private void asignarEspacioParqueo(int id) {
        // Solicita al usuario el nivel de parqueo (G1, G2 o G3)
        String nivel = JOptionPane.showInputDialog("Ingrese nivel (G1, G2, G3):");

        // Solicita al usuario la fila y columna donde quiere el espacio
        int fila = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fila:"));
        int columna = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la columna:"));

        // Obtiene la matriz (plano) correspondiente al nivel elegido
        String[][] matriz = escogerNivelParqueo(nivel);

        // Valida que la matriz exista y que la posición esté dentro de los límites
        if (matriz != null && validarPosicion(matriz, fila, columna)) {
            // Verifica si el espacio está libre (marcado como "L")
            if (matriz[fila][columna].equals("L")) {
                // Asigna el ID del cliente a esa posición en la matriz
                matriz[fila][columna] = String.valueOf(id);
                JOptionPane.showMessageDialog(null, "Espacio reservado exitosamente.");
            } else {
                // Si el espacio ya está ocupado, muestra el ID de quien lo ocupa
                JOptionPane.showMessageDialog(null, "Ese espacio ya está ocupado por ID: " + matriz[fila][columna]);
            }
        } else {
            // Si el nivel o la posición no son válidos, muestra mensaje de error
            JOptionPane.showMessageDialog(null, "Posición o nivel inválido.");
        }
    }

// Método para liberar un espacio de parqueo que estaba asignado a un cliente
    private void liberarEspacioParqueo(int id) {
        // Solicita al usuario el nivel de parqueo (G1, G2 o G3)
        String nivel = JOptionPane.showInputDialog("Ingrese nivel (G1, G2, G3):");

        // Solicita al usuario la fila y columna del espacio a liberar
        int fila = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fila:"));
        int columna = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la columna:"));

        // Obtiene la matriz del nivel elegido
        String[][] matriz = escogerNivelParqueo(nivel);

        // Verifica que el nivel y la posición sean válidos
        if (matriz != null && validarPosicion(matriz, fila, columna)) {
            // Comprueba que el espacio esté ocupado por el mismo ID que se quiere liberar
            if (matriz[fila][columna].equals(String.valueOf(id))) {
                // Marca el espacio como libre ("L")
                matriz[fila][columna] = "L";
                JOptionPane.showMessageDialog(null, "Espacio liberado correctamente.");
            } else {
                // Si el espacio no le pertenece al ID, muestra mensaje de error
                JOptionPane.showMessageDialog(null, "Este espacio no está reservado por usted.");
            }
        } else {
            // Si el nivel o la posición no son válidos, muestra mensaje de error
            JOptionPane.showMessageDialog(null, "Posición o nivel inválido.");
        }
    }

    private String[][] escogerNivelParqueo(String nivel) {
        switch (nivel.toUpperCase()) {
            case "G1":
                return g1;
            case "G2":
                return g2;
            case "G3":
                return g3;
            default:
                return null;
        }
    }

    private boolean validarPosicion(String[][] matriz, int fila, int columna) {
        return fila >= 0 && fila < matriz.length && columna >= 0 && columna < matriz[0].length;
    }

    private void mostrarParqueo() {
        StringBuilder sb = new StringBuilder("Estado actual del parqueo:\n");
        sb.append("\nG1:\n").append(matrizToString(g1));
        sb.append("\nG2:\n").append(matrizToString(g2));
        sb.append("\nG3:\n").append(matrizToString(g3));
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private String matrizToString(String[][] matriz) {
        StringBuilder sb = new StringBuilder();
        for (String[] fila : matriz) {
            for (String espacio : fila) {
                sb.append((espacio == null ? "L" : espacio)).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // 
    public void abrirInterfaz() {
        // === CREAR VENTANA PRINCIPAL ===
        JFrame frame = new JFrame("Sistema de Parqueo - Smart Fit");
        frame.setSize(1400, 900);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centra la ventana en pantalla

        // === FONDO CON IMAGEN ===
        FondoPanel fondo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondo.setLayout(new BorderLayout());
        frame.setContentPane(fondo); // Establece el panel de fondo como contenido

        // === PANEL SUPERIOR - TÍTULO ===
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false); // Transparente para ver el fondo
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 30, 15, 30)); // Márgenes

        JLabel titulo = new JLabel("SISTEMA DE PARQUEO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE); // Texto blanco

        JLabel subtitulo = new JLabel("Gestión inteligente de espacios de estacionamiento", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(200, 200, 200)); // Gris claro

        // === Panel de estadísticas rápidas ===
        JPanel panelStats = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelStats.setOpaque(false);

        // Calcula las estadísticas: libres, ocupados, especiales
        int[] stats = calcularEstadisticas();
        JLabel statsLibres = new JLabel("🟢 Libres: " + stats[0]);
        JLabel statsOcupados = new JLabel("🔴 Ocupados: " + stats[1]);
        JLabel statsEspeciales = new JLabel("🟡 Especiales: " + stats[2]);

        // Fuente y colores para estadísticas
        Font fontStats = new Font("Arial", Font.BOLD, 14);
        statsLibres.setFont(fontStats);
        statsLibres.setForeground(new Color(76, 175, 80)); // Verde
        statsOcupados.setFont(fontStats);
        statsOcupados.setForeground(new Color(244, 67, 54)); // Rojo
        statsEspeciales.setFont(fontStats);
        statsEspeciales.setForeground(new Color(255, 193, 7)); // Amarillo

        // Agrega las estadísticas al panel
        panelStats.add(statsLibres);
        panelStats.add(statsOcupados);
        panelStats.add(statsEspeciales);

        // Agrega título, subtítulo y estadísticas al panel superior
        panelTitulo.add(titulo, BorderLayout.NORTH);
        panelTitulo.add(subtitulo, BorderLayout.CENTER);
        panelTitulo.add(panelStats, BorderLayout.SOUTH);

        // === PANEL CENTRAL - NIVELES DEL PARQUEO ===
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Crea un sistema de pestañas para cambiar entre niveles
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setOpaque(false);
        pestanas.setFont(new Font("Arial", Font.BOLD, 16));
        pestanas.setForeground(Color.WHITE);

        // Crea paneles visuales para cada nivel
        JPanel panelG1 = crearPanelNivel("G1", g1, frame);
        JPanel panelG2 = crearPanelNivel("G2", g2, frame);
        JPanel panelG3 = crearPanelNivel("G3", g3, frame);

        // Agrega cada panel como una pestaña
        pestanas.addTab("NIVEL G1", panelG1);
        pestanas.addTab("NIVEL G2", panelG2);
        pestanas.addTab("NIVEL G3", panelG3);

        pestanas.setTabPlacement(JTabbedPane.TOP); // Pestañas arriba
        pestanas.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Añade las pestañas al panel central
        panelCentral.add(pestanas, BorderLayout.CENTER);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
        panelBotones.setOpaque(false);

        // Botón para reservar un espacio
        BotonRedondeado btnReservar = new BotonRedondeado("RESERVAR ESPACIO", 25);
        btnReservar.setPreferredSize(new Dimension(220, 55));
        btnReservar.setBackground(new Color(63, 81, 181)); // Azul índigo
        btnReservar.addActionListener(e -> mostrarDialogoReserva(frame));

        // Botón para liberar un espacio
        BotonRedondeado btnLiberar = new BotonRedondeado("LIBERAR ESPACIO", 25);
        btnLiberar.setPreferredSize(new Dimension(200, 55));
        btnLiberar.setBackground(new Color(255, 87, 34)); // Naranja
        btnLiberar.addActionListener(e -> mostrarDialogoLiberar(frame));

        // Botón para buscar un vehículo
        BotonRedondeado btnBuscar = new BotonRedondeado("BUSCAR VEHÍCULO", 25);
        btnBuscar.setPreferredSize(new Dimension(200, 55));
        btnBuscar.setBackground(new Color(156, 39, 176)); // Morado
        btnBuscar.addActionListener(e -> mostrarDialogoBuscar(frame));

        // Botón para mostrar estadísticas
        BotonRedondeado btnEstadisticas = new BotonRedondeado("ESTADÍSTICAS", 25);
        btnEstadisticas.setPreferredSize(new Dimension(170, 55));
        btnEstadisticas.setBackground(new Color(0, 150, 136)); // Verde agua
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas(frame));

        // Botón para cerrar la ventana
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 55));
        btnCerrar.setBackground(new Color(96, 125, 139)); // Gris azulado
        btnCerrar.addActionListener(e -> frame.dispose());

        // Agrega todos los botones al panel inferior
        panelBotones.add(btnReservar);
        panelBotones.add(btnLiberar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnCerrar);

        // === ENSAMBLAR VENTANA ===
        fondo.add(panelTitulo, BorderLayout.NORTH);
        fondo.add(panelCentral, BorderLayout.CENTER);
        fondo.add(panelBotones, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel crearPanelNivel(String nivel, String[][] matriz, JFrame parent) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título del nivel
        JLabel tituloNivel = new JLabel("NIVEL " + nivel, SwingConstants.CENTER);
        tituloNivel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloNivel.setForeground(Color.WHITE);
        tituloNivel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Grid de espacios
        JPanel gridEspacios = new JPanel(new GridLayout(matriz.length, matriz[0].length, 8, 8));
        gridEspacios.setOpaque(false);
        gridEspacios.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Crear cada espacio de parqueo
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                JPanel espacio = crearEspacioParqueo(nivel, i, j, matriz[i][j], parent);
                gridEspacios.add(espacio);
            }
        }

        // Panel de información del nivel
        JPanel panelInfo = crearPanelInfoNivel(nivel, matriz);

        panel.add(tituloNivel, BorderLayout.NORTH);
        panel.add(gridEspacios, BorderLayout.CENTER);
        panel.add(panelInfo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearEspacioParqueo(String nivel, int fila, int columna, String estado, JFrame parent) {
        // Crea un panel que representará visualmente un espacio de parqueo en la interfaz
        JPanel espacio = new JPanel();
        espacio.setLayout(new BorderLayout()); // Organiza el contenido en bordes (NORTE, SUR, CENTRO, etc.)
        espacio.setPreferredSize(new Dimension(80, 60)); // Tamaño fijo para cada espacio
        espacio.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Borde blanco inicial

        // Variables para determinar el color de fondo, icono y texto de ayuda (tooltip)
        Color colorFondo;
        String icono;
        String tooltip;

        // Según el estado del espacio, se asignan color, icono y descripción
        switch (estado) {
            case "L": // Libre
                colorFondo = new Color(76, 175, 80, 150); // Verde translúcido
                icono = "🟢"; // Emoji de círculo verde
                tooltip = "Espacio libre - Clic para reservar";
                break;
            case "E": // Especial (eléctrico)
                colorFondo = new Color(255, 193, 7, 150); // Amarillo translúcido
                icono = "⚡"; // Emoji de rayo
                tooltip = "Espacio especial (eléctrico)";
                break;
            case "D": // Discapacidad
                colorFondo = new Color(33, 150, 243, 150); // Azul translúcido
                icono = "♿"; // Emoji de silla de ruedas
                tooltip = "Espacio para personas con discapacidad";
                break;
            case "O": // Ocupado genérico
                colorFondo = new Color(158, 158, 158, 150); // Gris translúcido
                icono = "🔒"; // Emoji de candado
                tooltip = "Espacio ocupado";
                break;
            default: // Ocupado por un ID específico
                colorFondo = new Color(244, 67, 54, 150); // Rojo translúcido
                icono = "🚗"; // Emoji de carro
                tooltip = "Ocupado por ID: " + estado; // Muestra el ID del ocupante
                break;
        }

        // Asigna el color de fondo y el tooltip al panel
        espacio.setBackground(colorFondo);
        espacio.setToolTipText(tooltip);

        // Crea una etiqueta para mostrar el icono en el centro
        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 20)); // Tamaño de letra mediano

        // Crea una etiqueta para mostrar la posición (fila, columna) en la parte inferior
        JLabel lblPosicion = new JLabel("(" + fila + "," + columna + ")", SwingConstants.CENTER);
        lblPosicion.setFont(new Font("Arial", Font.BOLD, 10)); // Letra pequeña
        lblPosicion.setForeground(Color.WHITE); // Texto en blanco

        // Añade el icono y la posición al panel
        espacio.add(lblIcono, BorderLayout.CENTER);
        espacio.add(lblPosicion, BorderLayout.SOUTH);

        // Agrega interactividad con el mouse
        espacio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cuando el cursor entra, cambia el borde a amarillo y el puntero a mano
                espacio.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                espacio.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Cuando el cursor sale, vuelve el borde y el puntero a su estado original
                espacio.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                espacio.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Al hacer clic, muestra un cuadro con los detalles del espacio
                mostrarDetallesEspacio(nivel, fila, columna, estado, parent);
            }
        });

        // Devuelve el panel listo para añadirse a la interfaz
        return espacio;
    }
    
    
    private JPanel crearPanelInfoNivel(String nivel, String[][] matriz) {
    // Crea un panel con distribución FlowLayout centrado, espacio entre componentes de 30 px horizontal y 10 px vertical
    JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
    panelInfo.setOpaque(false); // Panel transparente (sin fondo sólido)
    
    // Agrega un borde compuesto: un borde blanco translúcido de 1 px + un margen interno (padding) de 15-20 px
    panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
    ));

    // Variables para contar los espacios por tipo dentro de la matriz
    int libres = 0, ocupados = 0, especiales = 0, discapacitados = 0;

    // Recorre todas las filas y columnas de la matriz de espacios
    for (String[] fila : matriz) {
        for (String espacio : fila) {
            switch (espacio) {
                case "L" -> libres++;            // Espacio libre
                case "E" -> especiales++;       // Espacio especial (eléctrico)
                case "D" -> discapacitados++;   // Espacio para discapacitados
                case "O" -> ocupados++;         // Ocupado genérico
                default -> ocupados++;           // Espacio ocupado con ID específico
            }
        }
    }

    // Crea etiquetas (JLabels) para mostrar las estadísticas con iconos y texto
    JLabel lblLibres = new JLabel("🟢 Libres: " + libres);
    JLabel lblOcupados = new JLabel("🔴 Ocupados: " + ocupados);
    JLabel lblEspeciales = new JLabel("⚡ Eléctricos: " + especiales);
    JLabel lblDiscapacitados = new JLabel("♿ Discapacitados: " + discapacitados);

    // Define fuente y color blanco para las etiquetas
    Font fontInfo = new Font("Arial", Font.BOLD, 12);
    Color colorTexto = Color.WHITE;

    // Aplica la fuente y color a cada etiqueta
    lblLibres.setFont(fontInfo);
    lblLibres.setForeground(colorTexto);
    lblOcupados.setFont(fontInfo);
    lblOcupados.setForeground(colorTexto);
    lblEspeciales.setFont(fontInfo);
    lblEspeciales.setForeground(colorTexto);
    lblDiscapacitados.setFont(fontInfo);
    lblDiscapacitados.setForeground(colorTexto);

    // Añade las etiquetas al panel
    panelInfo.add(lblLibres);
    panelInfo.add(lblOcupados);
    panelInfo.add(lblEspeciales);
    panelInfo.add(lblDiscapacitados);

    // Devuelve el panel listo para insertarlo en la interfaz
    return panelInfo;
}

private void mostrarDetallesEspacio(String nivel, int fila, int columna, String estado, JFrame parent) {
    // Construye un mensaje con detalles del espacio para mostrar en un diálogo
    StringBuilder detalles = new StringBuilder();
    detalles.append("=== DETALLES DEL ESPACIO ===\n\n");
    detalles.append("📍 Ubicación: ").append(nivel).append(" - Fila ").append(fila).append(", Columna ").append(columna).append("\n\n");

    // Según el estado del espacio, se agregan detalles específicos al mensaje
    switch (estado) {
        case "L":
            detalles.append("🟢 Estado: LIBRE\n");
            detalles.append("✅ Disponible para reservar\n");
            break;
        case "E":
            detalles.append("⚡ Estado: ESPECIAL (Eléctrico)\n");
            detalles.append("🔋 Para vehículos eléctricos únicamente\n");
            break;
        case "D":
            detalles.append("♿ Estado: DISCAPACITADOS\n");
            detalles.append("🚗 Para personas con discapacidad\n");
            break;
        case "O":
            detalles.append("🔒 Estado: OCUPADO\n");
            detalles.append("❌ No disponible\n");
            break;
        default:
            // Si el espacio está ocupado por un ID específico (número)
            detalles.append("🚗 Estado: OCUPADO\n");
            detalles.append("👤 Reservado por ID: ").append(estado).append("\n");

            // Intenta buscar información del usuario por el ID
            try {
                Usuario usuario = Usuario.buscarPorId(Integer.parseInt(estado));
                if (usuario != null) {
                    detalles.append("📝 Usuario: ").append(usuario.getNombre()).append("\n");
                }
            } catch (Exception e) {
                // Si hay error (por ejemplo, no es un número válido), no se muestra info adicional
            }
            break;
    }

    // Muestra el mensaje con detalles en un cuadro de diálogo modal
    JOptionPane.showMessageDialog(parent, detalles.toString(),
            "Espacio " + nivel + " (" + fila + "," + columna + ")", JOptionPane.INFORMATION_MESSAGE);
}
private void mostrarDialogoReserva(JFrame parent) {
    // Crear un diálogo modal con título, tamaño y centrado relativo a la ventana padre
    JDialog dialogo = new JDialog(parent, "Reservar Espacio de Parqueo", true);
    dialogo.setSize(550, 500);
    dialogo.setLocationRelativeTo(parent);

    // Crear panel de fondo con imagen y establecer layout BorderLayout
    FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
    fondoDialogo.setLayout(new BorderLayout());
    dialogo.setContentPane(fondoDialogo);

    // Panel principal que contendrá todo el contenido del diálogo
    JPanel panelPrincipal = new JPanel(new BorderLayout());
    panelPrincipal.setOpaque(false); // Fondo transparente
    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25)); // Márgenes internos

    // Etiqueta de título centrada, con fuente grande y color azul índigo
    JLabel titulo = new JLabel("RESERVAR ESPACIO", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 26));
    titulo.setForeground(new Color(63, 81, 181));
    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0)); // Margen abajo

    // Panel para formulario con 4 filas y 2 columnas, espacios entre componentes
    JPanel panelForm = new JPanel(new GridLayout(4, 2, 15, 15));
    panelForm.setOpaque(false);

    // Label y campo de texto para ingresar ID de usuario
    JLabel lblID = new JLabel("ID de Usuario:");
    lblID.setFont(new Font("Arial", Font.BOLD, 16));
    lblID.setForeground(Color.WHITE);

    JTextField txtID = new JTextField();
    txtID.setFont(new Font("Arial", Font.PLAIN, 14));
    txtID.setPreferredSize(new Dimension(0, 35));
    estilarCampoTexto(txtID); // Método para aplicar estilo personalizado al JTextField

    // Label y combo para seleccionar nivel del parqueo
    JLabel lblNivel = new JLabel("Nivel:");
    lblNivel.setFont(new Font("Arial", Font.BOLD, 16));
    lblNivel.setForeground(Color.WHITE);

    JComboBox<String> comboNivel = new JComboBox<>(new String[]{"G1", "G2", "G3"});
    comboNivel.setFont(new Font("Arial", Font.PLAIN, 14));
    comboNivel.setPreferredSize(new Dimension(0, 35));

    // Label y spinner para seleccionar fila (número entre 0 y 5)
    JLabel lblFila = new JLabel("Fila:");
    lblFila.setFont(new Font("Arial", Font.BOLD, 16));
    lblFila.setForeground(Color.WHITE);

    JSpinner spinnerFila = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
    spinnerFila.setFont(new Font("Arial", Font.PLAIN, 14));
    spinnerFila.setPreferredSize(new Dimension(0, 35));

    // Label y spinner para seleccionar columna (número entre 0 y 4)
    JLabel lblColumna = new JLabel("Columna:");
    lblColumna.setFont(new Font("Arial", Font.BOLD, 16));
    lblColumna.setForeground(Color.WHITE);

    JSpinner spinnerColumna = new JSpinner(new SpinnerNumberModel(0, 0, 4, 1));
    spinnerColumna.setFont(new Font("Arial", Font.PLAIN, 14));
    spinnerColumna.setPreferredSize(new Dimension(0, 35));

    // Agregar componentes al panel del formulario en orden
    panelForm.add(lblID);
    panelForm.add(txtID);
    panelForm.add(lblNivel);
    panelForm.add(comboNivel);
    panelForm.add(lblFila);
    panelForm.add(spinnerFila);
    panelForm.add(lblColumna);
    panelForm.add(spinnerColumna);

    // Panel para botones con espacio entre ellos y fondo transparente
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
    panelBotones.setOpaque(false);

    // Botón para confirmar la reserva, estilo personalizado con fondo azul índigo
    BotonRedondeado btnConfirmar = new BotonRedondeado("RESERVAR", 25);
    btnConfirmar.setPreferredSize(new Dimension(140, 45));
    btnConfirmar.setBackground(new Color(63, 81, 181));

    // Botón para cancelar la acción, estilo gris azulado
    BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 25);
    btnCancelar.setPreferredSize(new Dimension(130, 45));
    btnCancelar.setBackground(new Color(96, 125, 139));

    // Acción al presionar "RESERVAR"
    btnConfirmar.addActionListener(e -> {
        try {
            String idStr = txtID.getText().trim();
            // Validar que se haya ingresado un ID
            if (idStr.isEmpty()) {
                mostrarMensajeError(dialogo, "Debe ingresar un ID");
                return;
            }

            int id = Integer.parseInt(idStr); // Convertir ID a entero
            Usuario usuario = Usuario.buscarPorId(id); // Buscar usuario por ID

            // Validar que el usuario exista y esté activo
            if (usuario == null || !usuario.isActivo()) {
                mostrarMensajeError(dialogo, "Usuario inválido o inactivo");
                return;
            }

            // Obtener nivel, fila y columna seleccionados
            String nivel = (String) comboNivel.getSelectedItem();
            int fila = (Integer) spinnerFila.getValue();
            int columna = (Integer) spinnerColumna.getValue();

            // Obtener matriz correspondiente al nivel
            String[][] matriz = escogerNivelParqueo(nivel);

            // Validar que matriz y posición sean válidas
            if (matriz != null && validarPosicion(matriz, fila, columna)) {
                String estadoActual = matriz[fila][columna];

                // Si espacio está libre, reservarlo
                if (estadoActual.equals("L")) {
                    matriz[fila][columna] = String.valueOf(id);
                    mostrarMensajeExito(dialogo, "Espacio reservado exitosamente en " + nivel + " (" + fila + "," + columna + ")");
                    dialogo.dispose();  // Cierra diálogo de reserva
                    parent.dispose();   // Cierra ventana padre (posiblemente para refrescar)
                    abrirInterfaz();    // Abre nuevamente la interfaz para refrescar visualmente
                } else {
                    mostrarMensajeError(dialogo, "Espacio no disponible");
                }
            } else {
                mostrarMensajeError(dialogo, "Posición inválida");
            }
        } catch (NumberFormatException ex) {
            mostrarMensajeError(dialogo, "ID debe ser un número válido");
        }
    });

    // Acción al presionar "CANCELAR": cierra el diálogo
    btnCancelar.addActionListener(e -> dialogo.dispose());

    // Agregar botones al panel de botones
    panelBotones.add(btnConfirmar);
    panelBotones.add(btnCancelar);

    // Agregar componentes al panel principal
    panelPrincipal.add(titulo, BorderLayout.NORTH);
    panelPrincipal.add(panelForm, BorderLayout.CENTER);
    panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

    // Agregar panel principal al fondo del diálogo
    fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);

    // Mostrar diálogo (modal, bloquea hasta que se cierre)
    dialogo.setVisible(true);
}

    private void mostrarDialogoLiberar(JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Liberar Espacio de Parqueo", true);
        dialogo.setSize(550, 500);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 25, 20, 25));

        // Título
        JLabel titulo = new JLabel("LIBERAR ESPACIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(255, 87, 34));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 15, 15));
        panelForm.setOpaque(false);

        // Campo ID
        JLabel lblID = new JLabel("ID de Usuario:");
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        lblID.setForeground(Color.WHITE);

        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        txtID.setPreferredSize(new Dimension(0, 35));
        estilarCampoTexto(txtID);

        // Campo Nivel
        JLabel lblNivel = new JLabel("Nivel:");
        lblNivel.setFont(new Font("Arial", Font.BOLD, 16));
        lblNivel.setForeground(Color.WHITE);

        JComboBox<String> comboNivel = new JComboBox<>(new String[]{"G1", "G2", "G3"});
        comboNivel.setFont(new Font("Arial", Font.PLAIN, 14));
        comboNivel.setPreferredSize(new Dimension(0, 35));

        // Campo Fila
        JLabel lblFila = new JLabel("Fila:");
        lblFila.setFont(new Font("Arial", Font.BOLD, 16));
        lblFila.setForeground(Color.WHITE);

        JSpinner spinnerFila = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
        spinnerFila.setFont(new Font("Arial", Font.PLAIN, 14));
        spinnerFila.setPreferredSize(new Dimension(0, 35));

        // Campo Columna
        JLabel lblColumna = new JLabel("Columna:");
        lblColumna.setFont(new Font("Arial", Font.BOLD, 16));
        lblColumna.setForeground(Color.WHITE);

        JSpinner spinnerColumna = new JSpinner(new SpinnerNumberModel(0, 0, 4, 1));
        spinnerColumna.setFont(new Font("Arial", Font.PLAIN, 14));
        spinnerColumna.setPreferredSize(new Dimension(0, 35));

        panelForm.add(lblID);
        panelForm.add(txtID);
        panelForm.add(lblNivel);
        panelForm.add(comboNivel);
        panelForm.add(lblFila);
        panelForm.add(spinnerFila);
        panelForm.add(lblColumna);
        panelForm.add(spinnerColumna);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("LIBERAR", 25);
        btnConfirmar.setPreferredSize(new Dimension(140, 45));
        btnConfirmar.setBackground(new Color(255, 87, 34));

        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 25);
        btnCancelar.setPreferredSize(new Dimension(130, 45));
        btnCancelar.setBackground(new Color(96, 125, 139));

        btnConfirmar.addActionListener(e -> {
            try {
                String idStr = txtID.getText().trim();
                if (idStr.isEmpty()) {
                    mostrarMensajeError(dialogo, "Debe ingresar un ID");
                    return;
                }

                int id = Integer.parseInt(idStr);
                Usuario usuario = Usuario.buscarPorId(id);
                if (usuario == null || !usuario.isActivo()) {
                    mostrarMensajeError(dialogo, "Usuario inválido o inactivo");
                    return;
                }

                String nivel = (String) comboNivel.getSelectedItem();
                int fila = (Integer) spinnerFila.getValue();
                int columna = (Integer) spinnerColumna.getValue();

                String[][] matriz = escogerNivelParqueo(nivel);
                if (matriz != null && validarPosicion(matriz, fila, columna)) {
                    String estadoActual = matriz[fila][columna];
                    if (estadoActual.equals(String.valueOf(id))) {
                        matriz[fila][columna] = "L";
                        mostrarMensajeExito(dialogo, "Espacio liberado exitosamente en " + nivel + " (" + fila + "," + columna + ")");
                        dialogo.dispose();
                        parent.dispose();
                        abrirInterfaz(); // Refrescar
                    } else {
                        mostrarMensajeError(dialogo, "Este espacio no está reservado por usted");
                    }
                } else {
                    mostrarMensajeError(dialogo, "Posición inválida");
                }
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelForm, BorderLayout.CENTER);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    
    private void mostrarDialogoBuscar(JFrame parent) {

        // Crear el diálogo modal (bloquea la ventana principal hasta cerrarse)
        JDialog dialogo = new JDialog(parent, "Buscar Vehículo", true);
        dialogo.setSize(500, 350); // Tamaño de la ventana
        dialogo.setLocationRelativeTo(parent); // Centrar respecto a la ventana principal

        // Fondo personalizado usando una imagen
        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout()); // Distribución por regiones
        dialogo.setContentPane(fondoDialogo); // Establecer el panel como contenido principal

        // Panel principal transparente donde van todos los elementos
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false); // Permitir que se vea el fondo
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30)); // Márgenes internos

        // ===== TÍTULOS =====
        JLabel titulo = new JLabel("BUSCAR VEHÍCULO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(156, 39, 176)); // Morado vibrante

        JLabel subtitulo = new JLabel("Ingrese el ID para encontrar su vehículo", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitulo.setForeground(new Color(200, 200, 200)); // Gris claro
        subtitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Panel que agrupa título y subtítulo
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // ===== CAMPO DE TEXTO =====
        JPanel panelCentral = new JPanel(new BorderLayout(0, 15)); // Espaciado vertical
        panelCentral.setOpaque(false);

        JLabel lblID = new JLabel("ID de Usuario:");
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        lblID.setForeground(Color.WHITE);

        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 16));
        txtID.setPreferredSize(new Dimension(0, 40));
        txtID.setBackground(new Color(255, 255, 255, 240)); // Blanco con leve transparencia
        txtID.setForeground(new Color(33, 33, 33)); // Texto gris oscuro
        // Borde morado y margen interno
        txtID.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(156, 39, 176, 150), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtID.setHorizontalAlignment(JTextField.CENTER); // Centrar el texto

        panelCentral.add(lblID, BorderLayout.NORTH); // Etiqueta arriba
        panelCentral.add(txtID, BorderLayout.CENTER); // Campo de texto abajo

        // ===== BOTONES =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setOpaque(false);

        // Botón para buscar vehículo
        BotonRedondeado btnBuscar = new BotonRedondeado("BUSCAR", 25);
        btnBuscar.setPreferredSize(new Dimension(120, 45));
        btnBuscar.setBackground(new Color(156, 39, 176));

        // Botón para cerrar el diálogo
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(100, 45));
        btnCerrar.setBackground(new Color(96, 125, 139));

        // Acción del botón "Buscar"
        btnBuscar.addActionListener(e -> {
            try {
                // Convertir el texto ingresado a número entero
                int id = Integer.parseInt(txtID.getText().trim());
                // Llamar al método que busca el vehículo en el sistema
                String ubicacion = buscarVehiculo(id);

                // Si existe el vehículo, mostrar mensaje de éxito
                if (ubicacion != null) {
                    mostrarMensajeExito(dialogo, "Vehículo encontrado en: " + ubicacion);
                } // Si no se encuentra, mostrar mensaje de error
                else {
                    mostrarMensajeError(dialogo, "No se encontró ningún vehículo con ese ID");
                }
            } // Si el ID no es un número válido
            catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        // Acción del botón "Cerrar"
        btnCerrar.addActionListener(e -> dialogo.dispose());

        // Agregar botones al panel de botones
        panelBotones.add(btnBuscar);
        panelBotones.add(btnCerrar);

        // Agregar título y campo de texto al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Colocar paneles dentro del fondo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar el diálogo en pantalla
        dialogo.setVisible(true);
    }

    private String buscarVehiculo(int id) {
        String idStr = String.valueOf(id);

        // Buscar en G1
        for (int i = 0; i < g1.length; i++) {
            for (int j = 0; j < g1[i].length; j++) {
                if (idStr.equals(g1[i][j])) {
                    return "Nivel G1 - Fila " + i + ", Columna " + j;
                }
            }
        }

        // Buscar en G2
        for (int i = 0; i < g2.length; i++) {
            for (int j = 0; j < g2[i].length; j++) {
                if (idStr.equals(g2[i][j])) {
                    return "Nivel G2 - Fila " + i + ", Columna " + j;
                }
            }
        }

        // Buscar en G3
        for (int i = 0; i < g3.length; i++) {
            for (int j = 0; j < g3[i].length; j++) {
                if (idStr.equals(g3[i][j])) {
                    return "Nivel G3 - Fila " + i + ", Columna " + j;
                }
            }
        }

        return null; // No encontrado
    }

    private void mostrarEstadisticas(JFrame parent) {
        int[] stats = calcularEstadisticas();
        int totalEspacios = stats[0] + stats[1] + stats[2] + stats[3];
        
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("=== ESTADÍSTICAS DEL PARQUEO ===\n\n");
        mensaje.append("📊 RESUMEN GENERAL:\n");
        mensaje.append("• Total de espacios: ").append(totalEspacios).append("\n");
        mensaje.append("• Espacios libres: ").append(stats[0]).append(" (").append(String.format("%.1f%%", (stats[0] * 100.0) / totalEspacios)).append(")\n");
        mensaje.append("• Espacios ocupados: ").append(stats[1]).append(" (").append(String.format("%.1f%%", (stats[1] * 100.0) / totalEspacios)).append(")\n");
        mensaje.append("• Espacios eléctricos: ").append(stats[2]).append("\n");
        mensaje.append("• Espacios discapacitados: ").append(stats[3]).append("\n\n");
        
        mensaje.append("🏢 DETALLE POR NIVEL:\n");
        mensaje.append(obtenerEstadisticasNivel("G1", g1));
        mensaje.append(obtenerEstadisticasNivel("G2", g2));
        mensaje.append(obtenerEstadisticasNivel("G3", g3));

        // Crear diálogo personalizado
        JDialog dialogo = new JDialog(parent, "Estadísticas del Parqueo", true);
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Título
        JLabel titulo = new JLabel("ESTADÍSTICAS DEL PARQUEO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 150, 136));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Área de texto con estadísticas
        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBackground(new Color(0, 0, 0, 180));
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100)));

        // Botón cerrar
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(120, 40));
        btnCerrar.setBackground(new Color(96, 125, 139));
        btnCerrar.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBoton.add(btnCerrar);

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private String obtenerEstadisticasNivel(String nivel, String[][] matriz) {
        int libres = 0, ocupados = 0, especiales = 0, discapacitados = 0;
        
        for (String[] fila : matriz) {
            for (String espacio : fila) {
                switch (espacio) {
                    case "L" -> libres++;
                    case "E" -> especiales++;
                    case "D" -> discapacitados++;
                    case "O" -> ocupados++;
                    default -> ocupados++; // IDs específicos
                }
            }
        }
        
        int total = libres + ocupados + especiales + discapacitados;
        StringBuilder sb = new StringBuilder();
        sb.append("  ").append(nivel).append(" (").append(total).append(" espacios):\n");
        sb.append("    - Libres: ").append(libres).append("\n");
        sb.append("    - Ocupados: ").append(ocupados).append("\n");
        sb.append("    - Eléctricos: ").append(especiales).append("\n");
        sb.append("    - Discapacitados: ").append(discapacitados).append("\n\n");
        
        return sb.toString();
    }

    private int[] calcularEstadisticas() {
        int libres = 0, ocupados = 0, especiales = 0, discapacitados = 0;
        
        // Contar en todos los niveles
        String[][][] niveles = {g1, g2, g3};
        
        for (String[][] nivel : niveles) {
            for (String[] fila : nivel) {
                for (String espacio : fila) {
                    switch (espacio) {
                        case "L" -> libres++;
                        case "E" -> especiales++;
                        case "D" -> discapacitados++;
                        case "O" -> ocupados++;
                        default -> ocupados++; // IDs específicos
                    }
                }
            }
        }
        
        return new int[]{libres, ocupados, especiales, discapacitados};
    }

    private void estilarCampoTexto(JTextField campo) {
        campo.setBackground(new Color(255, 255, 255, 240));
        campo.setForeground(new Color(33, 33, 33));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void mostrarMensajeExito(Component parent, String mensaje) {
        // Crear diálogo personalizado de éxito
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Operación Exitosa", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true);

        // Panel principal con fondo degradado
        JPanel panelPrincipal = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Degradado verde
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(76, 175, 80, 250),
                        0, getHeight(), new Color(56, 142, 60, 250)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Borde
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
            }
        };

        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Icono de éxito
        JLabel iconoExito = new JLabel("✅", SwingConstants.CENTER);
        iconoExito.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoExito.setPreferredSize(new Dimension(0, 60));

        // Mensaje
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        // Botón OK 
        BotonRedondeado btnOK = new BotonRedondeado("PERFECTO", 20);
        btnOK.setPreferredSize(new Dimension(120, 35));
        btnOK.setBackground(new Color(255, 255, 255, 200));
        btnOK.setForeground(new Color(76, 175, 80));
        btnOK.setFont(new Font("Arial", Font.BOLD, 14));
        btnOK.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnOK);

        panelPrincipal.add(iconoExito, BorderLayout.NORTH);
        panelPrincipal.add(lblMensaje, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setContentPane(panelPrincipal);

        // Auto-cerrar después de 3 segundos
        Timer timer = new Timer(3000, e -> dialogo.dispose());
        timer.setRepeats(false);
        timer.start();

        dialogo.setVisible(true);
    }

    private void mostrarMensajeError(Component parent, String mensaje) {
        // Crear un diálogo modal personalizado para mostrar un mensaje de error
        // Se obtiene la ventana padre a partir del componente 'parent' para que el diálogo sea modal sobre ella
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Error", true);
        dialogo.setSize(400, 200);  // Tamaño fijo del diálogo
        dialogo.setLocationRelativeTo(parent);  // Centrar el diálogo respecto al componente padre
        dialogo.setUndecorated(true);  // Quitar la barra superior del diálogo (sin bordes ni botones estándar)

        // Crear un JPanel con pintura personalizada para el fondo degradado rojo con bordes redondeados
        JPanel panelPrincipal = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                //super.paintComponent(g);

                // Convertir Graphics a Graphics2D para usar características avanzadas
                Graphics2D g2d = (Graphics2D) g;
                // Activar antialiasing para suavizar el degradado y bordes
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Crear un degradado vertical de rojo fuerte a rojo oscuro con transparencia
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(244, 67, 54, 250), // Color rojo arriba (RGBA)
                        0, getHeight(), new Color(198, 40, 40, 250) // Color rojo más oscuro abajo
                );
                g2d.setPaint(gradient);
                // Dibujar un rectángulo redondeado con el degradado como fondo
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Dibujar borde blanco semi-transparente alrededor del panel con grosor 2
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
            }
        };

        panelPrincipal.setLayout(new BorderLayout());  // Layout para organizar icono, texto y botón
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Espaciado interno

        // Crear etiqueta para el icono de error (emoji cruz roja)
        JLabel iconoError = new JLabel("❌", SwingConstants.CENTER);
        iconoError.setFont(new Font("Arial", Font.PLAIN, 40));  // Tamaño grande para visibilidad
        iconoError.setPreferredSize(new Dimension(0, 60));  // Altura fija para el icono

        // Etiqueta para mostrar el mensaje de error con formato HTML para centrar texto
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);  // Texto blanco para contraste sobre fondo rojo

        // Botón personalizado para cerrar el diálogo, con fondo blanco y texto rojo
        BotonRedondeado btnOK = new BotonRedondeado("ENTENDIDO", 20);
        btnOK.setPreferredSize(new Dimension(130, 35));
        btnOK.setBackground(new Color(255, 255, 255, 200));  // Blanco semitransparente
        btnOK.setForeground(new Color(244, 67, 54));  // Texto rojo
        btnOK.setFont(new Font("Arial", Font.BOLD, 14));
        // Al hacer clic, cerrar el diálogo
        btnOK.addActionListener(e -> dialogo.dispose());

        // Panel para el botón, con FlowLayout centrado y transparente para dejar ver el fondo
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnOK);

        // Agregar componentes al panel principal en posiciones del BorderLayout
        panelPrincipal.add(iconoError, BorderLayout.NORTH);   // Icono arriba
        panelPrincipal.add(lblMensaje, BorderLayout.CENTER);  // Mensaje en el centro
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);   // Botón abajo

        // Establecer el panel personalizado como contenido del diálogo
        dialogo.setContentPane(panelPrincipal);

        // Crear efecto de vibración para captar atención al mostrar el diálogo
        Timer vibrar = new Timer(50, null);  // Evento cada 50 ms
        final int[] contador = {0};           // Contador para número de movimientos
        final Point posicionOriginal = dialogo.getLocation();  // Guardar posición original

        vibrar.addActionListener(e -> {
            if (contador[0] < 6) {
                // Alternar desplazamiento horizontal a la izquierda y derecha (5 píxeles)
                int offset = (contador[0] % 2 == 0) ? 5 : -5;
                dialogo.setLocation(posicionOriginal.x + offset, posicionOriginal.y);
                contador[0]++;
            } else {
                // Volver a la posición original y detener la vibración
                dialogo.setLocation(posicionOriginal);
                vibrar.stop();
            }
        });

        // Iniciar la vibración justo antes de mostrar el diálogo
        vibrar.start();

        // Mostrar el diálogo de forma modal (bloquea interacción con la ventana padre)
        dialogo.setVisible(true);
    }

}
