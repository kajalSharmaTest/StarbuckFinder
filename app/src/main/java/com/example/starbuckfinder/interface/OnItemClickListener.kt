package com.example.starbuckfinder.`interface`

import com.example.starbuckfinder.model.Model

/*
Interface to handle list click event
We can implement this interface anywhere we want to trigger the click item event
 */
public interface OnItemClickListener {
    fun onItemClick(model: Model?)
}