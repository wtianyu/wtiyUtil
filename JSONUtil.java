package util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * JSON工具类
 * @author wy
 *
 */
public class JSONUtil {

	/**
	 * Map集合转换JSON字符串
	 * @param maps
	 * @param flag true为迭代解析对象,false为一次解析对象
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static <T> String MapToJSON(Map<String, T> maps,boolean flag) throws IllegalArgumentException, IllegalAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		Iterator<Entry<String, T>> iterator = maps.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, T> map = iterator.next();
			String key = map.getKey();
			T value = map.getValue();
			if(flag&& !(value instanceof Comparable)&&value!=null){//开启迭代并且非基本类型
				sb.append("\""+key+"\":"+objectToJSON(value,flag));
				sb.append(",");
			}else{
				if(value instanceof String){
					sb.append("\""+key+"\":"+"\""+value+"\"");
				}else{
					sb.append("\""+key+"\":"+value);
				}
				sb.append(",");
			}
		}
		sb = new StringBuffer(sb.substring(0,sb.length()-1));
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * Object转换成map集合
	 * @param t object
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String,T> objectToMap(T t) throws IllegalArgumentException, IllegalAccessException {
		Map<String,T> maps=new LinkedHashMap<String, T>();
		Field[] fields=t.getClass().getDeclaredFields();
		for(Field field:fields){
			field.setAccessible(true);  
			if(field.get(t)!=null&&!"".equals(field.get(t).toString())){
				String key  = field.getName();
				T value = null;
//				if("a".equals(field.getName())){
//					value = (T) new String("无法识别");
//				}else{
//					value = (T) field.get(t);
//				}
				value = (T) field.get(t);
				maps.put(key, value);
			}else{
				maps.put(field.getName(), null);
			}
		}
		return maps;
	}
	
	/**
	 * object转换成json字符串
	 * @param t
	 * @param flag true为迭代解析对象,false为一次解析对象
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> String objectToJSON(T t,boolean flag) throws IllegalArgumentException, IllegalAccessException {
		return MapToJSON(objectToMap(t),flag);
	}
	
	/**
	 * objectList转换成json字符串
	 * @param tList
	 * @param flag true为迭代解析对象,false为一次解析对象
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	public static <T> String objectListToJSON(List<T> tList,boolean flag) throws IllegalArgumentException, IllegalAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < tList.size(); i++) {
			sb.append(MapToJSON(objectToMap(tList.get(i)),flag));
			sb.append(",");
		}
		sb.replace(sb.length()-1, sb.length(), "]");
		return sb.toString();
	}
	
}
