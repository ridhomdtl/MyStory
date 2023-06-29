package com.example.mystoryapp.story

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val storyList: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "https://img.freepik.com/free-photo/closeup-shot-cute-ginger-kitten-staring-camera-isolated-white-wall_181624-45452.jpg",
                "2022-01-08T06:34:18.598Z",
                "name $i",
                "description $i",
                106.81343696041388,
                "$i",
                -6.418331166109734
            )
            storyList.add(story)
        }
        return storyList
    }
}