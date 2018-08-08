package fs2.io.fx

import cats.Show
import cats.effect.IO
import fs2.async
import fs2.io.fx.Display.{Bounds, Dimension}

object TestDisplay extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit def show[A]: Show[A] = Show.fromToString[A]

  Display
    .launch(
      for {
        input <- async.boundedQueue[IO, String](1)
        window = Display.Window(
          bounds = Bounds(
            width = Dimension(min = Option(1600.0), max = Option(1920.0)),
            height = Dimension(min = Option(900.0), max = Option(1080.0))),
          fullscreen = true,
          fxml = "baccarat.fxml",
          alwaysOnTop = true
        )
      } yield window
    )(args)
    .unsafeRunSync()
}
