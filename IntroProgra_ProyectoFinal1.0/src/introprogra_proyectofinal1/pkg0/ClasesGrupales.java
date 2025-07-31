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
public class ClasesGrupales {
    
    // Nombres de las 6 clases disponibles
    private String[] clases = {"Yoga", "Crossfit", "Funcional", "Pilates", "Zumba", "Spinning"};
    
    // Cupos disponibles por clase (máximo 50 inicialmente)
    private int[] cuposDisponibles = {50, 50, 50, 50, 50, 50};
    
    // Matriz para registrar los ID de socios inscritos en cada clase
    private String[][] inscripciones = new String[6][50];
    
    // Horarios actuales asignados a cada clase
    private String[] horarios = {"8:00 AM", "10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM"};
    
    // Lista de horarios disponibles para seleccionar
    private String[] horariosDisponibles = {"8:00 AM", "10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM"};

    public void abrirInterfaz() {
        // === CREAR VENTANA PRINCIPAL ===
        JFrame frame = new JFrame("Clases Grupales - Smart Fit");
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

        JLabel titulo = new JLabel("CLASES GRUPALES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Entrenamientos dinámicos en grupo", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(200, 200, 200));

        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // === PANEL CENTRAL - GRID DE CLASES ===
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Panel de clases con grid 2x3
        JPanel panelClases = new JPanel(new GridLayout(2, 3, 15, 15));
        panelClases.setOpaque(false);
        panelClases.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crear tarjetas de clases
        for (int i = 0; i < clases.length; i++) {
            JPanel tarjetaClase = crearTarjetaClase(i, frame);
            panelClases.add(tarjetaClase);
        }

        panelCentral.add(panelClases, BorderLayout.CENTER);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setOpaque(false);

        BotonRedondeado btnReservar = new BotonRedondeado("RESERVAR CLASE", 25);
        btnReservar.setPreferredSize(new Dimension(200, 50));
        btnReservar.setBackground(new Color(156, 39, 176)); // Morado
        btnReservar.addActionListener(e -> mostrarDialogoReserva(frame));

        BotonRedondeado btnVer = new BotonRedondeado("VER INSCRITOS", 25);
        btnVer.setPreferredSize(new Dimension(180, 50));
        btnVer.setBackground(new Color(33, 150, 243)); // Azul
        btnVer.addActionListener(e -> mostrarInscritos(frame));

        BotonRedondeado btnModificar = new BotonRedondeado("MODIFICAR CLASE", 25);
        btnModificar.setPreferredSize(new Dimension(200, 50));
        btnModificar.setBackground(new Color(255, 152, 0)); // Naranja
        btnModificar.addActionListener(e -> mostrarDialogoModificar(frame));

        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 50));
        btnCerrar.setBackground(new Color(96, 125, 139)); // Gris azulado
        btnCerrar.addActionListener(e -> frame.dispose());

        panelBotones.add(btnReservar);
        panelBotones.add(btnVer);
        panelBotones.add(btnModificar);
        panelBotones.add(btnCerrar);

        // === ENSAMBLAR VENTANA ===
        fondo.add(panelTitulo, BorderLayout.NORTH);
        fondo.add(panelCentral, BorderLayout.CENTER);
        fondo.add(panelBotones, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel crearTarjetaClase(int index, JFrame parent) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBackground(new Color(0, 0, 0, 120));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Panel superior con icono y nombre
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        // Icono según el tipo de clase
        String icono = obtenerIconoClase(clases[index]);
        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 40));
        lblIcono.setPreferredSize(new Dimension(0, 50));

        // Nombre de la clase
        JLabel lblNombre = new JLabel(clases[index], SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        lblNombre.setForeground(Color.WHITE);

        panelSuperior.add(lblIcono, BorderLayout.NORTH);
        panelSuperior.add(lblNombre, BorderLayout.CENTER);

        // Panel central con horario
        JLabel lblHorario = new JLabel(horarios[index], SwingConstants.CENTER);
        lblHorario.setFont(new Font("Arial", Font.BOLD, 16));
        lblHorario.setForeground(new Color(255, 193, 7)); // Amarillo dorado
        lblHorario.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panel inferior con cupos
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);

        // Calcular inscritos
        int inscritos = contarInscritos(index);
        int disponibles = cuposDisponibles[index];

        JLabel lblCupos = new JLabel(disponibles + "/" + (inscritos + disponibles) + " cupos", SwingConstants.CENTER);
        lblCupos.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCupos.setForeground(new Color(200, 200, 200));

        // Barra de progreso
        int totalCupos = inscritos + disponibles;
        JProgressBar progreso = new JProgressBar(0, totalCupos);
        progreso.setValue(inscritos);
        progreso.setStringPainted(false);
        progreso.setPreferredSize(new Dimension(0, 8));
        progreso.setBackground(new Color(255, 255, 255, 50));

        // Color según disponibilidad
        if (disponibles > totalCupos * 0.5) {
            progreso.setForeground(new Color(76, 175, 80)); // Verde
        } else if (disponibles > 0) {
            progreso.setForeground(new Color(255, 193, 7)); // Amarillo
        } else {
            progreso.setForeground(new Color(244, 67, 54)); // Rojo
        }

        panelInferior.add(lblCupos, BorderLayout.NORTH);
        panelInferior.add(progreso, BorderLayout.SOUTH);

        tarjeta.add(panelSuperior, BorderLayout.NORTH);
        tarjeta.add(lblHorario, BorderLayout.CENTER);
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
                mostrarDetallesClase(index, parent);
            }
        });

        return tarjeta;
    }

    private String obtenerIconoClase(String nombreClase) {
        switch (nombreClase.toLowerCase()) {
            case "yoga": return "🧘";
            case "crossfit": return "🏋️‍";
            case "funcional": return "💪";
            case "pilates": return "🤸‍";
            case "zumba": return "💃";
            case "spinning": return "🚴";
            default: return "🏃‍";
        }
    }

    private int contarInscritos(int claseIndex) {
        int contador = 0;
        for (int i = 0; i < 50; i++) {
            if (inscripciones[claseIndex][i] != null) {
                contador++;
            }
        }
        return contador;
    }

    private void mostrarDialogoReserva(JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Reservar Clase", true);
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
        JLabel titulo = new JLabel("RESERVAR CLASE GRUPAL", SwingConstants.CENTER);
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

        // Panel de clases
        JPanel panelClases = new JPanel(new GridLayout(2, 3, 10, 10));
        panelClases.setOpaque(false);
        panelClases.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Clase",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        ButtonGroup grupoClases = new ButtonGroup();
        JRadioButton[] radioButtons = new JRadioButton[clases.length];

        for (int i = 0; i < clases.length; i++) {
            String texto = clases[i] + " (" + horarios[i] + ") - " + cuposDisponibles[i] + " cupos";
            radioButtons[i] = new JRadioButton(texto);
            radioButtons[i].setOpaque(false);
            radioButtons[i].setForeground(Color.WHITE);
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 12));
            radioButtons[i].setEnabled(cuposDisponibles[i] > 0);

            grupoClases.add(radioButtons[i]);
            panelClases.add(radioButtons[i]);
        }

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("RESERVAR", 20);
        btnConfirmar.setPreferredSize(new Dimension(130, 40));
        btnConfirmar.setBackground(new Color(156, 39, 176));

        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(96, 125, 139));

        btnConfirmar.addActionListener(e -> {
            try {
                String idStr = txtID.getText().trim();
                if (idStr.isEmpty()) {
                    mostrarMensajeError(dialogo, "Debe ingresar un ID");
                    return;
                }

                // Validar usuario si existe la clase Usuario
                try {
                    int id = Integer.parseInt(idStr);
                    Usuario usuario = Usuario.buscarPorId(id);
                    if (usuario == null || !usuario.isActivo()) {
                        mostrarMensajeError(dialogo, "Usuario inválido o inactivo");
                        return;
                    }
                } catch (Exception ex) {
                    // Si no existe la clase Usuario, continuar con la validación básica
                }

                int claseSeleccionada = -1;
                for (int i = 0; i < radioButtons.length; i++) {
                    if (radioButtons[i].isSelected()) {
                        claseSeleccionada = i;
                        break;
                    }
                }

                if (claseSeleccionada == -1) {
                    mostrarMensajeError(dialogo, "Debe seleccionar una clase");
                    return;
                }

                if (reservarClase(idStr, claseSeleccionada)) {
                    mostrarMensajeExito(dialogo, "Reserva exitosa en " + clases[claseSeleccionada]);
                    dialogo.dispose();
                    parent.dispose();
                    abrirInterfaz(); // Refrescar
                } else {
                    mostrarMensajeError(dialogo, "No se pudo realizar la reserva");
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
        panelPrincipal.add(panelClases, BorderLayout.SOUTH);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void mostrarDialogoModificar(JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Modificar Clase", true);
        dialogo.setSize(600, 600);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("MODIFICAR CLASE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Selección de clase a modificar
        JPanel panelSeleccion = new JPanel(new BorderLayout(0, 10));
        panelSeleccion.setOpaque(false);

        JLabel lblSeleccionar = new JLabel("Seleccionar clase a modificar:");
        lblSeleccionar.setFont(new Font("Arial", Font.BOLD, 16));
        lblSeleccionar.setForeground(Color.WHITE);

        JComboBox<String> comboClases = new JComboBox<>();
        for (int i = 0; i < clases.length; i++) {
            comboClases.addItem((i + 1) + ". " + clases[i] + " (" + horarios[i] + ")");
        }
        comboClases.setFont(new Font("Arial", Font.PLAIN, 14));
        comboClases.setPreferredSize(new Dimension(0, 35));

        panelSeleccion.add(lblSeleccionar, BorderLayout.NORTH);
        panelSeleccion.add(comboClases, BorderLayout.CENTER);

        // Panel de modificación
        JPanel panelModificacion = new JPanel(new GridLayout(3, 2, 10, 10));
        panelModificacion.setOpaque(false);
        panelModificacion.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Datos a Modificar",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        // Campo nombre
        JLabel lblNombre = new JLabel("Nuevo nombre:");
        lblNombre.setForeground(Color.WHITE);
        JTextField txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(0, 30));

        // Campo horario
        JLabel lblHorario = new JLabel("Nuevo horario:");
        lblHorario.setForeground(Color.WHITE);
        JComboBox<String> comboHorarios = new JComboBox<>(horariosDisponibles);
        comboHorarios.setPreferredSize(new Dimension(0, 30));

        // Campo cupos
        JLabel lblCupos = new JLabel("Cupos máximos:");
        lblCupos.setForeground(Color.WHITE);
        JTextField txtCupos = new JTextField();
        txtCupos.setPreferredSize(new Dimension(0, 30));

        panelModificacion.add(lblNombre);
        panelModificacion.add(txtNombre);
        panelModificacion.add(lblHorario);
        panelModificacion.add(comboHorarios);
        panelModificacion.add(lblCupos);
        panelModificacion.add(txtCupos);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        BotonRedondeado btnModificar = new BotonRedondeado("MODIFICAR", 20);
        btnModificar.setPreferredSize(new Dimension(130, 40));
        btnModificar.setBackground(new Color(255, 152, 0));

        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(96, 125, 139));

        btnModificar.addActionListener(e -> {
            try {
                int indiceSeleccionado = comboClases.getSelectedIndex();
                
                // Modificar nombre si no está vacío
                String nuevoNombre = txtNombre.getText().trim();
                if (!nuevoNombre.isEmpty()) {
                    clases[indiceSeleccionado] = nuevoNombre;
                }

                // Modificar horario
                horarios[indiceSeleccionado] = (String) comboHorarios.getSelectedItem();

                // Modificar cupos si se especifica
                String nuevoCupoStr = txtCupos.getText().trim();
                if (!nuevoCupoStr.isEmpty()) {
                    int reservados = contarInscritos(indiceSeleccionado);
                    int nuevoCupo = Integer.parseInt(nuevoCupoStr);
                    
                    if (nuevoCupo >= reservados && nuevoCupo <= 50) {
                        cuposDisponibles[indiceSeleccionado] = nuevoCupo - reservados;
                    } else {
                        mostrarMensajeError(dialogo, "Cupos debe ser entre " + reservados + " y 50");
                        return;
                    }
                }

                mostrarMensajeExito(dialogo, "Clase modificada correctamente");
                dialogo.dispose();
                parent.dispose();
                abrirInterfaz();
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "Cupos debe ser un número válido");
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        panelBotones.add(btnModificar);
        panelBotones.add(btnCancelar);

        JPanel panelCentral = new JPanel(new BorderLayout(0, 20));
        panelCentral.setOpaque(false);
        panelCentral.add(panelSeleccion, BorderLayout.NORTH);
        panelCentral.add(panelModificacion, BorderLayout.CENTER);

        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void mostrarDetallesClase(int claseIndex, JFrame parent) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("=== ").append(clases[claseIndex]).append(" ===\n\n");
        detalles.append("HORARIO: ").append(horarios[claseIndex]).append("\n");
        
        int inscritos = contarInscritos(claseIndex);
        int total = inscritos + cuposDisponibles[claseIndex];
        
        detalles.append("CUPOS: ").append(cuposDisponibles[claseIndex]).append("/").append(total).append("\n");
        detalles.append("INSCRITOS: ").append(inscritos).append("\n\n");
        detalles.append("PARTICIPANTES:\n");

        boolean hayInscritos = false;
        for (int i = 0; i < 50; i++) {
            if (inscripciones[claseIndex][i] != null) {
                String id = inscripciones[claseIndex][i];
                detalles.append("• ").append(id);
                
                // Buscar nombre del usuario si existe la clase
                try {
                    Usuario u = Usuario.buscarPorId(Integer.parseInt(id));
                    if (u != null) {
                        detalles.append(" - ").append(u.getNombre());
                    }
                } catch (Exception ex) {
                    // Si no existe la clase Usuario, solo mostrar ID
                }
                
                detalles.append("\n");
                hayInscritos = true;
            }
        }

        if (!hayInscritos) {
            detalles.append("No hay participantes inscritos aún.");
        }

        JOptionPane.showMessageDialog(parent, detalles.toString(),
                "Detalles - " + clases[claseIndex], JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarInscritos(JFrame parent) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("=== PARTICIPANTES POR CLASE ===\n\n");

        for (int c = 0; c < clases.length; c++) {
            mensaje.append(obtenerIconoClase(clases[c])).append(" ").append(clases[c])
                   .append(" (").append(horarios[c]).append("):\n");

            boolean hayInscritos = false;
            for (int i = 0; i < 50; i++) {
                if (inscripciones[c][i] != null) {
                    String id = inscripciones[c][i];
                    mensaje.append("   • ").append(id);
                    
                    try {
                        Usuario u = Usuario.buscarPorId(Integer.parseInt(id));
                        if (u != null) {
                            mensaje.append(" - ").append(u.getNombre());
                        }
                    } catch (Exception ex) {
                        // Ignorar si no existe la clase Usuario
                    }
                    
                    mensaje.append("\n");
                    hayInscritos = true;
                }
            }

            if (!hayInscritos) {
                mensaje.append("   Sin participantes inscritos\n");
            }
            mensaje.append("\n");
        }

        // Crear diálogo personalizado
        JDialog dialogo = new JDialog(parent, "Participantes Inscritos", true);
        dialogo.setSize(700, 600);
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

    private boolean reservarClase(String idSocio, int claseSeleccionada) {
        if (cuposDisponibles[claseSeleccionada] == 0) {
            return false; // Sin cupos
        }

        // Verificar si ya está registrado
        for (int j = 0; j < 50; j++) {
            if (idSocio.equals(inscripciones[claseSeleccionada][j])) {
                return false; // Ya registrado
            }
        }

        // Buscar espacio libre y registrar
        for (int j = 0; j < 50; j++) {
            if (inscripciones[claseSeleccionada][j] == null) {
                inscripciones[claseSeleccionada][j] = inscripciones[claseSeleccionada][j] = idSocio;
                cuposDisponibles[claseSeleccionada]--;
                return true;
            }
        }
        return false; // No hay espacio (no debería llegar aquí)
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

    // Método estático para menú simple (compatibilidad con código anterior)
    public static void menuClasesGrupales() {
        // Nombres de las 6 clases disponibles
        String[] clases = {"Yoga", "Crossfit", "Funcional", "Pilates", "Zumba", "Spinning"};

        // Cupos disponibles por clase (máximo 50 inicialmente)
        int[] cuposDisponibles = {50, 50, 50, 50, 50, 50};

        // Matriz para registrar los ID de socios inscritos en cada clase
        // inscripciones[clase][posicion]
        String[][] inscripciones = new String[6][50];

        // Horarios actuales asignados a cada clase (cada clase tiene un horario distinto)
        String[] horarios = {"8:00 AM", "10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM"};

        // Lista de horarios disponibles para seleccionar cuando se modifica una clase
        String[] horariosDisponibles = {"8:00 AM", "10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM"};

        boolean salir = false; // Controla si el usuario quiere cerrar el programa

        while (!salir) {
            // Menú principal mostrado con JOptionPane
            String opcion = JOptionPane.showInputDialog(
                    "Gimnasio - Selecciona una opción:\n"
                    + "1. Ver clases y cupos\n"
                    + "2. Reservar clase\n"
                    + "3. Salir\n"
                    + "4. Modificar clase");

            if (opcion == null) {
                break; // Si el usuario cierra la ventana, se sale del programa
            }
            switch (opcion) {
                case "1": // Mostrar clases y cupos disponibles
                    String mensaje = "Cupos disponibles:\n";
                    for (int i = 0; i < clases.length; i++) {
                        mensaje += (i + 1) + ". " + clases[i] + " - " + horarios[i] + ": " + cuposDisponibles[i] + " cupos\n";
                    }
                    JOptionPane.showMessageDialog(null, mensaje);
                    break;

                case "2": // Reservar un cupo en una clase
                    String idSocio = JOptionPane.showInputDialog("Ingrese su ID de socio:");

                    // Mostrar menú con las clases disponibles y sus cupos
                    String claseTexto = "Seleccione clase:\n";
                    for (int i = 0; i < clases.length; i++) {
                        claseTexto += (i + 1) + ". " + clases[i] + " (" + horarios[i] + ") - " + cuposDisponibles[i] + " cupos\n";
                    }

                    int claseSeleccionada = Integer.parseInt(JOptionPane.showInputDialog(claseTexto)) - 1;

                    if (claseSeleccionada >= 0 && claseSeleccionada < clases.length) {
                        if (cuposDisponibles[claseSeleccionada] == 0) {
                            // Si ya no hay cupos, se informa al usuario
                            JOptionPane.showMessageDialog(null, "Lo sentimos, no hay cupos en esa clase.");
                        } else {
                            // Verificar si el socio ya está registrado en esa clase
                            boolean yaRegistrado = false;
                            for (int j = 0; j < 50; j++) {
                                if (idSocio.equals(inscripciones[claseSeleccionada][j])) {
                                    yaRegistrado = true;
                                    break;
                                }
                            }

                            if (yaRegistrado) {
                                // El socio ya está inscrito en esta clase
                                JOptionPane.showMessageDialog(null, "Ya estás registrado en esta clase.");
                            } else {
                                // Buscar un espacio libre en esa clase para registrar al socio
                                for (int j = 0; j < 50; j++) {
                                    if (inscripciones[claseSeleccionada][j] == null) {
                                        inscripciones[claseSeleccionada][j] = idSocio; // Guardar el ID
                                        cuposDisponibles[claseSeleccionada]--; // Reducir cupos disponibles
                                        JOptionPane.showMessageDialog(null, "Reserva exitosa en " + clases[claseSeleccionada]);
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        // Si el número ingresado no es válido
                        JOptionPane.showMessageDialog(null, "Clase no válida.");
                    }

                    break;

                case "3": // Salir del programa
                    salir = true;
                    break;

                case "4": // Modificar nombre, horario o cupos de una clase
                    // Mostrar lista de clases para elegir cuál modificar
                    String menuModificar = "Seleccione la clase que desea modificar:\n";
                    for (int i = 0; i < clases.length; i++) {
                        menuModificar += (i + 1) + ". " + clases[i] + " (" + horarios[i] + ") - " + cuposDisponibles[i] + " cupos\n";
                    }

                    int indiceModificar = Integer.parseInt(JOptionPane.showInputDialog(menuModificar)) - 1;

                    if (indiceModificar >= 0 && indiceModificar < clases.length) {
                        // Cambiar el nombre de la clase
                        String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre para la clase (actual: " + clases[indiceModificar] + "):");
                        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                            clases[indiceModificar] = nuevoNombre;
                        }

                        // Cambiar el horario, mostrando las opciones posibles
                        String opcionesHorarios = "Seleccione nuevo horario:\n";
                        for (int i = 0; i < horariosDisponibles.length; i++) {
                            opcionesHorarios += (i + 1) + ". " + horariosDisponibles[i] + "\n";
                        }

                        int horarioSeleccionado = Integer.parseInt(JOptionPane.showInputDialog(opcionesHorarios)) - 1;
                        if (horarioSeleccionado >= 0 && horarioSeleccionado < horariosDisponibles.length) {
                            horarios[indiceModificar] = horariosDisponibles[horarioSeleccionado];
                        } else {
                            JOptionPane.showMessageDialog(null, "Horario no válido. Se mantiene el actual.");
                        }

                        // Ver cuántos socios ya están inscritos en esa clase
                        int reservados = 0;
                        for (int j = 0; j < 50; j++) {
                            if (inscripciones[indiceModificar][j] != null) {
                                reservados++;
                            }
                        }

                        // Cambiar el número máximo de cupos disponibles (mínimo: la cantidad ya ocupada)
                        String nuevoCupoStr = JOptionPane.showInputDialog(
                                "Cupos máximos (actuales: " + (reservados + cuposDisponibles[indiceModificar])
                                + "). Debe ser mayor o igual a reservados (" + reservados + "):");

                        if (nuevoCupoStr != null) {
                            int nuevoCupo = Integer.parseInt(nuevoCupoStr);
                            if (nuevoCupo >= reservados && nuevoCupo <= 50) {
                                // Actualiza los cupos disponibles sin afectar los ya ocupados
                                cuposDisponibles[indiceModificar] = nuevoCupo - reservados;
                                JOptionPane.showMessageDialog(null, "Clase modificada correctamente.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Valor inválido. Debe ser entre " + reservados + " y 50.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Selección inválida.");
                    }

                    break;

                default:
                    // Si la opción no es 1, 2, 3 o 4
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }
        }
    }
}


