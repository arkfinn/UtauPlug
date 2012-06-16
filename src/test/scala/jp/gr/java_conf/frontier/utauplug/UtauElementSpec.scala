package jp.gr.java_conf.frontier.utauplug

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import scala.io.Source
import scala.collection.mutable.StringBuilder

@RunWith(classOf[JUnitRunner])
class UtauElementSpec extends Specification {

  "attr" should {
    "set attr Volume" in {
      val elm = new UtauElement.Builder()
      elm.attr("Volume", "100")
      elm.build.attr("Volume") must be matching "100"
    }
    "set and update attr Volume" in {
      val elm = new UtauElement.Builder()
      elm.attr("Volume", "100")
      elm.attr("Volume", "200")
      elm.build.attr("Volume") must be matching "200"
    }
    "get null attr" in {
      val elm = new UtauElement.Builder()
      elm.build.attr("Volume") must be matching ""
    }
  }

  "attr accessor" should {
    val elm = new UtauElement.Builder()
    "lyric" in {
      elm.lyric = "う"
      elm.build.lyric must be matching "う"
    }

    "Note" in {
      elm.note = Note(30)
      elm.build.note.num must_== 30
    }
    "Note from num" in {
      elm.noteNum = 24
      elm.build.note.num must_== 24
    }
    "NoteNum" in {
      elm.noteNum = 24
      elm.build.noteNum must_== 24
    }
    "NoteNum error" in {
      elm.attr("NoteNum", "abc")
      elm.build.noteNum must_== 0
    }
    "Length" in {
      elm.length = 100
      elm.build.length must_== 100
    }
    "Moduration" in {
      elm.moduration = 50
      elm.build.moduration must_== 50
    }
    "Moduration max 200" in {
      elm.moduration = 500
      elm.build.moduration must_== 200
    }
    "Moduration min -200" in {
      elm.moduration = -300
      elm.build.moduration must_== -200
    }
    "Intensity" in {
      elm.intensity = 100
      elm.build.intensity must_== 100
    }
    "Intensity max 200" in {
      elm.intensity = 500
      elm.build.intensity must_== 200
    }
    "Intensity min 0" in {
      elm.intensity = -200
      elm.build.intensity must_== 0
    }

    //Flags
    //PitchList
  }

  "clear" should {
    "all attr clear" in {
      val elm = new UtauElement.Builder()
      elm.attr("Volume", "100")
      elm.attr("Moduration", "100")
      elm.clear
      elm.build.attr("Volume") must be matching ""
      elm.build.attr("Moduration") must be matching ""
    }
    "Volume attr clear" in {
      val elm = new UtauElement.Builder()
      elm.attr("Volume", "100")
      elm.attr("Moduration", "100")
      elm.clear("Volume")
      elm.build.attr("Volume") must be matching ""
      elm.build.attr("Moduration") must be matching "100"
    }
  }

  "isRest" should {
    val elm = new UtauElement.Builder()
    "rest note" in {
      elm.lyric = "R"
      elm.build.isRest must beTrue
    }
    "normal note" in {
      elm.lyric = "あ"
      elm.build.isRest must beFalse
    }
  }

  "isSelected" should {
    "prev" in {
      val elm = new UtauElement.Builder("#PREV")
      elm.build.isSelected must beFalse
    }
    "next" in {
      val elm = new UtauElement.Builder("#NEXT")
      elm.build.isSelected must beFalse
    }
    "note" in {
      val elm = new UtauElement.Builder("#0004")
      elm.build.isSelected must beTrue
    }
  }

  "output" should {
    "test1" in {
      val elm = new UtauElement.Builder("#0004")
      elm.length = 240
      elm.lyric = "か"
      elm.noteNum = 62
      elm.intensity = 100
      elm.moduration = 0
      elm.attr("PBS", "-45")
      elm.attr("PBW", "89")
      val sb = new StringBuilder
      elm.build.output(sb)

      val in = getClass().getResourceAsStream("UtauElement_OutputTest.ust")

      try {
        val sample = Source.fromInputStream(in, "SJIS").getLines.toList
        sb.toString.split(elm.build.nl).toList must haveTheSameElementsAs(sample)
      } finally {
        in.close
      }
    }
  }

  "builder" should {
    "get builder" in {
      val a = new UtauElement
      a.intensity must_== 100
      val b = a.builder
      b.intensity +=  10
      val c = b.build
      c.intensity must_== 110
      val d = c.builder
      d.intensity += 10
      val e = d.build
      e.intensity must_== 120

    }
  }
}