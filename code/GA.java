import java.util.Random;

public class GA{

	private Individual[] population;
	private final String TARGET = "HELLO WORLD";
	private char[] alphabet = new char[26];
	private Random generator = new Random(System.currentTimeMillis());

	private int popSize;  //population size
	private double mutationRate;  //Probability of mutation to happen 


	// initial the population
	public GA (int popSize, double mutationRate){
		this.popSize = popSize;
		this.mutationRate = mutationRate;
		this.population = new Individual[popSize];
		this.alphabetMaker();   // make alphabet array
		this.popGenerator(); 	// initial population
	}

	//make an array of charecter with all alphabet
	public void alphabetMaker(){
		// make an array with all the alphabet
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
	}

	// make an array of population (String of length (target))
	public void popGenerator(){
		for (int i = 0; i < popSize; i++) {
			char[] tempChromosome = new char[TARGET.length()];
			for (int j = 0; j < TARGET.length(); j++) {
				tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)];
			}
			this.population[i] = new Individual(tempChromosome);
		}
	}

	public void printPop(){
		for (int i = 0; i < population.length; i++) {
			System.out.println(population[i].genoToPhenotype());
		}
	}

	// assign a value to each phenotype according :
		// if the charecter exist in the target  +1
		//else if the charecter is same with the same index +100
		// make an array of fitness value with the same indexing 
	public void fittness() {
		for (int i = 0; i < population.length; i++) {

			String phenotype = population[i].genoToPhenotype(); 
			String tempTarget = TARGET; 
			int fitnessValue = 0;
			for (int j = 0; j < phenotype.length(); j++) {

				String gene = String.valueOf(phenotype.charAt(j));
				if(tempTarget.contains(gene)){
					fitnessValue += 1;
					if (tempTarget.indexOf(gene) == j){
						fitnessValue +=100;
					}
					tempTarget.replace(gene, "#");

				}
			}
			System.out.println(fitnessValue);
			population[i].setFitness(fitnessValue);
		}


	}


	// return total fittnes value of population
	public double totalFittnessPop(){
		double total = 0;
		for (int i = 0; i < population.length; i++) {
			total += population[i].getFitness();
		}
		return total;
	}

	

}