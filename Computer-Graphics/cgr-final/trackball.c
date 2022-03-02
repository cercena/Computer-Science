 
// Simples movimento em forma de trackball


#include <math.h>
#include <assert.h>
#include <GL/glut.h>
#include "trackball.h"


//Variáveis globais
static GLuint    tb_ultimavez;
static GLfloat   tb_ultimaposicao[3];

static GLfloat   tb_angulo = 0.0;
static GLfloat   tb_eixo[3];
static GLfloat   tb_transforma[4][4];

static GLuint    tb_largura;
static GLuint    tb_altura;

static GLint     tb_botao = -1;
static GLboolean tb_tracking = GL_FALSE;
static GLboolean tb_anima = GL_TRUE;


//Funções
static void
_tbApontaParaVetor(int x, int y, int largura, int altura, float v[3])
{
  float d, a;

  //Projeta x e y em um hemisferio centrado dentro da largura e altura
  v[0] = (2.0 * x - largura) / largura;
  v[1] = (altura - 2.0 * y) / altura;
  d = sqrt(v[0] * v[0] + v[1] * v[1]);
  v[2] = cos((3.14159265 / 2.0) * ((d < 1.0) ? d : 1.0));
  a = 1.0 / sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
  v[0] *= a;
  v[1] *= a;
  v[2] *= a;
}

static void
_tbAnima(void)
{
  glutPostRedisplay();
}

void
_tbComecaMovimento(int x, int y, int button, int vez)
{
  assert(tb_botao != -1);

  tb_tracking = GL_TRUE;
  tb_ultimavez = vez;
  _tbApontaParaVetor(x, y, tb_largura, tb_altura, tb_ultimaposicao);
}

void
_tbParaMovimento(int botao, unsigned vez)
{
  assert(tb_botao != -1);

  tb_tracking = GL_FALSE;

  if (vez == tb_ultimavez && tb_anima) {
    glutIdleFunc(_tbAnima);
  } else {
    tb_angulo = 0.0;
    if (tb_anima)
      glutIdleFunc(0);
  }
}

void
tbAnima(GLboolean anima)
{
  tb_anima = anima;
}

void
tbInit(GLuint botao)
{
  tb_botao = botao;
  tb_angulo = 0.0;

  //Coloca a identidade na transformação do trackball
  glPushMatrix();
  glLoadIdentity();
  glGetFloatv(GL_MODELVIEW_MATRIX, (GLfloat *)tb_transforma);
  glPopMatrix();
}

void
tbMatriz()
{
  assert(tb_botao != -1);

  glPushMatrix();
  glLoadIdentity();
  glRotatef(tb_angulo, tb_eixo[0], tb_eixo[1], tb_eixo[2]);
  glMultMatrixf((GLfloat *)tb_transforma);
  glGetFloatv(GL_MODELVIEW_MATRIX, (GLfloat *)tb_transforma);
  glPopMatrix();

  glMultMatrixf((GLfloat *)tb_transforma);
}

void
tbRemodela(int largura, int altura)
{
  assert(tb_botao != -1);

  tb_largura  = largura;
  tb_altura = altura;
}

void
tbMouse(int botao, int estado, int x, int y)
{
  assert(tb_botao != -1);

  if (estado == GLUT_DOWN && botao == tb_botao)
    _tbComecaMovimento(x, y, botao, glutGet(GLUT_ELAPSED_TIME));
  else if (estado == GLUT_UP && botao == tb_botao)
    _tbParaMovimento(botao, glutGet(GLUT_ELAPSED_TIME));
}

void
tbMovimento(int x, int y)
{
  GLfloat posicao_atual[3], dx, dy, dz;

  assert(tb_botao != -1);

  if (tb_tracking == GL_FALSE)
    return;

  _tbApontaParaVetor(x, y, tb_largura, tb_altura, posicao_atual);

  //Calcula o ângulo para rotacionar diretamente proporcional a largura
  //do movimento do mouse
  dx = posicao_atual[0] - tb_ultimaposicao[0];
  dy = posicao_atual[1] - tb_ultimaposicao[1];
  dz = posicao_atual[2] - tb_ultimaposicao[2];
  tb_angulo = 90.0 * sqrt(dx * dx + dy * dy + dz * dz);

  //Calcula os eixos da rotação (produto cruzado)
  tb_eixo[0] = tb_ultimaposicao[1] * posicao_atual[2] - 
               tb_ultimaposicao[2] * posicao_atual[1];
  tb_eixo[1] = tb_ultimaposicao[2] * posicao_atual[0] - 
               tb_ultimaposicao[0] * posicao_atual[2];
  tb_eixo[2] = tb_ultimaposicao[0] * posicao_atual[1] - 
               tb_ultimaposicao[1] * posicao_atual[0];

  //Reinicia para a próxima vez
  tb_ultimavez = glutGet(GLUT_ELAPSED_TIME);
  tb_ultimaposicao[0] = posicao_atual[0];
  tb_ultimaposicao[1] = posicao_atual[1];
  tb_ultimaposicao[2] = posicao_atual[2];

  //Lembra de desenhar nova posição
  glutPostRedisplay();
}
