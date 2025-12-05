package util;

public class Documento {

    /**
     * Remove tudo que não é dígito (pontos, traços, barras, espaços).
     * Ex: "123.456.789-00" vira "12345678900"
     */
    public static String limpar(String documento) {
        if (documento == null) {
            return "";
        }
        // Substitui tudo que NÃO for número (0-9) por vazio
        return documento.replaceAll("[^0-9]", "");
    }
}
