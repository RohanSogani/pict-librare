package com.android.akash.newsearch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookFetchr {

		byte[] getURLBytes(String urlspec)throws IOException{
				
				URL url = new URL(urlspec);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				
				try {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					InputStream in = conn.getInputStream();
					
					if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
						return null;
					}
					
					int bytesRead = 0  ; 
					byte[] buffer = new byte[1024];
					while((bytesRead = in.read(buffer))>0){
						out.write(buffer,0,bytesRead);
						
					}
					out.close();
					return out.toByteArray();
					
				}finally{
					conn.disconnect();
				}
		}
		
		public String getURL(String urlspec) throws IOException{
			return  new  String(getURLBytes(urlspec));
			
		}
}
