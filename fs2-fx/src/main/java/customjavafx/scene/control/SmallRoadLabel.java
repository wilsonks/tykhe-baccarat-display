package customjavafx.scene.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;

public class SmallRoadLabel extends Label {
    //Add States
    private static final PseudoClass PSEUDO_CLASS_RED = PseudoClass.getPseudoClass("red");
    private static final PseudoClass PSEUDO_CLASS_BLUE = PseudoClass.getPseudoClass("blue");

    //Private object property
    private ObjectProperty<SmallRoadResult> result = new ObjectPropertyBase<SmallRoadResult>() {
        @Override
        protected void invalidated() {
            switch(get()) {
                case RED:
                    pseudoClassStateChanged(PSEUDO_CLASS_RED,true);
                    pseudoClassStateChanged(PSEUDO_CLASS_BLUE,false);
                    break;
                case BLUE:
                    pseudoClassStateChanged(PSEUDO_CLASS_RED,false);
                    pseudoClassStateChanged(PSEUDO_CLASS_BLUE,true);
                    break;
                default:
            }
        }

        @Override
        public Object getBean() {
            return SmallRoadLabel.this;
        }

        @Override
        public String getName() {
            return "SmallRoadLabel";
        }
    };

    //Getters & Setters for the object property
    public SmallRoadResult getResult() {
        return result.get();
    }

    public ObjectProperty<SmallRoadResult> resultProperty() {
        return result;
    }

    public void setResult(SmallRoadResult result) {
        this.result.set(result);
    }
}
