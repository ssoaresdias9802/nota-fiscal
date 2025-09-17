import java.util.Scanner;

public class Rascunho{
    public static void main(String[] args){
        int [] idsProdutos;
        String[] nomesProdutos;
        double[] precoProdutos;
        int[] estoquesProdutos;
        
        
        int[] vendaAtuallds;
        int[] vendaAtualQuantidades;
        
        
        
        int[] historicosldsPedidos;
        double[] historicoValoresPedidos;
        int[][] historicoItensVendidos;   }

        System.out.println("1.Inicializar base \n 
        2.Exibir catálogo de produtos\n
         3. Adicionar item à venda\n 
         4. Ver resumo da venda atua\n 
         5. Finalizar venda (gerar histórico e Nota Fiscal)\n 
         6. Ver histórico de vendas\n 
         7. Buscar venda específica do histórico\n 
         8. (Admin) Repor estoque\n 
         9. (Admin) Relatório de estoque baixo"); 
    public static void inicializarBase(int[] idsProdutos, String[] nomesProdutos, double[] precoProdutos, int[] estoquesProdutos) {
        
        idsProdutos[0] = 101; nomesProdutos[0] = "Mouse Gamer"; precoProdutos[0] = 150.00; estoquesProdutos[0] = 100;
        idsProdutos[1] = 203; nomesProdutos[1] = "Teclado Mecânico"; precoProdutos[1] = 350.00; estoquesProdutos[1] = 50;
        idsProdutos[2] = 401; nomesProdutos[2] = "Headset 7.1"; precoProdutos[2] = 420.50; estoquesProdutos[2] = 75;
       

    }
    public static void exibirCatalogo(int[] idsProdutos, String[] nomesProdutos, double[] precoProdutos, int[] estoquesProdutos) {
        System.out.println("Catálogo de Produtos:");
        }if(estoquesProdutos[i] > 0) {
            System.out.printf("ID: %d | Produto: %s | Preço: R$ %.2f | Estoque: %d\n", idsProdutos[i], nomesProdutos[i], precoProdutos[i], estoquesProdutos[i]);
        } else {
            System.out.printf("ID: %d | Produto: %s | Preço: R$ %.2f | Estoque: Esgotado\n", idsProdutos[i], nomesProdutos[i], precoProdutos[i]);
        }
    public static void adicionarItemVenda(int[] idsProdutos, String[] nomesProdutos, double[] precoProdutos, int[] estoquesProdutos, int[] vendaAtualIds, int[] vendaAtualQuantidades) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o ID do produto que deseja adicionar à venda: ");
        int idProduto = scanner.nextInt();
        System.out.print("Digite a quantidade do produto: ");
        int quantidade = scanner.nextInt();
        if(quantidade <= 0) {
            System.out.println("Quantidade inválida. Deve ser maior que zero.");
            return;
        }else if(quantidade > estoquesProdutos[i]) {
            System.out.println("Quantidade indisponível em estoque.");
            return;
        }else if(idProduto not in idsProdutos) {
            System.out.println("ID de produto inválido.");
            return;
        } else {
            vendaAtualIds[i] = idProduto;
            vendaAtualQuantidades[i] = quantidade;
            System.out.println("Item adicionado à venda com sucesso.");
        }

}
