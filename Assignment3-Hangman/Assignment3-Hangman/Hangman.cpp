/*
 Author Name: Connor Price

 Date: 3/3//17

 Class : CSCI 262 
 Assignment: Evil Hangman

 Description: A program that constantly changes the hangman word chosen and makes it very difficult to solve/beat.





*/




#include <set.h>
#include <vector.h>
#include <string>
#include <iostream>
#include <set>
#include <map>
#include <vector>
#include <fstream>
#include <ostream>
#include <typeinfo>
#include <algorithm>
using namespace std;

Vector< string> dictionary_words;
Vector <char> alphabet = { 'a', 'b' ,'c' ,'d' ,'e','f','g','h','i','j', 'k','l', 'm','n','o', 'p','q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
char response;
bool solved;


// a basic data structure to hold each family, where word_count is the number of times a word appears in the family and family is the arrangement of letters/dashes.
struct word_family {
	int word_count;
	set<string> family;
};
// A reminder  map to differentiate between multiple strings(with dashes) and their corresponding families.
// This variable is just for show and to remind me what a list of families will look like.
 map <string, word_family> word_family_map;


 
// method to form the maps of all the families. 
 //The string is checked to see if it contains the guessed character.
 // It is then determined if it can be added to an already existing family or if it needs a new one.

 void create_word_families(char guess, set<string> words, map <string, word_family>& families)
{
	// HEY LOOK! I iterated thorugh a set with a pointer!!!
	string current_word;
	for (set<string>::iterator i = words.begin(); i != words.end(); ++i)
	{
		current_word = *i;
		for (int i = 0; i < current_word.length(); i++)
		{
			// marking the spot on each word that needs to be changed to a guessed character
			if (current_word[i] == guess)
			{
				current_word[i] = '@';
			}
			else
			{
				current_word[i] = '-';
			}
		}
		// if the word falls under a new family, make that family
		if (families.find(current_word) == families.end())
		{
			word_family family;
			family.word_count = 1;
			family.family.insert(*i);
			pair< string, word_family> fam (current_word, family);
			families.insert(fam);
		}
		// if the word falls under a family that already exists.
		else
		{
			families[current_word].word_count++;
			families[current_word].family.insert(*i);
		}
	}

}

// Updating the word with the character as it is guessed.
int update_word( string blueprint, string blank, char guess, vector<char>& coded_word)
{
	// if the coded word is blank, do nothing
	if (blueprint == blank)
	{
		return 0;
	}
	int char_count = 0;
	for (int i = 0; i < blueprint.size(); i++)
	{
		if (blueprint[i] == '@')
		{
			coded_word[i] = guess;
			char_count++;
		}

	}
	// the number of characters to be changed
	return char_count;

}
bool compare_family_count(const pair<string, word_family >& first,
	const pair<string, word_family >& second)
{
	return first.second.word_count < second.second.word_count;

}

// MAIN METHOD
int main()

{
	   
		// read a dictionary file and add the words to a giant vector
		cout << "Reading dictionary...";
		ifstream fin("dictionary.txt");
		if (!fin) {
			cerr << "Could not open 'dictionary.txt' for reading, exiting." << endl;
			return -1;
		}

		string word;
		while (!fin.eof())
		{
			getline(fin, word);
			dictionary_words.add(word);
		}
		fin.close();
		cout << "Dictionary done." << endl;
		



		do
		{
		cout << "The dictionary contains " << dictionary_words.size() << " words." << endl;
		// narrow down the dictionary to words of a particular length
		int word_length;
		bool is_possible = false;
		while (!is_possible)
		{
			cout << "Enter a word length :";

			cin >> word_length;
			// messing around with cin statements. This can be ignored as it is assumed the user will input the proper types for the questions.
			/*if(!cin)
			{
				cin.clear();
				cin.ignore();
				cout << "You must enter an actual number!" << endl;
				cout << endl;
				cout << "Enter a word length :";
				cin >> word_length;
				

			}
			*/
			if (word_length > 0)
			{
				for (int i = 0; i < dictionary_words.size(); i++)
				{
					if (dictionary_words[i].length() == word_length)
					{
						is_possible = true;
						break;
					}
				}
			}
			if (!is_possible)
			{
				cout << "There are no English words with that specified length. Please choose another size." << endl;
			}

		}


		cout << "How many guesses do you want?" << endl;
		int num_guesses;
		cin >> num_guesses;
		while (num_guesses < 0)
		{
			cout << "You must choose more than 0 guesses!" << endl;
			cout << "How many guesses do you want?" << endl;
			cin >> num_guesses;

		}

		cout << "Would you like to  keeping a running total of the remaining words in the word list?  (Y/N)" << endl;
		bool using_running_total;
		char running_total_answer;
		cin >> running_total_answer;
		while (running_total_answer != 'Y' && running_total_answer != 'N')
		{
			cout << "You must select eitiher yes or no to the question!" << endl;
			cout << "Would you like to  keeping a running total of the remaining words in the word list?  (Y/N)" << endl;
			cin >> running_total_answer;
		}
		if (running_total_answer == 'Y')
		{
			using_running_total = true;
		}
		else
		{
			using_running_total = false;
		}

		cout << "Starting the game! Good luck! >:) >:) >:)" << endl;
		// The list of viable words for each round of the game.
		set <string> chosen_words;
		for (int i = 0; i < dictionary_words.size(); i++)
		{
			if (dictionary_words[i].length() == word_length)
			{
				chosen_words.insert(dictionary_words[i]);
				//cout << dictionary_words[i] << endl;
			}
		}

		// creating initial blank string. Will be changed as the loop executes
		string blank_word;
		for (int i = 0; i < word_length; i++)
		{
			blank_word.append("-");
		}
		cout << "Starting string --------> " << blank_word << endl;
		Vector <char> guessed_letters;

		vector<char> guessed_word(word_length, '-');


		// Starting the guessing-------------------------------------------------------------------------------------------------------------------------------------
		// The loop ends when the number of guesses reaches 0, or the puzzle has been solved.
		while (num_guesses > 0 && word_length > 0 )
		{
			 solved = true;
			if (using_running_total)
			{
				cout << "There are " << chosen_words.size() << " possible words." << endl;
			}
			cout << "You have " << num_guesses << " guesses remaining." << endl;

			cout << "Please enter a letter to guess:" << endl;
			cout << "Letters already guessed: " << endl;
			for (int i = 0; i < guessed_letters.size(); i++)
			{
				cout << guessed_letters[i] << " ";
			}
			cout << endl;
			char guess;
			cin >> guess;
			bool valid_guess = false;
			bool been_guessed = true;
			// checking to see if the guess is an actual letter
			while (!valid_guess)
			{
				for (int i = 0; i < alphabet.size(); i++)
				{
					if (guess == alphabet[i])
					{
						valid_guess = true;

					}
				}
				if (!valid_guess)
				{
					cout << "Your guess is a not a valid letter. PLease try again." << endl;
					cin >> guess;
				}

				
			}
			// checking to see if the guess has been used already
			while (been_guessed && valid_guess)
			{

				int count = 0;
				for (int i = 0; i < guessed_letters.size(); i++)
				{
					if (guess == guessed_letters[i])
					{
						count++;
					}
				}

				if (count == 0)
				{
					been_guessed = false;
					guessed_letters.add(guess);
				}
				else
				{
					cout << "You have already guessed this later. Please choose another!" << endl;
					cin >> guess;



				}

			}
			
			// actual used MAP
			map <string, word_family> word_families;

			// creating word famillies
			create_word_families(guess, chosen_words, word_families);

			// locating the family with the largest collection of words... Max element will locate the largest value inside a range of values. 
			// It searches the word_count of every family
			pair<string, word_family> biggest_family = *max_element(word_families.begin(),
				word_families.end(), compare_family_count);

			// Tried iterating through a map with pointers.. Didn't work out for me
			/*
			pair<string, word_family> current_family;
			pair<string, word_family> biggest_family;
			int largest_count = 0;
			// more iterations via pointers. This finds the family with the largest count of words.
			for (map <string, word_family>::iterator i = word_families.begin(); i != word_families.end(); ++i)
			{
				current_family = *i;
				if (current_family.second.word_count > largest_count)
				{
					largest_count = current_family.second.word_count;
					biggest_family = current_family;
				}

			}*/
			
			// Fitting our set of words with the largest family
			chosen_words.clear();
			chosen_words = biggest_family.second.family;

			//updating our guessed word if needbe
			string new_word = biggest_family.first;
			int correct_guesses = update_word(new_word, blank_word, guess, guessed_word);

			if (correct_guesses == 0)
			{
				num_guesses--;

			}
			else
			{
				word_length -= correct_guesses;
			}

			// displaying the results of the guess!
			for (int i = 0; i < guessed_word.size(); i++)
			{
				cout << guessed_word[i];
				if (guessed_word[i] == '-')
				{
					solved = false;
				}
				
			}
			cout << endl;
			cout << "Guesses left: " << num_guesses << endl;

		}
		// fail scenario
		if (num_guesses == 0)
		{
			cout << "You lose! Good try but the correct answer was: " << *chosen_words.begin() << endl;
			cout << "Would you like to play again? (Y/N)" << endl;
			cin >> response;
			while (response != 'Y' && response != 'N')
			{
				cout << "You must choose either yes or no." << endl;
				cin >> response;
			}

			cin.get();
		}
		// win scenario
		if (solved)
		{
			cout << "Congratulations! You win!" << endl;
			cout << "Would you like to play again? (Y/N)" << endl;
			cin >> response;
			while (response != 'Y' && response != 'N')
			{
				cout << "You must choose either yes or no." << endl;
				cin >> response;
			}
			cin.get();
			fin.close();
		}
		
		
		
	}
	while (response != 'N' || response == 'Y');
	cout << "Exiting program." << endl;
	return 0;
}
