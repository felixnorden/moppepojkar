import com.ClientCommunicator;
import com.CommunicationListener;
import com.MopedDataType;
import com.MopedState;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Main implements ActionListener, CommunicationListener {

    private ClientCommunicator client;

    //UI Elements
    private JFrame frame;
    private JButton connectButton;
    private JLabel statusLabel;
    private JTextField ipTextField;
    private JTextField portTextField;

    //List containing all of the below ValuePanels
    private ArrayList<ValuePanel> valuePanels = new ArrayList<ValuePanel>();

    //All the different ValuePanels. Not very good looking but works
    private ValuePanel state = new ValuePanel("State");
    private ValuePanel velocity = new ValuePanel("Velocity");
    private ValuePanel sensorDistance = new ValuePanel("Sensor Distance");
    private ValuePanel throttle = new ValuePanel("Throttle");
    private ValuePanel steering = new ValuePanel("Steering");
    private ValuePanel pidIntegral = new ValuePanel("PID Integral Sum");
    private ValuePanel pidTarget = new ValuePanel("PID Target");
    private ValuePanel pidP = new ValuePanel("PID P");
    private ValuePanel pidY = new ValuePanel("PID Y");
    private ValuePanel pidD = new ValuePanel("PID D");
    private ValuePanel custom1 = new ValuePanel("Custom1");
    private ValuePanel custom2 = new ValuePanel("Custom2");
    private ValuePanel custom3 = new ValuePanel("Custom3");

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("MOPED Debug");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setMinimumSize(new Dimension(600, 500));
        frame.setLayout(new BorderLayout());

        //Create sub-panels
        createStatusPanel();
        createConnectPanel();
        createValuePanel();

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.invalidate();
    }

    /**
     * Creates the middle panel and adds it to the main frame.
     */
    private void createValuePanel() {
        JPanel valuePanel = new JPanel();
        valuePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.add(valuePanel, BorderLayout.CENTER);
        valuePanel.setLayout(new GridLayout(5, 3, 2, 2));

        valuePanels.add(state);
        valuePanels.add(velocity);
        valuePanels.add(sensorDistance);
        valuePanels.add(throttle);
        valuePanels.add(steering);
        valuePanels.add(pidD);
        valuePanels.add(pidP);
        valuePanels.add(pidY);
        valuePanels.add(pidTarget);
        valuePanels.add(pidIntegral);
        valuePanels.add(custom1);
        valuePanels.add(custom2);
        valuePanels.add(custom3);

        for (final ValuePanel value : valuePanels) {
            valuePanel.add(value);
            value.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    value.showChart();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }

    /**
     * Creates the lower bar and adds it to the main frame.
     */
    private void createConnectPanel() {
        // create the connect bar panel and shove it down the bottom of the frame
        JPanel connectPanel = new JPanel();
        connectPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        connectPanel.setLayout(new BorderLayout());
        connectPanel.setPreferredSize(new Dimension(frame.getWidth(), 32));
        frame.add(connectPanel, BorderLayout.SOUTH);

        Box box = Box.createHorizontalBox();
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(Box.createRigidArea(new Dimension(2, 0)));

        ipTextField = new JTextField();
        ipTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        ipTextField.setMaximumSize(new Dimension(130, 24));
        ipTextField.setPreferredSize(new Dimension(130, 24));
        box.add(ipTextField);
        box.add(Box.createRigidArea(new Dimension(5, 0)));

        portTextField = new JTextField();
        ipTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        portTextField.setMaximumSize(new Dimension(60, 24));
        portTextField.setPreferredSize(new Dimension(60, 24));
        box.add(portTextField);

        connectButton = new JButton("Connect");
        connectButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        connectButton.setMaximumSize(new Dimension(100, 24));
        connectButton.addActionListener(this);


        connectPanel.add(box, BorderLayout.WEST);
        connectPanel.add(connectButton, BorderLayout.EAST);
    }

    /**
     * Creates the upper status panel and adds it the the main frame.
     */
    private void createStatusPanel() {
        // create the status bar panel and shove it down the bottom of the frame
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        frame.add(statusPanel, BorderLayout.NORTH);
        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 32));
        statusPanel.setLayout(new BorderLayout(0, 0));

        //Add things to statusPanel
        statusLabel = new JLabel();
        statusPanel.add(statusLabel, BorderLayout.WEST);
        setStatusLabel("Disconnected");
    }

    /**
     *  Sets the status label to the given string. Also add "Status: " before the string.
     * @param status
     */
    private void setStatusLabel(String status) {
        statusLabel.setText("Status: " + status);
    }

    private Main() {
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
        //Check if it was a button which was pressed
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();

            //Get which button was pressed
            if (button.getText().equals("Connect")) {
                //Connect button was clicked
                onConnectButtonClicked();
            } else if (button.getText().equals("Disconnect")) {
                //Disconnect button was clicked
                onDisconnectButtonClicked();
            }
        }
    }

    /**
     * If a clientcommunicator has been created, stops it and updates the UI to reflect it.
     */
    private void onDisconnectButtonClicked() {
        if (client != null) {
            client.stop();
            setStatusLabel("Stopped");
            connectButton.setText("Connect");
        }
    }

    /**
     * Creates a clientcommunicator with the entered args and starts it.
     * Updates the UI to reflect that a clientcommunicator has been created.
     */
    private void onConnectButtonClicked() {
        //Check that input has been entered
        if (!ipTextField.getText().isEmpty() || !portTextField.getText().isEmpty()) {
            client = new ClientCommunicator(ipTextField.getText(), Integer.parseInt(portTextField.getText()));
            client.addListener(this);
            setStatusLabel("Disconnect");
            connectButton.setText("Disconnect");
            client.start();
        } else {
            setStatusLabel("Enter ip and port before connecting");
        }
    }

    //Below are all the events that might be dispatched by the ClientCommunicator.

    public void onConnection() {
        setStatusLabel("Connected");
        connectButton.setText("Disconnect");
    }

    public void onStateChange(MopedState mopedState) {
        switch (mopedState) {
            case ACC:
                state.setValue("ACC");
                break;
            case MANUAL:
                state.setValue("Manual");
                break;
        }
    }

    public void onDisconnection() {
        setStatusLabel("Disconnected");
        connectButton.setText("Connect");
    }

    public void onValueChanged(MopedDataType mopedDataType, int i) {
        switch (mopedDataType) {
            case VELOCITY:
                velocity.setValue(i);
                break;
            case SENSOR_DISTANCE:
                sensorDistance.setValue(i);
                break;
            case PID_TARGET_VALUE:
                pidTarget.setValue(i);
                break;
            case PID_P_CONSTANT:
                pidP.setValue(i);
                break;
            case PID_Y_CONSTANT:
                pidY.setValue(i);
                break;
            case PID_D_CONSTANT:
                pidD.setValue(i);
                break;
            case PID_INTEGRAL_SUM:
                pidIntegral.setValue(i);
                break;
            case THROTTLE:
                throttle.setValue(i);
                break;
            case STEERING:
                steering.setValue(i);
                break;
            case CUSTOM_1:
                custom1.setValue(i);
                break;
            case CUSTOM_2:
                custom2.setValue(i);
                break;
            case CUSTOM_3:
                custom3.setValue(i);
                break;
        }
    }
}
