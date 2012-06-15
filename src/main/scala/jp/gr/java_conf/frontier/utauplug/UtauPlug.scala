package jp.gr.java_conf.frontier.utauplug
import java.io.FileInputStream
import scala.io.Source
import scala.collection.mutable.MutableList
import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec
import java.io.FileWriter
import java.io.File
import java.io.OutputStreamWriter
import java.io.FileOutputStream

class UtauPlug(
  val filepath: String,
  val list: List[UtauElement],
  val prev: UtauElement = null,
  val next: UtauElement = null,
  val setting: UtauElement = null) {

  def node(i: Int) = Node(this, list(i))

  //  def foreach[U](f: Node => U): Unit = for (a <- list) f(Node(this, a))

  def ｍap(f: Node => UtauElement): UtauPlug = {
    new UtauPlug(filepath, list.map(a => f(Node(this, a))), prev, next, setting)
  }

  def flatMap(f: Node => List[UtauElement]): UtauPlug = {
    new UtauPlug(filepath, list.flatMap(a => f(Node(this, a))), prev, next, setting)
  }

  def output(filepath: String) = {
    val s = new StringBuilder
    for (a <- list) a.output(s)
    val filewriter = new OutputStreamWriter (new FileOutputStream(filepath), "SJIS");
    try {
      filewriter.write(s.toString());
    } finally {
      filewriter.close();
    }
  }
}

object UtauPlug {
  def fromFile(filepath: String): UtauPlug = {
    val out = new FileInputStream(filepath)
    val list = ListBuffer.empty[UtauElement.Builder]
    var setting, prev, next: UtauElement.Builder = null
    try {
      var elm: UtauElement.Builder = null
      for (line <- Source.fromInputStream(out, "SJIS").getLines() if line.nonEmpty) {
        if (line.startsWith("[")) {
          elm = new UtauElement.Builder(line.slice(1, line.size - 1))
          elm.blockName match {
            case "#SETTING" => setting = elm
            case "#PREV" => prev = elm
            case "#NEXT" => next = elm
            case _ => list += elm
          }
        } else {
          if (line.contains("=")) {
            val a = line.split('=')
            elm.attr(a(0), a(1))
          }
        }
      }
    } finally {
      out.close
    }
    new UtauPlug(
      filepath,
      list.map(_.build).toList,
      if (prev != null) prev.build else null,
      if (next != null) next.build else null,
      if (setting != null) setting.build else null)
  }

}