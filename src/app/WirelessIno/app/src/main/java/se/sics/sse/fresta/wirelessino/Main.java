package se.sics.sse.fresta.wirelessino;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

public class Main extends Activity {

    /*Listener for change in SeekBars (Sliders) */


    private SeekBar topBar;
    private SeekBar bottomBar;


	public static final String TAG = "WirelessIno";
	public static Socket socket = null;
	public static final boolean D = true; // The debug option
	
	private static PrintWriter out = null;
	private Menu menu = null;
	
	private static final int EXIT_INDEX = 0;		// Menu bar: exit  
	private static final int DISCONNECT_INDEX = 1;	// Menu bar: disconnect
	private static final int CONFIG_INDEX = 2	;	// Menu bar: WiFi configuration

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        topBar = (SeekBar) findViewById(R.id.TopBar);
        bottomBar = (SeekBar) findViewById(R.id.BottomBar);



        topBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /* The value 100 turns into "0" in calculations */
                topBar.setProgress(100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                transformPWM();
            }
        });

        bottomBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /* The value 100 turns into "0" in calculations */
                bottomBar.setProgress(100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                transformPWM();
            }
        });




	}
	
	/* 
	 * Add a disconnect option when connected to a socket. 
	 */
	protected void onResume() {
		/* Disable the disconnect option if no connection has been established */
		updateMenuVisibility();
		super.onResume();
	}

	public void goToOptions (View view){
		Intent intent = new Intent (this, SocketConnector.class);
		startActivity(intent);
	}

    public void transformPWM(){
		/*Here the values are formatted in a way that the arduino will understand */
		String textTop = "";
		String textBottom = "";
		if(topBar.getProgress()-100 > -1){
			textTop = "V0";
		}
		else{
			textTop = "V-";
		}
		if(bottomBar.getProgress()-100 > -1){
			textBottom = "H0";
		}
		else{
			textBottom = "H-";
		}


        /* Subtract 100 since the bar goes from 0 to 200 and we want values between -100 and 100 */
        if(topBar.getProgress()-100 < 10 && topBar.getProgress()-100 > -10){

				textTop += "00";



		}
		else if(topBar.getProgress()-100 < 100 && topBar.getProgress()-100 > -100){
			textTop += "0";
		}

		if(bottomBar.getProgress()-100 < 10 && bottomBar.getProgress()-100 > -10){
			textBottom += "00";
		}
		else if(bottomBar.getProgress()-100 < 100 && bottomBar.getProgress()-100 > -100){
			textBottom += "0";
		}
        textTop += Integer.toString(Math.abs(topBar.getProgress()-100));
        textBottom += Integer.toString(Math.abs(bottomBar.getProgress()-100));
        String out = textTop +  textBottom;
		Log.e("Before Socket", out);
		/* Send speed and steering values through the socket */
        if (socket != null) {
			send(out);
			Log.e("Test After Socket if", out);
		}
    }
	
	/* 
	 * Add menu options 
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

	/* 
	 * Handle different menu options
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == EXIT_INDEX) {
			finish();
		} 
		else if (item.getItemId() == DISCONNECT_INDEX) {
			try {
				if (socket != null) {
					socket.close();
					socket = null;
					
					menu.getItem(DISCONNECT_INDEX).setVisible(false); // Hide the disconnect option
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		else if (item.getItemId() == CONFIG_INDEX) {
			Intent i = new Intent(Main.this, SocketConnector.class);
			startActivity(i);
		}
		
		return super.onOptionsItemSelected(item);
	}

	/* 
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

	/*
	 * Send a message through the socket.
	 */
	public static void send(Object message) {
		out.println(message);
	}
	
	/*
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
}
