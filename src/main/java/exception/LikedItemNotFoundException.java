package exception;

public class LikedItemNotFoundException extends Exception {

    private String item;

    public LikedItemNotFoundException(String item) {
        super("Item not found: " + item);
        this.item = item;
    }

    public String getItem() {
        return item;
    }
}