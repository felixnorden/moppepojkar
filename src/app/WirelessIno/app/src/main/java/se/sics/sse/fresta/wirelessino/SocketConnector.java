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
    private final static int CONNECTION_TIMEOUT = 3000;
	
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
		String oldPortCommunicator = mSharedPreferences.getString("portcommunicator",null);
		if (oldPortCommunicator != null)
			ed_portmoped.setText(oldPortCommunicator);
		
		/** Setup the "connect"-button. On click, new host ip and port numbers should
		 * be stored and a socket connection created (this is done as a background task). 
		 */
		btn_connect.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String str_host = ed_host.getText().toString().trim();
				String str_port1 = ed_portmoped.getText().toString().trim();
				String str_port2 = ed_portcommunicator.getText().toString().trim();
				
				SharedPreferences mSharedPreferences = getSharedPreferences("list", MODE_PRIVATE);
				mSharedPreferences.edit().putString("host",str_host).commit();
				mSharedPreferences.edit().putString("portmoped",str_port1).commit();
				mSharedPreferences.edit().putString("portcommunicator",str_port2).commit();
				
				/* Create socket connection in a background task */
				new AsyncConnectionTask().execute(str_host, str_port1, str_port2);
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
	private class AsyncConnectionTask extends AsyncTask<String, Void, String> {
		private String msg = "def message";

		/**
		 * Establish a socket connection in the background.
		 *
		 * @param params
		 * @return
		 */
		protected String doInBackground(String... params) {
			try {
				msg = params[0] + ":" + params[1];

				/* Close any previously used socket 
				 * (for example to prevent double-clicks leading to multiple connections) */
				if (socket != null && !socket.isClosed())
					socket.close();

				communicator = new ClientCommunicator(params[0], Integer.parseInt(params[2]));

				socket = new Socket();
				socket.connect(new InetSocketAddress(params[0],						// host ip 
													 Integer.parseInt(params[1])), 	// port 
							   CONNECTION_TIMEOUT);
			} catch (NumberFormatException e) {
				msg = "Invalid port value (" + params[1] + "), type an integer between 0 and 65535";
			} catch (IllegalArgumentException e) { 
				msg = "Invalid port value (" + params[1] + "), type an integer between 0 and 65535";
			} catch (Exception e) {
				msg = e.getMessage();
			}
			
			return null;
		}

		/**
		 *
		 * Once the background operation is finished, pass the socket reference to the
		 * Main class and exit from this view. If something went wrong, notify the user.
		 *
		 * @param result
		 */
		protected void onPostExecute(String result) {
			if (socket != null && socket.isConnected()) {
				Main.init(socket, communicator);
				finish();
			}
			else {
				new AlertDialog.Builder(SocketConnector.this)
				.setTitle("notification")
				.setMessage(msg + " " + result)
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.show();
			}
		}
	}
	
}