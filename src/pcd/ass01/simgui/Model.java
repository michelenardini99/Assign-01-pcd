package pcd.ass01.simgui;


import pcd.ass01.simenginecunc.AbstractSimulation;
import pcd.ass01.simtrafficexamples.*;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<ModelObserver> observers;
    private List<AbstractSimulation> simulations;
    private String state;
    protected static final String DEFAULT_STATE = "Simulation not running";

    public Model(){
        state = DEFAULT_STATE;
        observers = new ArrayList<ModelObserver>();
        simulations = new ArrayList<>();
    }

    public synchronized void update(String simulationType){
        state = simulationType;
        notifyObservers();
    }

    public synchronized String getState(){
        return state;
    }

    public void addObserver(ModelObserver obs){
        observers.add(obs);
    }

    private void notifyObservers(){
        for (ModelObserver obs: observers){
            obs.modelUpdated(this);
        }
    }


    public void runSimulation(int numSteps) throws InterruptedException {
        switch (state){
            case "Single Road Two Cars":
                simulations.add(new TrafficSimulationSingleRoadTwoCars());
                break;
            case "Single Road Several Cars":
                simulations.add(new TrafficSimulationSingleRoadSeveralCars());
                break;
            case "Single Road With Traffic Lights":
                simulations.add(new TrafficSimulationSingleRoadWithTrafficLightTwoCars());
                break;
            case "Cross Roads":
                simulations.add(new TrafficSimulationWithCrossRoads());
                break;
            case null, default:
                break;
        }

        simulations.getLast().setup();

        RoadSimStatistics stat = new RoadSimStatistics();
        RoadSimView view = new RoadSimView();
        view.display();

        simulations.getLast().addSimulationListener(stat);
        simulations.getLast().addSimulationListener(view);

        simulations.getLast().run(numSteps);

    }

    public void stopLastSimulation() {
        System.out.println("Stop last simulation");
        simulations.getLast().stop();
        simulations.remove(simulations.getLast());
    }
}
