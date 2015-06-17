package com.exia.puydufou.data;

/**
 * Created by Iseldore on 15/06/2015.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class SoapCommunicator {
    private static final String URL = "http://10.176.130.60:8080/webService/webService";
    //private static final String URL = "http://192.168.43.12:8080/webService/webService";
    private static final String SOAP_ACTION = "";
    private Context context;
    private SoapObject request;
    private SoapObject resultsRequestSOAP;

    public SoapCommunicator(Context context){
        this.context = context;
    }

    public SoapObject sendRequest(SoapObject request) {
        // Build Soap Message
       /* this.request = request;

        new BackgroundTask(this).execute();
        //System.err.println(resultsRequestSOAP);
        return resultsRequestSOAP;*/

        // Build Soap Message
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try
        {
            // Bypass error about requesting on main thread
            if (android.os.Build.VERSION.SDK_INT > 9)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            // Call WebService
            androidHttpTransport.call(SOAP_ACTION, envelope);

            // Print error
            //SoapFault fault = (SoapFault) envelope.bodyIn;
            //System.out.println(fault);

            // Print result
            SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
            //System.out.println(resultsRequestSOAP);
            return resultsRequestSOAP;
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
/*
    private class BackgroundTask extends AsyncTask<String, String, SoapObject> {

        private ProgressDialog dialog;
        private AsyncTaskCompleteListener<SoapObject> callback;

        public BackgroundTask(AsyncTaskCompleteListener<SoapObject> cb){
            this.callback = cb;
            this.dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Chargement...");
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(String... f_url) {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            SoapObject resSoap = null;

            try {
                // Call WebService
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Print error
                //SoapFault fault = (SoapFault) envelope.bodyIn;
                //System.err.println(fault);

                // Print result
                resSoap = (SoapObject) envelope.bodyIn;
                //System.out.println(resSoap);
                Thread.sleep(5000);
                //System.out.println(resultsRequestSOAP);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                resSoap = null;
            } catch (XmlPullParserException e) {
                System.err.println(e.getMessage());
                resSoap = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(resSoap);
            return resSoap;
        }

        @Override
        protected void onPostExecute(SoapObject resSoap) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            callback.onTaskComplete(resSoap);
        }
    }


    private class BackgroundTask extends AsyncTask<Void, Void, SoapObject> {
        private ProgressDialog dialog;

        public BackgroundTask() {
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Chargement...");
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            SoapObject resSoap = null;

            try {
                // Call WebService
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Print error
                //SoapFault fault = (SoapFault) envelope.bodyIn;
                //System.err.println(fault);

                // Print result
                resSoap = (SoapObject) envelope.bodyIn;
                System.out.println(resSoap);
                Thread.sleep(5000);
                //System.out.println(resultsRequestSOAP);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                resSoap = null;
            } catch (XmlPullParserException e) {
                System.err.println(e.getMessage());
                resSoap = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(resSoap);
            return resSoap;
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            resultsRequestSOAP = result;
            System.out.println("onpost : " +resultsRequestSOAP);
        }

    }
*/
}
