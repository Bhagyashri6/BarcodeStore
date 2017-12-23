package com.atminfotech.barcodestorenew;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.squareup.picasso.Picasso;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppsAdapter extends ArrayAdapter<AppData> implements View.OnClickListener{

    private ArrayList<AppData> dataSet;
    Context mContext;
    boolean execute=false;
    private String mobno;
    private String clientname;
    SessionManagement session;
    String mobile,value,Emailid,name,imei;

    private static String url = "http://atm-india.in/Service.asmx";
    private static final String URL = "http://atm-india.in/Service.asmx";
    private static final String NAMESPACE = "http://tempuri.org/";
    private static String MethodName = "GetSMSFromClient";



    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
      //  TextView sendsms;
    }

    public AppsAdapter(ArrayList<AppData> data, Context context) {
        super(context, R.layout.list_item_softwares, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        AppData dataModel=(AppData)object;

        switch (v.getId())
        {
            case R.id.main_layout:
                getContext().startActivity(new Intent(getContext(),SoftwareDetails.class));
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        AppData dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_softwares, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.app_name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.app_type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.priceApp);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
           // viewHolder.sendsms =(TextView)convertView.findViewById(R.id.sendsms);



            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getSalesBillNo());
        viewHolder.txtType.setText(dataModel.getDate());
        viewHolder.txtVersion.setText(dataModel.getLRNumber());
        Picasso.with(mContext)
                .load(dataModel.getImageId())
                .resize(120, 120)
                .into(viewHolder.info);


    /*    TelephonyManager telephonyManager= (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        imei=  telephonyManager.getDeviceId();

*/




        viewHolder.txtVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IsLoggedIn().execute();
               // mContext.startActivity(new Intent(getContext(),RegisterActivity.class));
            }
        });


        session = new SessionManagement(mContext.getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        name = user.get(SessionManagement.KEY_NAME);
        mobile = user.get(SessionManagement.KEY_Mobile);
        Emailid = user.get(SessionManagement.KEY_EmailId);
        imei=user.get(SessionManagement.KEY_Imei);
       // session.createRegisterationSession(name,mobile,imei,Emailid);

        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

    protected class IsLoggedIn extends AsyncTask<Void, Void, Void> {

        boolean success = false;


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

                SoapObject request = new SoapObject(NAMESPACE, "IsLoggedIn");

                request.addProperty("methodName", "IsLoggedIn");

                request.addProperty("imei", getDeviceID(tm));

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                androidHttpTransport.call("http://tempuri.org/IsLoggedIn", envelope);

                SoapPrimitive objs = (SoapPrimitive) envelope.getResponse();

                if (objs.toString().contentEquals("UserAlreadyExist")) {

                    success = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!success) {
                if (!success) {
                    new MaterialStyledDialog.Builder(getContext())
                            .setTitle("Register")
                            .setDescription("Your are still not a member with us plz Register for become member")
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveText("Yes")
                            .withIconAnimation(true)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                             /*   moveTaskToBack(true);

                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);*/
                                    Intent i = new Intent(getContext(), RegisterActivity.class);
                                    session = new SessionManagement(mContext.getApplicationContext());
                                    // SessionManagement session = new SessionManagement(getApplicationContext());


                                    mContext.startActivity(i);
                                }
                            })
                            .setNegativeText("NO")
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(true)
                            .show();

                } else {
                    execute = true;
                    session = new SessionManagement(mContext.getApplicationContext());
                    HashMap<String, String> user = session.getUserDetails();

                    name = user.get(SessionManagement.KEY_NAME);
                    mobile = user.get(SessionManagement.KEY_Mobile);
                    Emailid = user.get(SessionManagement.KEY_EmailId);
                    imei = user.get(SessionManagement.KEY_Imei);
                    //    session.createRegisterationSession(name,mobile,imei,Emailid);


                    new SendSMSTask().execute();
                }
            }
        }
    }

        private class SendSMSTask extends AsyncTask<Void, Void, Void> {
            //   private ProgressDialog ringProgressDialog;
            private Map<String, String> bundle;
            boolean success;
            //String imei="";

            @Override
            protected void onPreExecute() {
            /*session = new SessionManagement(mContext.getApplicationContext());

            Map<String, String> map = new HashMap<>();
            map.put("name", name);   //  ("name=(that use from web service in paas string name ", name=(that use from database colom name for this)
            map.put("mobileNo", mobile);
            map.put("email",Emailid);
            map.put("imeiNo", imei);
            session.createRegisterationSession(name,mobile,imei,Emailid);
*/
          /*  ringProgressDialog = ProgressDialog.show(getContext(), "Please wait ...", "Requesting OTP Message ...", true);
            ringProgressDialog.setCancelable(true);
*/
            }

            @Override
            protected Void doInBackground(Void... voids) {

                SoapObject response = WebService.invokeWS("GetSMSFromClient");
                if (response != null) {
              /*  name = user.get(SessionManagement.KEY_NAME);
                mobile = user.get(SessionManagement.KEY_Mobile);
                Emailid = user.get(SessionManagement.KEY_EmailId);
                imei=user.get(SessionManagement.KEY_Imei);
                session.createRegisterationSession(name,mobile,imei,Emailid);*/

              /*  mobile = response.getProperty("Mo_no").toString();
                name = response.getProperty("Client_name").toString();
                imei = response.addProperty("Imei",imei.trim()).toString();*/
                    //  imei =response.addProperty("Imei",imei).toString();


                    try {
                        // Create http cliient object to send request to server

                        HttpClient Client = new DefaultHttpClient();

                        // Create URL string

                        try {
                            String SetServerString = "";

                            session = new SessionManagement(mContext.getApplicationContext());
                            HashMap<String, String> user = session.getUserDetails();

                            name = user.get(SessionManagement.KEY_NAME);
                            mobile = user.get(SessionManagement.KEY_Mobile);
                            Emailid = user.get(SessionManagement.KEY_EmailId);
                            imei = user.get(SessionManagement.KEY_Imei);


                            String msg = URLEncoder.encode("Client mobile no  for Software Demo  is \n " + mobile + "\n Client name is \n" + name, "UTF-8");
                            //String URL = "http://fast.admarksolution.com/vendorsms/pushsms.aspx?user=MUMATM&password=abc123&msisdn=91"+mobile+"&sid=ERHINO&msg="+msg+"&fl=0&gwid=2";
                            String URL = "http://fast.admarksolution.com/vendorsms/pushsms.aspx?user=MUMATM&password=abc123&msisdn=919821008090&sid=MUMATM&msg=" + msg + "&fl=0&gwid=2";
                            // Create Request to server and get response

                            HttpGet httpget = new HttpGet(URL);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            SetServerString = Client.execute(httpget, responseHandler);

                            SetServerString += "";
                        } catch (Exception ex) {
                            //content.setText("Fail!");
                            ex.printStackTrace();

                        }
                        success = true;

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Sorry! Your sms sending failed...",Toast.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                //  ringProgressDialog.dismiss();
                if (success) {
                    session.createRegisterationSession(name, mobile, imei, Emailid);
                    Toast.makeText(getContext(), "Message Sent Successfully. Please wait while you receive the message..", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Problem in sending message. Please try again..", Toast.LENGTH_LONG).show();
                }

            }


        }

        String getDeviceID(TelephonyManager phonyManager) {

            String id = phonyManager.getDeviceId();
            if (id == null) {
                id = "not available";
            }

            int phoneType = phonyManager.getPhoneType();
            switch (phoneType) {
                case TelephonyManager.PHONE_TYPE_NONE:
                    return id;

                case TelephonyManager.PHONE_TYPE_GSM:
                    return id;

                case TelephonyManager.PHONE_TYPE_CDMA:
                    return id;

                default:
                    return id;
            }
        }
    }
