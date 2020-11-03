package seedu.momentum.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.momentum.logic.statistic.StatisticEntry;

/**
 * A UI component that displays pie chart of statistics shown in the statistics panel.
 */
public class PieChartCard extends UiPart<Region> {

    private static final String FXML = "PieChartCard.fxml";

    private ObservableList<PieChart.Data> statisticsData;

    @FXML
    private PieChart pieChart;

    /**
     * Creates a {@code PieChartCard} with the give {@code ObservableList}.
     *
     * @param statisticList list of statistics to be used for pie chart generation.
     */
    public PieChartCard(ObservableList<StatisticEntry> statisticList) {
        super(FXML);
        setStatistics(statisticList);
        pieChart.setData(statisticsData);
    }

    private void setStatistics(ObservableList<StatisticEntry> statisticList) {
        statisticsData = FXCollections.observableArrayList();
        double sum = statisticList.stream().map(StatisticEntry::getValue).reduce(0.0, Double::sum);
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
