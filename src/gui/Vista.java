package gui;

import com.github.lgooddatepicker.components.DatePicker;
import gui.baseEnums.DenominacionOrigen;
import gui.baseEnums.TipoVino;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private final static String TITULO_FRAME="Aplicacion Vinoteca";


    //Bodega
    JPanel JPanelBodega;
    JTextField textNombreBodega;
    JTextField textEmail;
    JTextField textTelefono;
    JTextField textDireccion;
    JComboBox comboDenominacionOrigen;
    JButton btnBodegaAñadir;
    JButton btnBodegaModificar;
    JButton btnBodegaEliminar;
    JTable bodegaTabla;

    //Enólogo
    JPanel JPanelEnologo;
    JTextField textNombre;
    JTextField textApellido;
    JComboBox comboBodega;
    DatePicker fechaNacimiento;
    JButton btnEnologoAñadir;
    JButton btnEnologoModificar;
    JButton btnEnologoEliminar;
    JTable enologoTabla;

    //Vino
    JPanel JPanelVino;
    JTextField textNombreVino;
    JComboBox comboEnologo;
    JComboBox comboBodegaVino;
    JComboBox comboTipoVino;
    JTextField textAño;
    JButton btnVinoAñadir;
    JButton btnVinoModificar;
    JButton btnVinoEliminar;
    JTable vinoTabla;

    //busqueda
    private JLabel etiquetaEstado;

    //default table model
    DefaultTableModel dtmBodegas;
    DefaultTableModel dtmEnologos;
    DefaultTableModel dtmVinos;

    //menubar
    JMenuItem itemOpciones;
    JMenuItem itemDesconectar;
    JMenuItem itemSalir;

    //cuadro dialogo
    OptionDialog optionDialog;
    JDialog adminPasswordDialog;
    JButton btnValidate;
    JPasswordField adminPassword;

    public Vista() {
        super(TITULO_FRAME);
        initFrame();
    }

    public void initFrame() {
        this.setContentPane(panel1);
        //al clickar en cerrar no hace nada
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        //doy dimension
        this.setSize(new Dimension(this.getWidth()+100,this.getHeight()));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        //creo cuadro dialogo
        optionDialog=new OptionDialog(this);
        //llamo menu
        setMenu();
        //llamo cuadro dialogo admin
        setAdminDialog();
        //cargo enumerados
        setEnumComboBox();
        //cargo table models
        setTableModels();
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth() + 100, this.getHeight()));
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // Aplica colores
        applyStyles();

        // Creo cuadro de diálogo
        optionDialog = new OptionDialog(this);
        // Llamo menú
        setMenu();
        // Llamo cuadro diálogo admin
        setAdminDialog();
        // Cargo enumerados
        setEnumComboBox();
        // Cargo table models
        setTableModels();
    }
    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        //Opciones, desconectar, salir
        itemOpciones = new JMenuItem("Opciones");
        itemOpciones.setActionCommand("Opciones");
        itemDesconectar = new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");
        itemSalir=new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");
        menu.add(itemOpciones);
        menu.add(itemDesconectar);
        menu.add(itemSalir);
        mbBar.add(menu);
        //crea un espacio horizontal en las opciones de menu
        mbBar.add(Box.createHorizontalGlue());
        this.setJMenuBar(mbBar);
    }
    private void setAdminDialog() {
        btnValidate = new JButton("Validar");
        btnValidate.setActionCommand("abrirOpciones");
        adminPassword = new JPasswordField();
        //dimension al cuadro de texto
        adminPassword.setPreferredSize(new Dimension(100,26));
        Object[] options=new Object[] {adminPassword,btnValidate};
        JOptionPane jop = new JOptionPane("Introduce la contraseña",JOptionPane.WARNING_MESSAGE,
                JOptionPane.YES_NO_OPTION,null,options);
        adminPasswordDialog = new JDialog(this,"Opciones",true);
        adminPasswordDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        adminPasswordDialog.setContentPane(jop);
        adminPasswordDialog.pack();
        adminPasswordDialog.setLocationRelativeTo(this);
    }
    private void setEnumComboBox() {
        //recorrer los enumerados y los cargo en el comboBox correspondiente
        //.values cogemos valores del enumerado
        //.getValor los añadimos al combo
        for (DenominacionOrigen constant: DenominacionOrigen.values()) {
            comboDenominacionOrigen.addItem(constant.getValor());
        }
        //lo coloco en una posicion que no tenga valor
        comboDenominacionOrigen.setSelectedIndex(-1);
        for (TipoVino constant: TipoVino.values()) {
            comboTipoVino.addItem(constant.getValor());
        }
        comboTipoVino.setSelectedIndex(-1);
    }
    private void setTableModels() {
        //librosTabla, autoresTabla, editorialesTabla
        this.dtmVinos=new DefaultTableModel();
        this.vinoTabla.setModel(dtmVinos);

        this.dtmEnologos=new DefaultTableModel();
        this.enologoTabla.setModel(dtmEnologos);

        this.dtmBodegas=new DefaultTableModel();
        this.bodegaTabla.setModel(dtmBodegas);
    }

    private void applyStyles() {
        // Color de fondo del panel principal
        panel1.setBackground(new Color(240, 240, 240));

        // Colores para los paneles principales
        JPanelBodega.setBackground(new Color(220, 245, 255));
        JPanelEnologo.setBackground(new Color(235, 255, 220));
        JPanelVino.setBackground(new Color(255, 240, 245));

        // Colores para botones
        Color buttonBackground = new Color(70, 130, 180); // Azul acero
        Color buttonForeground = Color.WHITE;

        JButton[] buttons = {
                btnBodegaAñadir, btnBodegaModificar, btnBodegaEliminar,
                btnEnologoAñadir, btnEnologoModificar, btnEnologoEliminar,
                btnVinoAñadir, btnVinoModificar, btnVinoEliminar
        };

        for (JButton button : buttons) {
            button.setBackground(buttonBackground);
            button.setForeground(buttonForeground);
        }

        // Estiliza las tablas
        JTable[] tables = {bodegaTabla, enologoTabla, vinoTabla};
        for (JTable table : tables) {
            table.setBackground(Color.WHITE);
            table.setForeground(Color.BLACK);
            table.setGridColor(new Color(200, 200, 200));
            table.setSelectionBackground(new Color(135, 206, 250)); // Azul claro
            table.setSelectionForeground(Color.BLACK);
        }

        // Menú estilizado
        JMenuBar menuBar = this.getJMenuBar();
        if (menuBar != null) {
            menuBar.setBackground(new Color(100, 149, 237)); // Azul cielo medio
            menuBar.setForeground(Color.WHITE);
        }
    }
}
