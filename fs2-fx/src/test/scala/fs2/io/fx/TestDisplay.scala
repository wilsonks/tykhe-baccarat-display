package fs2.io.fx

import cats.Show
import cats.data.NonEmptyList
import cats.effect.IO
import fs2.{async, patterns, Stream}
import fs2.async.mutable.Queue
import fs2.io.ConsoleIO
import fs2.io.fx.Display.{Bounds, Dimension}

object TestDisplay extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit def show[A]: Show[A] = Show.fromToString[A]

  Display
    .launch(
      for {
        _ <- ConsoleIO.put("info", "booting...")
        input <- async.boundedQueue[IO, String](1)
        window = Display.Window(
          bounds = Bounds(
            width = Dimension(min = Option(1600.0), max = Option(1920.0)),
            height = Dimension(min = Option(900.0), max = Option(1080.0))),
          fullscreen = true,
          fxml = "baccarat.fxml",
          resolver = Display.resolveByType(
            Port(
              input.enqueue1,
              Echo
                .stepper(input)
                .through(ConsoleIO.logger("baccart-display"))
                .mergeHaltBoth(ConsoleIO.stream.takeWhile(_.nonEmpty).to(input.enqueue).drain)
            )
          ),
          alwaysOnTop = true
        )
      } yield window
    )(args)
    .unsafeRunSync()
}

case class Echo(text: String) {

  def update(s: String): Echo = copy(s)
}

object Echo {

  type Transition = (String, Echo)

  def read(input: Queue[IO, String]): (String, Echo) => IO[String] = (_, _) => input.dequeue1

  def stepper(input: Queue[IO, String]): Stream[IO, Transition] =
    patterns.stepper(read(input))(IO.pure(("reset", Echo(""))), (s, _) => s.reverse, _ update _)

  def machine(input: Queue[IO, String], init: Transition): Stream[IO, NonEmptyList[Transition]] =
    patterns.machine(read(input))(IO.pure(("reset", Echo(""))), (s, _) => s, _ update _, {
      case ("clear", e) => ""
    })
}
