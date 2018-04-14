package udc.customfx.calendar;

import com.ibm.icu.text.NumberFormat;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Calendar extends AnchorPane{
    private Locale locale = Locale.ENGLISH;
    private final ObjectProperty<LocalDate> selected = new SimpleObjectProperty<>(this, "selectd", null);
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>(this, "selected", LocalDate.now());

    public static final Locale ENGLISH = Locale.ENGLISH;
    public static final Locale SIMPLIFIED_CHINESE = Locale.forLanguageTag("zh-CN-u-nu-hans");
    public static final Locale TRADITIONAL_CHINESE = Locale.forLanguageTag("zh-TW-u-nu-hant");

    public static final Locale ARABIC = Locale.forLanguageTag("ar-AE-u-nu-arab");
    public static final Locale JAPANESE = Locale.forLanguageTag("ja-JP-u-nu-jpan");
    public static final Locale RUSSIAN = Locale.forLanguageTag("ru-RU-u-nu-cyrl");

    public static final Locale SPANISH = Locale.forLanguageTag("es-ES-u-nu-latn");

    @FXML
    private HBox body;

    @FXML
    private JFXComboBox<String> year;

    @FXML
    private Label month;

    @FXML
    private FontAwesomeIconView arrow_right, arrow_left;

    @FXML
    public void initialize() {
        arrow_left.setOnMouseClicked(event -> {
            date.set(date.get().minus(1, ChronoUnit.MONTHS));
            date.get().withDayOfMonth(1);
            this.paint();
        });

        arrow_right.setOnMouseClicked(event -> {
            date.set(date.get().plus(1, ChronoUnit.MONTHS));
            date.get().withDayOfMonth(1);
            this.paint();
        });

        year.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null && !newValue.isEmpty()) {
                    date.set(date.get().withYear(NumberFormat.getInstance(this.getLocale()).parse(newValue).intValue()));
                    this.paint();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    public Calendar(double width, double height) throws IOException {
        this(width, height, Locale.ENGLISH);
    }

    public Calendar(double width, double height, Locale lang) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Calendar.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        this.setSize(width, height);
        this.setLocale(lang);
    }

    private void paint() {
        month.setText(date.get().getMonth().getDisplayName(TextStyle.FULL, this.getLocale()));

        LocalDate temp = date.get().withDayOfMonth(1);
        int maxDays = temp.getMonth().length(date.get().isLeapYear());
        int row = 1, col = 1;

        for (col = 1; col <= 7; col++) {
            for (Node n : ((VBox) body.lookup("#col" + col)).getChildren()) {
                if (n instanceof AnchorPane)
                    ((AnchorPane) n).getChildren().clear();
            }
        }

        col = temp.getDayOfWeek().getValue();
        NumberFormat formatter = NumberFormat.getInstance(this.getLocale());
        LocalDateTime today = LocalDateTime.now();

        while (temp.getDayOfMonth() <= maxDays && date.get().getMonth() == temp.getMonth()) { // add label
            VBox column = (VBox) body.lookup("#col" + col);
            AnchorPane panel = (AnchorPane) column.lookup(".row" + row);


            Label label = new Label(formatter.format(temp.getDayOfMonth()));
            label.setFont(Font.font(12));
            AnchorPane.setTopAnchor(label, 0.0);
            AnchorPane.setBottomAnchor(label, 0.0);
            AnchorPane.setLeftAnchor(label, 0.0);
            AnchorPane.setRightAnchor(label, 0.0);
            label.setAlignment(Pos.CENTER);

            panel.getChildren().add(label);

            if (temp.getDayOfMonth() >= today.getDayOfMonth()) {
                LocalDate finalTemp = temp;
                label.setOnMouseClicked(event -> this.select(finalTemp));

                if (selected.get() != null) {
                    if (selected.get().isEqual(temp))
                        label.getStyleClass().add("selected");
                } else if (temp.isEqual(LocalDate.now())) {
                    label.getStyleClass().add("selected");
                }
            }

            temp = temp.plus(1, ChronoUnit.DAYS);

            col++;

            if (col == 8) {
                col = 1;
                row++;
            }
        }
    }

    public void select(LocalDate localDate) {
        this.selected.setValue(localDate);
        this.date.setValue(localDate);

        this.paint();
    }

    public final ObjectProperty<LocalDate> selectedProperty() {
        return selected;
    }

    public final ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public Locale getLocale() {
        return locale;
    }

    public LocalDate getSelected() {
        return selected.get();
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;

        year.getItems().clear();

        for (int i = date.get().getYear() - 50; i < date.get().getYear() + 50; i++)
            year.getItems().add(NumberFormat.getInstance(this.getLocale()).format(i).replaceAll("[,.]", ""));

        year.getSelectionModel().select(50);

        if (selected.getValue() == null)
            this.select(LocalDate.now());
        else
            this.select(selected.getValue());

        this.paint();
    }

    public void setSize(double width, double height) {
        this.setPrefSize(width, height);

        year.setLayoutX(width / 2 - 75);
        for (int i = 1; i <= 7; i++)
            for (Node n : ((VBox) body.lookup("#col" + i)).getChildren())
                if (n instanceof AnchorPane)
                    ((AnchorPane) n).setPrefHeight((height - 75) / 6);
    }

}
