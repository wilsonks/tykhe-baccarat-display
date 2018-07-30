package fs2.io.fx

import customjavafx.scene.control.{BeadRoadResult, BigEyeRoadLabel}
import customjavafx.scene.layout.{BeadRoadTilePane, BigEyeRoadTilePane, BigRoadTilePane}
import fs2.io.fx.syntax._
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.input.{KeyCode, KeyEvent}
import scalafxml.core.macros.sfxml

@sfxml(additionalControls = List("customjavafx.scene.layout"))
class DisplayHandler(
  val handBetMin: Label,
  val handBetMax: Label,
  val beadRoad: BeadRoadTilePane,
  val bigEyeRoad: BigEyeRoadTilePane,
  val bigRoad: BigRoadTilePane)(implicit display: Display, echo: Port[String, Echo.Transition]) {

  beadRoad.Initialize(8, 20)
  bigRoad.Initialize(6, 49)
  bigEyeRoad.Initialize(8, 30)

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
    .countProperty()
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.longValue() > t1.longValue()) {
          bigRoad.AddElement(beadRoad);
        } else {
          bigRoad.RemoveElement(beadRoad);
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

  display.root.iconifiedProperty().values.subscribe(i => if (i) echo.cancel() else echo.restart())
  display.root.setOnCloseRequest(_ => {
    echo.cancel() // stop service
    display.exit()
  })
}
