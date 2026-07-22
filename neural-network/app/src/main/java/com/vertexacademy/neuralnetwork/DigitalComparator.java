package com.vertexacademy.neuralnetwork;


class DigitalComparator()
{
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

		var and = new Neuron(inputs.length);
		and.setWeights(new double[]{1, 1});
		and.setBias(-1.5);


		var not = new Neuron(inputs.length);
		not.setWeights(new double[]{-1});
		not.setBias(0.5);
	}
}