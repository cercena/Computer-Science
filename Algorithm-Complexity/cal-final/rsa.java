import java.math.BigInteger;
import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class rsa {

    public static BigInteger randomBig(int bits, int flag) {

        Random gerador = new Random();
        BigInteger rand = new BigInteger(bits, gerador);

        if (flag == 1) {
            return rand;
        } else {
            if (rand.mod(BigInteger.valueOf(2)) == BigInteger.ZERO) {
                // rand % 2 == 0
                rand = rand.add(BigInteger.ONE);
                // rand++
            }

            while (true) {
                if (ehPrimo(rand, 100, bits) == 1) {
                    return rand;
                }
                rand = rand.add(BigInteger.TWO);
                // rand += 2
            }
        }
    }

    public static int ehPrimo(BigInteger n, int k, int bits) {

        if (n.compareTo(BigInteger.valueOf(2)) < 0 || n.equals(BigInteger.valueOf(4))) {
            // n < 2 || n == 4
            return 0;
        }

        if (n.compareTo(BigInteger.valueOf(4)) < 0) {
            // n < 4
            return 1;
        }

        while (k > 0) {
            Random gerador = new Random();
            BigInteger rand = new BigInteger(bits, gerador);

            BigInteger a = BigInteger.TWO.add(rand.mod(n.subtract(BigInteger.valueOf(4))));
            // 2 + (rand % (n-4))

            if (!(a.modPow(n.subtract(BigInteger.ONE), n)).equals(BigInteger.ONE)) {
                // a % (n - 1) != 1
                return 0;
            }
            k--;
        }
        return 1;

    }

    public static BigInteger EuclidesEstendido(BigInteger a, BigInteger b) {
        if (a.compareTo(BigInteger.ZERO) == 0) {
            // a == 0
            return b;
        }

        return EuclidesEstendido(b.mod(a), a);
        // b % a, a
    }

    public static void main(String[] args) throws IOException {
        // Declarações
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");
        String msg = "", msgInt = "";
        String[][] array = new String[2][];
        BigInteger p, q, n, e = BigInteger.ZERO, d, t, codigo, fat1 = BigInteger.ZERO, fat2 = BigInteger.ZERO, x;
        int opcao, i, bits;
        long start, elapsedTime;

        while (true) {
            System.out.println("\n0) Sair");
            System.out.println("1) Gerar Chaves");
            System.out.println("2) Criptografar Mensagem");
            System.out.println("3) Descriptografar Mensagem");
            System.out.println("4) Forca Bruta");
            opcao = teclado.nextInt();

            switch (opcao) {
                case 0:
                    teclado.close();
                    System.exit(0);
                    break;

                case 1:
                    System.out.println("Qual o numero de bits? 3-255");
                    bits = teclado.nextInt();
                    if (bits <= 2 || bits >= 256) {
                        System.out.println("Valor incorreto");
                        break;
                    }
                    start = System.currentTimeMillis();
                    p = randomBig(bits, 0);
                    while (true) {
                        q = randomBig(bits, 0);
                        if (!q.equals(p)) {
                            break;
                        }
                    }

                    n = p.multiply(q);
                    t = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
                    // t = (p - 1) * (q - 1)

                    while (true) {
                        x = randomBig(bits / 2, 1);
                        if (EuclidesEstendido(x, t).compareTo(BigInteger.ONE) == 0) {
                            // EuclidesEstendido(x, t) == 1
                            e = x;
                            break;
                        }
                    }

                    d = e.modInverse(t);

                    elapsedTime = System.currentTimeMillis() - start;
                    System.out.println("Tempo percorrido: " + ((double) elapsedTime) / 1000 + "s");
                    System.out.println("CHAVE PUBLICA(n,e): {" + n + "," + e + "}");
                    System.out.println("CHAVE PRIVADA(p,q,d): {" + p + "," + q + "," + d + "}");
                    break;

                case 2:
                    System.out.println("Digite a Mensagem: ");
                    msg = teclado.next();

                    System.out.println("Qual o n?");
                    n = teclado.nextBigInteger();

                    System.out.println("Qual o e?");
                    e = teclado.nextBigInteger();

                    FileWriter arq = new FileWriter("cript.txt");
                    PrintWriter gravarArq = new PrintWriter(arq);

                    start = System.currentTimeMillis();

                    for (i = 0; i < msg.length(); i += 1) {
                        msgInt = msgInt + BigInteger.valueOf(msg.charAt(i)).modPow(e, n) + "-";
                        // msgInt = msgInt + (int) pow(i,e) mod n "-";
                    }

                    elapsedTime = System.currentTimeMillis() - start;
                    System.out.println("\nTempo percorrido: " + ((double) elapsedTime) / 1000 + "s");

                    msgInt = msgInt.substring(0, msgInt.length() - 1) + '\n';
                    gravarArq.printf("%s", msgInt);
                    gravarArq.close();
                    arq.close();
                    break;

                case 3:
                    File arquivo = new File("cript.txt");
                    i = 0;
                    try {
                        Scanner sc = new Scanner(arquivo);
                        while (sc.hasNext()) {
                            array[i] = (sc.nextLine()).split("-");
                            i++;
                        }
                        sc.close();

                        System.out.println("Qual o p?");
                        p = teclado.nextBigInteger();

                        System.out.println("Qual o q?");
                        q = teclado.nextBigInteger();

                        System.out.println("Qual o d?");
                        d = teclado.nextBigInteger();

                        start = System.currentTimeMillis();
                        n = p.multiply(q);

                        System.out.println("MSG DESCRIPTOGRAFADA: ");

                        for (i = 0; i < array[0].length; i += 1) {
                            try {
                                codigo = new BigInteger(array[0][i]).modPow(d, n);
                                // msgInt = msgInt + (int) pow(i,d) mod n "-";
                                System.out.print(new String(codigo.toByteArray(), "UTF-8"));
                            } catch (UnsupportedEncodingException error) {
                                error.printStackTrace();
                            }
                        }

                        elapsedTime = System.currentTimeMillis() - start;
                        System.out.println("\nTempo percorrido: " + ((double) elapsedTime) / 1000 + "s");
                    } catch (IOException error) {
                        System.out.println("NENHUMA MENSAGEM FOI CRIPTOGRAFADA\n");
                    }
                    break;

                case 4:
                    System.out.println("Qual o n?");
                    n = teclado.nextBigInteger();

                    System.out.println("Qual o e?");
                    e = teclado.nextBigInteger();
                    start = System.currentTimeMillis();
                    x = BigInteger.valueOf(3);
                    try {
                        while (true) {
                            if ((n.mod(x)).compareTo(BigInteger.ZERO) == 0) {
                                fat1 = x;
                                fat2 = n.divide(x);
                                break;
                            }

                            if ((x.add(BigInteger.valueOf(2))).mod(BigInteger.valueOf(3))
                                    .compareTo(BigInteger.ZERO) == 0) {
                                x = x.add(BigInteger.valueOf(4));
                            } else {
                                x = x.add(BigInteger.valueOf(2));
                            }
                        }
                        t = (fat1.subtract(BigInteger.ONE)).multiply(fat2.subtract(BigInteger.ONE));
                        System.out.println("CHAVE PRIVADA POR FORCA BRUTA(p,q,n,d): {" + fat1 + "," + fat2 + ","
                                + fat1.multiply(fat2) + "," + e.modInverse(t) + "}");
                    } catch (java.lang.ArithmeticException error) {
                        System.out.println("Chave Invalida");
                    }
                    elapsedTime = System.currentTimeMillis() - start;
                    System.out.println("Tempo percorrido: " + ((double) elapsedTime) / 1000 + "s");
                    break;
            }
        }
    }
}