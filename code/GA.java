import java.util.ArrayList;
import java.util.Random;

public class GA {

	private Individual[] population;
	private final String TARGET = "HELLO WORLD";
	private char[] alphabet = new char[26];
	private Random generator = new Random(System.currentTimeMillis());

	private int popSize; // population size
	private double mutationRate; // Probability of mutation to happen

	// initial the population
	public GA(int popSize, double mutationRate) {
		this.popSize = popSize;
		this.mutationRate = mutationRate;
		this.population = new Individual[popSize];
		this.alphabetMaker(); // make alphabet array
		this.popGenerator(); // initial population
	}

	// make an array of charecter with all alphabet
	public void alphabetMaker() {
		// make an array with all the alphabet
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
	}

	// make an array of population (String of length (target))
	public void popGenerator() {
		for (int i = 0; i < popSize; i++) {
			char[] tempChromosome = new char[TARGET.length()];
			for (int j = 0; j < TARGET.length(); j++) {
				tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)];
			}
			this.population[i] = new Individual(tempChromosome);
		}
	}

	public void printPop() {
		for (int i = 0; i < population.length; i++) {
			System.out.println(population[i].genoToPhenotype());
		}
	}

	// assign a value to each phenotype according :
	// if the charecter exist in the target +1
	// else if the charecter is same with the same index +100
	// make an array of fitness value with the same indexing
	public void setFittness() {
		for (int i = 0; i < population.length; i++) {

			String phenotype = population[i].genoToPhenotype();
			String tempTarget = TARGET;
			int fitnessValue = 0;
			for (int j = 0; j < phenotype.length(); j++) {

				String gene = String.valueOf(phenotype.charAt(j));
				if (tempTarget.contains(gene)) {
					fitnessValue += 1;
					if (tempTarget.indexOf(gene) == j) {
						fitnessValue += 100;
					}
					tempTarget.replace(gene, "#");

				}
			}
			System.out.println(fitnessValue);
			population[i].setFitness(fitnessValue);
		}

	}

	// return total fittnes value of population
	public double totalFittnessPop() {
		double total = 0;
		for (int i = 0; i < population.length; i++) {
			total += population[i].getFitness();
		}
		return total;
	}

	// pick two phenotype randomly base on their distributions

	public ArrayList<Individual> naturalSelection() {
		ArrayList<Integer> probDistributionPop = new ArrayList<Integer>();
		Individual firstSelected;
		Individual secondSelected;
		Random rndOne = new Random();
		Random rndTwo = new Random();
		ArrayList<Individual> selectedParents = new ArrayList<Individual>();

		// make and array of phenotype based in their distribution
		for (int i = 0; i < this.population.length; i++) {
			for (int j = 0; j < population[i].getFitness(); j++) {
				probDistributionPop.add(i);
			}
		}

		System.out.println(probDistributionPop.toString());
		// select two Individual randomly from probDistributionPop
		int rndOneInt = rndOne.nextInt(probDistributionPop.size());
		int rndTwoInt = rndTwo.nextInt(probDistributionPop.size());

		int indexFistSelected = probDistributionPop.get(rndOneInt);
		int indexSecondSelected = probDistributionPop.get(rndTwoInt);

		firstSelected = population[indexFistSelected];
		secondSelected = population[indexSecondSelected];

		// this while is for avoiding having the same parent
		while (indexFistSelected == indexSecondSelected) {
			int tempRnd = rndTwo.nextInt(probDistributionPop.size());
			indexSecondSelected = probDistributionPop.get(tempRnd);
			secondSelected = population[indexSecondSelected];
		}

		selectedParents.add(firstSelected);
		selectedParents.add(secondSelected);

		System.out.println(selectedParents.get(0).genoToPhenotype());
		System.out.println(selectedParents.get(1).genoToPhenotype());
		return selectedParents;

	}

}