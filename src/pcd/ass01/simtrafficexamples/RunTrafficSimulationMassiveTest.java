package pcd.ass01.simtrafficexamples;

public class RunTrafficSimulationMassiveTest {

	public static void main(String[] args) throws InterruptedException {

		int numCars = 5000;
		int nSteps = 100;
		
		var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars);
		simulation.setup();
		
		log("Running the simulation: " + numCars + " cars, for " + nSteps + " steps ...");
		
		simulation.run(nSteps);

		long d = simulation.getSimulationDuration();
		log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");
		//Sequential
		//[ SIMULATION ] Running the simulation: 5000 cars, for 100 steps ...
		//[ SIMULATION ] Completed in 38187 ms - average time per step: 381 ms

		//Cuncurrent
		//Completed in 25155 ms - average time per step: 240 ms

	}
	
	private static void log(String msg) {
		System.out.println("[ SIMULATION ] " + msg);
	}
}
