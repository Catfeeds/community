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
     * ֪ͨ����ҳģ��
     * @param title:����
     * @param text:��Ϣ����
     * @param logo:����֪ͨ��ͼƬ
     * @param url:����
     * @param isRing:�Ƿ�����
     * @param isVibrate:�Ƿ���
     * @param isClearable:�Ƿ�����
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
	 * ͸����Ϣģ��
	 * @param transmissionContent:͸�����ݣ���֧��ת���ַ�
	 * @param  transmissionType: �յ���Ϣ�Ƿ���������Ӧ�ã�1������������2���ȴ��û�����
	 * 
	 * @return TransmissionTemplate
	 * **/
	public TransmissionTemplate transmissionTemplate(String transmissionContent){
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(APPID);
		template.setAppkey(APPKEY);
		// ͸����Ϣ���ã�1Ϊǿ������Ӧ�ã��ͻ��˽��յ���Ϣ��ͻ���������Ӧ�ã�2Ϊ�ȴ�Ӧ������
		template.setTransmissionType(FORCE_PLAY);
		template.setTransmissionContent(transmissionContent);
		return template;
	}
	/*
	 * ֪ͨ��Ϣ
	 * @param title:����
	 * @param text:����
	 * @param logo:֪ͨͼ������ƣ�������׺
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
