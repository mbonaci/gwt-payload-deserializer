# Description of Google Web Toolkit payload components #

## Payload Example ##
```
5|								{GWT SERIALIZATION_STREAM_VERSION}
0|								{flags}
7|								{number of string table elements - starts directly bellow this line}

1	http://192.168.105.139/ecs/emh/|			{path - this is the first string table element}
2	97C4ECAB71FD06CAF1EED4DEAF352460|			{whitelist hash*}
3	com.ecs.emh.client.service.interfaces.IUserService|	{service interface FQN}
4	getListOfPatients|					{method name}
5	java.lang.Long|						{Long}
6	I|							{int***}
7	java.lang.Long/4227064769|				{type serialization signature**}

1|2|3|4|		{service url endpoint, hash, interface and method definition - positions in str table}
3|			{number of method parameters}
5|			{first method parameter type - position in str table} - Long
6|			{second method parameter type - position in str table} - int
6|			{third method parameter type - position in str table} - int

7|			{first param's type serialization signature - position in str table}
2|			{first param's value}
0|			 + {first param's value} - Long params are represented by two payload elements (sum of)
0|			{second param's value}
1|			{third param's value}


whitelist hash *:			[The hash is used to load the whitelist of objects that can be deserialized.
					url/hash.gwt.rpc is a viewable url, 
					that may provide some useful insight about the datatypes used by the GWT application.]
										
type serialization signature **:	[The signature ensures that both the client and server are working on the same instance of the class. 
					Similar to serialVersionUID, but generated using type members (crc).
					If the signature sent by the client does not match the server’s signature for the class, 
					the server will throw an IncompatibleRemoteServiceException]

Type designation ***:
I - int
Z - boolean
J - long
D - double
F - float
B - byte
S - short
C - char
```