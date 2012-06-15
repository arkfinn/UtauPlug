package jp.gr.java_conf.frontier.utauplug

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import java.io.File

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
      plug.node(0).prev.get.prev.isEmpty must_== true
    }
    "一つ目のノードからPREV取得" in {
      plug.node(0).prev.get.get.lyric must_== "な"
    }
    "一つ目のノード取得" in {
      plug.node(0).get.lyric must_== "か"
    }
    "二つ目のノードから前のノード取得" in {
      plug.node(1).prev.get.get.lyric must_== "か"
    }
    "一つ目のノードから次のノード取得" in {
      plug.node(0).next.get.get.lyric must_== "る"
    }
    "最後のノードから次のノード（NEXT）取得" in {
      plug.node(plug.list.size - 1).next.get.get.lyric must_== "な"
    }

  }
  //
  //  //mapのほうがいいかも
  //  "foreach" should {
  //    val a = "src/test/scala/jp/gr/java_conf/frontier/utauplug/UtauPlug_test.ust"
  //    val plug = UtauPlug.fromFile(a)
  //
  //    "intensity + 10" in {
  //      plug.node(2).get.intensity must_== 100
  //      for(n <-plug){
  //        n.get.intensity += 10
  //      }
  //      plug.node(2).get.intensity must_== 110
  //
  //    }
  //  }

  //execは廃止
//  "exec" should {
//    val a = "src/test/scala/jp/gr/java_conf/frontier/utauplug/UtauPlug_test.ust"
//    val plug = UtauPlug.fromFile(a)
//
//    "intensity + 10" in {
//      plug.node(2).get.intensity must_== 100
//      val plug2 = plug.exec { e =>
//        val b = e.node.get.builder
//        b.intensity += 10
//        e.add(b.build)
//        e
//      }
//      plug2.node(2).get.intensity must_== 110
//    }
//
//    "insert" in {
//      val plug2 = plug.exec { e =>
//        e.add()
//        e.add(new UtauElement(Map("Intensity" -> "10", "Lyric" -> "てす")))
//      }
//      plug2.node(0).get.intensity must_== 100
//      plug2.node(0).get.lyric must_== "か"
//      plug2.node(1).get.intensity must_== 10
//      plug2.node(1).get.lyric must_== "てす"
//      plug2.node(2).get.intensity must_== 100
//      plug2.node(2).get.lyric must_== "る"
//      plug2.node(3).get.intensity must_== 10
//      plug2.node(3).get.lyric must_== "てす"
//    }
//  }

    "flatmap" should {
    val a = "src/test/scala/jp/gr/java_conf/frontier/utauplug/UtauPlug_test.ust"
    val plug = UtauPlug.fromFile(a)

    "intensity + 10" in {
      plug.node(2).get.intensity must_== 100
      val plug2 = plug.flatMap { n =>
        val b = n.get.builder
        b.intensity += 10
        List(b.build)
      }
      plug2.node(2).get.intensity must_== 110
    }

    "insert" in {
      val plug2 = plug.flatMap { n =>
        List(n.get,new UtauElement(Map("Intensity" -> "10", "Lyric" -> "てす")))
      }
      plug2.node(0).get.intensity must_== 100
      plug2.node(0).get.lyric must_== "か"
      plug2.node(1).get.intensity must_== 10
      plug2.node(1).get.lyric must_== "てす"
      plug2.node(2).get.intensity must_== 100
      plug2.node(2).get.lyric must_== "る"
      plug2.node(3).get.intensity must_== 10
      plug2.node(3).get.lyric must_== "てす"
    }
  }
  //output
  "output" should {
    val a = "src/test/scala/jp/gr/java_conf/frontier/utauplug/UtauPlug_test.ust"
    val plug = UtauPlug.fromFile(a)
    "output" in {
      val p = "output_test.ust"
      val f = new File(p)
      if (f.exists) {
        f.delete()
      }
      plug.output(p)
      val plug2 = UtauPlug.fromFile(p)
      plug2.node(0).get.lyric must_== "か"
    }
  }

}