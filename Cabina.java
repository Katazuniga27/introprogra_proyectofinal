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
        // Crea una nueva ventana JFrame con el título "Cabinas Privadas - Smart Fit"
        JFrame frame = new JFrame("Cabinas Privadas - Smart Fit");

        // Establece el tamaño de la ventana a 1200x800 píxeles
        frame.setSize(1200, 800);

        // Configura la operación de cierre para que solo cierre esta ventana (no toda la aplicación)
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Centra la ventana en la pantalla
        frame.setLocationRelativeTo(null);

        // === FONDO CON IMAGEN ===
        // Crea un panel con fondo de imagen usando la ruta especificada
        FondoPanel fondo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");

        // Establece el layout del panel de fondo como BorderLayout
        fondo.setLayout(new BorderLayout());

        // Establece el panel de fondo como content pane de la ventana
        frame.setContentPane(fondo);

        // === PANEL SUPERIOR - TÍTULO ===
        // Crea un panel para el título con BorderLayout
        JPanel panelTitulo = new JPanel(new BorderLayout());

        // Hace el panel transparente
        panelTitulo.setOpaque(false);

        // Añade un borde vacío (márgenes) de 20px arriba/abajo y 30px izquierda/derecha
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Crea el label del título principal
        JLabel titulo = new JLabel("CABINAS PRIVADAS", SwingConstants.CENTER);

        // Establece la fuente del título: Arial, negrita, tamaño 36
        titulo.setFont(new Font("Arial", Font.BOLD, 36));

        // Establece el color del texto a blanco
        titulo.setForeground(Color.WHITE);

        // Crea el label del subtítulo
        JLabel subtitulo = new JLabel("Espacios exclusivos para entrenamientos personalizados", SwingConstants.CENTER);

        // Establece la fuente del subtítulo: Arial, cursiva, tamaño 18
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));

        // Establece el color del texto a gris claro (RGB: 200,200,200)
        subtitulo.setForeground(new Color(200, 200, 200));

        // Añade el título al centro del panel
        panelTitulo.add(titulo, BorderLayout.CENTER);

        // Añade el subtítulo en la parte inferior del panel
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // === PANEL CENTRAL - GRID DE CABINAS ===
        // Crea el panel central con BorderLayout
        JPanel panelCentral = new JPanel(new BorderLayout());

        // Hace el panel transparente
        panelCentral.setOpaque(false);

        // Añade márgenes: 0 arriba, 20px izquierda/derecha, 20px abajo
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Panel para las cabinas
        JPanel panelCabinas = new JPanel(new GridLayout(1, 5, 12, 12));

        // Hace el panel transparente
        panelCabinas.setOpaque(false);

        // Añade márgenes internos de 20px arriba/abajo y 10px izquierda/derecha
        panelCabinas.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Crea 5 tarjetas de cabinas (una para cada cabina)
        for (int i = 0; i < 5; i++) {
            // Crea una tarjeta para la cabina i y la añade al panel
            JPanel tarjetaCabina = crearTarjetaCabina(i, frame);
            panelCabinas.add(tarjetaCabina);
        }

        // Crea el panel de horarios (barra lateral derecha)
        JPanel panelHorarios = crearPanelHorarios();

        // Añade el panel de cabinas al centro
        panelCentral.add(panelCabinas, BorderLayout.CENTER);

        // Añade el panel de horarios a la derecha
        panelCentral.add(panelHorarios, BorderLayout.EAST);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        // Crea panel para los botones con FlowLayout centrado
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Hace el panel transparente
        panelBotones.setOpaque(false);

        // Crea botón "RESERVAR CABINA" con bordes redondeados (radio 25)
        BotonRedondeado btnReservar = new BotonRedondeado("RESERVAR CABINA", 25);

        // Establece tamaño preferido del botón
        btnReservar.setPreferredSize(new Dimension(200, 50));

        // Establece color de fondo (verde agua)
        btnReservar.setBackground(new Color(0, 150, 136));

        // Añade ActionListener para mostrar diálogo de reserva al hacer clic
        btnReservar.addActionListener(e -> mostrarDialogoReserva(frame));

        // Crea botón "CANCELAR RESERVA"
        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR RESERVA", 25);
        btnCancelar.setPreferredSize(new Dimension(200, 50));

        // Color de fondo rojo
        btnCancelar.setBackground(new Color(244, 67, 54));

        // Añade ActionListener para mostrar diálogo de cancelación
        btnCancelar.addActionListener(e -> mostrarDialogoCancelar(frame));

        // Crea botón "VER DISPONIBILIDAD"
        BotonRedondeado btnVer = new BotonRedondeado("VER DISPONIBILIDAD", 25);
        btnVer.setPreferredSize(new Dimension(200, 50));

        // Color de fondo azul
        btnVer.setBackground(new Color(33, 150, 243));

        // Añade ActionListener para mostrar disponibilidad completa
        btnVer.addActionListener(e -> mostrarDisponibilidadCompleta(frame));

        // Crea botón "CERRAR"
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 50));

        // Color de fondo gris azulado
        btnCerrar.setBackground(new Color(96, 125, 139));

        // Añade ActionListener para cerrar la ventana
        btnCerrar.addActionListener(e -> frame.dispose());

        // Añade todos los botones al panel de botones
        panelBotones.add(btnReservar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnVer);
        panelBotones.add(btnCerrar);

        // === ENSAMBLAR VENTANA ===
        // Añade el panel de título en la parte superior del fondo
        fondo.add(panelTitulo, BorderLayout.NORTH);

        // Añade el panel central en el centro del fondo
        fondo.add(panelCentral, BorderLayout.CENTER);

        // Añade el panel de botones en la parte inferior del fondo
        fondo.add(panelBotones, BorderLayout.SOUTH);

        // Hace visible la ventana
        frame.setVisible(true);
    }

    private JPanel crearTarjetaCabina(int cabinaIndex, JFrame parent) {
        // Crea un panel para la tarjeta de cabina con BorderLayout
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());

        // Establece fondo negro semitransparente (RGB 0,0,0 con alpha 120)
        tarjeta.setBackground(new Color(0, 0, 0, 120));

        // Crea un borde compuesto: línea blanca semitransparente + margen interno
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Crea icono de cabina (emoji de casa)
        JLabel iconoCabina = new JLabel("🏠", SwingConstants.CENTER);

        // Establece fuente y tamaño
        iconoCabina.setFont(new Font("Arial", Font.PLAIN, 40));

        // Establece altura preferida de 50px
        iconoCabina.setPreferredSize(new Dimension(0, 50));

        // Crea label con el nombre de la cabina (desde array nombresCabinas)
        JLabel lblNombre = new JLabel(nombresCabinas[cabinaIndex], SwingConstants.CENTER);

        // Establece fuente: Arial, negrita, tamaño 18
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));

        // Texto en color blanco
        lblNombre.setForeground(Color.WHITE);

        // Cuenta cuántos horarios están ocupados en esta cabina
        int ocupados = 0;
        for (int j = 0; j < 9; j++) {
            if (reservas[cabinaIndex][j] != 0) {
                ocupados++;
            }
        }

        // Crea label para mostrar ocupación (ej: "3/9 horarios")
        JLabel lblOcupacion = new JLabel(ocupados + "/9 horarios", SwingConstants.CENTER);

        // Establece fuente: Arial, normal, tamaño 14
        lblOcupacion.setFont(new Font("Arial", Font.PLAIN, 14));

        // Texto en gris claro
        lblOcupacion.setForeground(new Color(200, 200, 200));

        // Crea barra de progreso para visualizar ocupación (rango 0-9)
        JProgressBar progreso = new JProgressBar(0, 9);

        // Establece valor actual de ocupación
        progreso.setValue(ocupados);

        // Oculta el texto de porcentaje
        progreso.setStringPainted(false);

        // Establece altura preferida de 8px
        progreso.setPreferredSize(new Dimension(0, 8));

        // Fondo blanco semitransparente
        progreso.setBackground(new Color(255, 255, 255, 50));

        // Cambia color de la barra según nivel de ocupación
        if (ocupados <= 3) {
            // Verde si hay 3 o menos ocupados
            progreso.setForeground(new Color(76, 175, 80));
        } else if (ocupados <= 6) {
            // Amarillo si hay entre 4-6 ocupados
            progreso.setForeground(new Color(255, 193, 7));
        } else {
            // Rojo si hay más de 6 ocupados
            progreso.setForeground(new Color(244, 67, 54));
        }

        // Panel superior (contiene icono y nombre)
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        // Añade icono en la parte superior
        panelSuperior.add(iconoCabina, BorderLayout.NORTH);

        // Añade nombre en el centro
        panelSuperior.add(lblNombre, BorderLayout.CENTER);

        // Panel inferior (contiene ocupación y barra de progreso)
        JPanel panelInferior = new JPanel(new BorderLayout(0, 5));
        panelInferior.setOpaque(false);

        // Añade label de ocupación arriba
        panelInferior.add(lblOcupacion, BorderLayout.NORTH);

        // Añade barra de progreso abajo
        panelInferior.add(progreso, BorderLayout.SOUTH);

        // Añade panel superior al centro de la tarjeta
        tarjeta.add(panelSuperior, BorderLayout.CENTER);

        // Añade panel inferior en la parte baja de la tarjeta
        tarjeta.add(panelInferior, BorderLayout.SOUTH);

        // Añade MouseListener para efectos hover y clic
        tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Al entrar el mouse: fondo más claro y cursor de mano
                tarjeta.setBackground(new Color(255, 255, 255, 30));
                tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Al salir el mouse: vuelve al fondo original y cursor normal
                tarjeta.setBackground(new Color(0, 0, 0, 120));
                tarjeta.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Al hacer clic: muestra detalles de la cabina
                mostrarDetallesCabina(cabinaIndex, parent);
            }
        });

        return tarjeta;
    }

    private JPanel crearPanelHorarios() {
        // Crea panel para horarios con BoxLayout vertical
        JPanel panelHorarios = new JPanel();
        panelHorarios.setLayout(new BoxLayout(panelHorarios, BoxLayout.Y_AXIS));

        // Hace el panel transparente
        panelHorarios.setOpaque(false);

        // Crea un borde compuesto con título y márgenes
        panelHorarios.setBorder(BorderFactory.createCompoundBorder(
                // Borde con título "Horarios Disponibles"
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 1),
                        "Horarios Disponibles",
                        0, 0, // Alineación del título
                        new Font("Arial", Font.BOLD, 14), // Fuente del título
                        Color.WHITE // Color del texto del título
                ),
                // Márgenes internos
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Establece ancho preferido de 180px
        panelHorarios.setPreferredSize(new Dimension(180, 0));

        // Itera sobre todos los horarios disponibles
        for (int h = 0; h < horarios.length; h++) {
            // Crea un panel para cada horario
            JPanel itemHorario = new JPanel(new BorderLayout());
            itemHorario.setOpaque(false);

            // Añade márgenes internos
            itemHorario.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // Crea label con la hora
            JLabel lblHora = new JLabel(horarios[h]);

            // Establece fuente: Arial, negrita, tamaño 14
            lblHora.setFont(new Font("Arial", Font.BOLD, 14));

            // Texto en blanco
            lblHora.setForeground(Color.WHITE);

            // Cuenta cuántas cabinas están ocupadas en este horario
            int ocupadasEnHorario = 0;
            for (int c = 0; c < 5; c++) {
                if (reservas[c][h] != 0) {
                    ocupadasEnHorario++;
                }
            }

            // Crea label para mostrar estado (ej: "2/5")
            JLabel lblEstado = new JLabel(ocupadasEnHorario + "/5");
            lblEstado.setFont(new Font("Arial", Font.PLAIN, 12));

            // Cambia color y añade icono según disponibilidad
            if (ocupadasEnHorario == 0) {
                // Verde con checkmark si todas libres
                lblEstado.setForeground(new Color(76, 175, 80));
                lblEstado.setText("✅ " + ocupadasEnHorario + "/5");
            } else if (ocupadasEnHorario < 5) {
                // Amarillo si algunas ocupadas
                lblEstado.setForeground(new Color(255, 193, 7));
                lblEstado.setText(ocupadasEnHorario + "/5");
            } else {
                // Rojo con X si todas ocupadas
                lblEstado.setForeground(new Color(244, 67, 54));
                lblEstado.setText("❌ " + ocupadasEnHorario + "/5");
            }

            // Añade la hora a la izquierda
            itemHorario.add(lblHora, BorderLayout.WEST);

            // Añade el estado a la derecha
            itemHorario.add(lblEstado, BorderLayout.EAST);

            // Añade el item al panel de horarios
            panelHorarios.add(itemHorario);
        }

        return panelHorarios;
    }

    private void mostrarDialogoReserva(JFrame parent) {
        // Crea un diálogo modal (bloqueante) para reservas
        JDialog dialogo = new JDialog(parent, "Reservar Cabina", true);

        // Establece tamaño del diálogo
        dialogo.setSize(600, 500);

        // Centra el diálogo respecto a la ventana padre
        dialogo.setLocationRelativeTo(parent);

        // Crea panel con fondo de imagen
        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal del diálogo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);

        // Añade márgenes de 20px
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título del diálogo
        JLabel titulo = new JLabel("RESERVAR CABINA PRIVADA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        // Añade margen inferior de 20px
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel para el campo de ID de usuario
        JPanel panelID = new JPanel(new BorderLayout(10, 10));
        panelID.setOpaque(false);

        // Label para el campo ID
        JLabel lblID = new JLabel("ID de Usuario:");
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        lblID.setForeground(Color.WHITE);

        // Campo de texto para ingresar ID
        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));

        // Establece altura preferida de 35px
        txtID.setPreferredSize(new Dimension(0, 35));

        // Crea borde compuesto: línea blanca + márgenes internos
        txtID.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Añade label y campo de texto al panel
        panelID.add(lblID, BorderLayout.NORTH);
        panelID.add(txtID, BorderLayout.CENTER);

        // Panel para las selecciones (cabinas y horarios)
        JPanel panelSeleccion = new JPanel(new GridLayout(1, 2, 20, 0));
        panelSeleccion.setOpaque(false);

        // Panel para selección de cabinas
        JPanel panelCabinasSelect = new JPanel();
        panelCabinasSelect.setLayout(new BoxLayout(panelCabinasSelect, BoxLayout.Y_AXIS));
        panelCabinasSelect.setOpaque(false);

        // Borde con título "Seleccionar Cabina"
        panelCabinasSelect.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Cabina",
                0, 0, // Alineación del título
                new Font("Arial", Font.BOLD, 14), // Fuente del título
                Color.WHITE // Color del texto
        ));

        // Grupo de botones para selección única de cabinas
        ButtonGroup grupoCabinas = new ButtonGroup();
        JRadioButton[] radioCabinas = new JRadioButton[5];

        // Crea un radio button por cada cabina
        for (int i = 0; i < 5; i++) {
            radioCabinas[i] = new JRadioButton(nombresCabinas[i]);
            radioCabinas[i].setOpaque(false); // Hace transparente el fondo
            radioCabinas[i].setForeground(Color.WHITE); // Texto blanco
            radioCabinas[i].setFont(new Font("Arial", Font.PLAIN, 14));

            // Añade al grupo y al panel
            grupoCabinas.add(radioCabinas[i]);
            panelCabinasSelect.add(radioCabinas[i]);
        }

        // Panel para selección de horarios
        JPanel panelHorariosSelect = new JPanel();
        panelHorariosSelect.setLayout(new BoxLayout(panelHorariosSelect, BoxLayout.Y_AXIS));
        panelHorariosSelect.setOpaque(false);

        // Borde con título "Seleccionar Horario"
        panelHorariosSelect.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Horario",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        // Grupo de botones para selección única de horarios
        ButtonGroup grupoHorarios = new ButtonGroup();
        JRadioButton[] radioHorarios = new JRadioButton[9];

        // Crea un radio button por cada horario
        for (int i = 0; i < 9; i++) {
            radioHorarios[i] = new JRadioButton(horarios[i]);
            radioHorarios[i].setOpaque(false);
            radioHorarios[i].setForeground(Color.WHITE);
            radioHorarios[i].setFont(new Font("Arial", Font.PLAIN, 14));

            // Añade al grupo y al panel
            grupoHorarios.add(radioHorarios[i]);
            panelHorariosSelect.add(radioHorarios[i]);
        }

        // Añade ambos paneles de selección al panel principal
        panelSeleccion.add(panelCabinasSelect);
        panelSeleccion.add(panelHorariosSelect);

        // Panel para los botones del diálogo
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        // Botón "RESERVAR"
        BotonRedondeado btnConfirmar = new BotonRedondeado("RESERVAR", 20);
        btnConfirmar.setPreferredSize(new Dimension(130, 40));
        btnConfirmar.setBackground(new Color(0, 150, 136)); // Verde agua

        // Botón "CANCELAR"
        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(96, 125, 139)); // Gris azulado

        // ActionListener para el botón de confirmar reserva
        btnConfirmar.addActionListener(e -> {
            try {
                // Obtiene el ID ingresado (convierte a número)
                int id = Integer.parseInt(txtID.getText().trim());

                // Busca el usuario por ID
                Usuario usuario = Usuario.buscarPorId(id);

                // Valida que el usuario exista y esté activo
                if (usuario == null || !usuario.isActivo()) {
                    mostrarMensajeError(dialogo, "Usuario inválido o inactivo");
                    return;
                }

                // Busca qué cabina está seleccionada
                int cabinaSeleccionada = -1;
                for (int i = 0; i < radioCabinas.length; i++) {
                    if (radioCabinas[i].isSelected()) {
                        cabinaSeleccionada = i;
                        break;
                    }
                }

                // Busca qué horario está seleccionado
                int horarioSeleccionado = -1;
                for (int i = 0; i < radioHorarios.length; i++) {
                    if (radioHorarios[i].isSelected()) {
                        horarioSeleccionado = i;
                        break;
                    }
                }

                // Valida que se hayan seleccionado cabina y horario
                if (cabinaSeleccionada == -1 || horarioSeleccionado == -1) {
                    mostrarMensajeError(dialogo, "Debe seleccionar cabina y horario");
                    return;
                }

                // Valida que el horario no esté ya ocupado
                if (reservas[cabinaSeleccionada][horarioSeleccionado] != 0) {
                    mostrarMensajeError(dialogo, "Horario ya ocupado");
                    return;
                }

                // Realiza la reserva (guarda el ID en el array)
                reservas[cabinaSeleccionada][horarioSeleccionado] = id;

                // Muestra mensaje de éxito
                mostrarMensajeExito(dialogo, "Reserva realizada exitosamente");

                // Cierra el diálogo y la ventana principal
                dialogo.dispose();
                parent.dispose();

                // Vuelve a abrir la interfaz para actualizar los datos
                abrirInterfaz();
            } catch (NumberFormatException ex) {
                // Maneja error si el ID no es un número válido
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        // ActionListener para cerrar el diálogo
        btnCancelar.addActionListener(e -> dialogo.dispose());

        // Añade botones al panel
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        // Añade componentes al panel principal
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelID, BorderLayout.CENTER);
        panelPrincipal.add(panelSeleccion, BorderLayout.SOUTH);

        // Añade paneles al fondo del diálogo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        // Hace visible el diálogo
        dialogo.setVisible(true);
    }

    private void mostrarDialogoCancelar(JFrame parent) {
        // Crea diálogo modal para cancelar reservas
        JDialog dialogo = new JDialog(parent, "Cancelar Reserva", true);
        dialogo.setSize(450, 300);
        dialogo.setLocationRelativeTo(parent);

        // Panel con fondo de imagen
        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal del diálogo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);

        // Añade márgenes
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        // Título del diálogo (en rojo)
        JLabel titulo = new JLabel("CANCELAR RESERVA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(244, 67, 54));

        // Subtítulo del diálogo
        JLabel subtitulo = new JLabel("Ingrese su ID para cancelar la reserva", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitulo.setForeground(new Color(200, 200, 200));

        // Añade márgenes al subtítulo
        subtitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Panel para el título
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // Panel central con el campo de texto
        JPanel panelCentral = new JPanel(new BorderLayout(0, 15));
        panelCentral.setOpaque(false);

        // Label para el campo ID
        JLabel lblID = new JLabel("ID de Usuario:");
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        lblID.setForeground(Color.WHITE);

        // Campo de texto para ingresar ID
        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 16));
        txtID.setPreferredSize(new Dimension(0, 40));

        // Establece fondo blanco semitransparente
        txtID.setBackground(new Color(255, 255, 255, 240));

        // Texto en color oscuro
        txtID.setForeground(new Color(33, 33, 33));

        // Borde rojo semitransparente con márgenes
        txtID.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(244, 67, 54, 150), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Texto centrado
        txtID.setHorizontalAlignment(JTextField.CENTER);

        // Añade componentes al panel central
        panelCentral.add(lblID, BorderLayout.NORTH);
        panelCentral.add(txtID, BorderLayout.CENTER);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setOpaque(false);

        // Botón "CANCELAR RESERVA"
        BotonRedondeado btnConfirmar = new BotonRedondeado("CANCELAR RESERVA", 25);
        btnConfirmar.setPreferredSize(new Dimension(200, 45));
        btnConfirmar.setBackground(new Color(244, 67, 54)); // Rojo

        // Botón "CERRAR"
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(100, 45));
        btnCerrar.setBackground(new Color(96, 125, 139)); // Gris azulado

        // ActionListener para el botón de confirmar cancelación
        btnConfirmar.addActionListener(e -> {
            try {
                // Obtiene el ID ingresado
                int id = Integer.parseInt(txtID.getText().trim());

                // Busca el usuario por ID
                Usuario usuario = Usuario.buscarPorId(id);

                if (usuario != null) {
                    boolean encontrado = false;

                    // Busca en todas las cabinas y horarios
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 9; j++) {
                            // Si encuentra una reserva con este ID
                            if (reservas[i][j] == id) {
                                // Libera la reserva (pone 0)
                                reservas[i][j] = 0;
                                encontrado = true;
                                break;
                            }
                        }
                        if (encontrado) {
                            break;
                        }
                    }

                    if (encontrado) {
                        // Si encontró y canceló la reserva
                        dialogo.dispose();
                        mostrarMensajeExito(parent, "Reserva cancelada exitosamente");
                        parent.dispose();
                        abrirInterfaz(); // Refresca la interfaz
                    } else {
                        // Si no encontró reservas
                        mostrarMensajeError(dialogo, "No tienes reservas activas");
                    }
                } else {
                    // Si el usuario no existe
                    mostrarMensajeError(dialogo, "Usuario no encontrado");
                }
            } catch (NumberFormatException ex) {
                // Si el ID no es un número válido
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        // ActionListener para cerrar el diálogo
        btnCerrar.addActionListener(e -> dialogo.dispose());

        // Añade botones al panel
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCerrar);

        // Añade componentes al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Añade paneles al fondo del diálogo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        // Hace visible el diálogo
        dialogo.setVisible(true);
    }

    private void mostrarDisponibilidadCompleta(JFrame parent) {
        // Crea diálogo para mostrar disponibilidad completa
        JDialog dialogo = new JDialog(parent, "Disponibilidad de Cabinas", true);
        dialogo.setSize(700, 500);
        dialogo.setLocationRelativeTo(parent);

        // Panel con fondo de imagen
        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal del diálogo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);

        // Añade márgenes
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título del diálogo
        JLabel titulo = new JLabel("DISPONIBILIDAD DE CABINAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        // Añade margen inferior
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel para la tabla de disponibilidad
        JPanel panelTabla = new JPanel();
        panelTabla.setOpaque(false);
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));

        // Panel para el encabezado de la tabla
        JPanel panelEncabezado = new JPanel(new GridLayout(1, 10, 2, 2));
        panelEncabezado.setOpaque(false);

        // Label para la esquina superior izquierda de la tabla
        JLabel lblVacio = new JLabel("Cabina\\Hora", SwingConstants.CENTER);
        lblVacio.setFont(new Font("Arial", Font.BOLD, 12));
        lblVacio.setForeground(Color.WHITE);
        lblVacio.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        lblVacio.setOpaque(true);
        lblVacio.setBackground(new Color(0, 0, 0, 150));
        panelEncabezado.add(lblVacio);

        // Añade los horarios como encabezados de columna
        for (String horario : horarios) {
            JLabel lblHorario = new JLabel(horario, SwingConstants.CENTER);
            lblHorario.setFont(new Font("Arial", Font.BOLD, 10));
            lblHorario.setForeground(Color.WHITE);
            lblHorario.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            lblHorario.setOpaque(true);
            lblHorario.setBackground(new Color(33, 150, 243, 150));
            panelEncabezado.add(lblHorario);
        }

        // Añade el encabezado a la tabla
        panelTabla.add(panelEncabezado);

        // Crea una fila por cada cabina
        for (int i = 0; i < 5; i++) {
            JPanel filaCabina = new JPanel(new GridLayout(1, 10, 2, 2));
            filaCabina.setOpaque(false);

            // Celda con el nombre de la cabina
            JLabel lblCabina = new JLabel(nombresCabinas[i], SwingConstants.CENTER);
            lblCabina.setFont(new Font("Arial", Font.BOLD, 12));
            lblCabina.setForeground(Color.WHITE);
            lblCabina.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            lblCabina.setOpaque(true);
            lblCabina.setBackground(new Color(96, 125, 139, 150));
            filaCabina.add(lblCabina);

            // Crea una celda por cada horario
            for (int j = 0; j < 9; j++) {
                JLabel lblEstado = new JLabel();
                lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
                lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
                lblEstado.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                lblEstado.setOpaque(true);

                // Si está libre (valor 0)
                if (reservas[i][j] == 0) {
                    lblEstado.setText("L"); // Libre
                    lblEstado.setBackground(new Color(76, 175, 80, 150)); // Verde
                    lblEstado.setForeground(Color.WHITE);
                } else {
                    lblEstado.setText("O"); // Ocupado
                    lblEstado.setBackground(new Color(244, 67, 54, 150)); // Rojo
                    lblEstado.setForeground(Color.WHITE);
                }

                filaCabina.add(lblEstado);
            }

            // Añade la fila a la tabla
            panelTabla.add(filaCabina);
        }

        // Panel para la leyenda (explicación de colores)
        JPanel panelLeyenda = new JPanel(new FlowLayout());
        panelLeyenda.setOpaque(false);
        panelLeyenda.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Label para "L = Libre"
        JLabel lblLeyendaLibre = new JLabel("L = Libre");
        lblLeyendaLibre.setForeground(new Color(76, 175, 80));
        lblLeyendaLibre.setFont(new Font("Arial", Font.BOLD, 14));

        // Label para "O = Ocupado"
        JLabel lblLeyendaOcupado = new JLabel("O = Ocupado");
        lblLeyendaOcupado.setForeground(new Color(244, 67, 54));
        lblLeyendaOcupado.setFont(new Font("Arial", Font.BOLD, 14));

        // Añade labels y espacio entre ellos
        panelLeyenda.add(lblLeyendaLibre);
        panelLeyenda.add(Box.createHorizontalStrut(30));
        panelLeyenda.add(lblLeyendaOcupado);

        // Botón para cerrar el diálogo
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(120, 40));
        btnCerrar.setBackground(new Color(96, 125, 139));
        btnCerrar.addActionListener(e -> dialogo.dispose());

        // Panel para el botón
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnCerrar);

        // ScrollPane para la tabla (por si no cabe todo)
        JScrollPane scrollPane = new JScrollPane(panelTabla);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        // Añade componentes al panel principal
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelLeyenda, BorderLayout.SOUTH);

        // Añade paneles al fondo del diálogo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBoton, BorderLayout.SOUTH);

        // Hace visible el diálogo
        dialogo.setVisible(true);
    }

    private void mostrarDetallesCabina(int cabinaIndex, JFrame parent) {
        // Crea un StringBuilder para construir el texto de detalles
        StringBuilder detalles = new StringBuilder();

        // Añade el nombre de la cabina
        detalles.append("=== ").append(nombresCabinas[cabinaIndex]).append(" ===\n\n");

        // Cuenta las reservas activas para esta cabina
        int reservasActivas = 0;
        for (int j = 0; j < 9; j++) {
            if (reservas[cabinaIndex][j] != 0) {
                reservasActivas++;
            }
        }

        // Añade información de ocupación
        detalles.append("OCUPACIÓN: ").append(reservasActivas).append("/9 horarios\n\n");
        detalles.append("DETALLE POR HORARIO:\n");

        // Itera por cada horario
        for (int j = 0; j < 9; j++) {
            detalles.append("🕐 ").append(horarios[j]).append(": ");

            // Si está libre
            if (reservas[cabinaIndex][j] == 0) {
                detalles.append("LIBRE ✅\n");
            } else {
                // Si está ocupado, muestra ID y nombre del usuario si está disponible
                detalles.append("OCUPADO por ID ").append(reservas[cabinaIndex][j]);
                Usuario u = Usuario.buscarPorId(reservas[cabinaIndex][j]);
                if (u != null) {
                    detalles.append(" (").append(u.getNombre()).append(")");
                }
                detalles.append(" ❌\n");
            }
        }

        // Crea diálogo para mostrar los detalles
        JDialog dialogo = new JDialog(parent, "Detalles - " + nombresCabinas[cabinaIndex], true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(parent);

        // Panel con fondo de imagen
        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Area de texto para mostrar los detalles
        JTextArea textArea = new JTextArea(detalles.toString());
        textArea.setEditable(false); // No editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fuente monoespaciada
        textArea.setBackground(new Color(0, 0, 0, 180)); // Fondo negro semitransparente
        textArea.setForeground(Color.WHITE); // Texto blanco

        // Añade márgenes internos
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ScrollPane por si el texto es muy largo
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Borde blanco semitransparente
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100)));

        // Botón para cerrar el diálogo
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 20);
        btnCerrar.setPreferredSize(new Dimension(120, 35));
        btnCerrar.setBackground(new Color(96, 125, 139));
        btnCerrar.addActionListener(e -> dialogo.dispose());

        // Panel para el botón
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBoton.add(btnCerrar);

        // Añade componentes al fondo del diálogo
        fondoDialogo.add(scrollPane, BorderLayout.CENTER);
        fondoDialogo.add(panelBoton, BorderLayout.SOUTH);

        // Hace visible el diálogo
        dialogo.setVisible(true);
    }

    private void mostrarMensajeExito(Component parent, String mensaje) {
        // Crea diálogo de éxito sin decoraciones (sin bordes de ventana)
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Operación Exitosa", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true);

        // Panel personalizado con fondo degradado
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Suaviza los bordes
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Crea degradado verde
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(76, 175, 80, 250),
                        0, getHeight(), new Color(56, 142, 60, 250)
                );
                g2d.setPaint(gradient);

                // Dibuja rectángulo redondeado con el degradado
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Dibuja borde blanco semitransparente
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
            }
        };

        // Configura layout y márgenes
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Icono de éxito (checkmark)
        JLabel iconoExito = new JLabel("✅", SwingConstants.CENTER);
        iconoExito.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoExito.setPreferredSize(new Dimension(0, 60));

        // Label para el mensaje (centrado con HTML)
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        // Botón "PERFECTO" con estilo
        BotonRedondeado btnOK = new BotonRedondeado("PERFECTO", 20);
        btnOK.setPreferredSize(new Dimension(120, 35));
        btnOK.setBackground(new Color(255, 255, 255, 200)); // Blanco semitransparente
        btnOK.setForeground(new Color(76, 175, 80)); // Texto verde
        btnOK.setFont(new Font("Arial", Font.BOLD, 14));
        btnOK.addActionListener(e -> dialogo.dispose());

        // Panel para el botón
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnOK);

        // Añade componentes al panel principal
        panelPrincipal.add(iconoExito, BorderLayout.NORTH);
        panelPrincipal.add(lblMensaje, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // Establece el panel como contenido del diálogo
        dialogo.setContentPane(panelPrincipal);

        // Temporizador para cerrar automáticamente después de 3 segundos
        Timer timer = new Timer(3000, e -> dialogo.dispose());
        timer.setRepeats(false);
        timer.start();

        // Muestra el diálogo
        dialogo.setVisible(true);
    }

    private void mostrarMensajeError(Component parent, String mensaje) {
        // Crea diálogo de error sin decoraciones
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Error", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true);

        // Panel personalizado con fondo degradado rojo
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

                // Dibuja rectángulo redondeado
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Dibuja borde blanco
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
            }
        };

        // Configura layout y márgenes
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Icono de error (X roja)
        JLabel iconoError = new JLabel("❌", SwingConstants.CENTER);
        iconoError.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoError.setPreferredSize(new Dimension(0, 60));

        // Label para el mensaje de error (centrado con HTML)
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        // Botón "ENTENDIDO" con estilo
        BotonRedondeado btnOK = new BotonRedondeado("ENTENDIDO", 20);
        btnOK.setPreferredSize(new Dimension(130, 35));
        btnOK.setBackground(new Color(255, 255, 255, 200)); // Blanco semitransparente
        btnOK.setForeground(new Color(244, 67, 54)); // Texto rojo
        btnOK.setFont(new Font("Arial", Font.BOLD, 14));
        btnOK.addActionListener(e -> dialogo.dispose());

        // Panel para el botón
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnOK);

        // Añade componentes al panel principal
        panelPrincipal.add(iconoError, BorderLayout.NORTH);
        panelPrincipal.add(lblMensaje, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // Establece el panel como contenido del diálogo
        dialogo.setContentPane(panelPrincipal);

        // Efecto de vibración para llamar la atención
        Timer vibrar = new Timer(50, null);
        final int[] contador = {0};
        final Point posicionOriginal = dialogo.getLocation();

        vibrar.addActionListener(e -> {
            if (contador[0] < 6) {
                // Alterna entre mover a la derecha e izquierda
                int offset = (contador[0] % 2 == 0) ? 5 : -5;
                dialogo.setLocation(posicionOriginal.x + offset, posicionOriginal.y);
                contador[0]++;
            } else {
                // Vuelve a la posición original
                dialogo.setLocation(posicionOriginal);
                vibrar.stop();
            }
        });

        vibrar.start();

        // Muestra el diálogo
        dialogo.setVisible(true);
    }
}
