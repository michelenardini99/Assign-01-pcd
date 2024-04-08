package pcd.ass01.simenginecunc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *   
 * Base class to define the environment of the simulation
 *   
 */
public abstract class AbstractEnvironment {

	private String id;
	private final Lock lock = new ReentrantLock();
	private final Condition allAgentsStepped = lock.newCondition();
	private final Condition environmentStepped = lock.newCondition();
	private int numAgents;
	private int agentsSteppedCount = 0;
	private boolean isLastStep = false;
	private boolean isStopped = false;

	
	protected AbstractEnvironment(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	
	/**
	 * 
	 * Called at the beginning of the simulation
	 */
	public abstract void init();
	
	/**
	 * 
	 * Called at each step of the simulation
	 * 
	 * @param dt
	 */
	public abstract void step(int dt);

	public void executeStep(int dt, boolean isLastStep) throws InterruptedException {
		lock.lock();
		try {
			while (agentsSteppedCount < numAgents) {
				allAgentsStepped.await();
			}
			step(dt);
			this.isLastStep = isLastStep;
			agentsSteppedCount = 0;
		} finally {
			lock.unlock();
		}
	}

	public void agentStepped() {
		lock.lock();
		try {
			agentsSteppedCount++;
			if (agentsSteppedCount == numAgents) {
				allAgentsStepped.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}

	public void waitForEnvironment() throws InterruptedException {
		lock.lock();
		try {
			environmentStepped.await();

		} finally {
			lock.unlock();
		}
	}

	public void signalEnvAndStepNext(){
		lock.lock();
		try{
			environmentStepped.signalAll();
		}finally {
			lock.unlock();
		}
	}


	public void setNumAgents(int numAgents){
		this.numAgents = numAgents;
	}

	public boolean isLastStep(){
		return isLastStep;
	}



	/**
	 *
	 * Called by an agent to get its percepts
	 *
	 * @param agentId - identifier of the agent
	 * @return agent percept
	 */
	public abstract Percept getCurrentPercepts(String agentId);

	/**
	 * 
	 * Called by agent to submit an action to the environment
	 * 
	 * @param agentId - identifier of the agent doing the action
	 * @param act - the action
	 */
	public abstract void doAction(String agentId, Action act);

	public boolean isStopped() {
		return this.isStopped;
	}

	public void stop(){
		isStopped = true;
	}
}
