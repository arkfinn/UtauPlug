package jp.gr.java_conf.frontier.utauplug
import scala.util.control.Exception._
import scala.collection.mutable.HashMap

/**
 * Flagsを扱うクラス
 */
class Flags extends HashMap[String, Int] {
  def calclate(c: Flags.Calculator): Flags = {
    val f = new Flags
    for ((k, v) <- this) f += (k -> v)
    for ((k, (op, v)) <- c) {
      f += (k -> Calc.run(getOrElse(k, 0), op, v))
    }
    f
  }

  /**
   * Flags文字列に変換。この文字列をそのままUTAUのFlags指定に使える
   */
  override def toString: String = {
    val buf = new StringBuilder
    for ((k, v) <- this) buf ++= k + v.toString()
    buf.toString()
  }
}

/**
 * Flagsおよび計算ルールの作成用
 */
object Flags {
  type Calculator = HashMap[String, (Calc.Value, Int)]

  private def isVal(c: Char): Boolean = (c.isDigit || c == '-')
  private def to(v: String): Int = allCatch opt v.toInt getOrElse 0

  /**
   * 文字列からFlagsクラスを生成
   */
  def parse(str: String): Flags = {
    def getKV(s: String, f: Flags): Flags = {
      if (s.nonEmpty) {
        val (k, ss) = s.span(!isVal(_))
        val (v, sss) = ss.span(isVal(_))
        f += (k -> to(v))
        getKV(sss, f)
      }
      f
    }
    getKV(str, new Flags())
  }

  /**
   * 文字列からFlags計算mapを作成。
   * 書式は(key + 演算子(Calc,isOpで有効なもの) + 値)の羅列
   * 演算子省略時はCalc.Setとなる。
   * また演算子を一つ以上設定した際の動作は保証していない。
   */
  def calculator(str: String): Calculator = {
    def getKV(s: String, f: Calculator): Calculator = {
      if (s.nonEmpty) {
        val (k, ss) = s.span(a => !isVal(a) && !Calc.isOp(a))
        val (opc, sss) = ss.span(Calc.isOp(_))
        val op = if (0 < opc.size) Calc(opc.toString()) else Calc.Set
        val (v, ssss) = sss.span(isVal(_))
        f += (k -> (op, to(v)))
        getKV(ssss, f)
      }
      f
    }
    getKV(str, new Calculator())
  }
}
