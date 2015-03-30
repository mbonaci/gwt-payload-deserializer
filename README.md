## gwt-payload-deserializer

<small>
Automatically exported from code.google.com/p/gwt-payload-deserializer on 2015.03.30
Originally developed in 2010 during a performance testing gig.
</small>


Project was fast-and-furiously developed for the purpose of translating and understanding _GWT_ payload components. 
Requests were previously intercepted and the payload saved by _jMeter's Proxy server_. 
The final goal was to parameterize _GWT RPC_ calls (during performance testing of a _GWT_ application), so the results 
would be as realistic as possible.  
Testing was done using _Apache JMeter_ and _Rational Peformance Tester_.

### Setup
To set this bitch up in _Eclipse_:  
 1. create a new Java project
 2. check out the src folder
 3. add your gwt project to build path (so that your classes can be loaded)
 4. add jdk6 to build path
 5. open run configuration and add this line as the _Program argument_: 
 
 ```
 "${string_prompt}""${file_prompt}"
 ```
 
Then, when you run/debug `PayloadDeserializer` class, _Eclipse_ will prompt you with two dialogs.  
First one is for your payload and the second one is for destination file path of the output file, where the parsed payload will be stored.

If you are having problems with pasting payload into _Eclipse_ dialog, as a quick-and-dirty fix, you can temporarily hard code your payload's value in the `PayloadDeserializer Main`'s local variable (as shown in the code).

Finally, sorry for the mess in the code, I really had very limited time for load testing so I had to patch things up like this. If I have had the proper timeline I would've, as the proper solution to the problem, developed _jMeter_ plugin for gwt rpc parametrization.


I hope this helps someone...

