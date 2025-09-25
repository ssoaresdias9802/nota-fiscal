import java.time.LocalDateTime;        // Classe para trabalhar com data e hora atual
import java.time.format.DateTimeFormatter;  // Classe para formatar a data/hora
import java.util.Scanner;             // Classe para ler entradas do usuário

public class MACKSHOP {

    // ===== Variáveis globais =====
    static int[] idsProdutos = new int[10];          // Array para armazenar IDs dos produtos
    static String[] nomesProdutos = new String[10];  // Array para armazenar nomes dos produtos
    static double[] precoProdutos = new double[10];  // Array para armazenar preços dos produtos
    static int[] estoquesProdutos = new int[10];     // Array para armazenar quantidade em estoque

    static int[] vendaAtualIds = new int[100];        // Array para armazenar IDs dos produtos na venda atual
    static int[] vendaAtualQuantidades = new int[100]; // Array para armazenar quantidades de cada produto na venda
    static int vendaAtualCont = 0;                    // Contador de itens adicionados na venda atual

    // Histórico de vendas
    static int[] historicoIdsPedidos = new int[100];       // Armazena IDs dos pedidos
    static double[] historicoValoresPedidos = new double[100]; // Armazena valores totais de cada pedido
    static int[][] historicoItensVendidos = new int[500][3]; // Armazena itens vendidos [pedidoId, produtoId, quantidade]
    static int historicoCont = 0;      // Contador de pedidos finalizados
    static int historicoItensCont = 0; // Contador de itens vendidos no histórico

    static int proximoIdPedido = 1;    // Variável para gerar ID automático para o próximo pedido
    static boolean baseInicializada = false; // Controla se a base foi inicializada antes de executar funções

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
                    inicializarBase();   // Inicializa produtos, venda atual e histórico
                    break;
                case 2:
                    exibirCatalogo();   // Exibe catálogo de produtos
                    break;
                case 3:
                    adicionarItemVenda(); // Adiciona produto à venda atual
                    break;
                case 4:
                    verResumoVendaAtual(); // Mostra resumo da venda atual
                    break;
                case 5:
                    finalizarVenda();    // Finaliza venda e atualiza histórico
                    break;
                case 6:
                    verHistoricoVendas(); // Exibe todas as vendas
                    break;
                case 7:
                    buscarVenda();        // Busca uma venda específica pelo ID
                    break;
                case 8:
                    reporEstoque();       // Reposição de estoque
                    break;
                case 9:
                    relatorioEstoqueBaixo(); // Relatório de produtos com estoque baixo
                    break;
                case 0:
                    System.out.println("Saindo..."); // Encerra o programa
                    break;
                default:
                    System.out.println("Opção inválida!"); // Caso o usuário digite uma opção inválida
            }

        } while (opcao != 0); // Continua exibindo o menu enquanto o usuário não digitar 0
        entrada.close();        // Fecha o Scanner para liberar recurso
    }

    // ===== Inicialização da base =====
    public static void inicializarBase() {
        // Preenche arrays com produtos padrão
        idsProdutos[0] = 101; nomesProdutos[0] = "Mouse Gamer"; precoProdutos[0] = 150.00; estoquesProdutos[0] = 100;
        idsProdutos[1] = 203; nomesProdutos[1] = "Teclado Mecânico"; precoProdutos[1] = 350.00; estoquesProdutos[1] = 50;
        idsProdutos[2] = 401; nomesProdutos[2] = "Headset 7.1"; precoProdutos[2] = 420.50; estoquesProdutos[2] = 75;

        // Inicializa vetores da venda atual e do histórico
        vendaAtualCont = 0;
        historicoCont = 0;
        historicoItensCont = 0;

        baseInicializada = true; // Marca a base como inicializada
        System.out.println("Base inicializada com sucesso. Agora é possível utilizar todas as funções.");
    }

    // ===== Exibir catálogo =====
    public static void exibirCatalogo() {
        if (!baseInicializada) { // Verificação de inicialização
            System.out.println("Erro: é necessário inicializar a base antes de usar esta função!");
            return;
        }

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

    // ===== Adicionar item à venda =====
    public static void adicionarItemVenda() {
        if (!baseInicializada) { // Verifica se a base foi inicializada
            System.out.println("Erro: é necessário inicializar a base antes de usar esta função!");
            return;
        }

        Scanner sc = new Scanner(System.in); 
        System.out.print("Digite o ID do produto que deseja adicionar à venda: ");
        int idProduto = sc.nextInt(); // Lê o ID do produto

        int pos = buscarPosicaoProduto(idProduto); // Busca posição do produto

        if (pos == -1) { // Produto não encontrado
            System.out.println("ID de produto inválido.");
            return;
        }

        System.out.print("Digite a quantidade do produto: ");
        int quantidade = sc.nextInt();

        if (quantidade <= 0) { // Verifica quantidade válida
            System.out.println("Quantidade inválida. Deve ser maior que zero.");
            return;
        }

        if (quantidade > estoquesProdutos[pos]) { // Verifica estoque
            System.out.println("Quantidade indisponível em estoque.");
            return;
        }

        // Adiciona o item à venda atual
        vendaAtualIds[vendaAtualCont] = idProduto;          // Armazena ID do produto
        vendaAtualQuantidades[vendaAtualCont] = quantidade; // Armazena quantidade
        vendaAtualCont++;                                   // Incrementa contador

        System.out.println("Item adicionado à venda com sucesso.");
    }

    // ===== Resumo da venda atual =====
    public static void verResumoVendaAtual() {
        if (!baseInicializada) {
            System.out.println("Erro: é necessário inicializar a base antes de usar esta função!");
            return;
        }

        if (vendaAtualCont == 0) { // Verifica se existem itens na venda
            System.out.println("Nenhum item na venda atual.");
            return;
        }

        double total = 0; // Inicializa total da venda
        System.out.println("Resumo da venda atual:");

        // Loop para percorrer itens da venda
        for (int i = 0; i < vendaAtualCont; i++) {
            int pos = buscarPosicaoProduto(vendaAtualIds[i]); // Encontra posição do produto
            double valorItem = precoProdutos[pos] * vendaAtualQuantidades[i]; // Calcula valor do item
            total += valorItem; // Soma ao total
            System.out.printf("Produto: %s | Qtd: %d | Valor: R$ %.2f\n",
                    nomesProdutos[pos], vendaAtualQuantidades[i], valorItem);
        }

        System.out.printf("TOTAL: R$ %.2f\n", total); // Exibe total da venda
    }

    // ===== Finalizar venda =====
    public static void finalizarVenda() {
        if (!baseInicializada) {
            System.out.println("Erro: é necessário inicializar a base antes de usar esta função!");
            return;
        }

        if (vendaAtualCont == 0) { // Verifica se há itens na venda
            System.out.println("Nenhuma venda para finalizar.");
            return;
        }

        double total = 0; // Inicializa total da venda

        // Atualiza estoque e calcula total da venda
        for (int i = 0; i < vendaAtualCont; i++) {
            int pos = buscarPosicaoProduto(vendaAtualIds[i]);
            total += precoProdutos[pos] * vendaAtualQuantidades[i];
            estoquesProdutos[pos] -= vendaAtualQuantidades[i]; // Atualiza estoque
        }

        // Adiciona ao histórico de pedidos
        historicoIdsPedidos[historicoCont] = proximoIdPedido;
        historicoValoresPedidos[historicoCont] = total;
        historicoCont++;

        // Adiciona itens vendidos ao histórico de itens
        for (int i = 0; i < vendaAtualCont; i++) {
            historicoItensVendidos[historicoItensCont][0] = proximoIdPedido;
            historicoItensVendidos[historicoItensCont][1] = vendaAtualIds[i];
            historicoItensVendidos[historicoItensCont][2] = vendaAtualQuantidades[i];
            historicoItensCont++;
        }

        // Imprime nota fiscal
        imprimirNotaFiscal(proximoIdPedido, total);

        vendaAtualCont = 0; // Limpa venda atual
        proximoIdPedido++;  // Incrementa próximo ID de pedido
        System.out.println("Venda finalizada com sucesso.");
    }

    // ===== Histórico de vendas =====
    public static void verHistoricoVendas() {
        if (!baseInicializada) {
            System.out.println("Erro: é necessário inicializar a base antes de usar esta função!");
            return;
        }

        if (historicoCont == 0) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        // Exibe cada pedido
        for (int i = 0; i < historicoCont; i++) {
            System.out.printf("ID do Pedido: %d | Valor Total: R$ %.2f\n",
                    historicoIdsPedidos[i], historicoValoresPedidos[i]);
            System.out.println("Itens Vendidos:");
            for (int j = 0; j < historicoItensCont; j++) {
                if (historicoItensVendidos[j][0] == historicoIdsPedidos[i]) {
                    System.out.printf("  ID Produto: %d | Quantidade: %d\n",
                            historicoItensVendidos[j][1], historicoItensVendidos[j][2]);
                }
            }
        }
    }

    // ===== Buscar venda específica =====
    public static void buscarVenda() {
        if (!baseInicializada) {
            System.out.println("Erro: é necessário inicializar a base antes de usar esta função!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do Pedido: ");
        int idBusca = sc.nextInt();

        // Procura pedido pelo ID
        for (int i = 0; i < historicoCont; i++) {
            if (historicoIdsPedidos[i] == idBusca) {
                imprimirNotaFiscal(idBusca, historicoValoresPedidos[i]);
                return;
            }
        }
        System.out.println("Pedido não encontrado.");
    }

    // ===== Reposição de estoque =====
    public static void reporEstoque() {
        if (!baseInicializada) {
            System.out.println("Erro: é necessário inicializar a base antes de usar esta função!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do produto: ");
        int id = sc.nextInt();

        int pos = buscarPosicaoProduto(id);
        if (pos == -1) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.print("Digite a quantidade para repor: ");
        int qtd = sc.nextInt();

        if (qtd <= 0) {
            System.out.println("Quantidade inválida.");
            return;
        }

        estoquesProdutos[pos] += qtd; // Atualiza estoque
        System.out.println("Estoque atualizado!");
    }

    // ===== Relatório de estoque baixo =====
    public static void relatorioEstoqueBaixo() {
        if (!baseInicializada) {
            System.out.println("Erro: é necessário inicializar a base antes de usar esta função!");
            return;
        }

        System.out.println("\nProdutos com estoque baixo (< 10):");
        for (int i = 0; i < idsProdutos.length; i++) {
            if (idsProdutos[i] != 0 && estoquesProdutos[i] < 10) {
                System.out.printf("ID: %d | Produto: %s | Estoque: %d\n",
                        idsProdutos[i], nomesProdutos[i], estoquesProdutos[i]);
            }
        }
    }

    // ===== Nota fiscal =====
    public static void imprimirNotaFiscal(int idPedido, double total) {
        LocalDateTime agora = LocalDateTime.now(); // Data e hora atual
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Formato da data

        System.out.println("*********************************************************************************************");
        System.out.printf("* NOTA FISCAL - VENDA AO CONSUMIDOR | Pedido ID: %d | Data: %s\n", idPedido, agora.format(fmt));
        System.out.println("*********************************************************************************************");

        double subtotal = 0; // Inicializa subtotal
        // Percorre itens do pedido
        for (int i = 0; i < historicoItensCont; i++) {
            if (historicoItensVendidos[i][0] == idPedido) {
                int id = historicoItensVendidos[i][1];
                int qtd = historicoItensVendidos[i][2];
                int pos = buscarPosicaoProduto(id); // Busca posição do produto
                double valorUnitario = precoProdutos[pos];
                double valorTotal = valorUnitario * qtd;
                subtotal += valorTotal;
                System.out.printf("* ID Produto: %d | Nome: %-16s | Qtd: %d | Unit: R$ %.2f | Total: R$ %.2f\n",
                        id, nomesProdutos[pos], qtd, valorUnitario, valorTotal);
            }
        }
        System.out.printf("* TOTAL DA VENDA: R$ %.2f\n", total);
        System.out.println("*********************************************************************************************");
    }

    // ===== Método auxiliar para buscar posição do produto =====
    public static int buscarPosicaoProduto(int idProduto) {
        for (int i = 0; i < idsProdutos.length; i++) {
            if (idsProdutos[i] == idProduto) {
                return i; // Retorna posição se encontrado
            }
        }
        return -1; // Retorna -1 se não encontrado
    }
}
