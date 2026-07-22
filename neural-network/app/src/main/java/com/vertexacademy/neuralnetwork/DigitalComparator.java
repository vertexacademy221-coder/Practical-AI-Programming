package com.vertexacademy.neuralnetwork;


class DigitalComparator
{
	public DigitalComparator(){}
	/*
	* Return an array of three elements [aLb, aEb, aGb] avec :
    *       * aLb => A less than B
    *       * aEb => A equal to B.
    *       * aGb => A greater than B.
	*/

	public double[] compute(double[] inputs) {

		if (inputs.length <= 1) 
			throw new IllegalArgumentException("Number of inputs should be 2");

		var a = inputs[0];
		var b = inputs[1];

		// Nous créons nos trois portes logiques sous forme de perceptrons 

		var and = new Neuron(inputs.length);
		and.setWeights(new double[]{1, 1});
		and.setBias(-1.5);

		var not = new Neuron(inputs.length);
		not.setWeights(new double[]{-1});
		not.setBias(0.5);	

		var nor = new Neuron(inputs.length);
		nor.setWeights(new double[]{-1, -1});
		nor.setBias(0.5);

		// et les relions entre elles à l'aide des variables notA, notB, 
		// aGb (a est supérieur à b), aLb (a est inférieur à b) et aEb (a est égal à b).
		
		var notA = not.feed(new double[]{a});
		var notB = not.feed(new double[]{b});

		var aLb = and.feed(new double[]{notA, b});
		var aGb = and.feed(new double[]{a, notB});
		var aEb = nor.feed(new double[]{aGb, aLb});

		// La méthode renvoie le résultat de l'évaluation du circuit sous 
		// la forme d'un tableau
		return new double[]{aGb, aEb, aLb};
	}
}