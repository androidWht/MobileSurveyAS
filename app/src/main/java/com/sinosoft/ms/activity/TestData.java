package com.sinosoft.ms.activity;

public class TestData {
	private static String title;
	private static String content;
	
	
	//String title = "移动查勘报告打印凭证";
	/*String centext = "================================\n"

		+ "报案号: 601011314481000000075\n" + "被保险人: 王五\n" + "报案人: 张三\n"
				+ "涉案险种: 玻璃破碎险\n" + "出险原因: 碰撞\n" + "交强险承保公司: 众诚保险\n"
				+ "保单号: 301071224481000000308\n" + "商业险承保公司: 众诚保险\n"
				+ "保单号: 301011224481000000073\n" + "车牌号码: 粤B:B601Y\n"
				+ "使用性质: 家庭自用\n" + "使用年限: 2\n" + "VIN码: 763548763JHUYW8T2U9\n"
				+ "行驶证车主: 张三\n" + "发动机号: IUEJ9874H\n" + "驾驶号姓名: 张三\n"
				+ "准驾车型: 5座（含）以下小型车\n" + "驾驶证号: 987352JUYFDW\n"
				+ "行驶证年审情况: 是\n" + "驾驶证年审情况: 是\n" + "出险时间: 2013-01-23\n"
				+ "出险地点: 广东省广州市天河区\n" + "事故处理单位: 众诚保险\n" + "勘验时间: 2013-01-23\n"
				+ "勘验地点: 广州市天河区广丰4S店\n" + "勘验人: 李四" + "\n\n--- 其它事故方损失信息 ---\n"
				+ "交强险承保公司: 众诚保险\n" + "保单号: 201071224481000000679\n"
				+ "车牌号码: 粤A:BW4018\n" + "厂牌型号: 广汽丰田\n" + "使用性质: 家庭自用\n"
				+ "事故发生经过:\n"
				+ "\0\0 2013年1月22号16时45分张三开车改道未打转向灯导致正道车碰上侧门玻璃。\n"
				+ "现场查勘验描述:\n" + "\0\0案发现场查勘\n" + "事故损失及原因分析:\n"
				+ "\0\0张三驾驶操作不当造成事故，罚款2500元\n" + "查勘结论: \n" + "\0\0张三自赔。\n"
				+ "================================\n" + "查勘人签字:\n\n\n\n"
				+ "客户签字:\n\n\n\n" + "================================\n";
	*/
	
	public static String getTitle() {
		
		return title;

	}
	public static void setContent(String contents){
		content=contents;
	}
	public static void setTitle(String tiltes){
		title=tiltes;
	}

	public static String getContent() {
		
		return content;
	}

	public static String getTail(int i) {
		String tail = "第 " + i + " 联";
		return tail;

	}
	
	public static String getString(){
		String str = "";
		str = str + title + "\n";
		str = str + getContent() + "\n";
		str = str + "            "+getTail(1) + "\n\n\n\n";
		
		return str;
	}
	
	
}
