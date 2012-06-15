package jp.gr.java_conf.frontier.utauplug
import scala.util.control.Exception._
import scala.collection.mutable.{ Map => MutableMap }

/**
 * ustの音符要素一つ分を表す。[#SETTING]については別扱いにしたい
 * attrMapを直指定することも可能だが、
 * 大文字小文字など誤植があると面倒なため、対応しているものはbuilderのアクセッサで作成する方が良い
 */
class UtauElement(
  val blockName: String = "#INSERT",
  val attrMap: Map[String, String] = Map.empty[String, String]) {

  def this(attr: Map[String, String]) = this("#INSERT", attr)
  /**
   * 属性の値を返す。存在しない場合は""を返す。
   * アクセッサが用意されている場合はそちらを使った方が良い
   */
  def attr(name: String): String = {
    if (attrMap.keySet.contains(name)) attrMap(name) else ""
  }
  //
  //  /**
  //   * 属性の値を設定する。設定後は自身を返す。
  //   * アクセッサが用意されている場合はそちらを使った方が良い
  //   * @return this
  //   */
  //  def attr(name: String, value: String) {
  //    attrMap += name -> value
  //  }
  //
  //  /** 全要素を削除 */
  //  def clear() { attrMap.keySet.foreach(clear(_)) }
  //
  //  /**
  //   * 特定の要素のみ削除
  //   * 削除結果を明示しないとUTAU側で消えないため、""をセットしている
  //   */
  //  def clear(name: String) { attr(name, "") }

  /** @return 休符扱いならtrue */
  def isRest: Boolean = lyric == "R"

  /** @return 現在選択中扱いの要素ならtrue */
  def isSelected: Boolean = blockName match {
    case "#PREV" | "#NEXT" => false
    case _ => true
  }

  def nl: String = "\n"

  //output
  def output(sb: StringBuilder) {
    sb.append("[" + blockName + "]" + nl)
    attrMap.foreach { case (k, v) => sb.append(k + "=" + v + nl) }
  }

  //attr accessor

  def lyric: String = attr("Lyric")
  //  def lyric_=(value: String) { attr("Lyric", value) }

  def noteNum: Int = allCatch opt attr("NoteNum").toInt getOrElse 0
  //  def noteNum_=(value: Int) { attr("NoteNum", value.toString) }

  /** NoteNumから作成したNoteクラスを返す */
  def note: Note = Note(noteNum)
  /** NoteのnumからNoteNumを設定 */
  //  def note_=(value: Note) { noteNum = value.num }

  def length: Int = allCatch opt attr("Length").toInt getOrElse 0
  //  def length_=(value: Int) { attr("Length", value.toString()) }

  /** @return Intensityの値、未設定の場合は100を返す*/
  def intensity: Int = allCatch opt attr("Intensity").toInt getOrElse 100
  /** Intensityの値をセット、0～200の範囲に丸められる*/
  //  def intensity_=(value: Int) { attr("Intensity", value.max(0).min(200).toString()) }

  /** @return modurationの値、未設定の場合は0を返す*/
  def moduration: Int = allCatch opt attr("Moduration").toInt getOrElse 0
  /** modurationの値をセット、-200～200の範囲に丸められる*/
  //  def moduration_=(value: Int) { attr("Moduration", value.max(-200).min(200).toString()) }

  def builder: UtauElement.Builder = new UtauElement.Builder(this)
}

object UtauElement {
  class Builder(
    val blockName: String = "#INSERT",
    attrMap: MutableMap[String, String] = MutableMap.empty[String, String]) {

    def this(attrMap: MutableMap[String, String]) = this("#INSERT", attrMap)
    def this(e: UtauElement) = this(e.blockName, MutableMap.empty ++ e.attrMap)
    /**
     * 属性の値を返す。存在しない場合は""を返す。
     * アクセッサが用意されている場合はそちらを使った方が良い
     */
    def attr(name: String): String = {
      if (attrMap.keySet.contains(name)) attrMap(name) else ""
    }

    /**
     * 属性の値を設定する。設定後は自身を返す。
     * アクセッサが用意されている場合はそちらを使った方が良い
     * @return this
     */
    def attr(name: String, value: String) {
      attrMap += name -> value
    }

    /** 全要素を削除 */
    def clear() { attrMap.keySet.foreach(clear(_)) }

    /**
     * 特定の要素のみ削除
     * 削除結果を明示しないとUTAU側で消えないため、""をセットしている
     */
    def clear(name: String) { attr(name, "") }
    /** @return 休符扱いならtrue */
    def isRest: Boolean = lyric == "R"

    /** @return 現在選択中扱いの要素ならtrue */
    def isSelected: Boolean = blockName match {
      case "#PREV" | "#NEXT" => false
      case _ => true
    }

    //  def nl: String = "\n"
    //  //output
    //  def output(sb: StringBuilder) {
    //    sb.append(blockName + nl)
    //    attrMap.foreach { case (k, v) => sb.append(k + "=" + v + nl) }
    //  }

    //attr accessor

    def lyric: String = attr("Lyric")
    def lyric_=(value: String) { attr("Lyric", value) }

    def noteNum: Int = allCatch opt attr("NoteNum").toInt getOrElse 0
    def noteNum_=(value: Int) { attr("NoteNum", value.toString) }

    /** NoteNumから作成したNoteクラスを返す */
    def note: Note = Note(noteNum)
    /** NoteのnumからNoteNumを設定 */
    def note_=(value: Note) { noteNum = value.num }

    def length: Int = allCatch opt attr("Length").toInt getOrElse 0
    def length_=(value: Int) { attr("Length", value.toString()) }

    /** @return Intensityの値、未設定の場合は100を返す*/
    def intensity: Int = allCatch opt attr("Intensity").toInt getOrElse 100
    /** Intensityの値をセット、0～200の範囲に丸められる*/
    def intensity_=(value: Int) { attr("Intensity", value.max(0).min(200).toString()) }

    /** @return modurationの値、未設定の場合は0を返す*/
    def moduration: Int = allCatch opt attr("Moduration").toInt getOrElse 0
    /** modurationの値をセット、-200～200の範囲に丸められる*/
    def moduration_=(value: Int) { attr("Moduration", value.max(-200).min(200).toString()) }

    def build: UtauElement = {
      new UtauElement(blockName, attrMap.toMap[String, String])
    }
  }
}