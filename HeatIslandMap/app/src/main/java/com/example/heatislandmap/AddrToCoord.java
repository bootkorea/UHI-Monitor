package com.example.heatislandmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddrToCoord {
    // 위도와 경도를 저장할 정적 변수
    private static Double latitude, longitude;

    // 좌표 변환 완료 시 호출될 리스너 인터페이스
    public interface CoordListener {
        void onCoordReceived(double latitude, double longitude);
    }

    // 주소를 좌표로 변환하는 스레드 클래스
    protected static class ConnectThread extends Thread {
        String urlStr;
        private CoordListener listener;

        // 생성자: 요청 URL과 리스너를 초기화
        public ConnectThread(String inStr, CoordListener listener) {
            this.urlStr = inStr;
            this.listener = listener;
        }

        // 스레드 실행: 주소를 좌표로 변환하고 결과를 리스너에게 전달
        @Override
        public void run() {
            try {
                request(urlStr);
                if (listener != null) {
                    listener.onCoordReceived(latitude, longitude);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 주소를 좌표로 변환하는 HTTP 요청을 처리하는 메서드
    private static void request(String urlStr) throws JSONException {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // HTTP 요청 설정
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "hfpnvnkly3");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", "SRXKg5LDVTMJGDUsabBY0UySVnAGwsqhJEWqR9HM");

            // HTTP 응답 코드 확인
            int response = conn.getResponseCode();

            // 정상 응답 처리
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

        // JSON 응답 파싱하여 위도와 경도 추출
        JSONObject jsonObject = new JSONObject(output.toString());
        JSONArray addresses = jsonObject.getJSONArray("addresses");
        JSONObject address = addresses.getJSONObject(0);

        longitude = address.getDouble("x");
        latitude = address.getDouble("y");
    }
}