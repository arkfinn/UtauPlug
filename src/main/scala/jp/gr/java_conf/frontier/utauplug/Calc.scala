package jp.gr.java_conf.frontier.utauplug

object Calc extends Enumeration {
  val Set, Add, Sub, Percent = Value
  def apply(s: String): Calc.Value = s match {
    case "+" => this.Add
    case "-" => this.Sub
    case "%" => this.Percent
    case "=" => this.Set
  }

  def run(a: Int, op: Calc.Value, b: Int): Int = op match {
    case Add => a + b
    case Sub => a - b
    case Percent => ((a * b) / 100).round
    case Set => b
  }

  def isOp(op: Char): Boolean = op match {
    case '+' | '-' | '%' | '=' => true
    case _ => false
  }
}