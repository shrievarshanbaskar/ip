Fozza User Guide

Fozza is a task management chatbot with a JavaFX graphical user interface (GUI).
It allows users to manage tasks efficiently using simple command-based inputs.
==========================================================================================
Overview:

#Fozza supports:

Creating tasks (todo, deadline, event, note)
Listing tasks
Searching tasks
Marking and unmarking tasks
Deleting tasks
Persistent storage (tasks are saved automatically)

All commands are entered in the input field at the bottom of the GUI.

==========================================================================================

##Adding Tasks-

#Adding Todos : Adds a simple task without a date.

Example:
todo read book

Expected outcome:
Got it. I've added this task:
[T][ ] read book
Now you have X tasks in the list.

---------------------------------------------------------------------------------------

#Adding Deadlines : Adds a task with a deadline.

Format:
deadline DESCRIPTION /by DATE

Example:
deadline submit report /by 2026-03-01 18:00

Expected outcome:
Got it. I've added this task:
[D][ ] submit report (by: 2026-03-01 18:00)
Now you have X tasks in the list.

----------------------------------------------------------------------------------------

#Adding Events : Adds a task that occurs within a time range.

Format:
event DESCRIPTION /from START /to END

Example:
event project meeting /from 2026-03-01 14:00 /to 2026-03-01 16:00

Expected outcome:

Got it. I've added this task:
[E][ ] project meeting (from: 2026-03-01 14:00 to: 2026-03-01 16:00)
Now you have X tasks in the list.


------------------------------------------------------------------------------------------

#Adding Notes : Adds a general note.

Example:
note buy groceries

Expected outcome:

Got it. I've added this task:
[N][ ] buy groceries
Now you have X tasks in the list.


-------------------------------------------------------------------------------------------

##Managing Tasks:

#Listing Tasks:Displays all tasks currently stored.

Example:
list

Expected outcome:
Here are the tasks in your list:

[T][ ] read book
[D][X] submit report (by: 2026-03-01 18:00)


--------------------------------------------------------------------------------------------

#Finding Tasks : Searches for tasks containing a keyword.

Format:
find KEYWORD

Example:
find report

Expected outcome:

Here are the matching tasks in your list:

[D][ ] submit report (by: 2026-03-01 18:00)


---------------------------------------------------------------------------------------------


#Marking Tasks as Done : Marks a task as completed.

Format:
mark INDEX

Example:
mark 1

Expected outcome:
Nice! I've marked this task as done:
[T][X] read book

------------------------------------------------------------------------------------------------


#Unmarking Tasks : Marks a completed task as not done.

Format:
unmark INDEX

Example:
unmark 1

Expected outcome:

OK, I've marked this task as not done yet:
[T][ ] read book


--------------------------------------------------------------------------------------------

#Deleting Tasks : Removes a task from the list.

Format:
delete INDEX

Example:
delete 2

Expected outcome:

Noted. I've removed this task:
[D][ ] submit report (by: 2026-03-01 18:00)
Now you have X tasks in the list.

-----------------------------------------------------------------------------------------------


##Exiting the Application : 

Closes Fozza.

Example:
bye

Expected outcome:
Bye. Hope to see you again soon!

-------------------------------------------------------------------------------------------------


##Data Storage -  Fozza automatically saves tasks to a local file.
Tasks will be restored when the application is restarted.
==========================================================================================