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
 *
 * @author andreyvargassolis
 */
public class Auditorio {

    // Temas de charlas (pueden variar cada día si lo desea)
    static String[] temas = {"Salud", "Rutinas Especiales", "Capacitaciones"};

    // Horarios fijos
    static String[] horarios = {"10:00 AM", "3:00 PM"};

    // Matriz para inscripciones: [horario][posición en la lista de 30]
    static String[][] inscripciones = new String[2][30];

    // Llama al menu del auditorio
    public void menuAuditorio() {
        gestionarAuditorio(); // Inicia el menú del auditorio
    }

    // Abre el menú del auditorio para ver sus opciones y llamar la deseada
    public void gestionarAuditorio() {
        boolean salir = false;

        //Ciclo while que lo mantiene en este menú hasta decirdir salir
        while (!salir) {
            String opcion = JOptionPane.showInputDialog("""
                    Auditorio Fitness - Selecciona una opción:
                    1. Ver charlas disponibles
                    2. Inscribirse a una charla
                    3. Modificar charla
                    4. Salir
                    """);

            // detecta si el usuario canceló el cuadro de diálogo y termina el ciclo while para salir del menú.
            if (opcion == null) {
                break;
            }

            // opciones del menu
            switch (opcion) {
                case "1":
                    mostrarCharlas();
                    break;

                case "2":
                    Usuario uInA = seleccionarUsuarioAuditorio();
                    if (uInA != null && uInA.isActivo()) {
                        inscribirParticipanteAuditorio(uInA.getId());
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario inválido o inactivo.");
                    }
                    break;

                case "3":
                    modificarNombreCharla();
                    break;

                case "4":
                    salir = true;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }
        }
    }

    private Usuario seleccionarUsuarioAuditorio() {
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

    // Despliegue de informacion de los horarios
    private static void mostrarCharlas() {
        String mensaje = "Charlas disponibles en el Auditorio Fitness:\n";

        for (int i = 0; i < horarios.length; i++) {
            int cuposRestantes = contarCupos(i);
            mensaje += (i + 1) + ". " + temas[i % temas.length] + " - " + horarios[i] + " (" + cuposRestantes + " cupos disponibles)\n";
        }
        JOptionPane.showMessageDialog(null, mensaje);
    }

    // inscripcion de la charla
    private static void inscribirParticipanteAuditorio(int id) {
        String idParticipante = String.valueOf(id);

        String opciones = "Seleccione la charla:\n";
        for (int i = 0; i < horarios.length; i++) {
            int cuposRestantes = contarCupos(i);
            opciones += (i + 1) + ". " + temas[i % temas.length] + " - " + horarios[i] + " (" + cuposRestantes + " cupos disponibles)\n";
        }

        try {
            int seleccion = Integer.parseInt(JOptionPane.showInputDialog(opciones)) - 1;
            if (seleccion >= 0 && seleccion < horarios.length) {
                if (contarCupos(seleccion) == 0) {
                    JOptionPane.showMessageDialog(null, "Cupo lleno para esta charla.");
                    return;
                }

                // Validar si ya está inscrito
                for (int i = 0; i < 30; i++) {
                    if (idParticipante.equals(inscripciones[seleccion][i])) {
                        JOptionPane.showMessageDialog(null, "Ya estás inscrito en esta charla.");
                        return;
                    }
                }

                // Registrar participante
                for (int i = 0; i < 30; i++) {
                    if (inscripciones[seleccion][i] == null) {
                        inscripciones[seleccion][i] = idParticipante;
                        JOptionPane.showMessageDialog(null, "Inscripción exitosa en la charla de " + temas[seleccion % temas.length] + " a las " + horarios[seleccion]);
                        return;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selección no válida.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada no válida.");
        }
    }

    // Modifica la charla que se va a recibir
    private static void modificarNombreCharla() {
        String mensaje = "Seleccione la charla que desea modificar:\n";
        for (int i = 0; i < horarios.length; i++) {
            mensaje += (i + 1) + ". " + temas[i % temas.length] + " - " + horarios[i] + "\n";
        }

        try {
            int seleccion = Integer.parseInt(JOptionPane.showInputDialog(mensaje)) - 1;

            if (seleccion >= 0 && seleccion < horarios.length) {
                String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre para la charla:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    temas[seleccion % temas.length] = nuevoNombre.trim();
                    JOptionPane.showMessageDialog(null, "Nombre de la charla actualizado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Nombre no válido. No se realizaron cambios.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selección fuera de rango.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida.");
        }
    }

    //funcion con ciclo para hacer la suma de cuanta gente inscrita hay y hacer la resta al maximo de capacidad
    private static int contarCupos(int horarioIndex) {
        int inscritos = 0;
        for (String id : inscripciones[horarioIndex]) {
            if (id != null) {
                inscritos++;
            }
        }
        return 30 - inscritos;
    }

    public void abrirInterfaz() {
        // === CREAR VENTANA PRINCIPAL ===
        JFrame frame = new JFrame("Auditorio Fitness - Smart Fit");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // === FONDO CON IMAGEN ===
        FondoPanel fondo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondo.setLayout(new BorderLayout());
        frame.setContentPane(fondo);

        // === PANEL SUPERIOR - TÍTULO ===
        //Panel del título
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Título
        JLabel titulo = new JLabel("AUDITORIO FITNESS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);

        // Subtitulo 
        JLabel subtitulo = new JLabel("Charlas y capacitaciones especializadas", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(200, 200, 200));

        //donde están cada uno
        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // === PANEL CENTRAL - CHARLAS DISPONIBLES ===
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        // Panel de charlas con grid
        JPanel panelCharlas = new JPanel(new GridLayout(1, 2, 20, 20));
        panelCharlas.setOpaque(false);
        panelCharlas.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Crear tarjetas de charlas
        for (int i = 0; i < horarios.length; i++) {
            JPanel tarjetaCharla = crearTarjetaCharla(i, frame);
            panelCharlas.add(tarjetaCharla);
        }

        panelCentral.add(panelCharlas, BorderLayout.CENTER);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        //crea el layout de botoens
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        //establece fondo transparente
        panelBotones.setOpaque(false);

        //crea el botón
        BotonRedondeado btnInscribir = new BotonRedondeado("INSCRIBIRSE A CHARLA", 25);
        //tamaño del botón
        btnInscribir.setPreferredSize(new Dimension(220, 50));
        //color del botón
        btnInscribir.setBackground(new Color(76, 175, 80)); // Verde
        //acción del botón
        btnInscribir.addActionListener(e -> mostrarDialogoInscripcion(frame));

        //crea el botón
        BotonRedondeado btnModificar = new BotonRedondeado("MODIFICAR CHARLA", 25);
        //tamaño del botón
        btnModificar.setPreferredSize(new Dimension(200, 50));
        //color del botón
        btnModificar.setBackground(new Color(255, 152, 0)); // Naranja
        //acción del botón
        btnModificar.addActionListener(e -> mostrarDialogoModificar(frame));

        //crea el botón
        BotonRedondeado btnVer = new BotonRedondeado("VER INSCRITOS", 25);
        //tamaño del botón
        btnVer.setPreferredSize(new Dimension(180, 50));
        //color del botón
        btnVer.setBackground(new Color(33, 150, 243)); // Azul
        //acción del botón
        btnVer.addActionListener(e -> mostrarInscritos(frame));
        
        //crea el botón
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        //tamaño del botón
        btnCerrar.setPreferredSize(new Dimension(150, 50));
        //color del botón
        btnCerrar.setBackground(new Color(96, 125, 139)); // Gris azulado
        //acción del botón
        btnCerrar.addActionListener(e -> frame.dispose());

        panelBotones.add(btnInscribir);
        panelBotones.add(btnModificar);
        panelBotones.add(btnVer);
        panelBotones.add(btnCerrar);

        // === ENSAMBLAR VENTANA ===
        fondo.add(panelTitulo, BorderLayout.NORTH);
        fondo.add(panelCentral, BorderLayout.CENTER);
        fondo.add(panelBotones, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel crearTarjetaCharla(int index, JFrame parent) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBackground(new Color(0, 0, 0, 120)); // Fondo semi-transparente
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Panel superior con tema
        JPanel panelTema = new JPanel(new BorderLayout());
        panelTema.setOpaque(false);

        JLabel lblTema = new JLabel(temas[index % temas.length], SwingConstants.CENTER);
        lblTema.setFont(new Font("Arial", Font.BOLD, 24));
        lblTema.setForeground(Color.WHITE);

        // Icono para el tema
        String icono = "🎯"; // Default
        switch (temas[index % temas.length].toLowerCase()) {
            case "salud" ->
                icono = "🏥";
            case "rutinas especiales" ->
                icono = "💪";
            case "capacitaciones" ->
                icono = "🎓";
        }

        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 40));

        panelTema.add(lblIcono, BorderLayout.NORTH);
        panelTema.add(lblTema, BorderLayout.CENTER);

        // Panel central con horario
        JLabel lblHorario = new JLabel(horarios[index], SwingConstants.CENTER);
        lblHorario.setFont(new Font("Arial", Font.BOLD, 20));
        lblHorario.setForeground(new Color(255, 193, 7)); // Amarillo dorado

        // Panel inferior con cupos
        JPanel panelCupos = new JPanel(new BorderLayout());
        panelCupos.setOpaque(false);

        int cuposDisponibles = contarCupos(index);
        JLabel lblCupos = new JLabel(cuposDisponibles + "/30 cupos", SwingConstants.CENTER);
        lblCupos.setFont(new Font("Arial", Font.PLAIN, 16));
        lblCupos.setForeground(new Color(200, 200, 200));

        // Barra de progreso
        JProgressBar progreso = new JProgressBar(0, 30);
        progreso.setValue(30 - cuposDisponibles);
        progreso.setStringPainted(false);
        progreso.setPreferredSize(new Dimension(0, 8));
        progreso.setBackground(new Color(255, 255, 255, 50));

        if (cuposDisponibles > 15) {
            progreso.setForeground(new Color(76, 175, 80)); // Verde
        } else if (cuposDisponibles > 5) {
            progreso.setForeground(new Color(255, 193, 7)); // Amarillo
        } else {
            progreso.setForeground(new Color(244, 67, 54)); // Rojo
        }

        panelCupos.add(lblCupos, BorderLayout.NORTH);
        panelCupos.add(progreso, BorderLayout.SOUTH);

        tarjeta.add(panelTema, BorderLayout.NORTH);
        tarjeta.add(lblHorario, BorderLayout.CENTER);
        tarjeta.add(panelCupos, BorderLayout.SOUTH);

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
                mostrarDetallesCharla(index, parent);
            }
        });

        return tarjeta;
    }

    private void mostrarDialogoInscripcion(JFrame parent) {
        // Crear diálogo personalizado
        JDialog dialogo = new JDialog(parent, "Inscribirse a Charla", true);
        dialogo.setSize(500, 450);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("INSCRIPCIÓN A CHARLA", SwingConstants.CENTER);
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

        // Panel de charlas
        JPanel panelCharlas = new JPanel(new GridLayout(2, 1, 10, 10));
        panelCharlas.setOpaque(false);
        panelCharlas.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Charla",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        ButtonGroup grupoCharlas = new ButtonGroup();
        JRadioButton[] radioButtons = new JRadioButton[horarios.length];

        for (int i = 0; i < horarios.length; i++) {
            int cupos = contarCupos(i);
            radioButtons[i] = new JRadioButton(temas[i] + " - " + horarios[i] + " (" + cupos + "/30)");
            radioButtons[i].setOpaque(false);
            radioButtons[i].setForeground(Color.WHITE);
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            radioButtons[i].setEnabled(cupos > 0);

            grupoCharlas.add(radioButtons[i]);
            panelCharlas.add(radioButtons[i]);
        }

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("INSCRIBIRSE", 20);
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

                int charlaSeleccionada = -1;
                for (int i = 0; i < radioButtons.length; i++) {
                    if (radioButtons[i].isSelected()) {
                        charlaSeleccionada = i;
                        break;
                    }
                }

                if (charlaSeleccionada == -1) {
                    mostrarMensajeError(dialogo, "Debe seleccionar una charla");
                    return;
                }

                inscribirParticipanteAuditorio(id);
                mostrarMensajeExito(dialogo, "Inscripción realizada exitosamente");
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
        panelPrincipal.add(panelCharlas, BorderLayout.SOUTH);

        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void mostrarDialogoModificar(JFrame parent) {
        modificarNombreCharla();
        parent.dispose();
        abrirInterfaz(); // Refrescar
    }

    private void mostrarDetallesCharla(int index, JFrame parent) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("CHARLA: ").append(temas[index % temas.length]).append("\n");
        detalles.append("HORARIO: ").append(horarios[index]).append("\n");
        detalles.append("CUPOS: ").append(contarCupos(index)).append("/30\n\n");
        detalles.append("PARTICIPANTES INSCRITOS:\n");

        boolean hayInscritos = false;
        for (int i = 0; i < 30; i++) {
            if (inscripciones[index][i] != null) {
                int id = Integer.parseInt(inscripciones[index][i]);
                Usuario u = Usuario.buscarPorId(id);
                detalles.append("• ").append(id);
                if (u != null) {
                    detalles.append(" - ").append(u.getNombre());
                }
                detalles.append("\n");
                hayInscritos = true;
            }
        }

        if (!hayInscritos) {
            detalles.append("No hay participantes inscritos aún.");
        }

        JOptionPane.showMessageDialog(parent, detalles.toString(),
                "Detalles de la Charla", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarInscritos(JFrame parent) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("=== PARTICIPANTES INSCRITOS ===\n\n");

        for (int h = 0; h < horarios.length; h++) {
            mensaje.append("🎓 ").append(temas[h % temas.length]).append(" - ").append(horarios[h]).append(":\n");

            boolean hayInscritos = false;
            for (int i = 0; i < 30; i++) {
                if (inscripciones[h][i] != null) {
                    int id = Integer.parseInt(inscripciones[h][i]);
                    Usuario u = Usuario.buscarPorId(id);
                    mensaje.append("   • ").append(id);
                    if (u != null) {
                        mensaje.append(" - ").append(u.getNombre());
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

    }
}
