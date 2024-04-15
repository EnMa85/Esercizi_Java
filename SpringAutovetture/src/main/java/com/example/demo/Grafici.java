package com.example.demo;

import org.knowm.xchart.*;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;

import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;


public class Grafici {
	
	
	public static void generaGraficoBarre(List<String> chiavi, List<Integer> valori, String nomeImg, String nomeGrafico) throws IOException {
    	
        // Crea il grafico a barre
        CategoryChart chart = new CategoryChartBuilder()
            .width(1000)
            .height(500)
            .title(nomeGrafico)
            .build();
        
        // Imposta lo sfondo bianco del grafico
        chart.getStyler().setChartBackgroundColor(Color.WHITE);

        // Imposta il testo in grassetto per il titolo del grafico
        chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 22));

        // Imposta il testo in grassetto per le etichette degli assi
        chart.getStyler().setAxisTickPadding(10);
        chart.getStyler().setAxisTickLabelsFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        
        // Aggiunge i dati al grafico
        chart.addSeries(nomeGrafico, chiavi, valori);

        // Salva il grafico come immagine
        String pathImg = "src/main/resources/static/" + nomeImg + ".png";
        BitmapEncoder.saveBitmap(chart, pathImg, BitmapFormat.PNG);
	}

	
	public static void generaGraficoTorta(Map<String, Integer> dati, String nomeImg, String nomeGrafico) throws IOException {
    	
		// Crea il grafico a torta
        PieChart chart = new PieChartBuilder()
            .title(nomeGrafico)
            .height(500)
            .build();
        
        // Imposta lo sfondo bianco del grafico
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        
        // Imposta la dimensione del testo
        chart.getStyler().setLegendFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
        chart.getStyler().setAnnotationsFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 22));

        // Aggiungi i dati al grafico
        for (Map.Entry<String, Integer> entry : dati.entrySet()) {
            chart.addSeries(entry.getKey(), (Number) entry.getValue());
        }

        // Salva il grafico come immagine
        String pathImg = "src/main/resources/static/" + nomeImg + ".png";
        BitmapEncoder.saveBitmap(chart, pathImg, BitmapFormat.PNG);
	}
}