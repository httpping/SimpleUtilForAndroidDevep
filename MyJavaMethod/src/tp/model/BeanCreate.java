package tp.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * 根据 json 创建 bean  和  dao
 * @author Administrator
 *
 */
public class BeanCreate {

	public static java.util.List<String> arrayList = new ArrayList<String>();

	public static String ORDER_STRING = "private String ";
	public static String ORDER_PUBLIC_STRING = "public static final String  ";

	
	public static String fileName ;
	
	private static CreateBeanFile beanCreate;
	private static CreateBeanFile daoBeanCreate;
	
	public static void main(String[] args) throws IOException {

		/*
		 * "id":"1", "name":"蜀渔坊", "region_name":"平江新城", "type_name":"川菜",
		 * "phone":"0512-8888-888", "address":"江苏省苏州市平江区人民路3188号",
		 * "longitude":"31.332818", "latitude":"120.826555",
		 * "detail_url":"\/download\/p1.png", "description":"要的就是够辣，才够味道..",
		 * "qr_code":"\/download\/picture\/maps\/p1.png"
		 */
		
		
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(
				System.in));

		String s = null;
		while (!(s = bReader.readLine()).equals("end")) {
			arrayList.add(s);
		}
		
		beanCreate = new CreateBeanFile();
		beanCreate.createFile("ModelBean.java");
		fileName ="ModelBean";
		daoBeanCreate = new CreateBeanFile();
		daoBeanCreate.createFile("DaoBean.java");

		for (int i = 0; i < arrayList.size(); i++) {
			parseStr(arrayList.get(i));
		}

		for (int i = 0; i < arrayList.size(); i++) {
			parseStrFinal(arrayList.get(i));
		}

		/**
		 * json 方法创建
		 */
		List<String> jsonMehtods = new ArrayList<String>();
		for (int i = 0; i < arrayList.size(); i++) {
			jsonMehtods.add(parseJson(arrayList.get(i)));
		}
		creteJsonMehtod(jsonMehtods);
		createJsonsMehtod();
		
		/**
		 * 创建读取
		 */
		
		
		for (int i = 0; i < arrayList.size(); i++) {
			createDbToObjectMehtod(arrayList.get(i));
			
			daoBeanCreate.writeLine(createDbToObjectMehtod(arrayList.get(i)));
		}
		
		/**
		 * 创建表
		 */
		createDbTable(arrayList);
		
		/**
		 * 创建插入
		 */
		createObjectToInsrtToDbMehtod(arrayList);
	}

	/**
	 * 私有的
	 * 
	 * @param string
	 */
	private static void parseStr(String string) {
		if (string == null) {
			return;
		}
		String[] strings = string.split(":");

		String name = strings[0];
		String value = strings[1];
		name = name.replace("\"", "").trim();
		System.out.println(ORDER_STRING + StringUtls.parseFristLowParamName(name) + ";");
		
		try {
			beanCreate.writeLine(ORDER_STRING + StringUtls.parseFristLowParamName(name) + ";");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	/**
	 * 共有的
	 * 
	 * @param name
	 * @return
	 */
	private static void parseStrFinal(String string) {
		if (string == null) {
			return;
		}
		String[] strings = string.split(":");

		String name = strings[0];
		String value = strings[1];
		name = name.replace("\"", "").trim();
		System.out.println(ORDER_PUBLIC_STRING + name.toUpperCase() + "=\""
				+ name.toLowerCase() + "\";");
		
		try {
			beanCreate.writeLine(ORDER_PUBLIC_STRING + name.toUpperCase() + "=\""
					+ name.toLowerCase() + "\";");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 解析json
	 */
	private static String parseJson(String string) {
		if (string == null) {
			return "";
		}
		String[] strings = string.split(":");

		String name = strings[0];
		String value = strings[1];
		name = name.replace("\"", "").trim();

		String value2 = "if (jsonObject.has(" + name.toUpperCase() + ")) {"
				+ "objectBean." + StringUtls.parseFristLowParamName(name)
				+ " =jsonObject.getString(" + name.toUpperCase() + ");" + "}";
		return value2;
		// System.out.println(value2);
	}

	/**
	 * 创建json方法
	 * @param list
	 * @throws IOException 
	 */
	private static void creteJsonMehtod(List<String> list) throws IOException {

		System.out.println("public static  "+fileName+"  parseJson(String json) {");
		System.out.println(" "+fileName+"  objectBean = new  "+fileName+" ();");
		System.out.println("try {");
		System.out.println("JSONObject jsonObject = new JSONObject(json);");
		
		
		beanCreate.writeLine("public static  "+fileName+"  parseJson(String json) {");
		beanCreate.writeLine(" "+fileName+"  objectBean = new  "+fileName+" ();");
		beanCreate.writeLine("try {");
		beanCreate.writeLine("JSONObject jsonObject = new JSONObject(json);");
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
			beanCreate.writeLine(list.get(i));
		}
		System.out
				.println("} catch (Exception e) {return null;}return objectBean;}");
		
		beanCreate.writeLine("} catch (Exception e) {return null;}return objectBean;}");
		
		

	}
	
	/**
	 * 创建json 数据方法
	 * @throws IOException 
	 */
	private static void createJsonsMehtod() throws IOException{
	 
		System.out.println("public static List< "+fileName+" > createFromJsonArray(String json){");
		System.out.println("List< "+fileName+" > modeBens  =new ArrayList< "+fileName+" >();");
		System.out.println("try {");
		System.out.println("JSONArray jsonArray = new JSONArray(json);");
		System.out.println("for (int i = 0; i < jsonArray.length(); i++) {   "+fileName+"   bean =  "+fileName+" .parseJson(jsonArray.getString(i));if (bean!= null) {modeBens.add(bean);}}} catch (JSONException e) {e.printStackTrace();}");
		System.out.println("	return modeBens; }"); 
		
		beanCreate.writeLine("public static List< "+fileName+" > createFromJsonArray(String json){");
		beanCreate.writeLine("List< "+fileName+" > modeBens  =new ArrayList< "+fileName+" >();");
		beanCreate.writeLine("try {");	
		beanCreate.writeLine("JSONArray jsonArray = new JSONArray(json);");
		beanCreate.writeLine("for (int i = 0; i < jsonArray.length(); i++) {   "+fileName+"   bean =  "+fileName+" .parseJson(jsonArray.getString(i));if (bean!= null) {modeBens.add(bean);}}} catch (JSONException e) {e.printStackTrace();}");
		beanCreate.writeLine("	return modeBens; }");
	
	}
	
	
	/**
	 *  创建解析 db获取方法
	 */
	private static String createDbToObjectMehtod(String string){
		if (string == null) {
			return "";
		}
		String[] strings = string.split(":");

		String name = strings[0];
		String value = strings[1];
		name = name.replace("\"", "").trim();
		 
		String result = "bean.set"+StringUtls.parseFristUpdateParamName(name)+"(c.getString(c.getColumnIndex(MODEL."+name.toUpperCase()+")));";
		System.out.println(result);
		return result;
	}

	/**
	 *  创建解析 db存储或更新方法 
	 *   ContentValues cvs = new ContentValues();
			 cvs.put(UserBean.ALIAS,userBean.getAlias());
			 cvs.put(UserBean.AVATAR, userBean.getAvatar());
			 cvs.put(UserBean.ID, userBean.getId());
			 cvs.put(UserBean.USERNAME, userBean.getUsername());
			 cvs.put(UserBean.TYPE, userBean.getType());
			 cvs.put(UserBean.EXTENDED_INFO, userBean.getExtendedInfo());
			 cvs.put(UserBean.MOBILE, userBean.getMobile());
			 cvs.put(UserBean.FLAG, userBean.getFlag());
			 cvs.put(UserBean.SIGNATURE, userBean.getSignature());
	 */
	private static void createObjectToInsrtToDbMehtod(List<String> list){
		
		if (list ==null) {
			return ;
		}
		System.out.println("ContentValues cvs = new ContentValues();");
		for (int i = 0; i < list.size(); i++) {
			String string = list.get(i);
			String[] strings = string.split(":");
			String name = strings[0];
			String value = strings[1];
			name = name.replace("\"", "").trim();
			String result = "cvs.put(MODEL."+name.toUpperCase()+",bean.get"+StringUtls.parseFristUpdateParamName(name)+"());";
			System.out.println(result);
		}
	}
	
	
	/**
	 * 创建db表 
	 */
	public static void createDbTable(List<String> list){
		String result ="create table  if not exists (";
		for (int i = 0; i < list.size(); i++) {
			String[] strings = list.get(i).split(":");
			String name = strings[0];
			String value = strings[1];
			name = name.replace("\"", "").trim();
			result += name +" text, ";
		}
		
		result +=")";
		System.out.println(result);
	}	
}
