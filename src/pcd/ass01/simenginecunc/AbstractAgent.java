package pcd.ass01.simenginecunc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * Base  class for defining types of agents taking part to the simulation
 * 
 */
public abstract class AbstractAgent extends Thread{
	
	private String myId;
	private AbstractEnvironment env;
	private final Lock lock = new ReentrantLock();

	/**
	 * Each agent has an identifier
	 * 
	 * @param idAgent
	 */
	protected AbstractAgent(String idAgent) {
		this.myId = idAgent;
	}
	
	/**
	 * This method is called at the beginning of the simulation
	 * 
	 * @param env
	 */
	public void init(AbstractEnvironment env) {
		this.env = env;
	}

	abstract public void sense();
	abstract public void decide();
	abstract public void act();

	public void run(){
		while (!isInterrupted()) {
			try {
				sense();
				decide();
				synchronized (env){
					act();
				}
				env.agentStepped();
				env.waitForEnvironment();
				if(env.isLastStep()){
					interrupt();
				}
			} catch (InterruptedException e) {
				interrupt();
			}
		}
	}




	public String getIdAgent() {
		return myId;
	}
	
	protected AbstractEnvironment getEnv() {
		return this.env;
	}
}
