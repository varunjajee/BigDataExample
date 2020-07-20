import java.util.Properties

import org.apache.kafka.clients.producer._
import org.apache.kafka.clients.producer.KafkaProducer

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


object producerKafka {

  def main(args:Array[String]) {
    println("**********Spark Producer Started **********")

    val props = new Properties()

    props.put("bootstrap.servers", "kafka-1-vm:9092")
    props.put("client.id", "ScalaProducerExample")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    /**
     * @define acks
     * The number of acknowledgments the producer requires the leader to have received before
     * considering a request complete. This controls the durability of records that are sent.
     *
     * acks=0 : If set to zero then the producer will not wait for any acknowledgment
     *
     * acks=1 : This will mean the leader will write the record to its local log but will respond
     * without awaiting full acknowledgement from all followers.
     *
     * acks all: This means the leader will wait for the full set of in-sync replicas to acknowledge
     * the record.
     */
    props.put("acks", "all")

    /**
     * retries
     * Setting a value greater than zero will cause the client to resend any record whose send
     * fails with a potentially transient error.
     */
    props.put("retries", "0")

    /**
     * batch.size
     * The producer will attempt to batch records together into fewer requests whenever multiple
     * records are being sent to the same partition.
     */
    props.put("batch.size", "16384")

    /**
     * linger.ms
     * The producer groups together any records that arrive in between request transmissions into a
     * single batched request.
     */
    props.put("linger.ms", "1")

    /**
     * buffer.memory
     * The total bytes of memory the producer can use to buffer records waiting to be sent tpo the
     * server.
     */
    props.put("buffer.memory", "33554432")

    /**
     * A producer is instantiated by providing the configuration.
     */
    val producer: KafkaProducer[Nothing, String] = new KafkaProducer[Nothing, String](props)

    /**
     * The topic where record should be sent to.
     */
    val topic = "kafka-topic-kip"

    println(s"Sending Records in Kafka Topic [$topic]")

    for (i <- 1 to 50) {
      /**
       * Creates a record to be sent to a specified topic and partition
       */
      val record: ProducerRecord[Nothing, String] = new ProducerRecord(topic, i.toString)
      println(s"$record")
      producer.send(record)
    }

    /**
     * Close this producer. This method blocks until all previously sent requests complete.
     */
    producer.close()

    println("**********Spark Producer Ended **********")
  }
}

