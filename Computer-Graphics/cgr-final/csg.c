
#include <GL/glut.h>
#include <math.h>
#include "trackball.h"
#include "trackball.c"

#define ESFERA 1
#define CONE   2
#define CUBO  3


//Operações combinatórias do CSG
typedef enum {
    CSG_A,
    CSG_B,
    CSG_A_OU_B,
    CSG_A_E_B,
    CSG_A_MENOS_B,
    CSG_B_MENOS_A
} operacaoCSG;


//Decalarações iniciais
GLint Largura;
GLint Altura;

GLfloat zoom = 0.0;

GLboolean cubo_selecionado   = GL_FALSE;
GLboolean esfera_selecionado = GL_FALSE;
GLboolean cone_selecionado   = GL_FALSE;

GLboolean selecao = GL_FALSE;	//Renderização para o buffer de seleção

#define SELECT_BUFFER 32
GLuint seleciona_buffer[SELECT_BUFFER];  //Seleção do buffer

GLfloat cone_x = 0.0;
GLfloat cone_y = 0.0;
GLfloat cone_z = 0.0;

GLfloat cubo_x = 0.0;
GLfloat cubo_y = 0.0;
GLfloat cubo_z = 0.0;

GLfloat esfera_x = 0.0;
GLfloat esfera_y = 0.0;
GLfloat esfera_z = 0.0;

GLint estado_mouse = -1;
GLint botao_mouse = -1;

operacaoCSG Op = CSG_A_OU_B;

void (*A)(void);
void (*B)(void);


//Funções combinatórias

//Desenha um único objeto	
void unico(void(*a)(void)) 
{
  glEnable(GL_DEPTH_TEST);
  a();
  glDisable(GL_DEPTH_TEST);
}

//Operação booleana A ou B, desenha ambos com o depth test
void ou(void(*a)(void), void(*b)())
{
    glPushAttrib(GL_ALL_ATTRIB_BITS);  
    glEnable(GL_DEPTH_TEST);
    a(); b();
    glPopAttrib();
}

//Mostra parte de A que está dentro do B
void dentro(void(*a)(void), void(*b)(void), GLenum face, GLenum teste)
{   
    //Desenha A no depth buffer, mas não no color buffer
    glEnable(GL_DEPTH_TEST);
    glColorMask(GL_FALSE, GL_FALSE, GL_FALSE, GL_FALSE);
    glCullFace(face);
    a();
     
    //Usa o stencil buffer para achar parte de A que estão dentro de B
    //incrementando o stencil buffer aonde as faces frontais de B estão
    glDepthMask(GL_FALSE);
    glEnable(GL_STENCIL_TEST);
    glStencilFunc(GL_ALWAYS, 0, 0);
    glStencilOp(GL_KEEP, GL_KEEP, GL_INCR);
    glCullFace(GL_BACK);
    b();

    //Então decrementa o stencil buffer onde as faces traseiras de B estão
    glStencilOp(GL_KEEP, GL_KEEP, GL_DECR);
    glCullFace(GL_FRONT);
    b();

    //Agora desenha a parte de A que está dentro de B
    glDepthMask(GL_TRUE);
    glColorMask(GL_TRUE, GL_TRUE, GL_TRUE, GL_TRUE);
    glStencilFunc(teste, 0, 1);
    glDisable(GL_DEPTH_TEST);
    glCullFace(face);
    a();

    //Reseta o stencil test
    glDisable(GL_STENCIL_TEST);
}

//Restaura o depth buffer com os valores profundos de A
void restaura(void(*a)(void))
{
    //Restaura o depth buffer
    glColorMask(GL_FALSE, GL_FALSE, GL_FALSE, GL_FALSE);
    glEnable(GL_DEPTH_TEST);
    glDisable(GL_STENCIL_TEST);
    glDepthFunc(GL_ALWAYS);
    a();

    //Restabelece o depth func
    glDepthFunc(GL_LESS);
}

//Operação booleana A e B, procura onde A está dentro de B e então procura
//onde B está dentro de A
void e(void(*a)(void), void(*b)(void))
{
    dentro(a, b, GL_BACK, GL_NOTEQUAL);
#if 1  /* set to 0 for faster, but incorrect results */
    restaura(b);
#endif
    dentro(b, a, GL_BACK, GL_NOTEQUAL);
}

//Operação booleana A menos B, procura onde A está dentro de B e então procura
//onde as faces traseiras de B não estão em A
void menos(void(*a)(void), void(*b)(void))
{
    dentro(a, b, GL_FRONT, GL_NOTEQUAL);
#if 1  //Configurar em 0 aumenta a rapidez, mas mostra resultados incorretos
    restaura(b);
#endif
    dentro(b, a, GL_BACK, GL_EQUAL);
}

//Esfera
void esfera(void)
{
    glLoadName(2);
    glPushMatrix();
    glTranslatef(esfera_x, esfera_y, esfera_z);
    glColor3f(1.0, 1.0, 0.0);
    glutSolidSphere(5.0, 16, 16);
    glPopMatrix();
}

//Cubo
void cubo(void)
{
    glLoadName(1);
    glPushMatrix();
    glTranslatef(cubo_x, cubo_y, cubo_z);
    glColor3f(1.0, 0.0, 0.0);
    glutSolidCube(8.0);
    glPopMatrix();
}

//Cone
void cone(void)
{
    glLoadName(3);
    glPushMatrix();
    glTranslatef(cone_x, cone_y, cone_z);
    glColor3f(0.0, 1.0, 0.0);
    glTranslatef(0.0, 0.0, -6.5);
    glutSolidCone(4.0, 15.0, 16, 16);
    glRotatef(180.0, 1.0, 0.0, 0.0);
    glutSolidCone(4.0, 0.0, 16, 1);
    glPopMatrix();
}

void init(void)
{
    GLfloat lightposition[] = { -3.0, 3.0, 3.0, 0.0 };

    tbInit(GLUT_MIDDLE_BUTTON);

    glSelectBuffer(SELECT_BUFFER, seleciona_buffer);

    glDepthFunc(GL_LESS);
    glEnable(GL_DEPTH_TEST);

    glEnable(GL_LIGHT0);
    glEnable(GL_LIGHTING);
    glLightfv(GL_LIGHT0, GL_POSITION, lightposition);
    glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);

    glEnable(GL_COLOR_MATERIAL);

    glEnable(GL_CULL_FACE);

    glClearColor(0.0, 0.0, 1.0, 0.0);
}

void remodela(int largura, int altura)
{
    Largura = largura;
    Altura = altura;

    tbRemodela(largura, altura);

    glViewport(0, 0, largura, altura);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glFrustum(-3.0, 3.0, -3.0, 3.0, 64, 256);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(0.0, 0.0, -200.0 + zoom);
}

void mostra(void)
{
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

  glPushMatrix();
  tbMatriz();
  
  switch(Op) {
  case CSG_A:
    unico(A);
    break;
  case CSG_B:
    unico(B);
    break;
  case CSG_A_OU_B:
    ou(A, B);
    break;
  case CSG_A_E_B:
    e(A, B);
    break;
  case CSG_A_MENOS_B:
    menos(A, B);
    break;
  case CSG_B_MENOS_A:
    menos(B, A);
    break;
  }
  
  glPopMatrix();
  
  if (!selecao)
    glutSwapBuffers();
}

GLuint
escolhe(int x, int y)
{
  GLuint    i, hits, num_nomes, escolhido;
  GLuint*   p;
  GLboolean salvo;
  GLuint    depth = (GLuint)-1;
  GLint     exibicao[4];

  // Pega os atuais parâmetros de exibição
  glGetIntegerv(GL_VIEWPORT, exibicao);

  // Configura o modo de renderização para a seleção
  glRenderMode(GL_SELECT);
  selecao = GL_TRUE;
  glInitNames();
  glPushName(0);

  // Configura uma matriz de seleção e renderiza no buffer de seleção
  glMatrixMode(GL_PROJECTION);
  glPushMatrix();

  glLoadIdentity();
  gluPickMatrix(x, exibicao[3] - y, 5.0, 5.0, exibicao);
  glFrustum(-3.0, 3.0, -3.0, 3.0, 64, 256);

  glMatrixMode(GL_MODELVIEW);
  glLoadIdentity();
  glTranslatef(0.0, 0.0, -200.0 + zoom);
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

  glPushMatrix();
  tbMatriz();
  ou(A, B);
  glPopMatrix();

  glMatrixMode(GL_PROJECTION);
  glPopMatrix();
  glMatrixMode(GL_MODELVIEW);

  hits = glRenderMode(GL_RENDER);

  selecao = GL_FALSE;

  p = seleciona_buffer;
  escolhido = 0;
  for (i = 0; i < hits; i++) {
    salvo = GL_FALSE;
    num_nomes = *p;		//Número de nomes nesse hit
    p++;

    if (*p <= depth) {  //Checa o 1º valor de profundidade
      depth = *p;
      salvo = GL_TRUE;
    }
    p++;
    if (*p <= depth) {	//Checa o 2º valor de profundidade
      depth = *p;
      salvo = GL_TRUE;
    }
    p++;

    if (salvo)
      escolhido = *p;

    p += num_nomes;		//Pula para o resto dos nomes
  }

  return escolhido;
}

void teclado(unsigned char tecla, int x, int y)
{
    switch(tecla) {
    case 'c':
	if(A == cubo && B == esfera) {
	    A = esfera;
	    B = cone;
	} else if(A == esfera && B == cone) {
	    A = cone;
	    B = cubo;
	} else { /* if(A == cone && B = cubo) */
	    A = cubo;
	    B = esfera;
	}
	break;
    case 'a':
	Op = CSG_A;
	break;
    case 'b':
	Op = CSG_B;
	break;
    case '|':
	Op = CSG_A_OU_B;
	break;
    case '&':
	Op = CSG_A_E_B;
	break;
    case '-':
	Op = CSG_A_MENOS_B;
	break;
    case '_':
	Op = CSG_B_MENOS_A;
	break;
    case 'z':
	zoom -= 6.0;
	remodela(Largura, Altura);
	break;
    case 'Z':
	zoom += 6.0;
	remodela(Largura, Altura);
	break;
    case 27:
	exit(0);
	break;
    case '\r':
	break;
    default:
	return;
    }

    glutPostRedisplay();
}

void
mouse(int botao, int estado, int x, int y)
{
  estado_mouse = estado;
  botao_mouse = botao;

  tbMouse(botao, estado, x, y);

  if (botao == GLUT_LEFT_BUTTON) {
    switch (escolhe(x, y)) {
    case 1:
      cubo_selecionado = GL_TRUE;
      esfera_selecionado = GL_FALSE;
      cone_selecionado = GL_FALSE;
      break;
      
    case 2:
      esfera_selecionado = GL_TRUE;
      cubo_selecionado = GL_FALSE;
      cone_selecionado = GL_FALSE;
      break;
      
    case 3:
      cone_selecionado = GL_TRUE;
      esfera_selecionado = GL_FALSE;
      cubo_selecionado = GL_FALSE;
      break;
      
    default:
      esfera_selecionado = GL_FALSE;
      cubo_selecionado = GL_FALSE;
      cone_selecionado = GL_FALSE;
      break;
    }
  }

  glutPostRedisplay();
}

GLvoid
cruza(GLfloat* u, GLfloat* v, GLfloat* n)
{
  /* compute the cross product (u x v for right-handed [ccw]) */
  //Computa o produto cruzado 
  n[0] = u[1] * v[2] - u[2] * v[1];
  n[1] = u[2] * v[0] - u[0] * v[2];
  n[2] = u[0] * v[1] - u[1] * v[0];
}

GLfloat
normaliza(GLfloat* n)
{
  GLfloat l;

  //Normaliza
  l = (GLfloat)sqrt(n[0] * n[0] + n[1] * n[1] + n[2] * n[2]);
  n[0] /= l;
  n[1] /= l;
  n[2] /= l;

  return l;
}

void
movimento(int x, int y)
{
  GLdouble modelo[4*4];
  GLdouble proj[4*4];
  GLint visao[4];
  GLdouble pan_x, pan_y, pan_z;

  tbMovimento(x, y);

  if (estado_mouse == GLUT_DOWN && botao_mouse == GLUT_LEFT_BUTTON) {
    glGetDoublev(GL_MODELVIEW_MATRIX, modelo);
    glGetDoublev(GL_PROJECTION_MATRIX, proj);
    glGetIntegerv(GL_VIEWPORT, visao);
    gluProject((GLdouble)x, (GLdouble)y, 0.0,
		 modelo, proj, visao,
		 &pan_x, &pan_y, &pan_z);
    gluUnProject((GLdouble)x, (GLdouble)y, pan_z,
		 modelo, proj, visao,
		 &pan_x, &pan_y, &pan_z);
    pan_y = -pan_y;

    if (esfera_selecionado) {
      esfera_x = pan_x;
      esfera_y = pan_y;
      esfera_z = pan_z;
    } else if (cone_selecionado) {
      cone_x = pan_x;
      cone_y = pan_y;
      cone_z = pan_z;
    } else if (cubo_selecionado) {
      cubo_x = pan_x;
      cubo_y = pan_y;
      cubo_z = pan_z;
    }
  }

  glutPostRedisplay();
}

void menu(int item)
{
    teclado((unsigned char)item, 0, 0);
}

void main(int argc, char** argv)
{
    int ops, zoom;

    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE | GLUT_DEPTH | GLUT_STENCIL);

    glutCreateWindow("Operações com CSG");

    glutDisplayFunc(mostra);
    glutReshapeFunc(remodela);
    glutKeyboardFunc(teclado);
    glutMouseFunc(mouse);
    glutMotionFunc(movimento);

    ops = glutCreateMenu(menu);
    glutAddMenuEntry("A apenas       [a]", 'a');
    glutAddMenuEntry("B apenas       [b]", 'b');
    glutAddMenuEntry("A ou B         [|]", '|');
    glutAddMenuEntry("A e B          [&]", '&');
    glutAddMenuEntry("A menos B      [-]", '-');
    glutAddMenuEntry("B menos A      [_]", '_');
    zoom = glutCreateMenu(menu);
    glutAddMenuEntry("Diminuir zoom  [z]", 'z');
    glutAddMenuEntry("Aumentar zoom  [Z]", 'Z');
    glutCreateMenu(menu);
    glutAddMenuEntry("Operações com CSG", '\0');
    glutAddMenuEntry("                 ", '\0');
    glutAddSubMenu(  "Operações         ", ops);
    glutAddSubMenu(  "Zoom             ", zoom);
    glutAddMenuEntry("                 ", '\0');
    glutAddMenuEntry("Mudar formas   (c)", 'c');
    glutAddMenuEntry("                 ", '\0');
    glutAddMenuEntry("Sair      (Esc)", '\033');
    glutAttachMenu(GLUT_RIGHT_BUTTON);

    init();

    A = cubo;
    B = esfera;

    glutMainLoop();
}
