package jp.gr.java_conf.frontier.utauplug

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class NodeSpec extends Specification {

  "test" should {
    "get" in{
      val u = new UtauPlug("test")
      val e = new UtauElement("2222")
      u.list = List(e)
      val n = Node(u, e)
      n.isEmpty must_== false
      n.get.blockName must_== "2222"

    }
    "prev prev" in {
      val u = new UtauPlug("test")
      u.prev = new UtauElement("1111")
      val e = new UtauElement("2222")
      val f = new UtauElement("3333")
      u.list = List(e,f)
      val n = Node(u, e)
      n.prev.isEmpty must_== false
      n.prev.get.blockName must_== "1111"
      n.prev.prev.isEmpty must_== true

    }
    "prev node" in {
      val u = new UtauPlug("test")
      u.prev = new UtauElement("1111")
      val e = new UtauElement("2222")
      val f = new UtauElement("3333")
      u.list = List(e,f)
      val n2 = Node(u, f)
      n2.prev.isEmpty must_== false
      n2.prev.get.blockName must_== "2222"
    }

  }

}