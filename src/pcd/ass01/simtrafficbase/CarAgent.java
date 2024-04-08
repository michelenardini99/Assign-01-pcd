package pcd.ass01.simtrafficbase;

import java.util.Optional;

import pcd.ass01.simenginecunc.*;

/**
 * 
 * Base class modeling the skeleton of an agent modeling a car in the traffic environment
 * 
 */
public abstract class CarAgent extends AbstractAgent{
	
	/* car model */
	protected double maxSpeed;		
	protected double currentSpeed;  
	protected double acceleration;
	protected double deceleration;
	protected int dt;

	/* percept and action retrieved and submitted at each step */
	protected CarPercept currentPercept;
	protected Optional<Action> selectedAction;
	
	
	public CarAgent(String id, RoadsEnv env, Road road, 
			double initialPos, 
			double acc, 
			double dec,
			double vmax,
					int dt) {
		super(id);
		this.acceleration = acc;
		this.deceleration = dec;
		this.maxSpeed = vmax;
		this.dt = dt;
		env.registerNewCar(this, road, initialPos);
	}

	/**
	 * 
	 * Basic behaviour of a car agent structured into a sense/decide/act structure 
	 * 
	 */
	public void step() {

		/* sense */

		AbstractEnvironment env = this.getEnv();		
		currentPercept = (CarPercept) env.getCurrentPercepts(getIdAgent());

		/* decide */
		selectedAction = Optional.empty();
		
		decide(dt);
		
		/* act */

		if (selectedAction.isPresent()) {
			env.doAction(getIdAgent(), selectedAction.get());
		}
	}
	
	/**
	 * 
	 * Base method to define the behaviour strategy of the car
	 * 
	 * @param dt
	 */
	protected abstract void decide(int dt);
	
	public double getCurrentSpeed() {
		return currentSpeed;
	}
	
	protected void log(String msg) {
		System.out.println("[CAR " + this.getIdAgent() + "] " + msg);
	}

	
}
