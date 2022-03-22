package com.example.biometrics;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity<BiometricAct> extends AppCompatActivity {

    BiometricAct biometricAct = new BiometricAct();
    Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_login=findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricAct.biometricPrompt(context:MainActivity.this);
            }
        });

        if(biometricAct.checkCompatibility(context:this)==true){

        }else
        {
            alertDialog();
        }

    }

    private void alertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(context:MainActivity.this);
        builder.setTitle("FingerprintDiary");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Your device doesn't support fingerprint feature ").setCancelable(false).setMessage()
                .setPositiveButton(text:"Exit",(dialog,id)-> finish());
        AlertDialog alert=builder.create();
        alert.show();



    }
}