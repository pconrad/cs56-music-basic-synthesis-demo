W14 Ready! (Bronwyn Perry-Huston)
cs56-music-basic-synthesis-demo
===============================

A basic demo of some simple synthesis starting with sine waves

![](http://i.imgur.com/xk4Nk1c.png)

# Melody GUI Example
	ant melodyGUI

# GUI Skeleton Example Run
	ant basicGUI
		
GUI is simply a skeleton, and is not functional

# Melody With Full ASR Options (Single Melody File)
	 ant melodyADSR_Options -Darg0=[attack] -Darg1=[decay] -Darg2=[sustain amp] -Darg3=[sustain time] -Darg4=[release] -Darg6=[filename.txt]
	
Replace each component of -Darg with the desired value. Ex:

	 ant melodyADSR_Options -Darg0=0.1 -Darg1=0.2 -Darg2=0.3 -Darg3=0.4 -Darg4=0.5 -Darg6=YellowSub.txt

Note that -Darg5 is skipped, as it is set to '1' in build.xml

# Melody Run of YellowSub, MaryHadALittleLamb, and Default
	 ant melodyAll

This uses ADSR presets: attack: 0.1 // decay: 0.2 // sustain amplitude: 0.6 // sustain time 1.0 // release 0.2

# Melody of Specific File	
	 ant melody -Darg6=[filename]

Replace [filename] with any text file in build/resources. Ex:

	 ant melody -Darg6=YellowSub.txt

This uses ADSR presets: attack: 0.1 // decay: 0.2 // sustain amplitude: 0.6 // sustain time 1.0 // release 0.2

# More Options With Full Classpath
To play more than one melody in a row, use 

	java -cp build edu.ucsb.cs56.projects.music.basic_synthesis_demo.Melody_Code.Melody 0.1 0.2 0.6 1.0 0.1 3 YellowSub.txt MaryHadALittleLamb.txt Default.txt 

This will play the Melodies contained in the files YellowSub.txt, MaryHadALittleLamb.txt, and Default.txt one after another. 
The Melodies in the files will be played in the order that they are entered. All of the text files to be played must be in the build/resource/ folder.

the parameters are: attack, decay, sustain amp, sustain time, release, the number of files to play, and the names of the files containing the melodies


# Useful resources
The following may be helpful:
	http://proquest.safaribooksonline.com/book/programming/game-programming/0596007302/audio-synthesis/killergame-chp-10-sect-1?reader=html

From off campus:
	http://proquest.safaribooksonline.com.proxy.library.ucsb.edu:2048/book/programming/game-programming/0596007302/audio-synthesis/killergame-chp-10-sect-1?reader=html
