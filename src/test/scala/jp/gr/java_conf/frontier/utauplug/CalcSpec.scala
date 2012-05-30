package jp.gr.java_conf.frontier.utauplug

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class CalcSpec extends Specification {

  "apply" should {
    "+ is Add" in { Calc("+") must_== Calc.Add }
    "- is Sub" in { Calc("-") must_== Calc.Sub }
    "% is Percent" in { Calc("%") must_== Calc.Percent }
    "= is Set" in { Calc("=") must_== Calc.Set }
  }

  //implitÇÃèoî‘Ç©
  //1.calc(Calculation.Add, 2)Ç∆Ç©Ç…ÇµÇΩÇ¢
  "do" should{
    "1+2" in{Calc.run(1, Calc.Add, 2) must_==3}
    "5-3" in{Calc.run(5, Calc.Sub, 3) must_==2}
    "set 5" in{Calc.run(1, Calc.Set, 5) must_==5}
    "200%20=40" in{Calc.run(200, Calc.Percent, 20) must_==40}
  }

  //do
}