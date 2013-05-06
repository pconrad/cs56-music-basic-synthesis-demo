package edu.ucsb.cs56.S12.ISSUE0000779.old;
import javax.swing.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

/**
@TODO add a tool for inserting keybindgs/keyboard mode: in keybinding, certain keys are set to bind to a set of params,
 with keyboard mode a set of keys isused as a keyboard for a 
	
	make a frame with five input boxes plus a keylistner bound to the space character
	*****************************
	*atk time					*
	*decay time					*
	*release time				*
	*atk vol(0-1)				*
	*sustain vol(0-1)			*
	*****************************
	use JLabel and JTextBox
	then add a keyboardlistner--on keypress start the loader thread
								on release intterupt the sustain thread
								literl on key
*/
public class BasicGuiForSynth{
	
}
