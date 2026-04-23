package practica2;

public class LikedItemNotFoundException extends Exception {

    private String item;

    public LikedItemNotFoundException(String item) {
        super("Item no encontrado: " + item);
        this.item = item;
    }

    public String getItem() {
        return item;
    }
}