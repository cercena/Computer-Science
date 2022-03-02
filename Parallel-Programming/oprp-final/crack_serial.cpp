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

void crack(char *arr, int len, vector<string> linhasChaves)
{
    string x = "";
    int achados = 0;
    int total = linhasChaves.size();
    for (int j = 0; j < len; j++)
    {
        if (linhasChaves.size() == 0)
        {
            return;
        }
        x = arr[j];
        for (int i = 0; i < linhasChaves.size(); i++)
        {
            if (strcmp(crypt(x.c_str(), "Xy"), linhasChaves[i].c_str()) == 0)
            {
                linhasChaves.erase(linhasChaves.begin() + i);
                achados++;
                break;
            }
        }
    }

    for (int j = 0; j < pow(len, 8); j++)
    {
        if (linhasChaves.size() == 0)
        {
            return;
        }
        string x = decToBase(j, len, arr);
        for (int i = 0; i < linhasChaves.size(); i++)
        {
            if (strcmp(crypt(x.c_str(), "Xy"), linhasChaves[i].c_str()) == 0)
            {
                linhasChaves.erase(linhasChaves.begin() + i);
                achados++;
                break;
            }
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
    for (int i = 0; i < linhasChaves.size(); i++)
    {
        crack(arr, len, linhasChaves);
    }
    end = clock();
    double time_taken = double(end - start) / double(CLOCKS_PER_SEC);
    cout << fixed << time_taken << setprecision(5) << endl;

    return 0;
}
