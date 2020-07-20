import java.util

import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import java.util.Properties
import java.util.{Collections, Properties}

import scala.collection.JavaConverters._
import org.apache.kafka.common.TopicPartition

//example
// https://github.com/knoldus/kafka-scala-producer-consumer-example/blob/master/src/main/scala/edu/knoldus/KafkaScalaConsumer.scala

object consumerKafka {


  def main(args:Array[String]): Unit = {

    val props = new Properties()

    props.put("bootstrap.servers", "kafka-1-vm:9092,kafka-1-vm:9093,kafka-1-vm:9094")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "consumer-group-1")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("auto.offset.reset", "earliest")
    props.put("session.timeout.ms", "30000")
    val topic = "mytopic4"

    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)

    consumer.subscribe(Collections.singletonList(topic))

    /**
     * Infinite loop to read from topic as soon as it gets the record
     */
    while (true) {
      /**
       * Fetch data for the topics or partitions specified using one of the subscribe/assign APIs.
       */
      val records: ConsumerRecords[String, String] = consumer.poll(100)
      println("**********RecordCOunt**********" + records.count())
      for (record <- records.asScala) {
        println(record)
      }
    }
  }
}

    /*
    def main(args:Array[String]): Unit = {
      println("**********Spark Consumer Started **********")


      val  props = new Properties()
      props.put("bootstrap.servers", "kafka-1-vm:9092,kafka-1-vm:9093,kafka-1-vm:9094")

      props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
      props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
      props.put("auto.offset.reset", "earliest")

      props.put("group.id", "GroupID")

      val consumer = new KafkaConsumer[String, String](props)

      val topics = List("mytopic2")
        consumer.subscribe(topics.asJava)

      //consumer.subscribe(util.Collections.singletonList(TOPIC))


     // println ("Partition 1" + consumer.beginningOffsets(TopicPartition("mytopic2", 0)))
      //println ("Partition 2" + consumer.beginningOffsets(TopicPartition("mytopic2", 1)))



      println ("List Topic" + consumer.listTopics())
      println("**********Consume Data **********")
      while(true){
        //val records=consumer.poll(10)
        val records: ConsumerRecords[String, String] = consumer.poll(1000)
        println("records Count ="+ records.count())
        //println("records Count ="+ records.)
        //for (record<-records.asScala)
        for (record <- records.asScala)
        {
          println("*********Topic data*********** ")
          println("Topic: " + record.topic() +
            ",Key: " + record.key() +
            ",Value: " + record.value() +
            ", Offset: " + record.offset() +
            ", Partition: " + record.partition())
        }
      }

      println("**********Spark Consumer Ended **********")
    }

    */






