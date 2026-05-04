// COMENTADO

package io;

import model.Row;
import model.RowWithLabel;
import model.Table;
import model.TableWithLabels;

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSV {

    public Table readTable(String fileName) throws IOException {
        Table table = new Table();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName));

        String line = br.readLine();
        table.setHeaders(Arrays.asList(line.split(",")));

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            List<Double> values = new ArrayList<>();

            for (String p : parts) {
                values.add(Double.parseDouble(p));
            }

            table.addRow(new Row(values));
        }

        br.close();
        return table;
    }

    public TableWithLabels readTableWithLabels(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/main/resources/" + path));
        String linea = sc.nextLine();
        String[] cabeceras = linea.split(",");
        List<String> headers = Arrays.asList(cabeceras).subList(0, cabeceras.length - 1);
        TableWithLabels tabla = new TableWithLabels();
        tabla.setHeaders(headers);
        while (sc.hasNextLine()) {
            linea = sc.nextLine();
            String[] fila = linea.split(",");
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < fila.length - 1; i++) {
                data.add(Double.parseDouble(fila[i]));
            }
            String label = fila[fila.length - 1];
            tabla.getLabelAsInteger(label);
            tabla.addRow(new RowWithLabel(data, label));
        }
        sc.close();
        return tabla;
    }
}