package jp.gr.java_conf.frontier.utauplug

/**
 * 音階を扱うクラス
 * データ的にはただのIntだが、音名取得などの機能を提供する。
 */
class Note private (val num: Int) {

  /**
   * この音のオクターブ値を返す
   * @return octave value
   */
  def octave: Int = (num - 12) / 12

  /**
   * この音の音名を返す
   * @return note name
   */
  def name: String = num % 12 match {
    case 0 => "C"
    case 1 => "C#"
    case 2 => "D"
    case 3 => "D#"
    case 4 => "E"
    case 5 => "F"
    case 6 => "F#"
    case 7 => "G"
    case 8 => "G#"
    case 9 => "A"
    case 10 => "A#"
    case 11 => "B"
  }

  /**
   * 音名+オクターブの文字列を返す
   * @return name+octave
   */
  def fullName: String = name + octave

}

/**
 * Factory for [[Note]] instances.
 */
object Note {
  /**
   * 音番号からNoteを生成します。
   * 番号はUTAUでの用例に依存しますが、24=C1からの計算になります。
   */
  def apply(num: Int): Note = new Note(num)

  /**
   * 音名、オクターブからNoteを作成します。
   * 音名はA-Fおよびa-f、#（シャープ）またはb（フラット）に対応しています。
   */
  def apply(name: String, octave: Int): Note = {
    val num = name match {
      case "C" => 0
      case "C#" => 1
      case "Db" => 1
      case "D" => 2
      case "D#" => 3
      case "Eb" => 3
      case "E" => 4
      case "F" => 5
      case "F#" => 6
      case "Gb" => 6
      case "G" => 7
      case "G#" => 8
      case "Ab" => 8
      case "A" => 9
      case "A#" => 10
      case "Bb" => 10
      case "B" => 11
    }
    new Note(num + ((octave + 1) * 12))
  }

  /**
   * 音名+オクターブからNoteを作成します。
   * apply(name, octave)と同じです。
   * @param fullName name+octaveの文字列 例："C4", "Eb3", "C-2"
   */
  def apply(fullName: String): Note = {
    val m = resolveFullName(fullName)
    apply(m._1, m._2)
  }

  /**
   * 音名+オクターブの文字列を分解する
   * apply(fullName)用の関数
   * @return (note name, octave)
   */
  def resolveFullName(fullName: String): (String, Int) = {
    val r = "([ABCDEFGabcdefg][#b]?)(-?[0-9]+)".r;
    val m = r.findFirstMatchIn(fullName).head
    (m.group(1), m.group(2).toInt)
  }

}