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
public class Recreacion {

    // Capacidades máximas por cancha
    private final int max_Futbol = 12;
    private final int max_Basquetbol = 10;
    private final int max_Tenis = 2;

    // Contadores de jugadores por espacio
    private int jugadoresFutbol1;
    private int jugadoresFutbol2;
    private int jugadoresBasquetbol;
    private int jugadoresTenis1;
    private int jugadoresTenis2;

    // Constructor
    public Recreacion() {
        this.jugadoresFutbol1 = 0;
        this.jugadoresFutbol2 = 0;
        this.jugadoresBasquetbol = 0;
        this.jugadoresTenis1 = 0;
        this.jugadoresTenis2 = 0;
    }

    public void abrirInterfaz() {
        // === CREAR VENTANA PRINCIPAL ===
        JFrame frame = new JFrame("Área de Recreación - Smart Fit");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // === FONDO CON IMAGEN ===
        FondoPanel fondo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondo.setLayout(new BorderLayout());
        frame.setContentPane(fondo);

        // === PANEL SUPERIOR - TÍTULO ===
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("ÁREA DE RECREACIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Reserva tu cancha deportiva favorita", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(200, 200, 200));

        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // === PANEL CENTRAL - CANCHAS ===
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        // Panel de canchas con grid personalizado
        JPanel panelCanchas = new JPanel(new GridLayout(2, 3, 15, 15));
        panelCanchas.setOpaque(false);
        panelCanchas.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Crear tarjetas de canchas
        panelCanchas.add(crearTarjetaCancha("Fútbol 1", "⚽", jugadoresFutbol1, max_Futbol, new Color(76, 175, 80), 0, frame));
        panelCanchas.add(crearTarjetaCancha("Fútbol 2", "⚽", jugadoresFutbol2, max_Futbol, new Color(76, 175, 80), 1, frame));
        panelCanchas.add(crearTarjetaCancha("Baloncesto", "🏀", jugadoresBasquetbol, max_Basquetbol, new Color(255, 152, 0), 2, frame));
        panelCanchas.add(crearTarjetaCancha("Tenis 1", "🎾", jugadoresTenis1, max_Tenis, new Color(33, 150, 243), 3, frame));
        panelCanchas.add(crearTarjetaCancha("Tenis 2", "🎾", jugadoresTenis2, max_Tenis, new Color(33, 150, 243), 4, frame));

        // Panel vacío para mantener la simetría
        JPanel panelVacio = new JPanel();
        panelVacio.setOpaque(false);
        panelCanchas.add(panelVacio);

        panelCentral.add(panelCanchas, BorderLayout.CENTER);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setOpaque(false);

        BotonRedondeado btnReservar = new BotonRedondeado("RESERVAR CANCHA", 25);
        btnReservar.setPreferredSize(new Dimension(200, 50));
        btnReservar.setBackground(new Color(76, 175, 80)); // Verde deportivo
        btnReservar.addActionListener(e -> mostrarDialogoReserva(frame));

        BotonRedondeado btnLiberar = new BotonRedondeado("LIBERAR CANCHA", 25);
        btnLiberar.setPreferredSize(new Dimension(200, 50));
        btnLiberar.setBackground(new Color(244, 67, 54)); // Rojo
        btnLiberar.addActionListener(e -> mostrarDialogoLiberar(frame));

        BotonRedondeado btnEstado = new BotonRedondeado("VER ESTADO", 25);
        btnEstado.setPreferredSize(new Dimension(180, 50));
        btnEstado.setBackground(new Color(33, 150, 243)); // Azul
        btnEstado.addActionListener(e -> mostrarEstado(frame));

        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 50));
        btnCerrar.setBackground(new Color(96, 125, 139)); // Gris azulado
        btnCerrar.addActionListener(e -> frame.dispose());

        panelBotones.add(btnReservar);
        panelBotones.add(btnLiberar);
        panelBotones.add(btnEstado);
        panelBotones.add(btnCerrar);

        // === ENSAMBLAR VENTANA ===
        fondo.add(panelTitulo, BorderLayout.NORTH);
        fondo.add(panelCentral, BorderLayout.CENTER);
        fondo.add(panelBotones, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel crearTarjetaCancha(String nombre, String icono, int ocupados, int maximo, Color colorTema, int index, JFrame parent) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBackground(new Color(0, 0, 0, 120)); // Fondo semi-transparente
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Panel superior con icono y nombre
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 40));

        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombre.setForeground(Color.WHITE);

        panelSuperior.add(lblIcono, BorderLayout.NORTH);
        panelSuperior.add(lblNombre, BorderLayout.CENTER);

        // Panel central con estado
        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setOpaque(false);

        String estado = ocupados == 0 ? "DISPONIBLE" : ocupados == maximo ? "LLENA" : "EN USO";
        JLabel lblEstado = new JLabel(estado, SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));

        if (ocupados == 0) {
            lblEstado.setForeground(new Color(76, 175, 80)); // Verde
        } else if (ocupados == maximo) {
            lblEstado.setForeground(new Color(244, 67, 54)); // Rojo
        } else {
            lblEstado.setForeground(new Color(255, 193, 7)); // Amarillo
        }

        panelEstado.add(lblEstado, BorderLayout.CENTER);

        // Panel inferior con ocupación
        JPanel panelOcupacion = new JPanel(new BorderLayout(0, 5));
        panelOcupacion.setOpaque(false);

        JLabel lblOcupacion = new JLabel(ocupados + "/" + maximo + " jugadores", SwingConstants.CENTER);
        lblOcupacion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblOcupacion.setForeground(new Color(200, 200, 200));

        // Barra de progreso
        JProgressBar progreso = new JProgressBar(0, maximo);
        progreso.setValue(ocupados);
        progreso.setStringPainted(false);
        progreso.setPreferredSize(new Dimension(0, 8));
        progreso.setBackground(new Color(255, 255, 255, 50));

        // Color según ocupación
        double porcentaje = (double) ocupados / maximo;
        if (porcentaje < 0.5) {
            progreso.setForeground(new Color(76, 175, 80)); // Verde
        } else if (porcentaje < 0.8) {
            progreso.setForeground(new Color(255, 193, 7)); // Amarillo
        } else {
            progreso.setForeground(new Color(244, 67, 54)); // Rojo
        }

        panelOcupacion.add(lblOcupacion, BorderLayout.NORTH);
        panelOcupacion.add(progreso, BorderLayout.SOUTH);

        tarjeta.add(panelSuperior, BorderLayout.NORTH);
        tarjeta.add(panelEstado, BorderLayout.CENTER);
        tarjeta.add(panelOcupacion, BorderLayout.SOUTH);

        // Efecto hover
        tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tarjeta.setBackground(new Color(255, 255, 255, 30));
                tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tarjeta.setBackground(new Color(0, 0, 0, 120));
                tarjeta.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDetallesCancha(index, parent);
            }
        });

        return tarjeta;
    }

    private void mostrarDialogoReserva(JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Reservar Cancha", true);
        dialogo.setSize(550, 500);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("RESERVAR CANCHA DEPORTIVA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel de entrada de datos
        JPanel panelDatos = new JPanel(new BorderLayout(0, 15));
        panelDatos.setOpaque(false);

        // Campo ID
        JPanel panelID = new JPanel(new BorderLayout(10, 5));
        panelID.setOpaque(false);

        JLabel lblID = new JLabel("ID de Usuario:");
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        lblID.setForeground(Color.WHITE);

        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        txtID.setPreferredSize(new Dimension(0, 35));
        txtID.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(76, 175, 80, 150)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panelID.add(lblID, BorderLayout.NORTH);
        panelID.add(txtID, BorderLayout.CENTER);

        // Panel de selección de cancha
        JPanel panelCancha = new JPanel(new BorderLayout(0, 10));
        panelCancha.setOpaque(false);
        panelCancha.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Cancha",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        ButtonGroup grupoCancha = new ButtonGroup();
        JPanel panelRadios = new JPanel(new GridLayout(5, 1, 5, 5));
        panelRadios.setOpaque(false);

        JRadioButton[] radioButtons = new JRadioButton[5];
        String[] nombresCanchas = {"Fútbol 1", "Fútbol 2", "Baloncesto", "Tenis 1", "Tenis 2"};
        int[] ocupaciones = {jugadoresFutbol1, jugadoresFutbol2, jugadoresBasquetbol, jugadoresTenis1, jugadoresTenis2};
        int[] maximos = {max_Futbol, max_Futbol, max_Basquetbol, max_Tenis, max_Tenis};

        for (int i = 0; i < 5; i++) {
            radioButtons[i] = new JRadioButton(nombresCanchas[i] + " (" + ocupaciones[i] + "/" + maximos[i] + ")");
            radioButtons[i].setOpaque(false);
            radioButtons[i].setForeground(Color.WHITE);
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            radioButtons[i].setEnabled(ocupaciones[i] < maximos[i]);

            grupoCancha.add(radioButtons[i]);
            panelRadios.add(radioButtons[i]);
        }

        panelCancha.add(panelRadios, BorderLayout.CENTER);

        // Campo cantidad
        JPanel panelCantidad = new JPanel(new BorderLayout(10, 5));
        panelCantidad.setOpaque(false);

        JLabel lblCantidad = new JLabel("Cantidad de jugadores:");
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 16));
        lblCantidad.setForeground(Color.WHITE);

        JSpinner spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        spinnerCantidad.setPreferredSize(new Dimension(0, 35));
        spinnerCantidad.setFont(new Font("Arial", Font.PLAIN, 14));

        panelCantidad.add(lblCantidad, BorderLayout.NORTH);
        panelCantidad.add(spinnerCantidad, BorderLayout.CENTER);

        panelDatos.add(panelID, BorderLayout.NORTH);
        panelDatos.add(panelCancha, BorderLayout.CENTER);
        panelDatos.add(panelCantidad, BorderLayout.SOUTH);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("RESERVAR", 20);
        btnConfirmar.setPreferredSize(new Dimension(130, 40));
        btnConfirmar.setBackground(new Color(76, 175, 80));

        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(96, 125, 139));

        btnConfirmar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtID.getText().trim());
                Usuario usuario = Usuario.buscarPorId(id);

                if (usuario == null || !usuario.isActivo()) {
                    mostrarMensajeError(dialogo, "Usuario inválido o inactivo");
                    return;
                }

                int canchaSeleccionada = -1;
                for (int i = 0; i < radioButtons.length; i++) {
                    if (radioButtons[i].isSelected()) {
                        canchaSeleccionada = i;
                        break;
                    }
                }

                if (canchaSeleccionada == -1) {
                    mostrarMensajeError(dialogo, "Debe seleccionar una cancha");
                    return;
                }

                int cantidad = (Integer) spinnerCantidad.getValue();

                if (reservarCancha(canchaSeleccionada, cantidad)) {
                    mostrarMensajeExito(dialogo, "Reserva realizada exitosamente");
                    dialogo.dispose();
                    parent.dispose();
                    abrirInterfaz(); // Refrescar
                } else {
                    mostrarMensajeError(dialogo, "No hay suficiente espacio en la cancha");
                }
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelDatos, BorderLayout.CENTER);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void mostrarDialogoLiberar(JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Liberar Cancha", true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("LIBERAR CANCHA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(244, 67, 54));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel de datos
        JPanel panelDatos = new JPanel(new BorderLayout(0, 15));
        panelDatos.setOpaque(false);

        // Campo ID
        JPanel panelID = new JPanel(new BorderLayout(10, 5));
        panelID.setOpaque(false);

        JLabel lblID = new JLabel("ID de Usuario:");
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        lblID.setForeground(Color.WHITE);

        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        txtID.setPreferredSize(new Dimension(0, 35));
        txtID.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(244, 67, 54, 150)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panelID.add(lblID, BorderLayout.NORTH);
        panelID.add(txtID, BorderLayout.CENTER);

        // Panel de selección de cancha
        JPanel panelCancha = new JPanel(new BorderLayout(0, 10));
        panelCancha.setOpaque(false);
        panelCancha.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Cancha a Liberar",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        ButtonGroup grupoCancha = new ButtonGroup();
        JPanel panelRadios = new JPanel(new GridLayout(5, 1, 5, 5));
        panelRadios.setOpaque(false);

        JRadioButton[] radioButtons = new JRadioButton[5];
        String[] nombresCanchas = {"Fútbol 1", "Fútbol 2", "Baloncesto", "Tenis 1", "Tenis 2"};
        int[] ocupaciones = {jugadoresFutbol1, jugadoresFutbol2, jugadoresBasquetbol, jugadoresTenis1, jugadoresTenis2};
        int[] maximos = {max_Futbol, max_Futbol, max_Basquetbol, max_Tenis, max_Tenis};

        for (int i = 0; i < 5; i++) {
            radioButtons[i] = new JRadioButton(nombresCanchas[i] + " (" + ocupaciones[i] + "/" + maximos[i] + ")");
            radioButtons[i].setOpaque(false);
            radioButtons[i].setForeground(Color.WHITE);
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            radioButtons[i].setEnabled(ocupaciones[i] > 0);

            grupoCancha.add(radioButtons[i]);
            panelRadios.add(radioButtons[i]);
        }

        panelCancha.add(panelRadios, BorderLayout.CENTER);

        // Campo cantidad
        JPanel panelCantidad = new JPanel(new BorderLayout(10, 5));
        panelCantidad.setOpaque(false);

        JLabel lblCantidad = new JLabel("Cantidad de jugadores a liberar:");
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 16));
        lblCantidad.setForeground(Color.WHITE);

        JSpinner spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        spinnerCantidad.setPreferredSize(new Dimension(0, 35));
        spinnerCantidad.setFont(new Font("Arial", Font.PLAIN, 14));

        panelCantidad.add(lblCantidad, BorderLayout.NORTH);
        panelCantidad.add(spinnerCantidad, BorderLayout.CENTER);

        panelDatos.add(panelID, BorderLayout.NORTH);
        panelDatos.add(panelCancha, BorderLayout.CENTER);
        panelDatos.add(panelCantidad, BorderLayout.SOUTH);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("LIBERAR", 20);
        btnConfirmar.setPreferredSize(new Dimension(130, 40));
        btnConfirmar.setBackground(new Color(244, 67, 54));

        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(96, 125, 139));

        btnConfirmar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtID.getText().trim());
                Usuario usuario = Usuario.buscarPorId(id);

                if (usuario == null) {
                    mostrarMensajeError(dialogo, "Usuario no encontrado");
                    return;
                }

                int canchaSeleccionada = -1;
                for (int i = 0; i < radioButtons.length; i++) {
                    if (radioButtons[i].isSelected()) {
                        canchaSeleccionada = i;
                        break;
                    }
                }

                if (canchaSeleccionada == -1) {
                    mostrarMensajeError(dialogo, "Debe seleccionar una cancha");
                    return;
                }

                int cantidad = (Integer) spinnerCantidad.getValue();

                liberarCancha(canchaSeleccionada, cantidad);
                mostrarMensajeExito(dialogo, "Cancha liberada exitosamente");
                dialogo.dispose();
                parent.dispose();
                abrirInterfaz(); // Refrescar
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelDatos, BorderLayout.CENTER);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void mostrarEstado(JFrame parent) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("=== ESTADO ACTUAL DE LAS CANCHAS ===\n\n");

        String[] nombres = {"Fútbol 1", "Fútbol 2", "Baloncesto", "Tenis 1", "Tenis 2"};
        int[] ocupaciones = {jugadoresFutbol1, jugadoresFutbol2, jugadoresBasquetbol, jugadoresTenis1, jugadoresTenis2};
        int[] maximos = {max_Futbol, max_Futbol, max_Basquetbol, max_Tenis, max_Tenis};
        String[] iconos = {"⚽", "⚽", "🏀", "🎾", "🎾"};

        for (int i = 0; i < nombres.length; i++) {
            mensaje.append(iconos[i]).append(" ").append(nombres[i]).append(": ");
            mensaje.append(ocupaciones[i]).append("/").append(maximos[i]);

            if (ocupaciones[i] == 0) {
                mensaje.append(" (DISPONIBLE)");
            } else if (ocupaciones[i] == maximos[i]) {
                mensaje.append(" (LLENA)");
            } else {
                mensaje.append(" (EN USO)");
            }
            mensaje.append("\n");
        }

        // Crear diálogo personalizado
        JDialog dialogo = new JDialog(parent, "Estado de las Canchas", true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(parent);

        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(new Color(45, 45, 45));
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        dialogo.add(scrollPane);
        dialogo.setVisible(true);
    }

    private void mostrarDetallesCancha(int index, JFrame parent) {
        String[] nombres = {"Fútbol 1", "Fútbol 2", "Baloncesto", "Tenis 1", "Tenis 2"};
        int[] ocupaciones = {jugadoresFutbol1, jugadoresFutbol2, jugadoresBasquetbol, jugadoresTenis1, jugadoresTenis2};
        int[] maximos = {max_Futbol, max_Futbol, max_Basquetbol, max_Tenis, max_Tenis};
        String[] iconos = {"⚽", "⚽", "🏀", "🎾", "🎾"};

        StringBuilder detalles = new StringBuilder();
        detalles.append("CANCHA: ").append(nombres[index]).append(" ").append(iconos[index]).append("\n");
        detalles.append("OCUPACIÓN: ").append(ocupaciones[index]).append("/").append(maximos[index]).append(" jugadores\n");
        detalles.append("ESTADO: ");

        if (ocupaciones[index] == 0) {
            detalles.append("DISPONIBLE ✅\n");
        } else if (ocupaciones[index] == maximos[index]) {
            detalles.append("LLENA ❌\n");
        } else {
            detalles.append("EN USO ⚡\n");
        }

        double porcentaje = (double) ocupaciones[index] / maximos[index] * 100;
        detalles.append("PORCENTAJE DE USO: ").append(String.format("%.1f", porcentaje)).append("%\n\n");

        detalles.append("DESCRIPCIÓN:\n");
        switch (index) {
            case 0, 1 ->
                detalles.append("Cancha de fútbol con césped sintético\nIdeal para partidos de 11 vs 11");
            case 2 ->
                detalles.append("Cancha de baloncesto profesional\nSuperficie de madera laminada");
            case 3, 4 ->
                detalles.append("Cancha de tenis con superficie dura\nPerfecta para singles y dobles");
        }

        // Crear diálogo personalizado
        JDialog dialogo = new JDialog(parent, "Detalles de " + nombres[index], true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título con icono
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);

        JLabel lblIcono = new JLabel(iconos[index], SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 50));

        JLabel lblTitulo = new JLabel(nombres[index], SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        panelTitulo.add(lblIcono, BorderLayout.NORTH);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        // Área de texto con detalles
        JTextArea textArea = new JTextArea(detalles.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(new Color(0, 0, 0, 150));
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        // Botón cerrar
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 20);
        btnCerrar.setPreferredSize(new Dimension(120, 40));
        btnCerrar.setBackground(new Color(96, 125, 139));
        btnCerrar.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnCerrar);

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);

        dialogo.setVisible(true);
    }

    private boolean reservarCancha(int canchaIndex, int cantidad) {
        switch (canchaIndex) {
            case 0: // Fútbol 1
                if (jugadoresFutbol1 + cantidad <= max_Futbol) {
                    jugadoresFutbol1 += cantidad;
                    return true;
                }
                break;
            case 1: // Fútbol 2
                if (jugadoresFutbol2 + cantidad <= max_Futbol) {
                    jugadoresFutbol2 += cantidad;
                    return true;
                }
                break;
            case 2: // Baloncesto
                if (jugadoresBasquetbol + cantidad <= max_Basquetbol) {
                    jugadoresBasquetbol += cantidad;
                    return true;
                }
                break;
            case 3: // Tenis 1
                if (jugadoresTenis1 + cantidad <= max_Tenis) {
                    jugadoresTenis1 += cantidad;
                    return true;
                }
                break;
            case 4: // Tenis 2
                if (jugadoresTenis2 + cantidad <= max_Tenis) {
                    jugadoresTenis2 += cantidad;
                    return true;
                }
                break;
        }
        return false;
    }

    private void liberarCancha(int canchaIndex, int cantidad) {
        switch (canchaIndex) {
            case 0: // Fútbol 1
                jugadoresFutbol1 = Math.max(0, jugadoresFutbol1 - cantidad);
                break;
            case 1: // Fútbol 2
                jugadoresFutbol2 = Math.max(0, jugadoresFutbol2 - cantidad);
                break;
            case 2: // Baloncesto
                jugadoresBasquetbol = Math.max(0, jugadoresBasquetbol - cantidad);
                break;
            case 3: // Tenis 1
                jugadoresTenis1 = Math.max(0, jugadoresTenis1 - cantidad);
                break;
            case 4: // Tenis 2
                jugadoresTenis2 = Math.max(0, jugadoresTenis2 - cantidad);
                break;
        }
    }

    private void mostrarMensajeExito(Component parent, String mensaje) {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Operación Exitosa", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true);

        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(76, 175, 80, 250),
                        0, getHeight(), new Color(56, 142, 60, 250)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
            }
        };

        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel iconoExito = new JLabel("✅", SwingConstants.CENTER);
        iconoExito.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoExito.setPreferredSize(new Dimension(0, 60));

        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

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

        Timer timer = new Timer(3000, e -> dialogo.dispose());
        timer.setRepeats(false);
        timer.start();

        dialogo.setVisible(true);
    }

    private void mostrarMensajeError(Component parent, String mensaje) {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Error", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true);

        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(244, 67, 54, 250),
                        0, getHeight(), new Color(198, 40, 40, 250)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
            }
        };

        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel iconoError = new JLabel("❌", SwingConstants.CENTER);
        iconoError.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoError.setPreferredSize(new Dimension(0, 60));

        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

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

        // Efecto de vibración
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

    // ===== MÉTODOS LEGACY PARA COMPATIBILIDAD =====
    public void iniciarRecreacion() {
        abrirInterfaz();
    }

    private Usuario seleccionarUsuarioRecreacion() {
        String input = JOptionPane.showInputDialog("\nIngrese el ID del usuario:");
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

    private void reservarFutbol(int id) {
        Usuario u = Usuario.buscarPorId(id);
        if (u == null || !u.isActivo()) {
            JOptionPane.showMessageDialog(null, "Usuario no válido o inactivo.");
            return;
        }

        String[] opciones = {"Fútbol 1 (" + jugadoresFutbol1 + "/" + max_Futbol + ")",
            "Fútbol 2 (" + jugadoresFutbol2 + "/" + max_Futbol + ")"};

        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione cancha:", "Reservar Fútbol",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != -1) {
            try {
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos jugadores desea ingresar?"));

                if (seleccion == 0) {
                    if (jugadoresFutbol1 + cantidad <= max_Futbol) {
                        jugadoresFutbol1 += cantidad;
                        JOptionPane.showMessageDialog(null, "Reservado por " + u.getNombre() + " en Fútbol 1. Jugadores actuales: " + jugadoresFutbol1);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cancha llena. Máximo " + max_Futbol + " jugadores.");
                    }
                } else {
                    if (jugadoresFutbol2 + cantidad <= max_Futbol) {
                        jugadoresFutbol2 += cantidad;
                        JOptionPane.showMessageDialog(null, "Reservado por " + u.getNombre() + " en Fútbol 2. Jugadores actuales: " + jugadoresFutbol2);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cancha llena. Máximo " + max_Futbol + " jugadores.");
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida.");
            }
        }
    }

    private void reservarBaloncesto(int id) {
        Usuario u = Usuario.buscarPorId(id);
        if (u == null || !u.isActivo()) {
            JOptionPane.showMessageDialog(null, "Usuario no válido o inactivo.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos jugadores desea ingresar a baloncesto?"));

            if ((jugadoresBasquetbol + cantidad) <= max_Basquetbol) {
                jugadoresBasquetbol += cantidad;
                JOptionPane.showMessageDialog(null, "Reserva exitosa por " + u.getNombre() + ". Jugadores en cancha: " + jugadoresBasquetbol);
            } else {
                JOptionPane.showMessageDialog(null, "No hay suficiente espacio en la cancha de baloncesto.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida.");
        }
    }

    private void reservarTenis(int id) {
        Usuario u = Usuario.buscarPorId(id);
        if (u == null || !u.isActivo()) {
            JOptionPane.showMessageDialog(null, "Usuario no válido o inactivo.");
            return;
        }

        String[] opciones = {"Tenis 1 (" + jugadoresTenis1 + "/" + max_Tenis + ")",
            "Tenis 2 (" + jugadoresTenis2 + "/" + max_Tenis + ")"};

        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione cancha:", "Reservar Tenis",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != -1) {
            try {
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos jugadores desea ingresar?"));

                if (seleccion == 0) {
                    if (jugadoresTenis1 + cantidad <= max_Tenis) {
                        jugadoresTenis1 += cantidad;
                        JOptionPane.showMessageDialog(null, "Reservado por " + u.getNombre() + " en Tenis 1. Jugadores actuales: " + jugadoresTenis1);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cancha llena. Máximo " + max_Tenis + " jugadores.");
                    }
                } else {
                    if (jugadoresTenis2 + cantidad <= max_Tenis) {
                        jugadoresTenis2 += cantidad;
                        JOptionPane.showMessageDialog(null, "Reservado por " + u.getNombre() + " en Tenis 2. Jugadores actuales: " + jugadoresTenis2);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cancha llena. Máximo " + max_Tenis + " jugadores.");
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida.");
            }
        }
    }

    private void liberarCancha(int id) {
        String[] opciones = {"Fútbol 1", "Fútbol 2", "Baloncesto", "Tenis 1", "Tenis 2"};

        int seleccion = JOptionPane.showOptionDialog(null, "¿Qué cancha desea liberar?", "Liberar Cancha",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != -1) {
            try {
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos jugadores desea liberar?"));

                switch (seleccion) {
                    case 0 ->
                        jugadoresFutbol1 = Math.max(0, jugadoresFutbol1 - cantidad);
                    case 1 ->
                        jugadoresFutbol2 = Math.max(0, jugadoresFutbol2 - cantidad);
                    case 2 ->
                        jugadoresBasquetbol = Math.max(0, jugadoresBasquetbol - cantidad);
                    case 3 ->
                        jugadoresTenis1 = Math.max(0, jugadoresTenis1 - cantidad);
                    case 4 ->
                        jugadoresTenis2 = Math.max(0, jugadoresTenis2 - cantidad);
                }

                JOptionPane.showMessageDialog(null, "Liberación realizada correctamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida.");
            }
        }
    }

    private void mostrarEstadoRecreacion() {
        String estado = """
            Estado actual de las canchas:
            Fútbol 1: """ + jugadoresFutbol1 + "/" + max_Futbol + "\n"
                + "Fútbol 2: " + jugadoresFutbol2 + "/" + max_Futbol + "\n"
                + "Baloncesto: " + jugadoresBasquetbol + "/" + max_Basquetbol + "\n"
                + "Tenis 1: " + jugadoresTenis1 + "/" + max_Tenis + "\n"
                + "Tenis 2: " + jugadoresTenis2 + "/" + max_Tenis;

        JOptionPane.showMessageDialog(null, estado);
    }
}
