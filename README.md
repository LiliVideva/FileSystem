# File System

x8academy: Core Java course, Project during the course

## Commands

### cd

Change current directory (by default - "root/home/")

```
cd <location>
```

### mkdir

Create new directory in the current location

```
mkdir <dir_name>
```

### create_file

Create an empty file  with such name in the current location
```
create_file <file_name>
```

### cat

Display the file contents

```
cat <file_name>
```

### write

Write the content into the file onto the given line

```
write <file_name> <line_number> <line_contents>
```
Overwrite the content into the file onto the given line with the set one

```
write --overwrite <file_name> <line_number> <line_contents>
```

### ls

Lists the files in the current directory
```
ls
```
List the files in the current directory in ascending/descending order, sorted by size
```
ls --sorted asc
ls --sorted desc
```

### wc

Count number of characters, words and lines in the given file
```
wc <file_name>
```
Count number of characters in the given file
```
wc -c <file_name>
```
Count number of words in the given file
```
wc -w <file_name>
```
Count number of lines in the given file
```
wc -l <file_name>
```

### rm

Delete file with given name (Not actually delete, just make it inaccessible for other commands and only if a new file is created on top of that memory, then the file contents are written on top)

```
rm <file_name>
```

### remove

Remove from file  lines from the first one inclusively to the second one exclusively
```
remove <file_name> <line_number1>-<line_number2>
```

## Specifics

### File size

```
file_size = number_of_lines + nuber_of_characters
```

### Pipe

Implemented pipeline functionality - an output of a command to be the input of the next

### Virtual and real file system

Two file system implementations supported - real and virtual. It is preliminary set what is the type of file system on which the user will be working