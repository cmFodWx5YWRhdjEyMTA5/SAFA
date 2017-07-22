package com.expose.safa.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.content.res.AppCompatResources;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.expose.safa.R;
import com.expose.safa.utilities.Constants;
import com.expose.safa.utilities.GMailSender;


public class EnquiryFragment extends Fragment {


    private Button btn_send;
    private EditText et_Name, et_Email, et_phone,et_message;

    private TextInputLayout inputLayout_Name, inputLayout_Email,
            inputLayout_phone,inputLayout_message;


    String body,
            name,
            phone,
            message,
            mail,
            fromMail,
            toMail
                    ;

    GMailSender sender;


    public EnquiryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= null;
        try {
            root = inflater.inflate(R.layout.fragment_enquiry, container, false);
        } catch (Exception e) {

            Log.d("eeeeeeeeeee",e.toString());
            e.printStackTrace();
        }

        inputLayout_Name   =(TextInputLayout)root.findViewById(R.id.input_layout_name);
        inputLayout_Email  =(TextInputLayout)root.findViewById(R.id.input_layout_email);
        inputLayout_phone  =(TextInputLayout)root.findViewById(R.id.input_layout_phone);
        inputLayout_message=(TextInputLayout)root.findViewById(R.id.input_layout_message);


        btn_send  =(Button)root.findViewById(R.id.btn_send);



        et_Name   =(EditText)root.findViewById(R.id.et_name);
        Drawable leftDrawable = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_person_name);
        et_Name.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);

        et_Email  =(EditText)root.findViewById(R.id.et_email);
        Drawable leftDrawable2 = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_email_person);
        et_Email.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable2, null);

        et_message=(EditText) root.findViewById(R.id.et_message);
        Drawable leftDrawable3 = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_chat_person);
        et_message.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable3, null);

        et_phone  =(EditText)root.findViewById(R.id.et_phone);
        Drawable leftDrawable4 = AppCompatResources.getDrawable(getActivity(), R.drawable.ic_call_person);
        et_phone.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable4, null);



        et_Name    .addTextChangedListener(new MyTextWatcher(et_Name));
        et_Email   .addTextChangedListener(new MyTextWatcher(et_Email));
        et_message .addTextChangedListener(new MyTextWatcher(et_message));
        et_phone   .addTextChangedListener(new MyTextWatcher(et_phone));

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });


        return root;
    }

    private void submitForm() {

        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatephone()) {
            return;
        }
        if (!validatemessage()) {
            return;
        }


        //after the correct information just perform send action

        name   =et_Name.getText().toString();
        mail   =et_Email.getText().toString();
        phone  =et_phone.getText().toString();
        message=et_message.getText().toString();


        sender = new GMailSender(Constants.EMAIL, Constants.PASSWORD);

        fromMail = "mobile.expose@gmail.com";


        try {

            Log.w("hai", "First mail");
            //Mail To
            toMail = "mobile.expose@gmail.com";




         /*   //phone of Email
            subject = "Enquiry request";*/

            body = "MobileNumber: "+phone+"\n\nMessage: "+message+"\n\nName: "+name +"\n"+"Email: "+mail;

            new MyAsyncClass().execute();

        }

        catch (Exception ex) {

            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validatemessage() {
        if (et_message.getText().toString().trim().isEmpty()) {
            inputLayout_message.setError(" messaages should not be blank");
            requestFocus(et_message);
            return false;
        } else {
            inputLayout_message.setErrorEnabled(false);
        }

        return true;
    }
    //#################################
    private boolean validatephone() {

        String ph_no=et_phone.getText().toString().trim();

        if (ph_no.isEmpty() || !isValidPhone(ph_no)||ph_no.length()>10) {
            inputLayout_phone.setError(" Phone Number is not valid ");
            requestFocus(et_phone);
            return false;
        } else {
            inputLayout_phone.setErrorEnabled(false);
        }

               /* if (!android.util.Patterns.PHONE.matcher(ph_no).matches()&&ph_no.trim().isEmpty()) {
                    inputLayout_phone.setError("Not a Valid Phone Number");
                    requestFocus(et_phone);
                    return false;
                }
                else
                {
                    inputLayout_phone.setErrorEnabled(false);
                }*/

        return true;
    }

//########################################################


    private boolean validateEmail() {

        String email = et_Email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayout_Email.setError(" email id is not valid ");
            requestFocus(et_Email);
            return false;
        } else {
            inputLayout_Email.setErrorEnabled(false);
        }

               /* if (et_Email.getText().toString().trim().isEmpty()) {
                    inputLayout_Email.setError("Name entered wrong");
                    requestFocus(et_Email);
                    return false;
                } else {
                    inputLayout_Email.setErrorEnabled(false);
                }
        */
        return true;
    }

//###################################################################

    private boolean validateName() {
        if (et_Name.getText().toString().trim().isEmpty()) {
            inputLayout_Name.setError("enter a valid name");
            requestFocus(et_Name);
            return false;
        } else {
            inputLayout_Name.setErrorEnabled(false);
        }

        return true;
    }


//#######################################################

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }


    private static boolean isValidPhone(String ph) {
        return !TextUtils.isEmpty(ph) && Patterns.PHONE.matcher(ph).matches();
    }


    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.et_name:
                    validateName();
                    break;
                case R.id.et_email:
                    validateEmail();
                    break;
                case R.id.et_message:
                    validatemessage();
                    break;
                case R.id.et_phone:
                    validatephone();
                    break;

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();



    }



    private class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }



        @Override
        protected Void doInBackground(Void... voids) {

            try {

                // Add subject, Body, your mail Id, and receiver mail Id.
                String subject="Enquiry";
                sender.sendMail(subject, body, fromMail, toMail);



            }

            catch (Exception ex) {

            }
            return null;

        }




        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
            Toast.makeText(getActivity(), "Email send success fully",Toast.LENGTH_SHORT).show();


            et_phone.setHint(phone.toString());
            et_message.setHint(message.toString());

        }
    }





}
