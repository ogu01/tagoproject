<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Home</title>
<!-- bootStrap 추가 -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>
<style type="text/css">
.atag {
	text-decoration: none;
	color: inherit;
	cursor: pointer;
	display: block;
}

.atag:hover {
	background-color: #f4f4f4;
}
</style>


</head>
<body>
	<!-- div.container 시작 -->
	<div class="container" style="width: 500px">
		<div class="row">
			<div class="col">
				<button class="btn btn-primary w-100">자주 찾는 정류소</button>
			</div>
			<div class="col">
				<button class="btn btn-primary w-100">즐겨찾기</button>
			</div>
		</div>
		<!-- 지도 구현 row 시작-->
		<div class="row mb-3">
			<div class="col">
				<div class="accordion" id="mapArea">
					<!-- 반복 시작 -->
					<div class="accordion-item">
						<h2 class="accordion-header">
							<button class="accordion-button collapsed" type="button"
								data-bs-toggle="collapse" data-bs-target="#mapId"
								aria-expanded="true" aria-controls="mapId">지도 확인</button>
						</h2>
						<div id="mapId" class="accordion-collapse collapse show"
							data-bs-parent="#mapArea">
							<div class="accordion-body" id="map" style="height: 400px;">
								<h1>지도 출력</h1>
								<!-- <button onclick="getBusStationList('37.438697', '126.674931')">
									37.438697 , 126.674931(인천일보아카데미)</button>

								<button onclick="getBusStationList('37.443462', '126.701061')">
									37.443462 , 126.701061(구월동 뉴코아)</button> -->
							</div>
						</div>
					</div>
					<!-- 반복 종료 -->
				</div>
			</div>
		</div>
		<!-- 지도 구현 row 끝-->

		<!-- 정류소 목록 row 시작 -->
		<div class="row">
			<!-- 정류소 목록 출력 시작 -->
			<div class="accordion" id="sttnListArea">
				<!-- 반복 시작 -->

				<div class="accordion-item">
					<h2 class="accordion-header">
						<button class="accordion-button collapsed" type="button"
							data-bs-toggle="collapse" data-bs-target="#nodeid"
							aria-expanded="true" aria-controls="nodeid">정류소 이름 출력</button>
					</h2>
					<div id="nodeid" class="accordion-collapse collapse"
						data-bs-parent="#sttnListArea"></div>
				</div>

				<!-- 반복 종료 -->
			</div>
			<!-- 정류소 목록 출력 끝 -->
		</div>
		<!-- 정류소 목록 row 끝 -->
	</div>
	<!-- div.container 끝 -->

	<!-- 카카오 API -->
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5e367f4c9aab2b8aa782752c8ade6a4d"></script>

	<script type="text/javascript">
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션 
			center: new kakao.maps.LatLng(37.23742423300009, 127.0608333981544), //지도의 중심좌표.      
	level: 3 //지도의레벨(확대, 축소 정도) 
	};
	var map = new kakao.maps.Map(container, options); //지도생성 및 객체 리턴
	
	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
	    
	    // 클릭한 위도, 경도 정보를 가져옵니다 
	    var latlng = mouseEvent.latLng;
	    
	    var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
	    message += '경도는 ' + latlng.getLng() + ' 입니다';
	    console.log(message);
	  /*   var resultDiv = document.getElementById('result'); 
	    resultDiv.innerHTML = message; */
	    
	    getBusStationList(latlng.getLat(), latlng.getLng())
	    
	});
	</script>


	<!-- jQuery -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="text/javascript">
		function getBusStationList(gpsLati, gpsLong){
			let sttnList = [];
			$.ajax({
				url : "getBusSttnList",
				type : "get",
				dataType : "json",
				data : { "gpsLati" : gpsLati, "gpsLong" : gpsLong },
				async : false,
				success:function(res){
					console.log(res);
					sttnList = res;
				}
			});
			viewBusSttnList(sttnList);
			
		}
		
		
		function viewBusSttnList(sttnList){
			console.log("viewBusSttnList");
			/* 정류소 목록 출력 DIV 선택 */
			const sttnListAreaEl = document.getElementById('sttnListArea');
			/* 정류소 목록 초기화 */
			sttnListAreaEl.innerHTML = "";
			console.log(sttnListAreaEl.innerHTML);
			// DJB8005559
			for(let sttn of sttnList){
				/* <div class="accordion-item"> */
				let accordionItemDiv = document.createElement('div'); // <div></div>
				accordionItemDiv.classList.add('accordion-item'); // <div class="accordion-item"></div>
				accordionItemDiv.innerHTML = `
		            <h2 class="accordion-header">
		              <button
		              	id = "selectsttn"
		                class="accordion-button collapsed"
		                type="button"
		                data-bs-toggle="collapse"
		                data-bs-target="#\${sttn.nodeid}"
		                aria-expanded="true"
		                aria-controls="\${sttn.nodeid}"
		                onclick="getBusArrInfo( '\${sttn.citycode}', '\${sttn.nodeid}','\${sttn.gpslati}' ,'\${sttn.gpslong}')"
		              >
		                \${sttn.nodenm}
		              </button>
		            </h2>
		            <div
		              id="\${sttn.nodeid}"
		              class="arriveList accordion-collapse collapse"
		              data-bs-parent="#sttnListArea"
		            ></div>`;
				
				sttnListAreaEl.appendChild(accordionItemDiv);
				console.log(sttn);
			
			}
			
		}
		/* 지도에 있는 마커 */
		let marker = null;
		function getBusArrInfo(citycode, nodeid, gpslati, gpslong){
			console.log("버스 도착정보 조회")
			console.log("도시코드 : " + citycode)
			console.log("정류소 : " + nodeid);
			console.log("위도 : "+gpslati);
			console.log("경도 : "+gpslong);
			let busArrInfoDiv = document.querySelector("#"+nodeid);
			
			let arriveListEls = document.querySelectorAll('.arriveList');
			for(let arriveList of arriveListEls){
				if(arriveList != busArrInfoDiv){
					arriveList.innerHTML = "";
				}
			}
			
			let accordionBodyDiv = busArrInfoDiv.querySelector('.accordion-body');
			if( accordionBodyDiv != null ){
				busArrInfoDiv.innerHTML = "";
				return;				
			}
			
			let arrInfoList = [];
			$.ajax({
				url : "getBusTime",
				type : "get",
				dataType : "json",
				data : { "citycode" : citycode, "nodeid" : nodeid },
				async : false,
				success:function(res){
					console.log(res);
					arrInfoList = res;
				}
			});	
			viewBusArriveList(arrInfoList, nodeid,citycode);
			
			
			// 마커가 표시될 위치입니다 
			var markerPosition  = new kakao.maps.LatLng(gpslati, gpslong); 
			
			
			if(marker == null){
				// 마커를 생성합니다
				marker = new kakao.maps.Marker({
				    position: markerPosition
				});

				// 마커가 지도 위에 표시되도록 설정합니다
				marker.setMap(map);				
			}else{
				 // 마커 위치를 클릭한 위치로 옮깁니다
			    marker.setPosition(markerPosition);
			}
		    map.panTo(markerPosition);


			
			
			
		
		}
		
		function viewBusArriveList(arrInfoList, nodeid,citycode){
			console.log('viewBusArriveList 호출');
			/* 도착정보를 출력할 DIV 선택 */
			let busListDiv = document.querySelector("#"+nodeid);
			
			/* 버스정보 DIV 생성 */
			let accordionDiv = document.createElement('div');
			accordionDiv.classList.add('accordion-body');
			
			/* for문 시작*/
			for(let arrInfo of arrInfoList){
				let infoDiv = document.createElement('div');
				infoDiv.innerHTML = `
					\${arrInfo.routeno} 번  \${arrInfo.arrprevstationcnt} 정거장 전<hr>`;
					infoDiv.addEventListener('click',function(){
					/*  <a href="busLocationList?citycode=\${citycode}&routeid=\${arrInfo.routeid}&selectnode=\${nodeid}" class = "atag">*/
					window.open(`busLocationList?citycode=\${citycode}&routeid=\${arrInfo.routeid}&selectnode=\${nodeid}`,
							'routePopUp',
							'width=500,height=700');
				});
					accordionDiv.appendChild(infoDiv);
			}
			/* for문 종료 */
			
			
			/* 도착정보를 출력할 DIV에 추가 */
			busListDiv.appendChild(accordionDiv);
			
		}
		
		function receiveGPS(citycode,nodeid, gpslati, gpslong) {
			 			 
			 getBusStationList(gpslati, gpslong);			 
			 getBusArrInfo(citycode, nodeid, gpslati, gpslong);
		}
		
		
		
		
		
	</script>

</body>
</html>






