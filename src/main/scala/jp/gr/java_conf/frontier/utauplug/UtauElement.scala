package jp.gr.java_conf.frontier.utauplug
import scala.util.control.Exception._
import scala.collection.mutable.{ Map => MutableMap }

protected trait UtauElementAttr {
  def attr(name: String): String
  def lyric: String = attr("Lyric")
  def noteNum: Int = allCatch opt attr("NoteNum").toInt getOrElse 0

  /** @return 休符扱いならtrue */
  def isRest: Boolean = lyric == "R"

  /** NoteNumから作成したNoteクラスを返す */
  def note: Note = Note(noteNum)

  /** 音の長さ*/
  def length: Int = allCatch opt attr("Length").toInt getOrElse 0

  /** @return Intensityの値、未設定の場合は100を返す*/
  def intensity: Int = allCatch opt attr("Intensity").toInt getOrElse 100

  /** @return modurationの値、未設定の場合は0を返す*/
  def moduration: Int = allCatch opt attr("Moduration").toInt getOrElse 0

  def flags: Flags = Flags.parse(attr("Flags"))

  /** 先行発声 今のところデフォルト0が帰るので、原音値かどうかを知るにはcontainsの有無で判定ください */
  def preUtterance: Int = allCatch opt attr("PreUtterance").toInt getOrElse 0

  /** オーバーラップ 今のところデフォルト0が帰るので、原音値かどうかを知るにはcontainsの有無で判定ください */
  def overlap: Int = allCatch opt attr("VoiceOverlap").toInt getOrElse 0

  /** STP */
  def startPoint: Int = allCatch opt attr("StartPoint").toInt getOrElse 0

  /** テンポ デフォルト0が返りますので、未設定は0で判断できます　その場合は#SETTINGの値を参照 */
  def tempo: Double = allCatch opt attr("Tempo").toDouble getOrElse 0

  /** 子音速度 */
  def velocity: Int = allCatch opt attr("Velocity").toInt getOrElse 100

  /*以下は既知の値だが未設定
   * ヘルパークラスにすると思う
   * flags Nフラグ（値無しフラグ）に対応していない。複数文字のフラグは廃止して1文字ずつ分割すべきかも
   *
   * Bre Bフラグとして登録される
   * No Formant Filter Nフラグとして登録される
   * StartPoint STP
   *
   * PBW ピッチベンド長さ(たぶん、一個前の要素からの相対距離)
   * PBS 開始位置（たぶん、一個目の要素の位置）
   * PBY ,区切り少数第一位　たぶん最初と最後を0としている
   * PBM　ピッチ曲線の形
   * VBR ビブラート　長さ,周期,深さ,入,出,位相,高さ,不明
   * Envelope　エンベロ
   */
}

/**
 * ustの音符要素一つ分を表す。[#SETTING]については別扱いにしたい
 * attrMapを直指定することも可能だが、
 * 大文字小文字など誤植があると面倒なため、対応しているものはbuilderのアクセッサで作成する方が良い
 */
class UtauElement(
  val blockName: String = "#INSERT",
  val attrMap: Map[String, String] = Map.empty[String, String]) extends UtauElementAttr {

  def this(attr: Map[String, String]) = this("#INSERT", attr)
  /**
   * 属性の値を返す。存在しない場合は""を返す。
   * アクセッサが用意されている場合はそちらを使った方が良い
   */
  def attr(name: String): String = if (contains(name)) attrMap(name) else ""
  def contains(name: String): Boolean = attrMap.keySet.contains(name)

  /** @return 現在選択中扱いの要素ならtrue */
  def isSelected: Boolean = blockName match {
    case "#SETTING" | "#PREV" | "#NEXT" => false
    case _ => true
  }

  def nl: String = "\n"

  //output
  def output(sb: StringBuilder) {
    sb.append("[" + blockName + "]" + nl)
    attrMap.foreach { case (k, v) => sb.append(k + "=" + v + nl) }
  }

  def builder: UtauElement.Builder = new UtauElement.Builder(this)

}

object UtauElement {

  lazy val delete: UtauElement = new UtauElement(blockName = "#DELETE")

  class Builder(
    val blockName: String = "#INSERT",
    attrMap: MutableMap[String, String] = MutableMap.empty[String, String]) extends UtauElementAttr {

    def this(attrMap: MutableMap[String, String]) = this("#INSERT", attrMap)
    def this(e: UtauElement) = this(e.blockName, MutableMap.empty ++ e.attrMap)
    /**
     * 属性の値を返す。存在しない場合は""を返す。
     * アクセッサが用意されている場合はそちらを使った方が良い
     */
    def attr(name: String): String = if (contains(name)) attrMap(name) else ""
    def contains(name: String): Boolean = attrMap.keySet.contains(name)

    /**
     * 属性の値を設定する。。
     * アクセッサが用意されている場合はそちらを使った方が良い
     * @return this
     */
    def attr(name: String, value: String) { attrMap += name -> value }

    /**
     * a=bの文字列からattrを設定し、設定名を返す
     * attr(attrFromString("aaa=bbb"))のように使える。
     * =が無い場合は中身が空の項目となる
     */
    def attrFromString(str: String): String = {
      if (str.contains("=")) {
        val a = str.split('=')
        if (1 < a.size) attr(a(0), a(1)) else attr(a(0), "")
        a(0)
      } else {
        attr(str, "")
        str
      }
    }

    /** 全要素を削除 */
    def clear() { attrMap.keySet.foreach(clear(_)) }

    /**
     * 特定の要素のみ削除
     * 削除結果を明示しないとUTAU側で消えないため、""をセットしている
     */
    def clear(name: String) { attr(name, "") }

    def build: UtauElement = new UtauElement(blockName, attrMap.toMap[String, String])

    //attr setter

    override def lyric: String = super.lyric
    def lyric_=(value: String) { attr("Lyric", value) }

    override def noteNum: Int = super.noteNum
    def noteNum_=(value: Int) { attr("NoteNum", value.toString) }

    override def note: Note = super.note
    /** NoteのnumからNoteNumを設定 */
    def note_=(value: Note) { noteNum = value.num }

    override def length: Int = super.length
    def length_=(value: Int) { attr("Length", value.toString()) }

    override def intensity: Int = super.intensity
    /** Intensityの値をセット、0～200の範囲に丸められる*/
    def intensity_=(value: Int) { attr("Intensity", value.max(0).min(200).toString()) }

    override def moduration: Int = super.moduration
    /** modurationの値をセット、-200～200の範囲に丸められる*/
    def moduration_=(value: Int) { attr("Moduration", value.max(-200).min(200).toString()) }

    override def flags = super.flags
    def flags_=(f: Flags) { attr("Flags", f.toString()) }

    override def preUtterance: Int = super.preUtterance
    /** 先行発声を設定。原音値に戻す場合はclearを指定ください 原音の範囲チェックはしません*/
    def preUtterance_=(v: Int) { attr("PreUtterance", v.toString()) }

    override def overlap: Int = super.overlap
    /** オーバーラップを設定。原音値に戻す場合はclearを指定ください 原音の範囲チェックはしません*/
    def overlap_=(v: Int) { attr("VoiceOverlap", v.toString()) }

    override def startPoint: Int = super.startPoint
    /** SPTを設定。原音の範囲チェックはしません*/
    def startPoint_=(v: Int) { attr("StartPoint", v.toString()) }

    override def tempo: Double = super.tempo
    /** テンポを指定。小数点第二位までだが制御はまだない*/
    def tempo_=(v: Int) { attr("Tempo", v.max(10).min(512).toString()) }

    override def velocity: Int = super.velocity
    def velocity(v: Int) { attr("Velocity", v.max(0).min(200).toString()) }

  }
}