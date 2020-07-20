import java.util

import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import java.util.Properties
import java.util.{Collections, Properties}

import scala.collection.JavaConverters._
import org.apache.kafka.common.TopicPartition

//examplecd
// https://github.com/knoldus/kafka-scala-producer-consumer-example/blob/master/src/main/scala/edu/knoldus/KafkaScalaConsumer.scala

object consumerKafka {


  def main(args:Array[String]): Unit = {

    val props = new Properties()


    props.put("bootstrap.servers", "kafka-1-vm:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "consumer-group-1")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")

    /**
     * What to do when there is no initial offset in Kafka or if the current offset does not exist
     * any more on the server (e.g. because that data has been deleted):
     *
     * earliest: automatically reset the offset to the earliest offset
     * latest: automatically reset the offset to the latest offset
     * none: throw exception to the consumer if no previous offset is found for the consumer's group
     * anything else: throw exception to the consumer.
     */
    props.put("auto.offset.reset", "earliest")

    /**
     * The timeout used to detect worker failures. The worker sends periodic heartbeats to indicate
     * its liveness to the broker.
     */
    props.put("session.timeout.ms", "30000")

    /**
     * The topic where record should be read from.
     */
    val topic = "kafka-topic-kip"

    /**
     * A consumer is instantiated by providing the configuration.
     */
    val consumer: KafkaConsumer[Nothing, String] = new KafkaConsumer[Nothing, String](props)

    /**
     * Subscribe to the given list of topics to get dynamically assigned partitions.
     */
    consumer.subscribe(Collections.singletonList(topic))

    /**
     * Infinite loop to read from topic as soon as it gets the record
     */
    while (true) {
      /**
       * Fetch data for the topics or partitions specified using one of the subscribe/assign APIs.
       */
      val records: ConsumerRecords[Nothing, String] = consumer.poll(100)
      for (record <- records.asScala) {
        println(record)
      }
    }


  }
}
