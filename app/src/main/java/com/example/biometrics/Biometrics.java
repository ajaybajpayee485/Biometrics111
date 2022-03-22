package com.example.biometrics;

import static android.hardware.biometrics.BiometricPrompt.*;

import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public abstract class Biometrics {
    private BiometricManager biometricManager;
    private Executor executor;
    public BiometricPrompt biometricPrompt = new androidx.biometric.BiometricPrompt();
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login")
            .setSubtitle("login using your fingerprint")
            .build();
    private Context context;

    protected Biometrics() {
    }

    @RequiresApi(api= Build.VERSION_CODES.Q)
    public boolean checkCompatibility(Context context){
        biometricManager= BiometricManager.from(context);
        if(biometricManager.canAuthenticate()==biometricManager.BIOMETRIC_SUCCESS){
            return true;
        }
        else {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void biometricPrompt(Context context){
        executor = ContextCompat.getMainExecutor(context);
        biometricPrompt=new Biometrics((FragmentActivity)context,executor,new AuthenticationCallback()){

            private AuthenticationResult result;

            @Override
            public void onAuthenticationSucceeded() {
                onAuthenticationSucceeded();
            }

            @Override
            public void onAuthenticationSucceeded(AuthenticationResult result){
                this.result = result;
                super.onAuthenticationSucceeded(result);

                Toast.makeText(context, "Authentication Success", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(context,DiaryActivity.class);
                context.startActivity(intent);
            }

        };

        biometricPrompt.authenticate(promptInfo);

    }

    public void onAuthenticationFailed(Context context){
        onAuthenticationFailed(context);
        Toast.makeText(context,
                "Authentication Failed",
                Toast.LENGTH_SHORT).show();



    }

    public void onAuthentication(int errorCode ,CharSequence errorString){
        onAuthenticationError(errorString,errorCode);
        Toast.makeText(context, "Authentication Error", Toast.LENGTH_SHORT).show();
    }

    public abstract void onAuthenticationSucceeded();

    public abstract void onAuthenticationSucceeded(AuthenticationResult result);
}
