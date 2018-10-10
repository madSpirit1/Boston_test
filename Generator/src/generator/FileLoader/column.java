package generator.FileLoader;

/**
 * класс для описания колонок
 */
public class column {
    private String title;
    private int width;

    public column(String title, int width) {
        this.title = title;
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }
}
