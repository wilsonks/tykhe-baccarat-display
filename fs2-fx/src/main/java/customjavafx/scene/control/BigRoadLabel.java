package customjavafx.scene.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;

public class BigRoadLabel extends Label {

    //Create states - 16
    private static final PseudoClass PSEUDO_CLASS_BANKER_WIN = PseudoClass.getPseudoClass("bankerWin");
    private static final PseudoClass PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR = PseudoClass.getPseudoClass("bankerWinBankerPair");
    private static final PseudoClass PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR = PseudoClass.getPseudoClass("bankerWinPlayerPair");
    private static final PseudoClass PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR = PseudoClass.getPseudoClass("bankerWinBothPair");

    private static final PseudoClass PSEUDO_CLASS_PLAYER_WIN = PseudoClass.getPseudoClass("playerWin");
    private static final PseudoClass PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR = PseudoClass.getPseudoClass("playerWinBankerPair");
    private static final PseudoClass PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR = PseudoClass.getPseudoClass("playerWinPlayerPair");
    private static final PseudoClass PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR = PseudoClass.getPseudoClass("playerWinBothPair");

    private static final PseudoClass PSEUDO_CLASS_TIE_AFTER_BANKER_WIN = PseudoClass.getPseudoClass("bankerWinBeforeTie");
    private static final PseudoClass PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR = PseudoClass.getPseudoClass("bankerWinBankerPairBeforeTie");
    private static final PseudoClass PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR = PseudoClass.getPseudoClass("bankerWinPlayerPairBeforeTie");
    private static final PseudoClass PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR = PseudoClass.getPseudoClass("bankerWinBothPairBeforeTie");

    private static final PseudoClass PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN = PseudoClass.getPseudoClass("playerWinBeforeTie");
    private static final PseudoClass PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR = PseudoClass.getPseudoClass("playerWinBankerPairBeforeTie");
    private static final PseudoClass PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR = PseudoClass.getPseudoClass("playerWinPlayerPairBeforeTie");
    private static final PseudoClass PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR = PseudoClass.getPseudoClass("playerWinBothPairBeforeTie");


    private ObjectProperty<BigRoadResult> result = new ObjectPropertyBase<BigRoadResult>() {
        @Override
        protected void invalidated() {
            switch (get()) {
                case BANKER_WIN:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case BANKER_WIN_BANKER_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case BANKER_WIN_PLAYER_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case BANKER_WIN_BOTH_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case PLAYER_WIN:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case PLAYER_WIN_BANKER_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case PLAYER_WIN_PLAYER_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case PLAYER_WIN_BOTH_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case TIE_AFTER_BANKER_WIN:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, true);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case TIE_AFTER_BANKER_WIN_BANKER_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case TIE_AFTER_BANKER_WIN_PLAYER_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case TIE_AFTER_BANKER_WIN_BOTH_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case TIE_AFTER_PLAYER_WIN:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case TIE_AFTER_PLAYER_WIN_BANKER_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case TIE_AFTER_PLAYER_WIN_PLAYER_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, true);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, false);
                    break;
                case TIE_AFTER_PLAYER_WIN_BOTH_PAIR:
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_PLAYER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN, false);

                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_BANKER_WIN_BOTH_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BANKER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_PLAYER_PAIR, false);
                    pseudoClassStateChanged(PSEUDO_CLASS_TIE_AFTER_PLAYER_WIN_BOTH_PAIR, true);
                    break;


                default:
            }
        }

        @Override
        public Object getBean() {
            return BigRoadLabel.this;
        }

        @Override
        public String getName() {
            return "BigRoadLabel";
        }
    };

    public BigRoadResult getResult() {
        return result.get();
    }

    public ObjectProperty<BigRoadResult> resultProperty() {
        return result;
    }

    public void setResult(BigRoadResult result) {
        this.result.set(result);
    }
}