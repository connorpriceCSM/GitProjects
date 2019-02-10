
/*
CSCI 262 Data Structures, Spring 2017, Project 4 - Markov

word_model.cpp

cpp file for word_model, the mapping Markov text generation with a word
model.

Author: C. Price

Modified: 3/17/2017
*/



#include "word_model.h"
#include "map.h"
#include <cstdlib>
#include "vector.h"
#include <string>

string word_model::generate(int size) {
	
	// random starting spot, same as in map_model
	string answer;
	int location = rand() % (_data.size() - _order);
	string k_word;
	for (int i = location; i < location + _order; i++)
	{ // get random starting seed (and word) from the vector of words. 
			k_word += _data[i];

		if (i != (location + _order) - 1) 
		{
			k_word += " ";
		}
	}

	// generate markov string of size-words
	for (int i = 0; i < size; i++) {
		// random word picker
		Vector<string> words = markov_map[k_word];
		string word = words[rand() % words.size()]; 
		answer += word + " "; 
		// specific scenario safeguard for the order if it is found to be only one word.
		if (_order > 1) 
		{
			k_word = k_word.substr(k_word.find(' ') + 1) + " " + word;
		}
		else 
		{
			k_word = word; 
		}
	}

	return answer;
}
/*	seed = random k-character substring from the training text --- the initial seed
repeat N times to generate N random letters
find the vector(value) associated with seed(key) using the map
next - char = choose a random char from the vector(value)
print or store next - char
seed = (last k - 1 characters of seed) + next - char
*/