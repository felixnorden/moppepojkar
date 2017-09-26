package se.sics.sse.fresta.wirelessino;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import android.content.SharedPreferences;
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
import com.MopedState;

/**
 * This is the Main class which is the main activity for the application.
 * All the interactive GUI elements are defined here as well as their
 * corresponding methods and calculations.
 */

public class Main extends Activity implements CommunicationListener {


    private SeekBar speedBar;
    private SeekBar steeringBar;
    private Button modeButton;
    private Button connectButton;
    private TextView turningTextView;
    private TextView speedTextView;

    private boolean isACCenabled = false;
    private boolean isConnected = false;

    private Communicator communicator;

    public static final String TAG = "WirelessIno";
    public static Socket socket = null;
    public static final boolean D = true; // The debug option

    private static PrintWriter out = null;
    private Menu menu = null;

    private static final int EXIT_INDEX = 0;        // Menu bar: exit
    private static final int DISCONNECT_INDEX = 1;    // Menu bar: disconnect
    private static final int CONFIG_INDEX = 2;    // Menu bar: WiFi configuration

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedBar = (SeekBar) findViewById(R.id.speedBar);
        steeringBar = (SeekBar) findViewById(R.id.SteeringBar);


        /*Listener for change in SeekBars (Sliders) */

        /**
         * Listener for topBar
         */

        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /* The value 100 turns into "0" in calculations */
                speedBar.setProgress(100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
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

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /* The value 100 turns into "0" in calculations */
                steeringBar.setProgress(100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                transformPWM();
            }
        });

        modeButton = (Button) findViewById(R.id.modeButton);
        turningTextView = (TextView) findViewById(R.id.turningTextView);
        speedTextView = (TextView) findViewById(R.id.speedTextView);

        turningTextView.setText(Integer.toString(0));
        speedTextView.setText(Integer.toString(0));

        connectButton = (Button) findViewById(R.id.serverButton);

        modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isACCenabled) {
                    modeButton.setText("ACC");
                    communicator.setState(MopedState.ACC);
                    isACCenabled = !isACCenabled;
                } else {
                    modeButton.setText("MANUAL");
                    communicator.setState(MopedState.MANUAL);
                    isACCenabled = !isACCenabled;
                }

            }
        });


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
        Intent intent = new Intent(this, SocketConnector.class);
        startActivity(intent);
    }


    public void serverConnect(View view) {
        Log.e("Buttontext", connectButton.getText().toString());
        if (connectButton.getText().toString().equals("Connect")) {
            SharedPreferences mSharedPreferences = getSharedPreferences("list", MODE_PRIVATE);
            String ip = mSharedPreferences.getString("host", null);
            int port = mSharedPreferences.getInt("portcommunicator", 0);
            if (ip == null || port == 0) {
                Toast.makeText(this, "Enter Port and IP in settings", Toast.LENGTH_SHORT).show();
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
            }
            updateModeButton();
        }



    }


    /**
     * Method which gets the data from the sliders and formats it in a way that an arduino will understand.
     * Uses the predefined send(Object message) method to notify the MOPED of a change in instructions.
     */

    public void transformPWM() {
        /*Here the values are formatted in a way that the arduino will understand */
        String textTop = "V";
        String textBottom = "H";

         /* Subtract 100 since the bar goes from 0 to 200 and we want values between -100 and 100 */
        int topValue = speedBar.getProgress() - 100;
        int bottomValue = steeringBar.getProgress() - 100;

        turningTextView.setText(Integer.toString(topValue));
        speedTextView.setText(Integer.toString(bottomValue));

        /*Check if the value is negative and add prefix*/
        if (topValue > -1) {
            textTop += "0";
        } else {
            textTop += "-";
        }
        if (bottomValue > -1) {
            textBottom += "0";
        } else {
            textBottom += "-";
        }


        if (topValue < 10 && topValue > -10) {
            textTop += "00";
        } else if (topValue < 100 && topValue > -100) {
            textTop += "0";
        }

        if (bottomValue < 10 && bottomValue > -10) {
            textBottom += "00";
        } else if (bottomValue < 100 && bottomValue > -100) {
            textBottom += "0";
        }
        textTop += Integer.toString(Math.abs(topValue));
        textBottom += Integer.toString(Math.abs(bottomValue));
        String out = textTop + textBottom;
        Log.e("Before Socket", out);
        /* Send speed and steering values through the socket */
        if (socket != null) {
            send(out);
            Log.e("Test After Socket if", out);
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
            try {
                if (socket != null) {
                    socket.close();
                    socket = null;

                    menu.getItem(DISCONNECT_INDEX).setVisible(false); // Hide the disconnect option
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (item.getItemId() == CONFIG_INDEX) {
            Intent i = new Intent(Main.this, SocketConnector.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize the output stream for the socket.
     */

    public static void init(Socket socket) {

        Main.socket = socket;


        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message through the socket.
     *
     * @param message the message that will be sent to the MOPED
     */

    public static void send(Object message) {
        out.println(message);
    }

    /**
     * Checks if a socket connection has been established and updates
     * the visibility of the "disconnect" menu option accordingly.
     */

    private void updateMenuVisibility() {
        if (menu != null) {
            if (socket == null || !socket.isConnected())
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
                updateModeButton();
            }
        });
    }

    @Override
    public void onStateChange(final MopedState mopedState) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "New state: " + mopedState.name(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDisconnection() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isConnected = false;
                updateModeButton();
                Toast.makeText(getApplicationContext(), "Connection Lost", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateModeButton() {
        if (isConnected) {
            connectButton.setText("Disconnect");
        } else {
            connectButton.setText("Connect");
        }
    }


}
