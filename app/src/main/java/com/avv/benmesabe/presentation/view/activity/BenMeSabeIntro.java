package com.avv.benmesabe.presentation.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.avv.benmesabe.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by angelvazquez on 8/11/15.
 */
public class BenMeSabeIntro extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {

        // Add your slide's fragments here
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(AppIntroFragment.newInstance("Carta", "Descubre nuestras sugerencias", R.drawable.sugerencias, R.color.primary));
        addSlide(AppIntroFragment.newInstance("Escanea", "Puedes también escanear la carta mediante QR o NFC", R.drawable.escanea, R.color.primary));
        addSlide(AppIntroFragment.newInstance("Pide", "Elabora tu pedido", R.drawable.pedido, R.color.primary));
        //addSlide(AppIntroFragment.newInstance("Title4", "Description4", R.drawable.qr_rounded, R.color.primary));

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest
        //addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));

        // OPTIONAL METHODS
        // Override bar/separator color
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button
        showSkipButton(false);
        showDoneButton(true);
        setDoneText("Listo");

        // Turn vibration on and set intensity
        // NOTE: you will probably need to ask VIBRATE permesssion in Manifest
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        startActivity(new Intent(this, BarcodeReaderActivity.class));
    }

}
