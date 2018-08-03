package fs2.io.fx

import customjavafx.scene.control._
import customjavafx.scene.layout._
import fs2.io.fx.syntax._
import javafx.animation._
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.input.{KeyCode, KeyEvent}
import javafx.scene.transform.Rotate
import javafx.util.Duration
import scalafx.scene.media.AudioClip
import scalafxml.core.macros.sfxml

@sfxml(additionalControls = List("customjavafx.scene.layout", "customjavafx.scene.control"))
class DisplayHandler(
  val playerWinCount: Label,
  val bankerWinCount: Label,
  val tieWinCount: Label,
  val playerPairCount: Label,
  val bankerPairCount: Label,
  val totalCount: Label,
  val beadRoad: BeadRoadTilePane,
  val bigEyeRoad: BigEyeRoadTilePane,
  val smallRoad: SmallRoadTilePane,
  val cockroachRoad: CockroachRoadTilePane,
  val b1: BigEyeRoadLabel,
  val b2: SmallRoadLabel,
  val b3: CockroachRoadLabel,
  val p1: BigEyeRoadLabel,
  val p2: SmallRoadLabel,
  val p3: CockroachRoadLabel,
  val lastWin: LastWinLabel,
  val bigRoad: BigRoadTilePane)(implicit display: Display, echo: Port[String, Echo.Transition]) {

  beadRoad.Initialize(8, 14)
  bigRoad.Initialize(6, 49)
  bigEyeRoad.Initialize(6, 30)
  smallRoad.Initialize(6, 30)
  cockroachRoad.Initialize(6, 30)

  display.root.addEventHandler(
    KeyEvent.KEY_PRESSED,
    new EventHandler[KeyEvent] {
      var pPair: Boolean = false
      var bPair: Boolean = false
      var bothPair: Boolean = false
      var natural: Boolean = false
      var bWin: Boolean = false
      var pWin: Boolean = false
      var tWin: Boolean = false

      override def handle(t: KeyEvent): Unit = {
        t.getCode match {
          case KeyCode.ENTER =>
            (bPair, pPair, bothPair, natural, bWin, pWin, tWin) match {
              case (false, false, false, false, true, false, false) => beadRoad.AddElement(BeadRoadResult.BANKER_WIN)
              case (true, false, false, false, true, false, false) =>
                beadRoad.AddElement(BeadRoadResult.BANKER_WIN_BANKER_PAIR)
              case (false, true, false, false, true, false, false) =>
                beadRoad.AddElement(BeadRoadResult.BANKER_WIN_PLAYER_PAIR)
              case (false, false, true, false, true, false, false) =>
                beadRoad.AddElement(BeadRoadResult.BANKER_WIN_BOTH_PAIR)
              case (false, false, false, true, true, false, false) =>
                beadRoad.AddElement(BeadRoadResult.BANKER_WIN_NATURAL)
              case (true, false, false, true, true, false, false) =>
                beadRoad.AddElement(BeadRoadResult.BANKER_WIN_BANKER_PAIR_NATURAL)
              case (false, true, false, true, true, false, false) =>
                beadRoad.AddElement(BeadRoadResult.BANKER_WIN_PLAYER_PAIR_NATURAL)
              case (false, false, true, true, true, false, false) =>
                beadRoad.AddElement(BeadRoadResult.BANKER_WIN_BOTH_PAIR_NATURAL)

              case (false, false, false, false, false, true, false) => beadRoad.AddElement(BeadRoadResult.PLAYER_WIN)
              case (true, false, false, false, false, true, false) =>
                beadRoad.AddElement(BeadRoadResult.PLAYER_WIN_BANKER_PAIR)
              case (false, true, false, false, false, true, false) =>
                beadRoad.AddElement(BeadRoadResult.PLAYER_WIN_PLAYER_PAIR)
              case (false, false, true, false, false, true, false) =>
                beadRoad.AddElement(BeadRoadResult.PLAYER_WIN_BOTH_PAIR)
              case (false, false, false, true, false, true, false) =>
                beadRoad.AddElement(BeadRoadResult.PLAYER_WIN_NATURAL)
              case (true, false, false, true, false, true, false) =>
                beadRoad.AddElement(BeadRoadResult.PLAYER_WIN_BANKER_PAIR_NATURAL)
              case (false, true, false, true, false, true, false) =>
                beadRoad.AddElement(BeadRoadResult.PLAYER_WIN_PLAYER_PAIR_NATURAL)
              case (false, false, true, true, false, true, false) =>
                beadRoad.AddElement(BeadRoadResult.PLAYER_WIN_BOTH_PAIR_NATURAL)

              case (false, false, false, false, false, false, true) => beadRoad.AddElement(BeadRoadResult.TIE_WIN)
              case (true, false, false, false, false, false, true) =>
                beadRoad.AddElement(BeadRoadResult.TIE_WIN_BANKER_PAIR)
              case (false, true, false, false, false, false, true) =>
                beadRoad.AddElement(BeadRoadResult.TIE_WIN_PLAYER_PAIR)
              case (false, false, true, false, false, false, true) =>
                beadRoad.AddElement(BeadRoadResult.TIE_WIN_BOTH_PAIR)
              case (false, false, false, true, false, false, true) =>
                beadRoad.AddElement(BeadRoadResult.TIE_WIN_NATURAL)
              case (true, false, false, true, false, false, true) =>
                beadRoad.AddElement(BeadRoadResult.TIE_WIN_BANKER_PAIR_NATURAL)
              case (false, true, false, true, false, false, true) =>
                beadRoad.AddElement(BeadRoadResult.TIE_WIN_PLAYER_PAIR_NATURAL)
              case (false, false, true, true, false, false, true) =>
                beadRoad.AddElement(BeadRoadResult.TIE_WIN_BOTH_PAIR_NATURAL)
              case _ =>
            }
            bPair = false;
            pPair = false;
            bothPair = false;
            natural = false;
            bWin = false;
            pWin = false;
            tWin = false;

          case KeyCode.END | KeyCode.NUMPAD1       => bWin = !bWin
          case KeyCode.DOWN | KeyCode.NUMPAD2      => pWin = !pWin
          case KeyCode.PAGE_DOWN | KeyCode.NUMPAD3 => tWin = !tWin

          case KeyCode.UP | KeyCode.NUMPAD8      => natural = !natural
          case KeyCode.PAGE_UP | KeyCode.NUMPAD9 => natural = !natural

          case KeyCode.LEFT | KeyCode.NUMPAD4  => bPair = !bPair
          case KeyCode.RIGHT | KeyCode.NUMPAD6 => bothPair = !bothPair
          case KeyCode.CLEAR | KeyCode.NUMPAD5 => pPair = !pPair

          case KeyCode.SUBTRACT               => beadRoad.RemoveElement()
          case KeyCode.HOME | KeyCode.NUMPAD7 => beadRoad.Reset()

          case _ => println(t)
        }
      }
    }
  )

  beadRoad.getCountProperty
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          lastWinAnimation.play()
          if (t2.longValue() > t1.longValue()) {
            bigRoad.AddElement(beadRoad)
          } else {
            bigRoad.RemoveElement(beadRoad)
          }
          new AudioClip(getClass.getResource(beadRoad.LastWinAudio()).toExternalForm).play()
          lastWin.setResult(beadRoad.LastWin())
          totalCount.setText(String.valueOf(t2.intValue()))
        } else {
          bigRoad.Reset()
          lastWin.setResult(LastWinResult.EMPTY)
          totalCount.setText("")
        }
        bigRoad.UpdatePredictions(b1, b2, b3, p1, p2, p3)
      }
    })

  beadRoad.getBankerWinCount
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          bankerWinCount.setText(String.valueOf(t2.intValue()))
        } else {
          bankerWinCount.setText("")
        }
      }
    })

  beadRoad.getPlayerWinCount
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          playerWinCount.setText(String.valueOf(t2.intValue()))
        } else {
          playerWinCount.setText("")
        }
      }
    })

  beadRoad.getTieWinCount
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          tieWinCount.setText(String.valueOf(t2.intValue()))
        } else {
          tieWinCount.setText("")
        }
      }
    })

  beadRoad.getBankerPairCount
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          bankerPairCount.setText(String.valueOf(t2.intValue()));
        } else {
          bankerPairCount.setText("");
        }
      }
    })

  beadRoad.getPlayerPairCount
    .addListener(new ChangeListener[Number] {
      override def changed(observableValue: ObservableValue[_ <: Number], t1: Number, t2: Number): Unit = {
        if (t2.intValue() > 0) {
          playerPairCount.setText(String.valueOf(t2.intValue()))
        } else {
          playerPairCount.setText("")
        }
      }
    })

  bigRoad.bigEyeRoadListProperty
    .addListener(new ChangeListener[ObservableList[BigEyeRoadLabel]] {
      override def changed(
        observableValue: ObservableValue[_ <: ObservableList[BigEyeRoadLabel]],
        t: ObservableList[BigEyeRoadLabel],
        t1: ObservableList[BigEyeRoadLabel]): Unit = {
        if (!t1.isEmpty) bigEyeRoad.ReArrangeElements(t1)
        else {
          bigEyeRoad.Reset()
        }
      }
    })

  bigRoad.smallRoadListProperty
    .addListener(new ChangeListener[ObservableList[SmallRoadLabel]] {
      override def changed(
        observableValue: ObservableValue[_ <: ObservableList[SmallRoadLabel]],
        t: ObservableList[SmallRoadLabel],
        t1: ObservableList[SmallRoadLabel]): Unit = {
        if (!t1.isEmpty) smallRoad.ReArrangeElements(t1)
        else {
          smallRoad.Reset()
        }
      }
    })

  bigRoad.cockroachRoadListProperty
    .addListener(new ChangeListener[ObservableList[CockroachRoadLabel]] {
      override def changed(
        observableValue: ObservableValue[_ <: ObservableList[CockroachRoadLabel]],
        t: ObservableList[CockroachRoadLabel],
        t1: ObservableList[CockroachRoadLabel]): Unit = {
        if (!t1.isEmpty) cockroachRoad.ReArrangeElements(t1)
        else {
          cockroachRoad.Reset()
        }
      }
    })

  val lastWinAnimation: RotateTransition = new RotateTransition(Duration.millis(50), lastWin)
  lastWinAnimation.setAxis(Rotate.Y_AXIS)
  lastWinAnimation.setByAngle(180)
  lastWinAnimation.setCycleCount(2)
  lastWinAnimation.setInterpolator(Interpolator.LINEAR)
  lastWinAnimation.setAutoReverse(true)

  display.root.iconifiedProperty().values.subscribe(i => if (i) echo.cancel() else echo.restart())
  display.root.setOnCloseRequest(_ => {
    echo.cancel() // stop service
    display.exit()
  })
}
