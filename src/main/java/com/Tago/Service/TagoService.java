package com.Tago.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.Tago.dto.Bus;
import com.Tago.dto.BusLocation;
import com.Tago.dto.BusNode;
import com.Tago.dto.BusStation;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class TagoService {
	private final String APIKEY = "uKUTlevoYeGaFdHxFBKkbRsf7748MMtgbJ4VfvKL4oCqDgTdyLh5vq1Vsudk9OKl4lfMsCkFCJY9PiQxHmIqvA%3D%3D";

	public ArrayList<BusStation> getCrdntPrxmtSttnList(String gpsLati, String gpsLong) throws Exception {
		System.out.println("TagoService - getCrdntPrxmtSttnList 호출");

		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1613000/BusSttnInfoInqireService/getCrdntPrxmtSttnList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + APIKEY); /* Service Key */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "="
				+ URLEncoder.encode("json", "UTF-8")); /* 데이터 타입(xml, json) */
		urlBuilder.append("&" + URLEncoder.encode("gpsLati", "UTF-8") + "="
				+ URLEncoder.encode(gpsLati, "UTF-8")); /* WGS84 위도 좌표 */
		urlBuilder.append("&" + URLEncoder.encode("gpsLong", "UTF-8") + "="
				+ URLEncoder.encode(gpsLong, "UTF-8")); /* WGS84 경도 좌표 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
//		System.out.println(sb.toString()); // 정루소 목록 JSON

//        JsonObject key_response_value = response_Json.get("response").getAsJsonObject();
//        System.out.println(key_response_value);
//        System.out.println();
//        
//        JsonObject key_header_value = key_response_value.get("header").getAsJsonObject();
//        System.out.println(key_header_value);
//        System.out.println();

		JsonObject response_Json = JsonParser.parseString(sb.toString()).getAsJsonObject();
//		JsonObject body_value = response_Json.get("response").getAsJsonObject().get("body").getAsJsonObject();
//		System.out.println(body_value);
//		System.out.println();
		JsonObject items_value = response_Json.get("response").getAsJsonObject().get("body").getAsJsonObject()
				.get("items").getAsJsonObject();
		System.out.println(items_value);

		// 응답 데이터를 JSON 으로 변환
		JsonArray itemArr = items_value.get("item").getAsJsonArray();

		ArrayList<BusStation> stationList = new ArrayList<>();
		for (JsonElement item : itemArr) {
			Gson gson = new Gson();
			// 정류소 정보JSON 을 정류소정보 클래스로 변환
			BusStation busStation = gson.fromJson(item, BusStation.class);
			System.out.println(busStation);
			stationList.add(busStation);
		}
		return stationList;
	}

	public ArrayList<Bus> getBusStop(String citycode, String nodeid) throws Exception {
		System.out.println("TagoService - getBusStop 호출");
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1613000/ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + APIKEY); /* Service Key */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "="
				+ URLEncoder.encode("json", "UTF-8")); /* 데이터 타입(xml, json) */
		urlBuilder.append("&" + URLEncoder.encode("cityCode", "UTF-8") + "="
				+ URLEncoder.encode(citycode, "UTF-8")); /* 도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능 */
		urlBuilder.append("&" + URLEncoder.encode("nodeId", "UTF-8") + "="
				+ URLEncoder.encode(nodeid, "UTF-8")); /* 정류소ID [국토교통부(TAGO)_버스정류소정보]에서 조회가능 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

		JsonObject response_Json = JsonParser.parseString(sb.toString()).getAsJsonObject();

		JsonObject items_value = response_Json.get("response").getAsJsonObject().get("body").getAsJsonObject()
				.get("items").getAsJsonObject();
		System.out.println(items_value);

		// 응답 데이터를 JSON 으로 변환
		System.out.println(items_value.get("item").isJsonArray());
		JsonArray itemArr = items_value.get("item").getAsJsonArray();

		ArrayList<Bus> busList = new ArrayList<>();
		for (JsonElement item : itemArr) {
			Gson gson = new Gson();
			// 버스도착정보를 정보JSON 을 버스도착정보 클래스로 변환
			Bus bus = gson.fromJson(item, Bus.class);
			System.out.println(bus);
			busList.add(bus);
		}
		return busList;

	}

	public ArrayList<BusNode> nodeList(String citycode, String routeid) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteAcctoThrghSttnList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + APIKEY); /* Service Key */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("200", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "="
				+ URLEncoder.encode("json", "UTF-8")); /* 데이터 타입(xml, json) */
		urlBuilder.append("&" + URLEncoder.encode("cityCode", "UTF-8") + "="
				+ URLEncoder.encode(citycode, "UTF-8")); /* 도시코드 [상세기능4. 도시코드 목록 조회]에서 조회 가능 */
		urlBuilder.append("&" + URLEncoder.encode("routeId", "UTF-8") + "="
				+ URLEncoder.encode(routeid, "UTF-8")); /* 노선ID [상세기능1. 노선번호목록 조회]에서 조회 가능 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());
		ArrayList<BusNode> nodeList = new ArrayList<>(); //버스 전체 노선 반환
		JsonObject response_Json = JsonParser.parseString(sb.toString()).getAsJsonObject();
		
		//노선 목록이 저장된 Items
		JsonElement items = response_Json.get("response").getAsJsonObject()
										 .get("body").getAsJsonObject()	
										 .get("items");
		if(items.isJsonObject()) {//JsonObject 인지 확인
			JsonObject items_Json = items.getAsJsonObject();
			if(items_Json.get("item").isJsonArray()) {
				JsonArray itemList = items_Json.get("item").getAsJsonArray();
				for(JsonElement item : itemList) {
					
					BusNode node = new Gson().fromJson(item, BusNode.class);
					nodeList.add(node);
				}
				
			}else {
				JsonElement item = items_Json.get("item");
				BusNode node = new Gson().fromJson(item, BusNode.class);
				nodeList.add(node);
			}
		}
		System.out.println(nodeList);
		return nodeList;

	}

	public ArrayList<BusLocation> busLoc(String citycode, String routeid) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + APIKEY); /* Service Key */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "="
				+ URLEncoder.encode("json", "UTF-8")); /* 데이터 타입(xml, json) */
		urlBuilder.append("&" + URLEncoder.encode("cityCode", "UTF-8") + "="
				+ URLEncoder.encode(citycode, "UTF-8")); /* 도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능 */
		urlBuilder.append("&" + URLEncoder.encode("routeId", "UTF-8") + "="
				+ URLEncoder.encode(routeid, "UTF-8")); /* 노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

		// 응답 데이터를 JSON 으로 변환
		JsonObject response_Json = JsonParser.parseString(sb.toString()).getAsJsonObject();
		// 정류소 정보가 담긴 item 분류
		JsonElement items = response_Json.get("response").getAsJsonObject().get("body").getAsJsonObject().get("items");

		ArrayList<BusLocation> stationList = new ArrayList<>(); // 반환할 정류소 목록
		Gson gson = new Gson();
		if(items.isJsonNull()) {
			
			return stationList;
		}
		
		
		if (items.isJsonObject()) { // items가 OBJECT 인지 확인
			JsonObject items_value = items.getAsJsonObject();
			if (items_value.get("item").isJsonArray()) { // item 이 Array 인지 확인
				// item이 Array 이면
				JsonArray itemArr = items_value.get("item").getAsJsonArray();
				for (JsonElement item : itemArr) {
					// 정류소 정보JSON 을 정류소정보 클래스로 변환
					BusLocation busLoc = gson.fromJson(item, BusLocation.class);
					System.out.println(busLoc);
					stationList.add(busLoc);
				}

			} else {
				// item이 Array가 아니면
				JsonElement item = items_value.get("item").getAsJsonObject();
				BusLocation busLoc = gson.fromJson(item, BusLocation.class);
				stationList.add(busLoc);
			}

		}
		System.out.println("Loc성공");
		return stationList;

	}

}
