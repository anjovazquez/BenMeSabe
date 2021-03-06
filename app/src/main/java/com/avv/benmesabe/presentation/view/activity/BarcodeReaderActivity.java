package com.avv.benmesabe.presentation.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.avv.benmesabe.R;
import com.avv.benmesabe.domain.CustomerRequest;
import com.avv.benmesabe.domain.Order;
import com.avv.benmesabe.domain.order.OrderManager;
import com.avv.benmesabe.picasso.CircleTransform;
import com.avv.benmesabe.presentation.gcm.service.BenMeSabePreferences;
import com.avv.benmesabe.presentation.gcm.service.RegistrationIntentService;
import com.avv.benmesabe.presentation.internal.di.HasComponent;
import com.avv.benmesabe.presentation.internal.di.components.DaggerProductComponent;
import com.avv.benmesabe.presentation.internal.di.components.ProductComponent;
import com.avv.benmesabe.presentation.internal.di.modules.ProductModule;
import com.avv.benmesabe.presentation.presenter.MainMenuPresenter;
import com.avv.benmesabe.presentation.view.MainMenuView;
import com.avv.benmesabe.presentation.view.dialog.CustomerRequestDialogFragment;
import com.avv.benmesabe.presentation.view.dialog.NFCActionDialogFragment;
import com.avv.benmesabe.presentation.view.dialog.OrderActionDialogFragment;
import com.avv.benmesabe.presentation.view.fragment.MenuFragmentPageAdapter;
import com.avv.benmesabe.presentation.view.fragment.OrderFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.expandableselector.ExpandableItem;
import com.karumi.expandableselector.ExpandableSelector;
import com.karumi.expandableselector.OnExpandableItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BarcodeReaderActivity extends BaseActivity implements HasComponent<ProductComponent>, MainMenuView, OrderActionDialogFragment.OnSendOrderListener, CustomerRequestDialogFragment.OnSendCustomerRequestListener {

    private static final String TAG = "BarcodeReaderActivity";

    private NFCActionDialogFragment dialog;
    private ProductComponent productComponent;


    private Order order;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.avatar)
    ImageView avatar;

    @Bind(R.id.scan_options)
    ExpandableSelector scanOptionSelector;

    @Inject
    MainMenuPresenter mainMenuPresenter;

    BroadcastReceiver mRegistrationBroadcastReceiver;

    public ExpandableSelector getScanOptionSelector() {
        return scanOptionSelector;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public ProductComponent getComponent() {
        return productComponent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        Picasso.with(this).load(R.drawable.icon_white).transform(new CircleTransform()).into(avatar);

        getApplicationComponent().inject(this);
        this.initializeInjector();
        productComponent.inject(this);

        mainMenuPresenter.setView(this);

        //Establecer el PageAdapter del componente ViewPager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MenuFragmentPageAdapter(
                getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);


        installMenuListeners(navigationView, viewPager);

        initializeScanOptionsExpandableSelector();



        /************************ GCM    *****************************/
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(BenMeSabePreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Toast.makeText(BarcodeReaderActivity.this,"Token enviado",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BarcodeReaderActivity.this,"Token error",Toast.LENGTH_SHORT).show();
                }
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
        /************************ GCM    *****************************/
    }

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(BenMeSabePreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    private void installMenuListeners(NavigationView navigationView, final ViewPager viewPager) {
        MenuItem navOrder = navigationView.getMenu().findItem(R.id.nav_order);
        navOrder.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                viewPager.setCurrentItem(2);
                return false;
            }
        });

        MenuItem navSuggestion = navigationView.getMenu().findItem(R.id.nav_suggestion);
        navSuggestion.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                viewPager.setCurrentItem(0);
                return false;
            }
        });

        MenuItem navScanNFC = navigationView.getMenu().findItem(R.id.nav_scan_nfc);
        navScanNFC.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                scanNFC();
                return false;
            }
        });

        MenuItem navScanQR = navigationView.getMenu().findItem(R.id.nav_scan_qr);
        navScanQR.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                scanQR();
                return false;
            }
        });

        MenuItem navScanIntro = navigationView.getMenu().findItem(R.id.nav_intro);
        navScanIntro.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent toIntro = new Intent(BarcodeReaderActivity.this, BenMeSabeIntro.class);
                startActivity(toIntro);
                return false;
            }
        });

        MenuItem navCustomerRequest = navigationView.getMenu().findItem(R.id.nav_customer);
        navCustomerRequest.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                sendCustomerRequest();
                return false;
            }
        });
    }

    private void initializeInjector() {
        ProductModule productModule = new ProductModule();
        productModule.setOrder(order);
        this.productComponent = DaggerProductComponent.builder()
                .benMeSabeAppComponent(getApplicationComponent())
                .activityModule(getActivityModule()).productModule(productModule)
                .build();
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
        this.dialog = new NFCActionDialogFragment();

        Bundle args = new Bundle();
        args.putString("text", "Acerque el dispositivo a la etiqueta de producto");
        args.putString("textButton", "Cancelar");
        dialog.setArguments(args);
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
                Intent intentDetail = new Intent(BarcodeReaderActivity.this, DetailActivity.class);
                contents = contents.replace("http://", "");
                intentDetail.putExtra(DetailActivity.EXTRA_PRODUCT_ID, Integer.parseInt(contents.substring(contents.indexOf("/")+1)));

                startActivity(intentDetail);
            }
            else {
                Snackbar.make(avatar, "No se ha detectado ningún producto", Snackbar.LENGTH_LONG)
                        .show();
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


        String readResult = this.readTag(intent);
        if(readResult!=null && !readResult.isEmpty()) {
            Toast toast = Toast.makeText(this, readResult, Toast.LENGTH_SHORT);
            toast.show();

            if (dialog != null) {
                this.dialog.dismiss();
            }

            Intent intentDetail = new Intent(BarcodeReaderActivity.this, DetailActivity.class);
            intentDetail.putExtra(DetailActivity.EXTRA_PRODUCT_ID, Integer.parseInt(readResult.substring(readResult.indexOf("/")+1)));

            startActivity(intentDetail);

        }
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


    private void initializeScanOptionsExpandableSelector() {
        List<ExpandableItem> expandableItems = new ArrayList<ExpandableItem>();
        ExpandableItem item  = new ExpandableItem();
        item.setResourceId(R.drawable.scanqrnfc);
        expandableItems.add(item);
        expandableItems.add(new ExpandableItem("NFC"));
        expandableItems.add(new ExpandableItem("QR"));
        scanOptionSelector.showExpandableItems(expandableItems);

        scanOptionSelector.setOnExpandableItemClickListener(new OnExpandableItemClickListener() {
            @Override
            public void onExpandableItemClickListener(int index, View view) {
                switch (index) {
                    case 1:
                        ExpandableItem firstItem = scanOptionSelector.getExpandableItem(1);
                        scanNFC();
                        break;
                    case 2:
                        ExpandableItem secondItem = scanOptionSelector.getExpandableItem(2);
                        scanQR();
                        break;
                    case 3:
                        ExpandableItem thirdItem = scanOptionSelector.getExpandableItem(3);
                        break;
                    default:
                }
                scanOptionSelector.collapse();
            }

            private void swipeFirstItem(int position, ExpandableItem clickedItem) {
                ExpandableItem firstItem = scanOptionSelector.getExpandableItem(0);
                scanOptionSelector.updateExpandableItem(0, clickedItem);
                scanOptionSelector.updateExpandableItem(position, firstItem);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setFloatingMenuOptions(String mode){
        switch(mode){
            case OrderFragment.NAME:
                initializeOrderExpandableSelector();
                break;
            default:
                initializeScanOptionsExpandableSelector();
                break;
        }

    }


    private void initializeOrderExpandableSelector() {
        List<ExpandableItem> expandableItems = new ArrayList<ExpandableItem>();
        ExpandableItem itemOrder = new ExpandableItem();
        ExpandableItem itemClose = new ExpandableItem();
        itemOrder.setResourceId(R.drawable.writing46);
        itemClose.setResourceId(R.drawable.origami28);
        expandableItems.add(itemOrder);
        expandableItems.add(itemClose);
        scanOptionSelector.showExpandableItems(expandableItems);

        scanOptionSelector.setOnExpandableItemClickListener(new OnExpandableItemClickListener() {
            @Override
            public void onExpandableItemClickListener(int index, View view) {
                switch (index) {
                    case 1:
                        ExpandableItem firstItem = scanOptionSelector.getExpandableItem(1);


                        showOrderDialog();

                        break;
                    case 2:
                        ExpandableItem secondItem = scanOptionSelector.getExpandableItem(2);
                        break;
                    default:
                }
                scanOptionSelector.collapse();
            }
        });
    }

    OrderActionDialogFragment orderDialog;

    public void showOrderDialog(){
        orderDialog = new OrderActionDialogFragment();

        orderDialog.setOnSendOrderListener(this);
        Bundle args = new Bundle();
        args.putString("text", "Se está enviando su pedido");
        args.putString("textButton", "Aceptar");
        orderDialog.setArguments(args);
        orderDialog.show(getSupportFragmentManager(), "order_action");
    }

    @Override
    public void sendOrderListener(String tableNo, String orderName) {
        order = OrderManager.getInstance().closeOrder();
        order.setOrderName(orderName);
        order.setTableNo(tableNo);
        mainMenuPresenter.postOrder(order);
    }

    @Override
    public void showOrderInView(Order order) {
        orderDialog.setOrderSended();
    }

    CustomerRequestDialogFragment customerDialog;
    public void sendCustomerRequest() {
        customerDialog = new CustomerRequestDialogFragment();

        customerDialog.setOnSendCustomerRequestListener(this);
        Bundle args = new Bundle();
        args.putString("text", "Elija el motivo de la consulta");
        args.putString("textButton", "Aceptar");
        customerDialog.setArguments(args);
        customerDialog.show(getSupportFragmentManager(), "customer_action");
    }

    @Override
    public void sendCustomerRequestListener(String requestType) {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setType(requestType);
        mainMenuPresenter.postCustomerRequest(customerRequest);
    }

    @Override
    public void showCustomerRequestInView(CustomerRequest order) {
        customerDialog.setCustomerRequestSended();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
