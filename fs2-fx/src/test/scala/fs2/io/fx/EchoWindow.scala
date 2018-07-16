package fs2

package io

package fx

import com.jfoenix.controls.JFXSnackbar
import fs2.io.fx.syntax._
import javafx.scene.control.{Button, TextField}
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import scalafxml.core.macros.sfxml

@sfxml
class EchoWindow(val message: TextField, val change: Button, val input: TextField, val pane: Pane, val restart: Button)(
  implicit display: Display,
  echo: Port[String, Echo.Transition]) {

  val snacks: JFXSnackbar = new JFXSnackbar()
  snacks.registerSnackbarContainer(pane)

  restart
    .events(MouseEvent.MOUSE_CLICKED)
    .subscribe(_ => {
      echo.cancel()
      display.restart()
    })
  change.events(MouseEvent.MOUSE_CLICKED).subscribe(_ => echo.push(input.getText))
  echo.map[String](_._1).feedTo(message.textProperty())
  echo.ack.subscribe(s => snacks.show(s.toString, 4000))
  echo.stateProperty().values.subscribe(s => snacks.show(s.toString, 4000))
  display.root.iconifiedProperty().values.subscribe(i => if (i) echo.cancel() else echo.restart())
  display.root.setOnCloseRequest(_ => {
    echo.cancel() // stop service
    display.exit()
  })
}
