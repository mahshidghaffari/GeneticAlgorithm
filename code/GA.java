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


	public void alphabetMaker(){
		// make an array with all the alphabet
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
	}

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

	public void fittness() {
		for (int i = 0; i < population.length; i++) {

			String phenotype = population[i].genoToPhenotype(); //get the phenotype 
			String tempTarget = TARGET; //clone of target is made for fitness evaluation
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
			if(fitnessValue >200){
				System.out.println(population[i].genoToPhenotype());
			}
			population[i].setFitness(fitnessValue); //cast double
		}
	}
	
	public double totalFittnessPop(){
		double total = 0;
		for (int i = 0; i < population.length; i++) {
			total += population[i].getFitness();
		}
		return total;
	}


}