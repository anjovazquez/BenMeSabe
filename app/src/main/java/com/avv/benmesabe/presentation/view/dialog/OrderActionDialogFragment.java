package com.avv.benmesabe.presentation.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avv.benmesabe.R;

import org.rocko.bpb.BounceProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by angel on 30/07/2015.
 */
public class OrderActionDialogFragment extends DialogFragment {

    private TextView textView;
    private Button bButton;

    private String text;
    private String textButton;

    @Bind(R.id.tableNo)
    EditText eTableNo;
    @Bind(R.id.orderName)
    EditText eOrderName;
    @Bind(R.id.orderData)
    LinearLayout orderData;
    @Bind(R.id.progress)
    BounceProgressBar progress;

    private OnSendOrderListener listener;

    public interface OnSendOrderListener {
        void sendOrderListener(String tableNo, String orderName);
    }

    public OrderActionDialogFragment(){
        text = "";
        textButton = "";
    }

    public void setOnSendOrderListener(OnSendOrderListener listener){
        this.listener = listener;
    }


    public static OrderActionDialogFragment newInstance(String text, String textButton) {

        Bundle args = new Bundle();

        OrderActionDialogFragment fragment = new OrderActionDialogFragment();
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
        this.getDialog().setTitle("Enviando pedido...");
        TextView titleView = ((TextView)getDialog().findViewById( android.R.id.title ));
        titleView.setBackgroundColor(getResources().getColor(R.color.primary));
        titleView.setTextColor(getResources().getColor(R.color.white));
        titleView.setPadding(10, 10, 10, 10);

        View view = inflater.inflate(R.layout.send_order_dialog, container,
                false);
        ButterKnife.bind(this, view);

        this.textView = (TextView) view.findViewById(R.id.textInfo);
        this.textView.setText(text);

        this.bButton = (Button) view.findViewById(R.id.bOk);
        this.bButton.setText(textButton);
        this.bButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!ended) {
                    if (eTableNo.getText().toString() != null && !eTableNo.getText().toString().equals("")
                            && eOrderName.getText().toString() != null && !eOrderName.getText().toString().equals("")) {
                        listener.sendOrderListener(eTableNo.getText().toString(), eOrderName.getText().toString());
                        orderData.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
                    } else {
                        Snackbar.make(v, "Debes rellenar los campos", Snackbar.LENGTH_SHORT).show();
                    }
                }
                else{
                    OrderActionDialogFragment.this.getDialog().dismiss();
                }
            }
        });

        progress = (BounceProgressBar)view.findViewById(R.id.progress);

        return view;
    }

    private boolean ended = false;

    public void setOrderSended(){
        orderData.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        textView.setText("Pedido enviado");
        ended = true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return super.onCreateDialog(savedInstanceState);
    }

}