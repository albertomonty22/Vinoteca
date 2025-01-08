package gui.baseEnums;

public enum DenominacionOrigen {
    Rioja("D.O. Rioja"),
    Prioriat("D.O. Priorat"),
    Duro("D.O. Ribera del Duero"),
    Somontano("D.O. Somontano"),
    Otra("Otra denominación");

    private String valor;

    DenominacionOrigen(String valor) {

        this.valor = valor;
    }

    public String getValor() {

        return valor;
    }
}
