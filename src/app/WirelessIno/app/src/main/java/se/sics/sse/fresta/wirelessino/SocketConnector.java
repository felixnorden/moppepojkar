package se.sics.sse.fresta.wirelessino;

import java.net.InetSocketAddress;
import java.net.Socket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.StrictMode;

import com.ClientCommunicator;

/**
 * This is the activity for connecting to the MOPED where the user has
 * to input an IP adress and a port to connect to, it also contains
 * instructions on how to do this properly.
 */

public class SocketConnector extends Activity {

	
	private EditText ed_host = null;
	private EditText ed_portmoped = null;
	private EditText ed_portcommunicator = null;
	private Button   btn_connect = null;
	private Socket   socket = null;
	private ClientCommunicator communicator = null;

	protected void onCreate(Bundle savedInstanceState) {
	    
		super.onCreate(savedInstanceState);


      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);

		setContentView(R.layout.socket_connection_build);
		ed_host = (EditText) findViewById(R.id.ed_host);
		ed_portmoped = (EditText) findViewById(R.id.ed_portmoped);
		ed_portcommunicator = (EditText) findViewById(R.id.ed_portcommunicator);
		btn_connect = (Button) findViewById(R.id.btn_connect);
		
		/* Fetch earlier defined host ip and port numbers and write them as default 
		 * (to avoid retyping this, typically standard, info) */
		SharedPreferences mSharedPreferences = getSharedPreferences("list",MODE_PRIVATE);
		String oldHost = mSharedPreferences.getString("host", null); 
		if (oldHost != null)
			ed_host.setText(oldHost);
		String oldPortMoped = mSharedPreferences.getString("portmoped",null);
		if (oldPortMoped != null)
			ed_portmoped.setText(oldPortMoped);
		int oldPortCommunicator = mSharedPreferences.getInt("portcommunicator",0);
		if (oldPortCommunicator != 0)
			ed_portcommunicator.setText(Integer.toString(oldPortCommunicator));
		
		/** Setup the "connect"-button. On click, new host ip and port numbers should
		 * be stored and a socket connection created (this is done as a background task). 
		 */
		btn_connect.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String str_host = ed_host.getText().toString().trim();
				String str_port1 = ed_portmoped.getText().toString().trim();
				String str_port2 = ed_portcommunicator.getText().toString().trim();
				
				SharedPreferences mSharedPreferences = getSharedPreferences("list", MODE_PRIVATE);
				mSharedPreferences.edit().putString("host",str_host).apply();
				mSharedPreferences.edit().putString("portmoped",str_port1).apply();
				mSharedPreferences.edit().putInt("portcommunicator",Integer.parseInt(str_port2)).apply();
				

			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public Socket getSocket() {
		return socket;
	}

	/**
	 * This asynchronous task is responsible for creating a socket connection
	 * (typically such tasks should not be done from the UI-thread, otherwise a
	 * NetworkOnMainThreadException exception may be thrown by the Android runtime tools).
	 */

	
}