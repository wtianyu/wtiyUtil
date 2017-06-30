package util;

import java.util.Date;

public class JsonToEntity {
	private static String uniqueId = new Date().getTime()+"wy";
	private static String jsonStrInit = "{\"id\":false,\"pid\":12.5,\"other\":{\"id\":\"lala\",\"pid\":null,\"name\":null," +
			"\"other2\":{\"id\":null,\"pid\":null,\"name\":\"wy\",\"other\":" +
			"{\"id\":null,\"pid\":\",\",\"name\":null,\"other4\":null}}},\"name\":\"12345\"}";
	private static StringBuffer classString = new StringBuffer();
	public static void main(String[] args) {
		if(args.length>0){
			jsonStrInit = args[0];
		}
		try{
			getChildenJson2(jsonStrInit,1);
			System.out.println(classString);
		}catch (Exception e) {
			System.out.println("The JSON format is not legal");
		}
	}

	private static String getChildenJson2(String jsonStr,int count){
		int start = -1;
		int end = -1;
		String jsonStrNew =jsonStr;
		if((start=jsonStr.indexOf("{"))!=jsonStr.lastIndexOf("{")){//只有一个json
			jsonStrNew = getChildenJson2(jsonStr.substring(start+1,jsonStr.lastIndexOf("}")),++count);
			StringBuilder jsonStrTemp = new StringBuilder(jsonStr);
			jsonStrTemp = jsonStrTemp.replace(jsonStrTemp.indexOf("{"), jsonStrTemp.lastIndexOf("}"), "{"+jsonStrNew);
			jsonStr = jsonStrTemp+"";
			start=jsonStr.indexOf("{");
		}
		end = jsonStrNew.indexOf("}")+1;
		StringBuilder jsonStrTemp = new StringBuilder(jsonStr);
		String jsonStrTest = jsonStr.substring(0, jsonStr.lastIndexOf("{"));
		String typeName = "";
		if("".equals(jsonStrTest)){
			typeName = "ROOT";
		}else{
			typeName = jsonStrTest.substring(jsonStrTest.lastIndexOf(",")+2,jsonStrTest.lastIndexOf(":")-1);
			typeName = (""+typeName.charAt(0)).toUpperCase()+typeName.substring(1);
		}
		subJsonStr(typeName,jsonStr.substring(jsonStr.indexOf("{")+1, jsonStr.indexOf("}")));
		jsonStrTemp = jsonStrTemp.replace(jsonStrTemp.lastIndexOf("{"), jsonStrTemp.lastIndexOf("}")+1, uniqueId+"");
		if(count==1){
			return null;
		}else{
			return jsonStrTemp+"";
		}
	}
	/**
	 * 对单个的json进行处理
	 * @param typeName
	 * @param jsonStrTemp
	 */
	private static void subJsonStr(String typeName,String jsonStrTemp) {
		if(classString.indexOf("public class "+typeName)>-1){//已存在
			return ;
		}
		classString.append("##################################################"+"\r\n");
		classString.append("public class "+typeName+" {"+"\r\n");
		String[] jsonArr1 = jsonStrTemp.split(",\"");
		if(jsonArr1.length>0){
			String[] keys = new String[jsonArr1.length];
			String[] valueTypes = new String[jsonArr1.length];
			for (int i = 0; i < jsonArr1.length; i++) {
				if(i!=jsonArr1.length-1&&"".equals(jsonArr1[i+1])){
					jsonArr1[i] = jsonArr1[i]+",\"";
				}else if(i!=jsonArr1.length-1&&"".equals(jsonArr1[i])){
					continue;
				}
				if(i!=0){
					jsonArr1[i] ="\""+jsonArr1[i];
				}
				String key = jsonArr1[i].substring(0, jsonArr1[i].indexOf(":"));
				String value = jsonArr1[i].substring(jsonArr1[i].indexOf(":")+1);
				String valueType = "";
				if(!value.contains("\"")&&!"null".equals(value)){
					valueType = baseTypeChange(value);
					if(valueType==""){
						valueType = "String";
					}
					if(uniqueId.equals(value)){
						valueType = key.substring(1,key.length()-1);
						valueType = (""+valueType.charAt(0)).toUpperCase()+valueType.substring(1);
					}
				}else{
					valueType = "String";
				}
				createEntityPara(typeName,key.substring(1,key.length()-1),valueType);
				keys[i] = key.substring(1,key.length()-1);
				valueTypes[i] = valueType;
			}
			createEntityMethod(keys,valueTypes);
			classString.append("}"+"\r\n");
			classString.append("\r\n"+"\r\n");
		}
	}

	private static String baseTypeChange(String value) {
		try{//测试int
			Integer.parseInt(value);
			return "int";
		}catch (Exception e) {
		}
		try{//测试boolean
			if("false".equals(value)||"true".equals(value)){
				return "boolean";
			}
		}catch (Exception e) {
		}
		try{//测试double
			Boolean.parseBoolean(value);
			return "double";
		}catch (Exception e) {
		}
		return "";
	}


	private static void createEntityMethod(String[] keys, String[] valueTypes) {
		for (int i = 0; i < keys.length; i++) {
			if(keys[i]!=null){
				//set
				classString.append("    public void  set"+(keys[i].charAt(0)+"").toUpperCase()+keys[i].substring(1)+"("+valueTypes[i]+" "+keys[i]+")"+"{"+"\r\n");
				classString.append("      this."+keys[i]+" = "+keys[i]+"\r\n");
				classString.append("    }"+"\r\n");
				//get
				classString.append("    public "+valueTypes[i]+" get"+(keys[i].charAt(0)+"").toUpperCase()+keys[i].substring(1)+"()"+"{"+"\r\n");
				classString.append("      return this."+keys[i]+"\r\n");
				classString.append("    }"+"\r\n");
			}
		}

	}

	private static void createEntityPara(String typeName,String key, String valueType) {
		classString.append("    private "+valueType+" "+key+";"+"\r\n");
	}

}
