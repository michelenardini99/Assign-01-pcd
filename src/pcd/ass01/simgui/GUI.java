package pcd.ass01.simgui;


public class GUI {
    static public void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller);
        model.addObserver(view);
        view.setVisible(true);

        new Agent(model).start();
    }

}
