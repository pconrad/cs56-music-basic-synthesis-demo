cs56-music-basic-synthesis-demo
===============================

A basic demo of some simple synthesis starting with sine waves

# Example run
	java -cp build  edu.ucsb.cs56.projects.music.basic_synthesis_demo.ADSREnvelopedContinuousSound 220 0.9 0.1 0.2 0.6 1.0 0.2

The parameters are: frequency, amplitude, attack, decay, sustain amplitude, sustain time, release.

frequency: The frequency to play at. (0 - 1000)

amplitude: The maximum volume. (0 - 1.0)

attack: The number of seconds to attack for. (0 - 1.0)

decay: The amount of time to decay/sustain. (0 - 1.0)

sustain amplitude: The sustained volume. (0 - 1.0)

sustain time: The time to sustain for. (0 - 1.0)

release: The amount of time to release over. (0 - 1.0)

# Gui Example run
        java -cp build edu.ucsb.cs56.projects.music.basic_synthesis_demo.view.GuiForSynth

GUI is simply a skeleton, and is not functional

# Melody Example run
	java -cp build edu.ucsb.cs56.projects.music.basic_synthesis_demo.Melody_Code.Melody 0.1 0.2 0.6 1.0 0.2

the parameters are: attack, decay, sustain amp, sustain time, release

# Useful resources
The following may be helpful:
	http://proquest.safaribooksonline.com/book/programming/game-programming/0596007302/audio-synthesis/killergame-chp-10-sect-1?reader=html

From off campus:
	http://proquest.safaribooksonline.com.proxy.library.ucsb.edu:2048/book/programming/game-programming/0596007302/audio-synthesis/killergame-chp-10-sect-1?reader=html
