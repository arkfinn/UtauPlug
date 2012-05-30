package jp.gr.java_conf.frontier.utauplug

class Node private (parent: UtauPlug, element: UtauElement) {
  def isEmpty = (element == null)
  def nonEmpty = (element != null)

  def get: UtauElement = {
    if (isEmpty) throw new NoSuchElementException
    element
  }

  def prev: Node = {
    parent.list.indexOf(element) match {
      case 0 => Node(parent, parent.prev)
      case a if 0 < a => Node(parent, parent.list(a - 1))
      case _ => Node.empty(parent)
    }
  }

  def next: Node = {
    parent.list.indexOf(element) match {
      case a if a == (parent.list.size - 1) => Node(parent, parent.next)
      case a if parent.list.isDefinedAt(a) => Node(parent, parent.list(a + 1))
      case _ => Node.empty(parent)
    }
  }
}

/**
 * Nodeのファクトリ
 */
object Node {
  def apply(parent: UtauPlug, element: UtauElement): Node = {
    new Node(parent, element)
  }

  def empty(parent: UtauPlug): Node = {
    new Node(parent, null)
  }
}