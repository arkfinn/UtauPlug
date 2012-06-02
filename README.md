UtauPlug
======================
Utauプラグインを作成する際に面倒なustファイルの読み込みや、一括処理を簡単に行うためのプラグインです。
以前は.Netにて開発をしていたプロジェクトの移植～改善版となります。

### なぜScala？ ###

1. Mac版への展開も踏まえ、jarによるプラグイン提供を画策
2. おもしろそうだったから

使い方
----

	//ファイルパスを指定して読み込み。起動時のargs利用を想定
	val plug = UtauPlug.fromFile(filePath)
	//exec実行で各要素をループできる
	val plug2 = plug.exec { e =>
	  //特に修正をしない場合はadd実行でそのままの値が入る
	  e.add()
	  //e.nodeでnode取得。node.getで対象の要素、node.prev,node.nextで前後の要素が取れる
	  val elm = e.node.get
	  //builderを呼ぶことで要素を操作できる
	  val b = elm.bulder
	  b.intensity += 10
	  //変更後の要素をadd
	  e.add(b.build)
	  //新規の要素を追加することもできる
	  e.add(new UtauElement(Map("Intensity" -> "10", "Lyric" -> "てす")))
	}

そのうち書きます。
