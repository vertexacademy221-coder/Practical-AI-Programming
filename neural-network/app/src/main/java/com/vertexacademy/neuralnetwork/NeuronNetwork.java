package com.vertexacademy.neuralnetwork;


import java.util.ArrayList;
import java.util.List;

public class NeuronNetwork {

    //
    //  Instances variables
    //

    // Layers un tableau dynamique de couche de neurones chainees.
    private List<NeuronLayer> layers = new ArrayList<>();

    // variable d'instance errors et precisions seront utiles pour suivre 
    // l'évolution des erreurs au cours de la phase d'apprentissage
    private double[] errors;
    private double[] precisions;


    //
    //  Public API
    //
    public NeuronNetwork(){}

    public void addLayer(NeuronLayer newLayer) {

        if (newLayer == null) {
            throw new IllegalArgumentException("newLayer ne doit pas être nul");
        }

        if (newLayer.getPreviousLayer() != null || newLayer.getNextLayer() != null) {
            throw new IllegalArgumentException("newLayer ne doit pas deja etre connectee");
        }

        // Si le reseau est vide ajoute tout simplement la couche sans creer de 
        // connexion
        if (layers.isEmpty()) {
            layers.add(newLayer);
            return;
        }

        // On cree la connexion entre les differentes couches
        NeuronLayer previousLayer = layers.getLast();
        newLayer.setPreviousLayer(previousLayer);
        previousLayer.setNextLayer(newLayer);
        layers.add(newLayer);
    }

    // Feeding a neural network involves simply feeding the first hidden layer
    public int[] feed(double[] inputs) {

        NeuronLayer firstLayer = this.layers.getFirst();

        if(firstLayer == null) {
            throw new IllegalStateException("Impossible de feed une couche nulle");
        }
        
        return firstLayer.feed(inputs);
    }

    // Retourne le nombre de sortie du reseau neuronal
    public int getNumberOfOutputs() {

        if (layers.isEmpty()) {
            throw new IllegalStateException("Le reseau de neurones ne contient aucune couche.");
        }

        NeuronLayer lastLayer = layers.getLast();

        if (lastLayer.isOutputLayer()) {
            return lastLayer.getNeurons().length;
        }
        else {
            return 0;
        }
    }

    //
    // Setters
    //

    // Definit le taux d'apprentissage pour toutes les couches du reseau neuronal
    public void setLearningRate(double learningRate) {

        if (layers.isEmpty()) 
            throw new IllegalStateException("Impossible d'ajuster le taux d'apprentissage" + 
            "d'un reseau neuronal vide.");
        
        for (NeuronLayer layer : layers) {
            layer.setLearningRate(learningRate);
        }
    }
}