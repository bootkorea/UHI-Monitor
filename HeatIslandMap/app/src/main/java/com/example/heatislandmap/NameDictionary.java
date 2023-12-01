package com.example.heatislandmap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameDictionary { // 번역을 위해서.. (구글 번역기, 파파고 해봤는데 안맞는게 많아서 어쩔수 없이 직접 만듦)
    protected static final Map<String, String> DONG_DICT = new HashMap<>();
    protected static final Map<String, String> GU_DICT = new HashMap<>();
    protected static final List<String> REGION_NAME = Arrays.asList( //서울에 있는 25개 구
            "Gangdong-gu", "Jongno-gu", "Jung-gu", "Yongsan-gu", "Seongdong-gu",
            "Gwangjin-gu", "Dongdaemun-gu", "Jungnang-gu", "Seongbuk-gu",
            "Gangbuk-gu", "Dobong-gu", "Nowon-gu", "Eunpyeong-gu",
            "Seodaemun-gu", "Mapo-gu", "Yangcheon-gu", "Gangseo-gu",
            "Guro-gu", "Geumcheon-gu", "Yeongdeungpo-gu", "Dongjak-gu",
            "Gwanak-gu", "Seocho-gu", "Gangnam-gu", "Songpa-gu"
    );

    protected static void Dong_Dictionary(){
        //종로구
        DONG_DICT.put("Sajik-dong", "사직동");
        DONG_DICT.put("Samcheong-dong", "삼청동");
        DONG_DICT.put("Pyeongchang-dong", "평창동");
        DONG_DICT.put("Muak-dong", "무악동");
        DONG_DICT.put("Gyonam-dong", "교남동");
        DONG_DICT.put("Gahoe-dong", "가회동");
        DONG_DICT.put("Jong-ro1.2.3.4-ga-dong", "종로1·2·3·4가동");
        DONG_DICT.put("Jong-ro5.6-ga-dong", "종로5·6가동");
        DONG_DICT.put("Ihwa-dong", "이화동");
        DONG_DICT.put("Changsin1-dong", "창신1동");
        DONG_DICT.put("Changsin2-dong", "창신2동");
        DONG_DICT.put("Sungin1-dong", "숭인1동");
        DONG_DICT.put("Sungin2-dong", "숭인2동");
        DONG_DICT.put("CheongwoonHyoja-dong", "청운효자동");
        DONG_DICT.put("Hyehwa-dong", "혜화동");

        //중구
        DONG_DICT.put("Sogong-dong", "소공동");
        DONG_DICT.put("Hoehyeon-dong", "회현동");
        DONG_DICT.put("Myeong-dong", "명동");
        DONG_DICT.put("Pil-dong", "필동");
        DONG_DICT.put("Jangchung-dong", "장충동");
        DONG_DICT.put("Gwanghui-dong", "광희동");
        DONG_DICT.put("Sogong-dong", "을지로동");
        DONG_DICT.put("Sindang5-dong", "신당5동");
        DONG_DICT.put("Hwanghak-dong", "황학동");
        DONG_DICT.put("Jungnim-dong", "중림동");
        DONG_DICT.put("Sindang-dong", "신당동");
        DONG_DICT.put("Dasan-dong", "다산동");
        DONG_DICT.put("Yaksu-dong", "약수동");
        DONG_DICT.put("Cheonggu-dong", "청구동");
        DONG_DICT.put("Donghwa-dong", "동화동");

        //용산구
        DONG_DICT.put("Huam-dong", "후암동");
        DONG_DICT.put("Yongsan-dong2-ga", "용산2가동");
        DONG_DICT.put("Hyochang-dong", "효창동");
        DONG_DICT.put("Yongmun-dong", "용문동");
        DONG_DICT.put("Ichon1-dong", "이촌1동");
        DONG_DICT.put("Ichon2-dong", "이촌2동");
        DONG_DICT.put("Itaewon1-dong", "이태원1동");
        DONG_DICT.put("Itaewon2-dong", "이태원2동");
        DONG_DICT.put("Seobinggo-dong", "서빙고동");
        DONG_DICT.put("Bogwang-dong", "보광동");
        DONG_DICT.put("Cheongpa-dong", "청파동");
        DONG_DICT.put("Hangang-ro-dong", "한강로동");
        DONG_DICT.put("Hannam-dong", "한남동");

        //성동구
        DONG_DICT.put("Wangsimni-dong2", "왕십리2동");
        DONG_DICT.put("Majang-dong", "마장동");
        DONG_DICT.put("Sageun-dong", "사근동");
        DONG_DICT.put("Haengdang1-dong", "행당1동");
        DONG_DICT.put("Haengdang2-dong", "행당2동");
        DONG_DICT.put("Eungbong-dong", "응봉동");
        DONG_DICT.put("Geumho-dong1-ga", "금호1가동");
        DONG_DICT.put("Geumho-dong4-ga", "금호4가동");
        DONG_DICT.put("Seongsu1ga1-dong", "성수1가1동");
        DONG_DICT.put("Seongsu1ga2-dong", "성수1가2동");
        DONG_DICT.put("Seongsu2ga1-dong", "성수2가1동");
        DONG_DICT.put("Seongsu2ga3-dong", "성수2가3동");
        DONG_DICT.put("Songjeong-dong", "송정동");
        DONG_DICT.put("Yongdap-dong", "용답동");
        DONG_DICT.put("Wangsimni-doSeondong", "왕십리도선동");
        DONG_DICT.put("Geumho-dong2.3-ga", "금호2·3가동");
        DONG_DICT.put("Oksu-dong", "옥수동");

        //광진구
        DONG_DICT.put("Hwayang-dong", "화양동");
        DONG_DICT.put("Gunja-dong", "군자동");
        DONG_DICT.put("Junggok1-dong", "중곡1동");
        DONG_DICT.put("Junggok2-dong", "중곡2동");
        DONG_DICT.put("Junggok3-dong", "중곡3동");
        DONG_DICT.put("Junggok4-dong", "중곡4동");
        DONG_DICT.put("Neung-dong", "능동");
        DONG_DICT.put("Guui1-dong", "구의1동");
        DONG_DICT.put("Guui2-dong", "구의2동");
        DONG_DICT.put("Guui3-dong", "구의3동");
        DONG_DICT.put("Gwangjang-dong", "광장동");
        DONG_DICT.put("Jayang1-dong", "자양1동");
        DONG_DICT.put("Jayang2-dong", "자양2동");
        DONG_DICT.put("Jayang3-dong", "자양3동");
        DONG_DICT.put("Jayang4-dong", "자양4동");

        //동대문구
        DONG_DICT.put("Hoegi-dong", "회기동");
        DONG_DICT.put("Hwigyeong1-dong", "휘경1동");
        DONG_DICT.put("Hwigyeong2-dong", "휘경2동");
        DONG_DICT.put("Cheongnyangni-dong", "청량리동");
        DONG_DICT.put("Yongshin-dong", "용신동");
        DONG_DICT.put("Jegi-dong", "제기동");
        DONG_DICT.put("Jeonnong1-dong", "전농1동");
        DONG_DICT.put("Jeonnong2-dong", "전농2동");
        DONG_DICT.put("Jeonnong3-dong", "전농2동");
        DONG_DICT.put("Dapsip-ri2-dong", "답십리2동");
        DONG_DICT.put("Jangan1-dong", "장안1동");
        DONG_DICT.put("Jangan2-dong", "장안2동");
        DONG_DICT.put("Imun1-dong", "이문1동");
        DONG_DICT.put("Imun2-dong", "이문2동");
        DONG_DICT.put("Dapsip-ri1-dong", "답십리1동");

        //중랑구
        DONG_DICT.put("Myeonmok2-dong", "면목2동");
        DONG_DICT.put("Myeonmok4-dong", "면목4동");
        DONG_DICT.put("Myeonmok5-dong", "면목5동");
        DONG_DICT.put("Myeonmok7-dong", "면목7동");
        DONG_DICT.put("Sangbong1-dong", "상봉1동");
        DONG_DICT.put("Sangbong2-dong", "상봉2동");
        DONG_DICT.put("Junghwa1-dong", "중화1동");
        DONG_DICT.put("Junghwa2-dong", "중화2동");
        DONG_DICT.put("Muk1-dong", "목1동");
        DONG_DICT.put("Muk2-dong", "목2동");
        DONG_DICT.put("Man-gu3-dong", "망우3동");
        DONG_DICT.put("Sinnae1-dong", "신내1동");
        DONG_DICT.put("Sinnae2-dong", "신내2동");
        DONG_DICT.put("Myeonmokbon-dong", "면목본동");
        DONG_DICT.put("Myeonmok3.8-dong", "면목3·8동");
        DONG_DICT.put("Mangubon-dong", "망우본동");

        //성북구
        DONG_DICT.put("Donam1-dong", "돈암1동");
        DONG_DICT.put("Donam2-dong", "돈암2동");
        DONG_DICT.put("Anam-dong", "안암동");
        DONG_DICT.put("Bomun-dong", "보문동");
        DONG_DICT.put("Jeongneung1-dong", "정릉1동");
        DONG_DICT.put("Jeongneung2-dong", "정릉2동");
        DONG_DICT.put("Jeongneung3-dong", "정릉3동");
        DONG_DICT.put("Jeongneung4-dong", "정릉4동");
        DONG_DICT.put("Gileum1-dong", "길음1동");
        DONG_DICT.put("Gileum2-dong", "길음2동");
        DONG_DICT.put("Wolgok1-dong", "월곡1동");
        DONG_DICT.put("Wolgok2-dong", "월곡2동");
        DONG_DICT.put("Jangwi1-dong", "장위1동");
        DONG_DICT.put("Jangwi2-dong", "장위2동");
        DONG_DICT.put("Seongbuk-dong", "성북동");
        DONG_DICT.put("Samseon-dong", "삼선동");
        DONG_DICT.put("Dongseon-dong", "동선동");
        DONG_DICT.put("Jongam-dong", "종암동");
        DONG_DICT.put("Seokgwan-dong", "석관동");

        //강북구
        DONG_DICT.put("Beon1-dong", "번1동");
        DONG_DICT.put("Beon2-dong", "번2동");
        DONG_DICT.put("Beon3-dong", "번3동");
        DONG_DICT.put("Suyu1-dong", "수유1동");
        DONG_DICT.put("Suyu2-dong", "수유2동");
        DONG_DICT.put("Suyu3-dong", "수유3동");
        DONG_DICT.put("Samyang-dong", "삼양동");
        DONG_DICT.put("Mia-dong", "미아동");
        DONG_DICT.put("Songjung-dong", "송중동");
        DONG_DICT.put("Songcheon-dong", "송천동");
        DONG_DICT.put("Samgaksan-dong", "삼각산동");
        DONG_DICT.put("Ui-dong", "우이동");
        DONG_DICT.put("Insu-dong", "인수동");

        //도봉구
        DONG_DICT.put("Ssangmun1-dong", "쌍문1동");
        DONG_DICT.put("Ssangmun2-dong", "쌍문2동");
        DONG_DICT.put("Ssangmun3-dong", "쌍문3동");
        DONG_DICT.put("Ssangmun4-dong", "쌍문4동");
        DONG_DICT.put("Banghak1-dong", "방학1동");
        DONG_DICT.put("Banghak2-dong", "방학2동");
        DONG_DICT.put("Banghak3-dong", "방학3동");
        DONG_DICT.put("Chang1-dong", "창1동");
        DONG_DICT.put("Chang2-dong", "창2동");
        DONG_DICT.put("Chang3-dong", "창3동");
        DONG_DICT.put("Chang4-dong", "창4동");
        DONG_DICT.put("Chang5-dong", "창5동");
        DONG_DICT.put("Dobong1-dong", "도봉1동");
        DONG_DICT.put("Dobong2-dong", "도봉2동");

        //노원구
        DONG_DICT.put("Wolgye1-dong", "월계1동");
        DONG_DICT.put("Wolgye2-dong", "월계2동");
        DONG_DICT.put("Wolgye3-dong", "월계3동");
        DONG_DICT.put("Gongneung2-dong", "공릉2동");
        DONG_DICT.put("Hagye1-dong", "하계1동");
        DONG_DICT.put("Hagye2-dong", "하계2동");
        DONG_DICT.put("Junggyebon-dong", "중계본동");
        DONG_DICT.put("Junggye1-dong", "중계1동");
        DONG_DICT.put("Junggye4-dong", "중계4동");
        DONG_DICT.put("Sanggye1-dong", "상계1동");
        DONG_DICT.put("Sanggye2-dong", "상계2동");
        DONG_DICT.put("Sanggye5-dong", "상계5동");
        DONG_DICT.put("Sanggye8-dong", "상계8동");
        DONG_DICT.put("Sanggye9-dong", "상계9동");
        DONG_DICT.put("Sanggye10-dong", "상계10동");
        DONG_DICT.put("Sanggye3.4-dong", "상계3·4동");
        DONG_DICT.put("Sanggye6.7-dong", "상계6·7동");
        DONG_DICT.put("Sanggye2.3-dong", "상계2·3동");
        DONG_DICT.put("Junggye2.3-dong", "중계2·3동");
        DONG_DICT.put("Gongneung1-dong", "공릉1동");

        //은평구
        DONG_DICT.put("Nokbeon-dong", "녹번동");
        DONG_DICT.put("Bulgwang1-dong", "불광1동");
        DONG_DICT.put("Galhyeon1-dong", "갈현1동");
        DONG_DICT.put("Galhyeon2-dong", "갈현2동");
        DONG_DICT.put("Gusan-dong", "구산동");
        DONG_DICT.put("Daejo-dong", "대조동");
        DONG_DICT.put("Eungam1-dong", "응암1동");
        DONG_DICT.put("Eungam2-dong", "응암2동");
        DONG_DICT.put("Sinsa1-dong", "신사1동");
        DONG_DICT.put("Sinsa2-dong", "신사2동");
        DONG_DICT.put("Jeungsan-dong", "증산동");
        DONG_DICT.put("Susaek-dong", "수색동");
        DONG_DICT.put("Jingwan-dong", "진관동");
        DONG_DICT.put("Bulgwang2-dong", "불광2동");
        DONG_DICT.put("Eungam3-dong", "응암3동");
        DONG_DICT.put("Yeokchon-dong", "역촌동");

        //서대문구
        DONG_DICT.put("Cheonyeon-dong", "천연동");
        DONG_DICT.put("Hongje1-dong", "홍제1동");
        DONG_DICT.put("Hongje3-dong", "홍제3동");
        DONG_DICT.put("Hongje2-dong", "홍제2동");
        DONG_DICT.put("Hongeun1-dong", "홍은1동");
        DONG_DICT.put("Hongeun2-dong", "홍은2동");
        DONG_DICT.put("Namgajwa1-dong", "남가좌1동");
        DONG_DICT.put("Namgajwa2-dong", "남가좌2동");
        DONG_DICT.put("Bukgajwa1-dong", "북가좌1동");
        DONG_DICT.put("Bukgajwa2-dong", "북가좌2동");
        DONG_DICT.put("Chunghyeon-dong", "충현동");
        DONG_DICT.put("Bukahyeon-dong", "북아현동");
        DONG_DICT.put("Sinchon-dong", "신촌동");
        DONG_DICT.put("Yeonhui-dong", "연희동");

        //마포구
        DONG_DICT.put("Yonggang-dong", "용강동");
        DONG_DICT.put("Daeheung-dong", "대흥동");
        DONG_DICT.put("Yeomni-dong", "염리동");
        DONG_DICT.put("Sinsu-dong", "신수동");
        DONG_DICT.put("Seogyo-dong", "서교동");
        DONG_DICT.put("Hapjeong-dong", "합정동");
        DONG_DICT.put("Mangwon-dong1", "망원1동");
        DONG_DICT.put("Mangwon-dong2", "망원2동");
        DONG_DICT.put("Yeonnam-dong", "연남동");
        DONG_DICT.put("Seongsan-dong1", "성산1동");
        DONG_DICT.put("Seongsan-dong2", "성산2동");
        DONG_DICT.put("Sangam-dong", "상암동");
        DONG_DICT.put("Dohwa-dong", "도화동");
        DONG_DICT.put("Sogang-dong", "서강동");
        DONG_DICT.put("Gongdeok-dong", "공덕동");
        DONG_DICT.put("Ahyeon-dong", "아현동");

        //양천구
        DONG_DICT.put("Mok1-dong", "목1동");
        DONG_DICT.put("Mok2-dong", "목2동");
        DONG_DICT.put("Mok3-dong", "목3동");
        DONG_DICT.put("Mok4-dong", "목4동");
        DONG_DICT.put("Sinwol1-dong", "신월1동");
        DONG_DICT.put("Sinwol2-dong", "신월2동");
        DONG_DICT.put("Sinwol3-dong", "신월3동");
        DONG_DICT.put("Sinwol4-dong", "신월4동");
        DONG_DICT.put("Sinwol5-dong", "신월5동");
        DONG_DICT.put("Sinwol6-dong", "신월6동");
        DONG_DICT.put("Sinwol7-dong", "신월7동");
        DONG_DICT.put("Sinjeong1-dong", "신정1동");
        DONG_DICT.put("Sinjeong2-dong", "신정2동");
        DONG_DICT.put("Sinjeong3-dong", "신정3동");
        DONG_DICT.put("Sinjeong6-dong", "신정6동");
        DONG_DICT.put("Sinjeong7-dong", "신정7동");
        DONG_DICT.put("Mok5-dong", "목5동");
        DONG_DICT.put("Sinjeong4-dong", "신정4동");

        //강서구
        DONG_DICT.put("Yeomchang-dong", "염창동");
        DONG_DICT.put("Deungchon-dong1", "등촌1동");
        DONG_DICT.put("Deungchon-dong2", "등촌2동");
        DONG_DICT.put("Deungchon-dong3", "등촌3동");
        DONG_DICT.put("Hwagokbon-dong", "화곡본동");
        DONG_DICT.put("Hwagok-dong2", "화곡2동");
        DONG_DICT.put("Hwagok-dong3", "화곡3동");
        DONG_DICT.put("Hwagok-dong4", "화곡4동");
        DONG_DICT.put("Hwagok-dong6", "화곡6동");
        DONG_DICT.put("Hwagok-dong8", "화곡8동");
        DONG_DICT.put("Gayang-dong1", "가양1동");
        DONG_DICT.put("Gayang-dong2", "가양2동");
        DONG_DICT.put("Gayang-dong3", "가양3동");
        DONG_DICT.put("Balsan-dong1", "발산1동");
        DONG_DICT.put("Gonghang-dong", "공항동");
        DONG_DICT.put("Banghwa1-dong", "방화1동");
        DONG_DICT.put("Banghwa2-dong", "방화2동");
        DONG_DICT.put("Banghwa3-dong", "방화3동");
        DONG_DICT.put("Hwagok-dong1", "화곡1동");
        DONG_DICT.put("Ujangsan-dong", "우장산동");

        //구로구
        DONG_DICT.put("Sindorim-dong", "신도림동");
        DONG_DICT.put("Gu-ro1-dong", "구로1동");
        DONG_DICT.put("Gu-ro3-dong", "구로3동");
        DONG_DICT.put("Gu-ro4-dong", "구로4동");
        DONG_DICT.put("Gu-ro5-dong", "구로5동");
        DONG_DICT.put("Gocheok1-dong", "고척1동");
        DONG_DICT.put("Gocheok2-dong", "고척2동");
        DONG_DICT.put("Gaebong2-dong", "개봉2동");
        DONG_DICT.put("Gaebong3-dong", "개봉3동");
        DONG_DICT.put("Oryu2-dong", "오류2동");
        DONG_DICT.put("Hang-dong", "항동");
        DONG_DICT.put("Sugung-dong", "수궁동");
        DONG_DICT.put("Garibong-dong", "가리봉동");
        DONG_DICT.put("Gu-ro2-dong", "구로2동");
        DONG_DICT.put("Gaebong1-dong", "개봉1동");

        //금천구
        DONG_DICT.put("Gasan-dong", "가산동");
        DONG_DICT.put("Doksan1-dong", "독산1동");
        DONG_DICT.put("Doksan-dong1", "독산1동");
        DONG_DICT.put("Doksan2-dong", "독산2동");
        DONG_DICT.put("Doksan3-dong", "독산3동");
        DONG_DICT.put("Doksan4-dong", "독산4동");
        DONG_DICT.put("Siheung1-dong", "시흥1동");
        DONG_DICT.put("Siheung2-dong", "시흥2동");
        DONG_DICT.put("Siheung3-dong", "시흥3동");
        DONG_DICT.put("Siheung4-dong", "시흥4동");
        DONG_DICT.put("Siheung5-dong", "시흥5동");

        //영등포구
        DONG_DICT.put("Yeouido-dong", "여의동");
        DONG_DICT.put("Dangsan-dong1", "당산1동");
        DONG_DICT.put("Dangsan-dong2", "당산2동");
        DONG_DICT.put("Sin-gil1-dong", "신길1동");
        DONG_DICT.put("Sin-gil3-dong", "신길3동");
        DONG_DICT.put("Sin-gil4-dong", "신길4동");
        DONG_DICT.put("Sin-gil5-dong", "신길5동");
        DONG_DICT.put("Sin-gil6-dong", "신길6동");
        DONG_DICT.put("Sin-gil7-dong", "신길7동");
        DONG_DICT.put("Daerim1-dong", "대림1동");
        DONG_DICT.put("Daerim2-dong", "대림2동");
        DONG_DICT.put("Daerim3-dong", "대림3동");
        DONG_DICT.put("Yangpyeong-dong1", "양평1동");
        DONG_DICT.put("Yangpyeong-dong1-ga", "양평1동");
        DONG_DICT.put("Yangpyeong-dong2-ga", "양평1동");
        DONG_DICT.put("Yeongdeungpobon-dong", "영등포본동");
        DONG_DICT.put("Yeongdeungpo-dong", "영등포동");
        DONG_DICT.put("Dorim-dong", "도림동");
        DONG_DICT.put("Munllae-dong", "문래동");

        //동작구
        DONG_DICT.put("Noryangjin2-dong", "노량진2동");
        DONG_DICT.put("Sang-do1-dong", "상도1동");
        DONG_DICT.put("Sang-do2-dong", "상도2동");
        DONG_DICT.put("Sang-do3-dong", "상도3동");
        DONG_DICT.put("Sang-do4-dong", "상도4동");
        DONG_DICT.put("Sadang1-dong", "사당1동");
        DONG_DICT.put("Sadang3-dong", "사당3동");
        DONG_DICT.put("Sadang4-dong", "사당4동");
        DONG_DICT.put("Sadang5-dong", "사당5동");
        DONG_DICT.put("Daebang-dong", "대방동");
        DONG_DICT.put("Sindaebang1-dong", "신대방1동");
        DONG_DICT.put("Sindaebang2-dong", "신대방2동");
        DONG_DICT.put("Heukseok-dong", "흑석동");
        DONG_DICT.put("Noryangjin1-dong", "노량진1동");
        DONG_DICT.put("Sadang2-dong", "사당2동");

        //관악구
        DONG_DICT.put("Boramae-dong", "보라매동");
        DONG_DICT.put("Cheongnim-dong", "청림동");
        DONG_DICT.put("Haengun-dong", "행운동");
        DONG_DICT.put("Nakseongdae-dong", "낙성대동");
        DONG_DICT.put("Seowon-dong", "서원동");
        DONG_DICT.put("Sinwon-dong", "신원동");
        DONG_DICT.put("Seorim-dong", "서림동");
        DONG_DICT.put("Sinllim-dong", "신림동");
        DONG_DICT.put("Nanhyang-dong", "난향동");
        DONG_DICT.put("Jowon-dong", "조원동");
        DONG_DICT.put("Daehak-dong", "대학동");
        DONG_DICT.put("Seonghyeon-dong", "성현동");
        DONG_DICT.put("Cheongnyong-dong", "청룡동");
        DONG_DICT.put("Nangok-dong", "난곡동");
        DONG_DICT.put("Samseong-dong", "삼성동");
        DONG_DICT.put("Miseong-dong", "미성동");
        DONG_DICT.put("Jungang-dong", "중앙동");
        DONG_DICT.put("Inheon-dong", "인헌동");
        DONG_DICT.put("Namhyeon-dong", "남현동");

        //서초구
        DONG_DICT.put("Seocho1-dong", "서초1동");
        DONG_DICT.put("Seocho2-dong", "서초2동");
        DONG_DICT.put("Seocho3-dong", "서초3동");
        DONG_DICT.put("Seocho4-dong", "서초4동");
        DONG_DICT.put("Jamwon-dong", "잠원동");
        DONG_DICT.put("Banpobon-dong", "반포본동");
        DONG_DICT.put("Banpo1-dong", "반포1동");
        DONG_DICT.put("Banpo2-dong", "반포2동");
        DONG_DICT.put("Banpo3-dong", "반포3동");
        DONG_DICT.put("Banpo4-dong", "반포4동");
        DONG_DICT.put("Bangbaebon-dong", "방배본동");
        DONG_DICT.put("Bangbae1-dong", "방배1동");
        DONG_DICT.put("Bangbae2-dong", "방배2동");
        DONG_DICT.put("Bangbae3-dong", "방배3동");
        DONG_DICT.put("Bangbae4-dong", "방배4동");
        DONG_DICT.put("Yangjae1-dong", "양재1동");
        DONG_DICT.put("Yangjae2-dong", "양재2동");
        DONG_DICT.put("Naegok-dong", "내곡동");

        //강남구
        DONG_DICT.put("Sinsa-dong", "신사동");
        DONG_DICT.put("Nonhyeon1-dong", "논현1동");
        DONG_DICT.put("Nonhyeon2-dong", "논현2동");
        DONG_DICT.put("Samseong1-dong", "삼성1동");
        DONG_DICT.put("Samseong2-dong", "삼성2동");
        DONG_DICT.put("Daechi1-dong", "대치1동");
        DONG_DICT.put("Daechi4-dong", "대치4동");
        DONG_DICT.put("Yeoksam1-dong", "역삼1동");
        DONG_DICT.put("Yeoksam2-dong", "역삼2동");
        DONG_DICT.put("Dogok1-dong", "도곡1동");
        DONG_DICT.put("Dogok2-dong", "도곡2동");
        DONG_DICT.put("Gaepo1-dong", "개포1동");
        DONG_DICT.put("Gaepo4-dong", "개포4동");
        DONG_DICT.put("Ilwonbon-dong", "일원본동");
        DONG_DICT.put("Ilwon1-dong", "일원1동");
        DONG_DICT.put("Ilwon2-dong", "일원2동");
        DONG_DICT.put("Suseo-dong", "수서동");
        DONG_DICT.put("Segok-dong", "세곡동");
        DONG_DICT.put("Apgujeong-dong", "압구정동");
        DONG_DICT.put("Cheongdam-dong", "청담동");
        DONG_DICT.put("Daechi2-dong", "대치2동");
        DONG_DICT.put("Gaepo2-dong", "개포2동");

        //송파구
        DONG_DICT.put("Pungnap1-dong", "풍납1동");
        DONG_DICT.put("Pungnap2-dong", "풍납2동");
        DONG_DICT.put("Geoyeo1-dong", "거여1동");
        DONG_DICT.put("Geoyeo2-dong", "거여2동");
        DONG_DICT.put("Macheon1-dong", "마천1동");
        DONG_DICT.put("Macheon2-dong", "마천2동");
        DONG_DICT.put("Bangi1-dong", "방이1동");
        DONG_DICT.put("Bangi2-dong", "방이2동");
        DONG_DICT.put("Oryun-dong", "오륜동");
        DONG_DICT.put("Ogeum-dong", "오금동");
        DONG_DICT.put("Songpa1-dong", "송파1동");
        DONG_DICT.put("Songpa2-dong", "송파2동");
        DONG_DICT.put("Seokchon-dong", "석촌동");
        DONG_DICT.put("Samjeon-dong", "삼전동");
        DONG_DICT.put("Garakbon-dong", "가락본동");
        DONG_DICT.put("Garak1-dong", "가락1동");
        DONG_DICT.put("Garak2-dong", "가락2동");
        DONG_DICT.put("Munjeong1-dong", "문정1동");
        DONG_DICT.put("Munjeong2-dong", "문정2동");
        DONG_DICT.put("Jamsilbon-dong", "잠실본동");
        DONG_DICT.put("Jamsil4-dong", "잠실4동");
        DONG_DICT.put("Jamsil6-dong", "잠실6동");
        DONG_DICT.put("Jamsil7-dong", "잠실7동");
        DONG_DICT.put("Jamsil2-dong", "잠실2동");
        DONG_DICT.put("Jamsil3-dong", "잠실3동");
        DONG_DICT.put("Jangji-dong", "장지동");
        DONG_DICT.put("Wirye-dong", "위례동");

        //강동구
        DONG_DICT.put("Gangil-dong", "강일동");
        DONG_DICT.put("Sangil-dong", "상일동");
        DONG_DICT.put("Myeon-gil1-dong", "명일1동");
        DONG_DICT.put("Myeon-gil2-dong", "명일2동");
        DONG_DICT.put("Godeok1-dong", "고덕1동");
        DONG_DICT.put("Godeok2-dong", "고덕2동");
        DONG_DICT.put("Amsa2-dong", "암사2동");
        DONG_DICT.put("Amsa3-dong", "암사3동");
        DONG_DICT.put("Cheonho1-dong", "천호1동");
        DONG_DICT.put("Cheonho3-dong", "천호3동");
        DONG_DICT.put("Seongnae1-dong", "성내1동");
        DONG_DICT.put("Seongnae2-dong", "성내2동");
        DONG_DICT.put("Seongnae3-dong", "성내3동");
        DONG_DICT.put("Dunchon1-dong", "둔촌1동");
        DONG_DICT.put("Dunchon2-dong", "둔촌2동");
        DONG_DICT.put("Amsa1-dong", "암사1동");
        DONG_DICT.put("Cheonho2-dong", "천호2동");
        DONG_DICT.put("Gil-dong", "길동");
    }

    protected static void Gu_Dictionary() {
        GU_DICT.put(REGION_NAME.get(0), "강동구");
        GU_DICT.put(REGION_NAME.get(1), "종로구");
        GU_DICT.put(REGION_NAME.get(2), "중구");
        GU_DICT.put(REGION_NAME.get(3), "용산구");
        GU_DICT.put(REGION_NAME.get(4), "성동구");
        GU_DICT.put(REGION_NAME.get(5), "광진구");
        GU_DICT.put(REGION_NAME.get(6), "동대문구");
        GU_DICT.put(REGION_NAME.get(7), "중랑구");
        GU_DICT.put(REGION_NAME.get(8), "성북구");
        GU_DICT.put(REGION_NAME.get(9), "강북구");
        GU_DICT.put(REGION_NAME.get(10), "도봉구");
        GU_DICT.put(REGION_NAME.get(11), "노원구");
        GU_DICT.put(REGION_NAME.get(12), "은평구");
        GU_DICT.put(REGION_NAME.get(13), "서대문구");
        GU_DICT.put(REGION_NAME.get(14), "마포구");
        GU_DICT.put(REGION_NAME.get(15), "양천구");
        GU_DICT.put(REGION_NAME.get(16), "강서구");
        GU_DICT.put(REGION_NAME.get(17), "구로구");
        GU_DICT.put(REGION_NAME.get(18), "금천구");
        GU_DICT.put(REGION_NAME.get(19), "영등포구");
        GU_DICT.put(REGION_NAME.get(20), "동작구");
        GU_DICT.put(REGION_NAME.get(21), "관악구");
        GU_DICT.put(REGION_NAME.get(22), "서초구");
        GU_DICT.put(REGION_NAME.get(23), "강남구");
        GU_DICT.put(REGION_NAME.get(24), "송파구");
    }
}