package com.company;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.NumericToNominal;

import java.util.Random;

class TestFeature {
    private NaiveBayes nb;
    private Instances data;
    private String attName;

    TestFeature(NaiveBayes nb, Instances dataAll, String attName) throws Exception {
        this.nb = nb;
        this.data = dataAll;
        this.attName = attName;

        int j = 0;
        while (j < data.numAttributes()){
            if(!data.attribute(j).equals(data.attribute(this.attName)) && !data.attribute(j).equals(data.attribute("orf_id")) && !data.attribute(j).equals(data.attribute("Essential")))
                data.deleteAttributeAt(j);
            else j++;
        }
        this.data.setClassIndex(1);     //1 indice dell'attributo Essential

        if(data.attribute(attName).type() == 0){
            NumericToNominal ntm = new NumericToNominal();          //Converto in nominale l'attributo numerico
            ntm.setInputFormat(data);
            this.data = ntm.useFilter(data,ntm);
        }
    }

    double ppv() throws Exception {
        nb.buildClassifier(data);
        Evaluation eva = new Evaluation(data);
        eva.crossValidateModel(nb,data,10,new Random(1));
        return eva.precision(0);
    }

}
