package com.Tago.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BusNode {
	private String routeid;
	private String nodeid;
	private String nodenm;
	private String nodeno;
	private String nodeord;
	private String gpslati;
	private String gpslong;
	private String updowncd;
	
	
}


//노선ID	routeid	30	옵	DJB30300004	노선ID
//정류소ID	nodeid	30	필	DJB1860090500	정류소ID
//정류소명	nodenm	30	필	유성고후문	정류소명
//정류소번호	nodeno	10	옵	44490	정류소번호
//정류소순번	nodeord	4	필	1	정류소순번
//정류소 Y좌표	gpslati	20	필	36.293125	WGS84 위도 좌표
//정류소 X좌표	gpslong	20	필	127.30067	WGS84 경도 좌표
//상하행구분코드	updowncd	1	옵	0	상하행구분코드 [0:상행, 1:하행]