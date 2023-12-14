package com.example.heatislandmap;

import static com.example.heatislandmap.GetTemp.windSpeed;
import static com.example.heatislandmap.NameDictionary.REGION_NAME;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class OpenWeatherTest {
    private static String latitude;
    private static String longitude;
    private static String apiKey = "c03efa9d1e3e9d23a65300866419e82d"; // API 키

    public static Map<String, Double> latMap = new LinkedHashMap<>();
    public static Map<String, Double> lngMap = new LinkedHashMap<>();
    public static void setLatLngMap() {
        latMap.put("Jongno-gu", 37.594);
        lngMap.put("Jongno-gu", 126.977);
//        var Jung = new naver.maps.LatLng(37.5601435645512, 126.995968139707);
        latMap.put("Jung-gu", 37.560);
        lngMap.put("Jung-gu", 126.995);
//        var Yongsan = new naver.maps.LatLng(37.5313849658815, 126.979906988235);
        latMap.put("Yongsan-gu", 37.531);
        lngMap.put("Yongsan-gu", 126.979);
//        var Seongdong = new naver.maps.LatLng(37.5510296891344, 127.041058533321);
        latMap.put("Seongdong-gu", 37.551);
        lngMap.put("Seongdong-gu", 127.041);
//        var Gwangjin = new naver.maps.LatLng(37.5467202379314, 127.085744097681);
        latMap.put("Gwangjin-gu", 37.546);
        lngMap.put("Gwangjin-gu", 127.085);
//        var Dongdaemun = new naver.maps.LatLng(37.581956547893, 127.054848127807);
        latMap.put("Dongdaemun-gu", 37.581);
        lngMap.put("Dongdaemun-gu", 127.054);
//        var Jungnang = new naver.maps.LatLng(37.5978173799249, 127.092883176298);
        latMap.put("Jungnang-gu", 37.597);
        lngMap.put("Jungnang-gu", 127.092);
//        var Seongbuk = new naver.maps.LatLng(37.6057019023968, 127.017579491168);
        latMap.put("Seongbuk-gu", 37.605);
        lngMap.put("Seongbuk-gu", 127.017);
//        var Gangbuk = new naver.maps.LatLng(37.6434739135586, 127.011188975239);
        latMap.put("Gangbuk-gu", 37.643);
        lngMap.put("Gangbuk-gu", 127.011);
//        var Dobong = new naver.maps.LatLng(37.6691020802884, 127.032368819558);
        latMap.put("Dobong-gu", 37.669);
        lngMap.put("Dobong-gu", 127.032);
//        var Nowon = new naver.maps.LatLng(37.65251104799, 127.075034689337);
        latMap.put("Nowon-gu", 37.652);
        lngMap.put("Nowon-gu", 127.075);
//        var Eunpyeong = new naver.maps.LatLng(37.6192112825748, 126.927022877199);
        latMap.put("Eunpyeong-gu", 37.619);
        lngMap.put("Eunpyeong-gu", 126.927);
//        var Seodaemun = new naver.maps.LatLng(37.5777853091699, 126.939063095715);
        latMap.put("Seodaemun-gu", 37.577);
        lngMap.put("Seodaemun-gu", 126.939);
//        var Mapo = new naver.maps.LatLng(37.559313492554, 126.908270027449);
        latMap.put("Mapo-gu", 37.559);
        lngMap.put("Mapo-gu", 126.908);
//        var Yangcheon = new naver.maps.LatLng(37.5247894102082, 126.855477660234);
        latMap.put("Yangcheon-gu", 37.524);
        lngMap.put("Yangcheon-gu", 126.855);
//        var Gangseo = new naver.maps.LatLng(37.5612354280122, 126.822806689705);
        latMap.put("Gangseo-gu", 37.561);
        lngMap.put("Gangseo-gu", 126.822);
//        var Guro = new naver.maps.LatLng(37.4944054285618, 126.856300578972);
        latMap.put("Guro-gu", 37.494);
        lngMap.put("Guro-gu", 126.856);
//        var Geumcheon = new naver.maps.LatLng(37.4605675649317, 126.900820244409);
        latMap.put("Geumcheon-gu", 37.460);
        lngMap.put("Geumcheon-gu", 126.900);
//        var Yeongdeungpo = new naver.maps.LatLng(37.5223082885753, 126.910169467376);
        latMap.put("Yeongdeungpo-gu", 37.522);
        lngMap.put("Yeongdeungpo-gu", 126.910);
//        var Dongjak = new naver.maps.LatLng(37.4988768760718, 126.951641454383);
        latMap.put("Dongjak-gu", 37.498);
        lngMap.put("Dongjak-gu", 126.951);
//        var Gwanak = new naver.maps.LatLng(37.4673756905879, 126.94533715305);
        latMap.put("Gwanak-gu", 37.467);
        lngMap.put("Gwanak-gu", 126.945);
//        var Seocho = new naver.maps.LatLng(37.4732954673459, 127.031220311114);
        latMap.put("Seocho-gu", 37.473);
        lngMap.put("Seocho-gu", 127.031);
//        var Gangnam = new naver.maps.LatLng(37.4966438942877, 127.062985204247);
        latMap.put("Gangnam-gu", 37.496);
        lngMap.put("Gangnam-gu", 127.062);
//        var Songpa = new naver.maps.LatLng(37.5056192415444, 127.115295039723);
        latMap.put("Songpa-gu", 37.505);
        lngMap.put("Songpa-gu", 127.115);
//        var Gangdong = new naver.maps.LatLng(37.5504502432305, 127.147011841765);
        latMap.put("Gangdong-gu", 37.550);
        lngMap.put("Gangdong-gu", 127.147);
    }
    protected static void doInBackground() {
        try {
            setLatLngMap();
            for(int i = 0; i < REGION_NAME.size(); i++) {
                latitude = latMap.get(REGION_NAME.get(i)).toString();
                longitude = lngMap.get(REGION_NAME.get(i)).toString();
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey + "&units=metric");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject jsonObject = new JSONObject(result.toString());
                windSpeed.add(jsonObject.getJSONObject("wind").getDouble("speed"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}