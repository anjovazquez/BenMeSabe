package com.avv.benmesabe.presentation.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avv.benmesabe.R;

/**
 * Created by angelvazquez on 3/11/15.
 */
public class ScanFragment extends Fragment {



    public static ScanFragment newInstance() {
        ScanFragment fragment = new ScanFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_fragment, container, false);
        //ButterKnife.bind(this, view);



        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        /*if(isVisibleToUser) {
            IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan a barcode");
            integrator.setResultDisplayDuration(0);
            integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.initiateScan();
        }*/
    }
}
