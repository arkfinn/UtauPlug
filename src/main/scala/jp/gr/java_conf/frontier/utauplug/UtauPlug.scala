package jp.gr.java_conf.frontier.utauplug
import java.io.FileInputStream
import scala.io.Source
import scala.collection.mutable.MutableList
import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec
import java.io.FileWriter
import java.io.File

class UtauPlug(var filepath: String) {
  var list = List[UtauElement]()
  var setting, prev, next: UtauElement = null
  def node(i: Int) = Node(this, list(i))

  //  def foreach[U](f: Node => U): Unit = for (a <- list) f(Node(this, a))

  class ExecElement(val node: Node) {
    var list = new ListBuffer[UtauElement]
    def add(e: UtauElement) { list += e }
    def add() { add(node.get) }
  }

  def exec(f: ExecElement => Unit): UtauPlug = {
    val res = new ListBuffer[UtauElement]
    for (a <- list) {
      val e = new ExecElement(Node(this, a))
      f(e)
      for (b <- e.list) res += b
    }
    val u = new UtauPlug(filepath)
    u.list = res.toList
    u.setting = setting
    u.prev = prev
    u.next = next
    u
  }

  def output(filepath: String) = {
    val s = new StringBuilder
    for (a <- list) a.output(s)
    val filewriter = new FileWriter(new File(filepath));
    try {
      filewriter.write(s.toString());
    } finally {
      filewriter.close();
    }
  }

}

object UtauPlug {
  def fromFile(filepath: String): UtauPlug = {

    val out = new FileInputStream(filepath)
    val list = ListBuffer.empty[UtauElementBuilder]
    var setting, prev, next: UtauElementBuilder = null
    try {
      var elm: UtauElementBuilder = null
      for (line <- Source.fromInputStream(out).getLines() if line.nonEmpty) {
        if (line.startsWith("[")) {
          elm = new UtauElementBuilder(line.slice(1, line.size - 1))
          elm.blockName match {
            case "#SETTING" => setting = elm
            case "#PREV" => prev = elm
            case "#NEXT" => next = elm
            case _ => list += elm
          }
        } else {
          if (line.contains("=")) {
            val a = line.split('=')
            elm.attr(a(0), a(1))
          }
        }
      }
    } finally {
      out.close
    }
    val res = new UtauPlug(filepath)
    if (setting != null) res.setting = setting.build
    if (prev != null) res.prev = prev.build
    if (next != null) res.next = next.build
    res.list = list.map(_.build).toList
    res
  }

}

//namespace UtauPluginSet
//{
//
//    public class UtauData
//    {
//        static public void setup(string filepath)
//        {
//            instance = new UtauData(filepath);
//        }
//
//        static private UtauData instance;
//
//        static public UtauData getInstance()
//        {
//            if (instance == null)
//            {
//                throw new ApplicationException("ïKÇ∏setupÇçsÇ¡Çƒâ∫Ç≥Ç¢");
//            }
//            return instance;
//        }
//
//        private string filepath;
//
//        public UtauElement SettingElement;
//        private List<UtauElement> mElements;
//
//        protected List<UtauElement> Elements
//        {
//            get
//            {
//                if (mElements == null)
//                {
//                    mElements = new List<UtauElement>();
//                }
//                return mElements;
//            }
//        }
//
//
//        public void output()
//        {
//            StringBuilder sb = new StringBuilder();
//            foreach (UtauElement v in Elements)
//            {
//                v.output(sb);
//            }
//
//            using (StreamWriter writer = new StreamWriter(filepath, false, Encoding.GetEncoding("Shift_JIS")))
//            {
//                writer.Write(sb.ToString());
//            }
//        }
//

//        public void InsertBefore(UtauElement elm, UtauElement insert_elm)
//        {
//            insert_elm.BlockName = "[#INSERT]";
//            int index = Elements.IndexOf(elm);
//            Elements.Insert(index, insert_elm);
//        }
//
//        public UtauElement GetNewElement()
//        {
//            return new UtauElement();
//        }
//
//        static private List<Note> noteList = null;
//        static public IList<Note> GetNoteList()
//        {
//            if (noteList == null)
//            {
//                noteList = new List<Note>();
//                for (int i = 107; 24 <=i ; i--)
//                {
//                    noteList.Add(new Note(i));
//                }
//            }
//            return noteList.AsReadOnly();
//        }
//
//    }
//
//    public delegate void UtauElementEventHandler(int key, UtauElement prev, UtauElement now, UtauElement next);
//
//
//}