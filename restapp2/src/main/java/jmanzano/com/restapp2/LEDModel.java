package jmanzano.com.restapp2;

public class LEDModel {
    private static LEDModel instance = null;

    Boolean estado;

    public static LEDModel getInstance() {
        if (instance == null) {
            instance = new LEDModel();
        }
        return instance;
    }

    private LEDModel() {
        this.estado = true;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}


