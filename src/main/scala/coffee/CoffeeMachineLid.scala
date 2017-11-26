package coffee

import util.{Active, Switch, Inactive}

sealed trait CoffeeMachineLid {
  def identity(): CoffeeMachineLid = this
  def negate(): CoffeeMachineLid
  def applySwitch(switch: Switch): CoffeeMachineLid = {
    switch match {
      case Active   => negate()
      case Inactive => identity()
    }
  }
}
case object CoffeeLidOpen extends CoffeeMachineLid {
  override def negate(): CoffeeMachineLid = CoffeeLidClosed
}
case object CoffeeLidClosed extends CoffeeMachineLid {
  override def negate(): CoffeeMachineLid = CoffeeLidOpen
}
