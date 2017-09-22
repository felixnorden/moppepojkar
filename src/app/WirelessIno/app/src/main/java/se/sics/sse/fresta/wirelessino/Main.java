package se.sics.sse.fresta.wirelessino;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
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

public class Main extends Activity implements CommunicationListener{


    private SeekBar speedBar;
    private SeekBar steeringBar;
    private Button modeButton;
    private boolean isACCenabled;

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

        communicator = new ClientCommunicator("192.168.137.1", 9000);
        communicator.addListener(this);
        communicator.start();

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

        modeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isACCenabled) {
                    modeButton.setText("ACC enabled");
                } else {
                    modeButton.setText("ACC disabled");
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
        updateMenuVisibility();
        super.onResume();
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
        System.out.println("Connected");
    }

    @Override
    public void onStateChange(MopedState mopedState) {
        System.out.println("New state: " + mopedState.name());
    }

    @Override
    public void onDisconnection() {

    }
}
