package com.example.bmobsample;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/** ��Ӱ
  * @ClassName: Movie
  * @Description: TODO
  * @author smile
  * @date 2014-5-22 ����8:07:00
  */
public class GIFObject extends BmobObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;//��Ӱ����
	private BmobFile file;//��Ӱ�ļ�
private  String url;



	public GIFObject(){
		
	}
	
	
	
	public GIFObject(String name,BmobFile file){
		this.name =name;
		this.file = file;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BmobFile getFile() {
		return file;
	}

	public void setFile(BmobFile file) {
		this.file = file;
	}
	
	public String getUrl() {
	return url;
}



public void setUrl(String url) {
	this.url = url;
}

	
	
	
}
