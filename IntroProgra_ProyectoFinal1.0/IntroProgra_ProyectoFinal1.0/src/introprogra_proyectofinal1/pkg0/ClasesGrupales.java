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
        // Crea una nueva ventana JFrame con el título "Clases Grupales - Smart Fit"
        JFrame frame = new JFrame("Clases Grupales - Smart Fit");

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
        JLabel titulo = new JLabel("CLASES GRUPALES", SwingConstants.CENTER);

        // Establece la fuente del título: Arial, negrita, tamaño 36
        titulo.setFont(new Font("Arial", Font.BOLD, 36));

        // Establece el color del texto a blanco
        titulo.setForeground(Color.WHITE);

        // Crea el label del subtítulo
        JLabel subtitulo = new JLabel("Entrenamientos dinámicos en grupo", SwingConstants.CENTER);

        // Establece la fuente del subtítulo: Arial, cursiva, tamaño 18
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 18));

        // Establece el color del texto a gris claro (RGB: 200,200,200)
        subtitulo.setForeground(new Color(200, 200, 200));

        // Añade el título al centro del panel
        panelTitulo.add(titulo, BorderLayout.CENTER);

        // Añade el subtítulo en la parte inferior del panel
        panelTitulo.add(subtitulo, BorderLayout.SOUTH);

        // === PANEL CENTRAL - GRID DE CLASES ===
        // Crea el panel central con BorderLayout
        JPanel panelCentral = new JPanel(new BorderLayout());

        // Hace el panel transparente
        panelCentral.setOpaque(false);

        // Añade márgenes: 0 arriba, 20px izquierda/derecha, 20px abajo
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Panel para las clases con GridLayout 2 filas x 3 columnas
        JPanel panelClases = new JPanel(new GridLayout(2, 3, 15, 15));

        // Hace el panel transparente
        panelClases.setOpaque(false);

        // Añade márgenes internos de 20px
        panelClases.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crea tarjetas para cada clase y las añade al panel
        for (int i = 0; i < clases.length; i++) {
            JPanel tarjetaClase = crearTarjetaClase(i, frame);
            panelClases.add(tarjetaClase);
        }

        // Añade el panel de clases al centro del panel central
        panelCentral.add(panelClases, BorderLayout.CENTER);

        // === PANEL INFERIOR - BOTONES DE ACCIÓN ===
        // Crea panel para los botones con FlowLayout centrado
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Hace el panel transparente
        panelBotones.setOpaque(false);

        // Botón "RESERVAR CLASE" con bordes redondeados (radio 25)
        BotonRedondeado btnReservar = new BotonRedondeado("RESERVAR CLASE", 25);

        // Establece tamaño preferido del botón
        btnReservar.setPreferredSize(new Dimension(200, 50));

        // Establece color de fondo morado
        btnReservar.setBackground(new Color(156, 39, 176));

        // Añade ActionListener para mostrar diálogo de reserva al hacer clic
        btnReservar.addActionListener(e -> mostrarDialogoReserva(frame));

        // Botón "VER INSCRITOS"
        BotonRedondeado btnVer = new BotonRedondeado("VER INSCRITOS", 25);
        btnVer.setPreferredSize(new Dimension(180, 50));

        // Color de fondo azul
        btnVer.setBackground(new Color(33, 150, 243));

        // Añade ActionListener para mostrar lista de inscritos
        btnVer.addActionListener(e -> mostrarInscritos(frame));

        // Botón "MODIFICAR CLASE"
        BotonRedondeado btnModificar = new BotonRedondeado("MODIFICAR CLASE", 25);
        btnModificar.setPreferredSize(new Dimension(200, 50));

        // Color de fondo naranja
        btnModificar.setBackground(new Color(255, 152, 0));

        // Añade ActionListener para mostrar diálogo de modificación
        btnModificar.addActionListener(e -> mostrarDialogoModificar(frame));

        // Botón "CERRAR"
        BotonRedondeado btnCerrar = new BotonRedondeado("CERRAR", 25);
        btnCerrar.setPreferredSize(new Dimension(150, 50));

        // Color de fondo gris azulado
        btnCerrar.setBackground(new Color(96, 125, 139));

        // Añade ActionListener para cerrar la ventana
        btnCerrar.addActionListener(e -> frame.dispose());

        // Añade todos los botones al panel de botones
        panelBotones.add(btnReservar);
        panelBotones.add(btnVer);
        panelBotones.add(btnModificar);
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

    private JPanel crearTarjetaClase(int index, JFrame parent) {
        // Crea un panel para la tarjeta de clase con BorderLayout
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout());

        // Establece fondo negro semitransparente (RGB 0,0,0 con alpha 120)
        tarjeta.setBackground(new Color(0, 0, 0, 120));

        // Crea un borde compuesto: línea blanca semitransparente + margen interno
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Panel superior (contiene icono y nombre)
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        // Obtiene el icono correspondiente al tipo de clase
        String icono = obtenerIconoClase(clases[index]);

        // Crea label para el icono
        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 40));
        lblIcono.setPreferredSize(new Dimension(0, 50));

        // Crea label para el nombre de la clase
        JLabel lblNombre = new JLabel(clases[index], SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        lblNombre.setForeground(Color.WHITE);

        // Añade icono y nombre al panel superior
        panelSuperior.add(lblIcono, BorderLayout.NORTH);
        panelSuperior.add(lblNombre, BorderLayout.CENTER);

        // Label para mostrar el horario de la clase
        JLabel lblHorario = new JLabel(horarios[index], SwingConstants.CENTER);
        lblHorario.setFont(new Font("Arial", Font.BOLD, 16));

        // Color amarillo dorado para el horario
        lblHorario.setForeground(new Color(255, 193, 7));

        // Añade márgenes arriba/abajo de 10px
        lblHorario.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panel inferior (contiene información de cupos)
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);

        // Cuenta los inscritos en esta clase
        int inscritos = contarInscritos(index);

        // Obtiene los cupos disponibles
        int disponibles = cuposDisponibles[index];

        // Crea label para mostrar información de cupos
        JLabel lblCupos = new JLabel(disponibles + "/" + (inscritos + disponibles) + " cupos", SwingConstants.CENTER);
        lblCupos.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCupos.setForeground(new Color(200, 200, 200));

        // Barra de progreso para visualizar ocupación
        int totalCupos = inscritos + disponibles;
        JProgressBar progreso = new JProgressBar(0, totalCupos);

        // Establece valor actual (número de inscritos)
        progreso.setValue(inscritos);

        // Oculta el texto de porcentaje
        progreso.setStringPainted(false);

        // Establece altura preferida de 8px
        progreso.setPreferredSize(new Dimension(0, 8));

        // Fondo blanco semitransparente
        progreso.setBackground(new Color(255, 255, 255, 50));

        // Cambia color de la barra según disponibilidad
        if (disponibles > totalCupos * 0.5) {
            // Verde si hay más del 50% de cupos disponibles
            progreso.setForeground(new Color(76, 175, 80));
        } else if (disponibles > 0) {
            // Amarillo si hay algunos cupos disponibles
            progreso.setForeground(new Color(255, 193, 7));
        } else {
            // Rojo si no hay cupos disponibles
            progreso.setForeground(new Color(244, 67, 54));
        }

        // Añade componentes al panel inferior
        panelInferior.add(lblCupos, BorderLayout.NORTH);
        panelInferior.add(progreso, BorderLayout.SOUTH);

        // Añade paneles a la tarjeta
        tarjeta.add(panelSuperior, BorderLayout.NORTH);
        tarjeta.add(lblHorario, BorderLayout.CENTER);
        tarjeta.add(panelInferior, BorderLayout.SOUTH);

        // Efecto hover al pasar el mouse
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
                // Al hacer clic: muestra detalles de la clase
                mostrarDetallesClase(index, parent);
            }
        });

        return tarjeta;
    }

    private String obtenerIconoClase(String nombreClase) {
        // Devuelve emoji correspondiente al tipo de clase
        switch (nombreClase.toLowerCase()) {
            case "yoga":
                return "🧘";
            case "crossfit":
                return "🏋️‍";
            case "funcional":
                return "💪";
            case "pilates":
                return "🤸‍";
            case "zumba":
                return "💃";
            case "spinning":
                return "🚴";
            default:
                return "🏃‍";
        }
    }

    private int contarInscritos(int claseIndex) {
        // Cuenta cuántos usuarios están inscritos en la clase especificada
        int contador = 0;
        for (int i = 0; i < 50; i++) {
            if (inscripciones[claseIndex][i] != null) {
                contador++;
            }
        }
        return contador;
    }

    private void mostrarDialogoReserva(JFrame parent) {
        // Crea diálogo modal para reservar clases
        JDialog dialogo = new JDialog(parent, "Reservar Clase", true);
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(parent);

        // Panel con fondo de imagen
        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal del diálogo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);

        // Añade márgenes de 20px
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título del diálogo
        JLabel titulo = new JLabel("RESERVAR CLASE GRUPAL", SwingConstants.CENTER);
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

        // Panel para mostrar las clases disponibles
        JPanel panelClases = new JPanel(new GridLayout(2, 3, 10, 10));
        panelClases.setOpaque(false);

        // Borde con título "Seleccionar Clase"
        panelClases.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Seleccionar Clase",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        // Grupo de botones para selección única de clases
        ButtonGroup grupoClases = new ButtonGroup();
        JRadioButton[] radioButtons = new JRadioButton[clases.length];

        // Crea un radio button por cada clase
        for (int i = 0; i < clases.length; i++) {
            // Texto del radio button: nombre, horario y cupos disponibles
            String texto = clases[i] + " (" + horarios[i] + ") - " + cuposDisponibles[i] + " cupos";
            radioButtons[i] = new JRadioButton(texto);
            radioButtons[i].setOpaque(false); // Hace transparente el fondo
            radioButtons[i].setForeground(Color.WHITE); // Texto blanco
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 12));

            // Deshabilita el botón si no hay cupos disponibles
            radioButtons[i].setEnabled(cuposDisponibles[i] > 0);

            // Añade al grupo y al panel
            grupoClases.add(radioButtons[i]);
            panelClases.add(radioButtons[i]);
        }

        // Panel para los botones del diálogo
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        // Botón "RESERVAR"
        BotonRedondeado btnConfirmar = new BotonRedondeado("RESERVAR", 20);
        btnConfirmar.setPreferredSize(new Dimension(130, 40));

        // Color de fondo morado
        btnConfirmar.setBackground(new Color(156, 39, 176));

        // Botón "CANCELAR"
        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
        btnCancelar.setPreferredSize(new Dimension(120, 40));

        // Color de fondo gris azulado
        btnCancelar.setBackground(new Color(96, 125, 139));

        // ActionListener para el botón de confirmar reserva
        btnConfirmar.addActionListener(e -> {
            try {
                // Obtiene el ID ingresado
                String idStr = txtID.getText().trim();

                // Valida que no esté vacío
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

                // Busca qué clase está seleccionada
                int claseSeleccionada = -1;
                for (int i = 0; i < radioButtons.length; i++) {
                    if (radioButtons[i].isSelected()) {
                        claseSeleccionada = i;
                        break;
                    }
                }

                // Valida que se haya seleccionado una clase
                if (claseSeleccionada == -1) {
                    mostrarMensajeError(dialogo, "Debe seleccionar una clase");
                    return;
                }

                // Intenta realizar la reserva
                if (reservarClase(idStr, claseSeleccionada)) {
                    // Si la reserva fue exitosa
                    mostrarMensajeExito(dialogo, "Reserva exitosa en " + clases[claseSeleccionada]);
                    dialogo.dispose();
                    parent.dispose();
                    abrirInterfaz(); // Refrescar la interfaz
                } else {
                    // Si no se pudo realizar la reserva
                    mostrarMensajeError(dialogo, "No se pudo realizar la reserva");
                }
            } catch (NumberFormatException ex) {
                // Si el ID no es un número válido
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
        panelPrincipal.add(panelClases, BorderLayout.SOUTH);

        // Añade paneles al fondo del diálogo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        // Hace visible el diálogo
        dialogo.setVisible(true);
    }

    private void mostrarDialogoModificar(JFrame parent) {
        // Crea diálogo modal para modificar clases
        JDialog dialogo = new JDialog(parent, "Modificar Clase", true);
        dialogo.setSize(600, 600);
        dialogo.setLocationRelativeTo(parent);

        // Panel con fondo de imagen
        FondoPanel fondoDialogo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondoDialogo.setLayout(new BorderLayout());
        dialogo.setContentPane(fondoDialogo);

        // Panel principal del diálogo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);

        // Añade márgenes de 20px
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título del diálogo
        JLabel titulo = new JLabel("MODIFICAR CLASE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        // Añade margen inferior de 20px
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel para seleccionar la clase a modificar
        JPanel panelSeleccion = new JPanel(new BorderLayout(0, 10));
        panelSeleccion.setOpaque(false);

        // Label para el combo box
        JLabel lblSeleccionar = new JLabel("Seleccionar clase a modificar:");
        lblSeleccionar.setFont(new Font("Arial", Font.BOLD, 16));
        lblSeleccionar.setForeground(Color.WHITE);

        // Combo box con las clases disponibles
        JComboBox<String> comboClases = new JComboBox<>();

        // Añade cada clase al combo box con su número, nombre y horario
        for (int i = 0; i < clases.length; i++) {
            comboClases.addItem((i + 1) + ". " + clases[i] + " (" + horarios[i] + ")");
        }
        comboClases.setFont(new Font("Arial", Font.PLAIN, 14));
        comboClases.setPreferredSize(new Dimension(0, 35));

        // Añade componentes al panel de selección
        panelSeleccion.add(lblSeleccionar, BorderLayout.NORTH);
        panelSeleccion.add(comboClases, BorderLayout.CENTER);

        // Panel para los campos de modificación
        JPanel panelModificacion = new JPanel(new GridLayout(3, 2, 10, 10));
        panelModificacion.setOpaque(false);

        // Borde con título "Datos a Modificar"
        panelModificacion.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Datos a Modificar",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE
        ));

        // Campo para nuevo nombre
        JLabel lblNombre = new JLabel("Nuevo nombre:");
        lblNombre.setForeground(Color.WHITE);
        JTextField txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(0, 30));

        // Campo para nuevo horario
        JLabel lblHorario = new JLabel("Nuevo horario:");
        lblHorario.setForeground(Color.WHITE);

        // Combo box con horarios disponibles
        JComboBox<String> comboHorarios = new JComboBox<>(horariosDisponibles);
        comboHorarios.setPreferredSize(new Dimension(0, 30));

        // Campo para cupos máximos
        JLabel lblCupos = new JLabel("Cupos máximos:");
        lblCupos.setForeground(Color.WHITE);
        JTextField txtCupos = new JTextField();
        txtCupos.setPreferredSize(new Dimension(0, 30));

        // Añade componentes al panel de modificación
        panelModificacion.add(lblNombre);
        panelModificacion.add(txtNombre);
        panelModificacion.add(lblHorario);
        panelModificacion.add(comboHorarios);
        panelModificacion.add(lblCupos);
        panelModificacion.add(txtCupos);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setOpaque(false);

        // Botón "MODIFICAR"
        BotonRedondeado btnModificar = new BotonRedondeado("MODIFICAR", 20);
        btnModificar.setPreferredSize(new Dimension(130, 40));

        // Color de fondo naranja
        btnModificar.setBackground(new Color(255, 152, 0));

        // Botón "CANCELAR"
        BotonRedondeado btnCancelar = new BotonRedondeado("CANCELAR", 20);
        btnCancelar.setPreferredSize(new Dimension(120, 40));

        // Color de fondo gris azulado
        btnCancelar.setBackground(new Color(96, 125, 139));

        // ActionListener para el botón de modificar
        btnModificar.addActionListener(e -> {
            try {
                // Obtiene el índice de la clase seleccionada
                int indiceSeleccionado = comboClases.getSelectedIndex();

                // Modificar nombre si no está vacío
                String nuevoNombre = txtNombre.getText().trim();
                if (!nuevoNombre.isEmpty()) {
                    clases[indiceSeleccionado] = nuevoNombre;
                }

                // Modificar horario con el seleccionado en el combo box
                horarios[indiceSeleccionado] = (String) comboHorarios.getSelectedItem();

                // Modificar cupos si se especifica
                String nuevoCupoStr = txtCupos.getText().trim();
                if (!nuevoCupoStr.isEmpty()) {
                    // Cuenta los inscritos actuales
                    int reservados = contarInscritos(indiceSeleccionado);
                    int nuevoCupo = Integer.parseInt(nuevoCupoStr);

                    // Valida que los nuevos cupos sean suficientes para los ya reservados
                    if (nuevoCupo >= reservados && nuevoCupo <= 50) {
                        // Calcula los nuevos cupos disponibles
                        cuposDisponibles[indiceSeleccionado] = nuevoCupo - reservados;
                    } else {
                        mostrarMensajeError(dialogo, "Cupos debe ser entre " + reservados + " y 50");
                        return;
                    }
                }

                // Muestra mensaje de éxito y cierra el diálogo
                mostrarMensajeExito(dialogo, "Clase modificada correctamente");
                dialogo.dispose();
                parent.dispose();
                abrirInterfaz(); // Refresca la interfaz
            } catch (NumberFormatException ex) {
                // Si los cupos no son un número válido
                mostrarMensajeError(dialogo, "Cupos debe ser un número válido");
            }
        });

        // ActionListener para cerrar el diálogo
        btnCancelar.addActionListener(e -> dialogo.dispose());

        // Añade botones al panel
        panelBotones.add(btnModificar);
        panelBotones.add(btnCancelar);

        // Panel central que contiene los paneles de selección y modificación
        JPanel panelCentral = new JPanel(new BorderLayout(0, 20));
        panelCentral.setOpaque(false);
        panelCentral.add(panelSeleccion, BorderLayout.NORTH);
        panelCentral.add(panelModificacion, BorderLayout.CENTER);

        // Añade componentes al panel principal
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Añade paneles al fondo del diálogo
        fondoDialogo.add(panelPrincipal, BorderLayout.CENTER);
        fondoDialogo.add(panelBotones, BorderLayout.SOUTH);

        // Hace visible el diálogo
        dialogo.setVisible(true);
    }

    private void mostrarDetallesClase(int claseIndex, JFrame parent) {
        // Construye el mensaje con los detalles de la clase
        StringBuilder detalles = new StringBuilder();
        detalles.append("=== ").append(clases[claseIndex]).append(" ===\n\n");
        detalles.append("HORARIO: ").append(horarios[claseIndex]).append("\n");

        // Obtiene información de cupos
        int inscritos = contarInscritos(claseIndex);
        int total = inscritos + cuposDisponibles[claseIndex];

        detalles.append("CUPOS: ").append(cuposDisponibles[claseIndex]).append("/").append(total).append("\n");
        detalles.append("INSCRITOS: ").append(inscritos).append("\n\n");
        detalles.append("PARTICIPANTES:\n");

        // Verifica si hay inscritos
        boolean hayInscritos = false;
        for (int i = 0; i < 50; i++) {
            if (inscripciones[claseIndex][i] != null) {
                String id = inscripciones[claseIndex][i];
                detalles.append("• ").append(id);

                // Intenta obtener nombre del usuario si existe la clase Usuario
                try {
                    Usuario u = Usuario.buscarPorId(Integer.parseInt(id));
                    if (u != null) {
                        detalles.append(" - ").append(u.getNombre());
                    }
                } catch (Exception ex) {
                    // Si no existe la clase Usuario, solo muestra el ID
                }

                detalles.append("\n");
                hayInscritos = true;
            }
        }

        // Si no hay inscritos, muestra mensaje
        if (!hayInscritos) {
            detalles.append("No hay participantes inscritos aún.");
        }

        // Muestra el diálogo con los detalles
        JOptionPane.showMessageDialog(parent, detalles.toString(),
                "Detalles - " + clases[claseIndex], JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarInscritos(JFrame parent) {
        // Construye el mensaje con todos los inscritos por clase
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("=== PARTICIPANTES POR CLASE ===\n\n");

        // Itera por cada clase
        for (int c = 0; c < clases.length; c++) {
            // Añade icono, nombre y horario de la clase
            mensaje.append(obtenerIconoClase(clases[c])).append(" ").append(clases[c])
                    .append(" (").append(horarios[c]).append("):\n");

            // Verifica si hay inscritos en esta clase
            boolean hayInscritos = false;
            for (int i = 0; i < 50; i++) {
                if (inscripciones[c][i] != null) {
                    String id = inscripciones[c][i];
                    mensaje.append("   • ").append(id);

                    // Intenta obtener nombre del usuario si existe la clase Usuario
                    try {
                        Usuario u = Usuario.buscarPorId(Integer.parseInt(id));
                        if (u != null) {
                            mensaje.append(" - ").append(u.getNombre());
                        }
                    } catch (Exception ex) {
                        // Si no existe la clase Usuario, solo muestra el ID
                    }

                    mensaje.append("\n");
                    hayInscritos = true;
                }
            }

            // Si no hay inscritos, muestra mensaje
            if (!hayInscritos) {
                mensaje.append("   Sin participantes inscritos\n");
            }
            mensaje.append("\n");
        }

        // Crea diálogo personalizado para mostrar la lista
        JDialog dialogo = new JDialog(parent, "Participantes Inscritos", true);
        dialogo.setSize(700, 600);
        dialogo.setLocationRelativeTo(parent);

        // Area de texto para mostrar el mensaje
        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false); // No editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fuente monoespaciada
        textArea.setBackground(new Color(45, 45, 45)); // Fondo oscuro
        textArea.setForeground(Color.WHITE); // Texto blanco

        // Añade márgenes internos
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ScrollPane por si el texto es muy largo
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Añade el scroll pane al diálogo
        dialogo.add(scrollPane);

        // Hace visible el diálogo
        dialogo.setVisible(true);
    }

    private boolean reservarClase(String idSocio, int claseSeleccionada) {
        // Verifica si hay cupos disponibles
        if (cuposDisponibles[claseSeleccionada] == 0) {
            return false; // Sin cupos
        }

        // Verifica si el socio ya está registrado en esta clase
        for (int j = 0; j < 50; j++) {
            if (idSocio.equals(inscripciones[claseSeleccionada][j])) {
                return false; // Ya registrado
            }
        }

        // Busca espacio libre para registrar al socio
        for (int j = 0; j < 50; j++) {
            if (inscripciones[claseSeleccionada][j] == null) {
                // Registra al socio y reduce los cupos disponibles
                inscripciones[claseSeleccionada][j] = idSocio;
                cuposDisponibles[claseSeleccionada]--;
                return true;
            }
        }
        return false; // No debería llegar aquí (hay cupos pero no espacio)
    }

    private void mostrarMensajeExito(Component parent, String mensaje) {
        // Crea diálogo de éxito sin decoraciones
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

                // Suaviza los bordes
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Crea degradado rojo
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(244, 67, 54, 250),
                        0, getHeight(), new Color(198, 40, 40, 250)
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


