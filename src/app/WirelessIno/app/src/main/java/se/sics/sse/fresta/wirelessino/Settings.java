package se.sics.sse.fresta.wirelessino;

import java.net.Socket;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
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

public class Settings extends Activity {

	
	private EditText ed_host = null;
	private EditText ed_portmoped = null;
	private EditText ed_port = null;
	private Button   btn_connect = null;

	protected void onCreate(Bundle savedInstanceState) {
	    
		super.onCreate(savedInstanceState);


      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);

		setContentView(R.layout.socket_connection_build);
		ed_host = (EditText) findViewById(R.id.ed_host);
		ed_port = (EditText) findViewById(R.id.ed_port);
		btn_connect = (Button) findViewById(R.id.btn_connect);
		
		/* Fetch earlier defined host ip and port numbers and write them as default 
		 * (to avoid retyping this, typically standard, info) */
		SharedPreferences mSharedPreferences = getSharedPreferences("list",MODE_PRIVATE);
		String oldHost = mSharedPreferences.getString("host", null); 
		if (oldHost != null)
			ed_host.setText(oldHost);
		int oldPortCommunicator = mSharedPreferences.getInt("port",0);
		if (oldPortCommunicator != 0)
			ed_port.setText(Integer.toString(oldPortCommunicator));
		


	}

	/** Setup the "connect"-button. On click, new host ip and port numbers should
	 * be stored and a socket connection created (this is done as a background task).
	 */

	public void goToMain(View view){
		String str_host = ed_host.getText().toString().trim();
		String str_port = ed_port.getText().toString().trim();

		SharedPreferences mSharedPreferences = getSharedPreferences("list", MODE_PRIVATE);
		mSharedPreferences.edit().putString("host",str_host).apply();
		mSharedPreferences.edit().putInt("port",Integer.parseInt(str_port)).apply();

		Intent intent = new Intent(this, Main.class);
		startActivity(intent);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}