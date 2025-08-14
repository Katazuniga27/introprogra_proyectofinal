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

    // Método principal para abrir la ventana con la interfaz gráfica
    public void abrirInterfaz() {
        // === CREAR VENTANA PRINCIPAL ===
        JFrame frame = new JFrame("Sala de Pesas - Smart Fit"); // Creamos la ventana con un título
        frame.setSize(1000, 700); // Tamaño fijo de la ventana
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cuando la cierran, solo se cierra esta ventana
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // === FONDO CON IMAGEN ===
        FondoPanel fondo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg"); // Panel con imagen de fondo
        fondo.setLayout(new BorderLayout()); // Layout para organizar los componentes
        frame.setContentPane(fondo); // Usar el fondo como contenido principal

        // === PANEL SUPERIOR - TÍTULO ===
        JPanel panelTitulo = new JPanel(new BorderLayout()); // Panel para contener título y subtítulo
        panelTitulo.setOpaque(false); // Para que sea transparente y se vea el fondo
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Margen alrededor

        // Etiqueta título principal, centrada
        JLabel titulo = new JLabel("SALA DE PESAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36)); // Fuente grande y en negrita
        titulo.setForeground(Color.WHITE); // Color blanco

        // Etiqueta subtítulo centrada y con letra en cursiva
        JLabel subtitulo = new JLabel("Reserva tu horario de entrenamiento", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(200, 200, 200)); // Gris claro

        panelTitulo.add(titulo, BorderLayout.CENTER); // Añadir título al centro del panel
        panelTitulo.add(subtitulo, BorderLayout.SOUTH); // Añadir subtítulo abajo

        // === PANEL CENTRAL - HORARIOS ===
        JPanel panelCentral = new JPanel(new BorderLayout()); // Panel central con BorderLayout
        panelCentral.setOpaque(false); // Transparente
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30)); // Margen

        // Panel con GridLayout para mostrar las tarjetas de horarios (2 filas x 3 columnas)
        JPanel panelHorarios = new JPanel(new GridLayout(2, 3, 15, 15)); // Espacio entre tarjetas
        panelHorarios.setOpaque(false);
        panelHorarios.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Margen arriba y abajo

        // Ciclo para crear y agregar cada tarjeta al panel
        for (int i = 0; i < horarios.length; i++) {
            JPanel tarjetaHorario = crearTarjetaHorario(i, frame); // Crear tarjeta para cada horario
            panelHorarios.add(tarjetaHorario); // Añadir tarjeta al panel
        }

        panelCentral.add(panelHorarios, BorderLayout.CENTER); // Añadir panel de horarios al centro

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Panel para botones, centrado
        panelBotones.setOpaque(false); // Transparente

        // Botón para reservar horario
        BotonRedondeado btnIngresar = new BotonRedondeado("RESERVAR HORARIO", 25);
        btnIngresar.setPreferredSize(new Dimension(200, 50)); // Tamaño del botón
        btnIngresar.setBackground(new Color(0, 150, 136)); // Color verde agua
        btnIngresar.addActionListener(e -> mostrarDialogoReserva(frame)); // Acción al hacer clic

        // Botón para cancelar reserva
        BotonRedondeado btnSalir = new BotonRedondeado("CANCELAR RESERVA", 25);
        btnSalir.setPreferredSize(new Dimension(200, 50));
        btnSalir.setBackground(new Color(244, 67, 54)); // Rojo
        btnSalir.addActionListener(e -> mostrarDialogoCancelar(frame)); // Acción al hacer clic

        // Botón para ver quién está presente
        BotonRedondeado btnVer = new BotonRedondeado("VER PRESENTES", 25);
        btnVer.setPreferredSize(new Dimension(200, 50));
        btnVer.setBackground(new Color(33, 150, 243)); // Azul
        btnVer.addActionListener(e -> mostrarPresentes(frame)); // Acción al hacer clic

        // Botón para cerrar la ventana
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 50));
        btnCerrar.setBackground(new Color(96, 125, 139)); // Gris azulado
        btnCerrar.addActionListener(e -> frame.dispose()); // Cierra la ventana

        // Agregamos los botones al panel inferior
        panelBotones.add(btnIngresar);
        panelBotones.add(btnSalir);
        panelBotones.add(btnVer);
        panelBotones.add(btnCerrar);

        // === ENSAMBLAR VENTANA ===
        fondo.add(panelTitulo, BorderLayout.NORTH); // Añadimos panel del título arriba
        fondo.add(panelCentral, BorderLayout.CENTER); // Añadimos panel central con horarios en el centro
        fondo.add(panelBotones, BorderLayout.SOUTH); // Añadimos panel botones abajo

        // Hacemos visible la ventana para que el usuario pueda verla y usarla
        frame.setVisible(true);
    }

// Método para crear cada tarjeta de horario individual, con su información y apariencia
    private JPanel crearTarjetaHorario(int index, JFrame parent) {
        JPanel tarjeta = new JPanel(); // Panel para la tarjeta
        tarjeta.setLayout(new BorderLayout()); // Layout para organizar componentes dentro de la tarjeta
        tarjeta.setBackground(new Color(0, 0, 0, 120)); // Fondo negro con transparencia
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1), // Borde blanco translúcido
                BorderFactory.createEmptyBorder(15, 15, 15, 15) // Margen interno dentro del borde
        ));

        // Etiqueta con el horario (ejemplo: "6:00 AM - 7:00 AM")
        JLabel lblHorario = new JLabel(horarios[index], SwingConstants.CENTER);
        lblHorario.setFont(new Font("Arial", Font.BOLD, 20)); // Fuente grande y negrita
        lblHorario.setForeground(Color.WHITE); // Color blanco

        // Etiqueta con el contador de personas (ejemplo: "12/50")
        JLabel lblContador = new JLabel(contadorPorHorario[index] + "/50", SwingConstants.CENTER);
        lblContador.setFont(new Font("Arial", Font.PLAIN, 16)); // Fuente normal
        lblContador.setForeground(new Color(200, 200, 200)); // Gris claro

        // Barra de progreso que muestra visualmente cuántas personas hay reservadas
        JProgressBar progreso = new JProgressBar(0, 50); // Rango de 0 a 50 personas
        progreso.setValue(contadorPorHorario[index]); // Valor actual
        progreso.setStringPainted(false); // No mostrar texto sobre la barra
        progreso.setPreferredSize(new Dimension(0, 8)); // Altura pequeña para que sea tipo barra fina
        progreso.setBackground(new Color(255, 255, 255, 50)); // Fondo translúcido blanco

        // Cambiamos el color de la barra según la cantidad de personas
        if (contadorPorHorario[index] < 25) {
            progreso.setForeground(new Color(76, 175, 80)); // Verde si está poco lleno
        } else if (contadorPorHorario[index] < 40) {
            progreso.setForeground(new Color(255, 193, 7)); // Amarillo si está medio lleno
        } else {
            progreso.setForeground(new Color(244, 67, 54)); // Rojo si está casi lleno
        }

        // Panel para contener el contador y la barra juntos, con un pequeño espacio vertical
        JPanel panelInfo = new JPanel(new BorderLayout(0, 5));
        panelInfo.setOpaque(false); // Transparente
        panelInfo.add(lblContador, BorderLayout.NORTH); // Arriba el texto del contador
        panelInfo.add(progreso, BorderLayout.SOUTH); // Abajo la barra de progreso

        tarjeta.add(lblHorario, BorderLayout.CENTER); // Horario en el centro de la tarjeta
        tarjeta.add(panelInfo, BorderLayout.SOUTH); // Contador y barra abajo

        // Añadimos eventos de mouse para efecto hover y click
        tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tarjeta.setBackground(new Color(255, 255, 255, 30)); // Cambia fondo cuando pasa el mouse por encima
                tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia cursor a mano para indicar que es clickeable
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tarjeta.setBackground(new Color(0, 0, 0, 120)); // Vuelve al fondo original cuando sale el mouse
                tarjeta.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Cursor normal
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDetallesHorario(index, parent); // Abre detalles del horario al hacer click
            }
        });

        return tarjeta; // Retornamos la tarjeta creada
    }

// Método que muestra el diálogo para reservar un horario
    private void mostrarDialogoReserva(JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Reservar Horario", true); // Diálogo modal (bloquea ventana padre)
        dialogo.setSize(500, 400); // Tamaño del diálogo
        dialogo.setLocationRelativeTo(parent); // Centrado sobre la ventana padre

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg"); // Fondo con imagen
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo); // Asignamos fondo al diálogo

        // Panel principal para contenido
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false); // Transparente
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno

        // Título del diálogo centrado
        JLabel titulo = new JLabel("RESERVAR HORARIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente grande y negrita
        titulo.setForeground(Color.WHITE); // Color blanco
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Espacio abajo

        // Panel para el campo donde se ingresa el ID del usuario
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

        panelID.add(lblID, BorderLayout.NORTH); // Texto arriba
        panelID.add(txtID, BorderLayout.CENTER); // Campo de texto debajo

        // Panel para los botones de selección de horarios (radio buttons)
        JPanel panelHorarios = new JPanel(new GridLayout(2, 3, 10, 10));
        panelHorarios.setOpaque(false);
        panelHorarios.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Horario",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        ButtonGroup grupoHorarios = new ButtonGroup(); // Para que solo se pueda seleccionar un horario
        JRadioButton[] radioButtons = new JRadioButton[horarios.length];

        for (int i = 0; i < horarios.length; i++) {
            radioButtons[i] = new JRadioButton(horarios[i] + " (" + contadorPorHorario[i] + "/50)");
            radioButtons[i].setOpaque(false); // Transparente
            radioButtons[i].setForeground(Color.WHITE); // Color blanco
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 12));
            radioButtons[i].setEnabled(contadorPorHorario[i] < 50); // Deshabilitar si ya está lleno

            grupoHorarios.add(radioButtons[i]); // Agregar al grupo
            panelHorarios.add(radioButtons[i]); // Agregar al panel
        }

        // Panel para botones Confirmar y Cancelar
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        BotonRedondeado btnConfirmar = new BotonRedondeado("CONFIRMAR", 20);
        btnConfirmar.setPreferredSize(new Dimension(120, 40));
        btnConfirmar.setBackground(new Color(0, 150, 136)); // Verde agua

        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(96, 125, 139)); // Gris azulado

        // Acción al confirmar la reserva
        btnConfirmar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtID.getText().trim()); // Obtener ID ingresado y convertir a número
                Usuario usuario = Usuario.buscarPorId(id); // Buscar usuario por ID

                if (usuario == null || !usuario.isActivo()) { // Validar si existe y está activo
                    mostrarMensajeError(dialogo, "Usuario inválido o inactivo");
                    return; // Salir si no es válido
                }

                // Buscar cuál horario fue seleccionado
                int horarioSeleccionado = -1;
                for (int i = 0; i < radioButtons.length; i++) {
                    if (radioButtons[i].isSelected()) {
                        horarioSeleccionado = i;
                        break;
                    }
                }

                if (horarioSeleccionado == -1) { // Si no seleccionó ninguno
                    mostrarMensajeError(dialogo, "Debe seleccionar un horario");
                    return;
                }

                // Intentar ingresar al horario, mostrar éxito y refrescar la ventana
                if (ingresarSalaPesas(id, horarioSeleccionado, dialogo)) {
                    mostrarMensajeExito(dialogo, "Reserva realizada exitosamente");
                    dialogo.dispose(); // Cerrar diálogo
                    parent.dispose(); // Cerrar ventana principal
                    abrirInterfaz(); // Abrir ventana principal de nuevo (refrescar)
                }
            } catch (NumberFormatException ex) {
                mostrarMensajeError(dialogo, "ID debe ser un número válido"); // Error si no es número
            }
        });

        // Acción para cancelar el diálogo sin hacer nada
        btnCancelar.addActionListener(e -> dialogo.dispose());

        // Agregar componentes al panel principal
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelID, BorderLayout.CENTER);
        panelPrincipal.add(panelHorarios, BorderLayout.SOUTH);

        // Agregar panel principal y botones al fondo del diálogo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        dialogo.setVisible(true); // Mostrar el diálogo
    }

// Método que muestra el diálogo para cancelar una reserva
    private void mostrarDialogoCancelar(JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Cancelar Reserva", true); // Diálogo modal
        dialogo.setSize(450, 350);
        dialogo.setLocationRelativeTo(parent);

        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal con margen
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        // Panel para el título y subtítulo
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);

        // Título principal del diálogo
        JLabel titulo = new JLabel("CANCELAR RESERVA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(244, 67, 54)); // Color rojo

        // Subtítulo con indicación
        JLabel subtitulo = new JLabel("Ingrese su ID para cancelar la reserva", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitulo.setForeground(new Color(200, 200, 200));
        subtitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Margen inferior

        panelTitulo.add(titulo, BorderLayout.CENTER);
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // Panel para el campo de texto del ID
        JPanel panelCentral = new JPanel(new BorderLayout(0, 15));
        panelCentral.setOpaque(false);

        JLabel lblID = new JLabel("ID de Usuario:");
        lblID.setFont(new Font("Arial", Font.BOLD, 16));
        lblID.setForeground(Color.WHITE);

        JTextField txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 16));
        txtID.setPreferredSize(new Dimension(0, 40));
        txtID.setBackground(new Color(255, 255, 255, 240)); // Fondo blanco semitransparente
        txtID.setForeground(new Color(33, 33, 33)); // Texto negro
        txtID.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(244, 67, 54, 150), 2), // Borde rojo

                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        // Esto centra el texto dentro del campo de texto, para que el usuario vea el número en el centro
        txtID.setHorizontalAlignment(JTextField.CENTER);

// === PANEL DE INFORMACIÓN ADICIONAL ===
// Aquí creamos un panel para mostrar un mensaje que explica qué hará la acción
        JPanel panelInfo = new JPanel();
// Lo hacemos transparente para que se vea el fondo (no tenga color sólido)
        panelInfo.setOpaque(false);
// Usamos un BoxLayout vertical para apilar componentes uno encima del otro
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));

// Creamos una etiqueta para mostrar el mensaje
        JLabel infoLabel = new JLabel("Esta acción cancelará tu reserva actual");
// Elegimos una fuente sencilla, tamaño 12 para que no distraiga mucho
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
// Usamos un color amarillo dorado para que resalte un poco, pero sin ser agresivo
        infoLabel.setForeground(new Color(255, 193, 7));
// Centramos el texto horizontalmente en el panel
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

// Agregamos la etiqueta al panel de información
        panelInfo.add(infoLabel);

// Ahora agregamos los componentes al panel central que usa BorderLayout
        panelCentral.add(lblID, BorderLayout.NORTH);     // Ponemos la etiqueta "ID de Usuario" arriba
        panelCentral.add(txtID, BorderLayout.CENTER);    // Campo de texto en el centro
        panelCentral.add(panelInfo, BorderLayout.SOUTH); // Mensaje informativo abajo

// === PANEL DE BOTONES ===
// Creamos un panel para colocar los botones con separación horizontal y vertical
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
// También transparente para que se vea el fondo
        panelBotones.setOpaque(false);

// Creamos botón para confirmar la cancelación
        BotonRedondeado btnConfirmar = new BotonRedondeado("CANCELAR RESERVA", 25);
// Definimos tamaño fijo para que se vean parejos
        btnConfirmar.setPreferredSize(new Dimension(180, 45));
// Color rojo para indicar acción de alerta o cancelar
        btnConfirmar.setBackground(new Color(244, 67, 54));
// Fuente en negrita para que el texto resalte bien
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));

// Creamos botón para cerrar el diálogo sin hacer cambios
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(100, 45));
// Color gris azulado para un botón más neutro
        btnCerrar.setBackground(new Color(96, 125, 139));
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));

// Aquí definimos qué pasa cuando el usuario presiona el botón de confirmar cancelación
        btnConfirmar.addActionListener(e -> {
            try {
                // Tomamos el texto que ingresó el usuario en el campo de texto y le quitamos espacios al inicio y final
                String texto = txtID.getText().trim();

                // Verificamos que no esté vacío el campo
                if (texto.isEmpty()) {
                    // Si está vacío, mostramos un mensaje de error y salimos del método
                    mostrarMensajeError(dialogo, "Debe ingresar un ID");
                    return;
                }

                // Intentamos convertir el texto a número entero (ID)
                int id = Integer.parseInt(texto);

                // Buscamos el usuario por ese ID usando un método que debe estar definido en tu clase Usuario
                Usuario usuario = Usuario.buscarPorId(id);

                if (usuario != null) { // Si el usuario existe
                    // Intentamos cancelar la reserva para ese usuario en la sala de pesas
                    if (salirSalaPesas(id)) {
                        dialogo.dispose(); // Cerramos este diálogo
                        // Mostramos mensaje de éxito en la ventana principal
                        mostrarMensajeExito(parent, "Reserva cancelada exitosamente");
                        parent.dispose(); // Cerramos ventana principal para refrescar
                        abrirInterfaz();  // Abrimos nuevamente la interfaz para que se actualice
                    } else {
                        // Si no tenía reserva activa, mostramos error
                        mostrarMensajeError(dialogo, "No tienes reservas activas");
                    }
                } else {
                    // Si no existe el usuario, mostramos error
                    mostrarMensajeError(dialogo, "Usuario no encontrado");
                }
            } catch (NumberFormatException ex) {
                // Si no se pudo convertir el texto a número, mostramos error
                mostrarMensajeError(dialogo, "El ID debe ser un número válido");
            }
        });

// Cuando el usuario presiona cerrar, simplemente cerramos el diálogo
        btnCerrar.addActionListener(e -> dialogo.dispose());

// Agregamos los dos botones al panel de botones
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCerrar);

// === ENSAMBLAR EL DIÁLOGO ===
// Agregamos el panel del título arriba
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
// Agregamos el panel central (con campos e info) en el centro
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

// Agregamos los paneles al panel principal del diálogo con fondo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

// Configuramos para que cuando el diálogo se abra, el cursor esté en el campo de texto para facilitar entrada
        dialogo.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                txtID.requestFocus();
            }
        });

// Finalmente, mostramos el diálogo
        dialogo.setVisible(true);
    }

    // Muestra los detalles de un horario específico en un diálogo simple con JOptionPane
    private void mostrarDetallesHorario(int index, JFrame parent) {
        // Usamos StringBuilder para construir un texto largo
        StringBuilder detalles = new StringBuilder();
        detalles.append("HORARIO: ").append(horarios[index]).append("\n"); // Nombre horario
        detalles.append("OCUPACIÓN: ").append(contadorPorHorario[index]).append("/50\n\n"); // Cupo usado

        detalles.append("USUARIOS REGISTRADOS:\n"); // Título de lista de usuarios

        // Recorremos la cantidad de usuarios registrados en ese horario
        for (int i = 0; i < contadorPorHorario[index]; i++) {
            int id = idsPorHorario[index][i]; // ID de usuario
            Usuario u = Usuario.buscarPorId(id); // Buscamos usuario por ID
            detalles.append("• ").append(id); // Agregamos el ID a la lista
            if (u != null) {
                detalles.append(" - ").append(u.getNombre()); // Si existe, mostramos nombre
            }
            detalles.append("\n"); // Nueva línea por cada usuario
        }

        // Mostramos todo el texto en un mensaje informativo estándar
        JOptionPane.showMessageDialog(parent, detalles.toString(),
                "Detalles del Horario", JOptionPane.INFORMATION_MESSAGE);
    }

// Muestra todos los usuarios presentes en cada horario en un diálogo personalizado
    private void mostrarPresentes(JFrame parent) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("=== USUARIOS REGISTRADOS POR HORARIO ===\n\n");

        // Recorremos todos los horarios
        for (int h = 0; h < horarios.length; h++) {
            // Agregamos título de cada horario con icono de reloj y número de personas
            mensaje.append("🕐 ").append(horarios[h]).append(" (").append(contadorPorHorario[h]).append("/50):\n");

            // Si no hay usuarios, decimos que no hay
            if (contadorPorHorario[h] == 0) {
                mensaje.append("   Sin usuarios registrados\n");
            } else {
                // Si hay usuarios, listamos cada uno
                for (int i = 0; i < contadorPorHorario[h]; i++) {
                    int id = idsPorHorario[h][i];
                    Usuario u = Usuario.buscarPorId(id);
                    mensaje.append("   • ").append(id);
                    if (u != null) {
                        mensaje.append(" - ").append(u.getNombre());
                    }
                    mensaje.append("\n");
                }
            }
            mensaje.append("\n"); // Línea en blanco entre horarios
        }

        // Creamos un diálogo personalizado para mostrar el texto con scroll
        JDialog dialogo = new JDialog(parent, "Usuarios Presentes", true);
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(parent);

        JTextArea textArea = new JTextArea(mensaje.toString()); // Área de texto con el mensaje
        textArea.setEditable(false); // No editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fuente monoespaciada para mejor lectura
        textArea.setBackground(new Color(45, 45, 45)); // Fondo oscuro para menos cansancio visual
        textArea.setForeground(Color.WHITE); // Texto blanco
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno

        JScrollPane scrollPane = new JScrollPane(textArea); // Scroll si el texto es muy largo
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Agregamos el scroll pane al diálogo y lo mostramos
        dialogo.add(scrollPane);
        dialogo.setVisible(true);
    }

// Muestra un mensaje bonito y personalizado cuando la operación fue exitosa
    private void mostrarMensajeExito(Component parent, String mensaje) {
        // Creamos un diálogo modal sin bordes
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Operación Exitosa", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true); // Sin barra de título

        // Panel principal con dibujo de fondo degradado verde
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Activamos antialiasing para que se vea mejor
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Creamos un degradado de verde oscuro a verde claro vertical
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(76, 175, 80, 250),
                        0, getHeight(), new Color(56, 142, 60, 250)
                );
                g2d.setPaint(gradient);
                // Rellenamos un rectángulo con bordes redondeados
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Dibujamos un borde blanco translúcido alrededor
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
            }
        };

        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Etiqueta con icono de check para éxito
        JLabel iconoExito = new JLabel("✅", SwingConstants.CENTER);
        iconoExito.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoExito.setPreferredSize(new Dimension(0, 60));

        // Etiqueta con el mensaje que se muestra en el centro
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        // Botón para cerrar el diálogo, estilizado
        BotonRedondeado btnOK = new BotonRedondeado("PERFECTO", 20);
        btnOK.setPreferredSize(new Dimension(120, 35));
        btnOK.setBackground(new Color(255, 255, 255, 200)); // Fondo blanco semitransparente
        btnOK.setForeground(new Color(76, 175, 80));       // Texto verde
        btnOK.setFont(new Font("Arial", Font.BOLD, 14));
        btnOK.addActionListener(e -> dialogo.dispose());  // Cierra diálogo al hacer clic

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnOK);

        panelPrincipal.add(iconoExito, BorderLayout.NORTH);
        panelPrincipal.add(lblMensaje, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setContentPane(panelPrincipal);

        // Configuramos para que el diálogo se cierre automáticamente después de 3 segundos
        Timer timer = new Timer(3000, e -> dialogo.dispose());
        timer.setRepeats(false);
        timer.start();

        dialogo.setVisible(true);
    }

// Muestra un mensaje bonito y personalizado cuando ocurrió un error
    private void mostrarMensajeError(Component parent, String mensaje) {
        // Diálogo modal sin decoraciones
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Error", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setUndecorated(true);

        // Panel con degradado rojo para el fondo
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

        // Icono de error (X roja)
        JLabel iconoError = new JLabel("❌", SwingConstants.CENTER);
        iconoError.setFont(new Font("Arial", Font.PLAIN, 40));
        iconoError.setPreferredSize(new Dimension(0, 60));

        // Mensaje del error centrado
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center'>" + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);

        // Botón para cerrar diálogo
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

        // Efecto de vibración para llamar la atención del usuario
        Timer vibrar = new Timer(50, null);
        final int[] contador = {0};
        final Point posicionOriginal = dialogo.getLocation();

        vibrar.addActionListener(e -> {
            if (contador[0] < 6) {
                // Alternar posición izquierda y derecha para simular vibración
                int offset = (contador[0] % 2 == 0) ? 5 : -5;
                dialogo.setLocation(posicionOriginal.x + offset, posicionOriginal.y);
                contador[0]++;
            } else {
                // Regresar a la posición original y parar la vibración
                dialogo.setLocation(posicionOriginal);
                vibrar.stop();
            }
        });

        vibrar.start();

        dialogo.setVisible(true);
    }

    
    // Método para ingresar a la sala de pesas en un horario específico
// Retorna true si se pudo registrar, false si hay algún problema
    public boolean ingresarSalaPesas(int id, int horarioIndex, Component parent) {
        // Validar si el horario ya está lleno (más de 50 personas)
        if (contadorPorHorario[horarioIndex] >= 50) {
            mostrarMensajeError(parent, "Este horario está lleno");
            return false; // No se puede ingresar
        }

        // Validar si el usuario ya está inscrito en ese horario (no duplicar)
        for (int i = 0; i < contadorPorHorario[horarioIndex]; i++) {
            if (idsPorHorario[horarioIndex][i] == id) {
                mostrarMensajeError(parent, "Ya tienes una reserva en este horario");
                return false; // No se puede duplicar reserva
            }
        }

        // Registrar al usuario en el arreglo para ese horario
        idsPorHorario[horarioIndex][contadorPorHorario[horarioIndex]] = id;
        // Incrementar contador de personas en ese horario
        contadorPorHorario[horarioIndex]++;
        return true; // Reserva exitosa
    }

// Método para cancelar la reserva de un usuario (removerlo de cualquier horario)
    public boolean salirSalaPesas(int id) {
        boolean encontrado = false; // Flag para saber si se encontró el usuario

        // Recorremos todos los horarios disponibles
        for (int h = 0; h < horarios.length; h++) {
            // Recorremos todos los usuarios registrados en ese horario
            for (int i = 0; i < contadorPorHorario[h]; i++) {
                if (idsPorHorario[h][i] == id) { // Si encontramos el ID del usuario
                    // Para eliminarlo, desplazamos todos los IDs posteriores una posición a la izquierda
                    for (int j = i; j < contadorPorHorario[h] - 1; j++) {
                        idsPorHorario[h][j] = idsPorHorario[h][j + 1];
                    }
                    // Disminuimos el contador de personas en ese horario
                    contadorPorHorario[h]--;
                    encontrado = true; // Marcamos que se encontró y eliminó al usuario
                    break; // Salimos del ciclo interno para ese horario
                }
            }
        }
    
        // Retornamos si se encontró y removió al usuario de alguna lista
        return encontrado;
    }    
        
    // Métodos legacy para compatibilidad
    // Método público que inicia la sala de pesas mostrando la interfaz principal
    public void iniciarSalaPesas() {
        abrirInterfaz();  // Llama al método que abre la interfaz gráfica principal
    }

// Método privado para seleccionar un usuario en la sala de pesas mediante su ID
    private Usuario seleccionarUsuarioSalaPesas() {
        // Solicita al usuario ingresar el ID a través de un cuadro de diálogo
        String input = JOptionPane.showInputDialog("Ingrese el ID del usuario:");
        // Si el usuario cancela o no ingresa nada, retorna null
        if (input == null) {
            return null;
        }

        try {
            // Convierte el texto ingresado a un número entero (ID)
            int id = Integer.parseInt(input);
            // Busca y retorna el usuario con ese ID, o null si no existe
            return Usuario.buscarPorId(id);
        } catch (NumberFormatException e) {
            // Si la conversión falla, muestra un mensaje de error y retorna null
            JOptionPane.showMessageDialog(null, "ID inválido.");
            return null;
        }
    }

// Método público para ingresar a la sala de pesas con ID y horario (sobrecarga legacy)
    public boolean ingresarSalaPesas(int id, int horarioIndex) {
        // Método legado que llama a la versión más nueva con un tercer parámetro null
        return ingresarSalaPesas(id, horarioIndex, null);
    }

// Método público para mostrar los usuarios presentes en la sala de pesas (legacy)
    public void mostrarPresentesSalaPesas() {
        // Método legado que abre la interfaz principal actualizada
        abrirInterfaz();
    }

// === CLASES DE COMPONENTES PERSONALIZADOS ===
// Clase que extiende JPanel para mostrar un fondo con imagen oscura
    class FondoPanel extends JPanel {

        private Image imagen;  // Variable que guarda la imagen de fondo

        // Constructor que carga la imagen desde una ruta en los recursos
        public FondoPanel(String rutaImagen) {
            imagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
        }

        // Método que dibuja el componente
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);  // Dibuja el fondo estándar
            if (imagen != null) {     // Si la imagen fue cargada correctamente
                Graphics2D g2 = (Graphics2D) g.create();  // Crea un contexto gráfico 2D
                g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);  // Dibuja la imagen escalada al tamaño del panel
                g2.setColor(new Color(0, 0, 0, 180));  // Crea un color negro semitransparente para oscurecer la imagen
                g2.fillRect(0, 0, getWidth(), getHeight());  // Dibuja un rectángulo semitransparente sobre la imagen para efecto de oscurecimiento
                g2.dispose();  // Libera recursos del contexto gráfico
            }
        }
    }

// Clase que extiende JButton para crear un botón con esquinas redondeadas
    class BotonRedondeado extends JButton {

        private int radius;  // Radio para las esquinas redondeadas

        // Constructor que recibe el texto del botón y el radio de redondeo
        public BotonRedondeado(String text, int radius) {
            super(text);  // Llama al constructor padre con el texto
            this.radius = radius;  // Asigna el radio de las esquinas
            setOpaque(false);  // Hace el botón transparente para personalizar el dibujo
            setContentAreaFilled(false);  // No rellena el área del contenido con color predeterminado
            setBorderPainted(false);  // No dibuja el borde predeterminado
            setFocusPainted(false);  // No dibuja el foco de selección predeterminado
            setForeground(Color.WHITE);  // Establece el color del texto a blanco
            setBackground(new Color(39, 39, 39));  // Color de fondo personalizado oscuro
            setFont(new Font("Arial", Font.BOLD, 18));  // Fuente personalizada para el texto
        }

        // Método que dibuja el botón con esquinas redondeadas y el texto centrado
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();  // Crea un contexto gráfico 2D
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // Activa suavizado para bordes redondeados

            g2.setColor(getBackground());  // Usa el color de fondo configurado
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);  // Dibuja un rectángulo redondeado con el radio indicado

            FontMetrics fm = g2.getFontMetrics();  // Obtiene métricas para centrar el texto
            int x = (getWidth() - fm.stringWidth(getText())) / 2;  // Calcula la posición X centrada
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;  // Calcula la posición Y centrada
            g2.setColor(getForeground());  // Usa el color del texto configurado
            g2.drawString(getText(), x, y);  // Dibuja el texto centrado

            g2.dispose();  // Libera recursos del contexto gráfico
        }
    }

}