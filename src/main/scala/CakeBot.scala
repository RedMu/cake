import scala.annotation.tailrec
import scala.concurrent.Future
import scalaz.effect.IO
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn

object CakeBot extends App {
  loop(LidClosed())

  @tailrec
  def loop(lidState: LidState): IO[Boolean] = {
    val io: IO[LidState] = for {
      switchValue <- readSwitch()
      newLidState <- IO { lidState.applySwitch(switchValue) }
      _ <- newLidState match {
        case LidClosed() => if(newLidState != lidState) outputToSlack() else IO.ioUnit
        case LidOpen() => IO.ioUnit
      }
    } yield newLidState
    loop(io.unsafePerformIO())
  }

  def readSwitch(): IO[SwitchState] = IO {
    StdIn.readLine() match {
      case "o" => SwitchOn()
      case _ => SwitchOff()
    }
  }
  def outputToSlack(): IO[Future[Unit]] = IO {
    Future {
      println("Coffee made")
    }
  }

  sealed trait LidState {
    def identity(): LidState
    def negate(): LidState
    def applySwitch(switch: SwitchState): LidState = {
      switch match {
        case SwitchOn() => negate()
        case SwitchOff() => identity()
      }
    }
  }
  case class LidOpen() extends LidState {
    override def identity(): LidState = LidOpen()
    override def negate(): LidState = LidClosed()
  }
  case class LidClosed() extends LidState {
    override def identity(): LidState = LidOpen()
    override def negate(): LidState = LidOpen()
  }

  sealed trait SwitchState {}
  case class SwitchOn() extends SwitchState {}
  case class SwitchOff() extends SwitchState {}
}