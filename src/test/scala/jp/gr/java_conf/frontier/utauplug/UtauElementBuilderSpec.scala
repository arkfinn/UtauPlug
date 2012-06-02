package jp.gr.java_conf.frontier.utauplug

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import scala.io.Source
import scala.collection.mutable.StringBuilder

@RunWith(classOf[JUnitRunner])
class UtauElementBuilderSpec extends Specification {

  "attr" should {
    "set attr Volume" in {
      val elm = new UtauElementBuilder()
      elm.attr("Volume", "100")
      elm.attr("Volume") must be matching "100"
    }
    "set and update attr Volume" in {
      val elm = new UtauElementBuilder()
      elm.attr("Volume", "100")
      elm.attr("Volume", "200")
      elm.attr("Volume") must be matching "200"
    }
    "get null attr" in {
      val elm = new UtauElementBuilder()
      elm.attr("Volume") must be matching ""
    }
  }

  "attr accessor" should {
    val elm = new UtauElementBuilder()
    "lyric" in {
      elm.lyric = "‚¤"
      elm.lyric must be matching "‚¤"
    }

    "Note" in {
      elm.note = Note(30)
      elm.note.num must_== 30
    }
    "Note from num" in {
      elm.noteNum = 24
      elm.note.num must_== 24
    }
    "NoteNum" in {
      elm.noteNum = 24
      elm.noteNum must_== 24
    }
    "NoteNum error" in {
      elm.attr("NoteNum", "abc")
      elm.noteNum must_== 0
    }
    "Length" in {
      elm.length = 100
      elm.length must_== 100
    }
    "Moduration" in {
      elm.moduration = 50
      elm.moduration must_== 50
    }
    "Moduration max 200" in {
      elm.moduration = 500
      elm.moduration must_== 200
    }
    "Moduration min -200" in {
      elm.moduration = -300
      elm.moduration must_== -200
    }
    "Intensity" in {
      elm.intensity = 100
      elm.intensity must_== 100
    }
    "Intensity max 200" in {
      elm.intensity = 500
      elm.intensity must_== 200
    }
    "Intensity min 0" in {
      elm.intensity = -200
      elm.intensity must_== 0
    }

    //Flags
    //PitchList
  }

  "clear" should {
    "all attr clear" in {
      val elm = new UtauElementBuilder()
      elm.attr("Volume", "100")
      elm.attr("Moduration", "100")
      elm.clear
      elm.attr("Volume") must be matching ""
      elm.attr("Moduration") must be matching ""
    }
    "Volume attr clear" in {
      val elm = new UtauElementBuilder()
      elm.attr("Volume", "100")
      elm.attr("Moduration", "100")
      elm.clear("Volume")
      elm.attr("Volume") must be matching ""
      elm.attr("Moduration") must be matching "100"
    }
  }

  "isRest" should {
    val elm = new UtauElementBuilder()
    "rest note" in {
      elm.lyric = "R"
      elm.isRest must beTrue
    }
    "normal note" in {
      elm.lyric = "‚ "
      elm.isRest must beFalse
    }
  }

  "isSelected" should {
    "prev" in {
      val elm = new UtauElementBuilder("#PREV")
      elm.isSelected must beFalse
    }
    "next" in {
      val elm = new UtauElementBuilder("#NEXT")
      elm.isSelected must beFalse
    }
    "note" in {
      val elm = new UtauElementBuilder("#0004")
      elm.isSelected must beTrue
    }
  }



}