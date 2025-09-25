import java.time.LocalDateTime;        // Classe para trabalhar com data e hora atual
import java.time.format.DateTimeFormatter;  // Classe para formatar a data/hora
import java.util.Scanner;             // Classe para ler entradas do usuário

public class MACKSHOP {

    // ===== Variáveis globais =====
    static int[] idsProdutos = new int[10];          // Array para armazenar IDs dos produtos
    static String[] nomesProdutos = new String[10];  // Array para armazenar nomes dos produtos
    static double[] precoProdutos = new double[10];  // Array para armazenar preços dos produtos
    static int[] estoquesProdutos = new int[10];     // Array para armazenar quantidade em estoque

    static int[] vendaAtualIds = new int[0];         // Array para armazenar IDs dos produtos da venda atual
    static int[] vendaAtualQuantidades = new int[0]; // Array para armazenar quantidades da venda atual

    static int[] historicoIdsPedidos = new int[0];      // Array para armazenar IDs dos pedidos finalizados
    static double[] historicoValoresPedidos = new double[0];  // Array para armazenar valores totais dos pedidos
    static int[][] historicoItensVendidos = new int[0][3];    // Array 2D para armazenar [IDPedido, IDProduto, Quantidade]

    static int proximoIdPedido = 1; // Variável para gerar ID automático para o próximo pedido

    // ===== Método principal =====
    public static void main(String[] args) {
        exibirMenu();  // Chama o método que exibe o menu principal
    }

    // ===== Menu principal =====
    public static void exibirMenu() {
        Scanner entrada = new Scanner(System.in); // Scanner para ler opções do usuário
        int opcao;  // Variável para armazenar a opção escolhida

        do {
            // Exibe as opções do menu
            System.out.println("\n=====MACKSHOP=====");
            System.out.println("1. Inicializar base");
            System.out.println("2. Exibir catálogo de produtos");
            System.out.println("3. Adicionar item à venda");
            System.out.println("4. Ver resumo da venda atual");
            System.out.println("5. Finalizar venda");
            System.out.println("6. Ver histórico de vendas");
            System.out.println("7. Buscar venda específica do histórico");
            System.out.println("8. (Admin) Repor estoque");
            System.out.println("9. (Admin) Relatório de estoque baixo");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = entrada.nextInt();  // Lê a opção digitada pelo usuário

            // Estrutura switch para decidir qual ação executar
            switch (opcao) {
                case 1: 
                    inicializarBase();   // Inicializa produtos com dados padrão
                    break;
                case 2:
                    exibirCatalogo();   // Mostra todos os produtos cadastrados
                    break;
                case 3:
                    adicionarItemVenda(); // Adiciona um produto à venda atual
                    break;
                case 4:
                    verResumoVendaAtual(); // Mostra resumo da venda atual
                    break;
                case 5:
                    finalizarVenda();    // Finaliza a venda, atualiza estoque e histórico
                    break;
                case 6:
                    verHistoricoVendas(); // Mostra todas as vendas finalizadas
                    break;
                case 7:
                    buscarVenda();        // Permite buscar uma venda específica pelo ID
                    break;
                case 8:
                    reporEstoque();       // Reposição de estoque para produtos
                    break;
                case 9:
                    relatorioEstoqueBaixo(); // Exibe produtos com estoque menor que 10
                    break;
                case 0:
                    System.out.println("Saindo..."); // Encerra o programa
                    break;
                default:
                    System.out.println("Opção inválida!"); // Caso o usuário digite uma opção inválida
            }

        } while (opcao != 0);  // Continua exibindo o menu enquanto o usuário não digitar 0
        entrada.close();         // Fecha o Scanner para liberar recurso
    }

    // ===== Inicialização da base de produtos =====
    public static void inicializarBase() {
        // Preenche arrays com produtos padrão
        idsProdutos[0] = 101; nomesProdutos[0] = "Mouse Gamer"; precoProdutos[0] = 150.00; estoquesProdutos[0] = 100;
        idsProdutos[1] = 203; nomesProdutos[1] = "Teclado Mecânico"; precoProdutos[1] = 350.00; estoquesProdutos[1] = 50;
        idsProdutos[2] = 401; nomesProdutos[2] = "Headset 7.1"; precoProdutos[2] = 420.50; estoquesProdutos[2] = 75;
        System.out.println("Base inicializada com sucesso.");
    }

    // ===== Exibir catálogo de produtos =====
    public static void exibirCatalogo() {
        System.out.println("Catálogo de Produtos:");
        // Loop para percorrer todos os produtos cadastrados
        for (int i = 0; i < idsProdutos.length && idsProdutos[i] != 0; i++) {
            if (estoquesProdutos[i] > 0) { // Se tiver estoque, mostra quantidade
                System.out.printf("ID: %d | Produto: %s | Preço: R$ %.2f | Estoque: %d\n",
                        idsProdutos[i], nomesProdutos[i], precoProdutos[i], estoquesProdutos[i]);
            } else { // Se estoque zerado, indica "Esgotado"
                System.out.printf("ID: %d | Produto: %s | Preço: R$ %.2f | Estoque: Esgotado\n",
                        idsProdutos[i], nomesProdutos[i], precoProdutos[i]);
            }
        }
    }

    // ===== Adicionar item à venda atual =====
    public static void adicionarItemVenda() {
        Scanner sc = new Scanner(System.in);  // Scanner para ler entrada do usuário
        System.out.print("Digite o ID do produto que deseja adicionar à venda: ");
        int idProduto = sc.nextInt();  // Lê o ID do produto

        int pos = -1; // Inicializa posição do produto como inválida
        // Procura a posição do produto no array
        for (int i = 0; i < idsProdutos.length; i++) {
            if (idsProdutos[i] == idProduto) {
                pos = i; // Se encontrar, armazena posição
                break;
            }
        }

        if (pos == -1) { // Se não encontrou produto
            System.out.println("ID de produto inválido.");
            return;
        }

        System.out.print("Digite a quantidade do produto: ");
        int quantidade = sc.nextInt(); // Lê quantidade desejada

        // Valida quantidade
        if (quantidade <= 0) {
            System.out.println("Quantidade inválida. Deve ser maior que zero.");
            return;
        }

        // Verifica se há estoque suficiente
        if (quantidade > estoquesProdutos[pos]) {
            System.out.println("Quantidade indisponível em estoque.");
            return;
        }

        // Adiciona produto à venda atual expandindo os arrays
        vendaAtualIds = java.util.Arrays.copyOf(vendaAtualIds, vendaAtualIds.length + 1);
        vendaAtualQuantidades = java.util.Arrays.copyOf(vendaAtualQuantidades, vendaAtualQuantidades.length + 1);
        vendaAtualIds[vendaAtualIds.length - 1] = idProduto;        // Armazena ID do produto
        vendaAtualQuantidades[vendaAtualQuantidades.length - 1] = quantidade; // Armazena quantidade

        System.out.println("Item adicionado à venda com sucesso.");
    }

    // ===== Ver resumo da venda atual =====
    public static void verResumoVendaAtual() {
        if (vendaAtualIds.length == 0) { // Se não houver itens na venda
            System.out.println("Nenhum item na venda atual.");
            return;
        }

        double total = 0; // Inicializa total da venda
        System.out.println("Resumo da venda atual:");

        // Loop para calcular valor de cada item e total
        for (int i = 0; i < vendaAtualIds.length; i++) {
            int pos = 0;
            // Procura a posição do produto no array de produtos
            for (int j = 0; j < idsProdutos.length; j++) {
                if (idsProdutos[j] == vendaAtualIds[i]) pos = j;
            }
            double valorItem = precoProdutos[pos] * vendaAtualQuantidades[i]; // Calcula valor do item
            total += valorItem; // Soma ao total
            System.out.printf("Produto: %s | Qtd: %d | Valor: R$ %.2f\n",
                    nomesProdutos[pos], vendaAtualQuantidades[i], valorItem);
        }

        System.out.printf("TOTAL: R$ %.2f\n", total); // Mostra total da venda
    }

    // ===== Finalizar venda =====
    public static void finalizarVenda() {
        if (vendaAtualIds.length == 0) { // Se não houver venda atual
            System.out.println("Nenhuma venda para finalizar.");
            return;
        }

        double total = 0; // Inicializa total
        // Loop para calcular total e atualizar estoque
        for (int i = 0; i < vendaAtualIds.length; i++) {
            int pos = 0;
            for (int j = 0; j < idsProdutos.length; j++) {
                if (idsProdutos[j] == vendaAtualIds[i]) pos = j;
            }
            total += precoProdutos[pos] * vendaAtualQuantidades[i]; // Soma valor do item ao total
            estoquesProdutos[pos] -= vendaAtualQuantidades[i];      // Atualiza estoque
        }

        // Adiciona venda ao histórico de pedidos
        historicoIdsPedidos = java.util.Arrays.copyOf(historicoIdsPedidos, historicoIdsPedidos.length + 1);
        historicoValoresPedidos = java.util.Arrays.copyOf(historicoValoresPedidos, historicoValoresPedidos.length + 1);

        historicoIdsPedidos[historicoIdsPedidos.length - 1] = proximoIdPedido;   // Armazena ID do pedido
        historicoValoresPedidos[historicoValoresPedidos.length - 1] = total;     // Armazena valor total

        // Adiciona itens vendidos ao histórico detalhado
        int linhasAtuais = historicoItensVendidos.length; // Pega tamanho atual do histórico
        historicoItensVendidos = java.util.Arrays.copyOf(historicoItensVendidos, linhasAtuais + vendaAtualIds.length); // Expande array
        for (int i = 0; i < vendaAtualIds.length; i++) {
            historicoItensVendidos[linhasAtuais + i] = new int[]{proximoIdPedido, vendaAtualIds[i], vendaAtualQuantidades[i]};
        }

        imprimirNotaFiscal(proximoIdPedido, total); // Imprime a nota fiscal
        proximoIdPedido++; // Incrementa ID do próximo pedido

        // Limpa a venda atual
        vendaAtualIds = new int[0];
        vendaAtualQuantidades = new int[0];

        System.out.println("Venda finalizada com sucesso.");
    }

    // ===== Ver histórico de vendas =====
    public static void verHistoricoVendas() {
        if (historicoIdsPedidos.length == 0) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        // Loop para exibir cada pedido
        for (int i = 0; i < historicoIdsPedidos.length; i++) {
            System.out.printf("ID do Pedido: %d | Valor Total: R$ %.2f\n",
                    historicoIdsPedidos[i], historicoValoresPedidos[i]);
            System.out.println("Itens Vendidos:");
            // Loop para mostrar itens do pedido
            for (int j = 0; j < historicoItensVendidos.length; j++) {
                if (historicoItensVendidos[j][0] == historicoIdsPedidos[i]) {
                    System.out.printf("  ID Produto: %d | Quantidade: %d\n",
                            historicoItensVendidos[j][1], historicoItensVendidos[j][2]);
                }
            }
        }
    }

    // ===== Buscar venda específica =====
    public static void buscarVenda() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do Pedido: ");
        int idBusca = sc.nextInt(); // Lê ID do pedido a buscar

        // Procura pedido pelo ID
        for (int i = 0; i < historicoIdsPedidos.length; i++) {
            if (historicoIdsPedidos[i] == idBusca) {
                imprimirNotaFiscal(idBusca, historicoValoresPedidos[i]); // Imprime nota fiscal do pedido encontrado
                return;
            }
        }
        System.out.println("Pedido não encontrado."); // Se não achar o pedido
    }

    // ===== Reposição de estoque =====
    public static void reporEstoque() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do produto: ");
        int id = sc.nextInt(); // Lê ID do produto

        int pos = -1; // Inicializa posição
        for (int i = 0; i < idsProdutos.length; i++) {
            if (idsProdutos[i] == id) pos = i; // Procura posição do produto
        }

        if (pos == -1) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.print("Digite a quantidade para repor: ");
        int qtd = sc.nextInt(); // Lê quantidade a repor

        if (qtd <= 0) {
            System.out.println("Quantidade inválida.");
            return;
        }

        estoquesProdutos[pos] += qtd; // Atualiza estoque
        System.out.println("Estoque atualizado!");
    }

    // ===== Relatório de estoque baixo =====
    public static void relatorioEstoqueBaixo() {
        System.out.println("\nProdutos com estoque baixo (< 10):");
        for (int i = 0; i < idsProdutos.length; i++) {
            if (idsProdutos[i] != 0 && estoquesProdutos[i] < 10) { // Se estoque < 10
                System.out.printf("ID: %d | Produto: %s | Estoque: %d\n",
                        idsProdutos[i], nomesProdutos[i], estoquesProdutos[i]);
            }
        }
    }

    // ===== Impressão da nota fiscal =====
    public static void imprimirNotaFiscal(int idPedido, double total) {
        LocalDateTime agora = LocalDateTime.now(); // Data/hora atual
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Formato da data

        // Cabeçalho da nota fiscal
        System.out.println("*********************************************************************************************");
        System.out.println("* MACKSHOP                                                                                 *");
        System.out.println("* CNPJ: 12.345.678/0001-99                                                                  *");
        System.out.println("*********************************************************************************************");
        System.out.printf("* NOTA FISCAL - VENDA AO CONSUMIDOR | Pedido ID: %d | Data: %s\n", idPedido, agora.format(fmt));
        System.out.println("*********************************************************************************************");
        System.out.println("* # | ID   | DESCRIÇÃO         | QTD | VL. UNIT. | VL. TOTAL                               *");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        double subtotal = 0; // Inicializa subtotal
        int count = 1;       // Número do item na nota

        // Lista os produtos da venda
        for (int i = 0; i < historicoItensVendidos.length; i++) {
            if (historicoItensVendidos[i][0] == idPedido) { // Se item pertence ao pedido
                int id = historicoItensVendidos[i][1]; // ID do produto
                int qtd = historicoItensVendidos[i][2]; // Quantidade
                int pos = 0;
                for (int j = 0; j < idsProdutos.length; j++) {
                    if (idsProdutos[j] == id) pos = j; // Procura posição do produto
                }
                double valorUnitario = precoProdutos[pos];     // Valor unitário
                double valorTotal = qtd * valorUnitario;       // Valor total do item
                subtotal += valorTotal;                        // Soma subtotal
                System.out.printf("* %d | %d | %-16s | %3d | R$ %7.2f | R$ %7.2f\n",
                        count++, id, nomesProdutos[pos], qtd, valorUnitario, valorTotal);
            }
        }

        // Total da nota
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.printf("* SUBTOTAL | R$ %.2f\n", subtotal);
        System.out.printf("* TOTAL    | R$ %.2f\n", total);
        System.out.println("*********************************************************************************************");
        System.out.println("* OBRIGADO PELA PREFERÊNCIA! VOLTE SEMPRE!                                                 *");
        System.out.println("*********************************************************************************************");
    }
}
