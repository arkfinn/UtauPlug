package jp.gr.java_conf.frontier.utauplug

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class FlagsSpec extends Specification {

  "parse" should {
    "parse g-20Y0y5" in {
      Flags.parse("g-20Y0y5") must haveTheSameElementsAs(Map("g" -> -20, "Y" -> 0, "y" -> 5))
    }
    "parse Test-20Y0Bre5" in {
      Flags.parse("Test-20Y0Bre5") must haveTheSameElementsAs(Map("Test" -> -20, "Y" -> 0, "Bre" -> 5))
    }
  }

  "calculator" should {
    "parse g-20Y0y%5" in {
      Flags.calculator("g-20Y0y%5") must haveTheSameElementsAs(
        Map("g" -> (Calc.Sub, 20),
          "Y" -> (Calc.Set, 0),
          "y" -> (Calc.Percent, 5)))
    }
  }

  "marge" should {
    "Flagsとcalculatorを混ぜた新しいリストを返す" in {
      val f = Flags.parse("g30Y50y80")
      val c = Flags.calculator("g-20Y0y%25")
      f.calclate(c) must haveTheSameElementsAs(Map("g" -> 10, "Y" -> 0, "y" -> 20))
    }
  }

  "toString" should {
    "test1" in {
      val f = new Flags()
      f += ("g" -> -20)
      f += ("Y" -> 0)
      f += ("y" -> 5)
      Flags.parse(f.toString()) must haveTheSameElementsAs(Flags.parse("g-20Y0y5"))
    }
    "test2" in {
      val f = new Flags()
      f += ("Test" -> -20)
      f += ("Y" -> 0)
      f += ("Bre" -> 5)
      Flags.parse(f.toString()) must haveTheSameElementsAs(Flags.parse("Test-20Y0Bre5"))
    }
  }

}