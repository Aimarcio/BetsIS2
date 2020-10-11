package cell;

public class CardCell extends Cell {
    private int type;
	public CardCell(int type, String name) {
        setName(name);
        this.type = type;
    }
    
    public boolean playAction(String msg) {
    }
    
    public int getType() {
        return type;
    }
}
