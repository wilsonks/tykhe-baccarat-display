package fs2.io.fx

import better.files._
import cats.Show
import cats.effect.IO
import com.typesafe.config.ConfigFactory
import fs2.io.fx.Display.{Bounds, Dimension}

object TestDisplay extends App {

  implicit def show[A]: Show[A] = Show.fromToString[A]

  val conf = ConfigFactory.load
  val databaseFile = File(conf.getString("database.file"))

  Display
    .launch(
      for {
        startMenu <- IO[Menu] {
          // read previous state from file
          if (databaseFile.exists) {
            println("Exists")
            databaseFile.readDeserialized[Menu]

          } else {
            println("Does not Exists")
            databaseFile.createIfNotExists(asDirectory = false, createParents = true)
            databaseFile.writeSerialized(Menu("222", "500", "50000", "500", "50000", "500", "5000"))
            databaseFile.readDeserialized[Menu]
          }
        }
        window = Display.Window(
          bounds = Bounds(
            width = Dimension(min = Option(1600.0), max = Option(1920.0)),
            height = Dimension(min = Option(900.0), max = Option(1080.0))),
          fullscreen = true,
          fxml = "baccarat.fxml",
          resolver = Display.resolve(
            Display.resolveBySubType(Host[Menu, Unit](menu =>
              IO[Unit] {
                // serialize and write to file
                databaseFile.writeSerialized(menu)
            })),
            Display.resolveBySubType(startMenu)
          ),
          alwaysOnTop = true
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
