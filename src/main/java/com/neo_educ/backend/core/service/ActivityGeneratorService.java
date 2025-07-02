package com.neo_educ.backend.core.service;

import java.util.Map;

/**
 * Interface que define o contrato para um serviço de geração de atividades.
 *
 * A interface utiliza o padrão Strategy, onde cada implementação
 * concreta será uma "estratégia" diferente para gerar conteúdo de IA, adaptada
 * ao seu respectivo módulo de negócio (inglês, nutrição, etc.).
 *
 * O framework irá invocar a implementação correta fornecida pela ApplicationFactory
 * em tempo de execução.
 */
public interface ActivityGeneratorService {

    /**
     * Gera conteúdo (atividades, exercícios, relatórios, etc.) com base em um conjunto
     * de parâmetros.
     *
     * @param infos Um hashmap (Map<String, Object>) contendo todos os dados
     * necessários para a geração do conteúdo. O uso de um hashmap
     * oferece flexibilidade máxima, permitindo que cada
     * implementação defina os parâmetros que espera receber.
     * Ex: {"topic": "Present Perfect", "level": "B1"} para inglês,
     * ou {"goal": "weight_loss", "restrictions": ["lactose"]} para nutrição.
     * @return Uma String contendo o conteúdo gerado pela IA.
     *
     * Exemplo para a aplicação do professor de inglês:
     * Map<String, Object> params = new HashMap<>();
     * params.put("topic", "Present Perfect");
     * params.put("level", "B2");
     * params.put("exerciseType", "multiple_choice");
     * params.put("quantity", 10);
     *
     * Dentro do método de gerar atividade:
     * String topic = (String) generationParams.get("topic"); // Pega o valor associado à chave "topic"
     * String level = (String) generationParams.get("level"); // Pega o valor associado à chave "level"
     * Integer quantity = (Integer) generationParams.get("quantity");
     * ... e assim por diante
     */
    String generateActivity(Map<String, Object> infos);

}
