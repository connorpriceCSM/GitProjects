
/*
CSCI 262 Data Structures, Spring 2017, Project 4 - Markov

map_model.cpp

cpp file for map_model, the mapping Markov text generation
model.

Author: C. Price

Modified: 3/13/2017
*/
#include "map_model.h"
#include "map.h"
#include <cstdlib>
#include "vector.h"
#include <string>
using namespace std;

// generating a markov string of size-length
string map_model::generate(int size)
{
	int start = (rand() % (_data.length() - _order));
	string k_gram = _data.substr(start, _order);

	string answer;
	answer.reserve(size);
	
	for (int i = 0; i < size; i++)
	{
		Vector<char> char_list = markov_map.get(k_gram);
		char c = char_list[rand() % char_list.size()];
		answer.push_back(c);
		k_gram = k_gram.substr(1) + c;
	}
	return answer;


/*	seed = random k-character substring from the training text --- the initial seed
	repeat N times to generate N random letters
		find the vector(value) associated with seed(key) using the map
		next - char = choose a random char from the vector(value)
		print or store next - char
		seed = (last k - 1 characters of seed) + next - char
		*/
}
