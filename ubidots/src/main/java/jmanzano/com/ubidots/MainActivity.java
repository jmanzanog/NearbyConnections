package jmanzano.com.ubidots;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {
    private final String token = "A1E-3aAB5KChbI1LzrSFH6475HrqbHtQZX";
    private final String idIluminacion = "5d12dd99c03f973c065aeaae";
    private final String idBoton = "5d12dda7c03f973c065aeab2";
    private final String PIN_BUTTON = "BCM23";
    private Gpio mButtonGpio;
    private Double buttonstatus = 0.0;
    private Handler handler = new Handler();
    private Runnable runnable = new UpdateRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PeripheralManager service = PeripheralManager.getInstance();
        try {
            mButtonGpio = service.openGpio(PIN_BUTTON);
            mButtonGpio.setDirection(Gpio.DIRECTION_IN);
            mButtonGpio.setActiveType(Gpio.ACTIVE_LOW);
            mButtonGpio.setEdgeTriggerType(Gpio.EDGE_FALLING);
            mButtonGpio.registerGpioCallback(mCallback);
        } catch (IOException e) {
            Log.e(TAG, "Error en PeripheralIO API", e);
        }
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
        runnable = null;
        if (mButtonGpio != null) {
            mButtonGpio.unregisterGpioCallback(mCallback);
            try {
                mButtonGpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Error en PeripheralIO API", e);
            }
        }
    }

    private GpioCallback mCallback = new GpioCallback() {
        @Override
        public boolean onGpioEdge(Gpio gpio) {
            Log.i(TAG, "Botón pulsado!");
            if (buttonstatus == 0.0) buttonstatus = 1.0;
            else buttonstatus = 0.0;
            final Data boton = new Data();
            boton.setVariable(idBoton);
            boton.setValue(buttonstatus);
            ArrayList<Data> message = new ArrayList<Data>() {{
                add(boton);
            }};
            UbiClient.getClient().sendData(message, token);
            return true;
        }
    };

    private class UpdateRunner implements Runnable {
        @Override
        public void run() {
            readLDR();
            Log.i(TAG, "Ejecución de acción periódica");
            handler.postDelayed(this, 5000);
        }
    }

    private void readLDR() {
        Data iluminacion = new Data();
        ArrayList<Data> message = new ArrayList<Data>();
        Random rand = new Random();
        float valor = rand.nextFloat() * 5.0f;
        iluminacion.setVariable(idIluminacion);
        iluminacion.setValue((double) valor);
        message.add(iluminacion);
        UbiClient.getClient().sendData(message, token);
    }
}
