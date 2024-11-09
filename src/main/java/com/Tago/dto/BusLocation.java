package com.Tago.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BusLocation {
	private String routenm;
	private String gpslati;
	private String gpslong;
	private String nodeord;
	private String nodenm;
	private String nodeid;
	private String routetp;
	private String vehicleno;
}

//항목명(국문)	항목명(영문)	항목크기	항목구분	샘플데이터	항목설명
//결과코드	resultCode	2	필수	00	결과코드
//결과메시지	resultMsg	50	필수	OK	결과메시지
//한 페이지 결과 수	numOfRows	4	필수	10	한 페이지 결과 수
//페이지 번호	pageNo	4	필수	1	페이지번호
//전체 결과 수	totalCount	4	필수	3	전체 결과 수
//노선번호	routenm	30	필수	202	노선번호
//맵매칭 Y좌표	gpslati	20	옵션	36.333445	WGS84 위도 좌표
//맵매칭 X좌표	gpslong	20	옵션	127.438859	WGS84 경도 좌표
//정류소 순서	nodeord	4	필수	1	정류소 순서
//정류소명	nodenm	30	필수	대전역동광장종점	정류소명
//정류소ID	nodeid	30	필수	DJB8005621	정류소ID
//노선유형	routetp	10	필수	간선버스	노선유형
//차량번호	vehicleno	10	필수	대전99가9999	차량번호