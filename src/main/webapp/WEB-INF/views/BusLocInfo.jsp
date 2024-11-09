<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.busLoc{
		display: flex;
	}
	.busLoc img{
		padding-left: 5px;
	}
	
	
</style>

</head>
<body>

	<h1>BusLocInfo.jsp</h1>

	<div id="routeList">
		<c:forEach items="${nodeList}" var="route">

			<c:set var="check" value="true" />
			<c:forEach items="${busLocList}" var="busLoc">
				<c:if test="${busLoc.nodenm eq route.nodenm}">
					<c:set var="check" value="false" />
				</c:if>
			</c:forEach>
			<c:if test="${check == 'false'}">
				<div id= "${route.nodeid}" tabindex="-1" class= "border border-danger p-3 mb-1 rounded" onclick = "send('${param.citycode}','${route.nodeid}','${route.gpslati}', '${route.gpslong}')">${route.nodenm}<img  src="resources/ImgUpLoad/bus.png" style="width: 20px;">${busLoc.vehicleno} </div>
			</c:if>
			<c:if test="${check == 'true'}">
				<div id= "${route.nodeid}" tabindex="-1" class= "border p-3 mb-1 rounded" onclick = "send('${param.citycode}','${route.nodeid}','${route.gpslati}' ,'${route.gpslong}')">${route.nodenm}</div>
			</c:if>


		</c:forEach>
	</div>
<script type="text/javascript">
let selectNode ='${param.selectnode}';
console.log("Home.jsp에서 선택한 정류소 아이디 : " + selectNode);

document.getElementById(selectNode).focus();

function send(citycode,nodeid,gpslati ,gpslong) {
    console.log("전송할 좌표 : " + gpslati + ", " + gpslong);
    window.opener.receiveGPS(citycode,nodeid,gpslati ,gpslong);
  }
</script>


</body>


</html>