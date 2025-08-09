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
        frame.setLocationRelativeTo(null); // Centrar ventana en pantalla

        // === FONDO CON IMAGEN ===
        FondoPanel fondo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondo.setLayout(new BorderLayout());
        frame.setContentPane(fondo); // Asignar el panel con fondo a la ventana

        // === PANEL SUPERIOR - TÍTULO ===
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false); // Transparente para que se vea el fondo
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Margen interno

        // Título principal
        JLabel titulo = new JLabel("ÁREA DE RECREACIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);

        // Subtítulo
        JLabel subtitulo = new JLabel("Reserva tu cancha deportiva favorita", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(200, 200, 200));

        // Añadir título y subtítulo al panel
        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // === PANEL CENTRAL - CANCHAS ===
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        // Panel con grid para tarjetas de canchas
        JPanel panelCanchas = new JPanel(new GridLayout(2, 3, 15, 15)); // 2 filas, 3 columnas, espacios
        panelCanchas.setOpaque(false);
        panelCanchas.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Agregar tarjetas con información de cada cancha
        panelCanchas.add(crearTarjetaCancha("Fútbol 1", "⚽", jugadoresFutbol1, max_Futbol, new Color(76, 175, 80), 0, frame));
        panelCanchas.add(crearTarjetaCancha("Fútbol 2", "⚽", jugadoresFutbol2, max_Futbol, new Color(76, 175, 80), 1, frame));
        panelCanchas.add(crearTarjetaCancha("Baloncesto", "🏀", jugadoresBasquetbol, max_Basquetbol, new Color(255, 152, 0), 2, frame));
        panelCanchas.add(crearTarjetaCancha("Tenis 1", "🎾", jugadoresTenis1, max_Tenis, new Color(33, 150, 243), 3, frame));
        panelCanchas.add(crearTarjetaCancha("Tenis 2", "🎾", jugadoresTenis2, max_Tenis, new Color(33, 150, 243), 4, frame));

        // Panel vacío para que el grid mantenga simetría (2x3)
        JPanel panelVacio = new JPanel();
        panelVacio.setOpaque(false);
        panelCanchas.add(panelVacio);

        // Añadir panel de canchas al panel central
        panelCentral.add(panelCanchas, BorderLayout.CENTER);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setOpaque(false);

        // Botón para reservar cancha
        BotonRedondeado btnReservar = new BotonRedondeado("RESERVAR CANCHA", 25);
        btnReservar.setPreferredSize(new Dimension(200, 50));
        btnReservar.setBackground(new Color(76, 175, 80)); // Verde deportivo
        btnReservar.addActionListener(e -> mostrarDialogoReserva(frame)); // Acción para abrir diálogo de reserva

        // Botón para liberar cancha
        BotonRedondeado btnLiberar = new BotonRedondeado("LIBERAR CANCHA", 25);
        btnLiberar.setPreferredSize(new Dimension(200, 50));
        btnLiberar.setBackground(new Color(244, 67, 54)); // Rojo
        btnLiberar.addActionListener(e -> mostrarDialogoLiberar(frame)); // Acción para abrir diálogo liberar

        // Botón para ver estado de canchas
        BotonRedondeado btnEstado = new BotonRedondeado("VER ESTADO", 25);
        btnEstado.setPreferredSize(new Dimension(180, 50));
        btnEstado.setBackground(new Color(33, 150, 243)); // Azul
        btnEstado.addActionListener(e -> mostrarEstado(frame)); // Acción para mostrar estado

        // Botón para cerrar la ventana principal
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 50));
        btnCerrar.setBackground(new Color(96, 125, 139)); // Gris azulado
        btnCerrar.addActionListener(e -> frame.dispose());

        // Añadir botones al panel de botones
        panelBotones.add(btnReservar);
        panelBotones.add(btnLiberar);
        panelBotones.add(btnEstado);
        panelBotones.add(btnCerrar);

        // === ENSAMBLAR VENTANA ===
        fondo.add(panelTitulo, BorderLayout.NORTH);
        fondo.add(panelCentral, BorderLayout.CENTER);
        fondo.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar ventana
        frame.setVisible(true);
    }


    private JPanel crearTarjetaCancha(String nombre, String icono, int ocupados, int maximo, Color colorTema, int index, JFrame parent) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBackground(new Color(0, 0, 0, 120)); // Fondo negro semi-transparente
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2), // Borde blanco semi-transparente
                BorderFactory.createEmptyBorder(20, 20, 20, 20) // Margen interno
        ));

        // Panel superior con icono y nombre de la cancha
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 40)); // Icono grande

        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombre.setForeground(Color.WHITE);

        panelSuperior.add(lblIcono, BorderLayout.NORTH);
        panelSuperior.add(lblNombre, BorderLayout.CENTER);

        // Panel central con estado (Disponible, En Uso, Llena)
        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setOpaque(false);

        // Determinar texto y color según ocupación
        String estado = ocupados == 0 ? "DISPONIBLE" : ocupados == maximo ? "LLENA" : "EN USO";
        JLabel lblEstado = new JLabel(estado, SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));

        if (ocupados == 0) {
            lblEstado.setForeground(new Color(76, 175, 80)); // Verde para disponible
        } else if (ocupados == maximo) {
            lblEstado.setForeground(new Color(244, 67, 54)); // Rojo para llena
        } else {
            lblEstado.setForeground(new Color(255, 193, 7)); // Amarillo para en uso
        }

        panelEstado.add(lblEstado, BorderLayout.CENTER);

        // Panel inferior con ocupación y barra de progreso
        JPanel panelOcupacion = new JPanel(new BorderLayout(0, 5));
        panelOcupacion.setOpaque(false);

        JLabel lblOcupacion = new JLabel(ocupados + "/" + maximo + " jugadores", SwingConstants.CENTER);
        lblOcupacion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblOcupacion.setForeground(new Color(200, 200, 200));

        // Barra de progreso que visualiza la ocupación
        JProgressBar progreso = new JProgressBar(0, maximo);
        progreso.setValue(ocupados);
        progreso.setStringPainted(false); // No mostrar texto dentro de la barra
        progreso.setPreferredSize(new Dimension(0, 8)); // Altura pequeña para barra

        // Color de barra según porcentaje ocupado
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

        // Añadir subpaneles a la tarjeta principal
        tarjeta.add(panelSuperior, BorderLayout.NORTH);
        tarjeta.add(panelEstado, BorderLayout.CENTER);
        tarjeta.add(panelOcupacion, BorderLayout.SOUTH);

        // Efecto hover para mejorar experiencia visual
        tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tarjeta.setBackground(new Color(255, 255, 255, 30)); // Cambio de fondo al pasar mouse
                tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor tipo mano
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tarjeta.setBackground(new Color(0, 0, 0, 120)); // Fondo original
                tarjeta.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Cursor normal
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Al hacer clic mostrar detalles de la cancha
                mostrarDetallesCancha(index, parent);
            }
        });

        return tarjeta;
    }

    private void mostrarDialogoReserva(JFrame parent) {
    // Crear diálogo modal (bloquea ventana padre)
    JDialog dialogo = new JDialog(parent, "Reservar Cancha", true);
    dialogo.setSize(550, 500);
    dialogo.setLocationRelativeTo(parent);

    // Fondo con imagen
    FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
    fondoDialogo.setLayout(new BorderLayout());
    dialogo.setContentPane(fondoDialogo);

    // Panel principal contenedor
    JPanel panelPrincipal = new JPanel(new BorderLayout());
    panelPrincipal.setOpaque(false);
    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Título del diálogo
    JLabel titulo = new JLabel("RESERVAR CANCHA DEPORTIVA", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 24));
    titulo.setForeground(Color.WHITE);
    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

    // Panel para entrada de datos
    JPanel panelDatos = new JPanel(new BorderLayout(0, 15));
    panelDatos.setOpaque(false);

    // Campo para ingresar ID de usuario
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

    // Panel para selección de cancha
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

    // Crear botones radio para cada cancha
    JRadioButton[] radioButtons = new JRadioButton[5];
    String[] nombresCanchas = {"Fútbol 1", "Fútbol 2", "Baloncesto", "Tenis 1", "Tenis 2"};
    int[] ocupaciones = {jugadoresFutbol1, jugadoresFutbol2, jugadoresBasquetbol, jugadoresTenis1, jugadoresTenis2};
    int[] maximos = {max_Futbol, max_Futbol, max_Basquetbol, max_Tenis, max_Tenis};

    for (int i = 0; i < 5; i++) {
        radioButtons[i] = new JRadioButton(nombresCanchas[i] + " (" + ocupaciones[i] + "/" + maximos[i] + ")");
        radioButtons[i].setOpaque(false);
        radioButtons[i].setForeground(Color.WHITE);
        radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
        radioButtons[i].setEnabled(ocupaciones[i] < maximos[i]); // Solo habilitar si hay espacio

        grupoCancha.add(radioButtons[i]);
        panelRadios.add(radioButtons[i]);
    }

    panelCancha.add(panelRadios, BorderLayout.CENTER);

    // Campo para cantidad de jugadores a reservar
    JPanel panelCantidad = new JPanel(new BorderLayout(10, 5));
    panelCantidad.setOpaque(false);

    JLabel lblCantidad = new JLabel("Cantidad de jugadores:");
    lblCantidad.setFont(new Font("Arial", Font.BOLD, 16));
    lblCantidad.setForeground(Color.WHITE);

    JSpinner spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1)); // Rango 1 a 12
    spinnerCantidad.setPreferredSize(new Dimension(0, 35));
    spinnerCantidad.setFont(new Font("Arial", Font.PLAIN, 14));

    panelCantidad.add(lblCantidad, BorderLayout.NORTH);
    panelCantidad.add(spinnerCantidad, BorderLayout.CENTER);

    // Añadir paneles ID, cancha y cantidad al panel de datos
    panelDatos.add(panelID, BorderLayout.NORTH);
    panelDatos.add(panelCancha, BorderLayout.CENTER);
    panelDatos.add(panelCantidad, BorderLayout.SOUTH);

    // Panel para botones Confirmar y Cancelar
    JPanel panelBotones = new JPanel(new FlowLayout());
    panelBotones.setOpaque(false);

    BotonRedondeado btnConfirmar = new BotonRedondeado("RESERVAR", 20);
    btnConfirmar.setPreferredSize(new Dimension(130, 40));
    btnConfirmar.setBackground(new Color(76, 175, 80));

    BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
    btnCancelar.setPreferredSize(new Dimension(120, 40));
    btnCancelar.setBackground(new Color(96, 125, 139));

    // Acción para confirmar reserva
    btnConfirmar.addActionListener(e -> {
        try {
            int id = Integer.parseInt(txtID.getText().trim());
            Usuario usuario = Usuario.buscarPorId(id);

            // Validar que usuario exista y esté activo
            if (usuario == null || !usuario.isActivo()) {
                mostrarMensajeError(dialogo, "Usuario inválido o inactivo");
                return;
            }

            // Validar selección de cancha
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

            // Intentar reservar cancha (lógica externa)
            if (reservarCancha(canchaSeleccionada, cantidad)) {
                mostrarMensajeExito(dialogo, "Reserva realizada exitosamente");
                dialogo.dispose();
                parent.dispose();
                abrirInterfaz(); // Refrescar la interfaz principal
            } else {
                mostrarMensajeError(dialogo, "No hay suficiente espacio en la cancha");
            }
        } catch (NumberFormatException ex) {
            mostrarMensajeError(dialogo, "ID debe ser un número válido");
        }
    });

    btnCancelar.addActionListener(e -> dialogo.dispose());

    // Añadir componentes al panel principal
    panelPrincipal.add(titulo, BorderLayout.NORTH);
    panelPrincipal.add(panelDatos, BorderLayout.CENTER);

    // Añadir panel principal y botones al fondo del diálogo
    fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
    fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

    dialogo.setVisible(true);
}


    /**
 * Muestra un diálogo para que el usuario pueda liberar jugadores de una cancha.
 * Permite ingresar el ID del usuario, seleccionar la cancha y la cantidad de jugadores a liberar.
 */
    private void mostrarDialogoLiberar(JFrame parent) {
        // Crear diálogo modal centrado sobre la ventana padre
        JDialog dialogo = new JDialog(parent, "Liberar Cancha", true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(parent);

        // Fondo personalizado con imagen
        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal con layout BorderLayout y transparencia
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título del diálogo
        JLabel titulo = new JLabel("LIBERAR CANCHA DEPORTIVA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel para contener los campos de entrada
        JPanel panelDatos = new JPanel(new BorderLayout(0, 15));
        panelDatos.setOpaque(false);

        // Panel para ingresar el ID de usuario
        JPanel panelID = new JPanel(new BorderLayout(10, 5));
        panelID.setOpaque(false);

        JLabel lblID = new JLabel("ID de Usuario:");
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        lblID.setForeground(Color.WHITE);

        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        txtID.setPreferredSize(new Dimension(0, 35));
        // Borde con color rojo semitransparente
        txtID.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(244, 67, 54, 150)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panelID.add(lblID, BorderLayout.NORTH);
        panelID.add(txtID, BorderLayout.CENTER);

        // Panel para seleccionar la cancha a liberar
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

        // Crear botones de opción para las canchas con estado y disponibilidad
        JRadioButton[] radioButtons = new JRadioButton[5];
        String[] nombresCanchas = {"Fútbol 1", "Fútbol 2", "Baloncesto", "Tenis 1", "Tenis 2"};
        int[] ocupaciones = {jugadoresFutbol1, jugadoresFutbol2, jugadoresBasquetbol, jugadoresTenis1, jugadoresTenis2};

        for (int i = 0; i < 5; i++) {
            radioButtons[i] = new JRadioButton(nombresCanchas[i] + " (" + ocupaciones[i] + " jugadores)");
            radioButtons[i].setOpaque(false);
            radioButtons[i].setForeground(Color.WHITE);
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            // Solo activar si hay jugadores en la cancha para liberar
            radioButtons[i].setEnabled(ocupaciones[i] > 0);

            grupoCancha.add(radioButtons[i]);
            panelRadios.add(radioButtons[i]);
        }

        panelCancha.add(panelRadios, BorderLayout.CENTER);

        // Panel para seleccionar la cantidad a liberar
        JPanel panelCantidad = new JPanel(new BorderLayout(10, 5));
        panelCantidad.setOpaque(false);

        JLabel lblCantidad = new JLabel("Cantidad de jugadores a liberar:");
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 16));
        lblCantidad.setForeground(Color.WHITE);

        // Spinner para seleccionar cantidad (entre 1 y 12)
        JSpinner spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        spinnerCantidad.setPreferredSize(new Dimension(0, 35));
        spinnerCantidad.setFont(new Font("Arial", Font.PLAIN, 14));

        panelCantidad.add(lblCantidad, BorderLayout.NORTH);
        panelCantidad.add(spinnerCantidad, BorderLayout.CENTER);

        // Agregar todos los campos al panel de datos
        panelDatos.add(panelID, BorderLayout.NORTH);
        panelDatos.add(panelCancha, BorderLayout.CENTER);
        panelDatos.add(panelCantidad, BorderLayout.SOUTH);

        // Panel para los botones de acción
        // Panel para los botones de acción
JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("LIBERAR", 20);
        btnConfirmar.setPreferredSize(new Dimension(130, 40));
        btnConfirmar.setBackground(new Color(244, 67, 54));

        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(96, 125, 139));

// Agregar botones al panel
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

// Agregar panel principal y panel de botones al fondoDialogo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);


        // Acción al confirmar liberación
        btnConfirmar.addActionListener(e -> {
            try {
                // Obtener y validar ID ingresado
                int id = Integer.parseInt(txtID.getText().trim());
                Usuario usuario = Usuario.buscarPorId(id);

                if (usuario == null || !usuario.isActivo()) {
                    mostrarMensajeError(dialogo, "Usuario inválido o inactivo");
                    return;
                }

                // Verificar cancha seleccionada
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

                // Obtener cantidad a liberar
                int cantidad = (Integer) spinnerCantidad.getValue();

                // Intentar liberar cancha
                if (liberarCancha(canchaSeleccionada, cantidad)) {
                    mostrarMensajeExito(dialogo, "Liberación realizada exitosamente");
                    dialogo.dispose();
                    parent.dispose();
                    
                    if (parent instanceof JFrame) {
                        ((JFrame) parent).dispose();
                    }
                    
                    abrirInterfaz(); // Refrescar la interfaz principal
                } else {
                    mostrarMensajeError(dialogo, "No se puede liberar esa cantidad");
                }
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelDatos, BorderLayout.CENTER);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }


    /**
 * Muestra un diálogo con el estado actual de ocupación de todas las canchas.
 * @param parent la ventana padre sobre la cual se centra el diálogo.
 */
private void mostrarEstado(JFrame parent) {
    // Usamos StringBuilder para construir el mensaje de estado de forma eficiente
    StringBuilder mensaje = new StringBuilder();
    mensaje.append("=== ESTADO ACTUAL DE LAS CANCHAS ===\n\n");

    // Arreglos con nombres, ocupación actual, capacidad máxima y símbolos para cada cancha
    String[] nombres = {"Fútbol 1", "Fútbol 2", "Baloncesto", "Tenis 1", "Tenis 2"};
    int[] ocupaciones = {jugadoresFutbol1, jugadoresFutbol2, jugadoresBasquetbol, jugadoresTenis1, jugadoresTenis2};
    int[] maximos = {max_Futbol, max_Futbol, max_Basquetbol, max_Tenis, max_Tenis};
    String[] iconos = {"⚽", "⚽", "🏀", "🎾", "🎾"};

    // Iterar sobre cada cancha para armar la línea con su estado
    for (int i = 0; i < nombres.length; i++) {
        // Agregar icono y nombre de la cancha
        mensaje.append(iconos[i]).append(" ").append(nombres[i]).append(": ");
        // Agregar la ocupación actual seguida de la capacidad máxima
        mensaje.append(ocupaciones[i]).append("/").append(maximos[i]);

        // Condiciones para mostrar el estado de la cancha según ocupación
        if (ocupaciones[i] == 0) {
            mensaje.append(" (DISPONIBLE)"); // Sin jugadores
        } else if (ocupaciones[i] == maximos[i]) {
            mensaje.append(" (LLENA)"); // Capacidad máxima alcanzada
        } else {
            mensaje.append(" (EN USO)"); // Parcialmente ocupada
        }
        mensaje.append("\n"); // Nueva línea para la próxima cancha
    }

    // Crear un diálogo modal para mostrar el mensaje de estado
    JDialog dialogo = new JDialog(parent, "Estado de las Canchas", true);
    dialogo.setSize(500, 400);
    dialogo.setLocationRelativeTo(parent); // Centrar respecto a la ventana padre

    // Crear un área de texto para mostrar el mensaje de estado
    JTextArea textArea = new JTextArea(mensaje.toString());
    textArea.setEditable(false); // Solo lectura
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Fuente monoespaciada para mejor alineación
    textArea.setBackground(new Color(45, 45, 45)); // Fondo oscuro
    textArea.setForeground(Color.WHITE); // Texto blanco para contraste
    textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno

    // Añadir el área de texto a un JScrollPane para permitir scroll si el texto es largo
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    // Agregar el scrollPane al diálogo
    dialogo.add(scrollPane);

    // Mostrar el diálogo
    dialogo.setVisible(true);
}

/**
 * 
 */
    private void mostrarDetallesCancha(int index, JFrame parent) {
        // Datos básicos de las canchas
        String[] nombres = {"Fútbol 1", "Fútbol 2", "Baloncesto", "Tenis 1", "Tenis 2"};
        int[] ocupaciones = {jugadoresFutbol1, jugadoresFutbol2, jugadoresBasquetbol, jugadoresTenis1, jugadoresTenis2};
        int[] maximos = {max_Futbol, max_Futbol, max_Basquetbol, max_Tenis, max_Tenis};
        String[] iconos = {"⚽", "⚽", "🏀", "🎾", "🎾"};

        // Construir el texto con los detalles de la cancha seleccionada
        StringBuilder detalles = new StringBuilder();
        detalles.append("CANCHA: ").append(nombres[index]).append(" ").append(iconos[index]).append("\n");
        detalles.append("OCUPACIÓN: ").append(ocupaciones[index]).append("/").append(maximos[index]).append(" jugadores\n");
        detalles.append("ESTADO: ");

        // Estado según ocupación actual
        if (ocupaciones[index] == 0) {
            detalles.append("DISPONIBLE ✅\n");
        } else if (ocupaciones[index] == maximos[index]) {
            detalles.append("LLENA ❌\n");
        } else {
            detalles.append("EN USO ⚡\n");
        }

        // Calcular y mostrar el porcentaje de uso de la cancha
        double porcentaje = (double) ocupaciones[index] / maximos[index] * 100;
        detalles.append("PORCENTAJE DE USO: ").append(String.format("%.1f", porcentaje)).append("%\n\n");

        // Agregar una descripción específica según el tipo de cancha
        detalles.append("DESCRIPCIÓN:\n");
        switch (index) {
            case 0, 1 ->
                detalles.append("Cancha de fútbol con césped sintético\nIdeal para partidos de 11 vs 11");
            case 2 ->
                detalles.append("Cancha de baloncesto profesional\nSuperficie de madera laminada");
            case 3, 4 ->
                detalles.append("Cancha de tenis con superficie dura\nPerfecta para singles y dobles");
        }

        // Crear un diálogo modal personalizado con imagen de fondo para mostrar los detalles
        JDialog dialogo = new JDialog(parent, "Detalles de " + nombres[index], true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(parent);

        // Fondo personalizado con imagen
        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal transparente con padding
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para título con icono y nombre de cancha
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);

        JLabel lblIcono = new JLabel(iconos[index], SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 50)); // Tamaño grande para icono

        JLabel lblTitulo = new JLabel(nombres[index], SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24)); // Título en negrita
        lblTitulo.setForeground(Color.WHITE);

        panelTitulo.add(lblIcono, BorderLayout.NORTH);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        // Área de texto con detalles, estilo y borde
        JTextArea textArea = new JTextArea(detalles.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(new Color(0, 0, 0, 150)); // Fondo semitransparente negro
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Scroll pane para el área de texto sin fondo para que se vea el fondo del panel
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        // Botón para cerrar el diálogo
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 20);
        btnCerrar.setPreferredSize(new Dimension(120, 40));
        btnCerrar.setBackground(new Color(96, 125, 139)); // Color gris azulado
        btnCerrar.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnCerrar);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // Agregar panel principal al fondo y mostrar diálogo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        dialogo.setVisible(true);
    }

    /**
     * Intenta reservar una cantidad de jugadores en la cancha indicada.
     *
     * @param canchaIndex índice de la cancha.
     * @param cantidad cantidad de jugadores a reservar.
     * @return true si la reserva fue exitosa, false si no hay suficiente
     * espacio.
     */
    private boolean reservarCancha(int canchaIndex, int cantidad) {
        switch (canchaIndex) {
            case 0: // Fútbol 1
                if (jugadoresFutbol1 + cantidad <= max_Futbol) {
                    jugadoresFutbol1 += cantidad; // Incrementar jugadores en la cancha
                    return true; // Reserva exitosa
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
        // No hay espacio suficiente para reservar la cantidad solicitada
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
        // Crear un JDialog modal con título "Operación Exitosa" centrado en el padre
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Operación Exitosa", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent); // Centrar en el componente padre
        dialogo.setUndecorated(true); // Quitar barra de título y bordes estándar

        // Panel principal con diseño personalizado de degradado y bordes redondeados
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Activar antialiasing para suavizar el dibujo
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Crear un gradiente vertical de verde claro a verde oscuro
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(76, 175, 80, 250), // Inicio arriba (verde claro)
                        0, getHeight(), new Color(56, 142, 60, 250) // Final abajo (verde oscuro)
                );
                g2d.setPaint(gradient);
                // Dibujar un rectángulo redondeado que llena el panel
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Dibujar borde blanco semi-transparente con grosor 2
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
            }
        };

        // Layout del panel y márgenes internos para que no se pegue el contenido al borde
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Etiqueta con el icono de éxito (emoji) centrado y tamaño grande
        JLabel iconoExito = new JLabel("✅", SwingConstants.CENTER);
        iconoExito.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoExito.setPreferredSize(new Dimension(0, 60)); // Altura fija

        // Etiqueta para el mensaje de texto, con HTML para centrar el texto multilínea
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        // Botón redondeado para cerrar el diálogo manualmente
        BotonRedondeado btnOK = new BotonRedondeado("PERFECTO", 20);
        btnOK.setPreferredSize(new Dimension(120, 35));
        btnOK.setBackground(new Color(255, 255, 255, 200)); // Fondo blanco semi-transparente
        btnOK.setForeground(new Color(76, 175, 80)); // Texto verde
        btnOK.setFont(new Font("Arial", Font.BOLD, 14));
        btnOK.addActionListener(e -> dialogo.dispose()); // Cierra el diálogo al hacer click

        // Panel para el botón con layout centrado y fondo transparente
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnOK);

        // Agregar componentes al panel principal
        panelPrincipal.add(iconoExito, BorderLayout.NORTH);
        panelPrincipal.add(lblMensaje, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // Establecer el panel principal como contenido del diálogo
        dialogo.setContentPane(panelPrincipal);

        // Timer para cerrar automáticamente el diálogo después de 3 segundos (3000 ms)
        Timer timer = new Timer(3000, e -> dialogo.dispose());
        timer.setRepeats(false);
        timer.start();

        // Mostrar el diálogo (bloqueante porque es modal)
        dialogo.setVisible(true);
    }

    
    private void mostrarMensajeError(Component parent, String mensaje) {
        // Crear un JDialog modal con título "Error" centrado en el padre
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Error", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true); // Quitar barra de título y bordes estándar

        // Panel principal con diseño personalizado de degradado rojo y bordes redondeados
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Activar antialiasing para suavizar el dibujo
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Crear un gradiente vertical de rojo claro a rojo oscuro
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(244, 67, 54, 250), // Inicio arriba (rojo claro)
                        0, getHeight(), new Color(198, 40, 40, 250) // Final abajo (rojo oscuro)
                );
                g2d.setPaint(gradient);
                // Dibujar un rectángulo redondeado que llena el panel
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Dibujar borde blanco semi-transparente con grosor 2
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
            }
        };

        // Layout del panel y márgenes internos para que no se pegue el contenido al borde
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Etiqueta con el icono de error (emoji) centrado y tamaño grande
        JLabel iconoError = new JLabel("❌", SwingConstants.CENTER);
        iconoError.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoError.setPreferredSize(new Dimension(0, 60)); // Altura fija

        // Etiqueta para el mensaje de texto, con HTML para centrar el texto multilínea
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        // Botón redondeado para cerrar el diálogo manualmente
        BotonRedondeado btnOK = new BotonRedondeado("ENTENDIDO", 20);
        btnOK.setPreferredSize(new Dimension(130, 35));
        btnOK.setBackground(new Color(255, 255, 255, 200)); // Fondo blanco semi-transparente
        btnOK.setForeground(new Color(244, 67, 54)); // Texto rojo
        btnOK.setFont(new Font("Arial", Font.BOLD, 14));
        btnOK.addActionListener(e -> dialogo.dispose()); // Cierra el diálogo al hacer click

        // Panel para el botón con layout centrado y fondo transparente
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnOK);

        // Agregar componentes al panel principal
        panelPrincipal.add(iconoError, BorderLayout.NORTH);
        panelPrincipal.add(lblMensaje, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // Establecer el panel principal como contenido del diálogo
        dialogo.setContentPane(panelPrincipal);

        // Timer para efecto de vibración: mueve la ventana izquierda/derecha 6 veces para llamar la atención
        Timer vibrar = new Timer(50, null);
        final int[] contador = {0}; // Contador para número de movimientos
        final Point posicionOriginal = dialogo.getLocation(); // Guardar posición inicial

        vibrar.addActionListener(e -> {
            if (contador[0] < 6) {
                // Alternar movimiento de 5 pixeles izquierda y derecha
                int offset = (contador[0] % 2 == 0) ? 5 : -5;
                dialogo.setLocation(posicionOriginal.x + offset, posicionOriginal.y);
                contador[0]++;
            } else {
                // Restaurar posición original y detener el timer
                dialogo.setLocation(posicionOriginal);
                vibrar.stop();
            }
        });

        vibrar.start(); // Iniciar efecto de vibración

        // Mostrar el diálogo (bloqueante porque es modal)
        dialogo.setVisible(true);
    }

   
    // Método público para iniciar el proceso de recreación, abre la interfaz principal
    public void iniciarRecreacion() {
        abrirInterfaz();
    }

// Método privado para seleccionar un usuario a través de su ID ingresado por el usuario
    private Usuario seleccionarUsuarioRecreacion() {
        // Solicita al usuario ingresar un ID mediante un cuadro de diálogo
        String input = JOptionPane.showInputDialog("\nIngrese el ID del usuario:");

        // Si el usuario cancela el diálogo o no ingresa nada, retorna null
        if (input == null) {
            return null;
        }

        try {
            // Intenta convertir el texto ingresado a un número entero (ID)
            int id = Integer.parseInt(input);
            // Busca y devuelve el usuario con ese ID, o null si no existe
            return Usuario.buscarPorId(id);
        } catch (NumberFormatException e) {
            // Si el ID no es un número válido, muestra mensaje de error y retorna null
            JOptionPane.showMessageDialog(null, "ID inválido.");
            return null;
        }
    }

// Método para reservar cancha de fútbol para un usuario dado por ID
    private void reservarFutbol(int id) {
        // Busca el usuario con el ID dado
        Usuario u = Usuario.buscarPorId(id);
        // Verifica que el usuario exista y esté activo
        if (u == null || !u.isActivo()) {
            JOptionPane.showMessageDialog(null, "Usuario no válido o inactivo.");
            return; // Sale del método si usuario inválido
        }

        // Opciones que muestran el nombre de la cancha y ocupación actual
        String[] opciones = {
            "Fútbol 1 (" + jugadoresFutbol1 + "/" + max_Futbol + ")",
            "Fútbol 2 (" + jugadoresFutbol2 + "/" + max_Futbol + ")"
        };

        // Muestra un cuadro de diálogo para que el usuario seleccione la cancha
        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione cancha:", "Reservar Fútbol",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        // Si seleccionó alguna opción (no canceló)
        if (seleccion != -1) {
            try {
                // Pregunta cuántos jugadores quiere ingresar en esa cancha
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos jugadores desea ingresar?"));

                // Si seleccionó la cancha 0 (Fútbol 1)
                if (seleccion == 0) {
                    // Verifica si hay espacio suficiente para esa cantidad
                    if (jugadoresFutbol1 + cantidad <= max_Futbol) {
                        // Suma la cantidad a la ocupación actual y confirma reserva
                        jugadoresFutbol1 += cantidad;
                        JOptionPane.showMessageDialog(null, "Reservado por " + u.getNombre() + " en Fútbol 1. Jugadores actuales: " + jugadoresFutbol1);
                    } else {
                        // Si no hay espacio, muestra mensaje de cancha llena
                        JOptionPane.showMessageDialog(null, "Cancha llena. Máximo " + max_Futbol + " jugadores.");
                    }
                } else {
                    // Igual que arriba pero para la cancha Fútbol 2
                    if (jugadoresFutbol2 + cantidad <= max_Futbol) {
                        jugadoresFutbol2 += cantidad;
                        JOptionPane.showMessageDialog(null, "Reservado por " + u.getNombre() + " en Fútbol 2. Jugadores actuales: " + jugadoresFutbol2);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cancha llena. Máximo " + max_Futbol + " jugadores.");
                    }
                }
            } catch (NumberFormatException e) {
                // Si la cantidad ingresada no es válida muestra error
                JOptionPane.showMessageDialog(null, "Cantidad inválida.");
            }
        }
    }

// Método para reservar cancha de baloncesto para un usuario dado por ID
    private void reservarBaloncesto(int id) {
        // Busca y valida al usuario igual que en reservarFutbol
        Usuario u = Usuario.buscarPorId(id);
        if (u == null || !u.isActivo()) {
            JOptionPane.showMessageDialog(null, "Usuario no válido o inactivo.");
            return;
        }

        try {
            // Pregunta cantidad de jugadores a ingresar en baloncesto
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos jugadores desea ingresar a baloncesto?"));

            // Verifica si hay espacio suficiente en la cancha
            if ((jugadoresBasquetbol + cantidad) <= max_Basquetbol) {
                // Suma la cantidad y confirma la reserva
                jugadoresBasquetbol += cantidad;
                JOptionPane.showMessageDialog(null, "Reserva exitosa por " + u.getNombre() + ". Jugadores en cancha: " + jugadoresBasquetbol);
            } else {
                // Si no hay espacio, muestra mensaje de cancha llena
                JOptionPane.showMessageDialog(null, "No hay suficiente espacio en la cancha de baloncesto.");
            }
        } catch (NumberFormatException e) {
            // Muestra error si la cantidad no es válida
            JOptionPane.showMessageDialog(null, "Cantidad inválida.");
        }
    }

// Método para reservar cancha de tenis para un usuario dado por ID
    private void reservarTenis(int id) {
        // Busca y valida usuario igual que antes
        Usuario u = Usuario.buscarPorId(id);
        if (u == null || !u.isActivo()) {
            JOptionPane.showMessageDialog(null, "Usuario no válido o inactivo.");
            return;
        }

        // Opciones para seleccionar cancha de tenis con ocupación actual
        String[] opciones = {
            "Tenis 1 (" + jugadoresTenis1 + "/" + max_Tenis + ")",
            "Tenis 2 (" + jugadoresTenis2 + "/" + max_Tenis + ")"
        };

        // Diálogo para seleccionar cancha
        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione cancha:", "Reservar Tenis",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        // Si se seleccionó una cancha
        if (seleccion != -1) {
            try {
                // Pregunta cantidad a ingresar
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos jugadores desea ingresar?"));

                // Para Tenis 1
                if (seleccion == 0) {
                    if (jugadoresTenis1 + cantidad <= max_Tenis) {
                        jugadoresTenis1 += cantidad;
                        JOptionPane.showMessageDialog(null, "Reservado por " + u.getNombre() + " en Tenis 1. Jugadores actuales: " + jugadoresTenis1);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cancha llena. Máximo " + max_Tenis + " jugadores.");
                    }
                } else {
                    // Para Tenis 2
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

// Método para liberar espacios en canchas dado el ID del usuario
    private void liberarCancha(int id) {
        // Opciones de canchas para liberar
        String[] opciones = {"Fútbol 1", "Fútbol 2", "Baloncesto", "Tenis 1", "Tenis 2"};

        // Diálogo para seleccionar la cancha a liberar
        int seleccion = JOptionPane.showOptionDialog(null, "¿Qué cancha desea liberar?", "Liberar Cancha",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        // Si se seleccionó una cancha
        if (seleccion != -1) {
            try {
                // Pregunta cuántos jugadores se desean liberar
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos jugadores desea liberar?"));

                // Según la cancha seleccionada, reduce la ocupación pero nunca menos que cero
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

                // Confirma que la liberación se realizó correctamente
                JOptionPane.showMessageDialog(null, "Liberación realizada correctamente.");
            } catch (NumberFormatException e) {
                // Mensaje de error si la cantidad no es válida
                JOptionPane.showMessageDialog(null, "Cantidad inválida.");
            }
        }
    }

// Método para mostrar el estado actual (ocupación y capacidad) de todas las canchas
    private void mostrarEstadoRecreacion() {
        String estado = """
        Estado actual de las canchas:
        Fútbol 1: """ + jugadoresFutbol1 + "/" + max_Futbol + "\n"
                + "Fútbol 2: " + jugadoresFutbol2 + "/" + max_Futbol + "\n"
                + "Baloncesto: " + jugadoresBasquetbol + "/" + max_Basquetbol + "\n"
                + "Tenis 1: " + jugadoresTenis1 + "/" + max_Tenis + "\n"
                + "Tenis 2: " + jugadoresTenis2 + "/" + max_Tenis;

        // Muestra un cuadro de diálogo con toda la información de ocupación
        JOptionPane.showMessageDialog(null, estado);
    }
}
