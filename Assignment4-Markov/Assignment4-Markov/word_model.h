#pragma once

/*
CSCI 262 Data Structures, Spring 2017, Project 4 - Markov

word_model.h

Class declaration for word_model, the mapping Markov text generation with a word
model.

Author: C. Price

Modified: 3/17/2017
*/

#ifndef _WORD_MODEL_H
#define _WORD_MODEL_H

#include "model.h"
#include "strlib.h"
#include "map.h"
#include <string>
using namespace std;

class word_model : public markov_model {
public:
	// give the model the example text and the model order; the model
	// should do any preprocessing in this call
	void word_model::initialize(string text, int order)
	{
		Map<string, Vector<string>> map;
		_order = order;
		while (text.length() > 0)
		{ // separate text into viable words provided the spaces are always behind the end of the text
			if (text.find(' ') < text.length())
			{
				// find the word
				string found_word = text.substr(0, text.find(' '));
				//add the word to the vector of good strings.
				_data.add(found_word);
				// update text minus the word that was just picked out. Prevents infinite loops
				text = text.substr(text.find(' ') + 1);
			}
			// final code for the end when the space is the last spot.
			else
			{
				_data.add(text);
				text = "";
			}
		}

		for (int i = 0; i < _data.size() - _order; i++)
		{
			string key_string = "";
			// the k-word- loop of _order number of words.
			for (int j = i; j < _order + i; j++)
			{ // create key string that will have the same words as the _data vector. Essentially creating a workable key for the map, separated by spaces.
				key_string += _data[j];
				if (j != (_order + i) - 1)
				{
					key_string += " ";
				}
				// prevent wrap-around if the end of the text is reached on word selection. Same as the map_model
				string word;
				if (i + _order >= _data.size())
				{
					word = _data[_data.size() - (i + _order)];
				}
				else
				{
					word = _data[i + _order];
				}
				// basic adding code. Checks if the key word already exists, if so it adds a word to it, otherwise it makes a new  key word.
				// Same idea as  map_model.
				if (map.containsKey(key_string))
				{
					map[key_string].add(word);
				}
				else
				{
					Vector<string> word_vector;
					word_vector.add(word);
					map[key_string] = word_vector;
				}
			}
		}
		markov_map = map;
	}
	virtual std::string generate(int size);

protected: 
	Vector<string> _data;
	int _order;
	Map <string, Vector<string>> markov_map;

};

#endif

