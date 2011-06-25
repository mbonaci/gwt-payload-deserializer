package hr.ecs.gwt.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

public class EcsSerializationPolicyProvider implements SerializationPolicyProvider {

	private String payload;
	
	
	public EcsSerializationPolicyProvider(String payload) {
		this.payload = payload;
	}


	@Override
	public SerializationPolicy getSerializationPolicy(String moduleBaseURL, String serializationPolicyStrongName) {
		if(payload.length() == 0){
			System.out.println("GWT Payload has to be sent as the first parameter!");
			return null;
		}
		try {
			RemoteServiceServlet rss = new RemoteServiceServlet();
			
			// first get the serialization whitelist from the server
			String[] elems = payload.split("\\|");
			String uri = elems[3].concat(elems[4]).concat(".gwt.rpc");
			HttpClient httpClient = new DefaultHttpClient();
			
			HttpUriRequest req = new HttpGet(uri);
			HttpResponse resp;
	
			resp = httpClient.execute(req);
			
			InputStream input = resp.getEntity().getContent();
			
			List<ClassNotFoundException> classNotFoundExceptions = new ArrayList<ClassNotFoundException>();
			
			SerializationPolicy whitelist = SerializationPolicyLoader.loadFromStream(input, classNotFoundExceptions);
		  
			return whitelist;
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
