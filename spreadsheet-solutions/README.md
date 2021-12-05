# Spreadsheet Solutions
---------------------------------------

## Introduction

So to have gotten an invite to participate on a private leaderboard for AoC 2021
with some old friends was exciting. The biggest challenge (apart from the fact that
I have not programmed for 4 years now) was that I am working in a low bandwidth
situation and with a tiny travel laptop that has a 10 inch screen and a CPU/RAM
combinaton that was used by creatures that last walked the earth before we 
humans decided to take that space that decided to have programming events.
Well. :)

Another challenge was that the puzzles are released at 10.30am IST and that means
it clashed with some of the work I am doing. So gave myself some personal rules
on going about this even before I started. Some of these are stolen from NK
who sent out the invite.

* Have fun!
* Spend maximum 1.5 to 2 hours (from 10.30am i.e)
* Walk away if needed for other things (can be disturbed)
* Always try and complete puzzle if time available later in the day
* Just get the answer right without worrying about how (no cheating of course)

Had no clue how to go about this initially. The laptop does not have a programming
environment ... No wait, that would be a lie. It is a Linux with C, C++, Python, Awk,
Bash, Golang (thanks to Hugo) and what not. So let me say that it does not have
a programming environment that I could use without re-checking my code a
thousand times. If I had to code with all the rules above, I would have to code
with most of the syntax and method-names just flowing out of my brain. Since I
had my brain filled up with non-code stuff from four years now, I was sure I would
stare at the screen for a long time and get demotivated. 

So some work on brushing up on programming languages I am familiar with, 
started. But that is an ongoing process and Day-01 arrived. It was just a hunch.
But I thought what if I used spreadsheets to solve the problems. I was using
spreadsheets on this laptop and while I can't open more than 3 of them without
making the system really slow it was enough to do some work and since I had
recently used some for complicated calculations for a project with some amount
of data, lots of formulae were understood. Why not?

I knew that this would mean I might give up on problems completely or postpone
solving them with programming logic that was not spreadsheet-like, but that
was okay as long as the rules were followed. Mainly having fun! But this also
involved looking at the problem and quickly "spreadsheetify" (if I may use that
word) it. Since looking at answers and getting them from the screen is not cheating,
spreadsheets if done right can also lead to situations where one can get away
without doing some end calculations, because one can "simply see it on the
screen", one can quicky take it and go. I shall explain that later if possible with
an example. 

For now, documenting below each day as a section. This document is expected
to be updated as new learning comes up or I figure out that I typed in a hurry
and made you read gibberish. 

NOTE: I am not going to put in the story part of the puzzles or even discuss them.
As a reader of this document I am going to presume that you and me are on the
same page when it comes to what the puzzle was, so each section will directly
go into the solution.

## Day 01

The solution seemed to require me to look at adjacent pieces of information and
this was easy on spreadsheets because the notion of "next-cell" and "previous-cell"
both by row and column is so natural to a spreadsheet that you won't realize
you are using it. Especially when you put in a formula in a column and copy it (not
the formula text, but copy the cell) and spreadsheets transmogrify it for you
how many ever times you want. So this power is what I intended to use for the
problem. 

The input data was simply pasted into a column. Leaving an offset for the first
piece of input data, a formula was written to compare the current and previous
cell and throw in a "state" (0 or 1) and then the formula was simply copied over
to all the other rows with an easy-peasy shorthand too by a double-click in the
corner. 

For the second part of the puzzle as well, the same input data could be used
but this time to create a sliding window by using another column to add up
three adjacent columns. Hence this puzzle was solved really fast (in about 5 minutes)
but sadly on the first day itself, I could attempt only at 11am thanks to following
rule 3 above since I had to walk away for a meeting with someone. Anyways. 
Had fun getting the first day right so quickly.

Spreadsheet Link : [Click Here](https://docs.google.com/spreadsheets/d/1eUcM825_ziNHo7qncAFBi6TeErPBpeRcgh-kF3_zAOo/edit)

## Day 02

This puzzle followed instructions based on which a certain amount had to change
continuously. Well in this case, two amounts which can be thought of as coordinates.
But this day also brought a new item in the workflow. The input was given as two
terms, a word and a number with space in between. So full disclosure, I had an
open window handy with my go-to editor geany which I use for simple, escape-based
and regex cleanup of data I get. So I did paste the input there first and converted
the spaces to tabs so that I could paste the information into my spreadsheet
first. 

The input was thus treated as two columns on the left. The horizontal movement
and depth was now simply about a conditional within which there was arithmetic
betwen a "previous value" and a "current value". This was understood and coded
fast but the second part troubled me.

The trouble was not in the solution, as anyone can see. It was straightforward. I 
think for a long time I understood the question incorrectly or perhaps the wording
in the puzzle confused me. So I made the mistake of leaving some remnants of
the arithmetic of the first part in there, continuously getting my answer wrong
and getting frustrated before realizing my mistake. But solution was similar
again with one looking for the numbers generated at the end of the cycle, i.e
in spreadsheet terms, the last row mattered. 

Spreadsheet Link : [Click Here](https://docs.google.com/spreadsheets/d/1BBQU7gAaDbPbkbcplghReMJq0bxIItvoS_j_hxoHVjQ/edit)

## Day 03

This puzzle involved binary numbers but that wasn't the difference. It involved
bit operations and the first thought that crossed my mind is if I can even visualize
this in a spreadsheet. Was there functions to get to the first bit of a number? I didn't
know ... I don't know. Then I decided to go with the flow here and just decide that
each bit itself is a column. Now technically for a spreadsheet that thinks in decimals
each column is a decimal number but why not? So I pasted the input into my
ever reliable geany editor and created a TAB between all digits so that the input
was ready to be pasted to my spreadsheet and it was there in all its glory looking
like a rain of 0s and 1s. I left some rows above for calculations.

Part one of the puzzle was rather easy. Compare which bit appears the most in
each position. So this simply involved spreadsheet arithmetic that can either
be cajoled into one cell or spread across multiple by adding the 1s and 0s separately
and then comparing them. I chose the method of keeping smaller portions of
the arithmetic in separate cells for easy debugging. It was all about concatenating
the "answer cells" back into a decimal, copying over the answer and pasting
at the AoC website. Second part wasn't easy at all, although it seemed to be.

Second part should have been easy if I had done some clear thinking and had not
been over-ambitious. Since it involved filtering the input over and over again I was
focussed on how to implement that filtering logic and forgot that I could not
go down a "filtered path" for both the values that were required this is because
they are "opposite sides of a tree" if I may think of that data structure i.e the
numbers discarded for 0 are the ones I need to use for 1. My filtering logic with
a spreadsheet however would just not let that happen, but I unnecessarily believed
it would and then went down a rabbit hole. So finally gave up and created two
sheets for both the paths (something if done first would have saved time) and
then the filtering logic worked.

Filtering logic: The filtering logic worked. I had to deal with the fact that I wasn't
yet writing macros or code. So a given cell can only be "aware" if itself and then
display values based on its surroundings and cannot modify "itself" or even other
cells in the neighborhood. So the only way to remember that a given bit position
is unusable was to maintain a state-cell next to the cell containing the bit. So for
Part TWO I created additional columns (marked in yellow) to maintain state after
each iteration. A -1 (minus one) indicates that this bit is not available for summaries.
Now the way it works is that on the same row, all cells will be -1 if one on the left
is -1, thus an entire number is filtered out. Rest is easy to figure out because it
is similar to Part ONE. The numbers get summed up, compared and then a bit
is put in the location. Except for one difference; some additional logic because this
operation can end anytime when we run out of numbers, so an overlapping IF
condition to handle that too. 

Spreadsheet Link : [Click Here](https://docs.google.com/spreadsheets/d/1-ixuC2pk05qcwyeLnP5vBzve-c6kwPxcnC7haGPaWd4/edit)

## Day 04

{ details and correct spreadsheet coming soon }

