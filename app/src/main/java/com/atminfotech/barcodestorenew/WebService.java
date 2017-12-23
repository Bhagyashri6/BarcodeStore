package com.atminfotech.barcodestorenew;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Map;

class WebService {

    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://atm-india.in/Service.asmx";

    private static String SOAP_ACTION = "http://tempuri.org/";

    public static SoapObject invokeWS(String webMethName) {

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        SoapObject response = null;

        try {
                androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);

                response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static SoapObject invokeWSWithProperties(long row, String text, String webMethName) {

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo pi=new PropertyInfo();
        pi.setName("rowId");
        pi.setValue(row);
        pi.setType(Long.class);
        request.addProperty(pi);
        pi=new PropertyInfo();
        pi.setName("fillValue");
        pi.setValue(text);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        SoapObject response = null;

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    static SoapObject invokeWSWithProperties(Map<String,String> mapStrings, Map<String,Integer> mapIntegers, String webMethName) {

        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        for(String s: mapStrings.keySet()){
            PropertyInfo pi=new PropertyInfo();
            pi.setName(s);
            pi.setValue(mapStrings.get(s));
            pi.setType(String.class);
            request.addProperty(pi);
        }

        if(mapIntegers != null) {

            for (String s : mapIntegers.keySet()) {
                PropertyInfo pi = new PropertyInfo();
                pi.setName(s);
                pi.setValue(mapIntegers.get(s));
                pi.setType(Integer.class);
                request.addProperty(pi);

            }
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        SoapObject response = null;

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            response = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static SoapPrimitive invokeWSWithPropertiesAndPrimitiveReturnType(Map<String,String> mapStrings, Map<String,Long> mapIntegers, String webMethName) {

        SoapPrimitive response = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        for(String s: mapStrings.keySet()){
            PropertyInfo pi=new PropertyInfo();
            pi.setName(s);
            pi.setValue(mapStrings.get(s));
            pi.setType(String.class);
            request.addProperty(pi);
        }

        if(mapIntegers != null) {

            for (String s : mapIntegers.keySet()) {
                PropertyInfo pi = new PropertyInfo();
                pi.setName(s);
                pi.setValue(mapIntegers.get(s));
                pi.setType(Integer.class);
                request.addProperty(pi);
            }
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.env = "http://schemas.xmlsoap.org/soap/envelope/";
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            response = (SoapPrimitive) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
