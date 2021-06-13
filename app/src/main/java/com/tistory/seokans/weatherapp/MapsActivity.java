// 1. 파싱해서 가져올 데이터 분류하기
// 2. 선택한 마커에 맞추어 화면 띄우기
// 3. 날씨에 따른 이미지 바꾸기

// 아래 링크는 기상청의 RSS 데이터의 주기, 사용방법을 적어놓았다. 2016년도 버
// https://wkdgusdn3.tistory.com/entry/Android-%EA%B8%B0%EC%83%81%EC%B2%AD%EC%97%90%EC%84%9C-%EB%82%A0%EC%94%A8-%EB%B0%9B%EC%95%84%EC%98%A4%EA%B8%B0

package com.tistory.seokans.weatherapp;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tistory.seokans.weatherapp.databinding.ActivityMapsBinding;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private static final LatLng SEOUL = new LatLng(37.566535, 126.977969);
    // private static final LatLng DAEJEON = new LatLng(36.350412, 127.384548);
    private static final LatLng BUSAN = new LatLng(35.179554, 129.075642);

    private Marker mSeoul;
    // private Marker mDaejeon;
    private Marker mBusan;

    private GoogleMap mMap;
    private Document doc = null;
//    private String s = "";

    private String[] s = {"","",""};

//    private WeatherState result = new WeatherState();
//    private WeatherState wsSeoul = new WeatherState();
//    private WeatherState wsBusan = new WeatherState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        GetXMLTask task = new GetXMLTask(this);
//        task.execute(
//        );


    }

    private String xmlParsing(Document doc) {
        String s = "";
        NodeList nodeList = doc.getElementsByTagName("data");
        Node node = nodeList.item(0);
        Element fstElmnt = (Element) node;
// ----------------------------------------------------------------------------------
        NodeList hourList = fstElmnt.getElementsByTagName("hour");
        Element hourElement = (Element) hourList.item(0);
        hourList = hourElement.getChildNodes();

//            result.setHour("기준시각 = " + ((Node) hourList.item(0)).getNodeValue() + ":00");
        s += "기준시각 = " + ((Node) hourList.item(0)).getNodeValue() + ":00";
// ----------------------------------------------------------------------------------
        NodeList nameList = fstElmnt.getElementsByTagName("temp");
        Element nameElement = (Element) nameList.item(0);
        nameList = nameElement.getChildNodes();

//            result.setTemp(((Node) nameList.item(0)).getNodeValue() + "℃");
        s += (((Node) nameList.item(0)).getNodeValue() + "℃");
// ---------------------------------------------------------------------------------------
//            NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
//            Element websiteElement = (Element) websiteList.item(0);
//            websiteList = websiteElement.getChildNodes();
//            s += "날씨(KOR) = " + ((Node) websiteList.item(0)).getNodeValue()
//                    + "\n";

// ---------------------------------------------------------------
        NodeList wfEnList = fstElmnt.getElementsByTagName("wfEn");
        Element wfEnElement = (Element) wfEnList.item(0);
        wfEnList = wfEnElement.getChildNodes();
//            result.setWfEn(((Node) wfEnList.item(0)).getNodeValue());
        s += (((Node) wfEnList.item(0)).getNodeValue());
        return s;
    }


    /** 지도가 준비되면 호출된다. */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // 날씨 아이콘 만들기
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.clear);
        Bitmap clearIcon = Bitmap.createScaledBitmap(bitmapdraw.getBitmap(), 200, 200, false);

        bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.cloudy);
        Bitmap cloudyIcon = Bitmap.createScaledBitmap(bitmapdraw.getBitmap(), 200, 200, false);

        String urls[] = {
                "https://www.kma.go.kr/wid/queryDFS.jsp?gridx=60&gridy=127", // 서울시청
                "https://www.kma.go.kr/wid/queryDFS.jsp?gridx=67&gridy=100",  // 대전시청
                "https://www.kma.go.kr/wid/queryDFS.jsp?gridx=98&gridy=76" // 부산시청
        };


//        for(int i = 0; i < 3; i++){
//            try {
//
//                URL url = new URL(urls[i]);
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                DocumentBuilder db;
//                db = dbf.newDocumentBuilder();
//                doc = db.parse(new InputSource(url.openStream()));
//                doc.getDocumentElement().normalize();
//
//            } catch (Exception e) {
////                Toast.makeText(getBaseContext(), "Parsing Error",
////                        Toast.LENGTH_SHORT).show();
//            }
//            s[i] = xmlParsing(doc);
//
//        }



        mMap = googleMap;


        mSeoul = mMap.addMarker(new MarkerOptions()
                .position(SEOUL)
                .title("SEOUL")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)) // 파란색으로 만들기
                .snippet(s[0])
                .icon(BitmapDescriptorFactory.fromBitmap(clearIcon))
             //   .snippet(s) // 임시 문장을 넣어두기 (초기화 느낌)
                // .zIndex(1.0f)
                // .anchor(0.5f, 0.5f)
                // .rotation(90.0f)
                //.snippet(result)
        );
        // mSeoul.showInfoWindow(); 바로 클릭한것처럼 보이기
        mSeoul.setTag(0);

//        mDaejeon = mMap.addMarker(new MarkerOptions()
//                .position(DAEJEON)
//                .title("DEAJEON")
//                .snippet("snippet 내용 적어주기")
//                .flat(true)
//                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant))
//                );
//        mDaejeon.setTag(0);

        mBusan = mMap.addMarker(new MarkerOptions()
                .position(BUSAN)
                .title("BUSAN")
                .snippet(s[2])
                .alpha(0.7f) // 투명
                .icon(BitmapDescriptorFactory.fromBitmap(cloudyIcon))
        );
        mBusan.setTag(1);



        // Custom 하는 코드
        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(adapter);
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 7));
    }

    /** 사용자가 마커를 클릭하면 호출된다.  */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        //marker.setSnippet("" + result.hour + "\n" + result.temp + "\n" + result.wfEn);

        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();


//        if(clickCount != null) {
//            clickCount = clickCount + 1;
//            marker.setTag(clickCount);
//            Toast.makeText(this, marker.getTitle() +
//                    " 가 클릭되었음, 클릭횟수 : " + clickCount,
//                    Toast.LENGTH_SHORT).show();
//        }
        return false;
    }

//    // 여기서 쓰레드 부분이다. 차례대로 모든 정보를 받아올 수 있도록 변형을 가해볼까 생각하고 있다.
//    @SuppressLint("NewApi")
//    private class GetXMLTask extends AsyncTask<String, Void, Document> {
//        private Activity context;
//        // private WeatherState result = null;
//        private Document doc;
//
//
//        public GetXMLTask(Activity context) {
//            this.context = context;
//        }
//
//        @Override
//        protected Document doInBackground(String... urls) {  // 무의미한 입력 받기
//
//            URL url;
//            try {
//
//                    url = new URL(urls[0]);
//                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                    DocumentBuilder db;
//                    db = dbf.newDocumentBuilder();
//                    doc = db.parse(new InputSource(url.openStream()));
//                    doc.getDocumentElement().normalize();
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
//        @Override
//        protected void onPostExecute(Document doc) {
//                NodeList nodeList = doc.getElementsByTagName("data");
//                Node node = nodeList.item(0);
//                Element fstElmnt = (Element) node;
//// ----------------------------------------------------------------------------------
//                NodeList hourList = fstElmnt.getElementsByTagName("hour");
//                Element hourElement = (Element) hourList.item(0);
//                hourList = hourElement.getChildNodes();
//
////            result.setHour("기준시각 = " + ((Node) hourList.item(0)).getNodeValue() + ":00");
////                s += "기준시각 = " + ((Node) hourList.item(0)).getNodeValue() + ":00";
//// ----------------------------------------------------------------------------------
//                NodeList nameList = fstElmnt.getElementsByTagName("temp");
//                Element nameElement = (Element) nameList.item(0);
//                nameList = nameElement.getChildNodes();
//
////            result.setTemp(((Node) nameList.item(0)).getNodeValue() + "℃");
////                s += (((Node) nameList.item(0)).getNodeValue() + "℃");
//// ---------------------------------------------------------------------------------------
////            NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
////            Element websiteElement = (Element) websiteList.item(0);
////            websiteList = websiteElement.getChildNodes();
////            s += "날씨(KOR) = " + ((Node) websiteList.item(0)).getNodeValue()
////                    + "\n";
//
//// ---------------------------------------------------------------
//                NodeList wfEnList = fstElmnt.getElementsByTagName("wfEn");
//                Element wfEnElement = (Element) wfEnList.item(0);
//                wfEnList = wfEnElement.getChildNodes();
////            result.setWfEn(((Node) wfEnList.item(0)).getNodeValue());
////            s += (((Node) wfEnList.item(0)).getNodeValue());
//
//        }
//    }
}