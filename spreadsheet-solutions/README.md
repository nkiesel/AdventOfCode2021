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

{ details coming soon }

https://docs.google.com/spreadsheets/d/1BBQU7gAaDbPbkbcplghReMJq0bxIItvoS_j_hxoHVjQ/edit

## Day 03

{ details and correct spreadsheet coming soon }

https://docs.google.com/spreadsheets/d/1-ixuC2pk05qcwyeLnP5vBzve-c6kwPxcnC7haGPaWd4/edit

## Day 04

{ details and correct spreadsheet coming soon }

https://docs.google.com/spreadsheets/d/1-ixuC2pk05qcwyeLnP5vBzve-c6kwPxcnC7haGPaWd4/edit