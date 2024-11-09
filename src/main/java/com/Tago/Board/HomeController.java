package com.Tago.Board;

import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Tago.Service.TagoService;
import com.Tago.dto.Bus;
import com.Tago.dto.BusLocation;
import com.Tago.dto.BusNode;
import com.Tago.dto.BusStation;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private TagoService tagoService;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		return "home";
	}

	@GetMapping("/getBusSttnList")
	@ResponseBody
	public ArrayList<BusStation> getBusSttnList(String gpsLati, String gpsLong) {
		System.out.println("/getBusSttnList - 선택한 좌표 주변 정류소 목록 조회");
		System.out.println("gpsLati : " + gpsLati);
		System.out.println("gpsLong : " + gpsLong);
		
		/* service - 좌표기반 정류소 목록 조회 기능 호출*/
		try {
			return tagoService.getCrdntPrxmtSttnList(gpsLati,gpsLong);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		

	}
	@GetMapping("/getBusTime")
	@ResponseBody
	public ArrayList<Bus>getBusStop(String citycode,String nodeid){
		System.out.println("/getBusStop - 버스 도착정보 조회");
		System.out.println("citynode : "+citycode );
		System.out.println("nodeid : "+ nodeid);
		
		
		try {
			return tagoService.getBusStop(citycode,nodeid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/busLocationList")
	public String busLocationList(String citycode, String routeid, Model model ) {
		System.out.println("/busLocationList - 버스 위치 정보 페이지 이동 요청");
		System.out.println("citycode : "+citycode );
		System.out.println("routeid : "+ routeid);
		
		try {
			ArrayList<BusNode>nodeList=tagoService.nodeList(citycode,routeid);
			ArrayList<BusLocation>busLoc=tagoService.busLoc(citycode,routeid);
			
			//버스 노선 정보-노선별 경유 정류소 목록
			model.addAttribute("nodeList", nodeList); //버스가 지나가는 정류소 목록
			
			//버스 위치 정보-노선별 버스 위치 목록
			model.addAttribute("busLocList",busLoc); //현재 4번 버스의 위치
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "BusLocInfo";
	}

}
