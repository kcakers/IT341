package it341.happening;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Guzmop on 5/3/17.
 */

public class BookmarkManager {

    private ArrayList<YelpLocation> bookmarks = new ArrayList<>();
    private static BookmarkManager sharedInstance;
    private FirebaseCloudSaver saver;

    private BookmarkManager() {
        saver = new FirebaseCloudSaver();
    }

    public static BookmarkManager getInstance() {
        if(sharedInstance == null) {
            sharedInstance = new BookmarkManager();
        }

        return sharedInstance;
    }

    public void addBookmark(YelpLocation location) {
        if(!contains(location)) {
            bookmarks.add(location);
            saver.saveBookmarks(bookmarks);
        }
    }

    public void setBookmarks(ArrayList<YelpLocation> bookmarks) {
        this.bookmarks = new ArrayList<>(bookmarks);
    }

    public void removeBookmark(YelpLocation location) {
        if(contains(location)) {
            bookmarks.remove(location);
            saver.saveBookmarks(bookmarks);
        }
    }

    public ArrayList<YelpLocation> getBookmarks() {
        return bookmarks;
    }

    public void save() {
        saver.saveBookmarks(bookmarks);
    }

    public void load() {
        saver.downloadBookmarks();
    }

    public boolean contains(YelpLocation location) {
        return bookmarks.contains(location);
    }
}
