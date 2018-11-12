package com.example.dgoodell.doomsday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.support.design.widget.Snackbar;


public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutContent;
    private BoardView gameView;
    private Switch mySwitch;
    private TextView switchStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);
        gameView = (BoardView) findViewById(R.id.gameView);

        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.restartGame();
            }
        });

        // Switch code used here and in activity_main xml, taken from:
        // http://www.mysamplecode.com/2013/04/android-switch-button-example.html
        switchStatus = (TextView) findViewById(R.id.switchStatus);
        mySwitch = (Switch) findViewById(R.id.mySwitch);

        //set the switch to OFF
        mySwitch.setChecked(false);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    switchStatus.setText(R.string.flagStatusON);
                    gameView.flagModeON();
                }else{
                    switchStatus.setText(R.string.flagStatusOFF);
                    gameView.flagModeOFF();
                }
            }
        });

        //check the current state before we display the screen
        if(mySwitch.isChecked()){
            switchStatus.setText(R.string.flagStatusON);
        }
        else {
            switchStatus.setText(R.string.flagStatusOFF);
        }
    }

    public void showSimpleSnackbarMessage(String message) {
        Snackbar.make(layoutContent, message, Snackbar.LENGTH_LONG).show();
    }
}
