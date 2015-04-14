#include <map>
#include <mutex>
#include <vector>
#include <iostream>
#include <random>
#include <functional>
#include <thread>

using namespace std;

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
    mutex m;
    
    auto func = [&]()
    {
        for(int i = 0; i < 125000; i++)
        {
            unique_lock<mutex> lock(m);
            tempMap[keys[i]] = values[i];
        }
    };
    
    auto start = clock();
    
    thread ta[8];
    
    auto total = clock() - start;
    
    for(const auto &x: tempMap)
    {
        cerr << x.first << ": " << x.second << endl;
    }
    cout << "Time: " << total/(double)(CLOCKS_PER_SEC/1000) << " ms" << endl;
    return 1;
}
