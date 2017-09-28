import com.ClientCommunicator;
import com.CommunicationListener;
import com.MopedDataType;
import com.MopedState;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main implements ActionListener, CommunicationListener {

    private ClientCommunicator client;

    private JFrame frame;
    private JButton connectButton;
    private JLabel statusLabel;
    private JTextField ipTextField;
    private JTextField portTextField;

    JLabel stateLabel;
    JLabel velocityLabel;
    JLabel sensorDistanceLabel;
    JLabel PID_TARGET_VALUELabel;
    JLabel PID_P_CONSTANTLabel;
    JLabel PID_Y_CONSTANTLabel;
    JLabel PID_D_CONSTANTLabel;
    JLabel PID_INTEGRAL_SUMLabel;
    JLabel THROTTLEabel;
    JLabel STEERINGLabel;
    JLabel CUSTOM_1Label;
    JLabel CUSTOM_2Label;
    JLabel CUSTOM_3Label;

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("MOPED Debug (Moppepojkar)");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setMinimumSize(new Dimension(600, 500));
        frame.setLayout(new BorderLayout());

        // create the status bar panel and shove it down the bottom of the frame
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 32));
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        //Add things to statusPanel
        statusLabel = new JLabel("Disconnected");
        statusPanel.add(statusLabel);

        statusPanel.add(Box.createHorizontalStrut(80));

        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);

        ipTextField = new JTextField();
        ipTextField.setMaximumSize(new Dimension(120, 30));
        ipTextField.setPreferredSize(new Dimension(130, 30));

        portTextField = new JTextField();
        portTextField.setMinimumSize(new Dimension(50, 30));
        portTextField.setPreferredSize(new Dimension(60, 30));

        statusPanel.add(ipTextField);

        statusPanel.add(portTextField);

        statusPanel.add(Box.createHorizontalStrut(80));
        statusPanel.add(connectButton);

        //Add values to main panel
        JPanel valuePanel = new JPanel();
        valuePanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight() - 50));
        valuePanel.setMaximumSize(new Dimension(frame.getWidth(), frame.getHeight() - 50));
        valuePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.add(valuePanel, BorderLayout.NORTH);
        valuePanel.setLayout(new GridLayout(5, 3, 5, 5));

        stateLabel = new JLabel("State: ");
        velocityLabel = new JLabel("Velocity: ");
        sensorDistanceLabel = new JLabel("Sensor distance: ");
        PID_TARGET_VALUELabel = new JLabel("PID Target value: ");
        PID_P_CONSTANTLabel = new JLabel("PID P Constant: ");
        PID_Y_CONSTANTLabel = new JLabel("PID Y Constant: ");
        PID_D_CONSTANTLabel = new JLabel("PID D Constant: ");
        PID_INTEGRAL_SUMLabel = new JLabel("PID IntegralSum: ");
        THROTTLEabel = new JLabel("Throttle: ");
        STEERINGLabel = new JLabel("Steering: ");
        CUSTOM_1Label = new JLabel("Custom1: ");
        CUSTOM_2Label = new JLabel("Custom2: ");
        CUSTOM_3Label = new JLabel("Custom3: ");


        valuePanel.add(stateLabel);
        valuePanel.add(velocityLabel);
        valuePanel.add(sensorDistanceLabel);
        valuePanel.add(THROTTLEabel);
        valuePanel.add(STEERINGLabel);
        valuePanel.add(PID_D_CONSTANTLabel);
        valuePanel.add(PID_P_CONSTANTLabel);
        valuePanel.add(PID_Y_CONSTANTLabel);
        valuePanel.add(PID_TARGET_VALUELabel);
        valuePanel.add(PID_INTEGRAL_SUMLabel);
        valuePanel.add(CUSTOM_1Label);
        valuePanel.add(CUSTOM_2Label);
        valuePanel.add(CUSTOM_3Label);


        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.invalidate();
    }

    public Main() {
        createAndShowGUI();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();

            if (button.getText().equals("Connect")) {
                onConnectButtonClicked();
            } else if (button.getText().equals("Disconnect")) {
                onDisconnectButtonClicked();
            }
        }
    }

    private void onDisconnectButtonClicked() {
        if (client != null) {
            client.stop();
            statusLabel.setText("Stopped");
            connectButton.setText("Connect");
        }
    }

    private void onConnectButtonClicked() {
        //Check for input
        if (!ipTextField.getText().isEmpty() || !portTextField.getText().isEmpty()) {
            client = new ClientCommunicator(ipTextField.getText(), Integer.parseInt(portTextField.getText()));
            client.addListener(this);
            statusLabel.setText("Connecting...");
            client.start();
        } else {
            statusLabel.setText("Enter info first");
        }
    }

    public void onConnection() {
        statusLabel.setText("Connected");
        connectButton.setText("Disconnect");
        resetLabels();
    }

    public void onStateChange(MopedState mopedState) {
        switch (mopedState) {
            case ACC:
                stateLabel.setText("State: ACC");
                break;
            case MANUAL:
                stateLabel.setText("State: Manual");
                break;
        }
    }

    public void onDisconnection() {
        statusLabel.setText("Disconnected");
    }

    public void onValueChanged(MopedDataType mopedDataType, int i) {
        switch (mopedDataType) {
            case VELOCITY:
                velocityLabel.setText("Velocity: " + i);
                break;
            case SENSOR_DISTANCE:
                sensorDistanceLabel.setText("Sensor distance: " + i);
                break;
            case PID_TARGET_VALUE:
                PID_TARGET_VALUELabel.setText("PID Target Value: " + i);
                break;
            case PID_P_CONSTANT:
                PID_P_CONSTANTLabel.setText("PID P Constant: " + i);
                break;
            case PID_Y_CONSTANT:
                PID_Y_CONSTANTLabel.setText("PID Y Constant: " + i);
                break;
            case PID_D_CONSTANT:
                PID_D_CONSTANTLabel.setText("PID D Constant: " + i);
                break;
            case PID_INTEGRAL_SUM:
                PID_INTEGRAL_SUMLabel.setText("PID IntegralSum: " + i);
                break;
            case THROTTLE:
                THROTTLEabel.setText("Throttle: " + i);
                break;
            case STEERING:
                STEERINGLabel.setText("Steering: " + i);
                break;
            case CUSTOM_1:
                CUSTOM_1Label.setText("Custom1: " + i);
                break;
            case CUSTOM_2:
                CUSTOM_2Label.setText("Custom2: " + i);
                break;
            case CUSTOM_3:
                CUSTOM_3Label.setText("Custom3: " + i);
                break;
        }
    }

    private void resetLabels() {
        stateLabel = new JLabel("State: ");
        velocityLabel = new JLabel("Velocity: ");
        sensorDistanceLabel = new JLabel("Sensor distance: ");
        PID_TARGET_VALUELabel = new JLabel("PID Target value: ");
        PID_P_CONSTANTLabel = new JLabel("PID P Constant: ");
        PID_Y_CONSTANTLabel = new JLabel("PID Y Constant: ");
        PID_D_CONSTANTLabel = new JLabel("PID D Constant: ");
        PID_INTEGRAL_SUMLabel = new JLabel("PID IntegralSum: ");
        THROTTLEabel = new JLabel("Throttle: ");
        STEERINGLabel = new JLabel("Steering: ");
        CUSTOM_1Label = new JLabel("Custom1: ");
        CUSTOM_2Label = new JLabel("Custom2: ");
        CUSTOM_3Label = new JLabel("Custom3: ");

        frame.invalidate();
    }
}
