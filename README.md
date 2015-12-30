# messageboard
An example of messageboard application by Scala and AngularJS

Scala ActorSystem bind itself to port 8080 which can be run any computer. It is better that the firewal turns off the incoming and outgoing tcp traffics except the one of webserver. The webserver usually is Apache or nginx and proxy the http post to the host where Scala ActorSystem runs. The web page containing AngularJS is placed in the directory of webserver by design. 

<em>To install,</em>
cd baseDirectory of the project
sbt clean package

<em>To run in linux, stay in baseDirectory</em>
nohup java -jar target/scala-2.11/hello-assembly-0.1.0.jar &

The file nohup.out will have the log of running program. It is possible to install the compiled binary as service. Then the init.d/*.sh has to be prepared. Some other SBT plugin maybe helpful for such intention.
