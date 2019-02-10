
/*
CSCI 262 Data Structures, Spring 2017, Project 4 - Markov

map_model.h

Class declaration for map_model, the mapping Markov text generation
model.

Author: C. Price

Modified: 3/13/2017
*/

#ifndef _MAP_MODEL_H
#define _MAP_MODEL_H

#include "model.h"
#include "strlib.h"
#include "map.h"
#include <string>
using namespace std;

class map_model : public markov_model {
public:
	// give the model the example text and the model order; the model
	// should do any preprocessing in this call
	virtual void initialize(std::string text, int order)
	{
		_data = text + text.substr(0, order);
		_order = order;
		char character;
		Map < string, Vector<char>> map;

		for (int i = 0; i < text.length() - order; i++)
		{
			// access the k-gram string
			string k_gram = text.substr(i, order);
			// prevent wrap-around if the end of the text is reached on character selection.
			if (i + order >= text.length())
			{
				character = text[text.length() - (i + order)];
			}
			else
			{
				character = text[i + order];
			}
			 
			// if the k-gram already exists, add the following character
			if (map.containsKey(k_gram))
			{
				map.get(k_gram).push_back(character);
			}
			else
				// otherwise, add a new k-gram to the map
			{
				Vector<char> character_vector;
				character_vector.add(character);
				map.put(k_gram, character_vector);
			}

		}
		markov_map = map;
	}
	// produce a text in the style of the example
	virtual std::string generate(int size);

protected:
	std::string _data;
	int _order;
	Map <string, Vector<char>> markov_map;
	
};

#endif

