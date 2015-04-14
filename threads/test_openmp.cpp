#include <map>
#include <vector>
#include <iostream>
#include <random>
#include <functional>

using std::default_random_engine;
using std::uniform_int_distribution;
using std::bind;
using std::map;
using std::vector;
using std::cout;
using std::cerr;
using std::endl;

int main()
{
    default_random_engine generator;
    uniform_int_distribution<int> distribution(1,1000000);
    auto ran = bind(distribution, generator);
    
    vector<int> keys;
    for(int i = 0; i < 1000000; i++)
    {
        keys.push_back(ran());
    }
    
    vector<char> values;
    for(int i = 0; i < 1000000; i++)
    {
        values.push_back(64 + ran() % 27);
    }
    
    map<int, char> tempMap;
    
    auto start = clock();
    
    int i;
#pragma omp parallel for
    for(i = 0; i < 1000000; i++)
    {
#pragma omp critical
{
        tempMap[keys[i]] = values[i];
}
    }
    
    auto total = clock() - start;
    
    for(const auto &x: tempMap)
    {
        cout << x.first << ": " << x.second << endl;
    }
    cerr << "Time: " << total/(double)(CLOCKS_PER_SEC/1000) << " ms" << endl;
    return 1;
}
