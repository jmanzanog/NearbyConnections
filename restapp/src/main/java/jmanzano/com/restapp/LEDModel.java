package jmanzano.com.restapp;

public class LEDModel {
    private static LEDModel instance = null;
    private Boolean state = true;

    public static LEDModel getInstance() {
        if (instance == null) {
            instance = new LEDModel();
        }
        return instance;
    }


    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
