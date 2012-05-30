package jp.gr.java_conf.frontier.utauplug
import java.io.FileInputStream
import scala.io.Source

class UtauPlug(var filepath: String) {
  var list = List[UtauElement]()
  var setting, prev, next: UtauElement = null
}

object UtauPlug {
  def fromFile(filepath: String): UtauPlug = {
    val res = new UtauPlug(filepath)
    val out = new FileInputStream(filepath)
    try {
      val output = Source.fromInputStream(out)
      var elm: UtauElement = null
      for (line <- output.getLines() if line.nonEmpty) {
        if (line.startsWith("[")) {
          elm = new UtauElement
          elm.blockName = line.slice(1, line.size - 1)
          line match {
            case "[#SETTING]" => res.setting = elm
            case "[#PREV]" => res.prev = elm
            case "[#NEXT]" => res.next = elm
            case _ => res.list = res.list ::: List(elm)
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
//        public void ElementForEach(UtauElementEventHandler callback)
//        {
//            int index = 0;
//
//            UtauElement[] list = Elements.ToArray();
//            for (int i = 0; i < list.Length; i++)
//            {
//                UtauElement now = list[i];
//                if (now.IsSelected())
//                {
//                    UtauElement prev = i == 0 ? new UtauElement("[#PREV]") : list[i - 1];
//                    UtauElement next = i == list.Length - 1 ? new UtauElement("[#NEXT]") : list[i + 1];
//                    callback(index, prev, now, next);
//                    index++;
//                }
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