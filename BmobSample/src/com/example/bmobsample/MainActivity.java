package com.example.bmobsample;

import java.io.File;











import com.smile.filechoose.api.ChooserType;
import com.smile.filechoose.api.ChosenFile;
import com.smile.filechoose.api.FileChooserListener;
import com.smile.filechoose.api.FileChooserManager;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Old Driver
 *
 */
public class MainActivity extends ActionBarActivity implements FileChooserListener{
	public static String APPID = "f34f9746b17a77d32d854bc248b88a96";
	private FileChooserManager fm;
	Button bth;
	ImageView iv;
	ChosenFile choosedFile;
	String url;
	TextView tv;
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//第二：默认初始化
		Bmob.initialize(this, APPID);
		initData();
		bth=(Button) findViewById(R.id.button1);
		iv=(ImageView)findViewById(R.id.imageView1);
		tv=(TextView) findViewById(R.id.textView1);
		bth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				insertDataWithOne();
				
			}
		});
	}
	
	
	/**
	 * 初始化 查询一张图片
	 */
	private void initData() {
		

			BmobQuery<GIFObject> query = new BmobQuery<GIFObject>();
			query.getObject(this, "b9e50fd654", new GetListener<GIFObject>() {
				
				@Override
				public void onSuccess(GIFObject object) {
					Log.i("life", ""+object.getFile().getFileUrl(MainActivity.this));
					String url=object.getUrl();
					Picasso.with(getApplicationContext()).load(url).into(iv);  
				}
				
				@Override
				public void onFailure(int code, String msg) {
					Log.i("wds", "onFailure = "+code+",msg = "+msg);
				}
			});
		
		
	}

	
	
	/** 插入一张图片（单个BmobFile列）
	  * 
	  * @return void
	  * @throws
	  */
	private void insertDataWithOne(){
		if(choosedFile ==null){

			pickFile();
			return;
		}
	}
	
	 public void pickFile() {
	        fm = new FileChooserManager(this);
	        fm.setFileChooserListener(this);
	        try {
	            fm.choose();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}



	@Override
	public void onError(String arg0) {
		// TODO Auto-generated method stub

		Log.d("wds", arg0);
	}



	@Override
	public void onFileChosen(ChosenFile arg0) {
		choosedFile = arg0;
		runOnUiThread(new Runnable() {
            @Override
            public void run() {


            	File img = new File(choosedFile.getFilePath());
            	uploadMovoieFile(img);
            }
        });
	}
	
	
	/** 上传指定路径下的图片
	  * @param file 
	  * @return void
	  */
	private void uploadMovoieFile(File file) {
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
		dialog.setTitle("上传中...");
		dialog.setIndeterminate(false);               
		dialog.setCancelable(true);       
		dialog.setCanceledOnTouchOutside(false);  
		dialog.show();
		final BmobFile bmobFile = new BmobFile(file);
		bmobFile.uploadblock(this, new UploadFileListener() {
			@Override
			public void onStart() {
				super.onStart();
				Log.i("bmob", "----onStart----");
			}
			
			@Override
			public void onSuccess() {
				dialog.dismiss();
				choosedFile=null;
				url = bmobFile.getUrl();
				tv.setText(url);
				Picasso.with(getApplicationContext()).load(url).into(iv);  
				insertObject(bmobFile);
				

			}

			@Override
			public void onProgress(Integer arg0) {
	
				dialog.setProgress(arg0);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
	
				dialog.dismiss();
			}
		});
		
	}
	
	

	
	/** 创建操作
	  * insertObject
	  * @return void
	  * @throws
	  */
	private void insertObject(BmobFile bf){
		GIFObject gif= new GIFObject();
		gif.setFile(bf);
		gif.setName("测试");
		gif.setUrl(bf.getUrl());
		gif.save(MainActivity.this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
		
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
		
			}
		});
	}
	
	


	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChooserType.REQUEST_PICK_FILE && resultCode == RESULT_OK) {
            if (fm == null) {
                fm = new FileChooserManager(this);
                fm.setFileChooserListener(this);
            }

            fm.submit(requestCode, data);
        }
    }
	

}
