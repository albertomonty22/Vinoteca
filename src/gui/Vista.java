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


}
