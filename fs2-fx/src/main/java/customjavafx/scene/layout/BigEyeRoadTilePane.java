package customjavafx.scene.layout;


import customjavafx.scene.control.*;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;

public class BigEyeRoadTilePane extends TilePane {

    private int column = 0;
    private int row = -1;

    public BigEyeRoadTilePane() {
        super();
        super.setOrientation(Orientation.VERTICAL);
        super.setAlignment(Pos.TOP_LEFT);
    }

    private int sizeLimit() {
        return (getPrefColumns() * getPrefRows());
    }

    private boolean childrenLimitReached() {
        return (getChildren().size() == sizeLimit());
    }

    private void Insert() {
        this.getChildren().add(new BigEyeRoadLabel(BigEyeRoadResult.EMPTY));
    }

    private int getCurrentPosition() {
        return (column * getPrefRows()) + row;
    }

    private void ClearLabel(BigEyeRoadLabel t) {
        t.setResult(BigEyeRoadResult.EMPTY);
    }

    private int FindDragonLength(int column,BigRoadTilePane road) {
        int dragonLength = 0;
        int pos= column*4;
        while(((BigRoadLabel)road.getChildren().get(pos)).getResult() != BigRoadResult.EMPTY ){
            if(pos%4 == 3){
                dragonLength++;
                pos = pos + 4;
            }else {
                dragonLength++;
                pos++;
            }
            if(pos >= road.getSizeLimit())break;
        }
        return dragonLength;
    }

    public void ReArrangeElements(BigRoadTilePane road) {
        getChildren().stream().map(x -> (BigEyeRoadLabel)x ).forEach(t-> ClearLabel(t));
        this.column = 0;
        this.row = -1;
        int currentColumnLength = 0;
        int targetColumnLength = 0;
        for(int pos= 4; pos <= road.getCurrentPosition();pos++) {
            if(((BigRoadLabel)road.getChildren().get(pos)).getResult() != BigRoadResult.EMPTY) {
                if((pos % 4) != 0) {
                    currentColumnLength= (pos % 4)+1;
                    targetColumnLength = FindDragonLength((pos /4) - 1,road);
                }
            }
        }
    }

    public void Initialize(int row, int column) {
        this.setPrefRows(row);
        this.setPrefColumns(column);
        while (!childrenLimitReached()) {
            Insert();
        }
        this.column = 0;
        this.row = -1;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
    }


}
