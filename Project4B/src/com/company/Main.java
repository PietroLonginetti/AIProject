package com.company;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.converters.ConverterUtils.DataSource;

public class Main {

    public static void main(String[] args) throws Exception {
        NaiveBayes nb = new NaiveBayes();
        DataSource source = new DataSource("EC_All dataset.csv");

        /* Naive Bayes per E.Coli per singoli attributi */
        // Indice di adattamento del codone
        TestFeature cai = new TestFeature(nb,source.getDataSet(),"CAI");
        double ppv_CAI = cai.ppv();
        // Numero di Paraloghi
        TestFeature pc = new TestFeature(nb,source.getDataSet(),"paralog_count");
        double ppv_PC = pc.ppv();
        // Dimensione del frammento
        TestFeature orf = new TestFeature(nb,source.getDataSet(),"orf_size");
        double ppv_OS = orf.ppv();
        // Serina
        TestFeature ser = new TestFeature(nb,source.getDataSet(),"Ser");
        double ppv_SER = ser.ppv();
        // Ritenzione Filetica
        TestFeature pr = new TestFeature(nb,source.getDataSet(),"phyletic_retention");
        double ppv_PhylRet = pr.ppv();

        /* Rappresentazione grafica dei dati ottenuti */
        CategoryChart chart = new CategoryChartBuilder().title("E.Coli").width(800).height(600).yAxisTitle("PPV(%)").xAxisTitle("Top % of predictions").build();

        double[] xCAI = {100};
        double[] yCAI = {ppv_CAI*100};

        double[] xParalogs = {100};
        double[] yParalogs = {ppv_PC*100};

        double[] xOrfSize = {100};
        double[] yOrfSize = {ppv_OS*100};

        double[] xSer = {100};
        double[] ySer = {ppv_SER*100};

        double[] xPhylRet = {100};
        double[] yPhylRet ={ppv_PhylRet*100};

        chart.addSeries("Phyletic Retention",xPhylRet,yPhylRet);
        chart.addSeries("CAI",xCAI,yCAI);
        chart.addSeries("Paralogs",xParalogs,yParalogs);
        chart.addSeries("ORF Size",xOrfSize,yOrfSize);
        chart.addSeries("Serine",xSer,ySer);

        new SwingWrapper(chart).displayChart();
    }
}
