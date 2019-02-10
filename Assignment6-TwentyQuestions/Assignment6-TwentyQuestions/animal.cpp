#pragma once

#include "animal.h"
#include <iostream>
#include <fstream>
#include <string>
#include <windows.h>

using namespace std;

//public methods
// start the game
animal_game::animal_game()
{	
	// find the binary tree file
	_data_file = "animal.txt";
}
//delete(deconstruct) the game tree
animal_game::~animal_game()
{
	delete_game_tree(_root);
}
// 
bool animal_game::load_game()
{
	ifstream load(_data_file);
	if (!load)
	{
		return false;
	}
	//create the root
	_root = new binary_tree_node<string>;
	//read off the tree and set it up
	read_preorder(_root, load);
	load.close();
	return true;
}
 // step through the game
void animal_game::play_game()
{    //erase the questions and start over
	 _ques.clear();
	 // start the game from the root node.
	 play_game_tree(_root);
}
// save the game to a stream
bool animal_game::save_game()
{   //open stream to save it
	ofstream save(_data_file);
	if (!save)
	{
		return false;
	}
	write_preorder(_root,save);
	save.close();
	return true;
}

// private methods

// outside method to locate a string value (Credit to Jim DeBlock)
void animal_game::find(string search, binary_tree_node<string>* tree)
{
	if (tree->data == search.substr(0, tree->data.length()))
	{
		_end_node = tree;
	}
	if (tree->left != NULL)
	{
		find(search, tree->left);
		find(search, tree->right);
	}
}
void animal_game::read_preorder(binary_tree_node<string>* tree, ifstream &fin)
{
	string script;
	getline(fin, script);
	if (!fin.eof())
	{
		tree->data = script.substr(3);
		//seeing if the spot is marked by a question
		// if so, recursively call the method two times
		// else,  we're done.
		if (script[1] == 'Q')
		{ 
			// create new trees on the left and right and read them out in typical recursive fashion
			tree->left = new binary_tree_node<string>;
			tree->right = new binary_tree_node<string>;
			read_preorder(tree->left, fin);
			read_preorder(tree->right, fin);
		}
		// regular answer
		else
		{
			return;

		}
	}
	// if fin.eof() = true;
	else
	{
		return;
	}
}
// step through the game
void animal_game::play_game_tree(binary_tree_node<string>* tree)
{
	cout << endl;
	
	// ask current question
	cout << tree->data << "(Y/N) :";
	char answer1;
	cin >> answer1;

	// if answer is Y, plug a yes onto the question asked
	if (answer1 == 'Y')
	{
		_ques.add(tree->data + " : YES");
	}
	//  otherwise plug in a no.
	else
	{
		_ques.add(tree->data + " : NO");
	}
	//pre-order traversal
	// starting if to check if we're on  leaf

	if (tree->left == NULL)
	{
		// if the answer was Y
		if (answer1 == 'Y')
		{
			cout << "I win! I guessed your animal" << endl;
			cin.ignore(); // avoid double inputs.
		}
		// if the answer was NO
		else
		{
			cout << "I have no clue what your animal is." << endl;
			// ask to expand the game tree."
			cout << "Would you like to expand the game tree (Y/N)? :";
			char expand;
			cin >> expand;
			// code to extend the binary tree with new data.
			if (expand == 'Y')
			{
				cout << "Questions asked: " << endl;
				for (string question : _ques)
				{
					cout << question << endl;
				}
				// avoid double inputs;
				cin.ignore(); 
				cout << "Enter a new animal in the form of a question. Example : 'Is it a whale?' :" << endl;
				// create new node to stick on the tree
				binary_tree_node<string>* answer = new binary_tree_node<string>;
				getline(cin, answer->data);
				cout << "Enter a question where the answer is 'yes' for your new animal. Make sure it doesn't contradict previous answers." << endl;
				binary_tree_node<string>* question = new binary_tree_node < string >;
				getline(cin, question->data);

				// code to set the tree for the new entry
				// yes
				question->left = answer;
				// no
				question->right = tree;

				// plug the new entry into the tree
				find(_ques[_ques.size() - 2], _root);
				if (_ques[_ques.size() - 2][_ques[_ques.size() - 2].size() - 1] == 'S')
				{
					_end_node->left = question;
				}
				else
				{
					_end_node->right = question;
				}
			}
			// if you picked No to expanding the tree, do nothing!
			else
			{
				cin.ignore();
			}
		}
	}
	// if the value wasn't a leaf, move on to the next question 
	// call the recursive method  for both the right and left nodes
	else
	{
		if (answer1 == 'Y')
		{
			play_game_tree(tree->left);
		}
		else
		{
			play_game_tree(tree->right);
		}
	}
}

// write the tree out.
void animal_game::write_preorder(binary_tree_node<string>* tree, ofstream &fout)
{   //check if you're on a leaf
	if (tree->left == NULL)
	{   //Because you're on  a leaf (as, the value to be written is #A
		fout << "#A " << tree->data << endl;
	}
	//otherwise. It's a question
	else
	{
		// call the method recursively twice like what's been done for all the other primary functions
		fout << "#Q " << tree->data << endl;
		write_preorder(tree->left, fout);
		write_preorder(tree->right, fout);
	}
}
// delete the tree node by node
void animal_game::delete_game_tree(binary_tree_node<string>* tree)
{
	//check if you're on a leaf
	if(tree->left == NULL)
	{
		delete tree;
	}
	else
	{   // call the method recursively twice like what's been done for all the other primary functions
		delete_game_tree(tree->left);
		delete_game_tree(tree->right);
		delete tree;
	}
}

