Author: MIDN Tristen Alvis (260102)
Description: SI444 Project (Fitness App)

Notes:

For this milestone, I implemented the barebones activities and views needed to allow navigation
between one activity in the app to another. I still need to work on displaying data in some of the
activities, making the views prettier, and using intents to pass data from one activity to another.

I made a few big changes to my Search Activity. In the original, the plan was to show a recycler view of
saved routines and to have a search bar that allows you to search a saved data base of other routines
that may not be saved. I decided that this implementation would be more difficult than I anticipated and
would be unintuitive for the user. Instead, the search activity now has a recycler view that shows either
saved or non-saved routines, and the view can be changed with left/right swaps. The search bar allows 
you to filter out items in the recycler view to make finding specific routines easier. I implemented the
swipe ability, the search filtering, and the reyclerview swapping. This was fairly difficult and the 
main accomplishment of milestone 1. 
