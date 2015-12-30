package com.dongjindu.messageboard

import spray.json._;

case class Message(email: String, subject: String, body: String)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val messageEntity = jsonFormat3(Message)
}

import spray.json._;
import MyJsonProtocol._;
import akka.actor.Actor;
import akka.actor.ActorSystem;
import spray.routing.SimpleRoutingApp;
import spray.routing.Directives.getFromFile;
import spray.routing.HttpService;
import akka.util._;
import java.util.concurrent.TimeUnit;
import spray.httpx.SprayJsonSupport._;
import com.typesafe.config._;

object MessageBoard extends App with SimpleRoutingApp {
  val config : com.typesafe.config.Config = ConfigFactory.parseString("""
     akka.http {
         server {
             bind-timeout = 100 s
         }
     }
     spray.can {
         server {
             bind-timeout = 100 s
         }
     }
    """)
    implicit val system = ActorSystem("mysystem", config)
  startServer(interface = "0.0.0.0", port = 8080) {
    path("") {
      get {
        //        complete {
        //          spray.routing.Directives.getFromFile("")
        //        }
        getFromResource("index.html")
      } ~ post {
        entity(as[Message]) { message =>
          complete { "Completed in root. " + message.email + ":" + message.body }
        }
      }
    } ~ pathPrefix("javascript") {
      get {
        getFromResourceDirectory("javascript")
      }
    } ~
      path("postMessage") {
        get {
          complete {
            "show postMessage path in get."
          }
        } ~ post {
          entity(as[Message]) {
            message =>
              import javax.mail._
              import javax.mail.internet._
              val properties = System.getProperties
              properties.put("mail.smtp.host", "mail.dongjindu.com")
              val session = javax.mail.Session.getDefaultInstance(properties)
              val mailmessage = new MimeMessage(session)
              mailmessage.setFrom(new InternetAddress(message.email))
              mailmessage.setRecipients(javax.mail.Message.RecipientType.TO, "dj@dongjindu.com")
              mailmessage.setSubject(message.subject)
              mailmessage.setText(message.body)
              try {
                Transport.send(mailmessage)
                complete("Message sent successfully.")
                //          complete{"message ->" + message.email + ":" + message.body}
              } catch {
                case e: Exception =>
                  import java.io._
                  val stringwriter = new StringWriter
                  e.printStackTrace(new PrintWriter(stringwriter))
                  complete("Something wrong in calling javax.mail to send message." + stringwriter.toString)
              }
          }
        }
      }
  }
}
