# Data Structures 2020 Assignment 4

Publish date: 17 /0 5 /

Due date: 7 /0 6 /

Senior faculty referent: Dr. Sebastian Ben-Daniel

Junior faculty referents: Maor Zafry, Amihay Elboher

## Assignment Structure

**1.** B-trees:
    **i.** Complete implementation
**ii.** 2 - pass insertion
**2.** Cuckoo Hashing - bonus

# Section 1: B-Tree

**A note for all this section** : the implementation should be as you saw in the lectures. We will mention
it again in a few strategical points. One thing we declare here which should not be different from the
lectures but better to be mentioned: you always use the left before the right (for example in shift –
try the left brother first) and the predecessor before the successor.

**Update** – version 2 : in the beginning we meant not to check recurrences heavily, that is we didn’t want
to check insertions of some value more than twice and not in the same node. Students showed us that
in more complicated cases it might become a serious trouble. Due to those complicated cases we
decided not to check recurrences at all. This update is relevant for the whole section. We apologizing
to anyone who spent time for that.

# Task 1 .1: Complete the B-tree Implementation

The serious/relevant parts, at last! In this task you are given an implementation of the B-Tree data
structure. As any (basic) data structure, B-Trees also have many different variants, and the
implementation you got is also different from the implementation you have seen in the lectures.

In this sub-task you should implement the methods “insert” and “delete”.

For this goal you should make some minor changes in the current implementation, and we leave this
problem for you. It’s not complicated...

We left the implementations of “add” and “remove”, and you may find them helpful.

**DO NOT MAKE ANY CHANGES IN THE “toString” METHODS!!!**

We will use those methods to check your code, and we assume they work exactly as it is in the code
you got. No appeals on that issue will be accepted!

The former methods – “add” and “remove” should not work in the code you submit. We will not test
them.

After task 1 .2 there are some notes about the supplied implementation for your convenience.

### Some advice for how to work on this sub-task:

1. You should know well how the algorithms of B-tree work, so repeat on the slides until you feel
    confident enough (or a bit more than that). You will use the slides a lot while working. In any
    case of doubt, decide according to the slides of the lectures (and not according to the slides
    of the practical sessions), and don’t forget – left before right and predecessor before
    successor.
2. Try to play with the given implementation: create a tree, insert some values, then remove
    some values and so on, and before printing the tree, think what you expect to get according


```
to what you’ve learned and what you actually get. It will help you to figure out the changes
you should make before implementing insertion and deletion.
```
3. Read the given implementation **carefully**. You can add comments where identifying suspected
    point/s while reading. It’s highly recommended not to change anything until reading all the
    code, or at least all the code sections that are related to what you want to change.
4. The order of changes should be done reasonably. We don’t give you any clues here, because
    it’s a very important ability you should develop. A simple and basic example is that it’s not a
    good idea to implement the deletion before fixing the insertion.
5. The best way to check your changes is writing methods and call them from a main function.
    Any such method should test one specific case by building a new tree on operate on it
    somehow. Write these methods, i.e. the main and the test methods, in a separated class – not
    in the Java file that contains the B-tree class. When debugging any test, comment out the calls
    to any of the other test methods, and when you are done, check that/whether you still pass
    all the former tests.
6. It might be very useful to back up your code occasionally.

You are absolutely invited to share your tests with your classmates. Helping friends (giving and taking)
is a crucial part of the university studies. **BUT** , beware not to share anything except for the tests
themselves! Dishonesty will be treated harshly, and please don’t try to attempt us.


# Task 1 .2: Two-pass Insertion

Advice: implement this sub-task not before you implement “insert” of task 1 .1.

You’ve learned two insertion algorithms in the class – 1 - pass and 2-pass. Here is a brief reminder for
2 - pass insertion. In the 1-pass insertion, when inserting a new key to the tree, any node in the path
from the root to the node the key should be inserted to is splat if found full. The idea is that when
splitting a node, the middle key passes to the parent, so the parent should have room for it, or, of
course, for the new key that should be inserted to this node, so it should have room for that key. In
some cases, the splitting is redundant, since no key will pass from the child node to the node that is
splat. This is the motivation behind the 2 - pass algorithm.

In the 2 - pass algorithm only the nodes which should be splat are really splat. You saw in the lectures
one way to do that, which declines two times: it goes down from the root to the right node – the first
pass, and then it declines again from the uppermost node that should to be splat to the right node.
The second decline starts from a node that was found during the first decline.

Here is another way you can implement the insertion (not a better one, just another alternative):

1. Finds the leaf that the key should be inserted to.
    This is the first pass – from the root to the correct leaf. This descent is just a search, meaning
    to say that no splat occurs while doing it.
2. Ascent while it is needed.
    When is it needed to ascent? If the current node has the maximal number of keys, then there’s
    no room for a new key to be inserted, so it should be splat. In such case, the parent node
    should have less than the maximal number of keys a node can hold. If this is not the case, the
    parent node should be splat, then the parent of the parent node will be checked also, i.e.
    there is a need to ascent.
3. From the node the algorithm has gotten to, it descents again – the “second” pass – and split
    any node on the way down to the leaf.

Let’s present an example:

In the tree below, the minimal rank is 푡= 2 , so the number of keys in any node is less than or equals
to 3. In this tree, the root is full, so, using 1 - pass insertion, the root should be splat (what leads the
tree to be higher by 1). Unlike that, the 2 - pass insertion doesn’t split the root, since no matter which
key is to be inserted, there’s room for it and for the needed changes in the lower levels.


For instance, if one inserts the key W to this tree using the 2 - pass algorithm, the new key is inserted
to the right place of the rightmost leaf without any split. If, in addition, he inserts X as well, then the
only node that should be splat is the rightmost one, but the root can still stay as is without any splitting.
After such insertions the tree looks like this:

A point of thought: the algorithm doesn’t necessarily optimize the insertion in some aspect. Consider
the case that the keys Y and Z are inserted too and try to think whether it was worth to use 2 - pass
instead 1 - pass.

In this sub-task, you are required to implement the 2 - pass algorithm. You were given an
unimplemented method named “insert2pass” that gets T value, which is the key to be inserted, and
should work as depicted above, or more accurate – as was taught in the class. The method should
return a boolean value: the returned value should be equivalent to the value returned from the “add”
method in the given code. In different words – the method “insert2pass” should return true if and
only if “add” would return true for the same tree and value parameter. Well, it means that it returns
always true, because insertion never fails. Thank us for the answer afterwards...

# Task 2: Cuckoo Hash Backtracking

In the cuckoo hash table data structure, in our version anyway (which is not the common version),
there is one array and a family of hash functions. The following is a short explanation for what’s going
on in the code we give you. The explanations here are written in a bit intuitive manner for simplicity.
After reading the notes here, it will be much easier for you to read the code. In our implementation
there is a use of 2 hash functions only, but generally the family of the hash functions might be bigger,
and most of the explanation is for 푑 hash function ℎ 1 ...ℎ푑.

Insertion:

Calculate ℎ 1 (푘), where 푘 is the key to insert. If the cell of that index is occupied – what called a
collision, try to hash 푘 by ℎ 2. It means that we check if the cell of index ℎ 2 (푘) is occupied. If not, 푘 will
be inserted there, and if yes, ℎ 3 will be used. The process proceeds in that way until ℎ푑, and if there
is a collision again, 푘 is inserted to the cell of index ℎ푖(푘), where 푖 is chosen randomly. The only
demand is that ℎ푖(푘) will be different from the current index (this is the idea behind the check whether
푝표푠==푘푖푐푘_푝표푠 in the helper method of insertion). But what about the former element that was
there? This one is taken out (or kicked out...) and go on a journey for a new house. It does exactly the
same process, and if it doesn’t find an empty cell... well, the expelled element is not a sucker, and it
behaves nicely in the same way as before: kicking out the former resident of ℎ푗(푎푟푟푎푦[ℎ푖(푘)]) (j is
chosen randomly again with the mentioned demand) and elegantly sits in the empty cell. Don’t worry,
the third element is not a child, and it does exactly the same as was done towards him if needed, and
so on.

And what if there is a cycle of collisions? A Groicé Broch! There are some ways to deal with that, and
we use just one simple way. The data structure holds a list called “stash”, and 푘 is inserted to this list.

Deletion:

The key parameter, denoted as 푘, might reside in any of the cells of indices ℎ푖(푘),∀푖∈{ 1 ...푑}, so
any of these cells is checked if not yet found. When 푘 is found, it is deleted. If it is not in the array, it
still may be in the stash. If it is there – delete it, and if not – do nothing. No exceptions should be
thrown in any situation.

Search:

Similar to deletion but without the deletion itself: check in the array. Found? Great, otherwise check
the stash. Found? Good, else the element is not in the table.

### Note about time complexity

Though the complexity issue is out of our objectives in this assignment, you can see that the search
and deletion are executed in 푂(푑) time in the **worst case**! 푑 is in 푂( 1 ), of course, so 푂(푑)=푂( 1 ).
The insertion complexity may be executed in 푂(푛), but on average it takes 푂( 1 ) too.


## Implement backtracking for cuckoo hash table

You should implement the method “undo”, which doesn’t get any parameters, and cancels the last
insertion. And now in more detail:

We take care just about “effective operations”, i.e. successful insertion or successful deletion but **not**
search and unsuccessful operations. Now, let assume that the last 푘 effective operations were
insertions, then calling “undo” succeeds 푘 times. In the next call (the (푘+ 1 )푡ℎ call) nothing happens.
The counting of 푘 calls starts from the last deletion or from the beginning – the latter of them.
Canceled insertions are not considered in the counting. For example, if 10 insertions were executed,
then 2 deletions, 4 insertions, 2 searches and 1 undo, then 푘= 3. Why? The deletions exclude the
first 10 insertions, then we count just the first 3 insertions, because the 4th was canceled by the undo.
The searches are ignored, since search is not an effective operation.

This method never throws an exception.

### Critical note!!!

Who, for God sake, is the weird guy who decided to call this data structure “cuckoo”?.. OK, so in
computer science there are many names of that kind, and even worse. The only reason to use such
names is very simple and very good – it reflects some idea that occurs somewhat in the data structure
/ algorithm / whatever. In our case, the reason is derived from the way the cuckoo bird behaves when
it is born (see this staggering video!). When the cuckoo fledgling hatches from the egg, it’s in a cruel
competition against the other chicks, that are not necessarily hatched yet. Mommy and daddy birds
will not be able to feed more than one offspring, so they do not interrupt – and even expect – the
competition to be over. Oh yeah, just one winner survives... and takes it all! The only rule is very
simple: throw any other guy out of the nest. The next chronicles of the nice family are not relevant
very much for now. Well, this data structure is not that cruel (and this task as well), but the idea of
throwing someone who resides in someone else’s place appears in that data structure. So maybe this
is not very a appropriate name (educationally speaking), but you must agree that this is an outstanding
name!
