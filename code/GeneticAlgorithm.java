import java.util.*;
/**
 * 
 */
public class GeneticAlgorithm {

	Individual[] population;
	public final String TARGET = "HELLO WORLD";
	public char[] alphabet = new char[27];
	public Random generator = new Random(System.currentTimeMillis());

	public int popSize;
	public double mutationRate;

	/**
	 * In the constructor we initialize the population size, mutation rate and the initial population is set initialized with random charecters (code for this was already given)
	 * @param popSize Size of the population - does not change over time
	 * @param mutationRate Probability of mutation to happen 
	 */
	GeneticAlgorithm(int popSize, double mutationRate) {
		this.popSize = popSize;
		this.mutationRate = mutationRate;
		this.population = new Individual[popSize];
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		alphabet[26] = ' ';
		for (int i = 0; i < popSize; i++) {
			char[] tempChromosome = new char[TARGET.length()];
			for (int j = 0; j < TARGET.length(); j++) {
				tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)];
			}
			this.population[i] = new Individual(tempChromosome);
		}
	}

	/**
	 * Method prints current phenotypes of individuals from population
	 */
	public void displayPopulation() {
		for (int i = 0; i < population.length; i++) {
			System.out.println(population[i].genoToPhenotype());
		}
	}

	/**
	 * Method sets the individual fitness value, which is based on matching of their genes' 
	 * location and repetition with the TARGET
	 */
	public void fitnessFunction() {
		for (int i = 0; i < population.length; i++) {
			String phenotype = population[i].genoToPhenotype(); //get the phenotype 
			String targetClone = TARGET; //clone of target is made for fitness evaluation
			int fitness = 0;
			for (int j = 0; j < TARGET.length(); j++) {

				String gene = String.valueOf(phenotype.charAt(j));
				if (TARGET.contains(gene) || targetClone.contains(gene)) {

					if (targetClone.contains(gene)) { //If there is a charecter from TARGET
						fitness+=1;
						targetClone.replaceFirst(gene, "*");
					}

					if (TARGET.contains(gene) && TARGET.charAt(j) == gene.charAt(0)) {
						fitness+=14; 
					}
				}

			}
		
			population[i].setFitness(fitness); //cast double
		}
	}

	public double getFitnessSum() {
		double fitnessSum = 0;
		for (int i = 0; i < population.length; i++) {
			fitnessSum += population[i].getFitness();
		}
		return fitnessSum;
	}


	// in faghat miad ye array az individuala minivise hamon populatione faghat be
	// in tavaot ke har loghat be andaze emtiazesh to on array sakhte mishe
	public ArrayList<Individual> createrouletteWheelList(){
		ArrayList<Individual> rouletteWheelList = new ArrayList<>();
		for (int i = 0; i < population.length; i++) {
			for (int j = 0; j < population[i].getFitness(); j++) {
				rouletteWheelList.add(population[i].clone());
			}
		}
		return rouletteWheelList;
	}

	public void crossOverIndividuals(ArrayList<Individual> rouletteWheelList) {
		ArrayList<Individual> selectionList = new ArrayList<>();
		ArrayList<String> bookMarkPairs = new ArrayList<>();
		int selectId1;
		int selectId2;
		boolean added;
		boolean coincided;
		String selectedIds = "";
		for (int i = 0; i < popSize; i++) {
			added = false;
			while (!added) {
				
				selectId1 = generator.nextInt(rouletteWheelList.size());
				selectId2 = generator.nextInt(rouletteWheelList.size());
				int[] selectedIdsIntArr = {selectId1, selectId2};
				Arrays.sort(selectedIdsIntArr);
				selectedIds = selectedIdsIntArr.toString();

				if (!bookMarkPairs.contains(selectedIds)) {
					coincided = rouletteWheelList.get(selectId1).genoToPhenotype().equals(rouletteWheelList.get(selectId2).genoToPhenotype());
					if (!coincided) {
						selectionList.add(rouletteWheelList.get(selectId1).clone());
						selectionList.add(rouletteWheelList.get(selectId2).clone());
						added = true;
					}
					bookMarkPairs.add(selectedIds);
				}
				
			}
		}


		ArrayList<Individual> newPopulation = new ArrayList<>();
		for (int i = 0; i < selectionList.size() - 1; i+=3) {
			char[] parent1;
			char[] parent2;
			char[] child1 = new char[TARGET.length()];
			char[] child2 = new char[TARGET.length()];
			parent1 = selectionList.get(i).getChromosome();
			parent2 = selectionList.get(i+1).getChromosome();
			
			int crossPoint1 = generator.nextInt(7);
			for (int j = 0; j < TARGET.length(); j++) {
				
				if (j < crossPoint1){
					child1[j] = parent1[j];
					child2[j] = parent2[j];
				}
				else {
					child1[j] = parent2[j];
					child2[j] = parent1[j];
				}
			}
			newPopulation.add(new Individual(child1));
			newPopulation.add(new Individual(child2));
		}
 
		for (int i = 0; i < population.length; i++) {
			population[i] = newPopulation.get(i);
		}
	}

	public void diagonalCrossOver(ArrayList<Individual> rouletteWheelList) {
		ArrayList<Individual> selectionList = new ArrayList<>();
		ArrayList<String> bookMarkPairs = new ArrayList<>();
			int selectId1;
			int selectId2;
			int selectId3;
			boolean added;
			boolean coincided = true;
			String selectedIds = "";
			for (int i = 0; i < popSize; i++) {
				added = false;
				while (!added) {
					selectId1 = generator.nextInt(rouletteWheelList.size());
					selectId2 = generator.nextInt(rouletteWheelList.size());
					selectId3 = generator.nextInt(rouletteWheelList.size());
					int[] selectedIdsIntArr = {selectId1, selectId2, selectId3};
					Arrays.sort(selectedIdsIntArr);
					selectedIds = selectedIdsIntArr.toString();
					if (!bookMarkPairs.contains(selectedIds)) {
						coincided = rouletteWheelList.get(selectId1).genoToPhenotype().equals(rouletteWheelList.get(selectId2).genoToPhenotype())
						|| rouletteWheelList.get(selectId2).genoToPhenotype().equals(rouletteWheelList.get(selectId3).genoToPhenotype())
						|| rouletteWheelList.get(selectId1).genoToPhenotype().equals(rouletteWheelList.get(selectId3).genoToPhenotype());
						if (!coincided) {
							selectionList.add(rouletteWheelList.get(selectId1).clone());
							selectionList.add(rouletteWheelList.get(selectId2).clone());
							selectionList.add(rouletteWheelList.get(selectId3).clone());
							added = true;
						}
						bookMarkPairs.add(selectedIds);
					}
					
				}
			}


		ArrayList<Individual> newPopulation = new ArrayList<>();
		for (int i = 0; i < selectionList.size() - 1; i+=3) {
			char[] parent1;
			char[] parent2;
			char[] parent3;
			char[] child1 = new char[TARGET.length()];
			char[] child2 = new char[TARGET.length()];
			char[] child3 = new char[TARGET.length()];
			parent1 = selectionList.get(i).getChromosome();
			parent2 = selectionList.get(i+1).getChromosome();
			parent3 = selectionList.get(i+2).getChromosome();
			int crossPoint1 = generator.nextInt(7);
			int crossPoint2 = generator.nextInt(5) + crossPoint1;
			for (int j = 0; j < TARGET.length(); j++) {
				
				if (j < crossPoint1){
					child1[j] = parent1[j];
					child2[j] = parent2[j];
					child3[j] = parent3[j];
				}
				else if (j >= crossPoint1 && j<crossPoint2){
					child1[j] = parent2[j];
					child2[j] = parent3[j];
					child3[j] = parent1[j];
				}
				else {
					child1[j] = parent2[j];
					child2[j] = parent1[j];
					child3[j] = parent2[j];
				}
			}
			newPopulation.add(new Individual(child1));
			newPopulation.add(new Individual(child2));
			newPopulation.add(new Individual(child3));
		}

		for (int i = 0; i < population.length; i++) {
			population[i] = newPopulation.get(i);
		}
	}

	
	public void ScrambleMutation(){
		Character[] scrambleSubset = new Character[5];
		int setStart;
		for (int i = 0; i < population.length ; i++){
			if (Math.random() < mutationRate) {
				setStart = generator.nextInt(TARGET.length()-6);
				char[] y = population[i].getChromosome();
				for (int j = setStart; j < setStart+5; j++){
					scrambleSubset[j-setStart] = y[j];
				}
				List<Character> subset1 = Arrays.asList(scrambleSubset);
				Collections.shuffle(subset1); 
				for (int p = setStart; p < setStart+5; p++){
					y[p] = scrambleSubset[p-setStart];
				}
				population[i].setChromosome(y);	
			}
		}
	}

	public void InversionMutation(){
		char[] inversionSubset = new char[5];
		for (int i = 0; i<population.length; i++){
			char[] y = population[i].getChromosome();
			int o = -1;
			for (int j = 6; j > 1; j--){
				inversionSubset[++o] = y[j];
			}
			for (int p= 2; p<7; p++){
			y[p] = inversionSubset[p-2];
			}
			population[i].setChromosome(y);
	
		}
	}

	public void RandomMutation() {
		int mutationId = 0;
		for (int i = 0; i < population.length; i++) {
			if (Math.random() < mutationRate*0.05) {
				mutationId = generator.nextInt(TARGET.length());
				population[i].chromosome[mutationId] = alphabet[generator.nextInt(alphabet.length)];
			}
		}
	}

	

	public boolean CheckConvergence(){
		for (int i = 0; i < population.length; i++) {
			if (population[i].genoToPhenotype().equals(TARGET)) {
				return true;
			}
		}
		return false;
	}



	public void commenceGA() {

		boolean converged = false;
		int generationCount = 0;
		
		while (!converged) {
			generationCount++;

			fitnessFunction();
			ArrayList<Individual> rouletteWheelList = createrouletteWheelList();
			// crossOverIndividuals(rouletteWheelList);
			diagonalCrossOver(rouletteWheelList);
			ScrambleMutation();
			RandomMutation();
			converged = CheckConvergence();
			
			if (converged) {
				//displayPopulation();
				System.out.println(generationCount);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for ( int i=0; i <3; i++){
			GeneticAlgorithm GATest = new GeneticAlgorithm(100, 0.0);
			GATest.commenceGA();
		}
		
	}
}