package com.avv.benmesabe;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.avv.benmesabe.picasso.CircleTransform;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;


public class BarcodeReaderActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private FloatingActionMenu fam;
    private NFCActionDialogFragment dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        fam = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fam.setOnMenuItemClickListener(new FloatingActionMenu.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionMenu fam, int index, FloatingActionButton item) {

                switch (index){
                    case 0:
                        scanQR();
                        break;
                    case 1:
                        scanNFC();
                        break;
                    default:
                        break;
                }
            }
        });

        final ImageView avatar = (ImageView) findViewById(R.id.avatar);
        Picasso.with(this).load("http://lorempixel.com/200/200/food/8").transform(new CircleTransform()).into(avatar);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void scanNFC(){
        this.dialog = new NFCActionDialogFragment("Read NFC",
                "Cancelar");
        this.dialog.show(getSupportFragmentManager(), "nfc_writer");
    }
    public void scanQR() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setResultDisplayDuration(0);
        integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            String contents = result.getContents();
            if (contents != null) {
                Toast toast = Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this, "Error " + result.toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onNewIntent(Intent intent) {

        this.readTagMsg(intent);
    }

    private void readTagMsg(Intent intent) {


        Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        StringBuffer sb = new StringBuffer();
        if ((tag != null) && (tag.getTechList() != null)) {
            for (int i = 0; i < tag.getTechList().length; i++) {
                sb.append(tag.getTechList()[i]).append("\n");
            }
        }
        Toast toast = Toast.makeText(this, this.readTag(intent), Toast.LENGTH_SHORT);
        toast.show();

        if(dialog!=null) {
            this.dialog.dismiss();
        }

        Intent intentDetail = new Intent(BarcodeReaderActivity.this, DetailActivity.class);
        intentDetail.putExtra(DetailActivity.EXTRA_NAME, "producto");

        startActivity(intentDetail);
        /*this.tTechs.setText(this.getResources().getString(R.string.techs));
        this.tTechs.append("\n");
        this.tTechs.append(sb.toString());

        if (this.tag != null) {
            this.tMessage.setText(this.getResources().getString(
                    R.string.message));
            this.tMessage.append("\n");
            this.tMessage.append(this.readTag(intent));
        }*/
    }


    public String readTag(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            NdefMessage[] messages = null;
            Parcelable[] rawMsgs = intent
                    .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                messages = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    messages[i] = (NdefMessage) rawMsgs[i];
                }
            }
            if (messages[0] != null) {
                String result = "";
                byte[] payload = messages[0].getRecords()[0].getPayload();
                for (int b = 1; b < payload.length; b++) {
                    result += (char) payload[b];
                }
                return result;
            }
        }
        return "";
    }
}
