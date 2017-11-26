package coffee

import util.{Active, Inactive, Slack, Switch}
import scala.io.StdIn
import scalaz.effect.IO

object CoffeeBot {
  def readCoffee[A](lid: CoffeeMachineLid): IO[CoffeeMachineLid] = for {
      switch <- readCoffeeSwitch()
      newLid <- IO { lid.applySwitch(switch) }
      _ <- newLid match {
        case CoffeeLidClosed => if(newLid != lid) Slack.output("Coffee made!") else IO.ioUnit
        case CoffeeLidOpen => IO.ioUnit
      }
  } yield newLid

  private def readCoffeeSwitch(): IO[Switch] = IO {
    println("Enter coffee switch ('o'a for on, anything else for off)")
    StdIn.readLine() match {
      case "o" => Active
      case _ => Inactive
    }
  }
}
