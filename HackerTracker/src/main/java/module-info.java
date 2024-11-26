module org.openjfx.HackerTracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
	requires javafx.base;
	requires java.base;

    opens org.openjfx.HackerTracker to javafx.fxml;
    exports org.openjfx.HackerTracker;
}
