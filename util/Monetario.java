package util;

public class Monetario {

    public static double converterParaDouble(String valorTexto) throws NumberFormatException {
        if (valorTexto == null || valorTexto.trim().isEmpty()) {
            return 0.0;
        }

        // 1. Remove R$ e espaços
        String limpo = valorTexto.replace("R$", "").replace(" ", "").trim();

        // 2. Lógica para corrigir formato Brasileiro (1.000,00)
        // Se tiver vírgula, assumimos que ela é o decimal.
        // Removemos os pontos (separador de milhar) e trocamos a vírgula por ponto (decimal do Java).
        if (limpo.contains(",")) {
            limpo = limpo.replace(".", ""); // 1.200,50 -> 1200,50
            limpo = limpo.replace(",", "."); // 1200,50 -> 1200.50
        } 
        // Se NÃO tiver vírgula, mas tiver ponto (ex: 1.000), o Java entenderia como 1.0
        // Para evitar confusão, se for sistema PT-BR, podemos forçar remover pontos se não houver decimal
        // Mas para simplificar e manter compatibilidade com input simples (1000.50),
        // vamos manter o padrão do Java se não houver vírgula.
        
        return Double.parseDouble(limpo);
    }
}
