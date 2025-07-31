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

    private void asignarEspacioParqueo(int id) {
        String nivel = JOptionPane.showInputDialog("Ingrese nivel (G1, G2, G3):");
        int fila = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fila:"));
        int columna = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la columna:"));

        String[][] matriz = escogerNivelParqueo(nivel);
        if (matriz != null && validarPosicion(matriz, fila, columna)) {
            if (matriz[fila][columna].equals("L")) {
                matriz[fila][columna] = String.valueOf(id);
                JOptionPane.showMessageDialog(null, "Espacio reservado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Ese espacio ya está ocupado por ID: " + matriz[fila][columna]);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Posición o nivel inválido.");
        }
    }

    private void liberarEspacioParqueo(int id) {
        String nivel = JOptionPane.showInputDialog("Ingrese nivel (G1, G2, G3):");
        int fila = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la fila:"));
        int columna = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la columna:"));

        String[][] matriz = escogerNivelParqueo(nivel);
        if (matriz != null && validarPosicion(matriz, fila, columna)) {
            if (matriz[fila][columna].equals(String.valueOf(id))) {
                matriz[fila][columna] = "L";
                JOptionPane.showMessageDialog(null, "Espacio liberado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Este espacio no está reservado por usted.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Posición o nivel inválido.");
        }
    }

    private String[][] escogerNivelParqueo(String nivel) {
        switch (nivel.toUpperCase()) {
            case "G1": return g1;
            case "G2": return g2;
            case "G3": return g3;
            default: return null;
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

    // ========== NUEVA INTERFAZ MODERNA ==========
    public void abrirInterfaz() {
        // === CREAR VENTANA PRINCIPAL ===
        JFrame frame = new JFrame("Sistema de Parqueo - Smart Fit");
        frame.setSize(1400, 900);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // === FONDO CON IMAGEN ===
        FondoPanel fondo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondo.setLayout(new BorderLayout());
        frame.setContentPane(fondo);

        // === PANEL SUPERIOR - TÍTULO ===
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 30, 15, 30));

        JLabel titulo = new JLabel("SISTEMA DE PARQUEO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Gestión inteligente de espacios de estacionamiento", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(200, 200, 200));

        // Panel de estadísticas rápidas
        JPanel panelStats = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelStats.setOpaque(false);

        int[] stats = calcularEstadisticas();
        JLabel statsLibres = new JLabel("🟢 Libres: " + stats[0]);
        JLabel statsOcupados = new JLabel("🔴 Ocupados: " + stats[1]);
        JLabel statsEspeciales = new JLabel("🟡 Especiales: " + stats[2]);

        Font fontStats = new Font("Arial", Font.BOLD, 14);
        statsLibres.setFont(fontStats);
        statsLibres.setForeground(new Color(76, 175, 80));
        statsOcupados.setFont(fontStats);
        statsOcupados.setForeground(new Color(244, 67, 54));
        statsEspeciales.setFont(fontStats);
        statsEspeciales.setForeground(new Color(255, 193, 7));

        panelStats.add(statsLibres);
        panelStats.add(statsOcupados);
        panelStats.add(statsEspeciales);

        panelTitulo.add(titulo, BorderLayout.NORTH);
        panelTitulo.add(subtitulo, BorderLayout.CENTER);
        panelTitulo.add(panelStats, BorderLayout.SOUTH);

        // === PANEL CENTRAL - NIVELES DEL PARQUEO ===
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Crear pestañas para los niveles
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setOpaque(false);
        pestanas.setFont(new Font("Arial", Font.BOLD, 16));
        pestanas.setForeground(Color.WHITE);

        // Crear paneles para cada nivel
        JPanel panelG1 = crearPanelNivel("G1", g1, frame);
        JPanel panelG2 = crearPanelNivel("G2", g2, frame);
        JPanel panelG3 = crearPanelNivel("G3", g3, frame);

        pestanas.addTab("NIVEL G1", panelG1);
        pestanas.addTab("NIVEL G2", panelG2);
        pestanas.addTab("NIVEL G3", panelG3);

        // Personalizar pestañas
        pestanas.setTabPlacement(JTabbedPane.TOP);
        pestanas.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        panelCentral.add(pestanas, BorderLayout.CENTER);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
        panelBotones.setOpaque(false);

        BotonRedondeado btnReservar = new BotonRedondeado("RESERVAR ESPACIO", 25);
        btnReservar.setPreferredSize(new Dimension(220, 55));
        btnReservar.setBackground(new Color(63, 81, 181)); // Azul índigo
        btnReservar.addActionListener(e -> mostrarDialogoReserva(frame));

        BotonRedondeado btnLiberar = new BotonRedondeado("LIBERAR ESPACIO", 25);
        btnLiberar.setPreferredSize(new Dimension(200, 55));
        btnLiberar.setBackground(new Color(255, 87, 34)); // Naranja profundo
        btnLiberar.addActionListener(e -> mostrarDialogoLiberar(frame));

        BotonRedondeado btnBuscar = new BotonRedondeado("BUSCAR VEHÍCULO", 25);
        btnBuscar.setPreferredSize(new Dimension(200, 55));
        btnBuscar.setBackground(new Color(156, 39, 176)); // Morado
        btnBuscar.addActionListener(e -> mostrarDialogoBuscar(frame));

        BotonRedondeado btnEstadisticas = new BotonRedondeado("ESTADÍSTICAS", 25);
        btnEstadisticas.setPreferredSize(new Dimension(170, 55));
        btnEstadisticas.setBackground(new Color(0, 150, 136)); // Verde agua
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas(frame));

        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 55));
        btnCerrar.setBackground(new Color(96, 125, 139)); // Gris azulado
        btnCerrar.addActionListener(e -> frame.dispose());

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
        JPanel espacio = new JPanel();
        espacio.setLayout(new BorderLayout());
        espacio.setPreferredSize(new Dimension(80, 60));
        espacio.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Determinar color y icono según estado
        Color colorFondo;
        String icono;
        String tooltip;

        switch (estado) {
            case "L":
                colorFondo = new Color(76, 175, 80, 150); // Verde - Libre
                icono = "🟢";
                tooltip = "Espacio libre - Clic para reservar";
                break;
            case "E":
                colorFondo = new Color(255, 193, 7, 150); // Amarillo - Especial
                icono = "⚡";
                tooltip = "Espacio especial (eléctrico)";
                break;
            case "D":
                colorFondo = new Color(33, 150, 243, 150); // Azul - Discapacitados
                icono = "♿";
                tooltip = "Espacio para personas con discapacidad";
                break;
            case "O":
                colorFondo = new Color(158, 158, 158, 150); // Gris - Ocupado genérico
                icono = "🔒";
                tooltip = "Espacio ocupado";
                break;
            default:
                // Ocupado por un ID específico
                colorFondo = new Color(244, 67, 54, 150); // Rojo - Ocupado
                icono = "🚗";
                tooltip = "Ocupado por ID: " + estado;
                break;
        }

        espacio.setBackground(colorFondo);
        espacio.setToolTipText(tooltip);

        // Etiqueta con icono
        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 20));

        // Etiqueta con posición
        JLabel lblPosicion = new JLabel("(" + fila + "," + columna + ")", SwingConstants.CENTER);
        lblPosicion.setFont(new Font("Arial", Font.BOLD, 10));
        lblPosicion.setForeground(Color.WHITE);

        espacio.add(lblIcono, BorderLayout.CENTER);
        espacio.add(lblPosicion, BorderLayout.SOUTH);

        // Efectos de mouse
        espacio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                espacio.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                espacio.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                espacio.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                espacio.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDetallesEspacio(nivel, fila, columna, estado, parent);
            }
        });

        return espacio;
    }

    private JPanel crearPanelInfoNivel(String nivel, String[][] matriz) {
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelInfo.setOpaque(false);
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // Contar espacios por tipo
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

        // Crear etiquetas informativas
        JLabel lblLibres = new JLabel("🟢 Libres: " + libres);
        JLabel lblOcupados = new JLabel("🔴 Ocupados: " + ocupados);
        JLabel lblEspeciales = new JLabel("⚡ Eléctricos: " + especiales);
        JLabel lblDiscapacitados = new JLabel("♿ Discapacitados: " + discapacitados);

        Font fontInfo = new Font("Arial", Font.BOLD, 12);
        Color colorTexto = Color.WHITE;

        lblLibres.setFont(fontInfo);
        lblLibres.setForeground(colorTexto);
        lblOcupados.setFont(fontInfo);
        lblOcupados.setForeground(colorTexto);
        lblEspeciales.setFont(fontInfo);
        lblEspeciales.setForeground(colorTexto);
        lblDiscapacitados.setFont(fontInfo);
        lblDiscapacitados.setForeground(colorTexto);

        panelInfo.add(lblLibres);
        panelInfo.add(lblOcupados);
        panelInfo.add(lblEspeciales);
        panelInfo.add(lblDiscapacitados);

        return panelInfo;
    }

    private void mostrarDetallesEspacio(String nivel, int fila, int columna, String estado, JFrame parent) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("=== DETALLES DEL ESPACIO ===\n\n");
        detalles.append("📍 Ubicación: ").append(nivel).append(" - Fila ").append(fila).append(", Columna ").append(columna).append("\n\n");

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
                detalles.append("🚗 Estado: OCUPADO\n");
                detalles.append("👤 Reservado por ID: ").append(estado).append("\n");
                
                // Buscar información del usuario
                try {
                    Usuario usuario = Usuario.buscarPorId(Integer.parseInt(estado));
                    if (usuario != null) {
                        detalles.append("📝 Usuario: ").append(usuario.getNombre()).append("\n");
                    }
                } catch (Exception e) {
                    // Si no se puede obtener info del usuario, continuar
                }
                break;
        }

        JOptionPane.showMessageDialog(parent, detalles.toString(),
                "Espacio " + nivel + " (" + fila + "," + columna + ")", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarDialogoReserva(JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Reservar Espacio de Parqueo", true);
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
        JLabel titulo = new JLabel("RESERVAR ESPACIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(63, 81, 181));
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

        BotonRedondeado btnConfirmar = new BotonRedondeado("RESERVAR", 25);
        btnConfirmar.setPreferredSize(new Dimension(140, 45));
        btnConfirmar.setBackground(new Color(63, 81, 181));

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
                    if (estadoActual.equals("L")) {
                        matriz[fila][columna] = String.valueOf(id);
                        mostrarMensajeExito(dialogo, "Espacio reservado exitosamente en " + nivel + " (" + fila + "," + columna + ")");
                        dialogo.dispose();
                        parent.dispose();
                        abrirInterfaz(); // Refrescar
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

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelForm, BorderLayout.CENTER);

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelForm, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);

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
        JDialog dialogo = new JDialog(parent, "Buscar Vehículo", true);
        dialogo.setSize(500, 350);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel titulo = new JLabel("BUSCAR VEHÍCULO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(156, 39, 176));

        JLabel subtitulo = new JLabel("Ingrese el ID para encontrar su vehículo", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitulo.setForeground(new Color(200, 200, 200));
        subtitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // Campo de texto
        JPanel panelCentral = new JPanel(new BorderLayout(0, 15));
        panelCentral.setOpaque(false);

        JLabel lblID = new JLabel("ID de Usuario:");
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        lblID.setForeground(Color.WHITE);

        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 16));
        txtID.setPreferredSize(new Dimension(0, 40));
        txtID.setBackground(new Color(255, 255, 255, 240));
        txtID.setForeground(new Color(33, 33, 33));
        txtID.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(156, 39, 176, 150), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtID.setHorizontalAlignment(JTextField.CENTER);

        panelCentral.add(lblID, BorderLayout.NORTH);
        panelCentral.add(txtID, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setOpaque(false);

        BotonRedondeado btnBuscar = new BotonRedondeado("BUSCAR", 25);
        btnBuscar.setPreferredSize(new Dimension(120, 45));
        btnBuscar.setBackground(new Color(156, 39, 176));

        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(100, 45));
        btnCerrar.setBackground(new Color(96, 125, 139));

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtID.getText().trim());
                String ubicacion = buscarVehiculo(id);
                
                if (ubicacion != null) {
                    mostrarMensajeExito(dialogo, "Vehículo encontrado en: " + ubicacion);
                } else {
                    mostrarMensajeError(dialogo, "No se encontró ningún vehículo con ese ID");
                }
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        btnCerrar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnBuscar);
        panelBotones.add(btnCerrar);

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

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
            protected void paintComponent(Graphics g) {
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
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 20, 20);
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
        // Crear diálogo personalizado de error
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Error", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true);

        // Panel principal con fondo degradado rojo
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Degradado rojo
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(244, 67, 54, 250),
                    0, getHeight(), new Color(198, 40, 40, 250)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Borde
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 20, 20);
            }
        };
        
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Icono de error
        JLabel iconoError = new JLabel("❌", SwingConstants.CENTER);
        iconoError.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoError.setPreferredSize(new Dimension(0, 60));

        // Mensaje
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        // Botón OK
        BotonRedondeado btnOK = new BotonRedondeado("ENTENDIDO", 20);
        btnOK.setPreferredSize(new Dimension(130, 35));
        btnOK.setBackground(new Color(255, 255, 255, 200));
        btnOK.setForeground(new Color(244, 67, 54));
        btnOK.setFont(new Font("Arial", Font.BOLD, 14));
        btnOK.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnOK);

        panelPrincipal.add(iconoError, BorderLayout.NORTH);
        panelPrincipal.add(lblMensaje, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setContentPane(panelPrincipal);

        // Efecto de vibración para llamar la atención
        Timer vibrar = new Timer(50, null);
        final int[] contador = {0};
        final Point posicionOriginal = dialogo.getLocation();
        
        vibrar.addActionListener(e -> {
            if (contador[0] < 6) {
                int offset = (contador[0] % 2 == 0) ? 5 : -5;
                dialogo.setLocation(posicionOriginal.x + offset, posicionOriginal.y);
                contador[0]++;
            } else {
                dialogo.setLocation(posicionOriginal);
                vibrar.stop();
            }
        });
        
        vibrar.start();

        dialogo.setVisible(true);
    }
}
