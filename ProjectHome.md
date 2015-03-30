Project was fast-and-furiously developed for the purpose of translating and understanding _GWT_ payload components. Requests were previously intercepted and the payload saved by _jMeter's Proxy server_, all for the purpose of parameterizing _GWT RPC_ calls, so that performance testing of a _GWT_ application with _Apache JMeter_ would be as realistic as possible.<br><br>
To setup project in <i>Eclipse</i>:<br>
<ol><li>create new Java project<br>
</li><li>check out the src folder<br>
</li><li>add your gwt project to build path (so that your classes can be loaded)<br>
</li><li>add jdk6 to build path<br>
</li><li>open run configuration and add this line to <i>Program arguments</i> window: <br><br>"${string_prompt}""${file_prompt}"<br><br>
Then, when you run/debug <code>PayloadDeserializer</code> class, <i>Eclipse</i> will prompt you with two dialogs. First one is for your payload and the second one is for destination file path of the output file, where the parsed payload will be stored.<br>
If you are going to have problems with pasting payload into <i>Eclipse</i> dialog, as a quick fix, to avoid entering payload in a dialog, you can temporarily hard code your payload's value in the <code>PayloadDeserializer Main</code>'s local variable (as shown in the code) :(<br><br>
Finally, sorry for the mess in the code, I really had very limited time for load testing, otherwise I would've, as the proper solution to the problem, developed <i>jMeter</i> plugin for gwt rpc parametrization.<br><br>
Anyway, I hope this'll help someone...