package fs2.io.fx

import customjavafx.scene.control.BeadRoadResult
import customjavafx.scene.layout.BeadRoadTilePane
import fs2.io.fx.syntax._
import javafx.scene.control.Label
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

  handBetMin.setOnMouseClicked(_ => {
    beadRoad.Remove()
    println(beadRoad.getPosition)
  })

  handBetMax.setOnMouseClicked(_ => {
    beadRoad.Add(BeadRoadResult.values().apply(scala.util.Random.nextInt(10)))
    println(beadRoad.getPosition)
  })

  display.root.iconifiedProperty().values.subscribe(i => if (i) echo.cancel() else echo.restart())
  display.root.setOnCloseRequest(_ => {
    echo.cancel() // stop service
    display.exit()
  })
}
