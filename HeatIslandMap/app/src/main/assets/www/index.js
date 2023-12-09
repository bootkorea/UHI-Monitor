// 상수 정의
const DEFAULT_MAP_CENTER = new naver.maps.LatLng(37.5642135, 127.0016985);

// 구 중심 좌표
var Jongno = new naver.maps.LatLng(37.5949173193145, 126.977321294872);
var Jung = new naver.maps.LatLng(37.5601435645512, 126.995968139707);
var Yongsan = new naver.maps.LatLng(37.5313849658815, 126.979906988235);
var Seongdong = new naver.maps.LatLng(37.5510296891344, 127.041058533321);
var Gwangjin = new naver.maps.LatLng(37.5467202379314, 127.085744097681);
var Dongdaemun = new naver.maps.LatLng(37.581956547893, 127.054848127807);
var Jungnang = new naver.maps.LatLng(37.5978173799249, 127.092883176298);
var Seongbuk = new naver.maps.LatLng(37.6057019023968, 127.017579491168);
var Gangbuk = new naver.maps.LatLng(37.6434739135586, 127.011188975239);
var Dobong = new naver.maps.LatLng(37.6691020802884, 127.032368819558);
var Nowon = new naver.maps.LatLng(37.65251104799, 127.075034689337);
var Eunpyeong = new naver.maps.LatLng(37.6192112825748, 126.927022877199);
var Seodaemun = new naver.maps.LatLng(37.5777853091699, 126.939063095715);
var Mapo = new naver.maps.LatLng(37.559313492554, 126.908270027449);
var Yangcheon = new naver.maps.LatLng(37.5247894102082, 126.855477660234);
var Gangseo = new naver.maps.LatLng(37.5612354280122, 126.822806689705);
var Guro = new naver.maps.LatLng(37.4944054285618, 126.856300578972);
var Geumcheon = new naver.maps.LatLng(37.4605675649317, 126.900820244409);
var Yeongdeungpo = new naver.maps.LatLng(37.5223082885753, 126.910169467376);
var Dongjak = new naver.maps.LatLng(37.4988768760718, 126.951641454383);
var Gwanak = new naver.maps.LatLng(37.4673756905879, 126.94533715305);
var Seocho = new naver.maps.LatLng(37.4732954673459, 127.031220311114);
var Gangnam = new naver.maps.LatLng(37.4966438942877, 127.062985204247);
var Songpa = new naver.maps.LatLng(37.5056192415444, 127.115295039723);
var Gangdong = new naver.maps.LatLng(37.5504502432305, 127.147011841765);

var coordinates = [
    Dongdaemun, Eunpyeong, Jungnang, Gangnam, Seongdong,
    Seongbuk, Jongno, Yeongdeungpo, Gangdong, Gwangjin,
    Mapo,Guro,Yangcheon,Jung,Yongsan,Nowon,Gwanak,Seodaemun,Seocho,
    Dongjak,Songpa,Gangseo,Dobong,Gangbuk,Geumcheon
];

var name_num_map_cnt = 0;
var name_num_map = {};

//// 정보 창 생성
var marker = new Array(25);
var contentString = new Array(25);
var infowindow = new Array(25);
for(var i = 0; i < 25; ++i) {
    contentString[i] = `${i}`;
    infowindow[i] = new naver.maps.InfoWindow({
        content: contentString[i],
        maxWidth: 200,
        backgroundColor: "#ffffff",
        borderColor: "#000000",
        borderWidth: 1,
        anchorSize: new naver.maps.Size(0, 0),
        anchorSkew: true,
        anchorColor: "#ffffff",
        pixelOffset: new naver.maps.Point(20, 20)
    });
}

//열섬온도
var HEAT_THRESHOLD1;
var HEAT_THRESHOLD2;
var HEAT_AVG;

// 전역 변수로 map 선언
var map;
// 전역 변수로 선택된 지역명을 저장
var selectedAreaName = null;

var current_temperature_dict = {};

// 페이지 로드 시 지도 초기화, 페이지가 로드될 때 맵이 한 번만 생성되고, 이후에는 동일한 맵 인스턴스가 재사용
window.onload = function() {
    map = initializeMap();
};

// 지도 초기 설정 함수
function initializeMap() {
    if (!map) {
        map = new naver.maps.Map(document.getElementById('map'), {
            zoom: 10,
            mapTypeId: 'normal',
            center: DEFAULT_MAP_CENTER,
            zoomControl: true,

            //지도 줌 컨드롤러바를 지도 오른쪽에 설정해둠
            zoomControlOptions: {
                position: naver.maps.Position.TOP_RIGHT
            }
        });

        // 반복문을 사용하여 마커와 정보 창을 생성
        for (var i = 0; i < coordinates.length; i++) {
            // 마커 생성
            marker[i] = new naver.maps.Marker({
                map: map,
                position: coordinates[i],
                icon: {
                    content: '<img src="./img.png" style="solid transparent; display: block; position: absolute; width: 25px; height: 25px;">',
                }
            });
//            marker[i] = new naver.maps.Marker({
//                map: map,
//                position: coordinates[i]
//            });

            // 이벤트 리스너 등록
            naver.maps.Event.addListener(marker[i], "click", (function (marker, infowindow) {
                return function () {
                    if (infowindow.getMap()) {
                        infowindow.close();
                    } else {
                        infowindow.open(map, marker);
                    }
                };
            })(marker[i], infowindow[i]));
        }
    }
    return map;
}

// loadMapData 지도정보를 불러오고 레이어를 띄움
function loadMapData(jsonString, HEAT_ISLAND_TEMP1, HEAT_ISLAND_TEMP2, HEAT_ISLAND_AVG) {
    HEAT_THRESHOLD1 = HEAT_ISLAND_TEMP1;
    HEAT_THRESHOLD2 = HEAT_ISLAND_TEMP2;
    HEAT_AVG = HEAT_ISLAND_AVG;

    var data = JSON.parse(jsonString);
    if (!map) {
        map = initializeMap(); // 지도가 아직 초기화되지 않았다면 초기화
    }
    get_current_temperature_dict(data);
    loadDataLayer(map, data); //레이어를 띄움
    //displayData(data); //html에 지역&해당지역온도가 잘 넘어가는지 확인하기 위함
    update_infowindow(map);
}

function get_current_temperature_dict(data) {
    for(key in data) if(!current_temperature_dict[key]) {
        current_temperature_dict[key] = data[key];
        name_num_map[name_num_map_cnt] = key;
        name_num_map_cnt += 1;
    }
}

function update_infowindow(map) {
    for(var i = 0; i < 25; ++i) {
        contentString[i] = `${name_num_map[i]}: ${current_temperature_dict[name_num_map[i]]}`;

        naver.maps.Event.clearListeners(marker[i], "click");

        infowindow[i] = new naver.maps.InfoWindow({
            content: contentString[i],
            maxWidth: 200,
            backgroundColor: "#ffffff",
            borderColor: "#000000",
            borderWidth: 1,
            anchorSize: new naver.maps.Size(0, 0),
            anchorSkew: true,
            anchorColor: "#ffffff",
            pixelOffset: new naver.maps.Point(20, 20)
        });

        naver.maps.Event.addListener(marker[i], "click", (function (marker, infowindow) {
            return function () {
                if (infowindow.getMap()) {
                    infowindow.close();
                } else {
                    infowindow.open(map, marker);
                }
            };
        })(marker[i], infowindow[i]));
    }
}

// 데이터 표시 함수
//function displayData(data) {
//    var e = document.getElementById('regionNameLoaded');
//    e.innerHTML = `평균온도: ${HEAT_ISLAND_TEMP1} °C`;
//    //e.innerHTML = createDataString(data);
//}
// 데이터 문자열 생성 함수, 지역 & 해당지역온도
function createDataString(data) {
    return Object.keys(data).map(key => `${key}: ${data[key]}`).join("<br>");
}

// 데이터 레이어 로드 함수
function loadDataLayer(map, data) {
    $.ajax({
        url:'https://raw.githubusercontent.com/bootkorea/UHI-Monitor/main/HeatIslandMap/app/src/main/assets/sggnm.geojson',
        dataType: 'json',
        success: function(response) {
            //$('#isCoordinatesLoaded').html('url을 통해서 geojson을 불러오는 것까지 성공');
            startDataLayer(map, response, data);
            //$('#isCoordinatesLoaded').html('불러온 geojson을 가지고 서울시 레이어 추가 완료');
        },
        error: function(xhr, status, error) {
            $('#isCoordinatesLoaded').html('데이터를 불러오는 데 실패했습니다');
        }
    });
}

//지도에 레이어를 추가함
function startDataLayer(map, geojson, data) {
    map.data.removeGeoJson(geojson); // 기존에 그린 layer 삭제
    map.data.addGeoJson(geojson);

    map.data.setStyle(function(feature) {
        var color = 'lightGray'; // 기본 레이어 색상

        // 검색한 지역('구'만 )의 레이어 테두리를 초록색으로 변경
        // 만약 '동'에 해당하는 레이어의 색상을 조정하고 싶다면 geojson을 읽어보세요
        if (feature.getProperty('SIG_KOR_NM') === selectedAreaName) {
            return {
                strokeColor: 'green', // 테두리 색상을 초록색으로 변경
                strokeWeight: 3,
                icon: null
            };
        }
        // 지역 온도가 HEAT_THRESHOLD(열섬온도)를 초과하면 빨간색으로 그림
        // key: 지역명 -> 예) 종로구
        // date[key] : 온도 -> 예) 50.0
        // sggnm: geojson에서 '구'에 해당
        for (var key in data) {
            if ((feature.getProperty('SIG_KOR_NM') == key) && ((data[key] - HEAT_AVG) >= HEAT_THRESHOLD1)) {
                color = 'red'; // 조건을 만족하면 색상 변경
                break;
            }
            else if ((feature.getProperty('SIG_KOR_NM') == key) && ((data[key] - HEAT_AVG) >= HEAT_THRESHOLD2)) {
                color = 'yellow';
                break;
            }
            else {
                color = 'lightGray';
            }
        }
        //fillColor: 레이어 내부 색상을 설정
        //strokeColor: 레이어 테두리 색상 설정
        //strokeWeight: 레이터 테두리 두께
        //icon: null
        return {
            fillColor: color,
            strokeColor: 'black',
            strokeWeight: 0.5,
            icon: null
        };
    });
}

// 좌표로 카메라 이동 함수
function moveCameraToArea(latitude, longitude, areaName){
    if (!map) map = initializeMap(); // 지도가 아직 초기화되지 않았다면 초기화, 이해 안 됐다면 10번째 line부터 읽어보세요!

    //지정한 좌표로 카메라를 이동시킴
    map.setCenter(new naver.maps.LatLng(latitude, longitude));

    // 검색된 지역명 업데이트
    selectedAreaName = areaName;

    //줌레벨을 11으로 설정, 줌레벨이 높을수록 확대
    map.setZoom(11, true);

    // 스타일 갱신을 위해 이벤트 트리거
    // 선택된 지역을 초록색으로 표시하기 위해서 사용
    map.data.setStyle(map.data.getStyle());
}

// 네이버 웹 지도에 대한 정보가 필요한 경우 여기서 예제 참고! -> https://navermaps.github.io/maps.js.ncp/docs/tutorial-digest.example.html


