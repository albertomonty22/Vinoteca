package gui;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        //initFrame();
    }


}
