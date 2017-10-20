package se.sics.sse.fresta.wirelessino;

import java.util.Random;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ClientCommunicator;
import com.CommunicationListener;
import com.Communicator;
import com.MopedDataType;
import com.MopedState;

import static com.MopedState.parseInt;

/**
 * This is the Main class which is the main activity for the application.
 * All the interactive GUI elements are defined here as well as their
 * corresponding methods and calculations.
 */

public class Main extends Activity implements CommunicationListener {

    private final static int CONNECTION_TIMEOUT = 2000;
    private final static int SEEKBAR_SNAP_SPEED = 2;

    private TextView velocityTextView;
    private TextView sensorTextView;

    private TextView velocityLabel;
    private TextView sensorLabel;

    private TextView AccPidTargetTextView;
    private TextView AccPidPTextView;
    private TextView AccPidITextView;
    private TextView AccPidDTextView;

    private TextView AccPidTargetLabel;
    private TextView AccPidPLabel;
    private TextView AccPidILabel;
    private TextView AccPidDLabel;


    private TextView latPidTargetTextView;
    private TextView latPidPTextView;
    private TextView latPidITextView;
    private TextView latPidDTextView;

    private TextView latPidTargetLabel;
    private TextView latPidPLabel;
    private TextView latPidILabel;
    private TextView latPidDLabel;



    private SeekBar speedBar;
    private SeekBar steeringBar;

    private Button debugToggle;
    private Button manualButton;
    private Button accButton;
    private Button platooningButton;

    private Button connectButton;
    private TextView turningTextView;
    private TextView speedTextView;


    private boolean isConnected = false;
    private boolean debug = false;

    private Communicator communicator;

    public static final String TAG = "WirelessIno";
    public static final boolean D = true; // The debug option

    private Menu menu = null;

    private static final int EXIT_INDEX = 0;        // Menu bar: exit
    private static final int DISCONNECT_INDEX = 1;    // Menu bar: disconnect
    private static final int CONFIG_INDEX = 2;    // Menu bar: WiFi configuration

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedBar = (SeekBar) findViewById(R.id.speedBar);
        steeringBar = (SeekBar) findViewById(R.id.SteeringBar);
        speedBar.setEnabled(false);
        steeringBar.setEnabled(false);


        /*Listener for change in SeekBars (Sliders) */

        /**
         * Listener for topBar
         */

        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            Thread speedBarThread;

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("ThreadCheck", "Bar Touched");
                if (speedBarThread != null) {
                    if (speedBarThread.isAlive()) {
                        speedBarThread.interrupt();
                        Log.e("ThreadCheck", "Thread Interrupted");
                    }
                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                speedBarThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (speedBar.getProgress() > 100) {
                            while (speedBar.getProgress() > 100 && !Thread.interrupted()) {
                                speedBar.setProgress(speedBar.getProgress() - 1);
                                try {
                                    Thread.sleep(SEEKBAR_SNAP_SPEED);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    break;
                                }
                            }
                        } else if (speedBar.getProgress() < 100) {
                            while (speedBar.getProgress() < 100 && !Thread.interrupted()) {
                                speedBar.setProgress(speedBar.getProgress() + 1);
                                try {
                                    Thread.sleep(SEEKBAR_SNAP_SPEED);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    break;
                                }
                            }
                        }
                    }
                });
                speedBarThread.start();
                Log.e("ThreadCheck", "Thread Started");
            }


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                transformPWM();
            }
        });

        /**
         * Listener for steeringBar
         */

        steeringBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            Thread SteeringBarThread;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                SteeringBarThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (steeringBar.getProgress() > 100) {
                            while (steeringBar.getProgress() > 100 && !Thread.interrupted()) {
                                steeringBar.setProgress(steeringBar.getProgress() - 1);
                                try {
                                    Thread.sleep(SEEKBAR_SNAP_SPEED);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    break;
                                }
                            }
                        } else if (steeringBar.getProgress() < 100) {
                            while (steeringBar.getProgress() < 100 && !Thread.interrupted()) {
                                steeringBar.setProgress(steeringBar.getProgress() + 1);
                                try {
                                    Thread.sleep(SEEKBAR_SNAP_SPEED);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    break;
                                }
                            }
                        }
                    }
                });

                SteeringBarThread.start();
                Log.e("ThreadCheck", "Thread Started");


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("ThreadCheck", "Bar Touched");
                if (SteeringBarThread != null) {
                    if (SteeringBarThread.isAlive()) {
                        SteeringBarThread.interrupt();
                        Log.e("ThreadCheck", "Thread Interrupted");
                    }
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                transformPWM();
            }
        });



        velocityTextView = (TextView) findViewById(R.id.velocityTextView);
        sensorTextView = (TextView) findViewById(R.id.sensorTextView);
        AccPidTargetTextView = (TextView) findViewById(R.id.pidTargetTextView);
        AccPidPTextView = (TextView) findViewById(R.id. pidPTextView);
        AccPidITextView = (TextView) findViewById(R.id.pidYTextView);
        AccPidDTextView = (TextView) findViewById(R.id.pidDTextView);


        latPidTargetTextView = (TextView) findViewById(R.id.LatPidTargetTextView);
        latPidPTextView = (TextView) findViewById(R.id. LatPidPTextView);
        latPidITextView = (TextView) findViewById(R.id.LatPidITextView);
        latPidDTextView = (TextView) findViewById(R.id.LatPidDTextView);

        velocityLabel = (TextView) findViewById(R.id.velocityLabel);
        sensorLabel = (TextView) findViewById(R.id.sensorLabel);
        AccPidTargetLabel = (TextView) findViewById(R.id.pidTargetLabel);
        AccPidPLabel = (TextView) findViewById(R.id. pidPLabel);
        AccPidILabel = (TextView) findViewById(R.id.pidYLabel);
        AccPidDLabel = (TextView) findViewById(R.id.pidDLabel);


        latPidTargetLabel = (TextView) findViewById(R.id.LatPidTargetLabel);
        latPidPLabel = (TextView) findViewById(R.id. LatPidPLabel);
        latPidILabel = (TextView) findViewById(R.id.LatPidYLabel);
        latPidDLabel = (TextView) findViewById(R.id.LatPidDLabel);



        AccPidTargetTextView.setVisibility(View.GONE);
        AccPidPTextView.setVisibility(View.GONE);
        AccPidITextView.setVisibility(View.GONE);
        AccPidDTextView.setVisibility(View.GONE);


        latPidTargetTextView.setVisibility(View.GONE);
        latPidPTextView.setVisibility(View.GONE);
        latPidITextView.setVisibility(View.GONE);
        latPidDTextView.setVisibility(View.GONE);

        AccPidTargetLabel.setVisibility(View.GONE);
        AccPidPLabel.setVisibility(View.GONE);
        AccPidILabel.setVisibility(View.GONE);
        AccPidDLabel.setVisibility(View.GONE);


        latPidTargetLabel.setVisibility(View.GONE);
        latPidPLabel.setVisibility(View.GONE);
        latPidILabel.setVisibility(View.GONE);
        latPidDLabel.setVisibility(View.GONE);


        manualButton = (Button) findViewById(R.id.manualButton);
        manualButton.setEnabled(false);
        debugToggle = (Button) findViewById(R.id.debugToggle);
        accButton = (Button) findViewById(R.id.accButton);
        accButton.setEnabled(false);
        platooningButton = (Button) findViewById(R.id.platooningButton);
        platooningButton.setEnabled(false);


        debugToggle.setBackgroundColor(Color.parseColor("#FF0000"));
        accButton.setBackgroundColor(Color.parseColor("#FF0000"));
        manualButton.setBackgroundColor(Color.parseColor("#FF0000"));
        platooningButton.setBackgroundColor(Color.parseColor("#FF0000"));

        turningTextView = (TextView) findViewById(R.id.turningTextView);
        speedTextView = (TextView) findViewById(R.id.speedTextView);

        turningTextView.setText(Integer.toString(0));
        speedTextView.setText(Integer.toString(0));

        connectButton = (Button) findViewById(R.id.serverButton);
        connectButton.setBackgroundColor(Color.parseColor("#7CFC00"));




        Toast.makeText(getApplicationContext(), getMoppeToast(), Toast.LENGTH_SHORT).show();


    }

    /**
     * Add a disconnect option when connected to a socket.
     */

    protected void onResume() {
        /* Disable the disconnect option if no connection has been established */
        if (communicator != null) {
            communicator.start();
        }
        updateMenuVisibility();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        if (communicator != null) {
            communicator.start();
        }
        super.onRestart();
    }

    @Override
    protected void onStop() {
        if (communicator != null) {
            communicator.stop();
        }
        super.onStop();
    }

    /**
     * Method to reach the options screen on "connect" button press
     *
     * @param view the current view, provided by the onClick method
     */

    public void goToOptions(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }


    public void serverConnect(View view) {
        Log.e("Buttontext", connectButton.getText().toString());
        if (connectButton.getText().toString().equals("Connect")) {
            SharedPreferences mSharedPreferences = getSharedPreferences("list", MODE_PRIVATE);
            final String ip = mSharedPreferences.getString("host", null);
            final int port = mSharedPreferences.getInt("port", 0);

            /* Create socket connection in a background task */


            if (ip == null || port == 0) {
                Toast.makeText(this, "Enter Ports and IP in settings", Toast.LENGTH_SHORT).show();
            } else {
                communicator = new ClientCommunicator(ip, port);
                communicator.addListener(this);
                communicator.start();
                Toast.makeText(this, "Waiting for Server Connection", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (communicator != null) {
                communicator.stop();
                Toast.makeText(this, "Connection stopped", Toast.LENGTH_SHORT).show();
                isConnected = false;
            }
            updateConnectButton();
        }
    }


    /**
     * Method which gets the data from the sliders and formats it in a way that an arduino will understand.
     * Uses the predefined send(Object message) method to notify the MOPED of a change in instructions.
     */

    int lastSpeed;
    int lastSteering;
    public void transformPWM() {
         /* Subtract 100 since the bar goes from 0 to 200 and we want values between -100 and 100 */
        int speed = speedBar.getProgress() - 100;
        int steering = steeringBar.getProgress() - 100;


        if (communicator != null) {
            if(speed != lastSpeed) {
                communicator.setValue(MopedDataType.THROTTLE, speed);
                speedTextView.setText(Integer.toString(speed));
                lastSpeed = speed;
            }
            if(steering != lastSteering) {
                communicator.setValue(MopedDataType.STEERING, steering);
                turningTextView.setText(Integer.toString(steering));
                lastSteering = steering;
            }
        }
    }


    /**
     * Adds options to the menu
     *
     * @param menu the menu which is being modified
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

		/* Add menu bars */
        menu.add(0, EXIT_INDEX, EXIT_INDEX, R.string.exit);
        menu.add(0, DISCONNECT_INDEX, DISCONNECT_INDEX, R.string.disconnect);
        menu.add(0, CONFIG_INDEX, CONFIG_INDEX, R.string.wifiConfig);

		/* To start with, disable the disconnect option if no connection has been established */
        updateMenuVisibility();

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle different menu options
     *
     * @param item the pressed menu item
     */

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EXIT_INDEX) {
            finish();
        } else if (item.getItemId() == DISCONNECT_INDEX) {
            menu.getItem(DISCONNECT_INDEX).setVisible(false); // Hide the disconnect option
        } else if (item.getItemId() == CONFIG_INDEX) {
            Intent i = new Intent(Main.this, Settings.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks if a socket connection has been established and updates
     * the visibility of the "disconnect" menu option accordingly.
     */

    private void updateMenuVisibility() {
        if (menu != null) {
            if (false)
                // TODO: 2017-09-27 Fix this false thing
                menu.getItem(DISCONNECT_INDEX).setVisible(false);
            else
                menu.getItem(DISCONNECT_INDEX).setVisible(true);
        }
    }

    /**
     * Crucial method to increase morale and safety while using the app
     *
     * @return returns an important warning message about safe driving to the user.
     */

    private String getMoppeToast() {
        String[] strs = new String[]{"Tänk på hur ni kör!", "Hallå moppepojkar, tänk på vad ni gör!", "Sen var de bara nio!"};
        return strs[new Random().nextInt(3)];
    }

    @Override
    public void onConnection() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Connected to server", Toast.LENGTH_SHORT).show();
                isConnected = true;
                accButton.setEnabled(true);
                platooningButton.setEnabled(true);
                manualButton.setEnabled(true);
                updateConnectButton();
            }
        });
    }


    @Override
    public void onDisconnection() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isConnected = false;
                accButton.setEnabled(false);
                platooningButton.setEnabled(false);
                manualButton.setEnabled(false);
                accButton.setBackgroundColor(Color.parseColor("#FF0000"));
                manualButton.setBackgroundColor(Color.parseColor("#FF0000"));
                platooningButton.setBackgroundColor(Color.parseColor("#FF0000"));
                updateConnectButton();
                Toast.makeText(getApplicationContext(), "Connection Lost", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onValueChanged(MopedDataType mopedDataType, int j) {
        final int i = j;
        switch (mopedDataType) {
            case MOPED_STATE:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onStateChange(parseInt(i));
                    }
                });

                break;
            case VELOCITY:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        velocityTextView.setText(String.valueOf(i));
                    }
                });

                break;
            case SENSOR_DISTANCE:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sensorTextView.setText(String.valueOf(i));
                    }
                });

                break;
            case ACC_TARGET_VALUE:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AccPidTargetTextView.setText(String.valueOf(i));
                    }
                });

                break;
            case ACC_P_CONSTANT:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AccPidPTextView.setText(String.valueOf(i));
                    }
                });

                break;
            case ACC_I_CONSTANT:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AccPidITextView.setText(String.valueOf(i));
                    }
                });

                break;
            case ACC_D_CONSTANT:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AccPidDTextView.setText(String.valueOf(i));
                    }
                });

                break;
            case ACC_INTEGRAL_SUM:

                break;
            case LAT_TARGET_VALUE:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        latPidTargetTextView.setText(String.valueOf(i));
                    }
                });

                break;
            case LAT_P_CONSTANT:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        latPidPTextView.setText(String.valueOf(i));
                    }
                });

                break;
            case LAT_I_CONSTANT:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        latPidITextView.setText(String.valueOf(i));
                    }
                });

                break;
            case LAT_D_CONSTANT:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        latPidDTextView.setText(String.valueOf(i));
                    }
                });

                break;
            case LAT_INTEGRAL_SUM:

                break;

            case THROTTLE:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        speedBar.setProgress(i + 100);
                        speedTextView.setText(String.valueOf(i));
                    }
                });

                break;
            case STEERING:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        steeringBar.setProgress(i + 100);
                        turningTextView.setText(String.valueOf(i));
                    }
                });

                break;
            case CUSTOM_1:
                break;
            case CUSTOM_2:
                break;
            case CUSTOM_3:
                break;
        }
    }


    @Override
    public void onStateChange(MopedState mopedState) {
        switch(mopedState){
            case ACC:
                accButtonEnable();
                break;
            case PLATOONING:
                platooningButtonEnable();
                break;
            case MANUAL:
                manualButtonEnable();
                break;
        }
    }

    public void accButtonPress(View view){
        accButtonEnable();
   }

    private void accButtonEnable(){
        speedBar.setEnabled(false);
        steeringBar.setEnabled(true);
        speedBar.setProgress(100);
        steeringBar.setProgress(100);
        accButton.setBackgroundColor(Color.parseColor("#7CFC00"));
        manualButton.setBackgroundColor(Color.parseColor("#FF0000"));
        platooningButton.setBackgroundColor(Color.parseColor("#FF0000"));
        communicator.setState(MopedState.ACC);
    }

    public void platooningButtonPress(View view){
        platooningButtonEnable();
    }

    public void toggleDebug(View view){
if(!debug) {
    AccPidTargetTextView.setVisibility(View.VISIBLE);
    AccPidPTextView.setVisibility(View.VISIBLE);
   AccPidITextView.setVisibility(View.VISIBLE);
    AccPidDTextView.setVisibility(View.VISIBLE);


   latPidTargetTextView.setVisibility(View.VISIBLE);
    latPidPTextView.setVisibility(View.VISIBLE);
   latPidITextView.setVisibility(View.VISIBLE);
    latPidDTextView.setVisibility(View.VISIBLE);

    AccPidTargetLabel.setVisibility(View.VISIBLE);
    AccPidPLabel.setVisibility(View.VISIBLE);
    AccPidILabel.setVisibility(View.VISIBLE);
    AccPidDLabel.setVisibility(View.VISIBLE);


    latPidTargetTextView.setVisibility(View.VISIBLE);
    latPidPTextView.setVisibility(View.VISIBLE);
    latPidITextView.setVisibility(View.VISIBLE);
    latPidDTextView.setVisibility(View.VISIBLE);

    AccPidTargetLabel.setVisibility(View.VISIBLE);
    AccPidPLabel.setVisibility(View.VISIBLE);
    AccPidILabel.setVisibility(View.VISIBLE);
    AccPidDLabel.setVisibility(View.VISIBLE);


    latPidTargetLabel.setVisibility(View.VISIBLE);
    latPidPLabel.setVisibility(View.VISIBLE);
    latPidILabel.setVisibility(View.VISIBLE);
    latPidDLabel.setVisibility(View.VISIBLE);


    debug = true;
    debugToggle.setBackgroundColor(Color.parseColor("#7CFC00"));
}
else{

    AccPidTargetTextView.setVisibility(View.GONE);
    AccPidPTextView.setVisibility(View.GONE);
    AccPidITextView.setVisibility(View.GONE);
    AccPidDTextView.setVisibility(View.GONE);


    latPidTargetTextView.setVisibility(View.GONE);
    latPidPTextView.setVisibility(View.GONE);
    latPidITextView.setVisibility(View.GONE);
    latPidDTextView.setVisibility(View.GONE);

    AccPidTargetLabel.setVisibility(View.GONE);
    AccPidPLabel.setVisibility(View.GONE);
    AccPidILabel.setVisibility(View.GONE);
    AccPidDLabel.setVisibility(View.GONE);


    latPidTargetLabel.setVisibility(View.GONE);
    latPidPLabel.setVisibility(View.GONE);
    latPidILabel.setVisibility(View.GONE);
    latPidDLabel.setVisibility(View.GONE);


    debug = false;
    debugToggle.setBackgroundColor(Color.parseColor("#FF0000"));
}

    }

    private void platooningButtonEnable(){
        speedBar.setEnabled(false);
        steeringBar.setEnabled(false);
        speedBar.setProgress(100);
        steeringBar.setProgress(100);
        accButton.setBackgroundColor(Color.parseColor("#FF0000"));
        manualButton.setBackgroundColor(Color.parseColor("#FF0000"));
        platooningButton.setBackgroundColor(Color.parseColor("#7CFC00"));
        communicator.setState(MopedState.PLATOONING);
    }

    public void manualButtonPress(View view){
        manualButtonEnable();
    }

    private void manualButtonEnable(){
        speedBar.setEnabled(true);
        steeringBar.setEnabled(true);
        speedBar.setProgress(100);
        steeringBar.setProgress(100);
        accButton.setBackgroundColor(Color.parseColor("#FF0000"));
        manualButton.setBackgroundColor(Color.parseColor("#7CFC00"));
        platooningButton.setBackgroundColor(Color.parseColor("#FF0000"));
        communicator.setState(MopedState.MANUAL);
    }


    private void updateConnectButton() {
        if (isConnected) {
            connectButton.setText("Disconnect");
            connectButton.setBackgroundColor(Color.parseColor("#FF0000"));
        } else {
            connectButton.setText("Connect");
            connectButton.setBackgroundColor(Color.parseColor("#7CFC00"));
        }
    }
}
