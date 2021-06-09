package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RequestMapping {
	
	///Field
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties; // actionmappint.properties : ����
	
	///Constructor
	// RequestMapping : ActionServlet���� �޴� ������ RequestMapping���� �����༭ mapping�� �����ش�.
	private RequestMapping(String resources) {
		
		// String�� Action�� key,value ������ ����
		map = new HashMap<String, Action>();
			//System.out.println("RequestMapping.....map���� ����Ϸ�");
		
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);
			properties = new Properties();
			properties.load(in);
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties ���� �ε� ���� :"  + ex);
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
	
	// Action Ŭ�������� Action �ޱ�
	public Action getAction(String path){
		
		Action action = map.get(path);
		
		if(action == null){
			
			// Ŭ�����̸��� properties�� �����ؼ� path ��������
			String className = properties.getProperty(path);
			className = className.trim(); // className ���� �� �ִ� ��������
				System.out.println("path : " + path);			
				System.out.println("className : " + className + "\n");
			
			try{
				Class c = Class.forName(className); // �ε��ܰ�(Ŭ��������), �޸𸮿��� �ȿö��
				Object obj = c.newInstance();		// c��� �ν��Ͻ��κ��� Object ������
				// �޾ƿ� Action�� put�� key,value ������ ����
				if(obj instanceof Action){
					map.put(path, (Action)obj);
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action;
	}
}