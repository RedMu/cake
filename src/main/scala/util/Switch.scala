package util

sealed trait Switch {}
case object Active extends Switch {}
case object Inactive extends Switch {}