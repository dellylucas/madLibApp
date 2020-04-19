package com.dfl.madlibs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_words.*

class WordsFragment : Fragment() {
    private var words: List<String>? = null
    private var wordsUser = arrayListOf<String>()
    private var wordsNumber: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_words, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val story = arguments?.getString("storyText")
        words = story?.split("<")
            ?.filter { it.contains('>') }
            ?.map { it.substring(0, it.indexOf('>')).replace("-", " ") }
        wordsNumber = words?.size ?: 0
        fillPropertiesWords()

        wordButton.setOnClickListener {
            wordsUser.add(wordEditText.text.toString())
            if (wordsNumber >= 1) {
                fillPropertiesWords()
                Toast.makeText(requireContext(), "great! keep going!", Toast.LENGTH_SHORT).show()
            } else {
                var text = ""
                var count = words!!.size
                story?.split("<", ">")?.forEachIndexed { index, s ->
                    text += if (index % 2 == 0) s
                    else {
                        count--
                        wordsUser[count]
                    }
                }

                val action = WordsFragmentDirections.actionWordsFragmentToReadFragment(text ?: "")
                view.findNavController().navigate(action)
            }
        }
    }

    private fun fillPropertiesWords() {
        numberWordsTextView.text = "$wordsNumber ${getString(R.string.words_left)}"
        wordEditText.text?.clear()
        wordEditText.hint = words!![wordsNumber - 1]
        numberWordsInformation.text =
            "${getString(R.string.please_type)} ${words!![wordsNumber - 1]}"
        wordsNumber--
    }
}
