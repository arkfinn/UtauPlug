UtauPlug
======================
Utauプラグインを作成する際に面倒なustファイルの読み込みや、一括処理を簡単に行うためのプラグインです。
以前は.Netにて開発をしていたプロジェクトの移植〜改善版となります。

### なぜScala？ ###

1. Mac版への展開も踏まえ、jarによるプラグイン提供を画策
2. おもしろそうだったから

使い方
----

	//ファイルパスを指定して読み込み。起動時のargs利用を想定
	val plug = UtauPlug.fromFile(filePath)
	//flatMap, mapが使えます
	val plug2 = plug.flatMap { n =>
	  //getで対象の要素、n.prev,n.nextで前後の要素が取れる
	  val elm = n.get
	  //builderを呼ぶことで要素を操作できる
	  val b = elm.bulder
	  b.intensity += 10
	  val changed_elm = b.build
	  //flatMapはUtauElementをListで返す。新規の要素を追加することもできる
	  List(elm, changed_elm, new UtauElement(Map("Intensity" -> "10", "Lyric" -> "てす")))
	}

実例は作成されたプラグインを参考にどうぞ