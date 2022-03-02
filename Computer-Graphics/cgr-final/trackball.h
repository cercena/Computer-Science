/*
	Simples movimento em froma de trackball.
	
	Uso:
		Chama tbInit() antes de qualquer outra chamada do tb
		Chama tbRemodela() do retorno da remodelagem
		Chama tbMatriz() para obter a rota��o de matriz do trackball
		Chama tbComecaMovimento() para come�ar o movimento do trackball
		Chama tbParaMovimento() para parar o movimento do trackball
		Chama tbMovimento() do retorno do movimento
		Chama tb_Anima (GL_TRUE) se desejar que o trackball continue girando
		ap�s o bot�o do mouse for solto
		Chama tb_Anima (GL_FALSE) se desejar que o trackball pare de girar 
		depois do bot�o do mouse ter sido solto

*/


//Fun��es
void
tbInit(GLuint botao);

void
tbMatriz();

void
tbRemodela(int largura, int altura);

void
tbMouse(int botao, int estado, int x, int y);

void
tbMovimento(int x, int y);

void
tbAnima(GLboolean anima);
