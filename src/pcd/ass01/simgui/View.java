package pcd.ass01.simgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ModelObserver {

    private final JTextField numStepsField;
    private final JComboBox<String> simulationTypeComboBox;
    private final JLabel statusLabel;
    private final Controller controller;

    public View(Controller controller){
        setTitle("Simulation Control");
        this.controller = controller;
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        numStepsField = new JTextField(10);
        numStepsField.setText("100");

        statusLabel = new JLabel();
        String[] simulationTypes = {"Single Road Two Cars", "Single Road Several Cars", "Single Road With Traffic Lights", "Cross Roads"};
        simulationTypeComboBox = new JComboBox<>(simulationTypes);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    startSimulation();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
            }
        });

        // Layout components
        JPanel mainPanel = new JPanel(new GridLayout(4, 2));
        mainPanel.add(new JLabel("Simulation Type:"));
        mainPanel.add(simulationTypeComboBox);
        mainPanel.add(new JLabel("Number of Steps:"));
        mainPanel.add(numStepsField);
        mainPanel.add(startButton);
        mainPanel.add(stopButton);
        mainPanel.add(new JLabel("Status:"));
        mainPanel.add(statusLabel);

        add(mainPanel);

        setVisible(true);
    }

    private void startSimulation() throws InterruptedException {

        String selectedSimulation = (String) simulationTypeComboBox.getSelectedItem();

        int numSteps = Integer.parseInt(numStepsField.getText());

        controller.processStartSimulation(selectedSimulation, numSteps);
    }

    private void stopSimulation(){
        controller.processStopSimulation();
    }

    @Override
    public void modelUpdated(Model model) {
        try {
            System.out.println("[View] model updated => updating the view");
            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("state running: " + model.getState());
            });
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
