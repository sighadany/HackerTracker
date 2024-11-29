module org.openjfx.HackerTracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
	requires javafx.base;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.annotation;
	requires java.base;
	requires java.desktop;

    opens org.openjfx.HackerTracker to javafx.fxml;
    exports org.openjfx.HackerTracker;
}
