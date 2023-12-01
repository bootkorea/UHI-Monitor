// 상수 정의
const DEFAULT_MAP_CENTER = new naver.maps.LatLng(37.5642135, 127.0016985);

//열섬온도
var HEAT_THRESHOLD;
// 전역 변수로 map 선언
var map;
// 전역 변수로 선택된 지역명을 저장
var selectedAreaName = null;

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
    }
    return map;
}

// loadMapData 지도정보를 불러오고 레이어를 띄움
function loadMapData(jsonString, HEAT_ISLAND_TEMP) {
    HEAT_THRESHOLD = HEAT_ISLAND_TEMP;
    var data = JSON.parse(jsonString);
    if (!map) map = initializeMap(); // 지도가 아직 초기화되지 않았다면 초기화
    displayData(data); //html에 지역&해당지역온도가 잘 넘어가는지 확인하기 위함
    loadDataLayer(map, data); //레이어를 띄움
}

// 데이터 표시 함수
function displayData(data) {
    var e = document.getElementById('regionNameLoaded');
    e.innerHTML = createDataString(data);
}
// 데이터 문자열 생성 함수, 지역 & 해당지역온도
function createDataString(data) {
    return Object.keys(data).map(key => `${key}: ${data[key]}`).join("<br>");
}

// 데이터 레이어 로드 함수
function loadDataLayer(map, data) {
    $.ajax({
        url:'https://raw.githubusercontent.com/bootkorea/UHI-Monitor/main/HeatIslandMap/app/src/main/assets/hangjeongdong.geojson',
        dataType: 'json',
        success: function(response) {
            $('#isCoordinatesLoaded').html('url을 통해서 geojson을 불러오는 것까지 성공');
            startDataLayer(map, response, data);
            $('#isCoordinatesLoaded').html('불러온 geojson을 가지고 서울시 레이어 추가 완료');
        },
        error: function(xhr, status, error) {
            $('#isCoordinatesLoaded').html('데이터를 불러오는 데 실패했습니다');
        }
    });
}

//지도에 레이어를 추가함
function startDataLayer(map, geojson, data) {
    map.data.addGeoJson(geojson);

    map.data.setStyle(function(feature) {
        var color = 'black'; // 기본 레이어 색상

        // 검색한 지역('구'만 )의 레이어 테두리를 초록색으로 변경
        // 만약 '동'에 해당하는 레이어의 색상을 조정하고 싶다면 geojson을 읽어보세요
        if (feature.getProperty('sggnm') === selectedAreaName) {
            return {
                strokeColor: 'green', // 테두리 색상을 초록색으로 변경
                strokeWeight: 2,
                icon: null
            };
        }

        // 지역 온도가 HEAT_THRESHOLD(열섬온도)를 초과하면 빨간색으로 그림
        // key: 지역명 -> 예) 종로구
        // date[key] : 온도 -> 예) 50.0
        // sggnm: geojson에서 '구'에 해당
        for (var key in data) {
            if (data[key] > HEAT_THRESHOLD && feature.getProperty('sggnm') == key) {
                color = 'red'; // 조건을 만족하면 색상 변경
                break; // 적절한 색상을 찾았으면 루프 종료
            }
        }
        //fillColor: 레이어 내부 색상을 설정
        //strokeColor: 레이어 테두리 색상 설정
        //strokeWeight: 레이터 테두리 두께
        //icon: null
        return {
            fillColor: color,
            strokeColor: color,
            strokeWeight: 2,
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


