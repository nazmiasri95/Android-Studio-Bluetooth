package com.example.user.babypronedetector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    RadioGroup genderGroup;
    RadioButton genderRadio;
    Button startButton;

    private String name, gender;
    private final int REQUEST_ENABLE_BT = 1;
    String deviceHardwareAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(getApplicationContext(), "Your device does not support Bluetooth !", Toast.LENGTH_SHORT).show();
        } else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    deviceHardwareAddress = device.getAddress(); // MAC address
                    Toast.makeText(getApplicationContext(), "Connected to: " + deviceName + ", MAC: " + deviceHardwareAddress, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "There are no paired device. Please connect to the hardware.", Toast.LENGTH_SHORT).show();
            }
        }

        nameInput = (EditText) findViewById(R.id.nameInput);
        genderGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = genderGroup.getCheckedRadioButtonId();
                genderRadio = (RadioButton) findViewById(selectedId);

                name = nameInput.getText().toString();

                if (name.isEmpty() || selectedId < 0) {
                    Toast.makeText(getApplicationContext(), "Please don't leave it blank !", Toast.LENGTH_SHORT).show();
                } else {
                    gender = genderRadio.getText().toString();

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("gender", gender);
                    intent.putExtra("bluetooth", deviceHardwareAddress);
                    startActivity(intent);
                }
            }
        });

    }
}
