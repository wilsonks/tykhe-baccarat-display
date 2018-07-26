package fs2.io.fx

import customjavafx.scene.control.BeadRoadResult
import customjavafx.scene.layout.{BeadRoadTilePane, BigRoadTilePane}
import fs2.io.fx.syntax._
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.input.{KeyCode, KeyEvent}
import scalafxml.core.macros.sfxml

@sfxml(additionalControls = List("customjavafx.scene.layout"))
class DisplayHandler(
  val handBetMin: Label,
  val handBetMax: Label,
  val beadRoad: BeadRoadTilePane,
  val bigRoad: BigRoadTilePane)(implicit display: Display, echo: Port[String, Echo.Transition]) {

  beadRoad.Initialize(8, 20)
  bigRoad.Initialize(6, 20)

  display.root.addEventHandler(
    KeyEvent.KEY_PRESSED,
    new EventHandler[KeyEvent] {
      override def handle(t: KeyEvent): Unit = {
        t.getCode match {
          case KeyCode.ENTER => {
            beadRoad.AddElement(BeadRoadResult.values().apply(scala.util.Random.nextInt(7)))
          }
          case _ => {
            beadRoad.RemoveElement()
          }
        }
      }
    }
  )

  beadRoad.count.addListener(new ChangeListener[Number] {
    override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
      if (t2.intValue() > t1.intValue()) {
        println(beadRoad.GetLastElement())
        println(s"Big Road Old Position:${bigRoad.getPosition}")
        bigRoad.AddElement(beadRoad.GetLastElement())
        println(s"Big Road New Position:${bigRoad.getPosition}")

      } else {
        println(s"--Big Road Old Position:${bigRoad.getPosition}")
        bigRoad.ShiftCellsBack(6)
        println(s"--Big Road New Position:${bigRoad.getPosition}")

      }

    }
  })

  display.root.iconifiedProperty().values.subscribe(i => if (i) echo.cancel() else echo.restart())
  display.root.setOnCloseRequest(_ => {
    echo.cancel() // stop service
    display.exit()
  })
}
