package org.ouobpo.ouvroir.scala

import org.specs._

object HelloSpec extends Specification {
  "Hello" should {
    "say hello" in {
      new Hello().hello must be matching "hello."
    }
  }
}