public class BacktrackElement {
    private String value;           //The element to undo
    private int index;              //The location of 'value'
    private boolean isOriginal;     //Is 'value' the inserted item or a kicked one

    public BacktrackElement (String value, int i, boolean original){
        this.value = value;
        this.index = i;
        this.isOriginal = original;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public boolean isOriginal() {
        return isOriginal;
    }
}