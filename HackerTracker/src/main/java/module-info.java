module org.openjfx.HackerTracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
	requires org.mongodb.driver.sync.client;
	requires org.mongodb.bson;
	requires javafx.base;

    opens org.openjfx.HackerTracker to javafx.fxml;
    exports org.openjfx.HackerTracker;
}
