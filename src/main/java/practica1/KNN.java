// - PROVISIONALMENTE APTO
//
// - Ver comentarios específicos en otros ficheros (el archivo comienza con "COMENTADO")
// - El proyecto no está configurado correctamente (no es un proyecto Maven; falta pom.xml; pruebas no se ejecutan)
// - La estructura general de paquetes no es correcta (es.uji....).
// - Debes usar paquetes para estructurar tus clases! Las clases que lógicamente pertenecen juntas van en un paquete.
// - En general, buena nomenclatura y buen uso de las convenciones de codificación.
// - Buen uso de la herencia para almacenar RowWithLabel en TableWithLabels.
// - No estás almacenando la etiqueta de la última columna en una TableWithLabels
// - La manera de leer nombre de ficheros (fileName) no funcionará con las pruebas.
// - Falta abstracción para calcular la distancia
//
// - generalmente buena solución, pero con bastantes problemas (ver comentarios). Para la próxima entrega, el proyecto tiene que ser
// bien configurado y todas las pruebas tienen que pasar para convertir el PROVISIONALMENTE APTO en APTO.



package practica1;
import practica2.Algorithm;

import java.util.List;

public class KNN implements Algorithm<Double> {

    private TableWithLabels tabla;

    @Override
    public void train(Table data) throws Exception {
        this.tabla = (TableWithLabels) data;
    }

    public Integer estimate(List<Double> sample) {

        double minDistance = Double.MAX_VALUE;
        String nearestLabel = "";

        for (int i=0; i<tabla.getRowCount(); i++) {

            // SVEN: el cast (RowWithLabel) no tendría que ser necesario
            RowWithLabel current =tabla.getRowAt(i);

            double distance = 0;

            for (int j = 0; j < sample.size(); j++) {
                distance += Math.pow(sample.get(j) - current.getData().get(j),2);
            }

            distance = Math.sqrt(distance);

            if (i==0){
                minDistance=distance;
                nearestLabel=current.getLabel();
            }else if (distance<minDistance){
                minDistance=distance;
                nearestLabel=current.getLabel();
            }
        }

        return tabla.getLabelAsInteger(nearestLabel);
    }
}