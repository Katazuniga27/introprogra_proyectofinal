/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package introprogra_proyectofinal1.pkg0;

/**
 *
 * @author andreyvargassolis
 */
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        // Configuración del frame principal
        setTitle("Menú Principal - Centro de Bienestar");  // Establece el título de la ventana
        setSize(1200, 950);  // Establece tamaño de la ventana (1200x950 px)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cierra la aplicación al cerrar la ventana
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla
        setUndecorated(true);  // Quita los bordes nativos del sistema operativo
        
        // Establece esquinas redondeadas con RoundRectangle2D
        setShape(new RoundRectangle2D.Double(0, 0, 1200, 950, 20, 20));
        
        // Crea y configura el panel de fondo con imagen
        FondoPanel fondo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondo.setLayout(new BorderLayout());  // Usa BorderLayout como administrador de diseño
        setContentPane(fondo);  // Establece el panel de fondo como content pane

        // Configuración del logo
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/smart-fit-logo-0.png"));
        Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);  // Escala la imagen
        JLabel logo = new JLabel(new ImageIcon(img), SwingConstants.CENTER);  // Crea label con la imagen
        logo.setPreferredSize(new Dimension(600, 300));  // Establece tamaño preferido

        // Texto de bienvenida principal
        JLabel mensajeBienvenida = new JLabel("Bienvenido al centro de bienestar", SwingConstants.CENTER);
        mensajeBienvenida.setFont(new Font("Arial", Font.BOLD, 26));  // Fuente Arial, negrita, tamaño 26
        mensajeBienvenida.setForeground(Color.WHITE);  // Texto en color blanco
        mensajeBienvenida.setPreferredSize(new Dimension(600, 50));  // Tamaño preferido
        
        // Texto de bienvenida secundario
        JLabel mensajeBienvenida2 = new JLabel("Seleccione el servicio que deseas utilizar", SwingConstants.CENTER);
        mensajeBienvenida2.setFont(new Font("Arial", Font.ITALIC, 18));  // Fuente Arial, cursiva, tamaño 18
        mensajeBienvenida2.setForeground(new Color(200, 200, 200));  // Texto en gris claro
        mensajeBienvenida2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));  // Margen superior
        mensajeBienvenida2.setPreferredSize(new Dimension(600, 50));  // Tamaño preferido
        
        // === BARRA SUPERIOR CON CONTROLES DE VENTANA ===
        JPanel barraVentana = crearBarraVentana();  // Crea la barra superior personalizada
        fondo.add(barraVentana, BorderLayout.NORTH);  // Añade la barra al norte del panel

        // Panel contenedor del logo + mensajes
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);  // Hace el panel transparente
        panelSuperior.add(logo, BorderLayout.NORTH);  // Añade el logo arriba
        panelSuperior.add(mensajeBienvenida, BorderLayout.CENTER);  // Añade mensaje principal al centro
        panelSuperior.add(mensajeBienvenida2, BorderLayout.SOUTH);  // Añade mensaje secundario abajo

        // Agregar al fondo
        fondo.add(panelSuperior, BorderLayout.NORTH);  // Añade el panel superior al norte del fondo

        // Panel de botones principales
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 20, 20));  // Grid de 2 filas x 3 columnas
        panelBotones.setOpaque(false);  // Panel transparente
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));  // Márgenes interno

        // Botones con emojis, títulos y descripciones
        BotonModerno btnParqueo = new BotonModerno("🚗", "PARQUEO", "Gestión inteligente de espacios");
        btnParqueo.addActionListener(e -> new Parqueo().abrirInterfaz());  // Acción al hacer clic
        panelBotones.add(btnParqueo);
        
        BotonModerno btnSalaPesas = new BotonModerno("🏋", "SALA DE PESAS", "Equipamiento profesional");
        btnSalaPesas.addActionListener(e -> new SalaPesas().abrirInterfaz());
        panelBotones.add(btnSalaPesas);
        
        BotonModerno btnAuditorio = new BotonModerno("🎭", "AUDITORIO", "Eventos y presentaciones");
        btnAuditorio.addActionListener(e -> new Auditorio().abrirInterfaz());
        panelBotones.add(btnAuditorio);
        
        BotonModerno btnCabina = new BotonModerno("🚿", "CABINAS", "Vestuarios y duchas");
        btnCabina.addActionListener(e -> new Cabina().abrirInterfaz());
        panelBotones.add(btnCabina);
        
        BotonModerno btnClasesGrupales = new BotonModerno("🧘", "CLASES GRUPALES", "Yoga, aeróbicos y más");
        btnClasesGrupales.addActionListener(e -> new ClasesGrupales().abrirInterfaz());
        panelBotones.add(btnClasesGrupales);
        
        BotonModerno btnRecreacion = new BotonModerno("🎯", "RECREACIÓN", "Actividades y juegos");
        btnRecreacion.addActionListener(e -> new Recreacion().abrirInterfaz());
        panelBotones.add(btnRecreacion);

        // Panel inferior con información de contacto y botón salir
        JPanel panelInferior = crearPanelInferior();
        
        // Botón salir
        BotonRedondeado btnSalir = new BotonRedondeado("SALIR", 30);  // Botón redondeado con radio 30
        btnSalir.setPreferredSize(new Dimension(150, 50));  // Tamaño preferido
        btnSalir.addActionListener(e -> System.exit(0));  // Cierra la aplicación al hacer clic
        panelInferior.add(btnSalir, BorderLayout.EAST);  // Añade el botón a la derecha

        // Añade componentes al fondo
        fondo.add(panelBotones, BorderLayout.CENTER);  // Panel de botones al centro
        fondo.add(panelInferior, BorderLayout.SOUTH);  // Panel inferior al sur

        setVisible(true);  // Hace visible la ventana
    }

    public static void main(String[] args) {
        // Ejecuta la aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> new MenuPrincipal());
    }
    
    private JPanel crearBarraVentana() {
        // Crea la barra superior personalizada
        JPanel barra = new JPanel(new BorderLayout());
        barra.setOpaque(false);  // Barra transparente
        barra.setPreferredSize(new Dimension(0, 40));  // Altura de 40px
        barra.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));  // Márgenes internos

        // Título de la ventana
        JLabel tituloVentana = new JLabel("SMART FIT - SISTEMA DE GESTIÓN");
        tituloVentana.setFont(new Font("Arial", Font.BOLD, 12));  // Fuente pequeña
        tituloVentana.setForeground(new Color(255, 255, 255, 180));  // Blanco semitransparente

        // Panel para botones de control (minimizar y cerrar)
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panelControles.setOpaque(false);  // Panel transparente

        // Botón minimizar (color amarillo)
        JButton btnMinimizar = crearBotonControl("−", new Color(255, 193, 7));
        btnMinimizar.addActionListener(e -> setState(JFrame.ICONIFIED));  // Minimiza la ventana

        // Botón cerrar (color rojo)
        JButton btnCerrar = crearBotonControl("×", new Color(244, 67, 54));
        btnCerrar.addActionListener(e -> System.exit(0));  // Cierra la aplicación

        // Añade botones al panel
        panelControles.add(btnMinimizar);
        panelControles.add(btnCerrar);

        // Añade componentes a la barra
        barra.add(tituloVentana, BorderLayout.WEST);  // Título a la izquierda
        barra.add(panelControles, BorderLayout.EAST);  // Controles a la derecha

        return barra;
    }
    
    private JPanel crearPanelInferior() {
        // Crea el panel inferior con información de contacto
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);  // Panel transparente
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));  // Márgenes
        
        // Panel de información de contacto
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);  // Panel transparente
        
        // Texto con información de contacto
        JLabel info = new JLabel("🏢 Smart Fit - Tu bienestar es nuestra prioridad | 📞 2222-3333 | 📧 info@smartfit.cr");
        info.setFont(new Font("Arial", Font.PLAIN, 12));  // Fuente pequeña
        info.setForeground(new Color(255, 255, 255, 150));  // Blanco semitransparente
        infoPanel.add(info);  // Añade el texto al panel
        
        panel.add(infoPanel, BorderLayout.WEST);  // Añade el panel de info a la izquierda
        
        return panel;
    }
    
    private JButton crearBotonControl(String texto, Color color) {
        // Crea botones de control circulares personalizados
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Cambia el color según el estado del botón
                if (getModel().isPressed()) {
                    g2d.setColor(color.darker());  // Más oscuro al presionar
                } else if (getModel().isRollover()) {
                    g2d.setColor(color);  // Color normal al pasar el mouse
                } else {
                    g2d.setColor(new Color(255, 255, 255, 100));  // Blanco semitransparente por defecto
                }
                
                g2d.fillOval(0, 0, getWidth(), getHeight());  // Dibuja círculo de fondo
                
                // Dibuja el texto centrado
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };
        
        // Configuración del botón
        boton.setPreferredSize(new Dimension(25, 25));  // Tamaño pequeño
        boton.setBorderPainted(false);  // Sin borde
        boton.setContentAreaFilled(false);  // Sin relleno por defecto
        boton.setFocusPainted(false);  // Sin efecto de foco
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Cursor de mano
        
        return boton;
    }
}


// Panel de fondo con imagen oscurecida
class FondoPanel extends JPanel {
    private Image imagen;  // Imagen de fondo

    public FondoPanel(String rutaImagen) {
        // Carga la imagen desde los recursos
        imagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Dibuja la imagen escalada al tamaño del panel
            g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            // Añade una capa oscura semitransparente
            g2.setColor(new Color(0, 0, 0, 180));  // Opacidad
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}

// Botón moderno con emoji, título y descripción
class BotonModerno extends JButton {
    private String emoji;  // Emoji del botón
    private String titulo;  // Título principal
    private String descripcion;  // Descripción secundaria
    private int radius = 20;  // Radio de las esquinas redondeadas
    private boolean isHovered = false;  // Estado de hover

    public BotonModerno(String emoji, String titulo, String descripcion) {
        this.emoji = emoji;
        this.titulo = titulo;
        this.descripcion = descripcion;
        
        // Configuración básica del botón
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));  // Cursor de mano
        
        // Eventos de hover para cambiar el aspecto
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                isHovered = true;
                repaint();  // Redibuja al entrar el mouse
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                isHovered = false;
                repaint();  // Redibuja al salir el mouse
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Configuración de calidad de renderizado
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Fondo con transparencia oscura y efecto hover
        if (isHovered) {
            g2.setColor(new Color(0, 0, 0, 120));  // Más opaco oscuro al hacer hover
        } else {
            g2.setColor(new Color(0, 0, 0, 80));  // Transparente oscuro por defecto
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);  // Fondo redondeado
        
        // Borde sutil blanco
        g2.setColor(new Color(255, 255, 255, 60));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);

        int centerX = getWidth() / 2;  // Centro horizontal
        int startY = 30;  // Posición vertical inicial

        // Dibujar emoji
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));  // Fuente para emojis
        FontMetrics emojiMetrics = g2.getFontMetrics();
        int emojiX = centerX - emojiMetrics.stringWidth(emoji) / 2;  // Centrar emoji
        g2.setColor(Color.WHITE);
        g2.drawString(emoji, emojiX, startY + emojiMetrics.getAscent());

        // Dibujar título
        g2.setFont(new Font("Arial", Font.BOLD, 18));  // Fuente para título
        FontMetrics tituloMetrics = g2.getFontMetrics();
        int tituloX = centerX - tituloMetrics.stringWidth(titulo) / 2;  // Centrar título
        int tituloY = startY + emojiMetrics.getHeight() + 20;  // Posición debajo del emoji
        g2.setColor(Color.WHITE);
        g2.drawString(titulo, tituloX, tituloY);

        // Dibujar descripción
        g2.setFont(new Font("Arial", Font.PLAIN, 14));  // Fuente para descripción
        FontMetrics descMetrics = g2.getFontMetrics();
        int descX = centerX - descMetrics.stringWidth(descripcion) / 2;  // Centrar descripción
        int descY = tituloY + 25;  // Posición debajo del título
        g2.setColor(new Color(200, 200, 200));  // Gris claro
        g2.drawString(descripcion, descX, descY);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(280, 150);  // Tamaño preferido del botón
    }
}

// Botón redondeado original para el botón de salir
class BotonRedondeado extends JButton {
    private int radius;  // Radio de las esquinas

    public BotonRedondeado(String text, int radius) {
        super(text);
        this.radius = radius;
        // Configuración básica del botón
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);  // Texto blanco
        setBackground(new Color(96, 125, 139));  // Color de fondo (gris azulado)
        setFont(new Font("Arial", Font.BOLD, 18));  // Fuente del texto
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibuja el fondo redondeado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Dibuja el texto centrado
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(getForeground());
        g2.drawString(getText(), x, y);

        g2.dispose();
    }
}