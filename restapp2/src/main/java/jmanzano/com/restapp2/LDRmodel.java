package jmanzano.com.restapp2;

import java.util.Random;

public class LDRmodel {
    private static LDRmodel instace;
    private Double result;


    private LDRmodel() {
        double leftLimit = 0D;
        double rightLimit = 5D;
        result = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    public static LDRmodel getInstance() {
        if (instace == null) {
            instace = new LDRmodel();
        }
        return instace;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
