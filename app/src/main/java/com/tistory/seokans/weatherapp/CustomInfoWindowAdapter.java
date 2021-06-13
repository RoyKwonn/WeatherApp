package com.tistory.seokans.weatherapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;
    private String state;
    private String s = "";

    private static final LatLng SEOUL = new LatLng(37.566535, 126.977969);
    // private static final LatLng DAEJEON = new LatLng(36.350412, 127.384548);
    private static final LatLng BUSAN = new LatLng(35.179554, 129.075642);

    private Marker mSeoul;
    // private Marker mDaejeon;
    private Marker mBusan;


    public CustomInfoWindowAdapter(Activity context){
        this.context = context;
    }

    public void setState(String state) {
//        View view = context.getLayoutInflater().inflate(R.layout.customwindow, null);
//        ImageView tvWeatherIcon = (ImageView) view.findViewById(R.id.tv_weathericon);

        this.state = state;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

//        GetXMLTask task = new GetXMLTask(context);

        // xml파일과 연결하는 부분
        View view = context.getLayoutInflater().inflate(R.layout.customwindow, null);

        // 변수를 선언한 이후 그 xml 요소들에 정보를 대입하는 것
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvSubTitle = (TextView) view.findViewById(R.id.tv_subtitle);
        ImageView tvWeatherIcon = (ImageView) view.findViewById(R.id.tv_weathericon);


//        if((int) marker.getTag() == 0)
//            task.execute("https://www.kma.go.kr/wid/queryDFS.jsp?gridx=60&gridy=127");
//        else if((int)marker.getTag() == 1)
//            task.execute("https://www.kma.go.kr/wid/queryDFS.jsp?gridx=98&gridy=76");

        tvTitle.setText(marker.getTitle());
        // tvSubTitle.setText(marker.getSnippet());
        tvSubTitle.setText(marker.getSnippet());
        // if(state.toString() == "Clear")
            tvWeatherIcon.setImageResource(R.drawable.clear);

        return view;
    }

//    // 여기서 쓰레드 부분이다. 차례대로 모든 정보를 받아올 수 있도록 변형을 가해볼까 생각하고 있다.
//    @SuppressLint("NewApi")
//    private class GetXMLTask extends AsyncTask<String, Void, Document> {
//        private Activity context;
//        // private WeatherState result = null;
//        private Document doc;
//
////        public MapsActivity.WeatherState getResult() {
////            return result;
////        }
//
//        public GetXMLTask(Activity context) {
//            this.context = context;
//        }
//
//
//        // --------------------------------------------------------
//
//
////        protected Document onPreExcuted() {
////
////        }
//
//
//        @Override
//        protected Document doInBackground(String... urls) {
//
//            URL url;
//            try {
//                url = new URL(urls[0]);
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                DocumentBuilder db;
//
//                db = dbf.newDocumentBuilder();
//
//                doc = db.parse(new InputSource(url.openStream()));
//                doc.getDocumentElement().normalize();
//
//            } catch (Exception e) {
////                Toast.makeText(getBaseContext(), "Parsing Error",
////                        Toast.LENGTH_SHORT).show();
//            }
//            return doc;
//        }
//
//        // Thread가 정상적으로 동작안할때 호출됨
//        // @Override
//        // protected void onCancelled(Document doc) { }
//
//        // doInBackground():Thread가 정상적으로 동작한 경우 Excute를 사용하면 이 부분이 호출된다.
//        @Override
//        protected void onPostExecute(Document doc) {
//
//
//            NodeList nodeList = doc.getElementsByTagName("data");
//            Node node = nodeList.item(0);
//            Element fstElmnt = (Element) node;
//// ----------------------------------------------------------------------------------
//            NodeList hourList = fstElmnt.getElementsByTagName("hour");
//            Element hourElement = (Element) hourList.item(0);
//            hourList = hourElement.getChildNodes();
//
////            result.setHour("기준시각 = " + ((Node) hourList.item(0)).getNodeValue() + ":00");
//             s += "기준시각 = " + ((Node) hourList.item(0)).getNodeValue() + ":00\n";
//// ----------------------------------------------------------------------------------
//            NodeList nameList = fstElmnt.getElementsByTagName("temp");
//            Element nameElement = (Element) nameList.item(0);
//            nameList = nameElement.getChildNodes();
//
////            result.setTemp(((Node) nameList.item(0)).getNodeValue() + "℃");
//            s += (((Node) nameList.item(0)).getNodeValue() + "℃\n");
//// ---------------------------------------------------------------------------------------
////            NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
////            Element websiteElement = (Element) websiteList.item(0);
////            websiteList = websiteElement.getChildNodes();
////            s += "날씨(KOR) = " + ((Node) websiteList.item(0)).getNodeValue()
////                    + "\n";
//// ---------------------------------------------------------------
//            NodeList wfEnList = fstElmnt.getElementsByTagName("wfEn");
//            Element wfEnElement = (Element) wfEnList.item(0);
//            wfEnList = wfEnElement.getChildNodes();
////            result.setWfEn(((Node) wfEnList.item(0)).getNodeValue());
//            s += (((Node) wfEnList.item(0)).getNodeValue());
//        }
//    }
}