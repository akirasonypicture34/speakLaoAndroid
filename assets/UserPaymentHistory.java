package com.infl.grocesry11;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.infl.grocesry11.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserPaymentHistory extends BaseActivity implements OnClickListener {
	
	ListView listUserPaymentHistory;
	MyAdapter myAdapter;
	RelativeLayout feedback,suggestion,favorite,setting;
	
	//Button btnBack;
	
	String userId = "";
	
	DisplayImageOptions options;
	
	ArrayList<String> listName = new ArrayList<String>();
	ArrayList<String> listDateTime = new ArrayList<String>();
	ArrayList<String> listAmount = new ArrayList<String>();
    ArrayList<String> listTransactionId = new ArrayList<String>();
    ArrayList<String> listImage = new ArrayList<String>();
	
	ProgressDialog dialog;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.userpaymenthistory);
	    context = this;
	    overridePendingTransition(0, 0);
	    
	    SharedPreferences sharedPreferences = getSharedPreferences("Account_type",MODE_PRIVATE);
	    userId = sharedPreferences.getString("user_id", "");
	    
	    imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		//imageLoader.getInstance().init(config);
		
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_launcher).cacheOnDisc()
				.imageScaleType(ImageScaleType.EXACTLY).build();
		
	    listUserPaymentHistory = (ListView)findViewById(R.id.list_paymentHistory);
	    
	   /* btnBack = (Button)findViewById(R.id.btn_back);
	    btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});*/
	    feedback = (RelativeLayout)findViewById(R.id.button4);
		suggestion = (RelativeLayout)findViewById(R.id.button5);
		favorite = (RelativeLayout)findViewById(R.id.button6);
		setting = (RelativeLayout)findViewById(R.id.button7);
		
		feedback.setOnClickListener(this);
		suggestion.setOnClickListener(this);
		favorite.setOnClickListener(this);
		setting.setOnClickListener(this);
	    
	    if (isNetworkAvailable()) {
    	   	dialog = ProgressDialog.show(context, "Processing", "Please wait...");
        
    	   	UserPaymentHistoryTask task = new UserPaymentHistoryTask();
    	   	task.execute(new String[] {userId});
       	} 
       	else {
       		Toast.makeText(context, "Please check your internet connection",
       				Toast.LENGTH_SHORT).show();
       	}
	    
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
	}
	
//**************************************************************************************************************************	
	
	/* Network Availability Method */
	 public boolean isNetworkAvailable() {
		  try {
		   ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		   NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		 
		   if (networkInfo != null && networkInfo.isConnected()) {
		    return true;
		   }
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return false;
		 } //end of Network Availability Method
	 
//**************************************************************************************************************************	 
	 
	 /* UserPaymentHistoryTask */
	 private class UserPaymentHistoryTask extends AsyncTask<String, Void, String> {
		  protected void onPreExecute() {
			  super.onPreExecute();
		  }
		 
		  protected String doInBackground(String... urls) {
		 
		   String response="";
		   
		   try {
		 
		    JsonCall obj = new JsonCall();
		 
		    response = obj.get_userPaymentHistory(urls[0],getString(R.string.userpaymenthistory_url));
		             
		    Log.e("User Payment History", "" + response);
		    
		   } catch (Exception e) {
		 
		    e.printStackTrace();
		   }
		 
		   return response;
		  }
		 
		  protected void onPostExecute(String result) {
		   JSONObject jObject;
		   try {
			   		jObject = new JSONObject(result);
		    
			   		JSONObject cr = jObject.getJSONObject("CommandResult");
			   		String status = cr.getString("success");
			   		String resp_str = cr.getString("response_string");
		    
		    
		 
		     
			   		Log.e("Status",""+status);
			   		if ((status.equalsIgnoreCase("1"))) {
			   				//Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_SHORT).show();
			   			JSONArray jsonary = cr.getJSONArray("result");
			   			
			   			for (int i = 0; i < jsonary.length(); i++) {
			   				
			   				JSONObject jobj = jsonary.getJSONObject(i);
			   				
				   			listImage.add(jobj.getString("image"));	
				   			listName.add(jobj.getString("name"));	
				   			listAmount.add(jobj.getString("amount"));
				   			listDateTime.add(jobj.getString("paymentDate"));
				   			listTransactionId.add(jobj.getString("transationId"));
			   			
			   		}
		    
		    }
		    
		    else {
		    	
		     Toast.makeText(getBaseContext(), resp_str,
		       Toast.LENGTH_SHORT).show();
		    
		    }
			   		
			myAdapter = new MyAdapter(getBaseContext(), listImage, listName, listAmount, listDateTime, listTransactionId);
			listUserPaymentHistory.setAdapter(myAdapter);
		   
			if (dialog != null)
		     dialog.cancel();
		 
		   } catch (Exception e) {
		    e.printStackTrace();
		    if (dialog != null)
		     dialog.cancel();
		   }
		  }
		 }
//**********************************************************************************************************************
	 /* My Adapter */
	 public  class MyAdapter extends BaseAdapter {
			
			private Context mContext;
		    LayoutInflater mInflater;
		    
		    ArrayList<String> listName = new ArrayList<String>();
			ArrayList<String> listDateTime = new ArrayList<String>();
			ArrayList<String> listAmount = new ArrayList<String>();
		    ArrayList<String> listTransactionId = new ArrayList<String>();
		    ArrayList<String> listImage = new ArrayList<String>();
		
		   public MyAdapter(Context context, ArrayList<String> listImage, ArrayList<String> listName, 
				   ArrayList<String> listAmount, ArrayList<String> listDateTime, ArrayList<String> listTransactionId) {
		     
			   mContext = context;
		       mInflater = LayoutInflater.from(mContext);
		       
		       this.listImage = listImage;
		       this.listName = listName;
		       this.listAmount = listAmount;
		       this.listDateTime = listDateTime;
		       this.listTransactionId = listTransactionId;
		    
		   }
		   
		   class ViewHolder {
		    		
		    		public TextView txtNameHolder, txtAmountHolder, txtDateTimeHolder, txtTransactionIdHolder;
		    		public ImageView imgUserHolder;
		       }
		   
		   public int getCount() {
		          return listName.size();
		      }

		   public Object getItem(int position) {
		          return null;
		      }

		   public long getItemId(int position) {
		          return 0;
		      }

		      // create a new ImageView for each item referenced by the Adapter
		   public View getView(int position, View convertView, ViewGroup parent) {
			   if(convertView == null) {
				   convertView = mInflater.inflate(R.layout.userpaymenthistory_row, null);
		           ViewHolder holder = new ViewHolder();
		     
		           holder.imgUserHolder = (ImageView) convertView.findViewById(R.id.imgUser);
		           holder.txtNameHolder = (TextView) convertView.findViewById(R.id.txt_namevalue);
		           holder.txtAmountHolder = (TextView) convertView.findViewById(R.id.txt_amountvalue);
		           holder.txtDateTimeHolder = (TextView) convertView.findViewById(R.id.txt_datetimevalue);
		           holder.txtTransactionIdHolder = (TextView) convertView.findViewById(R.id.txt_transactionIdvalue);
		          
		           convertView.setTag(holder);
			   	}
		       
		       final ViewHolder holder = (ViewHolder) convertView.getTag();
		       
		       Log.e("image", "$$"+listImage.get(position));
		    
		       imageLoader.displayImage("http://services.appliconsultants.com/Services/groceryApp/images/"+listImage.get(position), holder.imgUserHolder, options);
		      // imageLoader.displayImage(""+b, holder.imgRestaurant, options);
		       holder.txtNameHolder.setText(listName.get(position));
		       holder.txtAmountHolder.setText(listAmount.get(position));
		       holder.txtDateTimeHolder.setText(listDateTime.get(position));
		       holder.txtTransactionIdHolder.setText(listTransactionId.get(position));
		       /*int r = (int) Math.round(listDistance.get(position)*100);
		       double f = r / 100.0;
		       holder.txtDistanceValue.setText(""+f+"  Km");*/
		       
		       return convertView;
		      
		   }
		   
	 }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.button4:
			Intent i = new Intent(getBaseContext(), HomePage.class);
   			i.putExtra("check", "0");
		    startActivity(i);
		break;
		case R.id.button5:
			Intent i1 = new Intent(getBaseContext(), AlternateApple.class);
   			//i.putExtra("check", "0");
		    startActivity(i1);
			break;
		case R.id.button6:
			startActivity(new Intent(this, FavoriteList.class));
			break;
		case R.id.button7:
			Intent i4 = new Intent(context, Setting.class);
			startActivity(i4);
			break;
		
		}
		
	}

}
