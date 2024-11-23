module org.openjfx.HackerTracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens org.openjfx.HackerTracker to javafx.fxml;
    exports org.openjfx.HackerTracker;
}
