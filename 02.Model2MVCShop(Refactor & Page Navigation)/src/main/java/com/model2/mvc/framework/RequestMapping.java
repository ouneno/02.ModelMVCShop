package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RequestMapping {
	
	///Field
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties; // actionmappint.properties : 연결
	
	///Constructor
	// RequestMapping : ActionServlet에서 받는 값들을 RequestMapping으로 보내줘서 mapping을 시켜준다.
	private RequestMapping(String resources) {
		
		// String과 Action을 key,value 값으로 받음
		map = new HashMap<String, Action>();
			//System.out.println("RequestMapping.....map까지 진행완료");
		
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);
			properties = new Properties();
			properties.load(in);
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ 
					in.close(); 
				} catch(Exception ex){ }
			}
		}
	} 
	
	///Method
	
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	// Action 클래스에서 Action 받기
	public Action getAction(String path){
		
		Action action = map.get(path);
		
		if(action == null){
			
			// 클래스이름을 properties를 참고해서 path 가져오기
			String className = properties.getProperty(path);
			className = className.trim(); // className 있을 수 있는 공백제거
				System.out.println("path : " + path);			
				System.out.println("className : " + className + "\n");
			
			try{
				Class c = Class.forName(className); // 로딩단계(클래스조사), 메모리에는 안올라옴
				Object obj = c.newInstance();		// c라는 인스턴스로부터 Object 얻어오기
				// 받아온 Action을 put에 key,value 값으로 지정
				if(obj instanceof Action){
					map.put(path, (Action)obj);
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action;
	}
}