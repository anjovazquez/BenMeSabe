package com.avv.benmesabe.presentation.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avv.benmesabe.R;

/**
 * Created by angel on 30/07/2015.
 */
public class NFCActionDialogFragment extends DialogFragment {

    private TextView textView;
    private Button bButton;

    private String text;
    private String textButton;

    public interface OnNFCDetected {
        void nfcDetected(int size);
    }

    public NFCActionDialogFragment(){
        text = "";
        textButton = "";
    }

    public static NFCActionDialogFragment newInstance(String text, String textButton) {

        Bundle args = new Bundle();

        NFCActionDialogFragment fragment = new NFCActionDialogFragment();
        args.putString("text", text);
        args.putString("textButton", textButton);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = getArguments().getString("text");
        textButton = getArguments().getString("textButton");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getDialog().setTitle("Acción NFC");

        View view = inflater.inflate(R.layout.nfc_write_dialog, container,
                false);

        this.textView = (TextView) view.findViewById(R.id.textInfo);
        this.textView.setText(text);

        this.bButton = (Button) view.findViewById(R.id.bCancel);
        this.bButton.setText(textButton);
        this.bButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NFCActionDialogFragment.this.getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return super.onCreateDialog(savedInstanceState);
    }

}