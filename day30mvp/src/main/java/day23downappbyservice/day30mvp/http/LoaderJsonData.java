package day23downappbyservice.day30mvp.http;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class LoaderJsonData {

	public interface GetJsonData{
		void getJsonData(String jsonData);
	}

	private GetJsonData getData;
	public void getGift(String url, GetJsonData getData){
		this.getData = getData;
		new AsyncTaskJson().execute(url);
	}

	public void getGift(String url,List<String> list ,GetJsonData getData){
		this.getData = getData;
		new LoadJsonByPost(list).execute(url);
	}
	class LoadJsonByPost extends AsyncTask<String,Void,String>{
		List<String> urlParams;

		public LoadJsonByPost(List<String> urlParams) {
			this.urlParams = urlParams;
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				HttpURLConnection conn  = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				//通过输出流把把请求传给服务器
				OutputStream outputStream = conn.getOutputStream();
				for(String str:urlParams){
					outputStream.write(str.getBytes());
				}
				outputStream.flush();
				outputStream.close();
				InputStream inputStream = conn.getInputStream();
				int len = 0;
				byte [] bytes = new byte[1024];
				StringBuilder sb = new StringBuilder();
				while((len=inputStream.read(bytes))!=-1){
					sb.append(new String(bytes,0,len));
				}
				inputStream.close();
				return sb.toString();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			if(s!=null&&getData!=null){
				getData.getJsonData(s);
			}
		}
	}
	/**
	 * 这是默认使用get请求来加载网络数据
	 */
	class AsyncTaskJson extends AsyncTask<String, Void, String>{

		protected String doInBackground(String... params) {
			StringBuilder sb = new StringBuilder();
			URL url;
			try {
				url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//				try(
//						InputStream inputStream = conn.getInputStream();
//						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//						){
//					StringBuilder sb = new StringBuilder();
//					char[] c = new char[1024];
//					int length = 0;
//					while((length=reader.read(c))!=-1){
//						String str = new String(c,0,length);
//						sb.append(str);
//					}	
//				}
				try(
						InputStream inputStream = conn.getInputStream();
						){
					int len = 0;
					byte[] byt = new byte[1024];
					while((len=inputStream.read(byt))!=-1){
						String str = new String(byt,0,len);
						sb.append(str);
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(getData!=null){
			getData.getJsonData(result);
		}
	}
}
}

