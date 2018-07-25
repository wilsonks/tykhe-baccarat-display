package fs2.io.fx

import customjavafx.scene.control.BeadRoadResult
import customjavafx.scene.layout.BeadRoadTilePane
import fs2.io.fx.syntax._
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.scene.layout.TilePane
import scalafxml.core.macros.sfxml

@sfxml(additionalControls = List("customjavafx.scene.layout"))
class DisplayHandler(
  val handBetMin: Label,
  val handBetMax: Label,
  val beadRoad: BeadRoadTilePane,
  val bigRoad: TilePane)(implicit display: Display, echo: Port[String, Echo.Transition]) {

  //BeadRoad
  beadRoad.Initialize(8, 20)
  display.root.addEventHandler(
    KeyEvent.KEY_PRESSED,
    new EventHandler[KeyEvent] {
      override def handle(t: KeyEvent): Unit = {
        t.getCode match {
          case KeyCode.ENTER => {
            beadRoad.AddElement(BeadRoadResult.values().apply(scala.util.Random.nextInt(10)))
            println(beadRoad.count.toString)

          }
          case _ => {
            beadRoad.RemoveElement()
            println(beadRoad.count.toString)
          }
        }
      }
    }
  )

  beadRoad.count.addListener(new ChangeListener[Number] {
    override def changed(observableValue: ObservableValue[_ <: Number], t: Number, t1: Number): Unit = {
      println(s"Changed from $t $t1")
    }
  })

  display.root.iconifiedProperty().values.subscribe(i => if (i) echo.cancel() else echo.restart())
  display.root.setOnCloseRequest(_ => {
    echo.cancel() // stop service
    display.exit()
  })
}
