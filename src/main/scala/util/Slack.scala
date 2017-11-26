package util

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scalaz.effect.IO

object Slack {
  def output(o: String): IO[Unit] = IO {
    Future {
      println(o)
    }
  }
}
