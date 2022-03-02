#include <bits/stdc++.h>
#include <stdio.h>
#include <algorithm>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <vector>
#include <omp.h>
#include <iostream>
#include <fstream>
#include <crypt.h>
#include <mpi.h>

using namespace std;

string decToBase(int x, int base, char *arr)
{
    int result = x;
    string s = "";
    int array[base];
    int num_elements = 0;
    int j = 0;
    while (true)
    {
        array[j] = result % base;
        num_elements++;
        result = result / base;
        if (result < base)
        {
            num_elements++;
            array[j + 1] = result;
            break;
        }
        j++;
    }
    for (int j = 0; j < num_elements; j++)
    {
        s = s + arr[array[j]];
    }

    return s;
}

void crack(char *arr, int len, char texto_str[14])
{
    struct crypt_data data[1] = {0};
    string x = "";
    clock_t start, end;
    start = clock();

    for (int j = 0; j < len; j++)
    {
        x = arr[j];
        if (strcmp(crypt_r(x.c_str(), "Xy", data), texto_str) == 0)
        {
            printf("%s\n",x.c_str());
            end = clock();
            double time_taken = double(end - start) / double(CLOCKS_PER_SEC);
            cout << fixed << time_taken << setprecision(5) << endl;
            return;
        }
    }

    for (int j = 0; j < pow(len, 8); j++)
    {
        string x = decToBase(j, len, arr);
        if (strcmp(crypt_r(x.c_str(), "Xy", data), texto_str) == 0)
        {
            printf("%s\n",x.c_str());
            end = clock();
            double time_taken = double(end - start) / double(CLOCKS_PER_SEC);
            cout << fixed << time_taken << setprecision(5) << endl;
            return;
        }
    }
    return;
}

int main(int argc, char *argv[])
{
    char arr[] = {
        '.','/',
        '0','1','2','3','4','5','6','7','8','9',
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
    };
    vector<string> linhasChaves;
    string fileChaves("chaves.txt");
    ifstream inputChaves(fileChaves);
    string linha;
    int len = sizeof(arr) / sizeof(arr[0]);
    int rank, size;
    char chave[14];

    if (!inputChaves.is_open())
    {
        cerr << "Erro - " << fileChaves << endl;
        return EXIT_FAILURE;
    }
    while (getline(inputChaves, linha)){
        linhasChaves.push_back(linha);
    }
    char array[linhasChaves.size()][14];
    for(int i=0; i<linhasChaves.size(); i++){
        strcpy(array[i],linhasChaves[i].c_str());
    }

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    MPI_Scatter(array, 14, MPI_CHAR, chave, 14, MPI_CHAR, 0, MPI_COMM_WORLD);

    crack(arr, len, chave);

    MPI_Finalize();

    return 0;
}
