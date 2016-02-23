package info.devexchanges.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private NetworkBroadcastReceiver broadcastReceiver;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text_view);
        broadcastReceiver = new NetworkBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter());
    }

    public void updateNetworkState(String state) {
        textView.setText(textView.getText() + " " + state);
        Log.i("Main", "update");
    }

    public class NetworkBroadcastReceiver extends BroadcastReceiver {

        public NetworkBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            updateNetworkState("DISCONNECTED");
        } else if (activeNetwork.isConnected()){
            updateNetworkState("CONNECTED");
        } else if (activeNetwork.isConnectedOrConnecting()) {
            updateNetworkState("CONNECTING");
        }
        }
    }
}
