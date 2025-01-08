package gui.baseEnums;

public enum TipoVino {
    Blanco("Blanco"),
    Tinto("Tinto"),
    Rosado("Rosado");

    private String valor;

    TipoVino(String valor) {

        this.valor = valor;
    }

    public String getValor() {

        return valor;
    }
}
