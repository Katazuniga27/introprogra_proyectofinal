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
        //Frame principal
        setTitle("Menú Principal - Centro de Bienestar");
        setSize(1200, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //quita el borde
        setUndecorated(true);
        //esquinas redondeadas
        setShape(new RoundRectangle2D.Double(0, 0, 1200, 950, 20, 20));
        //fondo con imagen
        FondoPanel fondo = new FondoPanel("/imagenes/photo-1534438327276-14e5300c3a48.jpg");
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);
       
        

        // Logo
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/smart-fit-logo-0.png"));
        Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(600, 300));

        // Texto de bienvenida
        JLabel mensajeBienvenida = new JLabel("Bienvenido al centro de bienestar", SwingConstants.CENTER);
        mensajeBienvenida.setFont(new Font("Arial", Font.BOLD, 26));
        mensajeBienvenida.setForeground(Color.WHITE);
        mensajeBienvenida.setPreferredSize(new Dimension(600, 50));
        
        //texto de bienvenida2
        JLabel mensajeBienvenida2 = new JLabel("Seleccione el servicio que deseas utilizar", SwingConstants.CENTER);
        mensajeBienvenida2.setFont(new Font("Arial", Font.ITALIC, 18));
        mensajeBienvenida2.setBackground(new Color(255, 255, 200));;
        mensajeBienvenida2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mensajeBienvenida2.setPreferredSize(new Dimension(600, 50));
        
        // === BARRA SUPERIOR CON CONTROLES DE VENTANA ===
        JPanel barraVentana = crearBarraVentana();
        fondo.add(barraVentana, BorderLayout.NORTH);

        // Panel contenedor del logo + mensaje
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        panelSuperior.add(logo, BorderLayout.NORTH);
        panelSuperior.add(mensajeBienvenida, BorderLayout.SOUTH);
        panelSuperior.add(mensajeBienvenida2, BorderLayout.CENTER);

        // Agregar al fondo
        fondo.add(panelSuperior, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 20, 20));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Botones y acciones
        BotonRedondeado btnParqueo = new BotonRedondeado("Parqueo", 30);
        btnParqueo.addActionListener(e -> new Parqueo().abrirInterfaz());
        panelBotones.add(btnParqueo);
        BotonRedondeado btnSalaPesas = new BotonRedondeado("Sala de Pesas", 30);
        btnSalaPesas.addActionListener(e -> new SalaPesas().abrirInterfaz());
        panelBotones.add(btnSalaPesas);
        BotonRedondeado btnAuditorio = new BotonRedondeado("Auditorio", 30);
        btnAuditorio.addActionListener(e -> new Auditorio().abrirInterfaz());
        panelBotones.add(btnAuditorio);
        BotonRedondeado btnCabina = new BotonRedondeado("Cabinas", 30);
        btnCabina.addActionListener(e -> new Cabina().abrirInterfaz());
        panelBotones.add(btnCabina);
        BotonRedondeado btnClasesGrupales = new BotonRedondeado("Clases Grupales", 30);
        btnClasesGrupales.addActionListener(e -> new ClasesGrupales().abrirInterfaz());
        panelBotones.add(btnClasesGrupales);
        BotonRedondeado btnRecreacion = new BotonRedondeado("Recreación", 30);
        btnRecreacion.addActionListener(e -> new Recreacion().abrirInterfaz());
        panelBotones.add(btnRecreacion);

        // Botón salir
        BotonRedondeado btnSalir = new BotonRedondeado("Salir", 30);
        btnSalir.setPreferredSize(new Dimension(150, 50));
        btnSalir.addActionListener(e -> System.exit(0));

        JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panelSalir.setOpaque(false);
        panelSalir.add(btnSalir);

        fondo.add(panelBotones, BorderLayout.CENTER);
        fondo.add(panelSalir, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal());
    }
    private JPanel crearBarraVentana() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setOpaque(false);
        barra.setPreferredSize(new Dimension(0, 40));
        barra.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        // Título de la ventana
        JLabel tituloVentana = new JLabel("SMART FIT - SISTEMA DE GESTIÓN");
        tituloVentana.setFont(new Font("Arial", Font.BOLD, 12));
        tituloVentana.setForeground(new Color(255, 255, 255, 180));

        // Botones de control
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panelControles.setOpaque(false);

        JButton btnMinimizar = crearBotonControl("−", new Color(255, 193, 7));
        btnMinimizar.addActionListener(e -> setState(JFrame.ICONIFIED));

        JButton btnCerrar = crearBotonControl("×", new Color(244, 67, 54));
        btnCerrar.addActionListener(e -> System.exit(0));

        panelControles.add(btnMinimizar);
        panelControles.add(btnCerrar);

        barra.add(tituloVentana, BorderLayout.WEST);
        barra.add(panelControles, BorderLayout.EAST);

        return barra;
    }
  private JButton crearBotonControl(String texto, Color color) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(color);
                } else {
                    g2d.setColor(new Color(255, 255, 255, 100));
                }
                
                g2d.fillOval(0, 0, getWidth(), getHeight());
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };
        
        boton.setPreferredSize(new Dimension(25, 25));
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return boton;
    }
}


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

