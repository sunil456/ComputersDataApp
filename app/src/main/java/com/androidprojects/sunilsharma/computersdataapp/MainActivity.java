package com.androidprojects.sunilsharma.computersdataapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidprojects.sunilsharma.computersdataapp.Model.Computer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    EditText editComputerName;
    EditText editComputerPower;
    EditText editComputerSpeed;
    EditText editComputerRam;
    EditText editComputerScreen;
    EditText editComputerKeyboard;
    EditText editComputerCPU;
    Button buttonSendOrUpdateData;

    TextView textComputerData;
    Button buttonGetDataFromServer;

    FirebaseDatabase firebaseDatabaseInstance;
    DatabaseReference databaseReference;

    String uniqueComputerID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editComputerName = (EditText) findViewById(R.id.editComputerName);
        editComputerPower = (EditText) findViewById(R.id.editComputerPower);
        editComputerSpeed = (EditText) findViewById(R.id.editComputerSpeed);
        editComputerRam = (EditText) findViewById(R.id.editComputerRam);
        editComputerScreen = (EditText) findViewById(R.id.editComputerScreen);
        editComputerKeyboard = (EditText) findViewById(R.id.editComputerKeyboard);
        editComputerCPU = (EditText) findViewById(R.id.editComputerCPU);

        buttonSendOrUpdateData = (Button) findViewById(R.id.buttonSendOrUpdateData);
        textComputerData = (TextView) findViewById(R.id.textComputerData);
        buttonGetDataFromServer = (Button) findViewById(R.id.buttonGetDataFromServer);
        changeTheTextOfSendOrUpdateButtonAccordingToTheSituation();

        firebaseDatabaseInstance = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabaseInstance.getReference("Computers");
        firebaseDatabaseInstance.getReference("APPLICATION_NAME").setValue("Computers Data");

        firebaseDatabaseInstance.getReference("APPLICATION_NAME")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        String applicationName = dataSnapshot.getValue(String.class);

                        getSupportActionBar().setTitle(applicationName);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        //Todo : Send Data To The Firebase.
        buttonSendOrUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String computerName = editComputerName.getText().toString();
                String computerPower = editComputerPower.getText().toString();
                String computerSpeed = editComputerSpeed.getText().toString();
                String computerRam = editComputerRam.getText().toString();
                String computerScreen = editComputerScreen.getText().toString();
                String computerKeyboard = editComputerKeyboard.getText().toString();
                String computerCPU = editComputerCPU.getText().toString();



                int computerPowerIntegerValue = 0;
                int computerSpeedIntegerValue = 0;
                int computerRamIntegerValue = 0;

                //Todo Converting From String to Integer
                try
                {
                    computerPowerIntegerValue = Integer.parseInt(computerPower);
                }
                catch (Exception e)
                {
                    Log.i("TAG" , e.getMessage());
                }

                try
                {
                    computerSpeedIntegerValue = Integer.parseInt(computerSpeed);
                }
                catch (Exception e)
                {
                    Log.i("TAG" , e.getMessage());
                }

                try
                {
                    computerRamIntegerValue = Integer.parseInt(computerRam);
                }
                catch (Exception e)
                {
                    Log.i("TAG" , e.getMessage());
                }



                if(TextUtils.isEmpty(uniqueComputerID))
                {
                    produceNewComputerAndPutItInTheDatabase(computerName ,  computerPowerIntegerValue ,
                                        computerSpeedIntegerValue ,
                                        computerRamIntegerValue ,
                                        computerScreen ,
                                        computerKeyboard ,
                                        computerCPU);


                    editComputerName.setText("");
                    editComputerPower.setText("");
                    editComputerSpeed.setText("");
                    editComputerRam.setText("");
                    editComputerScreen.setText("");
                    editComputerKeyboard.setText("");
                    editComputerCPU.setText("");
                }
                else
                {
                    modifyTheProducedComputer(computerName , computerPower ,
                            computerSpeed , computerRam ,
                            computerScreen ,computerKeyboard ,
                            computerCPU);
                }

            }
        });


        buttonGetDataFromServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(uniqueComputerID == null)
                {
                    return;
                }
                computerDataChangedListener();
            }
        });
    }


    private void produceNewComputerAndPutItInTheDatabase(String computerName ,
                                                         int computerPower ,
                                                         int computerSpeed ,
                                                         int computerRam ,
                                                         String computerScreen ,
                                                         String computerKeyboard ,
                                                         String computerCPU)
    {
        if(TextUtils.isEmpty(uniqueComputerID))
        {
            uniqueComputerID = databaseReference.push().getKey();
        }

        Computer computer = new Computer(computerName , computerPower , computerSpeed ,
                computerRam , computerScreen , computerKeyboard , computerCPU);

        databaseReference.child(uniqueComputerID).setValue(computer);

        changeTheTextOfSendOrUpdateButtonAccordingToTheSituation();
    }


    private void changeTheTextOfSendOrUpdateButtonAccordingToTheSituation()
    {
        if(TextUtils.isEmpty(uniqueComputerID))
        {
            buttonSendOrUpdateData.setText("Produce a New computer and Send it to Server");
        }
        else
        {
            buttonSendOrUpdateData.setText("Modify The Produced Computer Data");
        }
    }

    private void modifyTheProducedComputer(String computerName , String computerPower ,
                                           String computerSpeed , String computerRam ,
                                           String computerScreen , String computerKeyboard ,
                                           String computerCPU)
    {
        if(!TextUtils.isEmpty(computerName))
        {
            databaseReference.child(uniqueComputerID).child("computerName").setValue(computerName);
        }

        if(!TextUtils.isEmpty(computerPower))
        {
            try
            {
                int computerPowerIntValue = Integer.parseInt(computerPower);
                databaseReference.child(uniqueComputerID).child("computerPower").setValue(computerPowerIntValue);
            }
            catch (Exception i)
            {
                Log.i("TAG" , i.getMessage());
            }
        }

        if(!TextUtils.isEmpty(computerSpeed))
        {
            try
            {
                int computerSpeedIntValue = Integer.parseInt(computerSpeed);
                databaseReference.child(uniqueComputerID).child("computerSpeed").setValue(computerSpeedIntValue);
            }
            catch (Exception i)
            {
                Log.i("TAG" , i.getMessage());
            }
        }

        if(!TextUtils.isEmpty(computerRam))
        {
            try
            {
                int computerRamIntValue = Integer.parseInt(computerRam);
                databaseReference.child(uniqueComputerID).child("computerRam").setValue(computerRamIntValue);
            }
            catch (Exception i)
            {
                Log.i("TAG" , i.getMessage());
            }
        }

        if(!TextUtils.isEmpty(computerScreen))
        {
            databaseReference.child(uniqueComputerID).child("computerScreen").setValue(computerScreen);
        }

        if(!TextUtils.isEmpty(computerKeyboard))
        {
            databaseReference.child(uniqueComputerID).child("computerKeyboard").setValue(computerKeyboard);
        }

        if(!TextUtils.isEmpty(computerCPU))
        {
            databaseReference.child(uniqueComputerID).child("computerCPU").setValue(computerCPU);
        }
    }


    private void computerDataChangedListener()
    {
        databaseReference.child(uniqueComputerID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        Computer computer = dataSnapshot.getValue(Computer.class);

                        if(computer == null)
                        {
                            return;
                        }


                        textComputerData.setText("Computer Name: " + computer.getComputerName()
                                + " - " + "Computer Power: " + computer.getComputerPower()
                                + " - " + "Computer Speed: " + computer.getComputerSpeed()
                                + " - " + "Computer Ram: " + computer.getComputerRam()
                                + " - " + "Computer Screen: " + computer.getComputerScreen()
                                + " - " + "Computer Keyboard: " + computer.getComputerKeyboard()
                                + " - " + "Computer CPU: " + computer.getComputerCPU());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
