package gui;

import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class Controlador implements ActionListener, ListSelectionListener, WindowListener {
    private Modelo modelo;
    private Vista vista;
    private boolean refrescar;
    public Controlador(Modelo modelo, Vista vista) {
        this.modelo=modelo;
        this.vista=vista;
        modelo.conectar();
        setOptions();
        addActionListener(this);
        addWindowListener(this);
        refrescarTodo();
        iniciar();
    }

    private void refrescarTodo() {
        refrescarEnologos();
        refrescarBodega();
        refrescarVinos();
        refrescar = false;
    }

    private void addActionListener(ActionListener listener) {
        vista.btnVinoAñadir.addActionListener(listener);
        vista.btnVinoAñadir.setActionCommand("anadirVino");
        vista.btnEnologoAñadir.addActionListener(listener);
        vista.btnEnologoAñadir.setActionCommand("anadirEnologo");
        vista.btnBodegaAñadir.addActionListener(listener);
        vista.btnBodegaAñadir.setActionCommand("anadirBodega");
        vista.btnVinoEliminar.addActionListener(listener);
        vista.btnVinoEliminar.setActionCommand("eliminarVino");
        vista.btnEnologoEliminar.addActionListener(listener);
        vista.btnEnologoEliminar.setActionCommand("eliminarEnologo");
        vista.btnBodegaEliminar.addActionListener(listener);
        vista.btnBodegaEliminar.setActionCommand("eliminarBodega");
        vista.btnVinoModificar.addActionListener(listener);
        vista.btnVinoModificar.setActionCommand("modificarVino");
        vista.btnEnologoModificar.addActionListener(listener);
        vista.btnEnologoModificar.setActionCommand("modificarEnologo");
        vista.btnBodegaModificar.addActionListener(listener);
        vista.btnBodegaModificar.setActionCommand("modificarBodega");
        vista.optionDialog.btnOpcionesGuardar.addActionListener(listener);
        vista.optionDialog.btnOpcionesGuardar.setActionCommand("guardarOpciones");
        vista.itemOpciones.addActionListener(listener);
        vista.itemOpciones.setActionCommand("Opciones");
        vista.itemSalir.addActionListener(listener);
        vista.itemSalir.setActionCommand("Salir");
        vista.itemDesconectar.addActionListener(listener);
        vista.itemDesconectar.setActionCommand("Desconectar");
        vista.btnValidate.addActionListener(listener);
        vista.btnValidate.setActionCommand("abrirOpciones");
    }

    private void addWindowListener(WindowListener listener) {
        vista.addWindowListener(listener);
    }

    void iniciar(){
        vista.bodegaTabla.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = vista.bodegaTabla.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.bodegaTabla.getSelectionModel())) {
                        int row = vista.bodegaTabla.getSelectedRow();
                        vista.textNombreBodega.setText(String.valueOf(vista.bodegaTabla.getValueAt(row, 1)));
                        vista.textEmail.setText(String.valueOf(vista.bodegaTabla.getValueAt(row, 2)));
                        vista.textTelefono.setText(String.valueOf(vista.bodegaTabla.getValueAt(row, 3)));
                        vista.textDireccion.setText(String.valueOf(vista.bodegaTabla.getValueAt(row, 4)));
                        vista.comboDenominacionOrigen.setSelectedItem(String.valueOf(vista.bodegaTabla.getValueAt(row, 5)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.bodegaTabla.getSelectionModel())) {
                            borrarCamposBodegas();
                        } else if (e.getSource().equals(vista.enologoTabla.getSelectionModel())) {
                            borrarCamposEnologos();
                        } else if (e.getSource().equals(vista.vinoTabla.getSelectionModel())) {
                            borrarCamposVinos();
                        }
                    }
                }
            }
        });

        vista.enologoTabla.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel2 = vista.enologoTabla.getSelectionModel();
        cellSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.enologoTabla.getSelectionModel())) {
                        int row = vista.enologoTabla.getSelectedRow();
                        vista.textNombre.setText(String.valueOf(vista.enologoTabla.getValueAt(row, 1)));
                        vista.textApellido.setText(String.valueOf(vista.enologoTabla.getValueAt(row, 2)));
                        vista.fechaNacimiento.setDate((Date.valueOf(String.valueOf(vista.enologoTabla.getValueAt(row, 3)))).toLocalDate());
                        vista.comboBodega.setSelectedItem(String.valueOf(vista.enologoTabla.getValueAt(row, 4)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.bodegaTabla.getSelectionModel())) {
                            borrarCamposBodegas();
                        } else if (e.getSource().equals(vista.enologoTabla.getSelectionModel())) {
                            borrarCamposEnologos();
                        } else if (e.getSource().equals(vista.vinoTabla.getSelectionModel())) {
                            borrarCamposVinos();
                        }
                    }
                }
            }
        });

        vista.vinoTabla.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel3 = vista.vinoTabla.getSelectionModel();
        cellSelectionModel3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel3.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.vinoTabla.getSelectionModel())) {
                        int row = vista.vinoTabla.getSelectedRow();
                        vista.textNombreVino.setText(String.valueOf(vista.vinoTabla.getValueAt(row, 1)));
                        vista.comboEnologo.setSelectedItem(String.valueOf(vista.vinoTabla.getValueAt(row, 2)));
                        vista.comboBodega.setSelectedItem(String.valueOf(vista.vinoTabla.getValueAt(row, 3)));
                        vista.comboTipoVino.setSelectedItem(String.valueOf(vista.vinoTabla.getValueAt(row, 4)));
                        vista.textAño.setText(String.valueOf(vista.vinoTabla.getValueAt(row, 5)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.bodegaTabla.getSelectionModel())) {
                            borrarCamposBodegas();
                        } else if (e.getSource().equals(vista.enologoTabla.getSelectionModel())) {
                            borrarCamposEnologos();
                        } else if (e.getSource().equals(vista.vinoTabla.getSelectionModel())) {
                            borrarCamposVinos();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
            if (e.getSource().equals(vista.bodegaTabla.getSelectionModel())) {
                int row = vista.bodegaTabla.getSelectedRow();
                vista.textNombreBodega.setText(String.valueOf(vista.bodegaTabla.getValueAt(row, 1)));
                vista.textEmail.setText(String.valueOf(vista.bodegaTabla.getValueAt(row, 2)));
                vista.textTelefono.setText(String.valueOf(vista.bodegaTabla.getValueAt(row, 3)));
                vista.textDireccion.setText(String.valueOf(vista.bodegaTabla.getValueAt(row, 4)));
                vista.comboDenominacionOrigen.setSelectedItem(String.valueOf(vista.bodegaTabla.getValueAt(row, 5)));
            } else if (e.getSource().equals(vista.enologoTabla.getSelectionModel())) {
                int row = vista.enologoTabla.getSelectedRow();
                vista.textNombre.setText(String.valueOf(vista.enologoTabla.getValueAt(row, 1)));
                vista.textApellido.setText(String.valueOf(vista.enologoTabla.getValueAt(row, 2)));
                vista.fechaNacimiento.setDate((Date.valueOf(String.valueOf(vista.enologoTabla.getValueAt(row, 3)))).toLocalDate());
                vista.comboBodega.setSelectedItem(String.valueOf(vista.enologoTabla.getValueAt(row, 4)));
            } else if (e.getSource().equals(vista.vinoTabla.getSelectionModel())) {
                int row = vista.vinoTabla.getSelectedRow();
                vista.textNombreVino.setText(String.valueOf(vista.vinoTabla.getValueAt(row, 1)));
                vista.comboEnologo.setSelectedItem(String.valueOf(vista.vinoTabla.getValueAt(row, 2)));
                vista.comboBodega.setSelectedItem(String.valueOf(vista.vinoTabla.getValueAt(row, 3)));
                vista.comboTipoVino.setSelectedItem(String.valueOf(vista.vinoTabla.getValueAt(row, 4)));
                vista.textAño.setText(String.valueOf(vista.vinoTabla.getValueAt(row, 5)));
            } else if (e.getValueIsAdjusting()
                    && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                if (e.getSource().equals(vista.bodegaTabla.getSelectionModel())) {
                    borrarCamposBodegas();
                } else if (e.getSource().equals(vista.enologoTabla.getSelectionModel())) {
                    borrarCamposEnologos();
                } else if (e.getSource().equals(vista.vinoTabla.getSelectionModel())) {
                    borrarCamposVinos();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command){
            case "Opciones":
                vista.adminPasswordDialog.setVisible(true);
                break;
            case "Desconectar":
                modelo.desconectar();
                break;
            case "Salir":
                System.exit(0);
                break;
            case "abrirOpciones":
                if (String.valueOf(vista.adminPassword.getPassword()).equals(modelo.getAdminPassword())){
                    vista.adminPassword.setText("");
                    vista.adminPasswordDialog.dispose();
                    vista.optionDialog.setVisible(true);
                }else {
                    Util.showErrorAlert("La contraseña introducida no es correcta");
                }
                break;
            case "guardarOpciones":
                modelo.setPropValues(vista.optionDialog.textIP.getText(),
                        vista.optionDialog.textUsuario.getText(),
                        String.valueOf(vista.optionDialog.pfPass.getPassword()),
                        String.valueOf(vista.optionDialog.pfAdmin.getPassword()));
                //eliminamos JFrame
                vista.optionDialog.dispose();
                vista.dispose();
                //Creamos de nuevo controlador
                new Controlador(new Modelo(),new Vista());
                break;
            case "anadirVino":
                try {
                    if (comprobarVinoVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.vinoTabla.clearSelection();
                    } else if (modelo.vinoYaExiste(vista.textNombreVino.getText())) {
                        Util.showErrorAlert("Este vino ya existe");
                        vista.vinoTabla.clearSelection();
                    } else {
                        modelo.insertarVino(
                                vista.textNombreVino.getText(),
                                String.valueOf(vista.comboEnologo.getSelectedItem()),
                                String.valueOf(vista.comboBodegaVino.getSelectedItem()),
                                String.valueOf(vista.comboTipoVino.getSelectedItem()),
                                vista.textAño.getText());
                    }
                }catch (NumberFormatException nfe){
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.vinoTabla.clearSelection();
                }
                borrarCamposVinos();
                refrescarVinos();
                break;
            case "modificarVino":
                try {
                    if (comprobarVinoVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.vinoTabla.clearSelection();
                    }  else {
                        modelo.modificarVino(
                                vista.textNombreVino.getText(),
                                String.valueOf(vista.comboEnologo.getSelectedItem()),
                                String.valueOf(vista.comboBodegaVino.getSelectedItem()),
                                String.valueOf(vista.comboTipoVino.getSelectedItem()),
                                vista.textAño.getText(),
                                (Integer) vista.vinoTabla.getValueAt(vista.vinoTabla.getSelectedRow(),0));
                    }
                }catch (NumberFormatException nfe){
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.vinoTabla.clearSelection();
                }
                borrarCamposVinos();
                refrescarVinos();
                break;
            case "eliminarVino":
                modelo.eliminarVino((Integer) vista.vinoTabla.getValueAt(vista.vinoTabla.getSelectedRow(),0));
                borrarCamposVinos();
                refrescarVinos();
                break;
            case "anadirEnologo":
                try {
                    if (comprobarEnologoVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.enologoTabla.clearSelection();
                    } else if (modelo.enologoNombreYaExiste(vista.textNombre.getText(),vista.textApellido.getText())) {
                        Util.showErrorAlert("Este enólogo ya existe");
                        vista.enologoTabla.clearSelection();
                    } else {
                        modelo.insertarEnologo(
                                vista.textNombre.getText(),
                                vista.textApellido.getText(),
                                vista.fechaNacimiento.getDate(),
                                String.valueOf(vista.comboBodega.getSelectedItem()));
                    }
                }catch (NumberFormatException nfe){
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.vinoTabla.clearSelection();
                }
                borrarCamposEnologos();
                refrescarEnologos();
                break;
            case "modificarEnologo":
                try {
                    if (comprobarEnologoVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.enologoTabla.clearSelection();
                    }  else {
                        modelo.modificarEnologo(
                                vista.textNombre.getText(),
                                vista.textApellido.getText(),
                                vista.fechaNacimiento.getDate(),
                                String.valueOf(vista.comboBodega.getSelectedItem()),
                                (Integer) vista.enologoTabla.getValueAt(vista.enologoTabla.getSelectedRow(),0));
                    }
                }catch (NumberFormatException nfe){
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.vinoTabla.clearSelection();
                }
                borrarCamposEnologos();
                refrescarEnologos();
                break;
            case "eliminarEnologo":
                modelo.eliminarEnologo((Integer) vista.enologoTabla.getValueAt(vista.enologoTabla.getSelectedRow(),0));
                borrarCamposEnologos();
                refrescarEnologos();
                break;
            case "anadirBodega":
                try {
                    if (comprobarBodegaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.bodegaTabla.clearSelection();
                    } else if (modelo.bodegaNombreYaExiste(vista.textNombreBodega.getText())) {
                        Util.showErrorAlert("Esta bodega ya existe");
                        vista.bodegaTabla.clearSelection();
                    } else {
                        modelo.insertarBodega(
                                vista.textNombreBodega.getText(),
                                vista.textEmail.getText(),
                                vista.textTelefono.getText(),
                                vista.textDireccion.getText(),
                                String.valueOf(vista.comboDenominacionOrigen.getSelectedItem()));
                    }
                }catch (NumberFormatException nfe){
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.bodegaTabla.clearSelection();
                }
                borrarCamposBodegas();
                refrescarBodega();
                break;
            case "modificarBodega":
                try {
                    if (comprobarBodegaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.bodegaTabla.clearSelection();
                    }  else {
                        modelo.modificarBodega(
                                vista.textNombreBodega.getText(),
                                vista.textEmail.getText(),
                                vista.textTelefono.getText(),
                                vista.textDireccion.getText(),
                                String.valueOf(vista.comboDenominacionOrigen.getSelectedItem()),
                                (Integer) vista.bodegaTabla.getValueAt(vista.bodegaTabla.getSelectedRow(),0));
                    }
                }catch (NumberFormatException nfe){
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.bodegaTabla.clearSelection();
                }
                borrarCamposEnologos();
                refrescarEnologos();
                break;
            case "eliminarBodega":
                modelo.eliminarBodega((Integer) vista.bodegaTabla.getValueAt(vista.bodegaTabla.getSelectedRow(),0));
                borrarCamposBodegas();
                refrescarBodega();
                break;
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }


    private void refrescarBodega() {
        try {
            vista.bodegaTabla.setModel(construirTableModelBodegas(modelo.consultarBodega()));
            vista.comboBodega.removeAllItems();
            vista.comboBodegaVino.removeAllItems();
            for(int i = 0; i < vista.dtmBodegas.getRowCount(); i++) {
                vista.comboBodega.addItem(vista.dtmBodegas.getValueAt(i, 0)+" - "+
                        vista.dtmBodegas.getValueAt(i, 1));
                vista.comboBodegaVino.addItem(vista.dtmBodegas.getValueAt(i, 0)+" - "+
                        vista.dtmBodegas.getValueAt(i, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModelBodegas(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        //Nombres de columnas
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column=1;column<=columnCount;column++){
            columnNames.add(metaData.getColumnName(column));
        }
        //Datos a tabla
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs,columnCount,data);

        //Ponemos en dtm
        vista.dtmBodegas.setDataVector(data,columnNames);
        return vista.dtmBodegas;
    }

    private void refrescarEnologos() {
        try {
            vista.enologoTabla.setModel(construirTableModelEnologos(modelo.consultarEnologo()));
            vista.comboEnologo.removeAllItems();
            for (int i = 0;i<vista.dtmEnologos.getRowCount();i++){
                vista.comboEnologo.addItem(vista.dtmEnologos.getValueAt(i,0)+" - "+
                        vista.dtmEnologos.getValueAt(i,2)+", "+
                        vista.dtmEnologos.getValueAt(i,1));
            }
            //ponemos datos en comboAutor con este formato:
            //1 - Perez, Pepe
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModelEnologos(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        //Nombres de columnas
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column=1;column<=columnCount;column++){
            columnNames.add(metaData.getColumnName(column));
        }
        //Datos a tabla
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs,columnCount,data);

        //Ponemos en dtm
        vista.dtmEnologos.setDataVector(data,columnNames);
        return vista.dtmEnologos;
    }

    private void refrescarVinos() {
        try {
            vista.vinoTabla.setModel(construirTableModelVinos(modelo.consultarVinos()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModelVinos(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        //Nombres de columnas
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column=1;column<=columnCount;column++){
            columnNames.add(metaData.getColumnName(column));
        }
        //Datos a tabla
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs,columnCount,data);

        //Ponemos en dtm
        vista.dtmVinos.setDataVector(data,columnNames);
        return vista.dtmVinos;
    }

    private void setDataVector(ResultSet rs, int columnCount, Vector<Vector<Object>> data) throws SQLException {
        while (rs.next()){
            Vector<Object> vector = new Vector<>();
            for (int columnIndex=1;columnIndex<=columnCount;columnIndex++){
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
    }

    private void setOptions() {
        vista.optionDialog.textIP.setText(modelo.getIp());
        vista.optionDialog.textUsuario.setText(modelo.getUser());
        vista.optionDialog.pfPass.setText(modelo.getPassword());
        vista.optionDialog.pfAdmin.setText(modelo.getAdminPassword());
    }

    private void borrarCamposVinos() {
        vista.textNombreVino.setText("");
        vista.comboEnologo.setSelectedIndex(-1);
        vista.comboBodegaVino.setSelectedIndex(-1);
        vista.comboTipoVino.setSelectedIndex(-1);
        vista.textAño.setText("");
    }

    private void borrarCamposEnologos() {
        vista.textNombre.setText("");
        vista.textApellido.setText("");
        vista.fechaNacimiento.setDate(null);
        vista.comboBodega.setSelectedIndex(-1);
    }

    private void borrarCamposBodegas() {
        vista.textNombreBodega.setText("");
        vista.textEmail.setText("");
        vista.textTelefono.setText("");
        vista.textDireccion.setText("");
        vista.comboDenominacionOrigen.setSelectedIndex(-1);
    }

    private boolean comprobarVinoVacio() {
        return vista.textNombreVino.getText().isEmpty() ||
                vista.comboEnologo.getSelectedIndex()==-1 ||
                vista.comboBodegaVino.getSelectedIndex()==-1 ||
                vista.comboTipoVino.getSelectedIndex()==-1 ||
                vista.textAño.getText().isEmpty();
    }

    private boolean comprobarEnologoVacio() {
        return vista.textNombre.getText().isEmpty() ||
                vista.textApellido.getText().isEmpty() ||
                vista.comboBodega.getSelectedIndex()==-1 ||
                vista.fechaNacimiento.getText().isEmpty();
    }

    private boolean comprobarBodegaVacia() {
        return vista.textNombreBodega.getText().isEmpty() ||
                vista.textEmail.getText().isEmpty() ||
                vista.textTelefono.getText().isEmpty() ||
                vista.textDireccion.getText().isEmpty() ||
                vista.comboDenominacionOrigen.getSelectedIndex()==-1;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
