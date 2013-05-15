cs56-music-basic-synthesis-demo
===============================

A basic demo of some simple synthesis starting with sine waves

# Example run
	java -cp build  edu.ucsb.cs56.projects.music.basic_synthesis_demo.ADSREnvelopedContinuousSound 220 0.9 0.1 0.2 0.6 1.0 0.2

The parameters are: frequency, amplitude, attack, decay, sustain amplitude, sustain time, release.
	frequency: The frequency to play at.
	amplitude: The maximum volume.
	attack: The number of seconds to attack for.
	decay: The amount of time to decay/sustain.
	sustain amplitude: The sustained volume.
	sustain time: The time to sustain for.
	release: The amount of time to release over.

# Useful resources
	The following may be helpful:
		http://proquest.safaribooksonline.com/book/programming/game-programming/0596007302/audio-	synthesis/killergame-chp-10-sect-1?reader=html

	From off campus:
		http://proquest.safaribooksonline.com.proxy.library.ucsb.edu:2048/book/programming/game-programming/0596007302/audio-synthesis/killergame-chp-10-sect-1?reader=html
