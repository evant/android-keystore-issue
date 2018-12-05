package com.example.keystoreissue;

import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    generateKey();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static KeyPair generateKey() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
        generator.initialize(new KeyGenParameterSpec.Builder("alias", KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setKeySize(4096)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                .setUserAuthenticationRequired(true)
                .build());
        KeyPair keyPair = generator.generateKeyPair();
        Log.d("TAG", "keyPair: " + keyPair);
        return keyPair;
    }
}
