package pcd.ass01.simgui;


public class Agent extends Thread{

    private Model model;

    public Agent(Model model){
        this.model = model;
    }

    public void run(){
        while (true){
            try {
                model.update(model.getState());
                Thread.sleep(500);
            } catch (Exception ex){
            }
        }
    }
}
