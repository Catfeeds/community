package com.tongwii.util;

import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class PushTemplate {
	private static String APPID = "JmL38ikDvT6BxVij26iff2";
    private static String APPKEY = "5ZtUygJRzc6Ssg2jt3dvE7";
	private static Integer WAIT_PLAY = 2;
	private static Integer FORCE_PLAY = 1;
    /*
     * 通知打开网页模板
     * @param title:主题
     * @param text:消息内容
     * @param logo:推送通知的图片
     * @param url:链接
     * @param isRing:是否响铃
     * @param isVibrate:是否震动
     * @param isClearable:是否可清除
     * 
     * @return LinkTemplate
     * */
	public LinkTemplate linkTemplate(String title, String text, String logo, String url, boolean isRing,
			boolean isVibrate, boolean isClearable){
		int noitfyStyle = 1;
		LinkTemplate template = new LinkTemplate();
		template.setAppId(APPID);
		template.setAppkey(APPKEY);
		template.setTitle(title);
		template.setText(text);
		template.setLogo(logo);
		template.setIsRing(isRing);
		template.setIsClearable(isClearable);
		template.setIsVibrate(isVibrate);
		if(url == null){
		template.setUrl("http://www.baidu.com");}
		else{
			template.setUrl(url);
		}
		
		return template;
		
	}
	
	/*
	 * 透传消息模板
	 * @param transmissionContent:透传内容，不支持转义字符
	 * @param  transmissionType: 收到消息是否立即启动应用，1：立即启动；2：等待用户启动
	 * 
	 * @return TransmissionTemplate
	 * **/
	public TransmissionTemplate transmissionTemplate(String transmissionContent){
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(APPID);
		template.setAppkey(APPKEY);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(FORCE_PLAY);
		template.setTransmissionContent(transmissionContent);
		return template;
	}
	/*
	 * 通知消息
	 * @param title:主题
	 * @param text:内容
	 * @param logo:通知图标的名称，包含后缀
	 * return NotificationTemplate
	 * */
	public NotificationTemplate notificationTemplate(String title, String text, String logo){
		NotificationTemplate template = new NotificationTemplate();
		
		template.setAppId(APPID);
		template.setAppkey(APPKEY);
		Style0 style = new Style0();
		style.setTitle(title);
		style.setText(text);
		style.setLogo(logo);
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		
		return template;
	}
}
