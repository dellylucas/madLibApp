package com.dfl.madlibs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_story.*
import java.util.*

class StoryFragment : Fragment() {
    private lateinit var storiesNames: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillList()
        listStories.setOnItemClickListener { _, _, position, _ ->
            val text = getStoryFile(position)
            val action = StoryFragmentDirections.actionStoryFragmentToWordsFragment(text)
            view.findNavController().navigate(action)
        }
    }

    private fun getStoryFile(position: Int): String {
        val scan = Scanner(
            resources.openRawResource(
                resources.getIdentifier(
                    storiesNames[position],
                    "raw",
                    context?.packageName
                )
            )
        )
        var allText = ""
        while (scan.hasNextLine()) {
            val line = scan.nextLine()
            allText += line
        }
        scan.close()
        return allText
    }

    private fun fillList() {
        storiesNames = resources.getStringArray(R.array.stories_name)

        val selectTittle = storiesNames.map { text ->
            text.split("_")[1].let {
                it.substring(0, 1).toUpperCase(Locale.getDefault()) + it.substring(1)
            }
        }
        listStories.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, selectTittle)
    }
}
