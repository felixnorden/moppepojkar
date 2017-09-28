import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;


class Value extends JPanel {

    private final String name;
    private int maxInt = 0; //Highest int recorded
    private int minInt = 0; //Lowest int recorded

    private volatile XYSeries dataset;

    private final JLabel nameLabel;
    private final JLabel valueLabel;
    private final JLabel maxValueLabel;
    private final JLabel minValueLabel;

    Value(String name) {
        this.name = name;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Set up upperBox
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

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());

        minValueLabel = new JLabel("Min:");
        minValueLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        lowerPanel.add(minValueLabel, BorderLayout.WEST);

        maxValueLabel = new JLabel("Max:");
        maxValueLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        lowerPanel.add(maxValueLabel, BorderLayout.EAST);

        add(lowerPanel);

        //Set up border
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }

    public void setValue(int value) {
        if (value > maxInt)
            maxInt = value;
        if (value < minInt)
            minInt = value;

        valueLabel.setText(String.valueOf(value));
        maxValueLabel.setText(String.valueOf(maxInt));
        minValueLabel.setText(String.valueOf(minInt));
        if (dataset != null) {
            if(dataset.getItemCount() > 8000){
                dataset.clear();
            }
            dataset.add(dataset.getItemCount() + 1, value);
        }

        repaint();
    }

    public void setValue(String value) {
        valueLabel.setText(value);
        repaint();
    }


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

    private JFreeChart createChart(final XYDataset dataset) {
        return ChartFactory.createTimeSeriesChart(
                name,
                "Seconds",
                "Value",
                dataset,
                false,
                false,
                false);
    }
}
