package fs2.io.fx

import customjavafx.scene.control.{BeadRoadResult, BigEyeRoadLabel, CockroachRoadLabel, SmallRoadLabel}
import customjavafx.scene.layout._
import fs2.io.fx.syntax._
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.input.{KeyCode, KeyEvent}
import scalafxml.core.macros.sfxml

@sfxml(additionalControls = List("customjavafx.scene.layout", "customjavafx.scene.control"))
class DisplayHandler(
  val handBetMin: Label,
  val handBetMax: Label,
  val playerWinCount: Label,
  val bankerWinCount: Label,
  val tieWinCount: Label,
  val playerPairCount: Label,
  val bankerPairCount: Label,
  val totalCount: Label,
  val beadRoad: BeadRoadTilePane,
  val bigEyeRoad: BigEyeRoadTilePane,
  val smallRoad: SmallRoadTilePane,
  val b1: BigEyeRoadLabel,
  val b2: SmallRoadLabel,
  val b3: CockroachRoadLabel,
  val p1: BigEyeRoadLabel,
  val p2: SmallRoadLabel,
  val p3: CockroachRoadLabel,
  val cockroachRoad: CockroachRoadTilePane,
  val bigRoad: BigRoadTilePane)(implicit display: Display, echo: Port[String, Echo.Transition]) {

  beadRoad.Initialize(8, 20)
  bigRoad.Initialize(6, 49)
  bigEyeRoad.Initialize(6, 30)
  smallRoad.Initialize(6, 30)
  cockroachRoad.Initialize(6, 30)

  display.root.addEventHandler(
    KeyEvent.KEY_PRESSED,
    new EventHandler[KeyEvent] {
      override def handle(t: KeyEvent): Unit = {
        t.getCode match {
          case KeyCode.ENTER => {
            beadRoad.AddElement(BeadRoadResult.values().apply(scala.util.Random.nextInt(11)))
          }
          case _ => {
            beadRoad.RemoveElement()
          }
        }
      }
    }
  )

  beadRoad
    .getCountProperty()
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.longValue() > t1.longValue()) {
          bigRoad.AddElement(beadRoad);
        } else {
          bigRoad.RemoveElement(beadRoad);
        }
        if (t2.intValue() > 0) {
          totalCount.setText(String.valueOf(t2.intValue()));
        } else {
          totalCount.setText("");
        }
        bigRoad.UpdatePredictions(b1, b2, b3, p1, p2, p3)
      }
    })

  beadRoad
    .getBankerWinCount()
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          bankerWinCount.setText(String.valueOf(t2.intValue()));
        } else {
          bankerWinCount.setText("");
        }
      }
    })

  beadRoad
    .getPlayerWinCount()
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          playerWinCount.setText(String.valueOf(t2.intValue()));
        } else {
          playerWinCount.setText("");
        }
      }
    })

  beadRoad
    .getTieWinCount()
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          tieWinCount.setText(String.valueOf(t2.intValue()));
        } else {
          tieWinCount.setText("");
        }
      }
    })

  beadRoad
    .getBankerPairCount()
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          bankerPairCount.setText(String.valueOf(t2.intValue()));
        } else {
          bankerPairCount.setText("");
        }
      }
    })

  beadRoad
    .getPlayerPairCount()
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          playerPairCount.setText(String.valueOf(t2.intValue()));
        } else {
          playerPairCount.setText("");
        }
      }
    })

  bigRoad
    .bigEyeRoadListProperty()
    .addListener(new ChangeListener[ObservableList[BigEyeRoadLabel]] {
      override def changed(
        observableValue: ObservableValue[_ <: ObservableList[BigEyeRoadLabel]],
        t: ObservableList[BigEyeRoadLabel],
        t1: ObservableList[BigEyeRoadLabel]): Unit = {
        bigEyeRoad.ReArrangeElements(t1)
      }
    })

  bigRoad
    .smallRoadListProperty()
    .addListener(new ChangeListener[ObservableList[SmallRoadLabel]] {
      override def changed(
        observableValue: ObservableValue[_ <: ObservableList[SmallRoadLabel]],
        t: ObservableList[SmallRoadLabel],
        t1: ObservableList[SmallRoadLabel]): Unit = {
        smallRoad.ReArrangeElements(t1)
      }
    })

  bigRoad
    .cockroachRoadListProperty()
    .addListener(new ChangeListener[ObservableList[CockroachRoadLabel]] {
      override def changed(
        observableValue: ObservableValue[_ <: ObservableList[CockroachRoadLabel]],
        t: ObservableList[CockroachRoadLabel],
        t1: ObservableList[CockroachRoadLabel]): Unit = {
        cockroachRoad.ReArrangeElements(t1)
      }
    })

  display.root.iconifiedProperty().values.subscribe(i => if (i) echo.cancel() else echo.restart())
  display.root.setOnCloseRequest(_ => {
    echo.cancel() // stop service
    display.exit()
  })
}
