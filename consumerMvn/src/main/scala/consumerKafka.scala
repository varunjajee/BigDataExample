import java.util

import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties
import scala.collection.JavaConverters._

object consumerKafka {

  def main(args:Array[String]): Unit = {
    println("**********Spark Consumer Started **********")

    val TOPIC="mytopic2"
    val  props = new Properties()
    props.put("bootstrap.servers", "kafka-1-vm:9092")

    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "GroupID")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Collections.singletonList(TOPIC))

    println("**********Consume Data **********")
    while(true){
      val records=consumer.poll(100)
      for (record<-records.asScala){
        println("record :- " + record)
      }
    }

    println("**********Spark Consumer Ended **********")
  }
}





