package cake

import util.{Active, Inactive, Slack, Switch}

import scala.io.StdIn
import scalaz.effect.IO

object CakeBot {
  def readCake(): IO[Unit] = for {
    switch <- readCakeSwitch()
    _ <- switch match {
      case Active   => Slack.output("Somebody bought cake!")
      case Inactive => IO.ioUnit
    }
  } yield ()

  private def readCakeSwitch(): IO[Switch] = IO {
    println("Enter cake switch (o for on, anything else for off")
    StdIn.readLine() match {
      case "o" => Active
      case _   => Inactive
    }
  }
}
