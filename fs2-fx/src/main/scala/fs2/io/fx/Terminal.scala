package fs2.io.fx

import better.files._
import cats.Show
import cats.effect.IO
import com.typesafe.config.ConfigFactory
import fs2.io.fx.Display.{Bounds, Dimension, Position}

object Terminal extends App {

  implicit def show[A]: Show[A] = Show.fromToString[A]

  val conf = ConfigFactory.load
  val databaseFile = File(conf.getString("database.file"))

  Display
    .launch(
      for {
        startMenu <- IO[Menu] {
          // read previous state from file
          if (databaseFile.exists) {
            databaseFile.readDeserialized[Menu]

          } else {
            databaseFile.createIfNotExists(asDirectory = false, createParents = true)
            databaseFile.writeSerialized(Menu("1", "100", "10000", "100", "10000", "500", "5000"))
            databaseFile.readDeserialized[Menu]
          }
        }
        window = Display.Window(
          title = conf.getString("title"),
          fullscreen = conf.getBoolean("window.fullscreen"),
          alwaysOnTop = conf.getBoolean("window.alwaysOnTop"),
          position = Position(Option(0.0), Option(0.0)),
          bounds = Bounds(
            width = Dimension(min = Option(1600.0), max = Option(1920.0)),
            height = Dimension(min = Option(900.0), max = Option(1080.0))),
          fxml = "baccarat.fxml",
          resolver = Display.resolve(
            Display.resolveBySubType(Host[Menu, Unit](menu =>
              IO[Unit] {
                // serialize and write to file
                databaseFile.writeSerialized(menu)
            })),
            Display.resolveBySubType(startMenu)
          )
        )
      } yield window
    )(args)
    .unsafeRunSync()
}

case class Menu(
  name: String,
  handBetMin: String,
  handBetMax: String,
  tieBetMin: String,
  tieBetMax: String,
  pairBetMin: String,
  pairBetMax: String)
