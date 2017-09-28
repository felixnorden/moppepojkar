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

    private JFrame frame;
    private JButton connectButton;
    private JLabel statusLabel;
    private JTextField ipTextField;
    private JTextField portTextField;

    ArrayList<Value> valuePanels = new ArrayList<Value>();

    Value state = new Value("State");
    Value velocity = new Value("Velocity");
    Value sensorDistance = new Value("Sensor Distance");
    Value throttle = new Value("Throttle");
    Value steering = new Value("Steering");
    Value pidIntegral = new Value("PID Integral Sum");
    Value pidTarget = new Value("PID Target");
    Value pidP = new Value("PID P");
    Value pidY = new Value("PID Y");
    Value pidD = new Value("PID D");
    Value custom1 = new Value("Custom1");
    Value custom2 = new Value("Custom2");
    Value custom3 = new Value("Custom3");


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

        for (final Value value : valuePanels) {
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

    private void setStatusLabel(String status) {
        statusLabel.setText("Status: " + status);
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
                //Connect button was clicked
                onConnectButtonClicked();
            } else if (button.getText().equals("Disconnect")) {
                //Disconnect button was clicked
                onDisconnectButtonClicked();
            }
        }
    }

    private void onDisconnectButtonClicked() {
        if (client != null) {
            client.stop();
            setStatusLabel("Stopped");
            connectButton.setText("Connect");
        }
    }

    private void onConnectButtonClicked() {
        //Check for input
        if (!ipTextField.getText().isEmpty() || !portTextField.getText().isEmpty()) {
            client = new ClientCommunicator(ipTextField.getText(), Integer.parseInt(portTextField.getText()));
            client.addListener(this);
            setStatusLabel("Disconnect");
            connectButton.setText("Disconnect");
            client.start();
        } else {
            setStatusLabel("Enter ip and port before connecting");
            state.showChart();
        }
    }

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
