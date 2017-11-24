import scala.annotation.tailrec
import scala.concurrent.Future
import scalaz.effect.IO
import scala.concurrent.ExecutionContext.Implicits.global

object CakeBot extends App {
  loop(false)

  @tailrec
  def loop(lidState: Boolean): IO[Boolean] = {
    val io: IO[Boolean] = for {
      switchActivated <- readSwitch()
      newLidState <- if(switchActivated) IO { !lidState } else IO { lidState }//IO { switchActivated ^ lidState }
      _ <- if(!newLidState) outputToSlack() else IO {}
    } yield newLidState
    loop(io.unsafePerformIO())
  }

  def readSwitch(): IO[Boolean] = IO { false }
  def outputToSlack(): IO[Future[Unit]] = IO {
    Future {
      println("Hello")
    }
  }
}
