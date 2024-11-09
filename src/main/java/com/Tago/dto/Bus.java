package com.Tago.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Bus {
	private String nodeid;		//정류소ID
	private String nodenm;		//정류소명
	private String routeid;		//노선ID
	private String routeno;		//노선번호
	private String routetp;		//노선유형
	private String arrprevstationcnt;	// 도착예정버스 남은 정류장 수
	private String vehicletp;	// 도착예정버스 차량유형
	private String arrtime;		// 도착예정버스 도착예상시간
}
