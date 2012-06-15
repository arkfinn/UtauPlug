package jp.gr.java_conf.frontier.utauplug

class Node private (parent: UtauPlug, element: UtauElement) {

  def get: UtauElement = {
    element
  }

  def prev: Option[Node] = {
    parent.list.indexOf(element) match {
      case 0 => if (parent.prev == null) None else Some(Node(parent, parent.prev))
      case a if 0 < a => Some(Node(parent, parent.list(a - 1)))
      case _ => None
    }
  }

  def next: Option[Node] = {
    parent.list.indexOf(element) match {
      case a if a == (parent.list.size - 1) => if (parent.next == null) None else Some(Node(parent, parent.next))
      case a if parent.list.isDefinedAt(a) => Some(Node(parent, parent.list(a + 1)))
      case _ => None
    }
  }
}

/**
 * Nodeのファクトリ
 */
object Node {
  def apply(parent: UtauPlug, i: Int): Node = {
    new Node(parent, parent.list(i))
  }
  def apply(parent: UtauPlug, element: UtauElement): Node = {
    new Node(parent, element)
  }
}