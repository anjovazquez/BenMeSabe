package com.avv.benmesabe.presentation.view.activity;

import android.content.Intent;

import com.avv.benmesabe.R;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Created by angelvazquez on 8/11/15.
 */
public class BenMeSabeSplash extends AwesomeSplash {

    public static final String TEST_LOGO = "M 222.00,24.96\r\n           C 236.46,25.59 250.04,27.60 264.00,31.58\r\n             331.31,50.78 382.13,105.25 395.80,174.00\r\n             395.80,174.00 398.96,194.00 398.96,194.00\r\n             398.96,194.00 398.96,203.00 398.96,203.00\r\n             399.35,206.87 400.15,208.71 399.91,213.00\r\n             398.67,234.87 395.53,256.31 387.95,277.00\r\n             370.48,324.64 333.51,363.22 287.00,383.28\r\n             268.94,391.07 249.59,395.96 230.00,397.96\r\n             230.00,397.96 221.00,397.96 221.00,397.96\r\n             217.13,398.35 215.29,399.15 211.00,398.91\r\n             162.42,396.16 122.75,383.03 86.00,349.83\r\n             52.98,320.01 32.10,278.00 26.85,234.00\r\n             26.85,234.00 25.09,210.00 25.09,210.00\r\n             27.84,161.42 40.97,121.75 74.17,85.00\r\n             98.36,58.22 132.86,37.96 168.00,29.65\r\n             176.81,27.57 185.99,26.03 195.00,25.17\r\n             195.00,25.17 208.00,24.42 208.00,24.42\r\n             215.67,23.50 215.44,24.68 222.00,24.96 Z\r\n           M 229.00,64.44\r\n           C 217.09,66.31 204.02,71.71 194.00,78.36\r\n             189.45,81.38 182.99,86.39 179.00,90.09\r\n             176.12,92.76 172.65,96.67 169.00,98.09\r\n             164.33,100.03 158.81,98.20 154.00,98.09\r\n             149.65,97.90 137.30,99.55 133.00,100.74\r\n             104.13,108.75 83.94,133.72 79.87,163.00\r\n             79.13,168.33 78.63,171.50 79.28,177.00\r\n             81.50,195.51 86.80,210.29 100.04,224.00\r\n             104.21,228.32 108.88,232.23 114.00,235.38\r\n             116.36,236.84 121.62,239.26 122.98,241.39\r\n             124.22,243.34 124.00,246.73 124.00,249.00\r\n             124.00,249.00 124.00,328.00 124.00,328.00\r\n             124.00,328.00 124.72,348.00 124.72,348.00\r\n             125.03,350.55 124.83,354.35 126.74,356.26\r\n             128.92,358.45 143.38,358.99 147.00,359.00\r\n             147.00,359.00 266.00,359.00 266.00,359.00\r\n             269.29,358.96 281.31,358.27 283.41,356.26\r\n             285.95,353.81 285.99,345.45 286.00,342.00\r\n             286.00,342.00 286.00,259.00 286.00,259.00\r\n             286.00,256.73 285.78,253.34 287.02,251.39\r\n             287.02,251.39 306.00,239.10 306.00,239.10\r\n             318.36,229.76 327.96,217.92 334.74,204.00\r\n             346.98,178.89 348.03,148.89 337.55,123.00\r\n             322.10,84.87 280.99,59.47 240.00,63.28\r\n             240.00,63.28 229.00,64.44 229.00,64.44 Z";

    @Override
    public void initSplash(ConfigSplash configSplash) {

        /* you don't have to override every property */

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.primary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1200); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_TOP); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        //configSplash.setLogoSplash(R.drawable.icon_white); //or any other drawable
        //configSplash.setAnimLogoSplashDuration(1500); //int ms
        //configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
        configSplash.setPathSplash(TEST_LOGO); //set path String
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(1500);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.gray); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(1500);
        configSplash.setPathSplashFillColor(R.color.white); //path object filling color


        //Customize Title
        configSplash.setTitleSplash("Ben Me Sabe");
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.BounceInDown);
        //configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located in assets/fonts/

    }

    @Override
    public void animationsFinished() {

        //transit to another activity here
        //or do whatever you want

        startActivity(new Intent(this, BarcodeReaderActivity.class));
    }
}
