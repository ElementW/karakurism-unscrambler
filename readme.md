Karakurism unscrambler
=========

Seen in Hyperdimension Neptunia: The App! (not on TV though :< )

Using
----
Java sucks so much, I'm lazy to write a MANIFEST.MF to provide the main class' name.
So use the unscrambler like this:

```sh
java -cp unscramble.jar Main <files>
# For example
java -cp unscramble.jar Main *.mp3 *.png
```

Wildcards (`*`) are not handled by the unscrambler itself, but by your shell. Note simple wildcards does not do recursive search, use a command like this if you want to:
```sh
java -cp unscramble.jar Main $(find . -name '*.mp3' -o -name '*.png')
```
For further headache, refer to `find`'s manpage.

The unscrambler will ***replace*** (note the bold font) the files supplied by their unscrambled versions; it will also ignore already unscrambled files and skip non-readable files.

Compiling
----

What you need:
 - `make`
 - A working `javac` and `jar` utility

How to build (caution: vodoo magic):
 1. `make`
 2. Done.

Tech note
----

The unscrambler is set up to always use Hyperdimension Neptunia: The App's decoding key. If the key changes or assets from another game/app/whatever went through the same scrambling process, open Main.java and change the `int key = ...;` line to whatever the key is. (Then recompile)

Bottom note
----

Coded in 5 hours (decompiling, analysis, refactoring)