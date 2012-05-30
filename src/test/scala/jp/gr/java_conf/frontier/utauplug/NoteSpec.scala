package jp.gr.java_conf.frontier.utauplug

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class NoteSpec extends Specification {

	"The 'octave' function" should {
		"num 12 is 0" in {
			Note(12).octave must_== 0
		}
		"num 38 is 2" in {
			Note(38).octave must_== 2
		}
	}

	"The 'name' function" should {
		"num 24 is C" in {
			Note(24).name must be matching "C"
		}
		"num 65 is F" in {
			Note(65).name must be matching "F"
		}
	}

	"The 'fullName' function" should {
		"num 24 is C1" in {
			Note(24).fullName must be matching "C1"
		}
		"num 65 is F4" in {
			Note(65).fullName must be matching "F4"
		}
	}

	//object Note

	"ólÅXÇ»ê∂ê¨ï˚ñ@" should{
		"by num" in {
			Note(24).num must_==24
		}
		"by name octave" in {
			Note("C", 1).num must_==24
		}
		"by fullname" in {
			Note("C1").num must_==24
		}
		"by fullname" in {
			Note("Db1").num must_==25
		}
	}

	"resolveFullName" should{
		"C4 is C, 4" in {
			val m = Note.resolveFullName("C4")
			m._1 must be matching "C"
			m._2 must_== 4
		}
		"D#4 is D#, 4" in {
			val m = Note.resolveFullName("D#4")
			m._1 must be matching "D#"
			m._2 must_== 4
		}
		"D#-2 is D#, -2" in {
			val m = Note.resolveFullName("D#-2")
			m._1 must be matching "D#"
			m._2 must_== -2
		}
		"Eb4 is D#, 4" in {
			val m = Note.resolveFullName("Eb4")
			m._1 must be matching "Eb"
			m._2 must_== 4
		}

	}

}