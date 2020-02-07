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

        /* Naive Bayes per S.Cerevisiae con sc_All attributi */
        DataSource source1 = new DataSource("SC_All dataset.csv");
        Instances dataAll = source1.getDataSet();
        dataAll.setClassIndex(1); //1 è l'indice dell'attributo Essential
        DataSource results1 = new DataSource("ResultsAll.csv");
        Instances res1 = results1.getDataSet();

        int threshold_50 = dataAll.numInstances()*50/100;
        int threshold_30 = dataAll.numInstances()*30/100;
        int threshold_15 = dataAll.numInstances()*15/100;
        int threshold_5 = dataAll.numInstances()*5/100;
        int threshold_1 = dataAll.numInstances()/100;

        TestDataSet tds1 = new TestDataSet(nb,dataAll,res1);
        double ppv_dataAll = tds1.getppv(dataAll.numInstances());
        double ppv_dataAll50 = tds1.getppv(threshold_50);
        double ppv_dataAll30 = tds1.getppv(threshold_30);
        double ppv_dataAll15 = tds1.getppv(threshold_15);
        double ppv_dataAll5 = tds1.getppv(threshold_5);
        double ppv_dataAll1 = tds1.getppv(threshold_1);


        //Naive Bayes per S.Cerevisiae con sc_GenProt attributi
        DataSource source2 = new DataSource("SC_GenProt dataset.csv");
        Instances dataGenProt = source2.getDataSet();
        dataGenProt.setClassIndex(1);   //1 è l'indice dell'attributo Essential
        DataSource results2 = new DataSource("ResultsGenProt.csv");
        Instances res2 = results2.getDataSet();

        TestDataSet tds2 = new TestDataSet(nb,dataGenProt,res2);
        double ppv_dataGenProt = tds2.getppv(dataAll.numInstances());
        double ppv_dataGenProt50 = tds2.getppv(threshold_50);
        double ppv_dataGenProt30 = tds2.getppv(threshold_30);
        double ppv_dataGenProt15 = tds2.getppv(threshold_15);
        double ppv_dataGenProt5 = tds2.getppv(threshold_5);
        double ppv_dataGenProt1= tds2.getppv(threshold_1);


        //Naive Bayes per S.Cerevisiae con sc_GenProt_No attributi
        DataSource source3 = new DataSource("SC_GenProt_No dataset.csv");
        Instances dataGenProtNo = source3.getDataSet();
        dataGenProtNo.setClassIndex(1);   //1 è l'indice dell'attributo Essential
        DataSource results3 = new DataSource("ResultsGenProtNo.csv");
        Instances res3 = results3.getDataSet();

        TestDataSet tds3 = new TestDataSet(nb,dataGenProtNo,res3);
        double ppv_dataGenProtNo = tds3.getppv(dataAll.numInstances());
        double ppv_dataGenProtNo50 = tds3.getppv(threshold_50);
        double ppv_dataGenProtNo30 = tds3.getppv(threshold_30);
        double ppv_dataGenProtNo15 = tds3.getppv(threshold_15);
        double ppv_dataGenProtNo5 = tds3.getppv(threshold_5);
        double ppv_dataGenProtNo1= tds3.getppv(threshold_1);



        //Rappresentazione grafica dei dati ottenuti
        CategoryChart chart = new CategoryChartBuilder().title("S. cerevisiae").width(800).height(600).yAxisTitle("PPV(%)").xAxisTitle("Top % of predictions").build();

        double[] xall = {1,5,15,30,50,100};
        double[] yall = {ppv_dataAll1*100,ppv_dataAll5*100,ppv_dataAll15*100,ppv_dataAll30*100,ppv_dataAll50*100,ppv_dataAll*100};

        double[] xSC = {1,5,15,30,50,100};
        double[] ySC = {ppv_dataGenProt1*100,ppv_dataGenProt5*100,ppv_dataGenProt15*100,ppv_dataGenProt30*100,ppv_dataGenProt50*100,ppv_dataGenProt*100};

        double[] xSC_no = {1,5,15,30,50,100};
        double[] ySC_no = {ppv_dataGenProtNo1*100,ppv_dataGenProtNo5*100,ppv_dataGenProtNo15*100,ppv_dataGenProtNo30*100,ppv_dataGenProtNo50*100,ppv_dataGenProtNo*100};


        chart.addSeries("SC_all",xall,yall);
        chart.addSeries("SC_GenProt",xSC,ySC);
        chart.addSeries("SC_GenProt_No",xSC_no,ySC_no);

        new SwingWrapper(chart).displayChart();
    }
}
