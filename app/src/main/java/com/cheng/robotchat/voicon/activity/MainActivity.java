package com.cheng.robotchat.voicon.activity;//package com.example.chientruong.voicon.activity;
//
//import android.app.Activity;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.example.chientruong.voicon.R;
//import OnReturnObject;
//import Links;
//import Message;
//import ApiService;
//import GetJSONObject;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.DateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//
//public class MainActivity extends Activity implements OnReturnObject{
//
//	// LogCat tag
//	private static final String TAG = MainActivity.class.getSimpleName();
//
//	private ImageView imgSend;
//	private EditText inputMsg;
//	// Chat messages list adapter
//	private MessagesListAdapter adapter;
//	private List<Message> listMessages;
//	private ListView listViewMessages;
//	private String currentDateTimeString;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		imgSend = (ImageView) findViewById(R.id.activity_imgSend);
//		inputMsg = (EditText) findViewById(R.id.inputMsg);
//		listViewMessages = (ListView) findViewById(R.id.list_view_messages);
//
//		listMessages = new ArrayList<Message>();
////		adapter = new MessagesListAdapter(this, listMessages,this);
////		listViewMessages.setAdapter(adapter);
//		imgSend.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				currentDateTimeString = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())+"<br>";
//				String params = inputMsg.getText().toString().trim().replace(" ","%20");
//				if(!params.equals("") || params== null){
//					listMessages.add(new Message("You",getTime(),inputMsg.getText().toString().trim(),true));
//					adapter.notifyDataSetChanged();
//					new GetJSONObject(getApplicationContext(),
//							"http://vnbot.azurewebsites.net/api/bot?data="+params,"", ApiService.ApiResquestType.TYPE_GET,MainActivity.this).execute();
//
//					inputMsg.setText("");
//				}else {
//					Toast.makeText(MainActivity.this, "Vui long nhap vao o chat", Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
//
//	}
//
//    public String getTime(){
//        Calendar c = Calendar.getInstance();
//
//        int minutes = c.get(Calendar.MINUTE);
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//		if(minutes <10 || hour < 10){
//			return  "<h6>0"+ hour+":0"+minutes+"</h6>";
//		}
//        return  "<h6>"+ hour+":"+minutes+"</h6>";
//    }
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//	}
//
//	public void playBeep() {
//
//		try {
//			Uri notification = RingtoneManager
//					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
//					notification);
//			r.play();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	@Override
//	public void onReturnObjectFinish(JSONObject object) {
//		if(object==null){
//			Toast.makeText(MainActivity.this, "Khong tim thay ket qua", Toast.LENGTH_SHORT).show();
//		}
//		try {
//			String value = object.getString("Value");
//			String isDownload = object.getString("IsDownload");
//			String type = object.getString("Type");
//			ArrayList<Links> link = new ArrayList<>();
//
//			if(!object.getString("Links").equals("null")){
//				JSONArray jArray = object.getJSONArray("Links");
//				for(int i = 0; i < jArray.length(); i++) {
//					link.add(new Links(
//							jArray.getJSONObject(i).getString("FileName"),
//							jArray.getJSONObject(i).getString("Title"),
//							jArray.getJSONObject(i).getString("Url"),
//							jArray.getJSONObject(i).getBoolean("IsEnable"),
//							jArray.getJSONObject(i).getBoolean("isPlay"))
//					);
//				}
//			}
//			listMessages.add(new Message("Voi Con",getTime(),value,isDownload,type,link,false));
//			adapter.notifyDataSetChanged();
//			playBeep();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//}
