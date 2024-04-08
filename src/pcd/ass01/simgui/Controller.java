package pcd.ass01.simgui;

public class Controller {
    private Model model;
    public Controller(Model model){
        this.model = model;
    }

    public void processStartSimulation(String simulation, int numSteps) {
        try {
            new Thread(() -> {
                try {
                    model.update(simulation);
                    model.runSimulation(numSteps);
                    model.update(Model.DEFAULT_STATE);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void processStopSimulation(){
        new Thread(() -> {
            try{
                model.stopLastSimulation();
                model.update(Model.DEFAULT_STATE);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }).start();
    }
}
