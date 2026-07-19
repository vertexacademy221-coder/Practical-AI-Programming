/*
 * @File    : Neuron.java
 * @Author  : Yossep BINYOUM
 * @Date    : 07/2026
 * @Brief   : Implentation d'une classe Neuron. Elle possede 
 *          des variables d'instance (weights et bias comme mentionne dans le cours),
 *          une sortie output.
 *          Des Methodes publiques :
 *              * Getter :
 *                  * getWeights () : Array[]
 *                  * getBias() : double
 *              * Setter :
 *                  * setWeights(Array[] weights),
 *                  * setBias(double b)
 *              * feed(Array[] inputs) : int 
 *              
*/

package com.vertexacademy.neuralnetwork;

/*
    * Ici nous parlons de Neuron et pas de perceptron car
    * dans la suite du cours cette classe sera etendue et
    * redeclaree comme etant abstraite afin de creer nos
    * differents types de reseaux de neurones.
    *
*/

import java.util.Arrays;
import java.util.Random;


public class Neuron {

    /* ==== INSTANCE VARIABLES === */

    private double[] weights;
    private double bias;
    private double output;


    /*
    *   Constructor pour initialiser un neurone avec un nombre spécifique d'entrées.
    *   Les poids et le biais sont souvent initialisés de manière aléatoire.
    */

    public Neuron(int nbInputs) 
    {
        this.weights = new double[nbInputs];
        Random random = new Random();

        for (int i = 0; i < nbInputs; i++)
        {
            this.weights[i] = random.nextDouble() * 2 - 1;
        }
        this.bias = random.nextDouble() * 2 - 1;
    }

    public int feed(double[] inputs)
    {
        if (inputs.length != weights.length)
            throw new IllegalArgumentException("Le nombre d'entrées ne correspond pas au nombre de poids du neurone.");
        
        double sum = 0.0;

        for (int i = 0; i < inputs.length; i++)
        {
            sum += (weights[i] * inputs[i]);
        }
        
        this.output = sum + bias;
        return (this.output > 0) ? 1 : 0;
    }

    /*[Getter]*/
    
    public double[] getWeights() {return weights;}
    public double getBias() {return bias;}

    /*[Setter]*/

    public void setWeights(double[] weights) { this.weights = weights;}
    public void setBias(double bias) { this.bias = bias;}

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {

        double[] data = {5.0, 2.0};
        Neuron n1 = new Neuron(2);

        System.out.println("neuron bias = " + n1.getBias());
        System.out.println("neuron weights : [" + n1.getWeights()[0]
                        + ", " + n1.getWeights()[1] + "]");
        System.out.println("neuron fires " + n1.feed(data));
    }
}
