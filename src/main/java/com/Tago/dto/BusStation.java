package com.Tago.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BusStation {
	private String citycode; //도시코드
	private String nodeid;   //정류소 ID
	private int nodeno;   //정류소번호
	private String nodenm;   //정류소명
	private String gpslati;  //위도좌표
	private String gpslong;  //경도좌표
	
	
}
