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
public class Cabina {
    //atributos 
    private final String[] horarios = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"};
    private final int[][] reservas; // [cabina][horario] = ID de socio (0 = libre)
    private final String[] nombresCabinas = {"Cabina 1", "Cabina 2", "Cabina 3", "Cabina 4", "Cabina 5"};

    //constructor vacío
    public Cabina() {
        reservas = new int[5][9];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                reservas[i][j] = 0; 
            }
        }
    }

    //método para menú
    public void menuCabinas() {
        boolean salir = false;
        while (!salir) {
            String opcion = JOptionPane.showInputDialog(
                "MENÚ CABINAS\n" +
                "1. Ver disponibilidad\n" +
                "2. Reservar cabina\n" +
                "3. Cancelar reserva\n" +
                "4. Salir");
            
            if (opcion == null) break;

            switch (opcion) {
                case "1":
                    mostrarDisponibilidad();
                    break;
                    
                case "2":
                    Usuario uInC = seleccionarUsuarioCabina();
                    if (uInC != null && uInC.isActivo()) {
                        reservarCabina(uInC.getId());
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario inválido o inactivo.");
                    }
                    break;
                    
                case "3":
                    Usuario uOtC = seleccionarUsuarioCabina();
                    if (uOtC != null && uOtC.isActivo()) {
                        cancelarReserva(uOtC.getId());
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario inválido o inactivo.");
                    }
                    break;
                case "4":
                    salir = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }
        }
    }
    
    private Usuario seleccionarUsuarioCabina() {
        String input = JOptionPane.showInputDialog( "\nIngrese el ID del usuario:");
        if (input == null) return null;

        try {
            int id = Integer.parseInt(input);
            return Usuario.buscarPorId(id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
            return null;
        }
    }

    private void mostrarDisponibilidad() {
        StringBuilder mensaje = new StringBuilder("HORARIOS (9:00 - 17:00)\nCabina\\Hora\t");
        for (String hora : horarios) mensaje.append(hora).append("\t");
        mensaje.append("\n");
        
        for (int i = 0; i < 5; i++) {
            mensaje.append(nombresCabinas[i]).append("\t");
            for (int j = 0; j < 9; j++) {
                mensaje.append(reservas[i][j] == 0 ? "L" : "O").append("\t");
            }
            mensaje.append("\n");
        }
        JOptionPane.showMessageDialog(null, mensaje.toString());
    }

    private void reservarCabina(int id) {
        int ids = id;

        int cabina = Integer.parseInt(JOptionPane.showInputDialog(
            "Seleccione cabina:\n1. Cabina 1\n2. Cabina 2\n3. Cabina 3\n4. Cabina 4\n5. Cabina 5")) - 1;
        
        int horario = Integer.parseInt(JOptionPane.showInputDialog(
            "Seleccione horario (1-9):\n1. 9:00\n2. 10:00\n...")) - 1;
        
        if (reservas[cabina][horario] == 0) {
            reservas[cabina][horario] = ids;
            JOptionPane.showMessageDialog(null, "Reserva exitosa para " + horarios[horario]);
        } else {
            JOptionPane.showMessageDialog(null, "Horario ocupado");
        }
    }

    private void cancelarReserva(int id) {
        int ids = id;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (reservas[i][j] == ids) {
                    reservas[i][j] = 0;
                    JOptionPane.showMessageDialog(null, "Reserva cancelada");
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "No se encontraron reservas para este ID");
    }

    public void abrirInterfaz() {
        // === CREAR VENTANA PRINCIPAL ===
        JFrame frame = new JFrame("Cabinas Privadas - Smart Fit");
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

        JLabel titulo = new JLabel("CABINAS PRIVADAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Espacios exclusivos para entrenamientos personalizados", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(200, 200, 200));

        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // === PANEL CENTRAL - GRID DE CABINAS ===
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Panel de cabinas
        JPanel panelCabinas = new JPanel(new GridLayout(1, 5, 12, 12));
        panelCabinas.setOpaque(false);
        panelCabinas.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Crear tarjetas de cabinas
        for (int i = 0; i < 5; i++) {
            JPanel tarjetaCabina = crearTarjetaCabina(i, frame);
            panelCabinas.add(tarjetaCabina);
        }

        // Panel de horarios (barra lateral)
        JPanel panelHorarios = crearPanelHorarios();

        panelCentral.add(panelCabinas, BorderLayout.CENTER);
        panelCentral.add(panelHorarios, BorderLayout.EAST);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setOpaque(false);

        BotonRedondeado btnReservar = new BotonRedondeado("RESERVAR CABINA", 25);
        btnReservar.setPreferredSize(new Dimension(200, 50));
        btnReservar.setBackground(new Color(0, 150, 136)); // Verde agua
        btnReservar.addActionListener(e -> mostrarDialogoReserva(frame));

        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR RESERVA", 25);
        btnCancelar.setPreferredSize(new Dimension(200, 50));
        btnCancelar.setBackground(new Color(244, 67, 54)); // Rojo
        btnCancelar.addActionListener(e -> mostrarDialogoCancelar(frame));

        BotonRedondeado btnVer = new BotonRedondeado("VER DISPONIBILIDAD", 25);
        btnVer.setPreferredSize(new Dimension(200, 50));
        btnVer.setBackground(new Color(33, 150, 243)); // Azul
        btnVer.addActionListener(e -> mostrarDisponibilidadCompleta(frame));

        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 50));
        btnCerrar.setBackground(new Color(96, 125, 139)); // Gris azulado
        btnCerrar.addActionListener(e -> frame.dispose());

        panelBotones.add(btnReservar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnVer);
        panelBotones.add(btnCerrar);

        // === ENSAMBLAR VENTANA ===
        fondo.add(panelTitulo, BorderLayout.NORTH);
        fondo.add(panelCentral, BorderLayout.CENTER);
        fondo.add(panelBotones, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel crearTarjetaCabina(int cabinaIndex, JFrame parent) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBackground(new Color(0, 0, 0, 120));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Icono de cabina
        JLabel iconoCabina = new JLabel("🏠", SwingConstants.CENTER);
        iconoCabina.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoCabina.setPreferredSize(new Dimension(0, 50));

        // Nombre de la cabina
        JLabel lblNombre = new JLabel(nombresCabinas[cabinaIndex], SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombre.setForeground(Color.WHITE);

        // Contador de espacios ocupados
        int ocupados = 0;
        for (int j = 0; j < 9; j++) {
            if (reservas[cabinaIndex][j] != 0) ocupados++;
        }

        JLabel lblOcupacion = new JLabel(ocupados + "/9 horarios", SwingConstants.CENTER);
        lblOcupacion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblOcupacion.setForeground(new Color(200, 200, 200));

        // Barra de progreso
        JProgressBar progreso = new JProgressBar(0, 9);
        progreso.setValue(ocupados);
        progreso.setStringPainted(false);
        progreso.setPreferredSize(new Dimension(0, 8));
        progreso.setBackground(new Color(255, 255, 255, 50));

        if (ocupados <= 3) {
            progreso.setForeground(new Color(76, 175, 80)); // Verde
        } else if (ocupados <= 6) {
            progreso.setForeground(new Color(255, 193, 7)); // Amarillo
        } else {
            progreso.setForeground(new Color(244, 67, 54)); // Rojo
        }

        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.add(iconoCabina, BorderLayout.NORTH);
        panelSuperior.add(lblNombre, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout(0, 5));
        panelInferior.setOpaque(false);
        panelInferior.add(lblOcupacion, BorderLayout.NORTH);
        panelInferior.add(progreso, BorderLayout.SOUTH);

        tarjeta.add(panelSuperior, BorderLayout.CENTER);
        tarjeta.add(panelInferior, BorderLayout.SOUTH);

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
                mostrarDetallesCabina(cabinaIndex, parent);
            }
        });

        return tarjeta;
    }

    private JPanel crearPanelHorarios() {
        JPanel panelHorarios = new JPanel();
        panelHorarios.setLayout(new BoxLayout(panelHorarios, BoxLayout.Y_AXIS));
        panelHorarios.setOpaque(false);
        panelHorarios.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 1),
                        "Horarios Disponibles",
                        0, 0,
                        new Font("Arial", Font.BOLD, 14),
                        Color.WHITE
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelHorarios.setPreferredSize(new Dimension(180, 0));

        for (int h = 0; h < horarios.length; h++) {
            JPanel itemHorario = new JPanel(new BorderLayout());
            itemHorario.setOpaque(false);
            itemHorario.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel lblHora = new JLabel(horarios[h]);
            lblHora.setFont(new Font("Arial", Font.BOLD, 14));
            lblHora.setForeground(Color.WHITE);

            // Contar cabinas ocupadas en este horario
            int ocupadasEnHorario = 0;
            for (int c = 0; c < 5; c++) {
                if (reservas[c][h] != 0) ocupadasEnHorario++;
            }

            JLabel lblEstado = new JLabel(ocupadasEnHorario + "/5");
            lblEstado.setFont(new Font("Arial", Font.PLAIN, 12));
            
            if (ocupadasEnHorario == 0) {
                lblEstado.setForeground(new Color(76, 175, 80)); // Verde
                lblEstado.setText("✅ " + ocupadasEnHorario + "/5");
            } else if (ocupadasEnHorario < 5) {
                lblEstado.setForeground(new Color(255, 193, 7)); // Amarillo
                lblEstado.setText(ocupadasEnHorario + "/5");
            } else {
                lblEstado.setForeground(new Color(244, 67, 54)); // Rojo
                lblEstado.setText("❌ " + ocupadasEnHorario + "/5");
            }

            itemHorario.add(lblHora, BorderLayout.WEST);
            itemHorario.add(lblEstado, BorderLayout.EAST);

            panelHorarios.add(itemHorario);
        }

        return panelHorarios;
    }

    private void mostrarDialogoReserva(JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Reservar Cabina", true);
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("RESERVAR CABINA PRIVADA", SwingConstants.CENTER);
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

        // Panel de selección
        JPanel panelSeleccion = new JPanel(new GridLayout(1, 2, 20, 0));
        panelSeleccion.setOpaque(false);

        // Panel de cabinas
        JPanel panelCabinasSelect = new JPanel();
        panelCabinasSelect.setLayout(new BoxLayout(panelCabinasSelect, BoxLayout.Y_AXIS));
        panelCabinasSelect.setOpaque(false);
        panelCabinasSelect.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Cabina",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        ButtonGroup grupoCabinas = new ButtonGroup();
        JRadioButton[] radioCabinas = new JRadioButton[5];

        for (int i = 0; i < 5; i++) {
            radioCabinas[i] = new JRadioButton(nombresCabinas[i]);
            radioCabinas[i].setOpaque(false);
            radioCabinas[i].setForeground(Color.WHITE);
            radioCabinas[i].setFont(new Font("Arial", Font.PLAIN, 14));

            grupoCabinas.add(radioCabinas[i]);
            panelCabinasSelect.add(radioCabinas[i]);
        }

        // Panel de horarios
        JPanel panelHorariosSelect = new JPanel();
        panelHorariosSelect.setLayout(new BoxLayout(panelHorariosSelect, BoxLayout.Y_AXIS));
        panelHorariosSelect.setOpaque(false);
        panelHorariosSelect.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Horario",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        ButtonGroup grupoHorarios = new ButtonGroup();
        JRadioButton[] radioHorarios = new JRadioButton[9];

        for (int i = 0; i < 9; i++) {
            radioHorarios[i] = new JRadioButton(horarios[i]);
            radioHorarios[i].setOpaque(false);
            radioHorarios[i].setForeground(Color.WHITE);
            radioHorarios[i].setFont(new Font("Arial", Font.PLAIN, 14));

            grupoHorarios.add(radioHorarios[i]);
            panelHorariosSelect.add(radioHorarios[i]);
        }

        panelSeleccion.add(panelCabinasSelect);
        panelSeleccion.add(panelHorariosSelect);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("RESERVAR", 20);
        btnConfirmar.setPreferredSize(new Dimension(130, 40));
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

                int cabinaSeleccionada = -1;
                for (int i = 0; i < radioCabinas.length; i++) {
                    if (radioCabinas[i].isSelected()) {
                        cabinaSeleccionada = i;
                        break;
                    }
                }

                int horarioSeleccionado = -1;
                for (int i = 0; i < radioHorarios.length; i++) {
                    if (radioHorarios[i].isSelected()) {
                        horarioSeleccionado = i;
                        break;
                    }
                }

                if (cabinaSeleccionada == -1 || horarioSeleccionado == -1) {
                    mostrarMensajeError(dialogo, "Debe seleccionar cabina y horario");
                    return;
                }

                if (reservas[cabinaSeleccionada][horarioSeleccionado] != 0) {
                    mostrarMensajeError(dialogo, "Horario ya ocupado");
                    return;
                }

                reservas[cabinaSeleccionada][horarioSeleccionado] = id;
                mostrarMensajeExito(dialogo, "Reserva realizada exitosamente");
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
        panelPrincipal.add(panelID, BorderLayout.CENTER);
        panelPrincipal.add(panelSeleccion, BorderLayout.SOUTH);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void mostrarDialogoCancelar(JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Cancelar Reserva", true);
        dialogo.setSize(450, 300);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel titulo = new JLabel("CANCELAR RESERVA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(244, 67, 54));

        JLabel subtitulo = new JLabel("Ingrese su ID para cancelar la reserva", SwingConstants.CENTER);
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
                BorderFactory.createLineBorder(new Color(244, 67, 54, 150), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        txtID.setHorizontalAlignment(JTextField.CENTER);

        panelCentral.add(lblID, BorderLayout.NORTH);
        panelCentral.add(txtID, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("CANCELAR RESERVA", 25);
        btnConfirmar.setPreferredSize(new Dimension(180, 45));
        btnConfirmar.setBackground(new Color(244, 67, 54));

        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(100, 45));
        btnCerrar.setBackground(new Color(96, 125, 139));

        btnConfirmar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtID.getText().trim());
                Usuario usuario = Usuario.buscarPorId(id);

                if (usuario != null) {
                    boolean encontrado = false;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (reservas[i][j] == id) {
                                reservas[i][j] = 0;
                                encontrado = true;
                                break;
                            }
                        }
                        if (encontrado) break;
                    }

                    if (encontrado) {
                        dialogo.dispose();
                        mostrarMensajeExito(parent, "Reserva cancelada exitosamente");
                        parent.dispose();
                        abrirInterfaz();
                    } else {
                        mostrarMensajeError(dialogo, "No tienes reservas activas");
                    }
                } else {
                    mostrarMensajeError(dialogo, "Usuario no encontrado");
                }
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        btnCerrar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCerrar);

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void mostrarDisponibilidadCompleta(JFrame parent) {
        // Crear diálogo personalizado para mostrar disponibilidad completa
        JDialog dialogo = new JDialog(parent, "Disponibilidad de Cabinas", true);
        dialogo.setSize(700, 500);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("DISPONIBILIDAD DE CABINAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Crear tabla de disponibilidad
        JPanel panelTabla = new JPanel();
        panelTabla.setOpaque(false);
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));

        // Encabezado de horarios
        JPanel panelEncabezado = new JPanel(new GridLayout(1, 10, 2, 2));
        panelEncabezado.setOpaque(false);
        
        JLabel lblVacio = new JLabel("Cabina\\Hora", SwingConstants.CENTER);
        lblVacio.setFont(new Font("Arial", Font.BOLD, 12));
        lblVacio.setForeground(Color.WHITE);
        lblVacio.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        lblVacio.setOpaque(true);
        lblVacio.setBackground(new Color(0, 0, 0, 150));
        panelEncabezado.add(lblVacio);

        for (String horario : horarios) {
            JLabel lblHorario = new JLabel(horario, SwingConstants.CENTER);
            lblHorario.setFont(new Font("Arial", Font.BOLD, 10));
            lblHorario.setForeground(Color.WHITE);
            lblHorario.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            lblHorario.setOpaque(true);
            lblHorario.setBackground(new Color(33, 150, 243, 150));
            panelEncabezado.add(lblHorario);
        }

        panelTabla.add(panelEncabezado);

        // Filas de cabinas
        for (int i = 0; i < 5; i++) {
            JPanel filaCabina = new JPanel(new GridLayout(1, 10, 2, 2));
            filaCabina.setOpaque(false);

            // Nombre de la cabina
            JLabel lblCabina = new JLabel(nombresCabinas[i], SwingConstants.CENTER);
            lblCabina.setFont(new Font("Arial", Font.BOLD, 12));
            lblCabina.setForeground(Color.WHITE);
            lblCabina.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            lblCabina.setOpaque(true);
            lblCabina.setBackground(new Color(96, 125, 139, 150));
            filaCabina.add(lblCabina);

            // Estados de horarios
            for (int j = 0; j < 9; j++) {
                JLabel lblEstado = new JLabel();
                lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
                lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
                lblEstado.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                lblEstado.setOpaque(true);

                if (reservas[i][j] == 0) {
                    lblEstado.setText("L");
                    lblEstado.setBackground(new Color(76, 175, 80, 150)); // Verde - Libre
                    lblEstado.setForeground(Color.WHITE);
                } else {
                    lblEstado.setText("O");
                    lblEstado.setBackground(new Color(244, 67, 54, 150)); // Rojo - Ocupado
                    lblEstado.setForeground(Color.WHITE);
                }

                filaCabina.add(lblEstado);
            }

            panelTabla.add(filaCabina);
        }

        // Leyenda
        JPanel panelLeyenda = new JPanel(new FlowLayout());
        panelLeyenda.setOpaque(false);
        panelLeyenda.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JLabel lblLeyendaLibre = new JLabel("L = Libre");
        lblLeyendaLibre.setForeground(new Color(76, 175, 80));
        lblLeyendaLibre.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblLeyendaOcupado = new JLabel("O = Ocupado");
        lblLeyendaOcupado.setForeground(new Color(244, 67, 54));
        lblLeyendaOcupado.setFont(new Font("Arial", Font.BOLD, 14));

        panelLeyenda.add(lblLeyendaLibre);
        panelLeyenda.add(Box.createHorizontalStrut(30));
        panelLeyenda.add(lblLeyendaOcupado);

        // Botón cerrar
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(120, 40));
        btnCerrar.setBackground(new Color(96, 125, 139));
        btnCerrar.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnCerrar);

        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(panelTabla);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelLeyenda, BorderLayout.SOUTH);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void mostrarDetallesCabina(int cabinaIndex, JFrame parent) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("=== ").append(nombresCabinas[cabinaIndex]).append(" ===\n\n");
        
        int reservasActivas = 0;
        for (int j = 0; j < 9; j++) {
            if (reservas[cabinaIndex][j] != 0) reservasActivas++;
        }
        
        detalles.append("OCUPACIÓN: ").append(reservasActivas).append("/9 horarios\n\n");
        detalles.append("DETALLE POR HORARIO:\n");

        for (int j = 0; j < 9; j++) {
            detalles.append("🕐 ").append(horarios[j]).append(": ");
            if (reservas[cabinaIndex][j] == 0) {
                detalles.append("LIBRE ✅\n");
            } else {
                detalles.append("OCUPADO por ID ").append(reservas[cabinaIndex][j]);
                Usuario u = Usuario.buscarPorId(reservas[cabinaIndex][j]);
                if (u != null) {
                    detalles.append(" (").append(u.getNombre()).append(")");
                }
                detalles.append(" ❌\n");
            }
        }

        // Crear diálogo personalizado
        JDialog dialogo = new JDialog(parent, "Detalles - " + nombresCabinas[cabinaIndex], true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        JTextArea textArea = new JTextArea(detalles.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBackground(new Color(0, 0, 0, 180));
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100)));

        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 20);
        btnCerrar.setPreferredSize(new Dimension(120, 35));
        btnCerrar.setBackground(new Color(96, 125, 139));
        btnCerrar.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBoton.add(btnCerrar);

        fondoDialogo.add(scrollPane, BorderLayout.CENTER);
        fondoDialogo.add(panelBoton, BorderLayout.SOUTH);

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