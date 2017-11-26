import cake.CakeBot
import coffee.{CoffeeBot, CoffeeLidClosed, CoffeeMachineLid}

import scalaz.effect.IO

object KitchenBot extends App {
  loop(CoffeeLidClosed).unsafePerformIO();

  def loop[A](c: CoffeeMachineLid): IO[A] = for {
    coffee <- CoffeeBot.readCoffee(c)
    _ <- CakeBot.readCake()
    _ <- IO { Thread.sleep(50) }
    a <- loop[A](coffee)
  } yield a
}