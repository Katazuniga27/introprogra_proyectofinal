/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package introprogra_proyectofinal1.pkg0;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author andreyvargassolis
 */

public class SalaPesas {

    // Horarios disponibles para reservar en la sala de pesas
    private final String[] horarios = {"6:00 AM", "9:00 AM", "12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM"};
    
    // Arreglo que almacena los ID de los usuarios registrados por horario
    private final int[][] idsPorHorario = new int[6][50]; // hasta 50 personas por cada horario
    
    // Contador de cuántos usuarios están registrados en cada horario
    private final int[] contadorPorHorario = new int[6];

    public void abrirInterfaz() {
        // === CREAR VENTANA PRINCIPAL ===
        JFrame frame = new JFrame("Sala de Pesas - Smart Fit");
        frame.setSize(1000, 700);
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

        JLabel titulo = new JLabel("SALA DE PESAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Reserva tu horario de entrenamiento", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(200, 200, 200));

        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // === PANEL CENTRAL - HORARIOS ===
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        // Panel de horarios con grid
        JPanel panelHorarios = new JPanel(new GridLayout(2, 3, 15, 15));
        panelHorarios.setOpaque(false);
        panelHorarios.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Crear tarjetas de horarios
        for (int i = 0; i < horarios.length; i++) {
            JPanel tarjetaHorario = crearTarjetaHorario(i, frame);
            panelHorarios.add(tarjetaHorario);
        }

        panelCentral.add(panelHorarios, BorderLayout.CENTER);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setOpaque(false);

        BotonRedondeado btnIngresar = new BotonRedondeado("RESERVAR HORARIO", 25);
        btnIngresar.setPreferredSize(new Dimension(200, 50));
        btnIngresar.setBackground(new Color(0, 150, 136)); // Verde agua
        btnIngresar.addActionListener(e -> mostrarDialogoReserva(frame));

        BotonRedondeado btnSalir = new BotonRedondeado("CANCELAR RESERVA", 25);
        btnSalir.setPreferredSize(new Dimension(200, 50));
        btnSalir.setBackground(new Color(244, 67, 54)); // Rojo
        btnSalir.addActionListener(e -> mostrarDialogoCancelar(frame));

        BotonRedondeado btnVer = new BotonRedondeado("VER PRESENTES", 25);
        btnVer.setPreferredSize(new Dimension(200, 50));
        btnVer.setBackground(new Color(33, 150, 243)); // Azul
        btnVer.addActionListener(e -> mostrarPresentes(frame));

        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 50));
        btnCerrar.setBackground(new Color(96, 125, 139)); // Gris azulado
        btnCerrar.addActionListener(e -> frame.dispose());

        panelBotones.add(btnIngresar);
        panelBotones.add(btnSalir);
        panelBotones.add(btnVer);
        panelBotones.add(btnCerrar);

        // === ENSAMBLAR VENTANA ===
        fondo.add(panelTitulo, BorderLayout.NORTH);
        fondo.add(panelCentral, BorderLayout.CENTER);
        fondo.add(panelBotones, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel crearTarjetaHorario(int index, JFrame parent) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBackground(new Color(0, 0, 0, 120)); // Fondo semi-transparente
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Horario principal
        JLabel lblHorario = new JLabel(horarios[index], SwingConstants.CENTER);
        lblHorario.setFont(new Font("Arial", Font.BOLD, 20));
        lblHorario.setForeground(Color.WHITE);

        // Contador de personas
        JLabel lblContador = new JLabel(contadorPorHorario[index] + "/50", SwingConstants.CENTER);
        lblContador.setFont(new Font("Arial", Font.PLAIN, 16));
        lblContador.setForeground(new Color(200, 200, 200));

        // Barra de progreso visual
        JProgressBar progreso = new JProgressBar(0, 50);
        progreso.setValue(contadorPorHorario[index]);
        progreso.setStringPainted(false);
        progreso.setPreferredSize(new Dimension(0, 8));
        progreso.setBackground(new Color(255, 255, 255, 50));
        
        // Color de la barra según ocupación
        if (contadorPorHorario[index] < 25) {
            progreso.setForeground(new Color(76, 175, 80)); // Verde
        } else if (contadorPorHorario[index] < 40) {
            progreso.setForeground(new Color(255, 193, 7)); // Amarillo
        } else {
            progreso.setForeground(new Color(244, 67, 54)); // Rojo
        }

        // Panel para el contador y barra
        JPanel panelInfo = new JPanel(new BorderLayout(0, 5));
        panelInfo.setOpaque(false);
        panelInfo.add(lblContador, BorderLayout.NORTH);
        panelInfo.add(progreso, BorderLayout.SOUTH);

        tarjeta.add(lblHorario, BorderLayout.CENTER);
        tarjeta.add(panelInfo, BorderLayout.SOUTH);

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
                mostrarDetallesHorario(index, parent);
            }
        });

        return tarjeta;
    }

    private void mostrarDialogoReserva(JFrame parent) {
        // Crear diálogo personalizado
        JDialog dialogo = new JDialog(parent, "Reservar Horario", true);
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
        JLabel titulo = new JLabel("RESERVAR HORARIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Campo ID
        JPanel panelID = new JPanel(new BorderLayout(10, 10));
        panelID.setOpaque(false);

        JLabel lblID = new JLabel("ID de Usuario:");
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        lblID.setForeground(Color.WHITE);

        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        txtID.setPreferredSize(new Dimension(0, 35));
        txtID.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panelID.add(lblID, BorderLayout.NORTH);
        panelID.add(txtID, BorderLayout.CENTER);

        // Panel de horarios
        JPanel panelHorarios = new JPanel(new GridLayout(2, 3, 10, 10));
        panelHorarios.setOpaque(false);
        panelHorarios.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE),
            "Seleccionar Horario",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            Color.WHITE
        ));

        ButtonGroup grupoHorarios = new ButtonGroup();
        JRadioButton[] radioButtons = new JRadioButton[horarios.length];

        for (int i = 0; i < horarios.length; i++) {
            radioButtons[i] = new JRadioButton(horarios[i] + " (" + contadorPorHorario[i] + "/50)");
            radioButtons[i].setOpaque(false);
            radioButtons[i].setForeground(Color.WHITE);
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 12));
            radioButtons[i].setEnabled(contadorPorHorario[i] < 50);
            
            grupoHorarios.add(radioButtons[i]);
            panelHorarios.add(radioButtons[i]);
        }

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("CONFIRMAR", 20);
        btnConfirmar.setPreferredSize(new Dimension(120, 40));
        btnConfirmar.setBackground(new Color(0, 150, 136));

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

                int horarioSeleccionado = -1;
                for (int i = 0; i < radioButtons.length; i++) {
                    if (radioButtons[i].isSelected()) {
                        horarioSeleccionado = i;
                        break;
                    }
                }

                if (horarioSeleccionado == -1) {
                    mostrarMensajeError(dialogo, "Debe seleccionar un horario");
                    return;
                }

                if (ingresarSalaPesas(id, horarioSeleccionado)) {
                    mostrarMensajeExito(dialogo, "Reserva realizada exitosamente");
                    dialogo.dispose();
                    parent.dispose();
                    abrirInterfaz(); // Refrescar la interfaz
                }
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelID, BorderLayout.CENTER);
        panelPrincipal.add(panelHorarios, BorderLayout.SOUTH);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void mostrarDialogoCancelar(JFrame parent) {
        // Crear diálogo personalizado para cancelar
        JDialog dialogo = new JDialog(parent, "Cancelar Reserva", true);
        dialogo.setSize(450, 350);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        // Título con icono
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        
        JLabel titulo = new JLabel("CANCELAR RESERVA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(244, 67, 54)); // Rojo elegante
        
        JLabel subtitulo = new JLabel("Ingrese su ID para cancelar la reserva", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitulo.setForeground(new Color(200, 200, 200));
        subtitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // Panel central con campo de texto estilizado
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
            BorderFactory.createLineBorder(new Color(244, 67, 54, 150), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtID.setHorizontalAlignment(JTextField.CENTER);

        // Panel de información
        JPanel panelInfo = new JPanel();
        panelInfo.setOpaque(false);
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        
        JLabel infoLabel = new JLabel("Esta acción cancelará tu reserva actual");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(255, 193, 7));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelInfo.add(infoLabel);

        panelCentral.add(lblID, BorderLayout.NORTH);
        panelCentral.add(txtID, BorderLayout.CENTER);
        panelCentral.add(panelInfo, BorderLayout.SOUTH);

        // Botones estilizados
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("CANCELAR RESERVA", 25);
        btnConfirmar.setPreferredSize(new Dimension(180, 45));
        btnConfirmar.setBackground(new Color(244, 67, 54));
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));

        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(100, 45));
        btnCerrar.setBackground(new Color(96, 125, 139));
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));

        btnConfirmar.addActionListener(e -> {
            try {
                String texto = txtID.getText().trim();
                if (texto.isEmpty()) {
                    mostrarMensajeError(dialogo, "Debe ingresar un ID");
                    return;
                }
                
                int id = Integer.parseInt(texto);
                Usuario usuario = Usuario.buscarPorId(id);
                
                if (usuario != null) {
                    if (salirSalaPesas(id)) {
                        dialogo.dispose();
                        mostrarMensajeExito(parent, "Reserva cancelada exitosamente");
                        parent.dispose();
                        abrirInterfaz(); // Refrescar
                    } else {
                        mostrarMensajeError(dialogo, "No tienes reservas activas");
                    }
                } else {
                    mostrarMensajeError(dialogo, "Usuario no encontrado");
                }
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "El ID debe ser un número válido");
            }
        });

        btnCerrar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCerrar);

        // Ensamblar diálogo
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        // Foco automático en el campo de texto
        dialogo.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                txtID.requestFocus();
            }
        });

        dialogo.setVisible(true);
    }

    private void mostrarDetallesHorario(int index, JFrame parent) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("HORARIO: ").append(horarios[index]).append("\n");
        detalles.append("OCUPACIÓN: ").append(contadorPorHorario[index]).append("/50\n\n");
        detalles.append("USUARIOS REGISTRADOS:\n");

        for (int i = 0; i < contadorPorHorario[index]; i++) {
            int id = idsPorHorario[index][i];
            Usuario u = Usuario.buscarPorId(id);
            detalles.append("• ").append(id);
            if (u != null) detalles.append(" - ").append(u.getNombre());
            detalles.append("\n");
        }

        JOptionPane.showMessageDialog(parent, detalles.toString(), 
            "Detalles del Horario", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarPresentes(JFrame parent) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("=== USUARIOS REGISTRADOS POR HORARIO ===\n\n");

        for (int h = 0; h < horarios.length; h++) {
            mensaje.append("🕐 ").append(horarios[h]).append(" (").append(contadorPorHorario[h]).append("/50):\n");

            if (contadorPorHorario[h] == 0) {
                mensaje.append("   Sin usuarios registrados\n");
            } else {
                for (int i = 0; i < contadorPorHorario[h]; i++) {
                    int id = idsPorHorario[h][i];
                    Usuario u = Usuario.buscarPorId(id);
                    mensaje.append("   • ").append(id);
                    if (u != null) mensaje.append(" - ").append(u.getNombre());
                    mensaje.append("\n");
                }
            }
            mensaje.append("\n");
        }

        // Crear diálogo personalizado para mostrar la información
        JDialog dialogo = new JDialog(parent, "Usuarios Presentes", true);
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(parent);

        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBackground(new Color(45, 45, 45));
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        dialogo.add(scrollPane);
        dialogo.setVisible(true);
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

        // Botón OK elegante
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

        // Botón OK elegante
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

    // === MÉTODOS DE LÓGICA DE NEGOCIO (ADAPTADOS) ===
    
    public boolean ingresarSalaPesas(int id, int horarioIndex) {
        // Validar si el horario ya está lleno
        if (contadorPorHorario[horarioIndex] >= 50) {
            return false;
        }

        // Validar si el usuario ya está inscrito en ese horario
        for (int i = 0; i < contadorPorHorario[horarioIndex]; i++) {
            if (idsPorHorario[horarioIndex][i] == id) {
                return false;
            }
        }

        // Registrar al usuario
        idsPorHorario[horarioIndex][contadorPorHorario[horarioIndex]] = id;
        contadorPorHorario[horarioIndex]++;
        return true;
    }

    public boolean salirSalaPesas(int id) {
        boolean encontrado = false;

        // Buscar en cada horario si el ID está registrado
        for (int h = 0; h < horarios.length; h++) {
            for (int i = 0; i < contadorPorHorario[h]; i++) {
                if (idsPorHorario[h][i] == id) {
                    // Eliminar al usuario desplazando el resto hacia la izquierda
                    for (int j = i; j < contadorPorHorario[h] - 1; j++) {
                        idsPorHorario[h][j] = idsPorHorario[h][j + 1];
                    }
                    contadorPorHorario[h]--;
                    encontrado = true;
                    break;
                }
            }
        }

        return encontrado;
    }

    // Métodos legacy para compatibilidad
    public void iniciarSalaPesas() {
        abrirInterfaz();
    }

    private Usuario seleccionarUsuarioSalaPesas() {
        String input = JOptionPane.showInputDialog("Ingrese el ID del usuario:");
        if (input == null) return null;

        try {
            int id = Integer.parseInt(input);
            return Usuario.buscarPorId(id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
            return null;
        }
    }

    public void ingresarSalaPesas(int id) {
        // Método legacy - usar la interfaz moderna
        abrirInterfaz();
    }

    public void mostrarPresentesSalaPesas() {
        // Método legacy - usar la interfaz moderna
        abrirInterfaz();
    }
}

// === CLASES DE COMPONENTES PERSONALIZADOS ===

// Fondo con imagen oscura
class FondoPanel extends JPanel {
    private Image imagen;

    public FondoPanel(String rutaImagen) {
        imagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            g2.setColor(new Color(0, 0, 0, 180)); // Opacidad
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}

// Botón redondeado
class BotonRedondeado extends JButton {
    private int radius;

    public BotonRedondeado(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setBackground(new Color(39, 39, 39));
        setFont(new Font("Arial", Font.BOLD, 18));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(getForeground());
        g2.drawString(getText(), x, y);

        g2.dispose();
    }
}