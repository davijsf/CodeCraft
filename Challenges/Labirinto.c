#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <ctype.h>

#define tam 3
#define personagem 'b'

typedef struct pos{
    int coordx;
    int coordy;
} Posicao;

typedef struct {
    bool top;
    bool right;
    bool left;
    bool bottom;
} Labirinto;

typedef enum {
        cima,
        direita,
        baixo,
        esquerda 
}Direcoes;

typedef struct movimentos {
    Direcoes direcao;
    struct Movimentos *proximo;
} Movimentos;

typedef struct {
    int tamanho;
    Movimentos *topo;
} PilhaMovimentos;

void ConfigurarLabirinto(Labirinto labirinto[tam][tam], Posicao jogador);
void ExibirLabirinto(Labirinto labirinto[tam][tam], Posicao jogador);
bool MovimentarPersonagem(Posicao *jogador, Labirinto labirinto[tam][tam], char direcao);


int main(void) {    
    Labirinto labirinto[tam][tam];
    Posicao jogador = {0,0};

    ConfigurarLabirinto(labirinto, jogador);

    printf("Seja bem-vindo ao Labirinto de Reperquilson!\n");

    while(1) {
        char direcao;    
        printf("\033[H\033[J"); // LIMPANDO A TELA
        ExibirLabirinto(labirinto, jogador); // Exibindo labirinto
        printf("Digite a posicao que deseja andar, [w, a, s, d], f para sair : ");
        direcao = getchar();
        getchar();

        if(tolower(direcao) == 'f') {
            printf("Ja desistiu? Que pena...\n");
            break;
        }

        if(MovimentarPersonagem(&jogador, labirinto, direcao)) {
            if(jogador.coordx == tam - 1  && jogador.coordy ==  tam - 1) {
                printf("Parabens! Voce superou os seus limites, pequeno mafagafinho!\n");
                break;
            }
        } else {
            printf("Movimento invalido, jovem! Tente novamente!\n");
        }
    }   

    return 0;
}




// FUNÇÕES: 

void ExibirLabirinto(Labirinto labirinto[tam][tam], Posicao jogador) {
    for (int i = 0; i < tam; i++) {
        // Exibindo as bordas superiores do labirinto
        for (int j = 0; j < tam; j++) {
            printf("#");
            if (labirinto[i][j].top) {
                printf("   ");
            } else {
                printf("###");
            }
        }
        printf("#\n");

        // Exibindo as paredes laterais e o jogador
        for (int j = 0; j < tam; j++) {
            if (labirinto[i][j].left) {
                printf(" ");
            } else {
                printf("#");
            }

            if (i == jogador.coordx && j == jogador.coordy) {
                printf(" %c ", personagem); // Exibe o personagem
            } else {
                printf("   "); // Espaço vazio
            }
        }
        printf("#\n");

        // Exibindo as paredes inferiores
        for (int j = 0; j < tam; j++) {
            printf("#");
            if (labirinto[i][j].bottom) {
                printf("   ");
            } else {
                printf("###");
            }
        }
        printf("#\n");
    }
}


void ConfigurarLabirinto(Labirinto labirinto[tam][tam], Posicao jogador) {
    char resposta;

    for(int i = 0; i < tam; i++) {
        for(int j = 0; j < tam; j++) {
            labirinto[i][j].top = false; // Inicializa todas as paredes do labirinto como falsas
            labirinto[i][j].right = false;  // Pois não há paredes ainda
            labirinto[i][j].bottom = false;
            labirinto[i][j].left = false;
        }
    }

    printf("Otimo! Agora, vamos criar o labirinto!\n");
    for(int i = 0; i < tam; i++) {
        for(int j = 0; j < tam; j++) {
            ExibirLabirinto(labirinto, jogador);
            printf("Vamos configurar a celula [%d][%d]!\n", i, j);

            // VERIFICA SE HÁ PAREDES EM CIMA
            do {
                printf("Tem caminho em cima? (s|n): ");
                resposta = getchar();
                getchar();
            } while(tolower(resposta) != 's'  && tolower(resposta) != 'n');
            labirinto[i][j].top = (tolower(resposta) == 's');
            ExibirLabirinto(labirinto, jogador); 


            // VERIFICA SE HÁ PAREDES EM BAIXO
            do {
                printf("Tem caminho em baixo? (s|n): ");
                resposta = getchar();
                getchar();
            } while(tolower(resposta) != 's'  && tolower(resposta) != 'n');
            labirinto[i][j].bottom = (tolower(resposta) == 's');
            ExibirLabirinto(labirinto, jogador);


           // VERIFICA SE HÁ PAREDES A ESQUERDA
           do {
                printf("Tem caminho a esquerda? (s|n): ");
                resposta = getchar();
                getchar();
            } while(tolower(resposta) != 's'  && tolower(resposta) != 'n');
            labirinto[i][j].left = (tolower(resposta) == 's');
            ExibirLabirinto(labirinto, jogador);


            // VERIFICA SE HÁ PAREDES A DIREITA
            do {
                printf("Tem caminho a direita? (s|n): ");
                resposta = getchar();
                getchar();
            } while(tolower(resposta) != 's'  && tolower(resposta) != 'n');
            labirinto[i][j].right = (tolower(resposta) == 's');
            ExibirLabirinto(labirinto, jogador); 
            printf("\033[H\033[J"); // LIMPANDO A TELA
            printf("\n");
        }
        
    }

}

bool MovimentarPersonagem(Posicao *jogador, Labirinto labirinto[tam][tam], char direcao) {
    int nova_x = jogador->coordx;
    int nova_y = jogador->coordy;

    switch(direcao) {
        case 'w': // CIMA
            if(nova_x > 0 && labirinto[nova_x][nova_y].top) {
                nova_x--;
            } else {
                printf("Lambeu a parede!\n");
                return false;
            }
            break;
        case 'd': // Direita
            if(nova_x < tam && labirinto[nova_x][nova_y].right) {
                nova_y++;
            } else {
                printf("Lambeu a parede!\n");
                return false;
            }
            break;

        case 's': // Baixo
            if(nova_x < tam - 1 && labirinto[nova_x][nova_y].bottom) {
                nova_x++;
            } else {
                printf("Lambeu a parede!\n");
                return false;
            }
            break;

        case 'a': // ESQUERDA
            if(nova_y > 0 && labirinto[nova_x][nova_y].left) {
                nova_y--;
            } else {
                printf("Lambeu a parede!\n");
                return false;
            }
            break;

        default:
            printf("Nao acerta nem a direcao, quem dira ganhar o jogo...\n");
            return false;
            break;
    }
    if(nova_x >= 0 && nova_x < tam && nova_y >= 0 && nova_y < tam) {
        jogador->coordx = nova_x;
        jogador->coordy = nova_y;
        return true;
    }
}