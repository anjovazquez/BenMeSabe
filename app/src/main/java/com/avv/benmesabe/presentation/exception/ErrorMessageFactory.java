package com.avv.benmesabe.presentation.exception;

import android.content.Context;

import com.avv.benmesabe.R;

/**
 * Created by angelvazquez on 18/10/15.
 */
public class ErrorMessageFactory {

    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);

        /*if (exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection);
        } else if (exception instanceof ProductNotFoundException) {
            message = context.getString(R.string.exception_message_user_not_found);
        }*/

        return message;
    }
}
