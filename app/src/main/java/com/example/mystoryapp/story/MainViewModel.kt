package com.example.mystoryapp.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

class MainViewModel(storiesRepository: StoriesRepository) : ViewModel() {

    val paging: LiveData<PagingData<ListStoryItem>> =
        storiesRepository.getStoriesInPaging().cachedIn(viewModelScope)
}