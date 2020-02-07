package com.company;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.converters.ConverterUtils.DataSource;

public class Main {

    public static void main(String[] args) throws Exception {
        NaiveBayes nb = new NaiveBayes();
        DataSource source = new DataSource("SC_All dataset.csv");

        /* Naive Bayes per S.Cerevisiae per singoli attributi */
        //Acivo Glutammico
        TestFeature glu = new TestFeature(nb,source.getDataSet(),"GLU");
        double ppv_GLU = glu.ppv();
        // Lisina
        TestFeature lys = new TestFeature(nb,source.getDataSet(),"LYS");
        double ppv_LYS = lys.ppv();
        // Grado DIP
        TestFeature deg = new TestFeature(nb,source.getDataSet(),"DIP_degree");
        double ppv_DEG = deg.ppv();
        // Ritenzione Filetica
        TestFeature pr = new TestFeature(nb,source.getDataSet(),"phyletic_retention");
        double ppv_PhylRet = pr.ppv();
        // Glicina
        TestFeature gly = new TestFeature(nb,source.getDataSet(),"GLY");
        double ppv_GLY = gly.ppv();
        // Dimensione del frammento
        TestFeature orf = new TestFeature(nb,source.getDataSet(),"orf_size");
        double ppv_ORF = orf.ppv();
        // Indice di adattamento del codone
        TestFeature cai = new TestFeature(nb,source.getDataSet(),"CAI");
        double ppv_CAI = cai.ppv();
        // Transcriprion Factor Binding Sites
        TestFeature tfbs = new TestFeature(nb,source.getDataSet(),"promoter_count(Harbison_et_al)");
        double ppv_TFBS = tfbs.ppv();
        // Upstream Size
        TestFeature us = new TestFeature(nb,source.getDataSet(),"upstream_size");
        double ppv_US = us.ppv();


        /* Rappresentazione grafica dei dati ottenuti */
        CategoryChart chart = new CategoryChartBuilder().title("S. cerevisiae").width(800).height(600).yAxisTitle("PPV(%)").xAxisTitle("Top % of predictions").build();

        double[] xPhyl = {100};
        double[] yPhyl = {ppv_PhylRet*100};

        double[] xGlu = {100};
        double[] yGlu = {ppv_GLU*100};

        double[] xLys = {100};
        double[] yLys = {ppv_LYS*100};

        double[] xGly = {100};
        double[] yGly = {ppv_GLY*100};

        double[] xOrf_size = {100};
        double[] yOrf_size = {ppv_ORF*100};

        double[] xCai = {100};
        double[] yCai = {ppv_CAI*100};

        double[] xTfbs = {100};
        double[] yTfbs = {ppv_TFBS*100};

        double[] xUpSize = {100};
        double[] yUpSize = {ppv_US*100};

        double[] xDeg = {100};
        double[] yDeg = {ppv_DEG*100};

        chart.addSeries("Phyletic Retention",xPhyl,yPhyl);
        chart.addSeries("Degree",xDeg,yDeg);
        chart.addSeries("Glutamic Acid",xGlu,yGlu);
        chart.addSeries("Lysine",xLys,yLys);
        chart.addSeries("Glycine",xGly,yGly);
        chart.addSeries("ORF Size",xOrf_size,yOrf_size);
        chart.addSeries("CAI",xCai,yCai);
        chart.addSeries("TFBS count",xTfbs,yTfbs);
        chart.addSeries("Upstream Size",xUpSize,yUpSize);

        new SwingWrapper(chart).displayChart();

    }
}
