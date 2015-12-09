package com.avv.benmesabe.presentation.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.avv.benmesabe.R;

import org.rocko.bpb.BounceProgressBar;

import butterknife.Bind;

/**
 * Created by angel on 30/07/2015.
 */
public class CustomerRequestDialogFragment extends DialogFragment {

    private TextView textView;
    private Button bButton;

    @Bind(R.id.textRequest)
    Spinner sTextRequest;

    private String text;
    private String textButton;

    BounceProgressBar progress;

    public CustomerRequestDialogFragment(){
        text = "";
        textButton = "";
    }

    public static CustomerRequestDialogFragment newInstance(String text, String textButton) {

        Bundle args = new Bundle();

        CustomerRequestDialogFragment fragment = new CustomerRequestDialogFragment();
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
        this.getDialog().setTitle("Petición");
        TextView titleView = ((TextView)getDialog().findViewById( android.R.id.title ));
        titleView.setBackgroundColor(getResources().getColor(R.color.primary));
        titleView.setTextColor(getResources().getColor(R.color.white));
        titleView.setPadding(0, 0, 0, 10);

        View view = inflater.inflate(R.layout.customer_request_dialog, container,
                false);

        this.textView = (TextView) view.findViewById(R.id.textInfo);
        this.textView.setText(text);

        this.bButton = (Button) view.findViewById(R.id.bSend);
        this.bButton.setText(textButton);
        this.bButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!ended) {
                    //  CustomerRequestDialogFragment.this.getDialog().dismiss();
                    listener.sendCustomerRequestListener(sTextRequest.getSelectedItem().toString());
                    sTextRequest.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                }
                else{
                    CustomerRequestDialogFragment.this.getDialog().dismiss();
                }
            }
        });

        sTextRequest  = (Spinner) view.findViewById(R.id.textRequest);

        final String[] data = getResources().getStringArray(
                R.array.textRequests);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
        sTextRequest.setAdapter(adapter);

        progress = (BounceProgressBar)view.findViewById(R.id.progress);
        return view;
    }

    private boolean ended = false;

    public void setCustomerRequestSended(){
        //orderData.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        sTextRequest.setVisibility(View.GONE);
        textView.setText("Petición enviada");
        ended = true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return super.onCreateDialog(savedInstanceState);
    }

    public interface OnSendCustomerRequestListener {
        void sendCustomerRequestListener(String requestType);
    }

    private OnSendCustomerRequestListener listener;

    public void setOnSendCustomerRequestListener(OnSendCustomerRequestListener listener){
        this.listener = listener;
    }

}