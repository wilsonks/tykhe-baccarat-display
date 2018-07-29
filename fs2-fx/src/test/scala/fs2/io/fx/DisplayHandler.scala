package fs2.io.fx

import customjavafx.scene.control.BeadRoadResult
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
  bigRoad.Initialize(4, 20)
  bigEyeRoad.Initialize(4, 21)

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
        bigRoad.ReArrangeElements(beadRoad)
//        bigEyeRoad.ReArrangeElements(bigRoad)
      }
    })

  bigRoad
    .bigEyeRoadListProperty()
    .addListener(new ChangeListener[ObservableList[String]] {
      override def changed(
        observableValue: ObservableValue[_ <: ObservableList[String]],
        t: ObservableList[String],
        t1: ObservableList[String]): Unit = {
        println("--------------------------------->")
        t1.forEach(x => {
          print(x);
        })
        println("<---------------------------------")

      }
    })

  display.root.iconifiedProperty().values.subscribe(i => if (i) echo.cancel() else echo.restart())
  display.root.setOnCloseRequest(_ => {
    echo.cancel() // stop service
    display.exit()
  })
}
