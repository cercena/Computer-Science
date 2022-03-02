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

void crack(char *arr, int len, string texto_str)
{
    struct crypt_data data[1] = {0};
    string x = "";
    for (int j = 0; j < len; j++)
    {
        x = arr[j];
        if (strcmp(crypt_r(x.c_str(), "Xy", data), texto_str.c_str()) == 0)
        {
            return;
        }
    }

    for (int j = 0; j < pow(len, 8); j++)
    {
        string x = decToBase(j, len, arr);
        if (strcmp(crypt_r(x.c_str(), "Xy", data), texto_str.c_str()) == 0)
        {
            return;
        }
    }
    return;
}

int main(int argc, char *argv[])
{
    clock_t start, end;
    char arr[] = {
        '.',
        '/',
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'a',
        'b',
        'c',
        'd',
        'e',
        'f',
        'g',
        'h',
        'i',
        'j',
        'k',
        'l',
        'm',
        'n',
        'o',
        'p',
        'q',
        'r',
        's',
        't',
        'u',
        'v',
        'w',
        'x',
        'y',
        'z',
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H',
        'I',
        'J',
        'K',
        'L',
        'M',
        'N',
        'O',
        'P',
        'Q',
        'R',
        'S',
        'T',
        'U',
        'V',
        'W',
        'X',
        'Y',
        'Z',
    };
    vector<string> linhasChaves;
    string fileChaves("chaves.txt");
    ifstream inputChaves(fileChaves);
    string linha;
    int len = sizeof(arr) / sizeof(arr[0]);

    if (!inputChaves.is_open())
    {
        cerr << "Erro - " << fileChaves << endl;
        return EXIT_FAILURE;
    }

    while (getline(inputChaves, linha))
    {
        linhasChaves.push_back(linha);
    }

    start = clock();

    #pragma omp parallel for num_threads(atoi(argv[1]))
    for (int i = 0; i < linhasChaves.size(); i++)
    {
        crack(arr, len, linhasChaves[i]);
    }
    
    end = clock();
    double time_taken = double(end - start) / double(CLOCKS_PER_SEC);
    cout << fixed << time_taken << setprecision(5) << endl;
    return 0;
}
