package com.example.heatislandmap;

import static com.example.heatislandmap.MainActivity.marker;
import static com.example.heatislandmap.MainActivity.naverMap;

import android.os.Handler;
import android.os.Looper;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddrToCoord {
    private static Double latitude, longitude;
    protected static class ConnectThread extends Thread {
        Handler handler = new Handler(Looper.getMainLooper());
        String urlStr;

        public ConnectThread(String inStr){
            urlStr = inStr;
        }

        public void run(){
            try{
                request(urlStr);

                handler.post(new Runnable(){
                    public void run(){
                        LatLng location = new LatLng(latitude, longitude); // 장소의 좌표 가져 오기

                        // 해당 좌표(해당 지역의 행정 구역)로 카메라 이동
                        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(location,9)
                                .animate(CameraAnimation.Fly, 1000);

                        naverMap.moveCamera(cameraUpdate);

                        // 마커 이동
                        marker.setPosition(new LatLng(latitude, longitude));
                        marker.setMap(naverMap);
                    }
                });
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    // request 보내서 좌표값 얻어 오기
    private static void request(String urlStr) throws JSONException {
        StringBuilder output = new StringBuilder();
        try{
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "hfpnvnkly3");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", "SRXKg5LDVTMJGDUsabBY0UySVnAGwsqhJEWqR9HM");

            int response = conn.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(output.toString());
        JSONArray addresses = jsonObject.getJSONArray("addresses");
        JSONObject address = addresses.getJSONObject(0);

        longitude = address.getDouble("x");
        latitude = address.getDouble("y");
    }
}