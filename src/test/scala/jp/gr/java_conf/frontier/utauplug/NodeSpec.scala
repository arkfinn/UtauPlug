package jp.gr.java_conf.frontier.utauplug

import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class NodeSpec extends Specification {

  "test" should {
    "get" in {
      val e = new UtauElement("2222")
      val u = new UtauPlug("test", List(e))
      val n = Node(u, e)
      n.get.blockName must_== "2222"

    }
    "prev none" in {
      val e = new UtauElement("2222")
      val f = new UtauElement("3333")
      val u = new UtauPlug("test", List(e, f))
      val n = Node(u, e)
      n.prev.isEmpty must_== true
    }

    "prev prev" in {
      val e = new UtauElement("2222")
      val f = new UtauElement("3333")
      val u = new UtauPlug("test", List(e, f), prev = new UtauElement("1111"))
      val n = Node(u, e)
      n.prev.isEmpty must_== false
      n.prev.get.get.blockName must_== "1111"
      n.prev.get.prev.isEmpty must_== true
    }
    "prev node" in {
      val e = new UtauElement("2222")
      val f = new UtauElement("3333")
      val u = new UtauPlug("test", List(e, f), prev = new UtauElement("1111"))
      val n2 = Node(u, f)
      n2.prev.isEmpty must_== false
      n2.prev.get.get.blockName must_== "2222"
    }
    "next node" in {
      val e = new UtauElement("2222")
      val f = new UtauElement("3333")
      val u = new UtauPlug("test", List(e, f), next = new UtauElement("1111"))
      val n = Node(u, e)
      n.next.isEmpty must_== false
      n.next.get.get.blockName must_== "3333"
    }
    "next next" in {
      val e = new UtauElement("2222")
      val f = new UtauElement("3333")
      val u = new UtauPlug("test", List(e, f), next = new UtauElement("1111"))
      val n = Node(u, f)
      n.next.isEmpty must_== false
      n.next.get.get.blockName must_== "1111"
      n.next.get.next.isEmpty must_== true
    }
    "next none" in {
      val e = new UtauElement("2222")
      val f = new UtauElement("3333")
      val u = new UtauPlug("test", List(e, f))
      val n = Node(u, f)
      n.next.isEmpty must_== true
    }
  }

}