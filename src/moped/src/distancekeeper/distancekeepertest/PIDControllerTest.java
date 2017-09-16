package distancekeeper.distancekeepertest;

import distancekeeper.PIDController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Emil Jansson on 2017-09-16.
 */
class PIDControllerTest{



    @Test
    void evaluation() {
        double targetValue = 50;
        PIDController pid = new PIDController(targetValue, 0.3, 1/10, 2);
        double currentValue = 0;
        double currentSpeedOfChange = 1;
        double lastError = 51;

        for (int i=0; i<100; i++){
            double throttle = pid.evaluation(currentValue,1);
            currentSpeedOfChange = currentSpeedOfChange + throttle - currentSpeedOfChange * 0.1;
            currentValue+= currentSpeedOfChange;
            double error = currentValue-targetValue;
            System.out.println(error);
            assertTrue(Math.abs(error) < lastError || Math.abs(error) < 2);
            lastError = Math.abs(error);

        }

    }

}