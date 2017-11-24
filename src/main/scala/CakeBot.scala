import scala.concurrent.Future
import scalaz.effect.IO
import scala.io.StdIn
import scala.concurrent.ExecutionContext.Implicits.global
object CakeBot extends App {
  loop(LidClosed).unsafePerformIO()

  def loop[A](lidState: LidState): IO[A] = for {
      switchValue <- readSwitch()
      newLidState <- IO { lidState.applySwitch(switchValue) }
      _ <- newLidState match {
        case LidClosed => if(newLidState != lidState) outputToSlack() else IO.ioUnit
        case LidOpen => IO.ioUnit
      }
      a <- loop[A](newLidState)
    } yield a

  def readSwitch(): IO[SwitchState] = IO {
    StdIn.readLine() match {
      case "o" => SwitchOn
      case _ => SwitchOff
    }
  }
  def outputToSlack(): IO[Unit] = IO { //[Future[Unit]] = IO {
    Future {
      println("Coffee made")
    }
  }

  sealed trait LidState {
    def identity(): LidState
    def negate(): LidState
    def applySwitch(switch: SwitchState): LidState = {
      switch match {
        case SwitchOn => negate()
        case SwitchOff => identity()
      }
    }
  }
  case object LidOpen extends LidState {
    override def identity(): LidState = LidOpen
    override def negate(): LidState = LidClosed
  }
  case object LidClosed extends LidState {
    override def identity(): LidState = LidClosed
    override def negate(): LidState = LidOpen
  }

  sealed trait SwitchState {}
  case object SwitchOn extends SwitchState {}
  case object SwitchOff extends SwitchState {}
}