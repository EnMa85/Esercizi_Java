package com.example.demo;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;

import java.util.List;
import java.util.Map;
import java.io.IOException;


public class Grafici {
	
	
	public static void generaGraficoBarre(List<String> chiavi, List<Integer> valori, String nomeAsseX, String nomeImg, String nomeGrafico) throws IOException {
    	
        // Crea il grafico a barre
        CategoryChart chart = new CategoryChartBuilder()
                .width(600)
                .height(280)
                .title(nomeGrafico)
                .xAxisTitle(nomeAsseX)
                .yAxisTitle("Numero")
                .theme(Styler.ChartTheme.GGPlot2)
                .build();

        // Aggiunge i dati al grafico
        chart.addSeries(nomeGrafico, chiavi, valori);

        // Salva il grafico come immagine
        String pathImg = "src/main/resources/static/" + nomeImg + ".png";
        BitmapEncoder.saveBitmap(chart, pathImg, BitmapFormat.PNG);
	}

	
	public static void generaGraficoTorta(Map<String, Integer> dati, String nomeImg, String nomeGrafico) throws IOException {
    	
		// Crea il grafico a torta
        PieChart chart = new PieChartBuilder()
                .width(500)
                .height(280)
                .title(nomeGrafico)
                .theme(Styler.ChartTheme.GGPlot2)
                .build();

        // Aggiungi i dati al grafico
        for (Map.Entry<String, Integer> entry : dati.entrySet()) {
            chart.addSeries(entry.getKey(), (Number) entry.getValue());
        }


        // Salva il grafico come immagine
        String pathImg = "src/main/resources/static/" + nomeImg + ".png";
        BitmapEncoder.saveBitmap(chart, pathImg, BitmapFormat.PNG);
	}
}