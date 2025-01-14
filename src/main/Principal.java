package main;

import com.formdev.flatlaf.FlatLightLaf;
import gui.Controlador;
import gui.Modelo;
import gui.Vista;

public class Principal {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo,vista);
    }
}
