package com.mikelalvarezgo.kamoot.infrastructure.sqs

import java.time.Duration

import com.typesafe.config.Config

final case class SQSConsumerConfig(
  topicArn: String,
  waitTime: Duration,
  maxBufferSize: Int,
  maxBatchSize: Int,
  minBackoff: Duration,
  maxBackoff: Duration,
  randomFactor: Double
)

object SQSConsumerConfig {
  def fromConfig(config: Config): SQSConsumerConfig =
    SQSConsumerConfig(
      config.getString("topic-arn"),
      config.getDuration("wait_time"),
      config.getInt("max_buffer_size"),
      config.getInt("max_batch_size"),
      config.getDuration("min_backoff"),
      config.getDuration("max_backoff"),
      config.getInt("random_factor")
    )
}
