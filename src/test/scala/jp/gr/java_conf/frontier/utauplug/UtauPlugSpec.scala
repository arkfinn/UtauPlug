package jp.gr.java_conf.frontier.utauplug

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class UtauPlugSpec extends Specification {

  "fromFile" should {
    val a = "src/test/scala/jp/gr/java_conf/frontier/utauplug/UtauPlug_test.ust"
    val plug = UtauPlug.fromFile(a)

    "一つ目の要素が取得できていること" in {
      plug.list.size must_== 22
      plug.list(0).lyric must_== "か"
      plug.list(0).blockName must_== "#0004"
    }
    "要素が順番に取得できていること" in {
      plug.list(0).lyric must_== "か"
      plug.list(1).lyric must_== "る"
      plug.list(2).lyric must_== "い"
      plug.list(3).lyric must_== "R"

    }

  }

  "node" should {
    val a = "src/test/scala/jp/gr/java_conf/frontier/utauplug/UtauPlug_test.ust"
    val plug = UtauPlug.fromFile(a)
    "prevの一つ前を取得しようとしたとき" in {
      plug.node(0).prev.prev.isEmpty must_== true
    }
    "一つ目のノードからPREV取得" in {
      plug.node(0).prev.get.lyric must_== "な"
    }
    "一つ目のノード取得" in {
      plug.node(0).get.lyric must_== "か"
    }
    "二つ目のノードから前のノード取得" in {
      plug.node(1).prev.get.lyric must_== "か"
    }
    "一つ目のノードから次のノード取得" in {
      plug.node(0).next.get.lyric must_== "る"
    }
    "最後のノードから次のノード（NEXT）取得" in {
      plug.node(plug.list.size-1).next.get.lyric must_== "な"
    }

  }

  //output
}