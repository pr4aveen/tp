//@@author kkangs0226
package seedu.momentum.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.momentum.commons.exceptions.IllegalValueException;
import seedu.momentum.logic.statistic.StatisticEntry;

/**
 * A UI component that displays pie chart of statistics shown in the statistics panel.
 */
public class PieChartCard extends UiPart<Region> {

    private static final String FXML = "PieChartCard.fxml";

    private ObservableList<PieChart.Data> statisticsData;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label message;

    /**
     * Creates a {@code PieChartCard} with the give {@code ObservableList}.
     *
     * @param statisticList List of statistics to be used for pie chart generation.
     */
    public PieChartCard(ObservableList<StatisticEntry> statisticList) {
        super(FXML);

        try {
            setStatistics(statisticList);
            pieChart.setData(statisticsData);
        } catch (IllegalValueException e) {
            pieChart.setTitle("There are no durations in the list!\n"
                    + "Start timing now.");
        }
    }

    private void setStatistics(ObservableList<StatisticEntry> statisticList) throws IllegalValueException {
        statisticsData = FXCollections.observableArrayList();
        double sum = statisticList.stream().map(StatisticEntry::getValue).reduce(0.0, Double::sum);

        if (sum == 0) {
            throw new IllegalValueException("No durations in this list");
        }
        for (StatisticEntry s : statisticList) {
            double value = s.getValue();
            String label = s.getLabel();
            double percentage = calculatePercentage(value, sum);
            PieChart.Data data = new PieChart.Data(wrapLabelText(label, percentage), value);
            statisticsData.add(data);
        }
    }

    private String wrapLabelText(String label, double percentage) {
        String pieChartLabel;
        if (label.length() > 9) {
            pieChartLabel = label.substring(0, 10) + "...\n";
        } else {
            pieChartLabel = label + "\n";
        }
        return pieChartLabel + String.format("%.1f%%", percentage);
    }

    private double calculatePercentage(double value, double sum) {
        return (value / sum) * 100;
    }
}
