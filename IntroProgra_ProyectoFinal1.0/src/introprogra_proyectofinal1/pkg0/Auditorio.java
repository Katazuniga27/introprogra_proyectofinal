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

// Método que abre la interfaz gráfica principal del auditorio
    public void abrirInterfaz() {
        // Crea una nueva ventana (JFrame) con el título especificado
        JFrame frame = new JFrame("Auditorio Fitness - Smart Fit");
        // Establece el tamaño de la ventana a 1000 píxeles de ancho y 700 de alto
        frame.setSize(1000, 700);
        // Define que al cerrar la ventana solo se cierre esta ventana, no toda la aplicación
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Centra la ventana en la pantalla del usuario
        frame.setLocationRelativeTo(null);

        // Crea un panel personalizado que usará una imagen de fondo
        FondoPanel fondo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        // Establece el layout del panel de fondo como BorderLayout, que permite organizar componentes en norte, sur, este, oeste y centro
        fondo.setLayout(new BorderLayout());
        // Asigna el panel de fondo como el contenido principal de la ventana
        frame.setContentPane(fondo);

        // === PANEL SUPERIOR - TÍTULO ===
        // Crea un nuevo panel para el título, usando BorderLayout para organizar el título y subtítulo
        JPanel panelTitulo = new JPanel(new BorderLayout());
        // Hace el panel transparente para que se vea la imagen de fondo
        panelTitulo.setOpaque(false);
        // Añade un borde vacío de 20 píxeles arriba y abajo, y 30 a los lados, para separar el título de los bordes de la ventana
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Crea una etiqueta (JLabel) para el título principal, alineada al centro
        JLabel titulo = new JLabel("AUDITORIO FITNESS", SwingConstants.CENTER);
        // Establece la fuente del texto a Arial, negrita, tamaño 36
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        // Cambia el color del texto del título a blanco
        titulo.setForeground(Color.WHITE);

        // Crea una etiqueta para el subtítulo, también centrada
        JLabel subtitulo = new JLabel("Charlas y capacitaciones especializadas", SwingConstants.CENTER);
        // Usa fuente Arial, cursiva, tamaño 18 para el subtítulo
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        // Cambia el color del subtítulo a un gris claro
        subtitulo.setForeground(new Color(200, 200, 200));

        // Añade el título al centro del panel de título
        panelTitulo.add(titulo, BorderLayout.CENTER);
        // Añade el subtítulo en la parte inferior (sur) del panel de título
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // === PANEL CENTRAL - CHARLAS DISPONIBLES ===
        // Crea un panel central para contener las tarjetas de charlas, usando BorderLayout
        JPanel panelCentral = new JPanel(new BorderLayout());
        // Hace el panel central transparente
        panelCentral.setOpaque(false);
        // Añade un borde vacío de 30 píxeles a los lados y 20 abajo
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        // Crea un panel para las tarjetas de charlas, usando GridLayout de 1 fila y 2 columnas, con separación de 20 píxeles
        JPanel panelCharlas = new JPanel(new GridLayout(1, 2, 20, 20));
        // Hace el panel de tarjetas transparente
        panelCharlas.setOpaque(false);
        // Añade un borde vacío de 20 píxeles arriba y abajo, y 50 a los lados
        panelCharlas.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Recorre el arreglo de horarios para crear una tarjeta por cada charla disponible
        for (int i = 0; i < horarios.length; i++) {
            // Llama al método que crea una tarjeta visual para la charla en la posición i
            JPanel tarjetaCharla = crearTarjetaCharla(i, frame);
            // Añade la tarjeta creada al panel de tarjetas
            panelCharlas.add(tarjetaCharla);
        }

        // Añade el panel de tarjetas al centro del panel central
        panelCentral.add(panelCharlas, BorderLayout.CENTER);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        // Crea un panel para los botones de acción, usando FlowLayout centrado y separación de 20 píxeles entre botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        // Hace el panel de botones transparente
        panelBotones.setOpaque(false);

        // Crea un botón personalizado para inscribirse a una charla, con esquinas redondeadas de radio 25
        BotonRedondeado btnInscribir = new BotonRedondeado("INSCRIBIRSE A CHARLA", 25);
        // Establece el tamaño preferido del botón a 220x50 píxeles
        btnInscribir.setPreferredSize(new Dimension(220, 50));
        // Cambia el color de fondo del botón a verde
        btnInscribir.setBackground(new Color(76, 175, 80));
        // Añade un ActionListener que muestra el diálogo de inscripción cuando se hace clic
        btnInscribir.addActionListener(e -> mostrarDialogoInscripcion(frame));

        // Crea un botón para modificar una charla, con esquinas redondeadas
        BotonRedondeado btnModificar = new BotonRedondeado("MODIFICAR CHARLA", 25);
        btnModificar.setPreferredSize(new Dimension(200, 50));
        // Color de fondo naranja
        btnModificar.setBackground(new Color(255, 152, 0));
        // Acción: muestra el diálogo de modificación al hacer clic
        btnModificar.addActionListener(e -> mostrarDialogoModificar(frame));

        // Crea un botón para ver los inscritos, con esquinas redondeadas
        BotonRedondeado btnVer = new BotonRedondeado("VER INSCRITOS", 25);
        btnVer.setPreferredSize(new Dimension(180, 50));
        // Color de fondo azul
        btnVer.setBackground(new Color(33, 150, 243));
        // Acción: muestra la lista de inscritos al hacer clic
        btnVer.addActionListener(e -> mostrarInscritos(frame));

        // Crea un botón para cerrar la ventana, con esquinas redondeadas
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 50));
        // Color de fondo gris azulado
        btnCerrar.setBackground(new Color(96, 125, 139));
        // Acción: cierra la ventana al hacer clic
        btnCerrar.addActionListener(e -> frame.dispose());

        // Añade todos los botones al panel de botones
        panelBotones.add(btnInscribir);
        panelBotones.add(btnModificar);
        panelBotones.add(btnVer);
        panelBotones.add(btnCerrar);

        // === ENSAMBLAR VENTANA ===
        // Añade el panel del título en la parte superior (norte) del panel de fondo
        fondo.add(panelTitulo, BorderLayout.NORTH);
        // Añade el panel central (tarjetas) en el centro del panel de fondo
        fondo.add(panelCentral, BorderLayout.CENTER);
        // Añade el panel de botones en la parte inferior (sur) del panel de fondo
        fondo.add(panelBotones, BorderLayout.SOUTH);

        // Hace visible la ventana principal para el usuario
        frame.setVisible(true);
    }

// Método que crea una tarjeta visual para una charla específica
    private JPanel crearTarjetaCharla(int index, JFrame parent) {
        // Crea un nuevo panel para la tarjeta, usando BorderLayout para organizar los elementos
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());
        // Establece el color de fondo de la tarjeta a negro semi-transparente (RGBA)
        tarjeta.setBackground(new Color(0, 0, 0, 120));
        // Añade un borde compuesto: línea blanca semi-transparente y margen interno de 20 píxeles
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Crea un panel para mostrar el tema de la charla, usando BorderLayout
        JPanel panelTema = new JPanel(new BorderLayout());
        // Hace el panel del tema transparente
        panelTema.setOpaque(false);

        // Crea una etiqueta con el nombre del tema de la charla, centrada
        JLabel lblTema = new JLabel(temas[index % temas.length], SwingConstants.CENTER);
        // Usa fuente Arial, negrita, tamaño 24 para el tema
        lblTema.setFont(new Font("Arial", Font.BOLD, 24));
        // Color de texto blanco
        lblTema.setForeground(Color.WHITE);

        // Define un icono visual según el tema de la charla
        String icono = "🎯"; // Icono por defecto
        switch (temas[index % temas.length].toLowerCase()) {
            case "salud" ->
                icono = "🏥";
            case "rutinas especiales" ->
                icono = "💪";
            case "capacitaciones" ->
                icono = "🎓";
        }

        // Crea una etiqueta para mostrar el icono, centrada y con fuente grande
        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 40));

        // Añade el icono en la parte superior del panel del tema
        panelTema.add(lblIcono, BorderLayout.NORTH);
        // Añade el nombre del tema en el centro del panel del tema
        panelTema.add(lblTema, BorderLayout.CENTER);

        // Crea una etiqueta con el horario de la charla, centrada
        JLabel lblHorario = new JLabel(horarios[index], SwingConstants.CENTER);
        // Usa fuente Arial, negrita, tamaño 20 para el horario
        lblHorario.setFont(new Font("Arial", Font.BOLD, 20));
        // Color de texto amarillo dorado
        lblHorario.setForeground(new Color(255, 193, 7));

        // Crea un panel para mostrar los cupos disponibles, usando BorderLayout
        JPanel panelCupos = new JPanel(new BorderLayout());
        // Hace el panel de cupos transparente
        panelCupos.setOpaque(false);

        // Calcula la cantidad de cupos disponibles para esta charla
        int cuposDisponibles = contarCupos(index);
        // Crea una etiqueta que muestra los cupos disponibles sobre el total
        JLabel lblCupos = new JLabel(cuposDisponibles + "/30 cupos", SwingConstants.CENTER);
        // Usa fuente Arial, tamaño 16 para la etiqueta de cupos
        lblCupos.setFont(new Font("Arial", Font.PLAIN, 16));
        // Color de texto gris claro
        lblCupos.setForeground(new Color(200, 200, 200));

        // Crea una barra de progreso para mostrar visualmente los cupos ocupados
        JProgressBar progreso = new JProgressBar(0, 30);
        // Establece el valor de la barra como la cantidad de inscritos (30 - cupos disponibles)
        progreso.setValue(30 - cuposDisponibles);
        // No muestra texto encima de la barra
        progreso.setStringPainted(false);
        // Establece la altura de la barra a 8 píxeles
        progreso.setPreferredSize(new Dimension(0, 8));
        // Color de fondo de la barra: blanco semi-transparente
        progreso.setBackground(new Color(255, 255, 255, 50));

        // Cambia el color de la barra según la cantidad de cupos restantes
        if (cuposDisponibles > 15) {
            progreso.setForeground(new Color(76, 175, 80)); // Verde si hay muchos cupos
        } else if (cuposDisponibles > 5) {
            progreso.setForeground(new Color(255, 193, 7)); // Amarillo si quedan pocos
        } else {
            progreso.setForeground(new Color(244, 67, 54)); // Rojo si quedan muy pocos
        }

        // Añade la etiqueta de cupos en la parte superior del panel de cupos
        panelCupos.add(lblCupos, BorderLayout.NORTH);
        // Añade la barra de progreso en la parte inferior del panel de cupos
        panelCupos.add(progreso, BorderLayout.SOUTH);

        // Añade el panel del tema en la parte superior de la tarjeta
        tarjeta.add(panelTema, BorderLayout.NORTH);
        // Añade el horario en el centro de la tarjeta
        tarjeta.add(lblHorario, BorderLayout.CENTER);
        // Añade el panel de cupos en la parte inferior de la tarjeta
        tarjeta.add(panelCupos, BorderLayout.SOUTH);

        // Añade un MouseListener para dar efecto visual al pasar el mouse y mostrar detalles al hacer clic
        tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambia el fondo a blanco semi-transparente cuando el mouse está encima
                tarjeta.setBackground(new Color(255, 255, 255, 30));
                // Cambia el cursor a mano para indicar que es interactivo
                tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaura el fondo original cuando el mouse sale
                tarjeta.setBackground(new Color(0, 0, 0, 120));
                // Restaura el cursor por defecto
                tarjeta.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Al hacer clic, muestra los detalles de la charla en un cuadro de diálogo
                mostrarDetallesCharla(index, parent);
            }
        });

        // Devuelve el panel de la tarjeta ya configurado
        return tarjeta;
    }

// Muestra el diálogo para inscribirse a una charla
    private void mostrarDialogoInscripcion(JFrame parent) {
        // Crea un diálogo modal (bloquea la ventana principal hasta cerrarse)
        JDialog dialogo = new JDialog(parent, "Inscribirse a Charla", true);
        // Define el tamaño del diálogo
        dialogo.setSize(500, 450);
        // Centra el diálogo respecto a la ventana principal
        dialogo.setLocationRelativeTo(parent);

        // Panel de fondo con imagen para el diálogo
        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal del diálogo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        // Hace al panel invisible
        panelPrincipal.setOpaque(false);
        // Tamaño del panel
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Etiqueta de título del diálogo
        JLabel titulo = new JLabel("INSCRIPCIÓN A CHARLA", SwingConstants.CENTER);
        // Fuente
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        // Color 
        titulo.setForeground(Color.WHITE);
        // Tamaño
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel para el campo de ID de usuario
        JPanel panelID = new JPanel(new BorderLayout(10, 10));
        // Hace el panel invisible
        panelID.setOpaque(false);

        // Etiqueta para el campo de ID
        JLabel lblID = new JLabel("ID de Usuario:");
        // Fuente del Label
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        // Fondo del Label
        lblID.setForeground(Color.WHITE);

        // Campo de texto para ingresar el ID
        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        txtID.setPreferredSize(new Dimension(0, 35));
        txtID.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Añade la etiqueta y el campo de texto al panel de ID
        panelID.add(lblID, BorderLayout.NORTH);
        panelID.add(txtID, BorderLayout.CENTER);

        // Panel para seleccionar la charla
        JPanel panelCharlas = new JPanel(new GridLayout(2, 1, 10, 10));
        panelCharlas.setOpaque(false);
        panelCharlas.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Charla",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        // Grupo de botones para seleccionar solo una charla
        ButtonGroup grupoCharlas = new ButtonGroup();
        JRadioButton[] radioButtons = new JRadioButton[horarios.length];

        // Crea un radio button para cada charla disponible
        for (int i = 0; i < horarios.length; i++) {
            int cupos = contarCupos(i);
            radioButtons[i] = new JRadioButton(temas[i] + " - " + horarios[i] + " (" + cupos + "/30)");
            radioButtons[i].setOpaque(false);
            radioButtons[i].setForeground(Color.WHITE);
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            radioButtons[i].setEnabled(cupos > 0); // Deshabilita si no hay cupos

            grupoCharlas.add(radioButtons[i]);
            panelCharlas.add(radioButtons[i]);
        }

        // Panel para los botones de acción del diálogo
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        // Botón para confirmar la inscripción
        BotonRedondeado btnConfirmar = new BotonRedondeado("INSCRIBIRSE", 20);
        btnConfirmar.setPreferredSize(new Dimension(130, 40));
        btnConfirmar.setBackground(new Color(76, 175, 80));

        // Botón para cancelar y cerrar el diálogo
        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(96, 125, 139));

        // Acción al hacer clic en "INSCRIBIRSE"
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
                abrirInterfaz(); // Refrescar la interfaz
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "ID debe ser un número válido");
            }
        });

        // Acción al hacer clic en "CANCELAR"
        btnCancelar.addActionListener(e -> dialogo.dispose());

        // Añade los botones al panel de botones
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        // Añade los componentes al panel principal del diálogo
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelID, BorderLayout.CENTER);
        panelPrincipal.add(panelCharlas, BorderLayout.SOUTH);

        // Añade el panel principal y el de botones al fondo del diálogo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        // Muestra el diálogo
        dialogo.setVisible(true);
    }

// Muestra el diálogo para modificar el nombre de una charla
    // Muestra el diálogo decorado para modificar el nombre de una charla
private void mostrarDialogoModificar(JFrame parent) {
    // Crea un diálogo modal (bloquea la ventana principal hasta cerrarse)
    JDialog dialogo = new JDialog(parent, "Modificar Charla", true);
    // Define el tamaño del diálogo
    dialogo.setSize(550, 500);
    // Centra el diálogo respecto a la ventana principal
    dialogo.setLocationRelativeTo(parent);

    // Panel de fondo con imagen para el diálogo
    FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
    fondoDialogo.setLayout(new BorderLayout());
    dialogo.setContentPane(fondoDialogo);

    // Panel principal del diálogo
    JPanel panelPrincipal = new JPanel(new BorderLayout());
    // Hace al panel invisible para mostrar el fondo
    panelPrincipal.setOpaque(false);
    // Añade margen interno
    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // === TÍTULO DEL DIÁLOGO ===
    JLabel titulo = new JLabel("MODIFICAR CHARLA", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 24));
    titulo.setForeground(Color.WHITE);
    titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

    // === PANEL DE SELECCIÓN DE CHARLAS ===
    JPanel panelSeleccion = new JPanel(new BorderLayout());
    panelSeleccion.setOpaque(false);
    
    // Etiqueta instructiva
    JLabel lblInstrucciones = new JLabel("Seleccione la charla que desea modificar:", SwingConstants.CENTER);
    lblInstrucciones.setFont(new Font("Arial", Font.BOLD, 16));
    lblInstrucciones.setForeground(Color.WHITE);
    lblInstrucciones.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

    // Panel para las tarjetas de charlas disponibles
    JPanel panelCharlas = new JPanel(new GridLayout(2, 1, 15, 15));
    panelCharlas.setOpaque(false);
    panelCharlas.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    // Grupo de botones para seleccionar solo una charla
    ButtonGroup grupoCharlas = new ButtonGroup();
    JRadioButton[] radioButtons = new JRadioButton[horarios.length];

    // Crea una tarjeta seleccionable para cada charla
    for (int i = 0; i < horarios.length; i++) {
        // Panel contenedor para cada opción de charla
        JPanel tarjetaOpcion = new JPanel();
        tarjetaOpcion.setLayout(new BorderLayout());
        tarjetaOpcion.setBackground(new Color(0, 0, 0, 120));
        tarjetaOpcion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // Radio button para seleccionar la charla
        radioButtons[i] = new JRadioButton();
        radioButtons[i].setOpaque(false);
        radioButtons[i].setForeground(Color.WHITE);

        // Panel de información de la charla
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setOpaque(false);

        // Nombre de la charla
        JLabel lblNombre = new JLabel(temas[i % temas.length]);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombre.setForeground(Color.WHITE);

        // Información adicional (horario y cupos)
        int cupos = contarCupos(i);
        JLabel lblDetalles = new JLabel(horarios[i] + " • " + cupos + "/30 cupos disponibles");
        lblDetalles.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDetalles.setForeground(new Color(200, 200, 200));

        // Añade la información al panel
        panelInfo.add(lblNombre, BorderLayout.NORTH);
        panelInfo.add(lblDetalles, BorderLayout.SOUTH);

        // Ensambla la tarjeta
        tarjetaOpcion.add(radioButtons[i], BorderLayout.WEST);
        tarjetaOpcion.add(panelInfo, BorderLayout.CENTER);

        // Añade efecto hover a la tarjeta
        final int index = i;
        tarjetaOpcion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tarjetaOpcion.setBackground(new Color(255, 255, 255, 30));
                tarjetaOpcion.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tarjetaOpcion.setBackground(new Color(0, 0, 0, 120));
                tarjetaOpcion.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                radioButtons[index].setSelected(true);
            }
        });

        // Añade el radio button al grupo y la tarjeta al panel
        grupoCharlas.add(radioButtons[i]);
        panelCharlas.add(tarjetaOpcion);
    }

    // === PANEL PARA EL NUEVO NOMBRE ===
    JPanel panelNuevoNombre = new JPanel(new BorderLayout());
    panelNuevoNombre.setOpaque(false);
    panelNuevoNombre.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

    // Etiqueta para el campo del nuevo nombre
    JLabel lblNuevoNombre = new JLabel("Nuevo nombre para la charla:");
    lblNuevoNombre.setFont(new Font("Arial", Font.BOLD, 16));
    lblNuevoNombre.setForeground(Color.WHITE);

    // Campo de texto para el nuevo nombre
    JTextField txtNuevoNombre = new JTextField();
    txtNuevoNombre.setFont(new Font("Arial", Font.PLAIN, 16));
    txtNuevoNombre.setPreferredSize(new Dimension(0, 40));
    txtNuevoNombre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
    ));
    txtNuevoNombre.setBackground(new Color(255, 255, 255, 240));

    // Añade componentes al panel del nuevo nombre
    panelNuevoNombre.add(lblNuevoNombre, BorderLayout.NORTH);
    panelNuevoNombre.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
    panelNuevoNombre.add(txtNuevoNombre, BorderLayout.SOUTH);

    // Ensambla el panel de selección
    panelSeleccion.add(lblInstrucciones, BorderLayout.NORTH);
    panelSeleccion.add(panelCharlas, BorderLayout.CENTER);
    panelSeleccion.add(panelNuevoNombre, BorderLayout.SOUTH);

    // === PANEL DE BOTONES ===
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
    panelBotones.setOpaque(false);

    // Botón para confirmar la modificación
    BotonRedondeado btnModificar = new BotonRedondeado("MODIFICAR", 20);
    btnModificar.setPreferredSize(new Dimension(140, 45));
    btnModificar.setBackground(new Color(255, 152, 0));
    btnModificar.setFont(new Font("Arial", Font.BOLD, 14));

    // Botón para cancelar
    BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
    btnCancelar.setPreferredSize(new Dimension(130, 45));
    btnCancelar.setBackground(new Color(96, 125, 139));
    btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));

    // === ACCIONES DE LOS BOTONES ===
    
    // Acción del botón MODIFICAR
    btnModificar.addActionListener(e -> {
        // Verificar que se haya seleccionado una charla
        int charlaSeleccionada = -1;
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isSelected()) {
                charlaSeleccionada = i;
                break;
            }
        }

        if (charlaSeleccionada == -1) {
            mostrarMensajeError(dialogo, "Debe seleccionar una charla para modificar");
            return;
        }

        // Verificar que se haya ingresado un nuevo nombre
        String nuevoNombre = txtNuevoNombre.getText().trim();
        if (nuevoNombre.isEmpty()) {
            mostrarMensajeError(dialogo, "Debe ingresar un nuevo nombre para la charla");
            return;
        }

        // Realizar la modificación
        String nombreAnterior = temas[charlaSeleccionada % temas.length];
        temas[charlaSeleccionada % temas.length] = nuevoNombre;

        // Mostrar mensaje de éxito
        mostrarMensajeExito(dialogo, "Charla modificada exitosamente:\n\"" + nombreAnterior + "\" → \"" + nuevoNombre + "\"");
        
        // Cerrar diálogo y refrescar interfaz
        dialogo.dispose();
        parent.dispose();
        abrirInterfaz();
    });

    // Acción del botón CANCELAR
    btnCancelar.addActionListener(e -> dialogo.dispose());

    // Añadir botones al panel
    panelBotones.add(btnModificar);
    panelBotones.add(btnCancelar);

    // === ENSAMBLAJE FINAL ===
    panelPrincipal.add(titulo, BorderLayout.NORTH);
    panelPrincipal.add(panelSeleccion, BorderLayout.CENTER);

    fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
    fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

    // Mostrar el diálogo
    dialogo.setVisible(true);
}

// Muestra los detalles de una charla específica en un cuadro de diálogo
    private void mostrarDetallesCharla(int index, JFrame parent) {
        // Crea un StringBuilder para armar el mensaje de detalles
        StringBuilder detalles = new StringBuilder();
        detalles.append("CHARLA: ").append(temas[index % temas.length]).append("\n");
        detalles.append("HORARIO: ").append(horarios[index]).append("\n");
        detalles.append("CUPOS: ").append(contarCupos(index)).append("/30\n\n");
        detalles.append("PARTICIPANTES INSCRITOS:\n");

        boolean hayInscritos = false;
        // Recorre la lista de inscritos para esta charla
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

        // Muestra los detalles en un cuadro de diálogo informativo
        JOptionPane.showMessageDialog(parent, detalles.toString(),
                "Detalles de la Charla", JOptionPane.INFORMATION_MESSAGE);
    }

// Muestra todos los inscritos en todas las charlas en un diálogo con scroll
    private void mostrarInscritos(JFrame parent) {
        // Crea un StringBuilder para armar el mensaje de inscritos
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("=== PARTICIPANTES INSCRITOS ===\n\n");

        // Recorre cada charla
        for (int h = 0; h < horarios.length; h++) {
            mensaje.append("🎓 ").append(temas[h % temas.length]).append(" - ").append(horarios[h]).append(":\n");

            boolean hayInscritos = false;
            // Recorre la lista de inscritos para esta charla
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

        // Crea un diálogo modal para mostrar la lista de inscritos
        JDialog dialogo = new JDialog(parent, "Participantes Inscritos", true);
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(parent);

        // Área de texto para mostrar la información, no editable
        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBackground(new Color(45, 45, 45));
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Scroll para la lista de inscritos
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Añade el scroll al diálogo y lo muestra
        dialogo.add(scrollPane);
        dialogo.setVisible(true);
    }

// Muestra un mensaje de éxito personalizado en un diálogo decorado
    private void mostrarMensajeExito(Component parent, String mensaje) {
        // Crea un diálogo modal sin decoración de ventana
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Operación Exitosa", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true);

        // Panel principal con gradiente de color de fondo
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

        // Icono de éxito grande
        JLabel iconoExito = new JLabel("✅", SwingConstants.CENTER);
        iconoExito.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoExito.setPreferredSize(new Dimension(0, 60));

        // Mensaje de éxito centrado
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        // Botón para cerrar el diálogo
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

        // Cierra el diálogo automáticamente después de 3 segundos
        Timer timer = new Timer(3000, e -> dialogo.dispose());
        timer.setRepeats(false);
        timer.start();

        dialogo.setVisible(true);
    }

// Muestra un mensaje de error personalizado en un diálogo decorado
    private void mostrarMensajeError(Component parent, String mensaje) {
        // Crea un diálogo modal sin decoración de ventana
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Error", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true);

        // Panel principal con gradiente de color de fondo rojo
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(244, 67, 54, 250),
                        0, getHeight(), new Color(211, 47, 47, 250)
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

        // Icono de error grande
        JLabel iconoError = new JLabel("❌", SwingConstants.CENTER);
        iconoError.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoError.setPreferredSize(new Dimension(0, 60));

        // Mensaje de error centrado
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        // Botón para cerrar el diálogo
        BotonRedondeado btnOK = new BotonRedondeado("CERRAR", 20);
        btnOK.setPreferredSize(new Dimension(120, 35));
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

        // Cierra el diálogo automáticamente después de 3 segundos
        Timer timer = new Timer(3000, e -> dialogo.dispose());
        timer.setRepeats(false);
        timer.start();

        dialogo.setVisible(true);
    }
}