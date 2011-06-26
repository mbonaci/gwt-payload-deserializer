package hr.ecs.gwt.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.google.gwt.user.server.rpc.RPC;


public class PayloadDeserializer {
	// Can application server (i.e. a URL listed in payload) be accessed from your location (e.g. off-site or development phase)
	public static final boolean IS_SERVER_ACCESSIBLE = true;
	private static Writer out;


	public static void main(String[] args) {
		try{
			if(args.length < 2){
				System.out.println("GWT Payload has to be sent as the first parameter and destination file path as the second!");
				return;
			}
			File file = new File(args[1]);
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

			String payload = args[0];
			// if you have problems with entering payload in eclipse params dialog (long payloads, > 16 000 elems), temporarily hard-code it here
			//			String payload = "5|0|7|http://192.168.105.139/ecs/emh/|97C4ECAB71FD06CAF1EED4DEAF352460|com.ecs.mhealth.client.service.interfaces.IUserService|getListOfUsers|java.lang.Long|I|java.lang.Long/4227064769|1|2|3|4|3|5|6|6|7|8|0|0|1|";

			EcsSerializationPolicyProvider provider = new EcsSerializationPolicyProvider(payload);

			RPC.decodeRequest(payload, provider.getClass(), provider);

			// if you want to get deserialized method params as Object[], here's how:
			//			RPCRequest rpcRequest = RPC.decodeRequest(payload, provider.getClass(), provider);
			//			Object[] params = rpcRequest.getParameters();

			out.close();

		}catch(Exception e){
			e.printStackTrace();
		}

	}


	public static void appendToFile(String str){
		try {
			out.write(str);
			System.out.print(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
