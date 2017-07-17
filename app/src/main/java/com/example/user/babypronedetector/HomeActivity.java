package com.example.user.babypronedetector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    private String name, gender, bluetoothMac;
    private static final String UUID_SERIAL_PORT_PROFILE = "00001101-0000-1000-8000-00805F9B34FB";

    TextView nameText, genderText;
    TextView bpmText, stableText;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice btDevice;
    Handler mHandler;

    // Member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        name = b.getString("name");
        gender = b.getString("gender");
        bluetoothMac = b.getString("bluetooth");

        // Initialize the bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Connect to bluetooth server
        btDevice = mBluetoothAdapter.getRemoteDevice(bluetoothMac);

        

        nameText = (TextView) findViewById(R.id.displayName);
        genderText = (TextView) findViewById(R.id.displayGender);
        bpmText = (TextView) findViewById(R.id.bpm_left);
        stableText = (TextView) findViewById(R.id.stable_right);

        nameText.setText("Name: " + name);
        genderText.setText("Gender: " + gender);
    }
}
