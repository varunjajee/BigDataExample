import java.util.Properties

import org.apache.kafka.clients.producer._
import org.apache.kafka.clients.producer.KafkaProducer
object producerKafka {

  def main(args:Array[String]) {
    println("**********Spark Producer Started **********")


    val props = new Properties()
    props.put("bootstrap.servers", "kafka-1-vm:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    val TOPIC = "mytopic4"
    println("TOPIC :- " + TOPIC)

    for (j <- 0 to 100) {
    for (i <- ((j*5)+ 1) to ((j+1)* 5)) {
      val record = new ProducerRecord(TOPIC, "key", s"TestHello $i")
      producer.send(record)
    }
    Thread.sleep(50000)
  }

    val record1 = new ProducerRecord(TOPIC, "key", s"hello ")
    producer.send(record1)


    val record = new ProducerRecord(TOPIC, "key", "the end " + new java.util.Date)
    producer.send(record)

    producer.close()

    println("**********Spark Producer Ended **********")
  }
}

/*
object sparkProducer extends App {

  import java.util.Properties

  import org.apache.kafka.clients.producer._

  println("Spark Producer")
  println("fullname :-" + sparkProducer.getClass.getName())
  val  props = new Properties()
  props.put("bootstrap.servers", "kafka-1-vm:9092")

  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val TOPIC="mytopic2"
  println("TOPIC :- " + TOPIC)
  for(i <- 1 to 50){
    val record = new ProducerRecord(TOPIC, "key", s"hello $i")
    producer.send(record)
  }

  val record = new ProducerRecord(TOPIC, "key", "the end "+new java.util.Date)
  producer.send(record)

  producer.close()
}
*/

