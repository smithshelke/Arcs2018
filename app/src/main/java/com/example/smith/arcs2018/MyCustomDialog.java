package com.example.smith.arcs2018;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * Created by SMITH on 03-Feb-18.
 */

public class MyCustomDialog extends DialogFragment {


    public interface OnDialogInputListener{
        void inputReceived(String msg);
    }

    private OnDialogInputListener mInputListener;


    public void getLink(OnDialogInputListener listener) {
        mInputListener = listener;
    }

    private EditText linkEditText;
    private ProgressBar mProgressBar;
    private Button submitButton;
    private String authToken;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dialog,container,false);
        getDialog().setTitle("Upload Link");
        linkEditText = (EditText)view.findViewById(R.id.linkEdiText);
        submitButton = (Button)view.findViewById(R.id.submitButton);
        authToken = getActivity().getSharedPreferences("MyPrefs",0).getString("auth",null);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar = (ProgressBar)view.findViewById(R.id.progress);
                mProgressBar.setVisibility(View.VISIBLE);
                APIRequestMethods APIRequestMethods = new APIRequestMethods();
                APIRequestMethods.postLink(authToken, linkEditText.getText().toString(), new APIRequestMethods.OnCompleteListener() {

                    @Override
                    public void onLinkReceived() {
                        super.onLinkReceived();
                        mInputListener.inputReceived(linkEditText.getText().toString());
                        getDialog().dismiss();

                    }
                    @Override
                    public void onFailure(String msg) {
                        super.onFailure(msg);
                        linkEditText.setError(msg+"Please try again");
                    }
                });
            }
        });
        return view;
    }
}
