package com.binbin.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CreateUrlApp {

    public static void main(String[] args) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        Random random = new Random();
        int startNum = 32671;
        for (int i = 17; i <= 30; i++) { // 调用次数
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + "   " + i);

            System.out.println("http://192.168.10.70:8801/admin/yujian?startNum=" + (startNum + 1) + "&endNum=" + (startNum + 1));

            int endNum = startNum;
            endNum = endNum + 27000;
            if (endNum > 200000) {
                endNum = 200000 - startNum;
                endNum = 27000 - endNum;
//                System.out.println("http://192.168.10.70:8801/admin/ccxi?startNum=" + (startNum + 1) + "&endNum=200000");
                System.out.println("http://192.168.10.70:8801/admin/yujian?startNum=" + (startNum + 2) + "&endNum=200000");
                startNum = 0;

            }
            int randomNum = random.nextInt(100);
            endNum = endNum + randomNum;
            if (startNum == 0) {
                startNum = -1;
            }
//            System.out.println("http://192.168.10.70:8801/admin/ccxi?startNum=" + (startNum + 1) + "&endNum=" + endNum);
            System.out.println("http://192.168.10.70:8801/admin/yujian?startNum=" + (startNum + 2) + "&endNum=" + endNum);
            System.out.println();
            startNum = endNum;
        }
    }
//	private static String post(String urlStr, String body, String mimeType, String charset, Integer connTimeout, Integer readTimeout) throws Exception {
//		StringBuffer result = new StringBuffer();
//		URL url = new URL(urlStr);
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setRequestProperty("Content-type", mimeType);
//		conn.setRequestMethod("POST");
//		conn.setConnectTimeout(connTimeout);
//		conn.setReadTimeout(readTimeout);
//		conn.setDoInput(true);
//		conn.setDoOutput(true);
//		OutputStream os = conn.getOutputStream();
//		os.write(body.toString().getBytes(charset));
//		os.close();
//
//		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
//		String line;
//		while ((line = br.readLine()) != null) {
//			result.append(line);
//		}
//		br.close();
//
//		return result.toString();
//
//	}
//
//	public static  void riskSearch(YujianRequest info) {
//		JSONObject bizContent = new JSONObject(true);
//		try {
//			bizContent.put("name", info.getName());// 四要素认证结果
//			bizContent.put("certId", info.getCertId());// 人脸识别得分
//			bizContent.put("phone", info.getPhone());// 四要素认证结果
//			String result = post("http://test.amsservice.unisafecap.com/risk/info/query", bizContent.toJSONString(), "application/json", "UTF-8", 80000, 80000);
//			System.out.println(result);
//		}
//		catch (Exception e) {}
//	}
//
//	public static boolean query(int start, int end) {
//		try {
//			Connection conn = null;
//			Statement stmt = null;
//			ResultSet rs = null;
//
//			conn = DatabaseUtilsTest.getIstance().getConnection("jdbc:mysql://192.168.10.70:3306/bhxt_ams_db_02?characterEncoding=utf8");
//			Connection lconn = DatabaseUtilsTest.getIstance().getConnection("jdbc:mysql://192.168.10.70:3306/bhxt_ams_db_02?characterEncoding=utf8");
//
//			stmt = conn.createStatement();
//			StringBuffer sql = new StringBuffer();
//			sql.append("SELECT * FROM (");
//			sql.append("SELECT A.* FROM (SELECT id,customer_name,cert_id,phone FROM yujian_info_20190122 a ");
//			sql.append(") A WHERE A.id  <= ").append(end);
//			sql.append(" )b WHERE b.id >  ").append(start);
//			System.out.println(sql.toString());
//			rs = stmt.executeQuery(sql.toString());
//			int i = 0;
//			while (rs.next()) {
//				String CUSTOMERNAME = rs.getString("customer_name");
//				String CERTID = rs.getString("cert_id");
//				String PHONE = rs.getString("phone");
//
//				YujianRequest info = new YujianRequest();
//				info.setName(CUSTOMERNAME);
//				info.setPhone(PHONE);
//				info.setCertId(CERTID);
//				riskSearch(info);
//			}
//			lconn.commit();
//			DatabaseUtilsTest.close(lconn);
//			DatabaseUtilsTest.close(conn, rs, stmt);
//
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//
//	}
//
//	public static void main(String[] args) {
//		System.out.println("*****************百度黑名单查询start*******************");
//		long startTime = System.currentTimeMillis();
//		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//		for (int i = 0; i < 5; i++) {
//			cachedThreadPool.execute(new YujianTestThread(i*20000+0 , (i+1)*20000+0));
//		}
//		cachedThreadPool.shutdown();
//		long endTime = System.currentTimeMillis();
//		System.out.println("*****************百度黑名单查询 end *******************,处理数据消耗时间:" + ((endTime - startTime) / 1000 / 60) + "分钟");
//	}
//
//}
//
//class YujianTestThread extends Thread {
//	private int start;
//	private int end;
//
//	public YujianTestThread(int start, int end) {
//		super();
//		this.start = start;
//		this.end = end;
//	}
//
//	@Override
//	public void run() {
//		System.out.println(Thread.currentThread().getName() + "正在执行。。。");
//		CreateUrlApp.query(start, end);
//
//	}
//
//	public int getStart() {
//		return start;
//	}
//
//	public int getEnd() {
//		return end;
//	}

}
