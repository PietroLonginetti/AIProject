package com.company;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Main {

    public static void main(String[] args) throws Exception {
        NaiveBayes nb = new NaiveBayes();

        /* Naive Bayes per E.Coli con EC_GenProt top4 attributi */
        DataSource source1 = new DataSource("EC_GenProt dataset.csv");
        Instances dataGenProt = source1.getDataSet();
        dataGenProt.setClassIndex(1);   //1 è l'indice dell'attributo Essential
        DataSource results1 = new DataSource("ResultsGenProt.csv");
        Instances res1 = results1.getDataSet();

        int threshold_50 = dataGenProt.numInstances()*50/100;
        int threshold_30 = dataGenProt.numInstances()*30/100;
        int threshold_15 = dataGenProt.numInstances()*15/100;
        int threshold_5 = dataGenProt.numInstances()*5/100;
        int threshold_1 = dataGenProt.numInstances()/100;

        TestDataSet tds1 = new TestDataSet(nb,dataGenProt,res1);
        double ppv_dataGenProt = tds1.getppv(dataGenProt.numInstances());
        double ppv_dataGenProt50 = tds1.getppv(threshold_50);
        double ppv_dataGenProt30 = tds1.getppv(threshold_30);
        double ppv_dataGenProt15 = tds1.getppv(threshold_15);
        double ppv_dataGenProt5 = tds1.getppv(threshold_5);
        double ppv_dataGenProt1 = tds1.getppv(threshold_1);



        /* Naive Bayes per S.Cerevisiae con sc_GenProtNo top9 attributi*/
        DataSource source2 = new DataSource("EC_GenProtNo dataset.csv");
        Instances dataGenProtNo = source2.getDataSet();
        dataGenProtNo.setClassIndex(1);   //1 è l'indice dell'attributo Essential
        DataSource results2 = new DataSource("ResultsGenProtNo.csv");
        Instances res2 = results2.getDataSet();

        TestDataSet tds2 = new TestDataSet(nb,dataGenProtNo,res2);
        double ppv_dataGenProtNo = tds2.getppv(dataGenProtNo.numInstances());
        double ppv_dataGenProtNo50 = tds2.getppv(threshold_50);
        double ppv_dataGenProtNo30 = tds2.getppv(threshold_30);
        double ppv_dataGenProtNo15 = tds2.getppv(threshold_15);
        double ppv_dataGenProtNo5 = tds2.getppv(threshold_5);
        double ppv_dataGenProtNo1 = tds2.getppv(threshold_1);



        /* Rappresentazione grafica dei dati ottenuti */
        CategoryChart chart = new CategoryChartBuilder().title("E.Coli").width(800).height(600).yAxisTitle("PPV(%)").xAxisTitle("Top % of predictions").build();

        double[] xSC = {1,5,15,30,50,100};
        double[] ySC = {ppv_dataGenProt1*100,ppv_dataGenProt5*100,ppv_dataGenProt15*100,ppv_dataGenProt30*100,ppv_dataGenProt50*100,ppv_dataGenProt*100};

        double[] xSC_no = {1,5,15,30,40,100};
        double[] ySC_no = {ppv_dataGenProtNo1*100,ppv_dataGenProtNo5*100,ppv_dataGenProtNo15*100,ppv_dataGenProtNo30*100,ppv_dataGenProtNo50*100,ppv_dataGenProtNo*100};

        chart.addSeries("EC_GenProt",xSC,ySC);
        chart.addSeries("EC_GenProt_No",xSC_no,ySC_no);

        new SwingWrapper(chart).displayChart();
    }
}
