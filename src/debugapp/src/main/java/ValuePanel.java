import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;


class ValuePanel extends JPanel {

    private final String name;
    private int maxInt = 0; //Highest value recorded
    private int minInt = 0; //Lowest value recorded

    //Dataset containing X and Y values for the graph. Gets reset every time a new graph is created.
    private volatile XYSeries dataset;

    //UI elements
    private JLabel nameLabel;
    private JLabel valueLabel;
    private JLabel maxValueLabel;
    private JLabel minValueLabel;

    ValuePanel(String name) {
        this.name = name;

        setupPanel();
    }

    /**
     * Does the required setup for the valuepanel.
     */
    private void setupPanel() {
        //Set layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Set up upper area
        Box upperBox = Box.createVerticalBox();

        nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        valueLabel = new JLabel(String.valueOf(0));
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        upperBox.add(Box.createRigidArea(new Dimension(0, 2)));
        upperBox.add(nameLabel);
        upperBox.add(Box.createRigidArea(new Dimension(0, 2)));
        upperBox.add(valueLabel);
        add(upperBox);

        //Set up lower area
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());

        minValueLabel = new JLabel();
        minValueLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        maxValueLabel = new JLabel();
        maxValueLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        lowerPanel.add(minValueLabel, BorderLayout.WEST);
        lowerPanel.add(maxValueLabel, BorderLayout.EAST);
        add(lowerPanel);

        //Set up border
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }

    /**
     * Sets the max value label to the given int.
     *
     * @param value
     */
    private void setMaxValueLabel(int value) {
        maxValueLabel.setText(String.valueOf(value));
    }

    /**
     * Sets the min value label to the given int.
     *
     * @param value
     */
    private void setMinValueLabel(int value) {
        minValueLabel.setText(String.valueOf(value));
    }

    /**
     * Sets the value to a int.
     * Checks if given value is a new min/max value
     * Updates labels with new information.
     * Adds the new value to the dataset
     *
     * @param value
     */
    public void setValue(int value) {
        if (value > maxInt)
            maxInt = value;
        if (value < minInt)
            minInt = value;

        valueLabel.setText(String.valueOf(value));
        setMaxValueLabel(maxInt);
        setMinValueLabel(minInt);
        
        if (dataset != null) {
            if (dataset.getItemCount() > 8000) {
                dataset.clear();
            }
            dataset.add(dataset.getItemCount() + 1, value);
        }

        repaint();
    }

    /**
     * Sets the value to a string. Also hides max/min labels because they become redundant when the value is a string
     *
     * @param value
     */
    public void setValue(String value) {
        valueLabel.setText(value);
        maxValueLabel.setVisible(false);
        minValueLabel.setVisible(false);
        repaint();
    }

    /**
     * Creates the chart and the frame that displays it to the user in a new thread.
     */
    public void showChart() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //Create new frame
                JFrame f = new JFrame();
                f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                //Reset current dataset
                dataset = new XYSeries(name);
                //Create chart with dataset
                JFreeChart chart = ChartFactory.createXYLineChart(
                        name,
                        name,
                        "Value",
                        new XYSeriesCollection(dataset),
                        PlotOrientation.VERTICAL,
                        true, false, false);

                //Setup new chartpanel
                ChartPanel chartPanel = new ChartPanel(chart);
                chartPanel.setPreferredSize(new Dimension(560, 370));
                f.setContentPane(chartPanel);
                f.pack();
                f.setVisible(true);
            }
        });
        t.start();
    }
}
