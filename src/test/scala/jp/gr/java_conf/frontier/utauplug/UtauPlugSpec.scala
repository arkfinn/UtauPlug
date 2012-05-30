package jp.gr.java_conf.frontier.utauplug

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class UtauPlagSpec extends Specification {

  "fromFile" should {
    "ˆê‚Â–Ú‚Ì—v‘f‚ªæ“¾‚Å‚«‚Ä‚¢‚é‚±‚Æ" in {
      val a = "src/test/scala/jp/gr/java_conf/frontier/utauplug/UtauPlug_test.ust"
      val plug = UtauPlug.fromFile(a)
      plug.list.size must_== 22
      plug.list(0).lyric must_== "‚©"
      plug.list(0).blockName must_== "#0004"
    }
    "—v‘f‚ª‡”Ô‚Éæ“¾‚Å‚«‚Ä‚¢‚é‚±‚Æ" in {
      val a = "src/test/scala/jp/gr/java_conf/frontier/utauplug/UtauPlug_test.ust"
      val plug = UtauPlug.fromFile(a)
      plug.list(0).lyric must_== "‚©"
      plug.list(1).lyric must_== "‚é"
      plug.list(2).lyric must_== "‚¢"
      plug.list(3).lyric must_== "R"

    }

  }

  //output
}