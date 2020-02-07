package com.company;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.NumericToNominal;

import java.util.*;

class TestDataSet {
    private NaiveBayes nb;
    public Evaluation eva;
    private Instances data;
    private List<NominalScore> ns = new ArrayList<NominalScore>();

    public TestDataSet(NaiveBayes nb, Instances dataAll, Instances distribution) throws Exception{
        this.nb = nb;
        this.data = dataAll;

        NumericToNominal ntm = new NumericToNominal();  //Converto in nominali gli attributi numerici così da poter essere trattati da N.Bayes
        ntm.setInputFormat(dataAll);
        data = ntm.useFilter(dataAll,ntm);

        nb.buildClassifier(data);
        eva = new Evaluation(data);

        for (int i = 0; i < data.numInstances(); i++) {
            ns.add(new NominalScore());
            ns.get(i).id = data.get(i).stringValue(0);
            ns.get(i).score = distribution.get(i).value(distribution.attribute("probability of being essential"));   //Lo score che considero è la probabilità di appartenere alla classe Essential=Y

        }
        Collections.sort(ns, new Comparator<NominalScore>() {           // li ordino per score
            @Override
            public int compare(NominalScore o1, NominalScore o2) {
                if (o1.score > o2.score) return -1;
                if (o1.score < o2.score) return 1;
                return 0;
            }
        });
    }

    double getppv(int threshold) throws Exception {
        if(threshold == data.numInstances()){
            eva.crossValidateModel(nb, data, 10, new Random(1));
            return eva.precision(0);      //PPV per la predizione di essenzialità Y (index 0 = y)
        }
        else {
            int i = threshold +1;
            while(i<ns.size()) {
                boolean removed = false;
                int j = 0;
                while(!removed && j< data.numInstances()){
                    if(data.get(j).stringValue(0).equals(ns.get(i).id)) {
                        data.remove(j);
                        removed = true;
                    }
                    j++;
                }
                ns.remove(i);
            }                                    //Il dataset ora contiene le instanze migliori
            nb.buildClassifier(data);
            eva = new Evaluation(data);
            eva.crossValidateModel(nb, data, 10, new Random(1));
            return eva.precision(0);
        }
    }

}