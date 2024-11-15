package mod.maxbogomol.fluffy_fur.common.book;

import java.util.ArrayList;
import java.util.List;

public class CustomBookHandler {
    public static List<CustomBook> books = new ArrayList<>();

    public static void register(CustomBook book) {
        books.add(book);
    }

    public static List<CustomBook> getBooks() {
        return books;
    }
}
