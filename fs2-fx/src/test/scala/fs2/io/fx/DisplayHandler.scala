package fs2.io.fx

import customjavafx.scene.control.{BeadRoadLabel, BeadRoadResult}
import customjavafx.scene.layout.BeadRoadTilePane
import fs2.io.fx.syntax._
import javafx.scene.control.Label
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.scene.layout.TilePane
import scalafxml.core.macros.sfxml

@sfxml(additionalControls = List("customjavafx.scene.layout"))
class DisplayHandler(val handBetMin: Label, val beadRoad: BeadRoadTilePane, val bigRoad: TilePane)(
  implicit display: Display,
  echo: Port[String, Echo.Transition]) {

  //BeadRoad
  beadRoad.getChildren.clear()
  for (r <- 1 to 8) {
    for (c <- 1 to 19) {
      val bead11: BeadRoadLabel = new BeadRoadLabel(BeadRoadResult.values().apply(scala.util.Random.nextInt(10)))
      beadRoad.Add(bead11)
    }
  }
  beadRoad.fillArea()

  for (i <- 1 to 3) beadRoad.Remove()

  display.root.iconifiedProperty().values.subscribe(i => if (i) echo.cancel() else echo.restart())
  display.root.setOnCloseRequest(_ => {
    echo.cancel() // stop service
    display.exit()
  })
}
