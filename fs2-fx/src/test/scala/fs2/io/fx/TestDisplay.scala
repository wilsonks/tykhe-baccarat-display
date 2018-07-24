package fs2.io.fx

import cats.Show
import cats.effect.IO
import fs2.async
import fs2.io.ConsoleIO

object TestDisplay extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit def show[A]: Show[A] = Show.fromToString[A]

  Display
    .launch(
      for {
        _ <- ConsoleIO.put("info", "booting...")
        input <- async.boundedQueue[IO, String](1)
        window = Display.Window(
          fxml = "baccarat.fxml",
          resolver = Display.resolveByType(
            Port(
              input.enqueue1,
              Echo
                .stepper(input)
                .through(ConsoleIO.logger("echo"))
                .mergeHaltBoth(ConsoleIO.stream.takeWhile(_.nonEmpty).to(input.enqueue).drain)
            )
          ),
          alwaysOnTop = true
        )
      } yield window
    )(args)
    .unsafeRunSync()
}
